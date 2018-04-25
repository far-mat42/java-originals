import becker.robots.City;
import becker.robots.Direction;
import java.awt.Color;

/**
 * An abstract robot that has all the basic AI that all subclasses will require, such as trying to fight others, resting, keeping track of its own stats and other opponents' stats, etc.
 * @author Farris Matar
 * @version December 1, 2017
 */
public abstract class BasicFighterBot extends FighterRobot{
	
	protected int attack;
	protected int defence;
	protected int speed;
	
	private Color DARK_PURPLE = new Color(89, 2, 147);
	private int health;
	protected int energy;
	protected int quadrant;
	protected int attackHistory = 0;
	protected int [] myInfo = {this.getAvenue(),this.getStreet(),speed,this.getID(),attack};
	protected IntelligentOppData [] dataList = new IntelligentOppData[BattleManager.NUM_PLAYERS];
	private boolean gatheredData = false;
	private boolean fightInitiated = false;
	protected boolean firstTurn = true;
	
	/**
	 * Creates a fighter robot
	 * @param c The city the robot is in.
	 * @param a The initial y-coordinate of the robot.
	 * @param s The initial x-coordinate of the robot.
	 * @param d The initial direction the robot is facing.
	 * @param id The ID of the robot.
	 * @param health The amount of health the robot has when first built.
	 */
	public BasicFighterBot(City c, int a, int s, Direction d, int id, int health, int attack, int defence, int speed) {
		super(c, a, s, d, id, attack, defence, speed);
		this.changeStats(attack,defence,speed);
		this.health = health;
		this.energy = 100;
		this.setLabel();
	}
	
	/**
	 * Command to apply the results of the battle to the robot (i.e. health lost/gained).
	 */
	public void battleResult(int healthLost, int oppID, int oppHealthLost, int numRoundsFought) {
		this.health -= healthLost;
		// Only updates the opponent's record if this robot started the fight, as defending results are not relevant for deciding who to attack.
		if (this.fightInitiated) {
			this.updateBattleRecord(oppID,(oppHealthLost-healthLost));
			this.fightInitiated = false;
			
			// Updates attack history based on rounds fought.
			// If the fight was lost
			if (healthLost > oppHealthLost)
				this.attackHistory--;
			// If the fight was won
			else if (healthLost < oppHealthLost)
				this.attackHistory += 2;
		}
	}
	
	/**
	 * Query to get the health of the robot.
	 * @return The health of the robot
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Command to change the stats of this robot, only to be used by its subclasses.
	 * @param a New attack of this robot
	 * @param d New defence of this robot
	 * @param s New speed of this robot
	 */
	protected void changeStats(int a, int d, int s) {
		this.attack = a;
		this.defence = d;
		this.speed = s;
	}
	
	/**
	 * Accessor method for this robot's location.
	 * @return Array of robot's coordinates
	 */
	public int [] getLocation() {
		int [] location = new int[2];
		location[0] = this.getAvenue();
		location[1] = this.getStreet();
		return location;
	}
	
	/**
	 * Overriding the move method to only move if there is no wall in front of it.
	 */
	public void move() {
		if (this.frontIsClear())
			super.move();
	}
	
	/**
	 * Command to make the robot go to a certain intersection.
	 * @param a The target avenue of the intersection
	 * @param s The target street of the intersection
	 */
	public void goToLocation(int a, int s) {
		this.getToTargetAvenue(a);
		this.getToTargetStreet(s);
	}
	
	/**
	 * Helper method to get the robot to the avenue it is trying to move to during its turn.
	 * @param a The target avenue of the intersection
	 */
	private void getToTargetAvenue(int a) {
		// Moves the robot up or down the street to get to its target avenue, using while loops to ensure not to go above maximum moves per turn.
		if (this.getAvenue() < a) {
			this.orientSelf(Direction.EAST);
			this.move(a-this.getAvenue());
		}
		else if (this.getAvenue() > a) {
			this.orientSelf(Direction.WEST);
			this.move(this.getAvenue()-a);
		}
	}
	
	/**
	 * Helper method to get the robot to the street it is trying to move to during its turn.
	 * @param s The target street of the intersection
	 */
	private void getToTargetStreet(int s) {
		// Moves the robot up or down the avenue to get to its target street.
		if (this.getStreet() < s) {
			this.orientSelf(Direction.SOUTH);
			this.move(s-this.getStreet());
		}
		else if (this.getStreet() > s) {
			this.orientSelf(Direction.NORTH);
			this.move(this.getStreet()-s);
		}
	}
	
