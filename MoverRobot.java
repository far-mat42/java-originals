import becker.robots.*;

/**
 * Blueprint for a mover robot that cleans avenues.
 * @author Farris Matar
 * @version December 5, 2017
 */
public class MoverRobot extends RobotSE {
	
	private int loadCap;
	private int startingAvenue, startingStreet;
	private int stackAvenue, stackStreet;
	
	/**
	 * Creating the constructor for the robot.
	 * @param city The city the robot is in
	 * @param avenue The initial y-coordinate of the robot
	 * @param street The initial x-coordinate of the robot
	 * @param direction The initial direction the robot is facing
	 */
	public MoverRobot(City city, int avenue, int street, Direction direction, int loadCap) {
		super(city, avenue, street, direction);
		this.loadCap = loadCap;
	}
	
	/**
	 * Overriding the pick thing command to only pick something up if it isn't at its load cap yet.
	 */
	public void pickThing() {
		if (this.countThingsInBackpack() < this.loadCap)
			super.pickThing();
	}
	
	/**
	 * Overriding the pick all things command to pick up as many things as it can (keeping in mind the load cap).
	 */
	public void pickAllThings() {
		// Keeps picking things up until it has reached its load cap.
		while (this.countThingsInBackpack() < this.loadCap && this.canPickThing()) {
			super.pickThing();
		}
	}
	
	// Layer one of the sweep avenue command.
	/**
	 * Sweeps an avenue clean.
	 */
	public void sweepAvenue() {
		this.recordStartingPosition();
		this.cleanStack();
		this.returnToStartingPosition();
	}
	
	// Layer two of the sweep avenue command.
	private void recordStartingPosition() {
		this.startingAvenue = this.getAvenue();
		this.startingStreet = this.getStreet();
	}
	
	/**
	 * Command to make the robot clear a stack and drop it off at the drop-off depot.
	 */
	private void cleanStack() {
		boolean stackCleaned;
		this.getToNextObjective(); // Gets the robot to the stack.
		this.recordStackLocation();
		
		// Keeps the robot moving between the stack and the drop-off depot until the stack is cleaned.
		do {
			this.pickAllThings();
			stackCleaned = this.checkStack();
			this.dropOffTrash();
			this.returnToStack(); // Returns the robot to the stack (or where the stack was).
			this.turnLeft();
		} while (stackCleaned == false);
	}
	
	/**
	 * Command to make the robot return to the spot that it started at.
	 */
	private void returnToStartingPosition() {
		this.getToStartingAvenue();
		this.getToStartingStreet();
		this.turnAround();
	}
	
	// Layer three of the sweep avenue command.
	/**
	 * Command to get the robot to its next task.
	 * 
	 * Pre: The robot is facing where it needs to go.
	 */
	private void getToNextObjective() {
		// Moves the robot until it finds things to clean up.
		while (this.canPickThing() == false && this.frontIsClear())
			this.move();
	}
	
	private void recordStackLocation() {
		this.stackAvenue = this.getAvenue();
		this.stackStreet = this.getStreet();
	}
	
	/**
	 * Checks if it has finished cleaning a stack.
	 * @return Whether or not the stack is cleaned
	 * 
	 * Pre: The robot is standing on the spot where the stack would be.
	 */
	private boolean checkStack() {
		// If it can pick something up, it determines the stack isn't cleaned. Otherwise, it determines it has finished cleaning.
		if (this.canPickThing())
			return false;
		else
			return true;
	}
	
	/**
	 * Command to make the robot drop all its things at the drop-off depot.
	 */
	private void dropOffTrash() {
		this.getToDepot();
		this.dropTrash();
	}
	
	/**
	 * Moves the robot until it gets to where the stack is (or was).
	 * 
	 * Pre: The robot is facing where it needs to go.
	 */
	private void returnToStack() {
		while (this.getAvenue() != this.stackAvenue || this.getStreet() != this.stackStreet)
			this.move();
	}
	
	/**
	 * Command to make the robot get to the avenue it started at.
	 */
	private void getToStartingAvenue() {
		// Moves robot east if it is to the left of the avenue it started at.
		if (this.getAvenue() < this.startingAvenue) {
			this.orientSelf(Direction.EAST);
		}
		
		// Moves robot west if it is to the right of the avenue it started at.
		else if (this.getAvenue() > this.startingAvenue) {
			this.orientSelf(Direction.WEST);
		}
		
		// Moves the robot until it gets to its starting avenue.
		while (this.getAvenue() != this.startingAvenue)
			this.move();
	}
	
	/**
	 * Command to make the robot get to the street it started at.
	 */
	private void getToStartingStreet() {
		// Moves robot south if it is above the street it started at.
		if (this.getStreet() < this.startingStreet) {
			this.orientSelf(Direction.SOUTH);
		}
		
		// Moves robot north if it is below the street it started at.
		else if (this.getStreet() > this.startingStreet) {
			this.orientSelf(Direction.NORTH);
		}
		
		// Moves the robot until it gets to its starting street.
		while (this.getStreet() != this.startingStreet)
			this.move();
	}
	
	/**
	 * Helper method to turn the robot to face a certain direction.
	 * @param d The direction the robot is trying to face
	 */
	private void orientSelf(Direction d) {
		// Keeps turning the robot until its direction matches the target direction.
		while (this.getDirection() != d)
			this.turnLeft();
	}
	
	// Layer four of the sweep avenue method.
	/**
	 * Command to get the robot to the drop-off depot.
	 * 
	 * Pre: The robot is in the same street of the drop-off depot and facing 90 degrees from the depot.
	 */
	private void getToDepot() {
		this.turnLeft();
		this.move(); // Moves the robot to get away from the stack and make the helper method work properly.
		this.getToNextObjective();
		this.turnAround();
	}
	
	/**
	 * Command to make the robot drop off its trash.
	 * 
	 * Pre: The robot is at the drop-off depot.
	 */
	private void dropTrash() {
		this.putAllThings();
	}
	
}
