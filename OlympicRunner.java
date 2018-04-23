import becker.robots.*;

/**
 * A blueprint for an olympic runner robot.
 * @author Farris Matar
 * @version November 14, 2017
 */
public class OlympicRunner extends RobotSE{
	
	/**
	 * Creating a constructor for the olympic runner.
	 * @param city: The city the robot is in.
	 * @param avenue: The y-coordinate of the robot.
	 * @param street: The x-coordinate of the robot.
	 * @param direction: The direction the robot is facing.
	 */
	public OlympicRunner(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the race commands.
	/**
	 * A command for the robot to run its portion of the race.
	 * 
	 * Pre: The robot is running the race from left to right.
	 */
	public void runRace() {
		this.pickUpBaton();
		// Makes the robot jump two hurdles.
		for (int i = 0; i < 2; i++) {
			this.getToHurdle();
			this.getOverHurdle();
		}
		this.passBaton();
	}
	
	/**
	 * A command to make a robot self-destruct.
	 * 
	 * Pre: All robots have completed the race, including this one.
	 */
	public void selfDestruct() {
		this.getAwayFromBaton();
		this.crashIntoWall();
	}
	
	// Layer two of the race commands.
	/**
	 * A command to make the robot pick up the baton.
	 * 
	 * Pre: The robot is standing over a thing/baton.
	 */
	private void pickUpBaton() {
		super.pickThing();
	}
	
	/**
	 * A command to make the robot get to the next hurdle.
	 * 
	 * Pre: The robot is facing where it needs to go.
	 */
	private void getToHurdle() {
		while (super.frontIsClear()) {
			super.move();
		}
	}
	
	/**
	 * A command to make the robot jump the hurdle
	 */
	private void getOverHurdle() {
		this.goUpHurdle();
		this.goAcrossHurdle();
		this.goDownHurdle();
	}
	
	/**
	 * A command for the robot to drop the baton for the next runner.
	 * 
	 * Pre: The robot is carrying a baton and in the correct place to drop it.
	 */
	private void passBaton() {
		super.putThing();
	}
	
	/**
	 * A command to make the robot get away from the baton.
	 */
	private void getAwayFromBaton() {
		super.move();
	}
	
	/**
	 * A command to make the robot look for an adjacent wall to crash into.
	 */
	private void crashIntoWall() {
		while (super.frontIsClear()) {
			super.turnRight();
		}
		super.move();
	}
	
	// Layer three of the race commands.
	/**
	 * A command for the robot to go up the hurdle
	 * 
	 * Pre: The robot is directly in front of and facing the hurdle.
	 * Post: The robot is above the hurdle and facing where it needs to go.
	 */
	private void goUpHurdle() {
		super.turnLeft();
		do {
			super.move();
		} while (this.hurdleClimbed() == false);
	}
	
	/**
	 * A command for the robot to get across the hurdle
	 * 
	 * Pre: The robot is above the hurdle and facing where it needs to go.
	 * Post: The robot is across the hurdle and facing where it needs to go.
	 */
	private void goAcrossHurdle() {
		super.move();
		super.turnRight();
	}
	
	/**
	 * A command for the robot to go down the hurdle
	 * 
	 * Pre: The robot is across the hurdle and facing where it needs to go.
	 * Post: The robot is down the hurdle and facing where it needs to go.
	 */
	private void goDownHurdle() {
		while (super.frontIsClear()) {
			super.move();
		}
		super.turnLeft();
	}
	
	/**
	 * Query to check if the robot has finished climbing the hurdle.
	 * @return Whether the robot has climbed the hurdle or not.
	 */
	private boolean hurdleClimbed() {
		boolean cleared;
		super.turnRight();
		
		if (super.frontIsClear())
			cleared = true;
		else {
			cleared = false;
			super.turnLeft();
		}
		
		return cleared;
	}
}
