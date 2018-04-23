import becker.robots.*;

public class SweeperRobot extends RobotSE{
	
	/**
	 * Constructor for the sweeper robot.
	 * @param city: The city the robot is in.
	 * @param avenue: The initial y-coordinate of the robot.
	 * @param street: The initial x-coordinate of the robot.
	 * @param direction: The initial direction the robot is facing.
	 */
	public SweeperRobot(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the sweep stairs method.
	/**
	 * Command to make the robot sweep the stairs.
	 */
	public void sweepStairs() {
		this.cleanStairs();
		this.throwOutTrash();
		this.celebrate();
	}
	
	/**
	 * Overriding the move method to pick up trash as it moves.
	 */
	public void move() {
		// Makes the robot pick up things before it moves.
		this.pickAllThings();
		
		// Only allows the robot to move if it can move.
		if (this.frontIsClear())
			super.move();
		
		// Makes the robot pick up things after it moves.
		this.pickAllThings();
	}
	
	// Layer two of the sweep stairs method.
	/**
	 * Command to make the robot get to get to its next task, such as the stairs or trash can.
	 * 
	 * Pre: The robot is facing towards wherever it needs to go.
	 * Post: The robot is directly adjacent to its next task (stairs or trash can) and facing a wall.
	 */
	private void getToNextObjective() {
		// Keeps moving the robot until it hits a wall.
		while (this.frontIsClear()) {
			this.move();
		}
	}
	
	/**
	 * Command to make the robot clean the stairs
	 * 
	 * Pre: The robot is facing where it needs to go (the stairs).
	 */
	private void cleanStairs() {
		this.getToNextObjective();
		this.climbStairs();
		this.clearLanding();
		this.descendStairs();
	}
	
	/**
	 * Command to make the robot put its trash in the trash can.
	 * 
	 * The robot is facing where it needs to go (trash can).
	 */
	private void throwOutTrash() {
		this.getToNextObjective();
		this.climbTrashCan();
		this.dropTrash();
		this.climbTrashCan();
	}
	
	/**
	 * Command to make the robot say it's done cleaning (not required).
	 */
	private void celebrate() {
		// Printing out the celebration.
		System.out.println("The stairs have been cleaned.");
		
		// Acting out the celebration.
		for (int i = 0; i < 5; i++) {
			this.turnAround();
			this.turnAround();
		}
	}
	
	// Layer three of the sweep stairs method.
	/**
	 * Command to make the robot climb up the stairs.
	 * 
	 * Pre: The robot is adjacent to the first step of the stairs and facing the wall.
	 * Post: The robot is above all steps and facing across the landing.
	 */
	private void climbStairs() {
		// Keeps moving the robot up steps until it doesn't find a step in front of it.
		do {
			this.moveStep(0);
		} while(this.frontIsClear() == false);
	}
	
	/**
	 * Command to make the robot clean the landing above the stairs.
	 * 
	 * Pre: The robot is above all the steps and facing where it needs to go.
	 * Post: The robot is past the landing and facing towards the first step of the stairs.
	 */
	private void clearLanding() {
		// Keeps moving the robot until it determines it has cleared the landing.
		do {
			this.move();
		} while (this.checkForGround());
		
		// Moving and turning the robot one last time to prepare for the next method.
		this.turnRight();
		this.move();
	}
	
	/**
	 * Command to make the robot go down the stairs.
	 * 
	 * Pre: The robot is past the landing and facing towards the first step of the stairs.
	 */
	private void descendStairs() {
		boolean stairsDescended; // Local variable to know if the robot has reached the bottom of the stairs.
		
		// Keeps moving the robot down stairs until it determines it has reached the bottom of the stairs.
		do {
			stairsDescended = this.moveStep(1);
		} while(stairsDescended == false);
	}
	
	/**
	 * Command to make the robot climb in or out the trash can.
	 * 
	 * Pre: The robot is directly adjacent to the trash can.
	 */
	private void climbTrashCan() {
		this.getUpTrashCan();
		this.getInTrashCan();
	}
	
	/**
	 * Command to make the robot put its trash in the trash can.
	 * 
	 * Pre: The robot is in the trash can.
	 */
	private void dropTrash() {
		this.putAllThings();
	}
	
	// Layer four of the sweep stairs method.
	/**
	 * Query to make the robot move a step and check if it has reached the bottom if it is descending (using a helper method).
	 * @param upOrDown: 1 means going down, any other number means going up.
	 * @return If the robot has reached the bottom of the stairs or not.
	 * 
	 * Pre: The robot is facing the wall of a step
	 * Post: Same orientation, but one step up or down.
	 */
	private boolean moveStep(int upOrDown) {
		// Getting over the step.
		this.turnLeft();
		this.move();
		
		boolean groundFound; // Local variable to know if the robot has reached the bottom of the stairs.
		
		// Checks if the robot is going up or down the stairs.
		if (upOrDown == 1) {
			// Checks if the robot has reached the bottom of the stairs (i.e. the ground) if it is going down.
			groundFound = this.checkForGround();
			
			// If it hasn't reached the bottom, the robot will continue going down the step.
			if (groundFound == false) {
				// Getting across the step.
				this.turnRight();
				this.move();
			}
		}
		
		// If the robot is going up the stairs, it will simply continue going up the step.
		else {
			groundFound = false;
			// Getting across the step.
			this.turnRight();
			this.move();
		}
		
		return groundFound;
	}
	
	/**
	 * Query to check if there is ground under the robot.
	 * @return: Whether or not the robot is standing on ground.
	 * 
	 * Pre: The robot is looking for a potential wall to the right of it.
	 * Post: Returns to its original position.
	 */
	private boolean checkForGround() {
		boolean groundFound; // Local variable to know if there is ground or not.
		
		// Turning to face the ground.
		this.turnRight();
		
		// The robot will look for a wall to determine if it has reached the ground.
		if (this.frontIsClear() == false)
			groundFound = true;
		else
			groundFound = false;
		
		// Returning the robot to its original position.
		this.turnLeft();
		
		return groundFound;
	}
	
	/**
	 * Command to make the robot get over the trash can.
	 * 
	 * Pre: The robot is facing the trash can and directly adjacent to it.
	 * Post: The robot has climbed the wall of the trash can and facing where it needs to go.
	 */
	private void getUpTrashCan() {
		this.turnLeft();
		super.move();
		this.turnRight();
	}
	
	/**
	 * Command to make the robot get in the trash can.
	 * 
	 * Pre: The robot has climbed the wall of the trash can and facing where it needs to go.
	 */
	private void getInTrashCan() {
		super.move();
		this.turnRight();
		super.move();
		this.turnLeft();
	}
}