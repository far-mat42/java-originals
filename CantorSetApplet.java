import java.applet.*;
import java.awt.*;

/**
 * Uses recursion to draw Cantor's Set
 * @author Farris Matar
 * @version December 25, 2017
 */
public class CantorSetApplet extends Applet{
	
	// Setting various global variables.
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 50;
	private final int INITIAL_LENGTH = 1200;
	private final int CANTOR_WIDTH = 25;
	
	/**
	 * Initializes the applet.
	 */
	public void init() {
		setSize(1200,600);
		setBackground(Color.BLACK);
	}
	
	/**
	 * Recursive method to draw Cantor's Set.
	 * @param x The x-coordinate of the next pair of rectangles
	 * @param y The y-coordinate of the next pair of rectangles
	 * @param length The length of the next pair of rectangles
	 * @param g The reference to the Graphics
	 */
	public void drawNextCantorSet(int x, int y, int length, Graphics g) {
		// Divides the set if the length is greater than the minimum
		if (length > 2) {
			drawNextCantorSet(x,y+50,length/3,g);
			drawNextCantorSet(x+length*2,y+50,length/3,g);
		}
		
		// Draws the pair of rectangles in the next set
		g.fillRect(x,y,length,CANTOR_WIDTH);
		g.fillRect(x+2*length,y,length,CANTOR_WIDTH);
	}
	
	/**
	 * Draws the applet.
	 */
	public void paint(Graphics g) {
		// Drawing Canor's Set using the recursive method.
		g.setColor(Color.ORANGE);
		drawNextCantorSet(INITIAL_X,INITIAL_Y,INITIAL_LENGTH,g);
	}
}
