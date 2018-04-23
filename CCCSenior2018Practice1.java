import java.util.Scanner;

public class CCCSenior2018Practice1 {

	public static void main(String[] args) {
		int [] numbers = new int[4];
		
		Scanner input = new Scanner(System.in);
		
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = input.nextInt();
		}
		
		if (numbers[0] == 8 || numbers[0] == 9) {
			if (numbers[3] == 8 || numbers[3] == 9) {
				if (numbers[1] == numbers[2]) {
					System.out.println("ignore");
				}
				else
					System.out.println("answer");
			}
			else
				System.out.println("answer");
		}
		else
			System.out.println("answer");
	}
}
