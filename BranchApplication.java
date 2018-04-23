import java.util.Scanner;
import java.io.*;

/**
 * Application class for the bank accounts.
 * @author Farris Matar
 * @version December 11, 2017
 */
public class BranchApplication {
	
	/**
	 * Accesses and sorts bank accounts from the Accounts file.
	 */
	public static void main(String[] args) {
		// Variable/array declaration.
		int userChoice;
		int accountID;
		boolean accountFound = false;
		String fileName = "Accounts.txt";
		BankAccount [] allAccounts = new BankAccount[19];
		
		// Setting up a try-catch method for the file input.
		try {
			// Initializing the scanners.
			Scanner userInput = new Scanner(System.in);
			Scanner fileInput = new Scanner(new File(fileName));
			
			// Using a loop to import the Accounts file's accounts into the array.
			for (int i = 0; i < 19; i++) {
				allAccounts[i] = new BankAccount(fileInput);
			}
			
			// Starting up the terminal.
			System.out.println("Welcome to the WOSS Bank.\n");
			
			// Looping the menu to keep asking the user what to do until they exit.
			do {
				printTerminalMenu();
				do {
					System.out.print("Enter selection: ");
					userChoice = userInput.nextInt();
				} while (userChoice < 1 || userChoice > 8);
				
				// Going through all possible commands.
				// Access account commands, signing into an account first.
				if (userChoice < 4) {
					System.out.print("Enter account ID: ");
					accountID = userInput.nextInt();
					
					while (accountFound == false) {
						for (int i = 0; i < allAccounts.length; i++) {
							if (accountID == allAccounts[i].getAccountID()) {
								// Signing into the account.
								accountFound = true;
								accountID = i;
								System.out.println("Signed in as"+allAccounts[accountID].getClientName()+", ID "+allAccounts[accountID].getAccountID()+".");
								break;
							}
						}
						if (accountFound == false) {
							System.out.print("That account was not found, please try again: ");
							accountID = userInput.nextInt();
						}
					}
					
					// Check balance command.
					if (userChoice == 1) 
						System.out.format("%s's current balance is $%.2f.\n\n",allAccounts[accountID].getClientName().substring(1),allAccounts[accountID].checkBalance());
					
					// Deposit command.
					if (userChoice == 2) {
						double userAmount;
						do {
							System.out.print("How much would you like to deposit? ");
							userAmount = userInput.nextDouble();
						} while (userAmount < 0);
						allAccounts[accountID].deposit(userAmount);
					}
					
					// Withdraw command.
					if (userChoice == 3) {
						double userAmount;
						do {
							System.out.print("How much would you like to withdraw? ");
							userAmount = userInput.nextDouble();
						} while (userAmount < 0);
						allAccounts[accountID].withdraw(userAmount);
					}
				}
				
				// Sort by balance command.
				if (userChoice == 4)
					sortByBalance(allAccounts);
				
				// Sort by ID command.
				if (userChoice == 5)
					sortByID(allAccounts);
				
				// Sort by names command.
				if (userChoice == 6)
					sortByNames(allAccounts);
				
				if (userChoice == 7) {
					mergeSort(allAccounts,0,allAccounts.length-1);
					
					// Prints out the new record of accounts.
					System.out.println("New record (sorted by balance): ");
					System.out.println("ID\tBalance\tClient name");
					
					for (int i = 0; i < allAccounts.length; i++) {
						System.out.format("%d\t$%6.2f\t%s\n",allAccounts[i].getAccountID(),allAccounts[i].checkBalance(),allAccounts[i].getClientName());
					}
					
					System.out.println();
				}
				
				// Resetting account signed in.
				accountFound = false;
				
			} while (userChoice != 8);
			
			System.out.println("\nLogging out...\nThank you for choosing WOSS Bank.");
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	/**
	 * Prints out the terminal menu to give the user choices on what to do.
	 */
	private static void printTerminalMenu() {
		System.out.println("What would you like to do?");
		System.out.println("Access account:");
		System.out.println("\t1. Check balance");
		System.out.println("\t2. Deposit");
		System.out.println("\t3. Withdraw");
		System.out.println("4. Sort accounts by balance using Bubble Sort");
		System.out.println("5. Sort accounts by ID using Insertion Sort");
		System.out.println("6. Sort accounts by names using Selection Sort");
		System.out.println("7. Sort accounts by balance using Merge Sort");
		System.out.println("8. Log out terminal\n");
	}
	
	/**
	 * Sorts a record of bank accounts by balance using Bubble Sort.
	 * @param accounts The record of bank accounts being sorted
	 */
	private static void sortByBalance(BankAccount [] accounts) {
		BankAccount [] copy = accounts; // Creating a copy of the accounts to make the Bubble Sort work.
		// Erasing the original record.
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = copy[i];
		}
		
		// Using bubble sort to make a sorted original record.
		for (int i = 0; i < copy.length; i++) {
			accounts[i] = copy[i];
			
			// Making sure the record is sorted (by balance) and swapping account positions as needed.
			for (int j = i; j > 0; j--) {
				// Reversing the position of two accounts if they are not sorted properly.
				if (accounts[j].checkBalance() > accounts[j-1].checkBalance())
					swap(j,j-1,accounts);
				else
					break;
			}
		}
		
		// Prints out the new record of accounts.
		System.out.println("New record (sorted by balance): ");
		System.out.println("ID\tBalance\tClient name");
		
		for (int i = 0; i < accounts.length; i++) {
			System.out.format("%d\t$%6.2f\t%s\n",accounts[i].getAccountID(),accounts[i].checkBalance(),accounts[i].getClientName());
		}
		
		System.out.println();
	}
	
	/**
	 * Sorts a record of bank accounts by ID using Insertion Sort.
	 * @param accounts The record of bank accounts being sorted
	 */
	private static void sortByID(BankAccount [] accounts) {
		for (int i = 1; i < accounts.length; i++) {
			int index = i;
			while (index > 0 && accounts[index].getAccountID() < accounts[index-1].getAccountID()) {
				swap(index,index-1,accounts);
				index--;
			}
		}
		
		// Prints out the new record of accounts.
		System.out.println("New record (sorted by ID): ");
		System.out.println("ID\tBalance\tClient name");
		
		for (int i = 0; i < accounts.length; i++) {
			System.out.format("%d\t$%6.2f\t%s\n",accounts[i].getAccountID(),accounts[i].checkBalance(),accounts[i].getClientName());
		}
				
		System.out.println();
	}
	
	/**
	 * Sorts a record of bank accounts by client names using Selection Sort.
	 * @param accounts The record of bank accounts being sorted
	 */
	private static void sortByNames(BankAccount[] accounts) {
		for (int i = 0; i < accounts.length; i++) {
			swapFirstName(i,accounts);
		}
		
		// Prints out the new record of accounts.
		System.out.println("New record (sorted by names): ");
		System.out.println("ID\tBalance\tClient name");
		
		for (int i = 0; i < accounts.length; i++) {
			System.out.format("%d\t$%6.2f\t%s\n",accounts[i].getAccountID(),accounts[i].checkBalance(),accounts[i].getClientName());
		}
						
		System.out.println();
	}
	
	/**
	 * Helper method to swap two elements in an array.
	 * @param index1 Index of the first element being swapped
	 * @param index2 Index of the second element being swapped
	 * @param array The array the elements are in
	 */
	private static void swap(int index1, int index2, BankAccount [] array) {
		BankAccount temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	/**
	 * Swaps a name in a record with whatever name is the highest in the names that follow.
	 * @param startingIndex The index of the name being swapped
	 * @param accounts The record of names being swapped
	 */
	private static void swapFirstName(int startingIndex, BankAccount[] accounts) {
		int firstNameIndex = startingIndex;
		String firstName = accounts[startingIndex].getClientName();
		
		// Looping through all the client names to find which one comes next in the record (alphabetized).
		for (int i = startingIndex+1; i < accounts.length; i++) {
			// Loops through all the letters of the highest name and the next name in the list.
			for (int j = 0; j < firstName.length(); j++) {
				// If the next name in the record is higher than the current highest name, the highest name and index are reset.
				if (firstName.charAt(j) > accounts[i].getClientName().charAt(j)) {
					firstName = accounts[i].getClientName();
					firstNameIndex = i;
					break;
				}
				// If it is determined that the highest name is higher than the next name in the record, the loop is broken.
				else if (firstName.charAt(j) < accounts[i].getClientName().charAt(j))
					break;
			}
		}
		
		// Swapping the highest name with the name at the starting index (if they are not the same).
		if (firstNameIndex != startingIndex) {
			swap(firstNameIndex,startingIndex,accounts);
		}
	}
	
	/**
	 * Recursive method to divide an array into two smaller ones and divide and sort them again.
	 * @param numbers Array being sorted
	 * @param start Starting index position
	 * @param end Ending index position
	 */
	private static void mergeSort(BankAccount [] numbers, int start, int end) {
		// Keeps dividing the array until it is as small as it can be (1 element long)
		if (start < end) {
			int mid = (start+end)/2;
			mergeSort(numbers,start,mid);
			mergeSort(numbers,mid+1,end);
			merge(numbers,start,mid,end);
		}
	}
	
	/**
	 * Helper method to sort a small part of a larger array and merge it into the larger one
	 * @param numbers Array the smaller arrays come from
	 * @param start Starting index position within entire array
	 * @param mid Middle of the indexes of elements being sorted
	 * @param end Ending index position within entire array
	 */
	private static void merge(BankAccount [] numbers, int start, int mid, int end) {
		// Setting up some markers and a temporary array.
		BankAccount [] temp = new BankAccount[numbers.length];
		int pos1 = start;
		int pos2 = mid+1;
		int spot = start;
		
		// Continues sorting and merging until the start or the end markers have moved to the middle.
		while (!(pos1 > mid && pos2 > end)) {
			if ((pos1 > mid) || ((pos2 <= end) && (numbers[pos2].checkBalance() < numbers[pos1].checkBalance()))) {
				temp[spot] = numbers[pos2];
				pos2++;
			}
			else {
				temp[spot] = numbers[pos1];
				pos1++;
			}
			spot++;
		}
		
		// Copy items from the temporary, sorted array into the original.
		for (int i = start; i <= end; i++) {
			numbers[i] = temp[i];
		}
	}
}
