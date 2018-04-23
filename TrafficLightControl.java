import java.applet.*;
import java.awt.*;

public class TrafficLightControl extends Applet{
	/**
	 * Initializes the applet
	 */
	public void init() {
		setSize(800,600);
		setBackground(Color.BLACK);
	}
	
	public void paint(Graphics g) {
		TrafficLight trafalgar = new TrafficLight(0,0,g);
		TrafficLight mcCraney = new TrafficLight(400,10,g);
		
		mcCraney.goYellow();
		trafalgar.goRed();
	}
}
