import becker.robots.City;
import becker.robots.Direction;

import java.awt.Color;
import java.util.Random;

/**
 * A robot that doesn't do anything at all, this robot is purely for testing purposes.
 * @author Farris Matar
 * @version January 19, 2018
 */
public class DefectiveBot extends BasicFighterBot{
	
	private static Random statGenerator = new Random();
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of the robot
	 * @param s Initial x-coordinate of the robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of this robot (for identification)
	 * @param health Initial health of this robot
	 */
	public DefectiveBot(City c, int a, int s, Direction d, int id, int health) {
		// Creates a robot with 1-5 defence.
		super(c, a, s, d, id, health, 1,statGenerator.nextInt(5)+1,3);
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request.
	 * @return Request it will give to the battle manager
	 */
	protected TurnRequest decideAction() {
		return this.rest();
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.GRAY);
	}
}
