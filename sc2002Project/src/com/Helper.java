package com;
import java.util.Scanner;
import java.io.Console;
import java.util.InputMismatchException;

/**
 * A scanner object for reading user input from the console.
 */
public class Helper {
	
	/**
	 * Constructor for helper class
	 */
	public Helper() {}
	
	/**
	 * A helper class that contains static methods for reading user input from the console.
	 */
	public static final Scanner sc = new Scanner(System.in);
	
	/**
	 * Reads an integer from the console. If the user enters an invalid input,
	 * an error message is displayed and the user is prompted to enter again.
	 * 
	 * @return the integer entered by the user
	 */
	public static int readInt() {
        while (true) {
            try {
                int userInput = -1;
                userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter an integer:");
            }
        }
    }
	
	
	/**
	 * Reads a single character from the console.
	 * 
	 * @return the character entered by the user
	 */
	public static char readChar() {
		char userInput;
		userInput =  sc.next().charAt(0);
		return userInput; 
	}
	
	/**
	 * Reads a string from the console.
	 *
	 * @return the string entered by the user
	 */
	public static String readString() {
		String userInput = sc.nextLine();
		return userInput;
	}
	
	public static void replyYesOrNo(RequestController requestController){
		char opt = Helper.readChar();
		switch (opt) {
		case 'y':
			requestController.acceptReq();
			System.out.println("The request has been accepted");
			break;
		case 'n':
			requestController.rejectReq();
			System.out.println("The request has been rejected");
			break;
		default:
			System.out.println("Invalid Choice!");
			break;
		}
		System.out.println("\n");
		}
	
}
