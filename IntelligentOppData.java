/**
 * A more intelligent version of Opp Data datasheet that can perform calculations on an opponent's compatibility as a choice to fight.
 * @author Farris Matar
 * @version January 6, 2018
 */
public class IntelligentOppData extends OppData{
	
	// Setting global variables as reference for the record.
	private double compatibility; // How optimal of an opponent this robot is.
	private int [] pastFights = new int[5]; // List of sum of past fights with this robot (-1 for round loss, 1 for round win, 0 for round tie, if total is 0 it is null or all ties)
	// My robot's information that is required for various calculations.
	private int myRobotAvenue;
	private int myRobotStreet;
	private int myRobotSpeed;
	private int myRobotID;
	private int myRobotAttack;
	private int [] lastLocation = new int [2]; // This opponent's location during their last turn.
	private int distance; // Distance between my robot and this opponent.
	private int speed; // This robot's calculated speed.
	private int quadrant; // The quadrant this robot is in (in the arena).
	private int oldestFight = 0;
	
	/**
	 * Constructor for a more intelligent datasheet for an opponent
	 * @param id Opponent's ID
	 * @param avenue Avenue of opponent
	 * @param street Street of opponent
	 * @param health Health of opponent
	 * @param myRobotInfo Array of my robot's information (avenue,street,speed,ID,attack)
	 */
	public IntelligentOppData(int id, int avenue, int street, int health, int [] myRobotInfo) {
		super(id,avenue,street,health);
		this.setAvenue(avenue);
		this.setStreet(street);
		this.setHealth(health);
		this.myRobotAvenue = myRobotInfo[0];
		this.myRobotStreet = myRobotInfo[1];
		this.myRobotSpeed = myRobotInfo[2];
		this.myRobotID = myRobotInfo[3];
		this.myRobotAttack = myRobotInfo[4];
		this.distance = Math.abs(this.myRobotAvenue-this.getAvenue())+Math.abs(this.myRobotStreet-this.getStreet());
		this.speed = -1; // Initializes speed to be -1, will be recognized by this record as 'unknown'.
	}
	
	/**
	 * Helper method to update this oppponent's location
	 * @param newCoordinates New location of this opponent
	 * @param myLocation New location of my robot
	 */
	public void updateLocation(int [] newCoordinates, int [] myLocation) {
		// Updating this robot's last location.
		this.lastLocation[0] = this.getAvenue();
		this.lastLocation[1] = this.getStreet();
		// Updating this robot's coordinates.
		this.setAvenue(newCoordinates[0]);
		this.setStreet(newCoordinates[1]);
		// Updating this robot's speed based on their last and current locations.
		this.calculateSpeed();
		
		// Updating my robot's location in the datasheet.
		this.myRobotAvenue = myLocation[0];
		this.myRobotStreet = myLocation[1];
		this.distance = Math.abs(this.myRobotAvenue-this.getAvenue())+Math.abs(this.myRobotStreet-this.getStreet());
		
		// Updating the quadrant this robot is in.
		this.updateQuadrant();
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
	 * Helper method to get the quadrant this robot is in.
	 * @return Quadrant this robot is in
	 */
	public int getQuadrant() {
		return this.quadrant;
	}
	
	/**
	 * Helper method to update this robot's health
	 * @param newHealth Current health of this robot
	 */
	public void updateHealth(int newHealth) {
		this.setHealth(newHealth);
	}
	
	private void calculateSpeed() {
		int newSpeed = Math.abs(this.getAvenue()-this.lastLocation[0])+Math.abs(this.getStreet()-this.lastLocation[1]);
		this.speed = newSpeed;
	}
	
	/**
	 * Accessor method for the distance between this opponent and my robot
	 * @return Distance between our robots
	 */
	public int getDistance() {
		return this.distance;
	}
	
	/**
	 * Accessor method for the distance between this opponent and a given location.
	 * @param a Avenue of location being searched
	 * @param s Street of location being searched
	 * @return Distance between this robot and the location
	 */
	public int getDistance(int a, int s) {
		return (Math.abs(this.getAvenue()-a)+Math.abs(this.getStreet()-s));
	}
	
	/**
	 * Accessor method for the speed of this opponent
	 * @return Speed of this robot
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	/**
	 * Accessor method to allow the battle log to be updated with a new battle result.
	 * @param winLossDifference Difference between rounds won and rounds lost of the battle
	 */
	public void updateBattleLog(int winLossDifference) {
		// Overwriting this opponent's oldest fight with the new fight data, and updating the oldest fight.
		this.pastFights[this.oldestFight] = winLossDifference;
		this.oldestFight++;
		
		// Resetting oldest fight to 0 if it gets bigger than array length.
		if (this.oldestFight > this.pastFights.length-1)
			this.oldestFight = 0;
	}
	
	/**
	 * Accessor method for the compatibility of this opponent, automatically calculates compatibility before returning.
	 * @return Compatibility of the robot
	 */
	public double getCompatibility() {
		this.compatibility = calculateCompatibility();
		return this.compatibility;
	}
	
	/**
	 * Calculates the compatibility of this robot being a good choice as an opponent. Compatibility can go up to 1 (most optimal) and down to 0 (least optimal).
	 */
	private double calculateCompatibility() {
		double newCompatibility;
		
		// Setting data to values the robot will recognize to be unusable (because it is its own or already dead).
		if (this.getID() == this.myRobotID || this.getHealth() <= 0) {
			newCompatibility = 0;
			this.distance = -1;
		}
		else {
			// Resets compatibility to 0.5 and adds or subtracts compatibility based on factors inputed.
			newCompatibility = 0.5;
			
			// Takes their distance and speed into consideration.
			if (this.distance > this.myRobotSpeed)
				newCompatibility -= 0.15;
			else
				newCompatibility += 0.15;
			
			if (this.speed > this.myRobotSpeed)
				newCompatibility -= 0.05;
			else if (this.speed != -1) // Only reduces compatibility if speed is known (not -1)
				newCompatibility += 0.05;
			
			// Takes their health into consideration.
			// Using two quadratic equations to increase compatibility quadratically as health decreases below 50 and do the opposite as health increases above 50.
			if (this.getHealth() > 50) 
				newCompatibility += (-0.00004164931)*Math.pow((this.getHealth()-50),2);
			else if (this.getHealth() < 50)
				newCompatibility += (0.0000416931)*Math.pow((this.getHealth()-50),2);
			
			// Takes their battle log into consideration.
			// First gets the sum of the battle log win-loss differences.
			int fightDifferenceSum = 0;
			for (int i = 0; i < this.pastFights.length; i++) {
				fightDifferenceSum += this.pastFights[i];
			}
			
			// Increases or decreases the compatibility at a decreasing rate if this robot has fought this opponent before.
			double rate = 0;
			if (fightDifferenceSum > 0) {
				rate = 0.04;
			}
			else if (fightDifferenceSum < 0) {
				rate = -0.04;
			}
			for (int i = 0; i < Math.abs(fightDifferenceSum); i++) {
				newCompatibility += (i+1)*rate;
				rate /= 1.0+(0.15*this.myRobotAttack);
			}
		}
		
		// Updates compatibility.
		return newCompatibility;
	}
}
