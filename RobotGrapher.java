import becker.robots.*;

/**
 * A blueprint for a graphing robot.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class RobotGrapher extends RobotSE{
	
	/**
	 * Constructor for the graphing robot.
	 * @param city: The city the robot is in.
	 * @param avenue: The initial y-coordinate of the robot.
	 * @param street: The initial x-coordinate of the robot.
	 * @param direction: The initial direction the robot is facing.
	 */
	public RobotGrapher(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the graphing method.
	/**
	 * Command to make the robot draw the bar graph.
	 */
	public void drawBarGraph() {
		while (super.canPickThing()) {
			this.drawBar();
			this.moveToNextBar();
		}
	}
	
	// Layer two of the graphing method.
	/**
	 * Command to make the robot pick up a stack of things needed to draw a bar.
	 */
	private void pickUpThings() {
		super.pickAllThings();
	}
	
	/**
	 * Command to make the robot draw one bar for the bar graph.
	 * 
	 * Pre: Robot is standing over a stack of things and facing where it needs to go.
	 */
	private void drawBar() {
		this.pickUpThings();
		this.drawBarSegment(super.countThingsInBackpack());
		this.returnToBeginning();
	}
	
	/**
	 * Command to have the robot return to the avenue with the thing stacks.
	 * 
	 * Pre: Robot is facing away from where it needs to go.
	 * Post: Robot is on the avenue where the thing stacks are to the left of the robot.
	 */
	private void returnToBeginning() {
		super.turnAround();
		super.move(super.getAvenue()-1);
	}
	
	/**
	 * Command to make robot move from its current bar to the next bar.
	 * 
	 * Pre: Robot is on the avenue where the thing stacks are to the left of the robot.
	 * Post: Robot is on the next stack of things and facing where it needs to go.
	 */
	private void moveToNextBar() {
		super.turnLeft();
		super.move();
		super.turnLeft();
	}
	
	// Layer three of the graphing method.
	/**
	 * Command to make robot draw all segments of a bar.
	 * @param segments: The number of segments for the bar that need to be drawn.
	 * 
	 * Pre: The robot is facing where it needs to go and is carrying the things it needs to draw the bar.
	 * Post: The robot is at the end of the bar facing away from where it needs to go.
	 */
	private void drawBarSegment(int segments) {
		for (int i = 0; i < segments; i++) {
			super.move();
			super.putThing();
		}
	}
}
