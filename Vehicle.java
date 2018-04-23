/**
 * An abstract class describing the general details of a vehicle
 * @author Farris Matar
 * @version December 12, 2017
 */
public abstract class Vehicle {
	
	// Setting the global variables.
	private int doors;
	private double towCap;
	private int passengers;
	private int vehicleClass;
	private double price;
	private String model;
	
	/**
	 * Constructor that all vehicles will share.
	 * @param doors Number of doors on the vehicle
	 * @param towCap Towing capacity of the vehicle
	 * @param passengers Number of passengers the vehicle can carry
	 */
	public Vehicle(int vehicleClass, int doors, double towCap, int passengers, double price, String model) {
		this.vehicleClass = vehicleClass;
		this.doors = doors;
		this.towCap = towCap;
		this.passengers = passengers;
		this.price = price;
		this.model = model;
	}
	
	/**
	 * Query to get the number of doors on the vehicle.
	 * @return Number of doors on the vehicle
	 */
	public int getDoors() {
		return this.doors;
	}
	
	/**
	 * Query to get the towing capacity of the vehicle.
	 * @return Towing capacity of the vehicle
	 */
	public double getTowCap() {
		return this.towCap;
	}
	
	/**
	 * Query to get the number of passengers the vehicle can carry.
	 * @return Number of passengers the vehicle can carry
	 */
	public int getPassengers() {
		return this.passengers;
	}
	
	/**
	 * Query to get a number representing the type of vehicle the object is.
	 * @return Vehicle class
	 */
	public int getVehicleClass() {
		return this.vehicleClass;
	}
	
	/**
	 * Query to get the price of a vehicle.
	 * @return Price of the vehicle
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Query to get the model name of a vehicle.
	 * @return Model name of the vehicle
	 */
	public String getModel() {
		return this.model;
	}
	
	/**
	 * Method that should return a string holding the vehicle's information.
	 */
	public abstract String toString();
}
