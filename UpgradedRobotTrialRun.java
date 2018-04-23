import becker.robots.*;

/**
 * A test run for the upgraded robot object.
 * @author Farris Matar
 * @version November 7, 2017
 */
public class UpgradedRobotTrialRun {
	
	/**
	 * Runs the upgraded robot through various test runs to ensure its upgrades work.
	 */
	public static void main(String[] args) {
		// Creating the city and the robot.
		City oakville = new City();
		UpgradedRobot myRobot = new UpgradedRobot(oakville,0,0,Direction.EAST);
		
		// Creating various piles of things.
		for (int i = 0; i < 5; i++) {
			@SuppressWarnings("unused")
			Thing trash = new Thing(oakville,1,2);
		}
		for (int i = 0; i < 8; i++) {
			@SuppressWarnings("unused")
			Thing trash = new Thing(oakville,5,5);
		}
		
		// Running the upgraded robot through its new commands.
		// Move command
		myRobot.move(5);
		
		// Turn right command
		myRobot.turnToRight();
		myRobot.move(3);
		
		// Turn around command
		myRobot.turnAround();
		myRobot.move(2);
		
		myRobot.turnLeft();
		myRobot.move(3);
		
		// Pick thing command
		myRobot.pickThing(3);
		System.out.println("The robot picked up "+myRobot.countThingsInBackpack()+" things.");
		
		myRobot.move(2);
		
		// Put thing command
		myRobot.putThing(2);
		
		myRobot.turnAround();
		myRobot.move(2);
		
		// Count things command
		System.out.println("The robot left "+myRobot.countThings()+" things here.");
		myRobot.turnAround();
		myRobot.move(2);
		System.out.println("The robot put down "+myRobot.countThings()+" things here.");
		
		myRobot.turnLeft();
		myRobot.move(4);
		myRobot.turnLeft();
		myRobot.move(5);
		
		// Pick all things command
		System.out.println("The robot sees "+myRobot.countThings()+" things here.");
		myRobot.pickAllThings();
		System.out.println("The robot picked up all "+(myRobot.countThingsInBackpack()-1)+" things and now has "+myRobot.countThingsInBackpack()+" things.");
		
		myRobot.turnAround();
		myRobot.move(2);
		
		// Put all things command
		myRobot.putAllThings();
		System.out.println("The robot put down all "+myRobot.countThings()+" things in its backpack and now has "+myRobot.countThingsInBackpack()+" things in its inventory.");
		
		// Can put thing command.
		if (myRobot.canPutThing())
			System.out.println("The robot can still put things down.");
		else
			System.out.println("The robot can no longer put anymore things down.");
	}
}
