import java.util.*;
import java.io.*;

/**
 * Translates text from a file into Pig Latin
 * @author Farris Matar
 * @version September 22, 2017
 */

public class PigLatinTranslator {

	/**
	 * Translator that converts a text file into Pig Latin
	 */
	public static void main(String[] args) {
		// Variable Declaration
		String fileName = "Sample Text.txt";
		String fileCopy = "";
		String currentWord, newWord;
		int currentLineLength = 0;
		
		// Using a try-catch method to open the file
		try {
			Scanner input = new Scanner(new File(fileName));
			
			// Using a while loop to keep checking every word in the file.
			while (input.hasNext()) {
				// Looking at the word and converting it to Pig Latin
				currentWord = input.next();
				newWord = currentWord.substring(1)+currentWord.charAt(0)+"ay";
				
				// Checking if capital letters must be modified.
				if (Character.isUpperCase(currentWord.charAt(0))) {
					newWord = newWord.substring(0,1).toUpperCase() + newWord.substring(1).toLowerCase();
				}
				
				// Checking if there is any punctuation that needs to be moved.
				if (newWord.indexOf(".") != -1 || newWord.indexOf(",") != -1 || newWord.indexOf(":") != -1 || newWord.indexOf(";") != -1 || newWord.indexOf("!") != -1) {
					// Determining which punctuation it is.
					String punctuation = "";
					
					if (newWord.indexOf(".") != -1)
						punctuation = ".";
					if (newWord.indexOf(",") != -1)
						punctuation = ",";
					if (newWord.indexOf(":") != -1)
						punctuation = ":";
					if (newWord.indexOf(";") != -1)
						punctuation = ";";
					if (newWord.indexOf("!") != -1)
						punctuation = "!";
					
					// Moving the punctuation to the end.
					newWord = newWord.replaceFirst(punctuation, "");
					newWord += punctuation;
				}
				
				fileCopy += newWord+" ";
				
				// Keeping track of the current line's length and making a new line if it gets too long.
				currentLineLength += newWord.length()+1;
				
				if (currentLineLength >= 60) {
					currentLineLength = 0;
					fileCopy += "\n";
				}
			}
			
			System.out.print(fileCopy);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}

}