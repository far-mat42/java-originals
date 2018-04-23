import becker.robots.*;

/**
 * A class that instructs a robot to draw a certain number of spirals of things.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class SpiralCanvasEnvironment {
	
	/**
	 * Makes a robot draw a spiral of things.
	 */
	public static void main(String[] args) {
		// Creating the city and the robot.
		City oakville = new City(10,10);
		SpiralBot myRobot = new SpiralBot(oakville,3,3,Direction.EAST,50);
		
		myRobot.drawSpiral(3);
	}
}
