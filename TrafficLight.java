import java.awt.*;

/**
 * A class that stores methods to draw and modify traffic lights.
 * @author Farris Matar
 * @version October 30, 2017
 */
public class TrafficLight{
	// Creating the colors.
	Color darkYellow = new Color(200,200,0);
	
	// Creating the global variables.
	private int x;
	private int y;
	private Graphics g;
	
	/**
	 * Creating the constructor method
	 * @param x The x-coordinate of the light.
	 * @param y The y-coordinate of the light.
	 * @param g The reference to the Graphics object.
	 */
	public TrafficLight(int x, int y, Graphics g) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.drawTrafficLight();
	}
	
	/**
	 * Draws a traffic light at the coordinates given.
	 */
	private void drawTrafficLight() {
		g.setColor(darkYellow);
		g.fillRect(this.x,this.y,200,500);
		g.setColor(Color.GRAY);
		g.fillOval(this.x+50,this.y+50,100,100);
		g.fillOval(this.x+50,this.y+200,100,100);
		g.fillOval(this.x+50,this.y+350,100,100);
	}
	
	/**
	 * Makes a traffic light turn green.
	 */
	public void goGreen() {
		// Resetting the other lights.
		g.setColor(Color.GRAY);
		g.fillOval(this.x+50,this.y+50,100,100);
		g.fillOval(this.x+50,this.y+200,100,100);
		g.fillOval(this.x+50,this.y+350,100,100);
		// Making the bottom light green.
		g.setColor(Color.GREEN);
		g.fillOval(this.x+50,this.y+350,100,100);
	}
	
	/**
	 * Makes a traffic light turn yellow.
	 */
	public void goYellow() {
		// Resetting the other lights.
		g.setColor(Color.GRAY);
		g.fillOval(this.x+50,this.y+50,100,100);
		g.fillOval(this.x+50,this.y+200,100,100);
		g.fillOval(this.x+50,this.y+350,100,100);
		// Making the middle light yellow.
		g.setColor(Color.YELLOW);
		g.fillOval(this.x+50,this.y+200,100,100);
	}
	
	/**
	 * Makes a traffic light turn red.
	 */
	public void goRed() {
		// Resetting the other lights.
		g.setColor(Color.GRAY);
		g.fillOval(this.x+50,this.y+50,100,100);
		g.fillOval(this.x+50,this.y+200,100,100);
		g.fillOval(this.x+50,this.y+350,100,100);
		// Making the top light red.
		g.setColor(Color.RED);
		g.fillOval(this.x+50,this.y+50,100,100);
	}
}
