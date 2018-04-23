import java.applet.*;
import java.awt.*;

/**
 * Drawing a robot in an applet.
 * @author Farris Matar
 * @version October 18, 2017
 */
public class RobotsApplet  extends Applet{
	// Creating the colors.
	Color GRAY = new Color(125,125,125);
	Color SHADED_GRAY = new Color(95,95,95);
	Color DARK_GRAY = new Color(75,75,75);
	Color DARKER_GRAY = new Color(50,50,50);
	Color DARKEST_GRAY = new Color(25,25,25);
	Color DARK_RED = new Color(180,0,0);
	Color DARKER_RED = new Color(50,0,0);
	Color DARK_GREEN = new Color(0,120,0);
	Color DARKER_GREEN = new Color(0,50,0);
	Color LIGHT_BLUE = new Color(100,220,180);
	Color LIGHTER_BLUE = new Color(180,220,255);
	Color DARK_BLUE = new Color(25,55,45);
	Color YELLOW = new Color(255,255,0);
	Color DARK_YELLOW = new Color(40,40,0);
	Color PURPLE = new Color(90,0,135);
	Color DARK_PURPLE = new Color(40,0,60);
	
	/**
	 * Initializes the applet.
	 */
	public void init() {
		setSize(1000,600);
		setBackground(Color.BLACK);
	}
	
	/**
	 * Draws one leg for the robot
	 * @param x: x-coordinate of the leg
	 * @param y: y-coordinate of the leg
	 * @param g: The reference to the Graphics
	 */
	public void drawActivatedRobotLeg(int x,int y,Graphics g) {
		// Main leg
		g.setColor(GRAY);
		g.fillRect(x,y,35,100);
		// Bends/details
		g.setColor(DARKEST_GRAY);
		// Using a loop to create multiple bends.
		for (int i = 0; i < 9; i++) {
			g.fillRect(x,(y+10*(i+1))-1,35,2);
		}
		// Drawing the foot.
		g.setColor(DARK_GRAY);
		g.fillRect(x-8,y+100,51,25);
	}
	
