import java.util.Scanner;

public class SUV extends Vehicle{
	
	/**
	 * Constructor for an SUV
	 * @param price Price of the SUV
	 * @param model Name of the SUV
	 */
	public SUV(double price, String model) {
		super(3, 4, 1000.0, 8, price, model);
	}
	
	/**
	 * Constructor for an SUV allowing automatic input.
	 * @param input Source of input (i.e. file, user)
	 */
	public SUV(Scanner input) {
		super(3, 4, 1000.0, 8, input.nextDouble(), input.nextLine().substring(1));
	}
	
	/**
	 * Query method to return information on the vehicle.
	 * @return Information on the vehicle in a string
	 */
	public String toString() {
		String data = "";
		
		data += "SUV\t\t";
		data += this.getModel() + "\t";
		data += "$" + this.getPrice();
		
		return data;
	}
}