	/**
	 * Query for the robot to make a request for a turn.
	 * @param energy The energy level of the robot
	 * @param data The list of opponents at the target location
	 * @return Robot's request for what it wants to do in its turn
	 */
	public TurnRequest takeTurn(int energy, OppData[] data) {
		// Updating energy received from battle manager.
		this.energy = energy;
		
		// First time constructing datasheet on opponents.
		if (this.gatheredData == false) {
			// Creating a record for each opponent.
			for (int i = 0; i < data.length; i++) {
				this.dataList[i] = new IntelligentOppData(data[i].getID(),data[i].getAvenue(),data[i].getStreet(),data[i].getHealth(),this.myInfo);
			}
			this.gatheredData = true; // Indicating that the datasheet has been constructed
		}
		
		// Updates datasheet if it has already been constructed.
		else
			this.updateOppData(data);
		
		// Updating which quadrant my robot is in.
		this.updateQuadrant();
		
		// Deciding what to do.
		return this.decideAction();
	}
	
	/**
	 * Helper method that updates the datasheet on opponents and sorts it.
	 * @param data Datasheet of opponent data
	 */
	private void updateOppData(OppData[] data) {
		// Updates the records of opponent data to match their current states.
		for (int i = 0; i < data.length; i++) {
			int sendingIndex = -1;
			int receivingIndex = -1;
			// Ensuring each robot is updated correctly by getting a match in ID.
			for (int j = 0; j < this.dataList.length; j++) {
				if (this.dataList[j].getID() == data[i].getID()) {
					sendingIndex = i;
					receivingIndex = j;
					break;
				}
			}
			
			// Updating the robot's records.
			// Location.
			int [] theirCoordinates = {data[sendingIndex].getAvenue(),data[sendingIndex].getStreet()};
			
			this.dataList[receivingIndex].updateLocation(theirCoordinates,this.getLocation());
			
			// Health
			this.dataList[receivingIndex].updateHealth(data[sendingIndex].getHealth());
		}
		
		// Sorting the datasheet by compatibility after updating it.
		this.sortData(this.dataList);
	}
	
	/**
	 * Updates the battle log in the record of an opponent.
	 * @param id ID of robot's record being updated
	 * @param battleResult Difference between wins and losses against this robot
	 */
	private void updateBattleRecord(int id, int battleResult) {
		int targetRobot = 0;
		for (int i = 0; i < this.dataList.length; i++) {
			if (this.dataList[i].getID() == id) {
				targetRobot = i;
				break;
			}
		}
		
		this.dataList[targetRobot].updateBattleLog(battleResult);
	}
	
	/**
	 * Updates which quadrant of the arena this robot is in based on their location.
	 */
	private void updateQuadrant() {
		int [] location = {this.getAvenue(),this.getStreet()};
		
		if (location[0] <= BattleManager.HEIGHT/2 && location[1] <= BattleManager.WIDTH/2)
			this.quadrant = 1;
		else if (location[0] <= BattleManager.HEIGHT/2 && location[1] > BattleManager.WIDTH/2)
			this.quadrant = 2;
		else if (location[0] > BattleManager.HEIGHT/2 && location[1] <= BattleManager.WIDTH/2)
			this.quadrant = 3;
		else
			this.quadrant = 4;
	}
	
	/**
	 * Sets the ID/Health label for the robot on the battlefield.
	 */
	public void setLabel() {
		super.setLabel("ID"+this.getID()+": "+ this.getHealth());
		this.checkDeath();
	}
	
	/**
	 * Helper method to orient the robot to face the direction it wants to go.
	 * @param d The direction the robot is trying to face
	 */
	private void orientSelf(Direction d) {
		while (this.getDirection() != d) {
			this.turnLeft();
		}
	}
	
	/**
	 * Insertion sorts the opponent datasheet based on their compatibility (highest to lowest)
	 * @param data Datasheet being sorted
	 */
	private void sortData(IntelligentOppData [] data) {
		for (int i = 1; i < data.length; i++) {
			int index = i;
			
			while (index > 0 && data[index].getCompatibility() > data[index-1].getCompatibility()) {
				this.swap(index,index-1,data);
				index--;
			}
		}
	}
	
	/**
	 * Command to make the robot decide what to do during its return and construct a request, all subclasses will implement this differently depending on their AI.
	 * @return Request it will give to the battle manager
	 */
	protected abstract TurnRequest decideAction();
	
	/**
	 * Command to make this robot rest during its turn.
	 * @return Request battle manager to not move and fight no one
	 */
	protected TurnRequest rest() {
		return new TurnRequest(this.getAvenue(), this.getStreet(), -1, 0);
	}
	
