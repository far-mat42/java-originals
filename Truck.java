import java.util.Scanner;

public class Truck extends Vehicle{
	
	/**
	 * Constructor for a truck
	 * @param price Price of the truck
	 * @param model Name of the truck
	 */
	public Truck(double price, String model) {
		super(4, 2, 5000.0, 3, price, model);
	}
	
	/**
	 * Constructor for a truck allowing automatic input.
	 * @param input Source of input (i.e. file, user)
	 */
	public Truck(Scanner input) {
		super(4, 2, 5000.0, 3, input.nextDouble(), input.nextLine().substring(1));
	}
	
	/**
	 * Query method to return information on the vehicle.
	 * @return Information on the vehicle in a string
	 */
	public String toString() {
		String data = "";
		
		data += "Truck\t\t";
		data += this.getModel() + "\t";
		data += "$" + this.getPrice();
		
		return data;
	}
}
