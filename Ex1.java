/*
* File: Ex1.java
* Created: 4 November 2020, 11:38
* Author: Stella Jaquiss (u2003869)
With credit to Stephen Jarvis for the creation of DumboController.java and Broken.java in 2001 / 2002
*/

/* Ex1 Preamble:
	I've have ensured that the robot won't collide with any walls as, if the direction selected will lead to a collision, 
	I force the robot to pick a new direction. There is the exception of a 1 by 1 maze where no matter where the robot goes, 
	it will always collide with a wall, although in that scenario, because it's 1 by 1, the robot will have already reached 
	the endpoint, so all scenarios are covered in my code. 
	Lastly, I've chosen switch statements to output my log as I find it to be a more logical and readable approach. 
	(See comments below for more information about the workings of the code) */


import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.lang.Math;

public class Ex1 {
	public void controlRobot(IRobot robot) {

		int randno;
		int direction;
		do{
			// Select a random number
			randno = (int) Math.round(Math.random()*3);
	
			// Convert this to a direction
			if (randno == 0){
				direction = IRobot.LEFT;
			}else if (randno == 1){
				direction = IRobot.RIGHT;
			}else if (randno == 2){
				direction = IRobot.BEHIND;
			}else {
				direction = IRobot.AHEAD;
			}
		// Repeats process if new direction will lead to a collision (ex1)

		}while ((robot.look(direction)) == (IRobot.WALL));

		robot.face(direction);  /* Faces the robot in direction chosen*/ 

		int walls = 0;
		int a;

		/* Iterates through all four directions
		And adds one to walls variable if there's a wall in a certain direction */

		for (a=IRobot.AHEAD; a <= IRobot.LEFT; a+=1){
			if (robot.look(a) == IRobot.WALL) 
				walls+=1;
		}

		// Then prints direction...		
		switch (direction){
			case IRobot.LEFT: 
				System.out.print("I'm going left ");break;
			case IRobot.RIGHT:
				System.out.print("I'm going right ");break;
			case IRobot.BEHIND:
				System.out.print("I'm going backwards ");break;
			default:
				System.out.print("I'm going forwards ");break;
		}
		
		/* depending on the number of walls surrounding the robot, 
		program prints out what sort of place the robot's at */

		switch (walls){
			case 0: 
				System.out.println("at a crossroads");break;
			case 1:
				System.out.println("at a junction");break;
			case 2:
				System.out.println("in a corridor");break;
			case 3:
				System.out.println("at a deadend");break;
		}
		
		
	}

}