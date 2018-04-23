import java.util.Scanner;

/**
 * Problem 1 for the 2017 CCC.
 * @author Farris Matar
 * @version November 9, 2017
 */
public class CCCSenior2017Practice1 {

	/**
	 * The first problem of the 2017 CCC.
	 */
	public static void main(String[] args) {
		int numberOfDays;
		int sameRunsDay = 0;
		int swiftsTotalRuns = 0;
		int semaphoresTotalRuns = 0;
		
		Scanner input = new Scanner(System.in);
		numberOfDays = input.nextInt();
		
		int [] swiftsRuns = new int [numberOfDays];
		int [] semaphoresRuns = new int [numberOfDays];
		
		for (int i = 0; i < numberOfDays; i++) {
			swiftsRuns[i] = input.nextInt();
		}
		for (int i = 0; i < numberOfDays; i++) {
			semaphoresRuns[i] = input.nextInt();
		}
		
		for (int i = 0; i < numberOfDays; i++) {
			swiftsTotalRuns += swiftsRuns[i];
			semaphoresTotalRuns += semaphoresRuns[i];
			
			if (swiftsTotalRuns == semaphoresTotalRuns) {
				sameRunsDay = i+1;
			}
		}
		
		System.out.println(sameRunsDay);
	}
}
