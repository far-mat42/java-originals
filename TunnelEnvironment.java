import becker.robots.*;
import java.util.Random;

/**
 * A cleaning robot that clears a tunnel of heavy items.
 * @author Farris Matar
 * @version November 13, 2017
 */
public class TunnelEnvironment {
	/**
	 * Clears a 1-wide tunnel of things.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// Initializing the random generator.
		Random generator = new Random();
		
		// Creating the robot and the city.
		City oakville = new City();
		TunnelClearer myRobot = new TunnelClearer(oakville,2,0,Direction.EAST);
		
		// Creating the drop-off box the robot starts in.
		Wall dropOffBoxTop = new Wall(oakville,2,0,Direction.NORTH);
		Wall dropOffBoxBottom = new Wall(oakville,2,0,Direction.SOUTH);
		Wall dropOffBoxSide = new Wall(oakville,2,0,Direction.WEST);
		
		// Creating the tunnel (a random length long) with items inside it.
		int tunnelLength = generator.nextInt(9)+1;
		for (int i = 0; i < tunnelLength; i++) {
			Wall tunnelTop = new Wall(oakville,2,2+i,Direction.NORTH);
			Wall tunnelBottom = new Wall(oakville,2,2+i,Direction.SOUTH);
			Thing heavyItem = new Thing(oakville,2,2+i);
			Thing heavyItem2 = new Thing(oakville,2,2+i);
		}
		Wall tunnelEnd = new Wall(oakville,2,1+tunnelLength,Direction.EAST);
		
		// Running the tunnel clearer.
		myRobot.clearTunnel();
	}

}
