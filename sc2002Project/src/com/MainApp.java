package com;
import java.util.Scanner;

/**
 * This is the main class of the SC2002 Project <br>
 * It is responsible for initializing the Database and the AppView.
 *
 */
public class MainApp {
	
	/**
	 * The main method creates an instance of the Database class and the AppView class.
	 * It then calls the viewApp() method to start the application.
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		ExcelData excelData = new ExcelData();
		AppView appview = new AppView();
		
		System.out.println("				.!!!!!.     ~!!!!. ^!!!!!!!!!!!!!!!^ .!!!!!.    .!!!!!.");
        System.out.println("				:@@@@@B~    G@@@@~ Y@@@@@@@@@@@@@@@Y ^@@@@@^    :&@@@@~");
        System.out.println("				:&@@@@@@J   P@@@@~ !PPP5P@@@@@G5PPP7 ^@@@@@^    :&@@@@~");
        System.out.println("				:&@@@@@@@B^ P@@@@~      :@@@@@^      ^@@@@@^    :&@@@@~");
        System.out.println("				:&@@@#P@@@@?G@@@@~      ^@@@@@^      ^@@@@@^    .&@@@@~");
        System.out.println("				:&@@@# 7&@@@@@@@@~      ^@@@@@^      :@@@@@^    .&@@@@~");
        System.out.println("				:&@@@#  :P@@@@@@@~      ^@@@@@^       P@@@@G!^^~P@@@@B.");
        System.out.println("				:@@@@#.   ?&@@@@@~      ^@@@@@^       .5@@@@@@@@@@@@P: ");
        System.out.println("				.Y555J     ^Y5555^      :55555:         :7Y5PGGGPY?^   ");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("		^GPPPPGGGGGGGY ?GGPPP^    .YGPPGY..5GPPPPGGPP5J!    YGPPPPG~    JGPPPPG~    !YPGB#BGPY!.  ");
        System.out.println("		!@@@@@&&&&&&@G  J@@@@&7  ^B@@@@5: .&@@@@&##@@@@@B:  B@@@@@@G   ~@@@@@@@7  ^B@@@#P5G@@@@#^ ");
        System.out.println("		!@@@@&:.......   ^B@@@@J7&@@@#!   .#@@@@7  .G@@@@J  B@@@#@@@7  G@@#&@@@7  Y@@@@5^   5YYY! ");
        System.out.println("		!@@@@@P5555P7      J@@@@@@@@P:    .#@@@@Y!7J&@@@@!  B@@@YG@@# !@@@?#@@@7  :G@@@@@@&         ");
        System.out.println("		!@@@@@&&&&&@5       ~#@@@@&7      .#@@@@@@@@@@&G!   B@@@J^@@@YB@@G.#@@@7    ^?5G#&@@@@  ");
        System.out.println("		!@@@@&^......        J@@@@G       .#@@@@J~~~~^.     B@@@J P@@@@@@~ #@@@7 .???J?  .^?&@@@@~");
        System.out.println("		!@@@@&.              J@@@@G       .#@@@@!           B@@@J ^@@@@@P .#@@@7  G@@@@P!^~?@@@@B. ");
        System.out.println("		!@@@@&:              J@@@@G       .#@@@@7           B@@@J  Y@@@&^ .#@@@7  .?B&@@@@@@@&BJ. ");
        System.out.println("		.~~~~^               :~~~~^        ^~~~~.           ^~~~:  .~~~^   ^~~~.     :~!!7!!~:   ");
        System.out.println("\n");
        System.out.println("\n");
        
        
        appview.viewApp();
	}
}
