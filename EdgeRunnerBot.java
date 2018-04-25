import becker.robots.City;
import becker.robots.Direction;

import java.awt.Color;
import java.util.Random;

/**
 * A blueprint for a robot that is very passive and tries to stay near the edge of the arena rather than engaging in fights.
 * The robot is quick to flee, but has quite high defence to get energy and health should it be attacked. Its low attack will cause it to have to defend itself often in order to keep its health up. 
 * @author Farris Matar
 * @version January 16, 2018
 */
public class EdgeRunnerBot extends BasicFighterBot{
	
	private int passiveRounds = 0;
	private Random generator = new Random();
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of this robot
	 * @param s Initial x-coordinate of this robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of this robot (for identification)
	 * @param health Initial health of this robot 
	 */
	public EdgeRunnerBot(City c, int a, int s, Direction d, int id, int health) {
		super(c, a, s, d, id, health, 1,6,3);
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request.
	 * @return Request it will give to the battle manager
	 */
	protected TurnRequest decideAction() {
		// If the robot is not at the edge, it will try to get to the edge.
		if (!this.atEdge() && this.energy >= 10) {
			this.passiveRounds++;
			return this.getToEdge();
		}
		// If there are robots near the edge and this robot hasn't fought in a while, it will attempt an attack.
		else if (this.robotsNearEdge() > 0 && this.passiveRounds % 5 >= 3 && this.energy > 15) {
			this.passiveRounds = 0;
			return this.attemptAttack(1);
		}
		else if (this.energy >= 30) {
			this.passiveRounds++;
			return this.circleEdge(this.generator.nextInt(this.speed)+1);
		}
		else {
			this.passiveRounds++;
			return this.rest();
		}
	}
	
	/**
	 * Moves the robot as close as possible to the nearest edge of the arena.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest getToEdge() {
		int [] edgeDistances = new int[4];
		int shortestDistance = BattleManager.WIDTH*BattleManager.HEIGHT;
		int index = 0;
		
		// Checks the distance between the robot and each edge of the arena.
		edgeDistances[0] = 2-this.getAvenue(); // West edge
		edgeDistances[1] = (BattleManager.WIDTH-3)-this.getAvenue(); // East edge
		edgeDistances[2] = 2-this.getStreet(); // North edge
		edgeDistances[3] = (BattleManager.HEIGHT-3)-this.getStreet(); // South edge
		
		// Heads towards the first edge that is close enough (if there is one). Otherwise, it will head to the nearest edge.
		for (int i = 0; i < edgeDistances.length; i++) {
			// Looks if the edge is close enough and heads towards it if so.
			if (Math.abs(edgeDistances[i]) <= this.speed) {
				// If heading to an east/west edge
				if (i <= 1)
					return new TurnRequest(this.getAvenue()+edgeDistances[i],this.getStreet(),-1,0);
				// If heading to a north/south edge
				else
					return new TurnRequest(this.getAvenue(),this.getStreet()+edgeDistances[i],-1,0);
			}
			// If not in range, it updates the shortest distance.
			else {
				if (Math.abs(edgeDistances[i]) < shortestDistance) {
					shortestDistance = edgeDistances[i];
					index = i;
				}
			}
		}
		
		// If no edge is in range, it heads towards the nearest edge.
		// If heading towards west edge
		if (index == 0)
			return new TurnRequest(this.getAvenue()-this.speed,this.getStreet(),-1,0);
		// If heading towards east edge
		else if (index == 1)
			return new TurnRequest(this.getAvenue()+this.speed,this.getStreet(),-1,0);
		// If heading towards north edge
		else if (index == 2)
			return new TurnRequest(this.getAvenue(),this.getStreet()-this.speed,-1,0);
		// If heading towards south edge
		else
			return new TurnRequest(this.getAvenue(),this.getStreet()+this.speed,-1,0);	
	}
	
	/**
	 * Has the robot keep circling edge in a clockwise manner.
	 * @param moves How many moves to make
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest circleEdge(int moves) {
		int avenueMove = 0;
		int streetMove = 0;
		
		// Finds which edge the robot is at and determines how to move it from there (tries to have robot circle arena in a clockwise manner).
		// If robot is at west edge.
		if (this.getAvenue() <= 2) {
			// Keeps moving robot up until it reaches north edge.
			while (this.getStreet()+streetMove > 2 && Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				streetMove--;
			}
			// After it reaches north edge, it begins moving east.
			while (Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				avenueMove++;
			}
		}
		// If robot is at north edge.
		else if (this.getStreet() <= 2) {
			// Keeps moving robot right until it reaches east edge.
			while (this.getAvenue()+avenueMove < (BattleManager.WIDTH-3) && Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				avenueMove++;
			}
			// After it reaches east edge, it begins moving south.
			while (Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				streetMove++;
			}
		}
		// If robot is at east edge.
		else if (this.getAvenue() >= (BattleManager.WIDTH-3)) {
			// Keeps moving robot down until it reaches south edge.
			while (this.getStreet()+streetMove < (BattleManager.HEIGHT-3) && Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				streetMove++;
			}
			// After it reaches south edge, it begins moving west.
			while (Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				avenueMove--;
			}
		}
		// If robot is at south edge.
		else {
			// Keeps moving robot left until it reaches west edge.
			while (this.getAvenue()+avenueMove > 2 && Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				avenueMove--;
			}
			// After it reaches west edge, it begins moving north.
			while (Math.abs(streetMove)+Math.abs(avenueMove) < moves) {
				streetMove--;
			}
		}
		
		return new TurnRequest(this.getAvenue()+avenueMove,this.getStreet()+streetMove,-1,0);
	}
	
	/**
	 * Helper method to check if this robot is near the edge of the arena.
	 * @return Whether or not the robot is at the edge
	 */
	private boolean atEdge() {
		// Checks if the avenue or street is less than an imaginary boundary near the edge of the arena.
		if (this.getAvenue() <= 2 || this.getAvenue() >= BattleManager.WIDTH-3 || this.getStreet() <= 2 || this.getStreet() >= BattleManager.HEIGHT-3)
			return true;
		else
			return false;
	}
	
	/**
	 * Helper method to check if a robot at a certain location is near the edge of the arena (criteria is not as strict).
	 * @param a Avenue this robot is at
	 * @param s Street this robot is at
	 * @return Whether or not the robot is at the edge
	 */
	private boolean atEdge(int a, int s) {
		// Checks if the avenue or street is less than an imaginary boundary near the edge of the arena.
		if (a <= 5 || a >= BattleManager.WIDTH-6 || s <= 5 || s >= BattleManager.HEIGHT-6)
			return true;
		else
			return false;
	}
	
	/**
	 * Calculates how many robots are in range of this robot and 
	 * @return
	 */
	private int robotsNearEdge() {
		int robotsNearEdge = 0;
		
		// For every robot, it checks if they are compatible, close enough to get to, and near enough to the edge.
		for (int i = 0; i < this.dataList.length; i++) {
			if (this.dataList[i].getCompatibility() != 0 && this.dataList[i].getDistance() <= speed && this.atEdge(this.dataList[i].getAvenue(),this.dataList[i].getStreet())) {
				robotsNearEdge++;
			}
		}
		
		return robotsNearEdge;
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.getHealth() <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(Color.GREEN);
	}
}
