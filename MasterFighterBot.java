import becker.robots.City;
import becker.robots.Direction;

/**
 * The final decision for a fighter, combines the AI of the other robots into a robot similar to the tank fighter and edge runner, but with certain improvements.
 * These improvements consist of checking to avoid penalties and using more complex and strategic AI before making a decision.
 * @author Farris Matar
 * @version January 15, 2018
 */
public class MasterFighterBot extends BasicFighterBot{
	
	private int passiveRounds = 0;
	private int adjusterMoves = 0;
	private int turnCount = 0;
	
	/**
	 * Constructor for this robot.
	 * @param c City the robot is in
	 * @param a Initial y-coordinate of the robot
	 * @param s Initial x-coordinate of the robot
	 * @param d Initial direction the robot is facing
	 * @param id ID of this robot (for identification)
	 * @param health Initial health of this robot
	 */
	public MasterFighterBot(City c, int a, int s, Direction d, int id, int health) {
		super(c, a, s, d, id, health, 5, 4, 1);
	}
	
	/**
	 * Using a mix of the other robots' AI and some of its own, it decides what to do during its turn.
	 * @return Request to be given to the request adjuster
	 */
	protected TurnRequest decideAction() {
		// Updates turn count.
		this.turnCount++;
		// Rests for its first turn to gather data.
		if (this.firstTurn) {
			this.firstTurn = false;
			this.passiveRounds++;
			return this.rest();
		}
		// If it is still the first 100 turns, it will circle the edge and remain passive, only attacking to avoid penalties.
		else if (this.turnCount <= 150 && this.energy >= 5) {
			// Gets to the edge if it isn't at the edge
			if (!this.atEdge()) {
				this.passiveRounds++;
				return this.getToEdge();
			}
			// If it is about to be penalized, it will try to attack someone with the minimum requirement.
			else if (this.passiveRounds % 5 >= 2 && this.energy > 5 && this.robotsNearEdge() > 0) {
				this.passiveRounds = 0;
				return this.adjustRequest(this.attemptAttack(1));
			}
			// If it has enough energy, it will continue to circle the edge of the arena.
			else if (this.energy > 15) {
				this.passiveRounds++;
				return this.adjustRequest(this.circleEdge(this.speed));
			}
			// If nothing else is chosen, it will choose to rest.
			else {
				this.passiveRounds++;
				return this.rest();
			}
		}
		// Attempts an attack if it has enough energy or it hasn't fought in a while.
		else if (this.energy > 25 || (this.passiveRounds % 5 >= 2 && this.energy > 5)) {
			// If the robot is close enough to someone to fight them, it resets passive rounds.
			if (this.attemptAttack().getFightID() != -1)
				this.passiveRounds = 0;
			else
				this.passiveRounds++;
			
			// Determines how many rounds to risk fighting depending on how much energy it has left after moving and its attack history.
			// Less than 20 energy after moving.
			if (this.energy-(this.speed*5) < 20) {
				// If the robot has a poor attack history
				if (attackHistory < -1)
					return this.adjustRequest(this.attemptAttack(1));
				// If the robot has an average attack history
				else if (this.attackHistory <= 2)
					return this.adjustRequest(this.attemptAttack(2));
				// If the robot has a good attack history
				else
					return this.adjustRequest(this.attemptAttack(3));
			}
			// More than 20 energy after moving
			else {
				// If the robot has a poor attack history
				if (this.attackHistory < -1)
					return this.adjustRequest(this.attemptAttack(3));
				// If the robot has an average attack history
				else if (this.attackHistory <= 2)
					return this.adjustRequest(this.attemptAttack(4));
				// If the robot has a good attack history
				else 
					return this.adjustRequest(this.attemptAttack());
			}
		}
		// Resting to get more energy if the robot's energy is low.
		else if (this.energy <= 15) {
			this.passiveRounds++;
			return this.rest();
		}
		
		// Tries to find a more dense area of robots if it is not currently in one.
		else if (this.densityOfRobots() < 2) {
			this.passiveRounds++;
			return this.findCrowd();
		}
		// Approaches a target (but does not fight) if all other choices aren't chosen.
		else {
			this.passiveRounds++;
			return this.adjustRequest(this.approachTarget());
		}
	}
	