	/**
	 * Command to attempt an attack on a compatible target.
	 * @return Request to either attack or approach an opponent
	 */
	protected TurnRequest attemptAttack() {
		TurnRequest request;
		boolean targetAcquired = false;
		int target = -1;
		
		// Finds the most compatible robot that is in range.
		for (int i = 0; i < this.dataList.length; i++) {
			if (this.dataList[i].getDistance() <= this.speed && this.dataList[i].getDistance() != -1) {
				targetAcquired = true;
				target = i;
				break;
			}
		}
		
		if (targetAcquired)
			request = this.attackTarget(target,this.attack);
		else
			request = this.approachTarget();
		
		return request;
	}
	
	/**
	 * Command to attempt an attack on a target, overloaded to take in a custom number of rounds.
	 * @param rounds
	 * @return
	 */
	protected TurnRequest attemptAttack(int rounds) {
		TurnRequest request;
		boolean targetAcquired = false;
		int target = -1;
		
		// Finds the most compatible robot that is in range.
		for (int i = 0; i < this.dataList.length; i++) {
			if (this.dataList[i].getDistance() <= this.speed && this.dataList[i].getDistance() != -1) {
				targetAcquired = true;
				target = i;
				break;
			}
		}
		
		// If it has found a nearby target, it will attack them. Otherwise, it will try to get close to one.
		if (targetAcquired)
			request = this.attackTarget(target,rounds);
		else
			request = this.approachTarget();
		
		return request;
	}
	
	/**
	 * Command to make this robot request to attack an opponent.
	 * @param target Index of opponent's record in data list
	 * @return Request to be given to battle manager
	 */
	private TurnRequest attackTarget(int target, int rounds) {
		// Indicating that this robot is initiating a fight.
		this.fightInitiated = true;
		// Creating the request.
		return new TurnRequest(this.dataList[target].getAvenue(),this.dataList[target].getStreet(),this.dataList[target].getID(),rounds);
	}
	
	/**
	 * Request for robot to move near a compatible opponent.
	 * @return Request to approach an opponent
	 */
	protected TurnRequest approachTarget() {
		// Keeping track of how many spaces this robot has moved.
		int avenueMove = 0;
		int streetMove = 0;
		
		// Finding which opponent is the nearest in the list.
		int target = this.findNearestOpponent();
		
		// As long as this robot has spaces left to move, it will try to get to its nearest opponent.
		// Getting to target avenue
		while ((Math.abs(streetMove)+Math.abs(avenueMove) < this.speed) && this.getAvenue() + avenueMove != this.dataList[target].getAvenue() && (Math.abs(streetMove)+Math.abs(avenueMove) < speed)) {
			if (this.getAvenue()+avenueMove < this.dataList[target].getAvenue()) {
				avenueMove++;
			}
			else if (this.getAvenue()+avenueMove > this.dataList[target].getAvenue()) {
				avenueMove--;
			}
			else
				break;
		}
		// Getting to target street.
		while ((Math.abs(streetMove)+Math.abs(avenueMove) < this.speed) && this.getStreet() + streetMove != this.dataList[target].getStreet()) {
			if (this.getStreet()+streetMove < this.dataList[target].getStreet()) {
				streetMove++;
			}
			else if (this.getStreet()+streetMove > this.dataList[target].getStreet()) {
				streetMove--;
			}
			else
				break;
		}
		
		// Creating the request for the battle manager.
		return new TurnRequest(this.getAvenue()+avenueMove,this.getStreet()+streetMove,-1,0);
	}
	
	/**
	 * Helper method to find the nearest opponent.
	 * @return Index of the nearest opponent's record in datasheet
	 */
	private int findNearestOpponent() {
		int shortestDistance = BattleManager.WIDTH* BattleManager.HEIGHT;
		int target = -1;
		
		// Goes through every opponent record and finds who has the shortest distance.
		for (int i = 0; i < this.dataList.length; i++) {
			if (dataList[i].getDistance() < shortestDistance && dataList[i].getDistance() != -1) {
				shortestDistance = dataList[i].getDistance();
				target = i;
			}
		}
		
		return target;
	}
	
	/**
	 * Swaps to records of opponent data in the datasheet
	 * @param index1 Index of first record
	 * @param index2 Index of second record
	 * @param data Datasheet where records exist
	 */
	private void swap(int index1, int index2, IntelligentOppData[] data) {
		IntelligentOppData temp = data[index1];
		data[index1] = data[index2];
		data[index2] = temp;
	}
	
	/**
	 * Command to set the color of the robot depending on whether or not it is dead.
	 */
	protected void checkDeath() {
		if (this.health <= 0)
			this.setColor(Color.BLACK);
		else
			this.setColor(DARK_PURPLE);
	}
}
