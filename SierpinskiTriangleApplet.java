import java.applet.*;
import java.awt.*;

/**
 * Draws Sierpinski's Triangle using recursion.
 * @author Farris Matar
 * @version December 25, 2017
 */
public class SierpinskiTriangleApplet extends Applet{
	
	/**
	 * Initializes the applet
	 */
	public void init() {
		setSize(800,600);
		setBackground(Color.WHITE);
	}
	
	/**
	 * Recursive method to draw Sierpinski's Triangle using multiple smaller triangles.
	 * @param centerX The x-coordinate of the center of the Sierpinski's Triangle
	 * @param centerY The y-coordinate of the center of the Sierpinski's Triangle
	 * @param sideLength Length of the outer sides of the Sierpinski's Triangle
	 * @param g Reference to the Graphics
	 */
	public void drawSierpinskiTriangle(int centerX, int centerY,int sideLength, Graphics g) {
		// If the side length is large enough, it draws three more triangles in a triangle shape.
		if (sideLength > 3) {
			drawSierpinskiTriangle(centerX,centerY-sideLength/4,sideLength/2,g);
			drawSierpinskiTriangle(centerX+sideLength/4,centerY+sideLength/4,sideLength/2,g);
			drawSierpinskiTriangle(centerX-sideLength/4,centerY+sideLength/4,sideLength/2,g);
		}
		// Draws the triangle.
		drawTriangle(centerX,centerY,sideLength,g);
	}
	
	/**
	 * Helper method to draw a triangle shape using lines.
	 * @param centerX The x-coordinate of the center of the triangle
	 * @param centerY The y-coordinate of the center of the triangle
	 * @param sideLength Length of each side of the triangle
	 * @param g Reference to the Graphics
	 */
	public void drawTriangle(int centerX, int centerY, int sideLength, Graphics g) {
		// Only draws a single triangle if the side lengths are too small.
		if (sideLength < 4) {
			g.drawLine(centerX,centerY-sideLength/2,centerX-sideLength/2,centerY+sideLength/2);
			g.drawLine(centerX,centerY-sideLength/2,centerX+sideLength/2,centerY+sideLength/2);
			g.drawLine(centerX+sideLength/2,centerY+sideLength/2,centerX-sideLength/2,centerY+sideLength/2);
		}
		else {
			// Draws the outer, larger triangle.
			g.drawLine(centerX,centerY-sideLength/2,centerX-sideLength/2,centerY+sideLength/2);
			g.drawLine(centerX,centerY-sideLength/2,centerX+sideLength/2,centerY+sideLength/2);
			g.drawLine(centerX+sideLength/2,centerY+sideLength/2,centerX-sideLength/2,centerY+sideLength/2);
			// Draws the inner, smaller, upside-down triangle.
			g.drawLine(centerX-sideLength/4,centerY,centerX+sideLength/4,centerY);
			g.drawLine(centerX-sideLength/4,centerY,centerX,centerY+sideLength/2);
			g.drawLine(centerX+sideLength/4,centerY,centerX,centerY+sideLength/2);
		}
	}
	
	/**
	 * Paint method to draw the applet.
	 */
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		
		drawSierpinskiTriangle(400,300,300,g);
	}
}
