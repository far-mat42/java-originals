import becker.robots.*;
import java.util.Random;

/**
 * Runs a robot that draws a bar graph made of things.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class GraphCanvasEnvironment {
	
	/**
	 * A robot made to build a bar graph of things.
	 */
	public static void main(String[] args) {
		// Initializing the random generator.
		Random generator = new Random();
		
		// Creating the city and the robot.
		City oakville = new City();
		oakville.showThingCounts(true);
		RobotGrapher myRobot = new RobotGrapher(oakville,1,1,Direction.EAST);
		
		// Creating 7 random stacks of things (up to 10 in one stack).
		for (int i = 0; i < generator.nextInt(5)+5; i++) {
			int stackSize = generator.nextInt(10)+1;
			for (int j = 0; j < stackSize; j++) {
				@SuppressWarnings("unused")
				Thing thingStack = new Thing(oakville,i+1,1);
			}
		}
		
		myRobot.drawBarGraph();
	}
}
