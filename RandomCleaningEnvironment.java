import java.util.Random;

import becker.robots.*;

/**
 * Testing out the new cleaning robot by cleaning a city.
 * @author Farris Matar
 * @version November 16, 2017
 */
public class RandomCleaningEnvironment {

	/**
	 * Redesigned cleaning robot test.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Creating the random generator.
		Random generator = new Random();
		
		// Creating variables for the city's size
		final int CITY_WIDTH = 7;
		final int CITY_HEIGHT = 9;
		
		// Creating the robot and the city.
		City oakville = new City(10,10);
		CleanerRobot myRobot = new CleanerRobot(oakville,4,1,Direction.WEST);
		
		// Creating the north and south walls using a for loop.
		for (int i = 1; i < CITY_WIDTH+1; i++) {
			Wall northCityBorder = new Wall(oakville,1,i,Direction.NORTH);
			Wall southCityBorder = new Wall(oakville,CITY_HEIGHT,i,Direction.SOUTH);
		}
		// Creating the east and west walls using a for loop.
		for (int i = 1; i < CITY_HEIGHT+1; i++) {
			Wall westCityBorder = new Wall(oakville,i,1,Direction.WEST);
			Wall eastCityBorder = new Wall(oakville,i,CITY_WIDTH,Direction.EAST);
		}
		
		// Using a for loop to create 5 things in random locations.
		for (int i = 0; i < 15; i++) {
			Thing trash = new Thing(oakville,generator.nextInt(CITY_HEIGHT)+1,generator.nextInt(CITY_WIDTH)+1);
		}
		
		// Cleaning the city.
		myRobot.cleanCity();
	}
}
