import java.io.*;
import java.util.*;

public class TwentyQuestionsMain {
    // Constant variable for the file name storing questions
    public static final String QUESTION_FILE = "questions";

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to 20 Questions!" + "\n" + "You will be asked a series of questions and the computer will try to guess what you are thinking of");
        System.out.println();

        // Create a new instance of TwentyQuestionsTree
        TwentyQuestionsTree questions = new TwentyQuestionsTree();

        // Check if the user wants to load a previous game
        if (questions.yesTo("Do you want to load a previous game?")) {
            Scanner fileScanner = null;
            try {
                // Open the file for reading
                fileScanner = new Scanner(new File(QUESTION_FILE));
                // Load the game from the file
                questions.load(fileScanner);
            } finally {
                if (fileScanner != null) {
                    fileScanner.close(); // Close the Scanner to release resources
                }
            }
        }
        System.out.println();

        // Game loop
        do {
            // Start a new game
            System.out.println("Think of an object, and I'll try to guess it!");
            questions.playGame();
            // Display the total number of questions asked in the game
            System.out.println("Total questions asked: " + questions.getQuestionCount());
            System.out.println();
        } while (questions.yesTo("Do you want to play again?"));

        // Save the game state to a file
        questions.save(new PrintStream(new File(QUESTION_FILE)));
    }
}
