import becker.robots.*;

public class UpgradedRobot extends Robot {
	
	/**
	 * Constructor for an upgraded robot.
	 * @param theCity: The city the robot exists in
	 * @param street: The initial street the robot is on (x-coordinate)
	 * @param avenue: The initial avenue the robot is on (y-coordinate)
	 * @param direction: The initial direction the robot will face
	 */
	public UpgradedRobot(City theCity, int street, int avenue, Direction direction) {
		// Calls the constructor of the super class (Robot)
		super(theCity,street,avenue,direction);
	}
	
	/**
	 * Command to make the robot turn right by making it turn left three times.
	 */
	public void turnToRight() {
		for (int i = 0; i < 3; i++) {
			super.turnLeft();
		}
	}
	
	/**
	 * Command to turn the robot around by making it turn left twice.
	 */
	public void turnAround() {
		for (int i = 0; i < 2; i++) {
			super.turnLeft();
		}
	}
	
	/**
	 * Command to make the robot move a certain number of steps in its current direction
	 * @param numSteps: The number of steps the robot will take.
	 */
	public void move(int numSteps) {
		for (int i = 0; i < numSteps; i++) {
			super.move();
		}
	}
	
	/**
	 * Command for robot to pick up a certain number of things at the current intersection.
	 * @param numThings: The number of things the robot will pick up.
	 */
	public void pickThing(int numThings) {
		for (int i = 0; i < numThings; i++) {
			super.pickThing();
		}
	}
	
	/**
	 * Command for robot to put down a certain number of things at the current intersection.
	 * @param numThings: The number of things the robot will put down.
	 */
	public void putThing(int numThings) {
		for (int i = 0; i < numThings; i++) {
			super.putThing();
		}
	}
	
	/**
	 * Query to see if robot can put a thing down.
	 * @return Whether or not the robot can put a thing down.
	 */
	public boolean canPutThing() {
		if (super.countThingsInBackpack() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Command for robot to pick up all things at the current intersection.
	 */
	public void pickAllThings() {
		while (super.canPickThing()) {
			super.pickThing();
		}
	}
	
	/**
	 * Command for robot to put down all things at the current intersection.
	 */
	public void putAllThings() {
		while (super.countThingsInBackpack() > 0) {
			super.putThing();
		}
	}
	
	/**
	 * Command for robot to count the number of things at the intersection
	 * @return Number of things at the current intersection.
	 */
	public int countThings() {
		int thingCount = 0;
		
		while (super.canPickThing()) {
			super.pickThing();
			thingCount ++;
		}
		
		for (int i = 0; i < thingCount; i++) {
			super.putThing();
		}
		
		return thingCount;
	}
}
