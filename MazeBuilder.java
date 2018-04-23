import becker.robots.*;
import java.util.Random;

/**
 * A testing class that builds a maze for the EscapeRobot to escape from.
 * @author Farris Matar
 * @version December 4, 2017
 */
public class MazeBuilder {

	/**
	 * Builds a maze for the robot to get out of.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Builds the maze.
		MazeCity place = new MazeCity(10, 10);
		
		// Initializing the random generator.
		Random generator = new Random();
		
		// Places the portal in a random spot in the maze.
		Thing escapePortal = new Thing(place,generator.nextInt(10),generator.nextInt(10));
		
		// Places the robot in a random spot in the maze.
		EscapeRobot myRobot = new EscapeRobot(place,generator.nextInt(10),generator.nextInt(10),Direction.NORTH);
		
		// Running the EscapeRobot.
		myRobot.escapeMaze();
	}
}
