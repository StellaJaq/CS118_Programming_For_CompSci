/*
* File: DumboController.java
* Created: 17 September 2002, 00:34
* Author: Stephen Jarvis
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
	
			do{
				// Select a random decimal number between 0 and 3
				randno = Math.random()*3;
	
				// Convert this to a direction
				if (randno <= 1){
					direction = IRobot.LEFT;
				}else if (randno <= 2){
					direction = IRobot.RIGHT;
				}else {
					direction = IRobot.BEHIND;
				}
		// Repeats process if new direction will lead to a collision (ex1)

			}while ((robot.look(direction)) == (IRobot.WALL));
		}
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
			default:
				System.out.println("but I'm trapped!!");break;
		}
		
	}

}