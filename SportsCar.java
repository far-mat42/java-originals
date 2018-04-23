import java.util.Scanner;

public class SportsCar extends Vehicle{
	
	/**
	 * Constructor for a sports car.
	 * @param price Price of the car
	 * @param model Name of the car
	 */
	public SportsCar(double price, String model) {
		super(2, 2, 100.0, 2, price, model);
	}
	
	/**
	 * Constructor for a sports car allowing automatic input.
	 * @param input Source of input (i.e. file, user)
	 */
	public SportsCar(Scanner input) {
		super(2, 2, 100.0, 2, input.nextDouble(), input.nextLine().substring(1));
	}
	
	/**
	 * Query method to return information on the vehicle.
	 * @return Information on the vehicle in a string
	 */
	public String toString() {
		String data = "";
		
		data += "Sports Car\t";
		data += this.getModel() + "\t";
		data += "$" + this.getPrice();
		
		return data;
	}
}
