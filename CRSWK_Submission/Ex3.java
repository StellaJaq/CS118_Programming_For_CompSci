/*
 * File:    Ex3.java
 * Created: 4 November 2020, 11:38
 * Author:  Stella Jaquiss (u2003869)
 With credit to Stephen Jarvis for the creation of DumboController.java and Broken.java in 2001 / 2002
 */
/* Excercise 3 Preamble 
The design for my headingController first revolved around the idea of taking the two possible directions that would lead 
the robot to the Target, and going through systematically the following three cases: 
	- the two directions (one vertically orientated, and the other horizontally) lead to a wall
	- the two directions don't lead to a wall
	- or only one of the directions leads to a wall
The problem lied in dealing with the special case that either the vertical or horizontal heading was on the same axis as 
the target. This meant I had to add an extra if statement so that this case could be handled. See comments below.
The other problem was finding a way to manipulate the output of isTargetNorth / isTargetEast as to be able to eventually 
store:
	IRobot.NORTH, .SOUTH, .EAST, .WEST. 
 correctly in the vertical, horizontal variables. See comments below for further info.

The way that this is built means that if the direction chosen doesn't lead to a collision, then the robot will always head 
towards the target. However with this being said, this also means that in some circumstances the robot will be caught in a 
loop and won't reach the target. For example, say there exists a maze such that the robot encounters a wall in both 
directions that lead the robot to the target. In its first step, it will be forced to go in the oppositeVertical or 
oppositeHorizontal direction (otherwise it'd collide with the wall), moving away from the target. However in the second step, 
the robot would go back on itself, as it's instructed (in the spec) to always head towards the target if possible. Plus by 
originally moving one step away from the wall, we know moving one back won't cause a collision. And it's with this logic 
that the robot gets stuck in a loop (in any maze except from a blank one) going back and forth on itself never reaching the 
target. 
		This is just one example scenario where the robot exhibits this sort of behaviour. Much like Ex2, I'd utilise 
IRobot.BEENBEFORE in some sort of way as to identify when the robot has caught itself in a loop and is going back on itself.
Or perhaps systematically creating a log of past successful and unsuccessfull routes plus other relevant details, such that
the robot "learns" from it's past mistakes so it doesn't make them again for a paticualar maze in the next go. */

import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.lang.Math;

public class Ex3{
	//controlRobot main method
	public void controlRobot(IRobot robot) { 
		int heading = headingController(robot);
		ControlTest.test(heading, robot);
		robot.setHeading(heading); //points robot in appropiate heading/direction
	}

	public void reset() { //prints results of test
		ControlTest.printResults();
	}

	private byte isTargetNorth(IRobot robot) {
		int coordY = robot.getLocation().y; // y coordinate of robot
		int targetCoordY = robot.getTargetLocation().y; // y coordinate of target
		byte result;

		if (targetCoordY > coordY) //if target is south of robot return -1
			result = -1;
		else if (targetCoordY < coordY) // else if target is north of robot return 1
			result = 1;
		else
			result = 0;
		
		return result;
		// returning 1 for ‘yes’, -1 for ‘no’ and 0 for ‘same latitude’
	}

	private byte isTargetEast(IRobot robot) {
		int coordX = robot.getLocation().x; // x coordinate of robot
		int targetCoordX = robot.getTargetLocation().x; // x coordinate of target
		byte result;

		if (targetCoordX > coordX) // if target is east of robot return 1
			result = 1;
		else if (targetCoordX < coordX) // else if target is west of robot return -1
			result = -1;
		else
			result = 0;
		return result;

		// returning 1 for ‘yes’, -1 for ‘no’ and 0 for ‘same longitude’
	}
	private int lookHeading (int newHeading, IRobot robot){
		int oldHeading = robot.getHeading(); //Current heading the robot is facing
		robot.setHeading(newHeading); 
		//Makes the robot to face a certain heading that we want to look at. 
		//If we want to look in this new heading, we can now use the relative direction IRobot.AHEAD
		
		int result = robot.look(IRobot.AHEAD);		
		//But the robot has changed direction from where it started
		//so we change it back before we return a result 

		robot.setHeading(oldHeading);
		return result;
		
	}

	// Method takes two integers as parameters and randomly returns one of them
	private int randomChooseInt (int num1, int num2){
		double randomNumber = Math.random()*2;
		if (randomNumber <= 1)
			return num1;
		else 
			return num2;
	}

	public int headingController(IRobot robot) {
		int vertical = 0; //vertical (ie NORTH or SOUTH) 
		int horizontal = 0; // ...and horizontal (ie EAST or WEST) will represent the two headings that lead to target
		int finalHeading; //This will be the variable that the controller ends up returning

		//If robot is on same latitude, or longitude, as target, we leave corresponding direction as initial value (which is 0)
		if (isTargetNorth(robot) != 0) 
			vertical += ((IRobot.EAST) - isTargetNorth(robot));
		if (isTargetEast(robot) != 0)
			horizontal += ((IRobot.SOUTH) - isTargetEast(robot));

		//NB isTargetNorth, isTargetEast outputs 1,0, and -1, and the relationship between the headings are defined as:
		// EAST+1 = SOUTH, EAST - 1 = NORTH, so IRobot.EAST +/- 1 will output a heading in the vertical
		// SOUTH +1 = WEST, SOUTH - 1 = EAST, so IRobot.SOUTH +/- 1 will output a heading in the horizontal

		//Initialises opposite directions to target, in the case that both directions to target lead to a wall
		//ie if vertical == NORTH, then oppositeVertical == SOUTH
		int oppositeVertical = IRobot.EAST + isTargetNorth(robot); 
		int oppositeHorizontal = IRobot.SOUTH + isTargetEast(robot);

		//if (robot is on same latitude) OR (robot is on same longitude) then
		if ((vertical ==0)||(horizontal==0)){ 
			if (vertical == 0)			// for example if robot is on same latitude
				vertical = horizontal; 	// assign horizontal direction to vertical variable
			else
				horizontal = vertical;
		}// This means we can deal with only one direction towards the target while still dealing with two variables 
		//	and not compromising on functionality of the code below
		
		//Precondition: both directions to target lead to a collision, randomly chooses between the remaining opposite directions
		if ((lookHeading(vertical,robot)==IRobot.WALL)&&(lookHeading(horizontal,robot)==IRobot.WALL)){
			do{
				finalHeading = randomChooseInt(oppositeHorizontal, oppositeVertical);
			}while (lookHeading(finalHeading, robot) == IRobot.WALL); //keeps choosing if direction chosen leads to wall
		
		// Precondition: neither direction leads to a collision
		} else if ((lookHeading(vertical,robot)!=IRobot.WALL)&&(lookHeading(horizontal,robot)!=IRobot.WALL)){
			finalHeading = randomChooseInt(horizontal, vertical);
		}
		//Precondition: vertical XOR horizontal leads to a collision
		//NB here vertical and vertical are distinct (i.e robot not on same latitude/longitude with target),
		// as if vertical == horizontal, then one of the two statements above would be satisfied
		else {
			if (lookHeading(vertical, robot) == IRobot.WALL)
				finalHeading = horizontal;
			else
				finalHeading = vertical;
		}
	
		return finalHeading;
	}

}
