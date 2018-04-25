import java.awt.Color;

import becker.robots.City;
import becker.robots.Direction;

/**
 * A blueprint for a robot that is semi-aggressive and tries to keep itself as close as possible to large crowds of robots to get into fights often.
 * It has insanely high defense and moderate attack, allowing it to have high success during fights, but its slow speed can impede its success.
 * @author Farris Matar
 * @version January 18, 2018
 */
public class TitanFighterBot extends BasicFighterBot{
	
	private int passiveRounds = 0;
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of this robot
	 * @param s Initial x-coordinate of this robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of this robot (for identification)
	 * @param health Initial health of this robot 
	 */
	public TitanFighterBot(City c, int a, int s, Direction d, int id, int health) {
		super(c, a, s, d, id, health, 3 ,6 ,1);
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request.
	 * @return Request it will give to the battle manager
	 */
	protected TurnRequest decideAction() {
		if (this.densityOfRobots() < 3 && this.energy >= 10) {
			this.passiveRounds++;
			return this.findCrowd();
		}
		else if (this.densityOfRobots() >= 3 && this.energy >= 25 || (this.passiveRounds % 5 >= 3 && this.energy > 5)) {
			this.passiveRounds = 0;
			if (this.energy <= 20)
				return this.attemptAttack(1);
			else if (this.energy <= 30)
				return this.attemptAttack(2);
			else
				return this.attemptAttack(3);
		}
		else if (this.densityOfRobots() >= 3 && this.energy >= 15) {
			this.passiveRounds++;
			return this.approachTarget();
		}
		else {
			this.passiveRounds++;
			return this.rest();
		}
	}
	
	/**
	 * Has the robot move to a more dense location.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest findCrowd() {
		int [] searchAreas = new int[4];
		int highestDensity = -1;
		int index = 0;
		
		// Finds the density of robots of 4 areas: 2 spaces north, south, east and west, only calculates if location is not out of bounds.
		// West direction
		if (this.getAvenue()-this.speed >= 0)
			searchAreas[0] = this.densityOfRobots(this.getAvenue()-2,this.getStreet());
		// South direction
		if (this.getStreet()+this.speed <= (BattleManager.HEIGHT-1))
			searchAreas[1] = this.densityOfRobots(this.getAvenue(),this.getStreet()+2);
		// East direction
		if (this.getAvenue()+this.speed <= (BattleManager.WIDTH-1))
			searchAreas[2] = this.densityOfRobots(this.getAvenue()+2,this.getStreet());
		// North direction
		if (this.getStreet()-this.speed >= 0)
			searchAreas[3] = this.densityOfRobots(this.getAvenue(),this.getStreet()-2);
		
		// Determines which location has the highest density.
		for (int i = 0; i < searchAreas.length; i++) {
			if (searchAreas[i] > highestDensity) {
				highestDensity = searchAreas[i];
				index = i;
			}
		}
		
		// Heads to the most dense location.
		// West location
		if (index == 0 && searchAreas[0] != 0)
			return new TurnRequest(this.getAvenue()-this.speed,this.getStreet(),-1,0);
		// South location
		else if (index == 1 && searchAreas[1] != 0)
			return new TurnRequest(this.getAvenue(),this.getStreet()+this.speed,-1,0);
		// East location
		else if (index == 2 && searchAreas[2] != 0)
			return new TurnRequest(this.getAvenue()+this.speed,this.getStreet(),-1,0);
		// North location
		else if (index == 3 && searchAreas[3] != 0)
			return new TurnRequest(this.getAvenue(),this.getStreet()-this.speed,-1,0);
		// If no results are found, it will approach the nearest target.
		else
			return this.approachTarget();
	}
	
	/**
	 * Helper method to see how many robots are nearby (within a 3 space radius).
	 * @return Number of robots around my robot
	 */
	private int densityOfRobots() {
		int robotsNearby = 0;
		
		// Checks distance of all robots.
		for (int i = 0; i < this.dataList.length; i++) {
			// If the robot is within a 3 space distance of this robot, it is considered nearby.
			if (this.dataList[i].getCompatibility() > 0 && this.dataList[i].getDistance() <= 3) {
				robotsNearby++;
			}
		}
		
		return robotsNearby;
	}
	
	/**
	 * Helper method to see how many robots are near a given location (within a 3 space radius).
	 * @param a Avenue of location being searched
	 * @param s Street of location being searched
	 * @return Number of robots near this location
	 */
	private int densityOfRobots(int a, int s) {
		int robotsNearby = 0;
		
		// Checks distance of all robots.
		for (int i = 0; i < this.dataList.length; i++) {
			// If the robot is within a 3 space distance of the given location, it is considered nearby.
			if (this.dataList[i].getCompatibility() > 0 && this.dataList[i].getDistance(a,s) <= 3) {
				robotsNearby++;
			}
		}
		
		return robotsNearby;
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.RED);
	}
}
