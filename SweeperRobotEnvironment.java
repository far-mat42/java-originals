import becker.robots.*;
import java.util.Random;

public class SweeperRobotEnvironment {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Creating the city and the robot.
		City oakville = new City(8,12);
		SweeperRobot myRobot = new SweeperRobot(oakville,3,-3,Direction.EAST);
		
		// Creating the random generator.
		Random generator = new Random();
		
		// Creating variables that can be modified to change the environment. By default, they are all randomized.
		int landingLength = generator.nextInt(10)+1;
		int stairsUpHeight = generator.nextInt(10)+1;
		int stairsDownHeight = generator.nextInt(10)+1;
		int distanceToStairs = generator.nextInt(10);
		int distanceToTrashCan = generator.nextInt(10);
		int TRASH_PER_SPACE = generator.nextInt(5)+1;
		
		// Creating variables to keep track of the current building position.
		int streetPosition = -3;
		int avenuePosition = 3;
		
		// Creating the walls and trash for the stairs.
		// Draws the ground before the stairs.
		for (int i = 0; i < distanceToStairs; i++) {
			Wall initialGround = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
			
			// Random chance to place between 0 and the limit for trash per space on the current space.
			for (int j = 0; j < generator.nextInt(TRASH_PER_SPACE+1); j++) {
				Thing trash = new Thing(oakville,avenuePosition,streetPosition);
			}
			
			// Updating the build position.
			streetPosition++;
		}
		
		// Draws the stairs going up.
		for (int i = 0; i < stairsUpHeight; i++) {
			Wall bottomStair = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
			Wall sideStair = new Wall(oakville,avenuePosition,streetPosition,Direction.EAST);
			
			// Random chance to place between 0 and the limit for trash per space on the current space.
			for (int j = 0; j < generator.nextInt(TRASH_PER_SPACE+1); j++) {
				Thing trash = new Thing(oakville,avenuePosition,streetPosition);
			}
			
			// Updating the build position.
			streetPosition++;
			avenuePosition--;
		}
		
		avenuePosition++; // Moving the avenue position down one to draw the landing at the right spot.
		
		// Draws the landing.
		for (int i = 0; i < landingLength; i++) {
			Wall top = new Wall(oakville,avenuePosition,streetPosition,Direction.NORTH);
			
			// Random chance to place between 0 and the limit for trash per space on the current space.
			for (int j = 0; j < generator.nextInt(TRASH_PER_SPACE+1); j++) {
				Thing trash = new Thing(oakville,avenuePosition-1,streetPosition);
			}
			
			streetPosition++;
		}
		
		// Draws the stairs going down.
		for (int i = 0; i < stairsDownHeight; i++) {
			Wall bottomStair = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
			Wall sideStair = new Wall(oakville,avenuePosition,streetPosition,Direction.WEST);
			
			// Random chance to place between 0 and the limit for trash per space on the current space.
			for (int j = 0; j < generator.nextInt(TRASH_PER_SPACE+1); j++) {
				Thing trash = new Thing (oakville,avenuePosition,streetPosition);
			}
			
			// Updating the build position.
			streetPosition++;
			avenuePosition++;
		}
		
		avenuePosition--; // Moving the avenue position up one to draw the ground at the right spot.
		
		// Drawing the ground between the bottom of the stairs and the trash can.
		for (int i = 0; i < distanceToTrashCan; i++) {
			Wall ground = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
			
			// Random chance to place between 0 and the limit for trash per space on the current space.
			for (int j = 0; j < generator.nextInt(TRASH_PER_SPACE+1); j++) {
				Thing trash = new Thing(oakville,avenuePosition,streetPosition);
			}
			
			// Updating the build position.
			streetPosition++;
		}
		
		// Draws the trash can.
		Wall trashCanSide1 = new Wall(oakville,avenuePosition,streetPosition,Direction.EAST);
		Wall trashCanSide2 = new Wall(oakville,avenuePosition,streetPosition,Direction.WEST);
		Wall trashCanBottom = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
		
		// Draws some ground after the trash can.
		for (int i = 0; i < 2; i++) {
			streetPosition++;
			Wall ground = new Wall(oakville,avenuePosition,streetPosition,Direction.SOUTH);
		}
		
		// Cleaning the stairs.
		myRobot.sweepStairs();
	}
}