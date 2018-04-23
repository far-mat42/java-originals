import becker.robots.*;

/**
 * A blueprint for a spiral drawing robot.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class SpiralBot extends RobotSE{
	
	/**
	 * Constructor for a spiral drawing robot.
	 * @param city: The city the robot is in.
	 * @param avenue: The initial y-coordinate of the robot.
	 * @param street: The initial x-coordinate of the robot.
	 * @param direction: The initial direction the robot is facing.
	 * @param startingItems: The number of Things the robot starts off with.
	 */
	public SpiralBot(City city, int avenue, int street, Direction direction, int startingItems) {
		super(city, avenue, street, direction, startingItems);
	}
	
	// Layer one of the draw spiral method.
	/**
	 * Command to make the robot draw a certain number of spirals
	 * @param numOfSpirals: The number of spirals the robot will draw (must be greater than 0).
	 */
	public void drawSpiral (int numOfSpirals) {
		this.orientRobot();
		this.pickUpSupplies();
		for (int i = 0; i < numOfSpirals; i++) {
			this.initiateSpiral();
			this.drawSpiralSides(i);
		}
		this.leaveSpiral();
	}
	
	// Layer two of the draw spiral method.
	/**
	 * Command to make the robot orient itself to face towards the east.
	 * 
	 * Post: The robot is facing east.
	 */
	private void orientRobot() {
		while (super.getDirection() != Direction.EAST) {
			super.turnLeft();
		}
	}
	
	/**
	 * Command to make robot pick up the things it will need to draw a spiral.
	 * 
	 * Pre: The robot is standing over a stack of things.
	 */
	private void pickUpSupplies() {
		super.pickAllThings();
	}
	
	/**
	 * Command to make the robot start a spiral.
	 * 
	 * Pre: The robot is at the end of a spiral or the very centre.
	 * Post: The centre of the spiral is created and the robot is in position to draw the first side of the current spiral.
	 */
	private void initiateSpiral() {
		if (super.canPickThing() == false)
			super.putThing();
		super.move();
		super.turnLeft();
	}
	
	/**
	 * Makes the robot draw the sides of the spiral.
	 * @param currentSpiral: Which spiral the robot is currently drawing.
	 */
	private void drawSpiralSides(int currentSpiral) {
		this.drawFirstSide(currentSpiral);
		this.drawOtherSides(currentSpiral);
	}
	
	/**
	 * Command to make the robot leave the spiral.
	 * 
	 * Pre: The robot is standing over the end of the spiral.
	 */
	private void leaveSpiral() {
		this.orientRobot();
		super.move();
	}
	
	// Layer three of the draw spiral method.
	/**
	 * Command to draw the first side of the spiral (requires different approach from other sides).
	 * @param spiral: Which spiral the robot is currently drawing.
	 * 
	 * Pre: The robot is facing where it needs to go and doesn't have any things placed under it.
	 * Post: The robot has a thing under it and is facing where it needs to go next.
	 */
	private void drawFirstSide(int spiral) {
		for (int i = 0; i < 1+(2*spiral); i++) {
			super.putThing();
			super.move();
		}
		super.putThing();
		super.turnLeft();
	}
	
	/**
	 * Command to draw the second, third and fourth sides of the spiral.
	 * @param spiral: Which spiral the robot is currently drawing.
	 * 
	 * Pre: The robot has a thing under it and is facing where it needs to go.
	 * Post: The robot is standing over the last thing it put (the end of the spiral).
	 */
	private void drawOtherSides(int spiral) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2+(2*spiral); j++) {
				super.move();
				super.putThing();
			}
			if (i < 2)
				super.turnLeft();
		}
	}
}