	/**
	 * This method will avoid penalties by checking to ensure the request will not break any rules.
	 * If the request does break rules, it will determine what to do from there.
	 * @param request Request to be checked for errors or penalties
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest adjustRequest(TurnRequest request) {
		TurnRequest adjustedRequest = new TurnRequest(request.getEndAvenue(),request.getEndStreet(),request.getFightID(),request.getNumRounds());
		
		// Avoids having robot fight itself.
		if (adjustedRequest.getFightID() == this.getID()) {
			// Changes the fight ID and number of rounds to fight nobody.
			adjustedRequest = new TurnRequest(adjustedRequest.getEndAvenue(),adjustedRequest.getEndStreet(),-1,0);
		}
		// Avoids fighting more rounds than it can.
		if (adjustedRequest.getNumRounds() > this.attack) {
			// Changes the number of rounds to its attack stat.
			adjustedRequest = new TurnRequest(adjustedRequest.getEndAvenue(),adjustedRequest.getEndStreet(),adjustedRequest.getFightID(),this.attack);
		}
		
		// Avoids having robot move outside the arena.
		// If crossing east wall
		if (adjustedRequest.getEndAvenue() >= BattleManager.WIDTH) {
			adjustedRequest = new TurnRequest(BattleManager.WIDTH-1,adjustedRequest.getEndStreet(),adjustedRequest.getFightID(),adjustedRequest.getNumRounds());
		}
		// If crossing west wall
		if (adjustedRequest.getEndAvenue() < 0) {
			adjustedRequest = new TurnRequest(0,adjustedRequest.getEndStreet(),adjustedRequest.getFightID(),adjustedRequest.getNumRounds());
		}
		// If crossing south wall
		if (adjustedRequest.getEndStreet() >= BattleManager.HEIGHT) {
			adjustedRequest = new TurnRequest(adjustedRequest.getEndAvenue(),BattleManager.HEIGHT-1,adjustedRequest.getFightID(),adjustedRequest.getNumRounds());
		}
		// If crossing north wall
		if (adjustedRequest.getEndAvenue() < 0) {
			adjustedRequest = new TurnRequest(adjustedRequest.getEndAvenue(),0,adjustedRequest.getFightID(),adjustedRequest.getNumRounds());
		}
		
		// Avoids having robot move more than it is allowed.
		if (Math.abs(this.getAvenue()-adjustedRequest.getEndAvenue())+Math.abs(this.getStreet()-adjustedRequest.getEndStreet()) > this.speed) {
			adjustedRequest = new TurnRequest(this.adjustEndAvenue(adjustedRequest.getEndAvenue()),this.adjustEndStreet(adjustedRequest.getEndStreet()),adjustedRequest.getFightID(),adjustedRequest.getNumRounds());
		}
		
		// Avoids having robot move more than it has enough energy to.
		if ((Math.abs(this.getAvenue()-adjustedRequest.getEndAvenue())+Math.abs(this.getStreet()-adjustedRequest.getEndStreet()))*5 > this.energy) {
			// Makes the robot rest if it doesn't have enough energy to move and doesn't allow it to fight.
			adjustedRequest = new TurnRequest(this.getAvenue(),this.getStreet(),-1,0);
		}
		
		// Avoids having robot fight someone when they won't have enough energy to after moving.
		if (adjustedRequest.getFightID() != -1 && (Math.abs(this.getAvenue()-adjustedRequest.getEndAvenue())+Math.abs(this.getStreet()-adjustedRequest.getEndStreet()))*5 == this.energy) {
			// Makes robot not fight anyone.
			adjustedRequest = new TurnRequest(adjustedRequest.getEndAvenue(),adjustedRequest.getEndStreet(),-1,0);
		}
		
		return adjustedRequest;
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
	 * Has the robot move to a more dense location.
	 * @return Request to be given to the battle manager
	 */
	private TurnRequest findCrowd() {
		int [] searchAreas = new int[4];
		int highestDensity = -1;
		int index = 0;
		
		// Finds the density of robots of 4 areas: 1 space north, south, east and west, only calculates if location is not out of bounds.
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
	 * Adjusts a turn request's desired location to avoid going over move limit.
	 * @param endStreet Request's desired location
	 * @return Adjusted location that follows rules
	 */
	private int adjustEndAvenue(int endAvenue) {
		int newEndAvenue = this.getAvenue();
		
		// Moves robot as close as possible to its desired avenue.
		while (newEndAvenue != endAvenue && this.adjusterMoves < this.speed) {
			// If it needs to move west.
			if (newEndAvenue > endAvenue) {
				this.adjusterMoves++;
				newEndAvenue--;
			}
			// If it needs to move east.
			else if (newEndAvenue > endAvenue) {
				this.adjusterMoves++;
				newEndAvenue++;
			}
			// If it has reached its location.
			else
				break;
		}
		
		return newEndAvenue;
	}
	
	/**
	 * Adjusts a turn request's desired location to avoid going over move limit.
	 * @param endStreet Request's desired location
	 * @return Adjusted location that follows rules
	 */
	private int adjustEndStreet(int endStreet) {
		int newEndStreet = this.getAvenue();

		// Moves robot as close as possible to its desired avenue.
		while (newEndStreet != endStreet && this.adjusterMoves < this.speed) {
			// If it needs to move west.
			if (newEndStreet > endStreet) {
				this.adjusterMoves++;
				newEndStreet--;
			}
			// If it needs to move east.
			else if (newEndStreet > endStreet) {
				this.adjusterMoves++;
				newEndStreet++;
			}
			// If it has reached its location.
			else
				break;
		}

		return newEndStreet;
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
		if (a <= 3 || a >= BattleManager.WIDTH-4 || s <= 3 || s >= BattleManager.HEIGHT-4)
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
			if (this.dataList[i].getCompatibility() != 0 && this.dataList[i].getDistance() <= this.speed && this.atEdge(this.dataList[i].getAvenue(),this.dataList[i].getStreet())) {
				robotsNearEdge++;
			}
		}
		
		return robotsNearEdge;
	}
}
