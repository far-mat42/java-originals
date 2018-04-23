import java.util.Scanner;

/**
 * A class that stores bank account information.
 * @author Farris Matar
 * @version October 28, 2017
 */
public class BankAccount {
	// Creating the global variables.
	private double balance;
	private int accountID;
	private String clientName;
	
	/**
	 * The constructor method for a bank account.
	 * @param accountNum A unique number for the account.
	 * @param balance The initial balance of the account.
	 */
	public BankAccount(int accountID, String clientName, double balance) {
		this.balance = balance;
		this.clientName = clientName;
		this.accountID = accountID;
	}
	
	/**
	 * A constructor method for a bank account that is built with file input.
	 * @param input File input holding account information
	 */
	public BankAccount(Scanner input) {
		this.accountID = input.nextInt();
		this.balance = input.nextDouble();
		this.clientName = input.nextLine();
	}
	
	/**
	 * A command method to deposit money into an account.
	 * @param amount The amount being added to the account.
	 */
	public void deposit(double amount) {
		this.balance += amount;
		System.out.format("Successfully deposited $%.2f to%s's account.\n\n",amount,this.clientName);
	}
	
	/**
	 * A command method to withdraw money from an account.
	 * @param amount The amount being taken out of the account.
	 */
	public void withdraw(double amount) {
		// Checking if the amount is less than the balance to prevent overdrawing.
		if (amount <= this.balance) {
			this.balance -= amount;
			System.out.format("Successfully withdrawn $%.2f from%s's account.\n\n",amount,this.clientName);
		}
		else
			System.out.format("%s does not have enough money in their account to withdraw this amount.\n\n",this.clientName);
	}
	
	/**
	 * A query method to get an account's number.
	 * @return Account number
	 */
	public int getAccountID() {
		return this.accountID;
	}
	
	/**
	 * A query method to get an account's balance.
	 * @return Account balance
	 */
	public double checkBalance() {
		return this.balance;
	}
	
	/**
	 * A query method to get the client name of an account.
	 * @return Name of account's client
	 */
	public String getClientName() {
		return this.clientName;
	}
}
