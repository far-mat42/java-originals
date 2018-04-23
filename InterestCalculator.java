import java.util.Scanner;

/**
 * Gets information about a loan or payment from the user and outputs the payment schedule
 * @author Farris Matar
 * @version September 21, 2017
 */

public class InterestCalculator {

	/**
	* Making a payment schedule for paying a loan
	*/

	public static void main(String[] args) {
		// Declaring variables
		double initialLoan,interestRate,monthlyPayment; // To be entered in by user
		double interest,principalPaid,initialAmount,amountRemaining; // To be calculated every month
		
		double month = 0;
		double totalInterest = 0;
		
		// Getting the info from the user
		// Initializing scanner
		Scanner input = new Scanner(System.in);
		// Inputting info
		// Setting amount to be paid (using a loop to ensure it is above 0)
		do {
			System.out.print("Enter the loan amount: ");
			initialLoan = input.nextDouble();
			input.nextLine();
			amountRemaining = initialLoan;
		} while(amountRemaining <=0);
			
		// Setting amount to be paid (using a loop to ensure it is at least 0)
		do {
			System.out.print("Enter the interest rate (%): ");
			interestRate = (input.nextDouble())/100;
			input.nextLine();
		} while(interestRate < 0);
		
		System.out.print("Enter how much you'll pay per month: ");
		monthlyPayment = input.nextDouble();
		input.nextLine();
		
		// Checking if monthly payment will allow user to pay off loan.
		interest = amountRemaining/12*interestRate;
		while (monthlyPayment <= interest) {
			System.out.print("Current monthly payment is insufficient to pay off loan. Enter another amount: ");
			monthlyPayment = input.nextDouble();
			input.nextLine();
		}
		
		// Printing the headings for the chart.
		System.out.println("\nMonth\t   Initial Amount\t Interest\tPrincipal Paid\t    Amount Remaining");
		
		// Creating a loop to continue making a chart showing the payment schedule.
		// Loop will stop one month before final payment so as not to overpay loan (special case, see below).
		while (amountRemaining >= monthlyPayment) {
			// Calculating the interest
			interest = amountRemaining/12*interestRate;
			totalInterest += interest;
			
			// Calculating how much of loan is actually paid (principal paid).
			principalPaid = monthlyPayment-interest;
			
			// Updating the amounts
			initialAmount = amountRemaining;
			amountRemaining -= principalPaid;
			
			// Printing out the chart.
			month ++;
			System.out.format("\n%.0f\t   $%9.2f\t\t $%7.2f\t$%7.2f\t    $%9.2f",month,initialAmount,interest,principalPaid,amountRemaining);
			
		}
		// Calculating and printing out the last payment (special case).
		// Redoing calculations keeping in mind not to overpay the loan.
		month ++;
		interest = amountRemaining/12*interestRate;
		principalPaid = amountRemaining+interest;
		initialAmount = amountRemaining;
		amountRemaining = 0;
		// Printing the last payment
		System.out.format("\n%.0f\t   $%9.2f\t\t $%7.2f\t$%7.2f\t    $%9.2f",month,initialAmount,interest,principalPaid,amountRemaining);
		
		// Printing out the total interest.
		System.out.format("\n\nTotal interest: $%.2f",totalInterest);
	}

}
