/*
* File: Ex2.java
* Created: 4 November 2020, 11:38
* Author: Stella Jaquiss (u2003869)
With credit to Stephen Jarvis for the creation of DumboController.java and Broken.java in 2001 / 2002
*/
/* Excercise 2 Preamble
The initial problem surrounding 'fair' probabilitlites concerned the idea that the code would round the random number produced lying between 0 and 3.
This meant for example that the direction LEFT would only be selected if the randno variable were in the interval [0,0.5), 
whereas RIGHT would be chosen if randno were in the interval [0.5, 1.5). 
This lead to the fact that RIGHT and BEHIND were twice as likely to be chosen as LEFT and AHEAD. 
I have altered the code such that randno is now stored as a double, rather than as an int and using the method .round(), 
and by using inequalities in my boolean statement in the if conditional statement rather than equalities 
to ensure that each direction has an equal probablity of being selected if staying in the direction AHEAD leads to a collision 
or the robot decides to randomly change direction.

I've incorporated the random change in direction with a 1 in 8 chance by adding an OR operator to my if statement, 
and by calling a separate variable (secondRandno) that takes a random value between 0 and 8. 
See comments below for further info into choice of interval selected. 
	I edited the count.pl file and added a print command into my code every time the robot changed direction randomly (which I have now removed)
to analyse the amount of times there was a random change in direction (using the BlankGenerator in the maze environment) 
to confirm the fact that my code meets the spec provided. (i.e. For 21,286 steps in total, the robot changed direction randomly 2741 times. 
(This is roughly 1 in every 8 steps.)
Though adding a random change in direction was disruptive, as without it, in a blank maze, less steps would be taken to reach the target, 
it has benefited with the fact that the robot will no longer be stuck in an infinite loop going back and forth in a PrimMaze 
(or atleast the likely hood of that happening has been greatly reduced). This is since, prior spec stated the robot should always go ahead 
if it doesn't cause a collision.
	I do think some sort of implementation of IRobot.BEENBEFORE could be used, rather than randomly changing direction on average every 8 moves, 
to make the robot more efficient in detecting whether it is stuck in such a loop as mentioned above.
*/

import uk.ac.warwick.dcs.maze.logic.IRobot;
import java.lang.Math;

public class Ex2 {
	public void controlRobot(IRobot robot) {

		double randno;
		double secondRandno = Math.random()*8;
		int direction = IRobot.AHEAD; 

		if (((robot.look(direction)) == (IRobot.WALL)) || (secondRandno <= 1)){
			/* Note here chance of secondRandno being less than or equal to one is 1/8
			So robot will change direction, other than for avoiding a collision, on average 
			1 in 8 goes. */

			do {
				// Select a random decimal number between 0 and 4
				randno = Math.random()*4;
	
				// Convert this to a direction
				if (randno <= 1){
					direction = IRobot.LEFT;
				}else if (randno <= 2){
					direction = IRobot.RIGHT;
				}else if (randno <= 3){
					direction = IRobot.BEHIND;
				}else
					direction = IRobot.AHEAD; //NB remaining AHEAD is still a valid random new diection, so meets the spec
				
		// Repeats process if new random direction will lead to a collision 

			} while ((robot.look(direction)) == (IRobot.WALL));
		}
		robot.face(direction);  /* Faces the robot in direction chosen*/ 	

		int walls = 0;
		int a;

		/* Iterates through all four directions
		And adds one to walls variable if there's a wall in a certain direction */

		for (a = IRobot.AHEAD; a <= IRobot.LEFT; a+=1){
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
			default:
				System.out.println("but I'm trapped!!");break;
		}
		
	}

}