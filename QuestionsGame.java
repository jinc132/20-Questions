/**
* Jin Chang
* August 10, 2017
* TA: Miri Hyman
* Assignment 6: QuestionsGame.java
*
* An QuestionsGame is a guessing game in which it asks multiple yes or
* no questions to determine the final answer. It will play the game
* once. If the computer determines the answer correctly then, it wins.
* Otherwise, if it does not then the client wins. The client has the
* ability to create a new QuestionsGame from scratch or modify to make
* it better.
*/
import java.util.*;
import java.io.*;

public class QuestionsGame {
   // Private fields.
   private QuestionNode overallRoot;     // Reference to current Node.
   private Scanner console;              // Client's responses.
   
   /**
   * Constructs a new Scanner object that takes in information
   * from the client.
   */
   private QuestionsGame() {
      console = new Scanner(System.in);
   }
   
   /**
   * If the file is unavailable, then with the given intialObject
   * it will create a new QuestionsGame in which the given initial
   * object is the first answer in the game.
   */
   public QuestionsGame(String initialObject) {
      this();
      overallRoot = new QuestionNode(initialObject);
   }
   
   /**
   * It will construct a new QuestionsGame with the given input,
   * building a new game of questions and answers.
   */
   public QuestionsGame(Scanner input) {
      this();
      // Creates the tree.
      overallRoot = buildTree(input);
   }
   
   /**
   * pre: If the given output is null then, it will throw an
   *      IllegalArgumentException.
   * post: Will traverse and store the current QuestionsGame to
   * the given output file.
   */
   public void saveQuestions(PrintStream output) {
      if(output == null) {
         throw new IllegalArgumentException();
      }
      // Traverses throught the tree.
      traverseTree(overallRoot, output);
   }
   
   /**
   * Using the current QuestionsGame, it will play one complete guessing
   * game with the client. It will ask the user for a yes or no question
   * until the final answer has been determined. If the computer doesn't
   * win the game then, the QuestionsGame will be edited so that it
   * contains the correct answer and question.
   */
   public void play() {
      overallRoot = play(overallRoot);
   }
   
   /**
   * post: Creates a new QuestionsGame by looking at each line of information
   *       and returns a new QuestionNode of questions and answers in pre-order.
   */
   private QuestionNode buildTree(Scanner input) {
      String type = input.nextLine();
      // Differentiates between the question and answer.
      if(type.contains("A")) {
         return new QuestionNode(input.nextLine());
      } else {
         return new QuestionNode(input.nextLine(), buildTree(input), buildTree(input));
      }
   }
   
   /**
   * Will traverse through the QuestionsGame and will print each given current
   * QuestionsNode to the given output file in pre-order.
   */
   private void traverseTree(QuestionNode curr, PrintStream out) {
      if(curr.left == null && curr.right == null) {
         out.println("A:");
         out.println(curr.data);
      } else {
         out.println("Q:");
         out.println(curr.data);
         // Print the left side.
         traverseTree(curr.left, out);
         // Print the right side.
         traverseTree(curr.right, out);
      }
   }
   
   /**
   * Traverses through the QuestionsGame once using the given QuestionNode. It will
   * ask the client yes or no questions until the final answer has been determined.
   * Once the answer has been reached it will return that answer.
   */
   private QuestionNode play(QuestionNode curr) {
      if(curr.left == null && curr.right == null) {
         System.out.println("I guess that your object is " + curr.data + "!");
         System.out.print("Am I right? (y/n)? ");
         // Check if the answer is correct.
         if(console.nextLine().trim().toLowerCase().startsWith("y")) {
            System.out.println("Awesome! I win!");
            return overallRoot;
         } else {
            // Asks for correct answer.
            System.out.println("Boo! I Lose.  Please help me get better!");
            System.out.print("What is your object? ");
            String answr = console.nextLine();
            // Question
            System.out.println("Please give me a yes/no question that"
                  + " distinguishes between " + answr + " and " + curr.data + ".");
            System.out.print("Q: ");
            String question = console.nextLine();
            System.out.print("Is the answer \"yes\" for " + answr + "? (y/n)? ");
            // Determines which branch the answers goes to.
            if(console.nextLine().trim().toLowerCase().startsWith("y")) {
               curr = new QuestionNode(question, new QuestionNode(answr), curr);
            } else {
               curr = new QuestionNode(question, curr, new QuestionNode(answr));
            }
            return overallRoot;
         }
      } else {
         System.out.print(curr.data + " (y/n)? ");
         if(console.nextLine().trim().toLowerCase().startsWith("y")) {
            return play(curr.left);
         } else {
            return play(curr.right);
         }
      }
   }
   
   /**
   * It will traverse through the QuestionsGame until the computer's incorrect answer
   * is located using the given QuestionNode. Once found it will rearrange the
   * QuestionsGame such that it replaces the given incorrect answer with the given
   * question and has the incorrect answer and the client's answer as it's answers.
   * It will return the newly edited QuestionsGame.
   */
//    private QuestionNode modify(String question, String answr, QuestionNode myAnswr, 
//          QuestionNode curr) {
//      if(curr == myAnswr) {
//             System.out.print("Is the answer \"yes\" for " + answr + "? (y/n)? ");
//             // Determines which branch the answers goes to.
//             if(console.nextLine().trim().toLowerCase().startsWith("y")) {
//                curr = new QuestionNode(question, new QuestionNode(answr), myAnswr);
//             } else {
//                curr = new QuestionNode(question, myAnswr, new QuestionNode(answr));
//             }
//      } else {
//             // Edits the left and right branch of the tree.
//             curr.left = modify(question, answr, myAnswr, curr.left);
//             curr.right = modify(question, answr, myAnswr, curr.right);
//      }
//      return curr;
//    }
   
   /**
   * Each QuestionNode object represents a single node in the binary tree
   * for a game of 20 questions.
   */
   private static class QuestionNode {
      public final String data;
      public QuestionNode left;
      public QuestionNode right;
      
      /**
      * Constructs a new node to store the given data and no left or right
      * node.
      */
      public QuestionNode(String data) {
         this(data, null, null);
      }
      
      /**
      * Constructs a new node to store the given data and a references
      * to the given left node and right node.
      */
      public QuestionNode(String data, QuestionNode left, QuestionNode right) {
         this.data = data;
         this.left = left;
         this.right = right;
      }
   }
}