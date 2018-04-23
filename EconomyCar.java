import java.util.Scanner;

public class EconomyCar extends Vehicle{
	
	/**
	 * Constructor for an economy car
	 * @param price Price of the car
	 * @param model Name of the car
	 */
	public EconomyCar(double price, String model) {
		super(1, 4, 400.0, 5, price, model);
	}
	
	/**
	 * Constructor for an economy car allowing automatic input.
	 * @param input Source of input (i.e. file, user)
	 */
	public EconomyCar(Scanner input) {
		super(1, 4, 400.0, 5, input.nextDouble(), input.nextLine().substring(1));
	}
	
	/**
	 * Query method to return information on the vehicle.
	 * @return Information on the vehicle in a string
	 */
	public String toString() {
		String data = "";
		
		data += "Economy Car\t";
		data += this.getModel() + "\t";
		data += "$" + this.getPrice();
		
		return data;
	}
}
