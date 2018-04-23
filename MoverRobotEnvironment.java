import becker.robots.*;

/**
 * Creates a test environment to test the mover robot.
 * @author Farris Matar
 * @version December 5, 2017
 */
public class MoverRobotEnvironment {

	/**
	 * Builds a city to test the mover robot.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Creating the city and the robot.
		City oakville = new City(10,10);
		MoverRobot myRobot = new MoverRobot(oakville,8,3,Direction.EAST,3);
		
		// Creating two stacks to clean.
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				Thing trash = new Thing(oakville,8,7+2*i);
			}
		}
		
		// Creating the drop-off depots.
		for (int i = 0; i < 2; i++) {
			Wall depotTop = new Wall(oakville,6-i,7+2*i,Direction.NORTH);
			Wall depotSide = new Wall(oakville,6-i,7+2*i,Direction.EAST);
			Wall depotBottom = new Wall(oakville,6-i,7+2*i,Direction.WEST);
		}
		
		// Running the robot.
		myRobot.sweepAvenue();
		myRobot.sweepAvenue();
	}
}
