import becker.robots.*;

/**
 * A blueprint for a cleaning robot.
 * @author Farris Matar
 * @version November 16, 2017
 */

public class CleanerRobot extends RobotSE{
	
	/**
	 * Constructor for the cleaning robot.
	 * @param city: The city the robot is in
	 * @param avenue: The initial y-coordinate of the robot.
	 * @param street: The initial x-coordinate of the robot.
	 * @param direction: The initial direction the robot is facing.
	 */
	public CleanerRobot(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the clean city method.
	/**
	 * Command that overrides the old move method to make the robot pick up anything that may be under it before moving.
	 */
	public void move() {
		if (super.canPickThing())
			super.pickAllThings();
		super.move();
	}
	
	public void cleanCity() {
		this.getToStartingPosition();
		this.sweepStreets();
		this.dropOffTrash();
	}
	
	// Layer two of the clean method.
	private void getToStartingPosition() {
		this.orientSelf(Direction.EAST);
		this.getToCityWall();
		this.orientSelf(Direction.NORTH);
		this.getToCityWall();
		this.orientSelf(Direction.WEST);
	}
	
	private void sweepStreets() {
		boolean cityNotCleaned;
		do {
			this.getToCityWall();
			cityNotCleaned = this.checkCityCleaned();
			this.turnToNextStreet();
		} while (cityNotCleaned == true);
	}
	
	private void dropOffTrash() {
		this.orientSelf(Direction.WEST);
		this.getToTopLeftCorner();
		this.dropItems();
		this.getOutOfTheWay();
	}
	
	// Layer three of the clean method.
	private void getToCityWall() {
		while (this.frontIsClear()) {
			this.move();
		}
	}
	
	private void orientSelf(Direction d) {
		while(super.getDirection() != d) {
			super.turnLeft();
		}
	}
	
	private void turnToNextStreet() {
		this.orientSelf(Direction.SOUTH);
		if (super.frontIsClear())
			this.move();
		this.orientSelf(Direction.EAST);
		
		// Checking if the robot is facing the right direction.
		if (super.frontIsClear() == false)
			this.orientSelf(Direction.WEST);
	}
	
	/**
	 * Command to get the robot to the drop off area at the top left corner.
	 * 
	 * Pre: The robot is at the bottom right corner and acing where it needs to go.
	 * Post: The robot is at the top-left corner and facing east.
	 */
	private void getToTopLeftCorner() {
		// Moves the robot down the street.
		while(super.frontIsClear()) {
			this.move();
		}
		// Turns the robot onto the avenue.
		this.orientSelf(Direction.NORTH);
		// Moves the robot up the avenue.
		while(super.frontIsClear()) {
			this.move();
		}
		// Turns the robot to face away from the walls.
		this.orientSelf(Direction.EAST);
	}
	
	/**
	 * Command to make robot drop off its trash.
	 * 
	 * Pre: Robot has cleaned the city and has returned to the top-left corner.
	 */
	private void dropItems() {
		super.putAllThings();
	}
	
	/**
	 * Command to make the robot get away from where it dropped off its trash.
	 */
	private void getOutOfTheWay() {
		super.move();
	}
	
	/**
	 * Query to check if the city has been cleaned.
	 */
	private boolean checkCityCleaned() {
		boolean cityCleaned;
		
		if (this.getDirection() == Direction.WEST) {
			this.turnLeft();
			cityCleaned = this.frontIsClear();
			this.turnRight();
		}
		
		else {
			this.turnRight();
			cityCleaned = this.frontIsClear();
			this.turnLeft();
		}
		
		return cityCleaned;
	}
}
