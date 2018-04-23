import becker.robots.*;

/**
 * A simulation of a hurdle relay race.
 * @author Farris Matar
 * @version November 13, 2017
 */
public class RaceEnvironment {

	/**
	 * A hurdle relay race in an Olympic Arena
	 */
	public static void main(String[] args) {
		// Creating the robots and the city.
		City oakville = new City();
		OlympicRunner runner1 = new OlympicRunner(oakville,3,0,Direction.EAST);
		OlympicRunner runner2 = new OlympicRunner(oakville,3,4,Direction.EAST);
		
		// Creating the arena with the hurdles.
		// Bottom
		for (int i = 0; i < 10; i++) {
			@SuppressWarnings("unused")
			Wall bottom = new Wall(oakville,3,i,Direction.SOUTH);
		}
		// Hurdles
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				@SuppressWarnings("unused")
				Wall hurdle = new Wall(oakville,j+2,i*2+1,Direction.EAST);
			}
		}
		// Baton.
		@SuppressWarnings("unused")
		Thing baton = new Thing(oakville,3,0);
		
		// Running the race.
		runner1.runRace();
		runner2.runRace();
		
		// Destroying the second robot.
		runner2.selfDestruct();
	}
}
