import becker.robots.*;
import java.util.Random;

/**
 * Redoing the cleaning robot class, this time with an upgraded robot.
 * @author Farris Matar
 * @version November 7, 2017
 */
public class CleaningEnvironment {
	
	/**
	 * Using an upgraded robot to pick up trash littered around the city.
	 */
	public static void main(String[] args) {
		// Creating the city and the robot.
		City oakville = new City();
		UpgradedRobot myRobot = new UpgradedRobot(oakville,1,5,Direction.WEST);
		
		// Creating the lists to hold all the walls.
		Wall [] northSouthWalls = new Wall [10];
		Wall [] westEastWalls = new Wall[8];
		Wall [][] allWalls = {northSouthWalls,westEastWalls};
		
		// Creating the north and south walls using a for loop.
		for (int i = 1; i < 6; i++) {
			Wall northCityBorder = new Wall(oakville,1,i,Direction.NORTH);
			allWalls[0][i-1] = northCityBorder; // Adding the wall to the list of all walls
			Wall southCityBorder = new Wall(oakville,4,i,Direction.SOUTH);
			allWalls[0][i-1] = southCityBorder; // Adding the wall to the list of all walls
		}
		// Creating the east and west walls using a for loop.
		for (int i = 1; i < 5; i++) {
			Wall westCityBorder = new Wall(oakville,i,1,Direction.WEST);
			allWalls[1][i-1] = westCityBorder; // Adding the wall to the list of all walls
			Wall eastCityBorder = new Wall(oakville,i,5,Direction.EAST);
			allWalls[1][i-1] = eastCityBorder; // Adding the wall to the list of all walls
		}
		
		// Initializing the random generator
		Random generator = new Random();
		
		// Scattering 5 pieces of trash in random places across the city.
		for (int i = 0; i < 5; i++) {
			@SuppressWarnings("unused")
			Thing trash = new Thing(oakville,generator.nextInt(4)+1,generator.nextInt(5)+1);
		}
		
		// Having the robot sweep the streets of all the things with a nested for loop.
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				// Checking if trash exists at the current intersection and picking it all up if so.
				if (myRobot.canPickThing()) {
					myRobot.pickAllThings();
				}
				// Moving the robot only if there is no wall in front of it.
				if (myRobot.frontIsClear())
					myRobot.move();
			}
			// Turning the robot to go to the next street.
			// If the robot is on an odd street
			if (myRobot.getStreet() % 2 != 0) {
				myRobot.turnLeft();
				myRobot.move();
				myRobot.turnLeft();
			}
			// If the robot is on an even street and not on the last street (to prevent crashing).
			else if (myRobot.getStreet() % 2 == 0 && myRobot.getStreet() != 4) {
				myRobot.turnToRight();
				myRobot.move();
				myRobot.turnToRight();
			}
		}
		
		// Bringing the robot back to the top-left corner to dump all its trash.
		myRobot.turnAround();
		myRobot.move(4);
		myRobot.turnToRight();
		myRobot.move(3);
						
		// Dropping off all the robot's trash.
		myRobot.putAllThings();
		// Ensuring all 5 pieces of trash are accounted for.
		if (myRobot.countThings() == 5)
			System.out.println("All 5 pieces of trash were cleaned up. City cleaning successful.");
		else
			System.out.println("City cleaning unsuccessful. Some trash remaining.");
		
		// Moving the robot out of the way.
		myRobot.turnToRight();
		myRobot.move(2);
		
		// Ensuring no things are remaining in the robot's inventory.
		if (myRobot.countThingsInBackpack() == 0) 
			System.out.println("No trash remains carried by the robot.");
		else
			System.out.println("The robot is still carrying some trash.");
	}
}
