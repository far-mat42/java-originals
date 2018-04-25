import becker.robots.City;
import becker.robots.Direction;

import java.awt.Color;
import java.util.Random;

/**
 * A control robot that only moves around randomly. This robot is purely for testing purposes only.
 * @author Farris Matar
 * @version January 15, 2018
 */
public class DummyBot extends BasicFighterBot{
	
	private static Random statGenerator = new Random();
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of the robot
	 * @param s Initial x-coordinate of the robot
	 * @param d Initial direction the robot is facing
	 * @param id ID for this robot (for identification)
	 * @param health Initial health of the robot
	 */
	public DummyBot(City c, int a, int s, Direction d, int id, int health) {
		// Randomizes stats to have 4 speed, 2-4 defence, and 2 attack.
		super(c, a, s, d, id, health, 2, statGenerator.nextInt(3)+2, 4);
	}

	/**
	 * As a dummy, this robot will always randomly move up to 2 spaces up/down and up to 2 spaces left/right.
	 * @return Request to be given to the battle manager
	 */
	protected TurnRequest decideAction() {
		if (this.energy >= 15)
			return this.basicMove();
		else
			return this.rest();
	}
	
	/**
	 * A basic command to randomly move 2 spaces up/down and/or left/right.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest basicMove() {
		int targetAvenue = this.getAvenue();
		int targetStreet = this.getStreet();
		Random generator = new Random();
		
		targetAvenue += generator.nextInt(5)-2;
		targetStreet += generator.nextInt(5)-2;
		
		targetAvenue = this.adjustAvenue(targetAvenue);
		targetStreet = this.adjustStreet(targetStreet);
		
		return new TurnRequest(targetAvenue,targetStreet,-1,0);
	}
	
	/**
	 * Adjusts target avenue to avoid going out of bounds.
	 * @param a Target avenue to be adjusted
	 * @return Adjusted target avenue
	 */
	private int adjustAvenue(int a) {
		if (a < 0)
			return 0;
		else if (a > BattleManager.WIDTH-1)
			return BattleManager.WIDTH-1;
		else
			return a;
	}
	
	/**
	 * Adjusts target street to avoid going out of bounds.
	 * @param s Target street to be adjusted
	 * @return Adjusted target street
	 */
	private int adjustStreet(int s) {
		if (s < 0)
			return 0;
		else if (s > BattleManager.HEIGHT-1)
			return BattleManager.HEIGHT-1;
		else
			return s;
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.WHITE);
	}
}
