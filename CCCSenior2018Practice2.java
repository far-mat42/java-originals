import java.util.Scanner;

public class CCCSenior2018Practice2 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int rows = input.nextInt();
		
		int [][] table = new int[rows][rows];
		
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				table[i][j] = input.nextInt();
			}
		}
		
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				System.out.print(table[i][j]+" ");
			}
			System.out.println();
		}
	}
}