	/**
	 * Draws both arms for the robot
	 * @param x: x-coordinate of the arms
	 * @param y: y-coordinate of the arms
	 * @param g: The reference to the Graphics
	 */
	public void drawActivatedRobotArms(int x,int y,Graphics g) {
		// List of coordinates for the left arm.
		int [] xCoordinatesLeft = {x-200,x-190,x+10,x};
		int [] yCoordinatesLeft = {y-50,y-15,y-50,y-85};
		// List of coordinates for the right arm.
		int [] xCoordinatesRight = {x+200,x+190,x-10,x};
		int [] yCoordinatesRight= {y-50,y-15,y-50,y-85};
		
		// Drawing the arms.
		g.setColor(GRAY);
		g.fillPolygon(xCoordinatesLeft,yCoordinatesLeft,4);
		g.fillPolygon(xCoordinatesRight,yCoordinatesRight,4);
		
		// Using a nested for loop to draw thicker details for each arm.
		g.setColor(DARKEST_GRAY);
		// Right arm
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x-110+j-(10*i),y-67+(2*i),x-102+j-(10*i),y-32+(2*i));
			}
		}
		// Left arm
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x+188+j-(10*i),y-51-(2*i),x+182+j-(10*i),y-14-(2*i));
			}
		}
		
		// Using a for loop to draw thick claws for each arm.
		// Right claw
		g.setColor(DARK_GRAY);
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-249+i,y-55+i,60-(2*i),60-(2*i),-90,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-250+i,y-55+i,60-(2*i),60-(2*i),-90,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-251+i,y-55+i,60-(2*i),60-(2*i),-90,210);
		}
		// Left claw
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+189+i,y-57+i,60-(2*i),60-(2*i),60,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+190+i,y-57+i,60-(2*i),60-(2*i),60,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+191+i,y-57+i,60-(2*i),60-(2*i),60,210);
		}
	}
	
	/**
	 * Draws the robot's head
	 * @param x: x-coordinate of the robot's head
	 * @param y: y-coordinate of the robot's head
	 * @param g: The reference to the Graphics
	 */
	public void drawActivatedRobotHead(int x,int y,Graphics g) {
		// Drawing the main head
		g.setColor(DARK_GRAY);
		g.fillRect(x-60,y-30,120,80);
		
		// Drawing the antenna
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-2,y-60,4,30);
		g.setColor(DARK_RED);
		g.fillOval(x-5,y-65,10,10);
		
		// Drawing the eyes.
		g.setColor(DARKEST_GRAY);
		g.fillOval(x-35,y-10,25,25);
		g.fillOval(x+10,y-10,25,25);
		
		g.setColor(DARK_GREEN);
		g.fillOval(x+14,y-6,17,17);
		g.fillOval(x-31,y-6,17,17);
		
		// Drawing the mouth with a series of bright blue squares.
		for (int i = 0; i < 8; i++) {
			g.setColor(Color.BLACK);
			g.fillRect(x-47+(12*i),y+28,12,12);
			g.setColor(LIGHTER_BLUE);
			g.fillRect(x-45+(12*i),y+30,8,8);
		}
	}
	
	/**
	 * Draws various lights on the robot's body
	 * @param x: x-coordinate of the lights
	 * @param y: y-coordinates of the lights
	 * @param g: The reference to the Graphics
	 */
	public void drawAcivatedRobotLights(int x,int y,Graphics g) {
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-2,y-2,34,49);
		g.setColor(DARK_RED);
		g.fillRect(x,y,30,45);
		
		for (int i = 0; i < 3; i++) {
			g.setColor(DARKEST_GRAY);
			g.fillRect(x+38,y-12+(24*i),34,24);
			g.setColor(YELLOW);
			g.fillRect(x+40,y-10+(24*i),30,20);
		}
		
		for (int i = 0; i < 3; i++) {
			g.setColor(DARKEST_GRAY);
			g.fillOval(x-2+(10*i),y-15,12,12);
			g.setColor(Color.GREEN);
			g.fillOval(x+(10*i),y-13,8,8);
		}
		
		g.setColor(DARKEST_GRAY);
		g.fillOval(x-39,y-8,31,31);
		g.fillOval(x-39,y+27,31,31);
		g.setColor(LIGHT_BLUE);
		g.fillOval(x-36,y-5,25,25);
		g.setColor(Color.RED);
		g.fillOval(x-36,y+30,25,25);
		
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-38,y+62,111,21);
		g.setColor(PURPLE);
		g.fillRect(x-35,y+65,105,15);
	}
	
	/**
	 * Draws the robot in a standard state
	 * @param x: x-coordinate of the robot
	 * @param y: y-coordinate of the robot
	 * @param g: The reference to the Graphics
	 */
	public void drawRobotActivated(int x,int y,Graphics g) {	
		// Drawing the legs
		drawActivatedRobotLeg(x-70,y+75,g);
		drawActivatedRobotLeg(x+35,y+75,g);
		// Drawing the arms
		drawActivatedRobotArms(x,y+30,g);
		// Drawing the neck
		g.setColor(GRAY);
		g.fillRect(x-25,y-120,50,50);
		// Drawing the details for the neck.
		g.setColor(DARKEST_GRAY);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x-25,y-100+(i*10)+j,x+25,y-100+(i*10)+j);
			}
		}
		// Drawing the main body
		g.setColor(DARK_GRAY);
		g.fillRect(x-100,y-75,200,175);
		// Drawing the head
		drawActivatedRobotHead(x,y-150,g);
		// Drawing the robot's lights
		drawAcivatedRobotLights(x-15,y-23,g);
	}
	
	
	
	/**
	 * Draws the disabled robot's head
	 * @param x: x-coordinate of the robot's head
	 * @param y: y-coordinate of the robot's head
	 * @param g: The reference to the Graphics
	 */
	public void drawDisabledRobotHead(int x,int y,Graphics g) {
		// Drawing the main head
		g.setColor(DARKER_GRAY);
		g.fillRect(x-60,y-30,120,80);
		
		// Drawing the antenna
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-2,y-60,4,30);
		g.setColor(DARKER_RED);
		g.fillOval(x-5,y-65,10,10);
		
		// Drawing the eyes.
		g.setColor(DARKEST_GRAY);
		g.fillOval(x-35,y-10,25,25);
		g.fillOval(x+10,y-10,25,25);
		
		g.setColor(DARKER_GREEN);
		g.fillOval(x+14,y-6,17,17);
		g.fillOval(x-31,y-6,17,17);
		
		// Drawing the mouth with a series of bright blue squares.
		for (int i = 0; i < 8; i++) {
			g.setColor(Color.BLACK);
			g.fillRect(x-47+(12*i),y+28,12,12);
			g.setColor(DARK_BLUE);
			g.fillRect(x-45+(12*i),y+30,8,8);
		}
	}
	
	/**
	 * Draws one leg for the disabled robot
	 * @param x: x-coordinate of the leg
	 * @param y: y-coordinate of the leg
	 * @param g: The reference to the Graphics
	 */
	public void drawDisabledRobotLeg(int x,int y,Graphics g) {
		// Main leg
		g.setColor(SHADED_GRAY);
		g.fillRect(x,y,35,100);
		// Bends/details
		g.setColor(DARKEST_GRAY);
		// Using a loop to create multiple bends.
		for (int i = 0; i < 9; i++) {
			g.fillRect(x,(y+10*(i+1))-1,35,2);
		}
		// Drawing the foot.
		g.setColor(DARKER_GRAY);
		g.fillRect(x-8,y+100,51,25);
	}
	
	/**
	 * Draws both arms for the disabled robot
	 * @param x: x-coordinate of the arms
	 * @param y: y-coordinate of the arms
	 * @param g: The reference to the Graphics
	 */
	public void drawDisabledRobotArms(int x,int y,Graphics g) {
		// List of coordinates for the left arm.
		int [] xCoordinatesLeft = {x-170,x-160,x-60,x-70};
		int [] yCoordinatesLeft = {y+50,y+85,y-70,y-105};
		// List of coordinates for the right arm.
		int [] xCoordinatesRight = {x+170,x+160,x+60,x+70};
		int [] yCoordinatesRight= {y+50,y+85,y-70,y-105};
		
		// Drawing the arms.
		g.setColor(SHADED_GRAY);
		g.fillPolygon(xCoordinatesLeft,yCoordinatesLeft,4);
		g.fillPolygon(xCoordinatesRight,yCoordinatesRight,4);
		
		// Using a nested for loop to draw thicker details for each arm.
		g.setColor(DARKEST_GRAY);
		// Right arm
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x-90+j-(9*i),y-72+(13*i),x-82+j-(9*i),y-32+(13*i));
			}
		}
		// Left arm
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x+158+j-(9*i),y+28-(13*i),x+150+j-(9*i),y+70-(13*i));
			}
		}
		
		// Using a for loop to draw thick claws for each arm.
		// Right claw
		g.setColor(DARKER_GRAY);
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-209+i,y+40+i,60-(2*i),60-(2*i),-65,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-210+i,y+40+i,60-(2*i),60-(2*i),-65,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x-211+i,y+40+i,60-(2*i),60-(2*i),-65,210);
		}
		// Left claw
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+149+i,y+50+i,60-(2*i),60-(2*i),30,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+150+i,y+50+i,60-(2*i),60-(2*i),30,210);
		}
		for (int i = 0; i < 15; i++) {
			g.drawArc(x+151+i,y+50+i,60-(2*i),60-(2*i),30,210);
		}
	}
	
	/**
	 * Draws the lights on the disabled robot's body
	 * @param x: x-coordinate of the lights
	 * @param y: y-coordinate of the lights
	 * @param g: The reference to the Graphics
	 */
	public void drawDisabledRobotLights(int x,int y,Graphics g) {
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-2,y-2,34,49);
		g.setColor(DARKER_RED);
		g.fillRect(x,y,30,45);
		
		for (int i = 0; i < 3; i++) {
			g.setColor(DARKEST_GRAY);
			g.fillRect(x+38,y-12+(24*i),34,24);
			g.setColor(DARK_YELLOW);
			g.fillRect(x+40,y-10+(24*i),30,20);
		}
		
		for (int i = 0; i < 3; i++) {
			g.setColor(DARKEST_GRAY);
			g.fillOval(x-2+(10*i),y-15,12,12);
			g.setColor(DARKER_GREEN);
			g.fillOval(x+(10*i),y-13,8,8);
		}
		
		g.setColor(DARKEST_GRAY);
		g.fillOval(x-39,y-8,31,31);
		g.fillOval(x-39,y+27,31,31);
		g.setColor(DARK_BLUE);
		g.fillOval(x-36,y-5,25,25);
		g.setColor(DARKER_RED);
		g.fillOval(x-36,y+30,25,25);
		
		g.setColor(DARKEST_GRAY);
		g.fillRect(x-38,y+62,111,21);
		g.setColor(DARK_PURPLE);
		g.fillRect(x-35,y+65,105,15);
	}
	
	/**
	 * Draws the robot in its disabled state
	 * @param x: x-coordinate of the robot
	 * @param y: y-coordinate of the robot
	 * @param g: The reference to the Graphics
	 */
	public void drawRobotDisabled(int x,int y,Graphics g) {
		// Drawing the legs
		drawDisabledRobotLeg(x-70,y+75,g);
		drawDisabledRobotLeg(x+35,y+75,g);
		// Drawing the arms
		drawDisabledRobotArms(x,y+30,g);
		// Drawing the neck
		g.setColor(SHADED_GRAY);
		g.fillRect(x-25,y-120,50,50);
		// Drawing the details for the neck.
		g.setColor(DARKEST_GRAY);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				g.drawLine(x-25,y-100+(i*10)+j,x+25,y-100+(i*10)+j);
			}
		}
		// Drawing the main body
		g.setColor(DARKER_GRAY);
		g.fillRect(x-100,y-75,200,175);
		// Drawing the head
		drawDisabledRobotHead(x,y-150,g);
		// Drawing the robot's lights
		drawDisabledRobotLights(x-15,y-23,g);
	}
	
	/**
	 * The paint method
	 * @param g: The reference to the Graphics
	 */
	public void paint(Graphics g) {
		this.drawRobotActivated(250,300, g);
		this.drawRobotDisabled(750,300,g);
	}
	
}