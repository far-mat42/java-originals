import becker.robots.*;
import java.awt.Color;

public class EscapeRobot extends RobotSE{
	
	Color invisible = new Color(1.0f,0.0f,0.0f,0.5f);
	
	/**
	 * Constructor for the EscaperBot
	 * @param city The city the robot is in
	 * @param avenue The initial y-coordinate of the robot
	 * @param street The initial x-coordinate of the robot
	 * @param direction The initial direction the robot is facing
	 */
	public EscapeRobot(City city, int avenue, int street, Direction direction) {
		super(city, avenue, street, direction);
	}
	
	// Layer one of the escape maze command.
	public void escapeMaze() {
		System.out.println("ESCAPE_ROBOT_LOG: INITIATING ESCAPE PROTOCOL");
		this.findOpenPath();
		this.locateEscapePortal();
		this.teleport();
	}
	
	// Layer two of the escape maze command.
	private void findOpenPath() {
		while (this.frontIsClear() == false) {
			this.turnLeft();
		}
	}
	
	private void locateEscapePortal() {
		while (this.portalLocated() == false) {
			this.hugRightWall();
		}
	}
	
	private void teleport() {
		System.out.println("ESCAPE_ROBOT_LOG: PORTAL FOUND");
		System.out.println("ESCAPE_ROBOT_LOG: TELEPORTING");
		for (int i = 0; i < 10; i++) {
			this.turnAround();
		}
		
		System.out.println("ESCAPE_ROBOT_LOG: MAZE ESCAPED");
	}
	
	// Layer three of the escape maze command.
	private boolean portalLocated() {
		if (this.canPickThing())
			return true;
		else
			return false;
	}
	
	private void hugRightWall() {
		if (this.checkForWall() == false) {
			this.turnRight();
		}
		
		this.avoidDeadEnd();
		this.move();
	}
	
	// Layer four of the escape maze command.
	private boolean checkForWall() {
		boolean wallFound;
		this.turnRight();
		
		if (this.frontIsClear())
			wallFound = false;
		else
			wallFound = true;
		
		this.turnLeft();
		return wallFound;
	}
	
	private void avoidDeadEnd() {
		while (this.frontIsClear() == false)
			this.turnLeft();
	}
}
