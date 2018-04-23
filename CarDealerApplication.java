import java.util.Scanner;
import java.io.*;

public class CarDealerApplication {
	
	// Creating global variables.
	final static int ECONOMY_CAR_CLASS = 1;
	final static int SPORTS_CAR_CLASS = 2;
	final static int SUV_CAR_CLASS = 3;
	final static int TRUCK_CLASS = 4;
	
	public static void main(String[] args) {
		// Variable/array declaration.
		final int VEHICLE_COUNT = 10;
		String fileName = "Vehicle Order.txt";
		Vehicle [] allVehicles = new Vehicle[VEHICLE_COUNT];
		
		// Using a try-catch method for file input.
		try {
			// Initializing the scanners.
			Scanner fileInput = new Scanner(new File(fileName));
			Scanner userInput = new Scanner(System.in);
			
			// Putting all the vehicles from the vehicle order in the array.
			for (int i = 0; i < allVehicles.length; i++) {
				int model = fileInput.nextInt();
				
				if (model == ECONOMY_CAR_CLASS)
					allVehicles[i] = new EconomyCar(fileInput);
				else if (model == SPORTS_CAR_CLASS)
					allVehicles[i] = new SportsCar(fileInput);
				else if (model == SUV_CAR_CLASS)
					allVehicles[i] = new SUV(fileInput);
				else if (model == TRUCK_CLASS)
					allVehicles[i] = new Truck(fileInput);
			}
			
			// Starting up the terminal.
			System.out.println("Welcome to Oakville Auto-Dealer.");
			int userCommand;
			
			// Looping the terminal until the user decides to log out.
			do {
				dealerTerminalMenu();
				
				// Getting the user's selection from the menu.
				do {
					System.out.print("Enter selection: ");
					userCommand = userInput.nextInt();
				} while (userCommand < 1 || userCommand > 6);
				
				// Going through all the possible commands.
				// Print vehicle records.
				if (userCommand == 1)
					printVehicleRecord(allVehicles);
				
				// Print vehicle records of a certain class.
				if (userCommand == 2) {
					int classChosen;
					
					// Getting the vehicle class from the user.
					do {
						System.out.print("Enter 1 for economy, 2 for sport, 3 for SUV, 4 for truck: ");
						classChosen = userInput.nextInt();
					} while (classChosen < 1 || classChosen > 4);
					
					// Printing out the records of the class chosen.
					recordsOfVehicleClass(allVehicles,classChosen);
				}
				
				/// Sort vehicle records by class.
				if (userCommand == 3) {
					sortRecordsByClass(allVehicles);
					printNewlySortedRecord(allVehicles);
				}
				
				// Sort vehicle records by model name.
				if (userCommand == 4) {
					sortRecordsByNames(allVehicles);
					printNewlySortedRecord(allVehicles);
				}
				
				// Sort vehicle records by price.
				if (userCommand == 5) {
					sortRecordsByPrice(allVehicles);
					printNewlySortedRecord(allVehicles);
				}
				
			} while (userCommand != 6);
			
			// Logging out of the terminal.
			System.out.println("\nLogging out...");
			System.out.println("Thank you for using Oakville Auto-Dealer.");
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	private static void dealerTerminalMenu() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("1. View vehicle records");
		System.out.println("2. View records of a vehicle class");
		System.out.println("3. Sort vehicle records by class");
		System.out.println("4. Sort vehicle records by model name.");
		System.out.println("5. Sort vehicle records by price");
		System.out.println("6. Log out terminal\n");
	}
	
	/**
	 * Helper method to print a record of vehicles neatly.
	 * @param vehicles Record of vehicles being printed
	 */
	private static void printVehicleRecord(Vehicle[] vehicles) {
		System.out.println("\nClass\t\tModel\t\tPrice");
		for (int i = 0; i < vehicles.length; i++) {
			System.out.println(vehicles[i].toString());
		}
	}
	
	/**
	 * Helper method to print the data of a certain class of vehicles in a record of vehicles.
	 * @param vehicles Record of vehicles being searched
	 */
	private static void recordsOfVehicleClass(Vehicle[] vehicles, int vehicleClass) {
		// Sorting the vehicles by name first.
		sortRecordsByNames(vehicles);
		
		System.out.println("\nClass\t\tModel\t\tPrice");
		// Printing out all the vehicles of the vehicle class specified.
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i].getVehicleClass() == vehicleClass)
				System.out.println(vehicles[i].toString());
		}
	}
	
	/**
	 * Sorts a record of vehicles by vehicle classes
	 * @param vehicles Record of vehicles being sorted
	 */
	private static void sortRecordsByClass(Vehicle[] vehicles) {
		// Goes through every vehicle in the record from the second one.
		for (int i = 1; i < vehicles.length; i++) {
			int index = i;
			// Keeps moving the current vehicle to the left of the array until it is in its proper spot.
			while (index > 0 && vehicles[index].getVehicleClass() < vehicles[index-1].getVehicleClass()) {
				swap(index,index-1,vehicles);
				index--;
			}
		}
	}
	
	/**
	 * Sorts a record of vehicles by model names.
	 * @param vehicles Record of vehicles being sorted
	 */
	private static void sortRecordsByNames(Vehicle[] vehicles) {
		// Running the sorting algorithm for all models in the record.
		for (int startingIndex = 0; startingIndex < vehicles.length; startingIndex++) {
			// Resetting the highest name and index
			int firstNameIndex = startingIndex;
			String firstName = vehicles[startingIndex].getModel();
			
			// Going through the record from the first name to the end to find the next alphabetical name.
			for (int i = startingIndex; i < vehicles.length; i++) {
				// Loops through all the letters of the next name in the record to see if it is higher than the current first name.
				for (int j = 0; j < firstName.length(); j++) {
					// If the next name in the record is higher than the current highest name, the highest name and index are reset.
					if (firstName.charAt(j) > vehicles[i].getModel().charAt(j)) {
						firstName = vehicles[i].getModel();
						firstNameIndex = i;
						break;
					}
					// If it is determined that the highest name is higher than the next name in the record, the loop is broken.
					else if (firstName.charAt(j) < vehicles[i].getModel().charAt(j))
						break;
				}
			}
			
			// Swapping the highest name with the name at the starting index (if they are not the same).
			if (firstNameIndex != startingIndex) {
				swap(firstNameIndex,startingIndex,vehicles);
			}
		}
	}
	
	/**
	 * Sorts a record of vehicles by their prices.
	 * @param vehicles Record of vehicles being sorted
	 */
	private static void sortRecordsByPrice(Vehicle[] vehicles) {
		// Going through the selection process for all elements in the record.
		for (int startingIndex = 0; startingIndex < vehicles.length; startingIndex++) {
			// Resetting the lowest price and index.
			int lowestPriceIndex = startingIndex;
			double lowestPrice = vehicles[startingIndex].getPrice();
			
			// Going through the entire record to see if there are any lower prices.
			for (int i = startingIndex+1; i < vehicles.length; i++) {
				// Resets the lowest price and index if a lower price is found.
				if (vehicles[i].getPrice() < lowestPrice) {
					lowestPrice = vehicles[i].getPrice();
					lowestPriceIndex = i;
				}
			}
			
			// Swapping the vehicle of lowest price with the vehicle at the starting index.
			if (lowestPriceIndex != startingIndex)
				swap(lowestPriceIndex,startingIndex,vehicles);
		}
	}
	
	/**
	 * Helper method to swap two vehicles in a record
	 * @param index1 Index of one vehicle
	 * @param index2 Index of another vehicle
	 * @param vehicles Record the vehicles are in
	 */
	private static void swap(int index1, int index2, Vehicle[] vehicles) {
		Vehicle temp = vehicles[index1];
		vehicles[index1] = vehicles[index2];
		vehicles[index2] = temp;
	}
	
	/**
	 * Helper method to print a record of vehicles after they are sorted.
	 * @param vehicles
	 */
	private static void printNewlySortedRecord(Vehicle[] vehicles) {
		// Prints out the newly sorted record.
		System.out.println("\nNew record");
		System.out.println("Class\t\tModel\t\tPrice");
		for (int i = 0; i < vehicles.length; i++) {
			System.out.println(vehicles[i].toString());
		}
	}
}
