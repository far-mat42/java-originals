import java.awt.Color;

import becker.robots.City;
import becker.robots.Direction;

/**
 * A blueprint for a quick, powerful robot - This robot will only start fights when it needs to (i.e. to avoid penalties or get energy), otherwise it tries to stay away from other robots.
 * Its high attack and speed allows it to get in and out of fights, but it has very low defense and will try to avoid conflict otherwise.
 * @author Farris Matar
 * @version January 13, 2018
 */
public class FencingFighterBot extends BasicFighterBot{
	
	private int passiveTurns = 0;
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of the robot
	 * @param s Initial x-coordinate of the robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of the robot (for identification)
	 * @param health Initial health of the robot
	 */
	public FencingFighterBot(City c, int a, int s, Direction d, int id, int health) {
		super(c, a, s, d, id, health,4,1,5);
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request.
	 * @return Request it will give to the battle manager
	 */
	protected TurnRequest decideAction() {
		if (this.firstTurn) {
			this.firstTurn = false;
			this.passiveTurns++;
			return this.rest();
		}
		
		// If low on energy, the robot will rest.
		else if (this.energy <= 25) {
			this.passiveTurns++;
			return this.rest();
		}
		
		// Attempts an attack if it has enough energy.
		else if (this.energy > 40 || (this.energy > 15 && this.passiveTurns > 6)) {
			this.passiveTurns = 0;
			return this.attemptAttack();
		}
		
		// Runs away if there are too many enemies nearby.
		else if (this.nearbyEnemies() >= 3) {
			this.passiveTurns++;
			return this.runAway();
		}
		
		// If no other options are chosen, the robot will rest.
		else {
			this.passiveTurns++;
			return this.rest();
		}
	}
	
	/**
	 * Requests for this robot to run away if it is getting crowded.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest runAway() {
		if (this.energy >= 15)
			return this.calculatedRun();
		else
			return this.emergencyEscape();
	}
	
	/**
	 * Carefully runs in a direction where there are fewer enemies.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest calculatedRun() {
		int lowestDensity = BattleManager.NUM_PLAYERS+1;
		int index = 0;
		int [] locationEnemyCounts = {this.nearbyEnemies(this.getAvenue()-(energy/5),this.getStreet()),this.nearbyEnemies(this.getAvenue()+(energy/5),this.getStreet()),this.nearbyEnemies(this.getAvenue(),this.getStreet()-(energy/5)),this.nearbyEnemies(this.getAvenue(),this.getStreet()+(energy/5))};
		
		for (int i = 0; i < locationEnemyCounts.length; i++) {
			if (locationEnemyCounts[i] < lowestDensity) {
				lowestDensity = locationEnemyCounts[i];
				index = i;
			}
		}
		
		if (index == 0)
			return new TurnRequest(this.getAvenue()-(energy/5),this.getStreet(),-1,0);
		else if (index == 0)
			return new TurnRequest(this.getAvenue()+(energy/5),this.getStreet(),-1,0);
		else if (index == 0)
			return new TurnRequest(this.getAvenue(),this.getStreet()-(energy/5),-1,0);
		else
			return new TurnRequest(this.getAvenue(),this.getStreet()+(energy/5),-1,0);
	}
	
	/**
	 * Rushes to the quadrant with the least amount of enemies, this will intentionally make the robot break the rules.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest emergencyEscape() {
		int [] quadrants = new int[4];
		
		int emptiestQuadrant = 1;
		int emptyCount = BattleManager.NUM_PLAYERS+1;
		
		for (int i = 0; i < this.dataList.length; i++) {
			if (this.dataList[i].getQuadrant() == 1 && this.dataList[i].getCompatibility() != 0)
				quadrants[0]++;
			else if (this.dataList[i].getQuadrant() == 2 && this.dataList[i].getCompatibility() != 0)
				quadrants[1]++;
			else if (this.dataList[i].getQuadrant() == 3 && this.dataList[i].getCompatibility() != 0)
				quadrants[2]++;
			else
				quadrants[3]++;
		}
		
		for (int i = 0; i < quadrants.length; i++) {
			if (quadrants[i] < emptyCount) {
				emptyCount = quadrants[i];
				emptiestQuadrant = i;
			}
		}
		
		return this.runToQuadrant(emptiestQuadrant+1);
	}
	
	/**
	 * Command to make the robot run to one of the quadrants of the arena based on where it currently is.
	 * @param quadrant Which quadrant to run to
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest runToQuadrant(int quadrant) {
		int avenueChange = 0;
		int streetChange = 0;
		// Uses a simple algorithm that adds its max speed to its current avenue/street to move it to the desired quadrant.
		if (quadrant == 1) {
			if (this.quadrant == 2)
				avenueChange = -speed;
			else if (this.quadrant == 3)
				streetChange = speed;
			else {
				avenueChange = (int) -(speed/2+0.5);
				streetChange = -(speed/2);
			}
		}
		else if (quadrant == 2) {
			if (this.quadrant == 1)
				avenueChange = speed;
			else if (this.quadrant == 3) {
				avenueChange = (int) (speed/2+0.5);
				streetChange = -(speed/2);
			}
			else
				streetChange = -speed;
		}
		else if (quadrant == 3) {
			if (this.quadrant == 1)
				streetChange = speed;
			else if (this.quadrant == 2) {
				streetChange = (int) (speed/2+0.5);
				avenueChange = -(speed/2);
			}
			else
				avenueChange = -speed;
		}
		else {
			if (this.quadrant == 1) {
				avenueChange = (int) (speed/2+0.5);
				streetChange = speed/2;
			}
			else if (this.quadrant == 2)
				streetChange = speed;
			else
				avenueChange = speed;
		}
		
		// Creating the request.
		return new TurnRequest(this.getAvenue()+avenueChange,this.getStreet()+streetChange,-1,0);
	}
	
	/**
	 * Calculates how many enemies are less than 3 moves away from this robot.
	 * @return Number of enemies close by
	 */
	private int nearbyEnemies() {
		int nearbyEnemies = 0;
		for (int i = 0; i < this.dataList.length; i++) {
			// Checks if the distance is close enough and the record's robot is not itself/dead.
			if (this.dataList[i].getDistance() <= 3 && this.dataList[i].getCompatibility() != 0)
				nearbyEnemies++;
		}
		
		return nearbyEnemies;
	}
	
	/**
	 * Calculates how many enemies are less than 3 moves away from a given location.
	 * @param a Y-coordinate of location being searched
	 * @param s X-coordinate of location being searched
	 * @return Number of enemies near the area
	 */
	private int nearbyEnemies(int a, int s) {
		int nearbyEnemies = 0;
		for (int i = 0; i < this.dataList.length; i++) {
			// Checks if the distance is close enough and the record's robot is not itself/dead.
			if (this.dataList[i].getDistance(a,s) <= 3 && this.dataList[i].getCompatibility() != 0)
				nearbyEnemies++;
		}
		
		return nearbyEnemies;
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.BLUE);
	}
}
