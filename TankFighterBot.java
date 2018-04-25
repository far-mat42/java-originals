import java.awt.Color;

import becker.robots.City;
import becker.robots.Direction;

/**
 * A blueprint for a tank-style robot - this robot has high attack and defence, with AI designed to act aggressively.
 * The robot starts many fights and always tries to stay close to opponents in order to have robots to fight and defend itself often, cutting down everyone's health and stockpiling energy.
 * @author Farris Matar
 * @version January 13, 2018
 */
public class TankFighterBot extends BasicFighterBot{
	
	private int passiveRounds = 0;
	
	/**
	 * Constructor for this class of robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of the robot
	 * @param s Initial x-coordinate of the robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of the robot (for identification)
	 * @param health Initial health of the robot
	 */
	public TankFighterBot(City c, int a, int s, Direction d, int id, int health) {
		super(c, a, s, d, id, health,4,4,2);
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request.
	 * @return Request it will give to the battle manager
	 */
	protected TurnRequest decideAction() {
		// Rests for its first turn.
		if (this.firstTurn) {
			this.firstTurn = false;
			this.passiveRounds++;
			return this.rest();
		}
		// Attempts an attack if it has enough energy or it hasn't fought in a while.
		else if (this.energy > 30 || ((this.passiveRounds % 5 >= 3 || this.passiveRounds > 5) && this.energy > 10)) {
			this.passiveRounds = 0;
			return this.attemptAttack();
		}
		// Resting to get more energy if the robot's energy is low.
		else if (this.energy <= 25) {
			this.passiveRounds++;
			return this.rest();
		}
		
		// Approaches a target (but does not fight) if all other choices aren't chosen.
		else {
			this.passiveRounds++;
			return this.approachTarget();
		}
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.YELLOW);
	}
}
