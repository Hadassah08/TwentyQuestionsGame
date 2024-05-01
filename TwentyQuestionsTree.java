import java.util.*;
import java.io.*;

public class TwentyQuestionsTree {
    private Scanner scanner; // Scanner object for user input
    private TwentyQuestionsNode root; // Root node of the question tree
    public int questionCount; // Counter to keep track of the number of questions asked

    // Constructor to initialize the scanner and create the root node with a default item
    public TwentyQuestionsTree() {
        scanner = new Scanner(System.in);
        root = new TwentyQuestionsNode("Computer");
    }

    // Method to load the question tree from a file
    public void load(Scanner input) {
        root = loadTree(input);
    }

    // Recursive helper method to load the tree from the input scanner
    private TwentyQuestionsNode loadTree(Scanner input) {
        String type = input.nextLine(); // Read the type of node from the file
        String item = input.nextLine(); // Read the item of the node from the file
        if (type.equals("Q:")) { // If it's a question node
            TwentyQuestionsNode yes = loadTree(input); // Load the yes branch recursively
            TwentyQuestionsNode no = loadTree(input); // Load the no branch recursively
            return new TwentyQuestionsNode(item, yes, no); // Create and return a new question node
        } else if (type.equals("A:")) { // If it's an answer node
            return new TwentyQuestionsNode(item); // Create and return a new answer node
        } else {
            throw new IllegalArgumentException("Invalid file format!"); // Throw exception for invalid format
        }
    }

    // Method to save the question tree to a file
    public void save(PrintStream output) {
        saveTree(root, output);
    }

    // Recursive helper method to save the tree to the output stream
    private void saveTree(TwentyQuestionsNode node, PrintStream output) {
        if (node != null) {
            if (node.isitLeaf()) { // If it's a leaf node (answer)
                output.println("A:"); // Write answer tag
                output.println(node.item); // Write item
            } else { // If it's a question node
                output.println("Q:"); // Write question tag
                output.println(node.item); // Write item
            }
            saveTree(node.yes, output); // Recursively save the yes branch
            saveTree(node.no, output); // Recursively save the no branch
        }
    }

    // Method to start the game
    public void playGame() {
        questionCount = 0; // Reset question count
        root = playGame(root); // Start the game recursively
    }

    // Recursive helper method to play the game
    private TwentyQuestionsNode playGame(TwentyQuestionsNode node) {
        if (node.isitLeaf()) { // If it's a leaf node (answer)
            if (yesTo("Is it a " + node.item + "?")) { // Ask if the item matches
                System.out.println("I win!"); // If yes, computer wins
            } else {
                node = expandTree(node); // If no, expand the tree
            }
        } else { // If it's a question node
            if (yesTo(node.item)) { // Ask the question and move to yes or no branch
                questionCount++; // Increment question count
                node.yes = playGame(node.yes); // Recursively play game for yes branch
            } else {
                questionCount++; // Increment question count
                node.no = playGame(node.no); // Recursively play game for no branch
            }
        }
        return node;
    }

    // Method to expand the tree based on user input
    private TwentyQuestionsNode expandTree(TwentyQuestionsNode node) {
        System.out.print("You win! What were you thinking of? "); // Ask for the new item
        String newItem = scanner.nextLine();
        System.out.print("Give me a question to distinguish " + newItem + " from " + node.item + ": "); // Ask for a question
        String newQuestion = scanner.nextLine();
        System.out.print("What would the answer be for " + newItem + "? (yes/no): "); // Ask for the answer
        boolean answer = scanner.nextLine().trim().toLowerCase().equals("yes");

        TwentyQuestionsNode oldNode = new TwentyQuestionsNode(node.item); // Create a new node for the old item
        TwentyQuestionsNode newNode = new TwentyQuestionsNode(newItem); // Create a new node for the new item
        node.item = newQuestion; // Update the current node's item to the new question
        if (answer) { // If the answer is yes
            node.yes = newNode; // Set the yes branch to the new item
            node.no = oldNode; // Set the no branch to the old item
        } else { // If the answer is no
            node.yes = oldNode; // Set the yes branch to the old item
            node.no = newNode; // Set the no branch to the new item
        }
        return node; // Return the updated node
    }

    // Method to ask a yes/no question and get the user's response
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (yes/no): "); // Display the prompt
        String response = scanner.nextLine().trim().toLowerCase(); // Read the user's response
        while (!response.equals("yes") && !response.equals("no")) { // Validate the response
            System.out.println("Please answer 'yes' or 'no'."); // Ask for valid input
            System.out.print(prompt + " (yes/no): "); // Display the prompt again
            response = scanner.nextLine().trim().toLowerCase(); // Read the user's response
        }
        return response.equals("yes"); // Return true if the response is yes, false otherwise
    }

    // Method to get the total number of questions asked in the game
    public int getQuestionCount() {
        return questionCount; // Return the question count
    }
}
