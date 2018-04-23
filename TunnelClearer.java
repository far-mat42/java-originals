import becker.robots.*;

/**
 * A blueprint for a tunnel clearing robot.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class TunnelClearer extends RobotSE{
	/**
	 * The constructor for the Tunnel clearer.
	 * @param city: The city the robot is in.
	 * @param avenue: The initial y-coordinate of the robot.
	 * @param street: The initial x-coordinate of the robot.
	 * @param direction: The initial direction the robot is facing.
	 */
	public TunnelClearer(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the tunnel clearing method.
	/**
	 * Command to make the robot clear the tunnel.
	 */
	public void clearTunnel() {
		boolean tunnelClear;
		
		// Keeps making the robot run the tunnel until it has determined it is clear.
		do {
			this.getToTunnel();
			tunnelClear = this.getToNextItem();
			this.returnToDropOffBox();
			this.dropOffItem();
		} while (tunnelClear == false);
		
		this.exitDropOffBox();
	}
	
	// Layer two of the tunnel clearing method.
	/**
	 * Command to make robot get to the tunnel from the drop-off box.
	 * 
	 * Pre: Robot is in the drop-off box and facing where it needs to go.
	 */
	private void getToTunnel() {
		super.move();
	}
	
	/**
	 * Command to make the robot move to the next item in the tunnel.
	 * @param itemDistance: The distance to the next item.
	 * @return Whether or not the tunnel is clear yet.
	 * 
	 * Pre: The robot is at the drop-off box, facing where it needs to go and isn't carrying anything.
	 * Post: The robot is standing over an item and turned towards the drop-off box.
	 */
	private boolean getToNextItem() {
		boolean cleared;
		// Moves the robot until it finds something to pick up.
		while (super.canPickThing() == false) {
			super.move();
		}
		
		cleared = this.checkTunnelClear();
		
		super.turnAround();
		return cleared;
	}
	
	/**
	 * Command to make robot pick up item in the tunnel.
	 * 
	 * Pre: Robot is standing over a thing.
	 */
	private void clearItem() {
		super.pickThing();
	}
	
	/**
	 * Command to make robot return to drop-off box.
	 * 
	 * Pre: The robot has picked up the item and is facing where it needs to go.
	 * Post: The robot is at the drop-off box and has turned around.
	 */
	private void returnToDropOffBox() {
		super.move(calculateDistanceToDropBox());
		super.turnAround();
	}
	
	/**
	 * Command to make robot drop off the item at the drop off box.
	 * 
	 * Pre: Robot is at the drop off box and carrying an item.
	 */
	private void dropOffItem() {
		super.putThing();
	}
	
	/**
	 * Command to make the robot get out of the drop off box.
	 */
	private void exitDropOffBox() {
		super.move();
	}
	
	// Layer three of the tunnel clearing method.
	/**
	 * Query to calculate the distance between the robot and the drop off box.
	 * @return Distance to the drop-off box.
	 */
	private int calculateDistanceToDropBox() {
		int currentPosition = super.getAvenue();
		return currentPosition-0;
	}
	
	/**
	 * Query to determine if the tunnel is clear yet.
	 * @return
	 */
	private boolean checkTunnelClear() {
		// If the robot has reached the end of the tunnel, the tunnel is considered cleared. Otherwise, it is considered not cleared.
		if (super.frontIsClear() == false) {
			this.clearItem();
			if (super.canPickThing())
				return false;
			else
				return true;
		}
		else {
			this.clearItem();
			return false;
		}
	}
}
