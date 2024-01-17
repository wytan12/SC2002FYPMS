package com;
import java.util.Scanner;

/**
 * The AppView class is responsible for displaying the main menu of the FYPMS application and handling user input.
 * It implements the View interface and contains references to the StudentView, SupervisorView, FYPView, Student, 
 * Supervisor, FYP, StudentController, SupervisorController, and FYPController classes.
 */

public class AppView implements View{

	private StudentView studentView;
	private SupervisorView supervisorView;
	private FYPView fypView; 
	private Student student;
	private StudentController studentController;
	private Supervisor supervisor;
	private SupervisorController supervisorController;
	private FYP fyp; 
	private FYPController fypController; 
	String userIdInput;
	boolean authenticated = false;

	/**
	 * Constructor for the AppView class.
	 */
	public AppView(){
	}

	/**
	 * Displays the main menu of the FYPMS application and handles user input.
	 */
	public void viewApp() {
		String userinput;

		while (true) {
			printDisplay();
			userinput = Helper.readString();

			switch (userinput) {
			case "s": 
				int isWrong = 0; // Variable check if password is wrong 
				authenticated = false; 
				while (!authenticated) { 
					System.out.println("Please enter your userId:"); 
					userIdInput = Helper.readString(); 
					studentController = new StudentController(ExcelData.studentDatabase.get(userIdInput)); 
					student = ExcelData.studentDatabase.get(userIdInput); 
					studentView = new StudentView(this.student, studentController); 


					if (studentController.login(userIdInput)) { 
						System.out.println("Please enter your password:"); 
						String password = Helper.readString(); 
						if (studentController.authenticate(password)) 
							authenticated = true; 
						else { 
							System.out.println("Wrong password. Please try again."); 
							isWrong = 1; 
							break; 
						} 
					}  
					else { 
						System.out.println("Invalid user ID. Please try again."); 
						isWrong = 1; 
						break; 
					} 
				} 
				if (isWrong == 1) break; 
				System.out.println("Log in successful!\n"); 
				studentView.viewApp(); 
				break;
			case "f": 
				int isWrongSup  = 0; 
				authenticated = false; 
				while (!authenticated) { 
					System.out.println("Please enter your userId:"); 
					userIdInput = Helper.readString(); 

					if (userIdInput.equals("ASFLI")) { 
						fypController = new FYPController(ExcelData.FYPDatabase.get(userIdInput)); 
						fyp = ExcelData.FYPDatabase.get(userIdInput); 
						fypView = new FYPView(this.fyp, fypController); 
					} 
					else { 
						supervisorController = new SupervisorController(ExcelData.supervisorDatabase.get(userIdInput)); 
						supervisor = ExcelData.supervisorDatabase.get(userIdInput); 
						supervisorView = new SupervisorView(this.supervisor, supervisorController); 
					} 

					if (supervisorController != null && supervisorController.login(userIdInput)) { 
						System.out.println("Please enter your password:"); 
						String password = Helper.readString(); 
						if (supervisorController.authenticate(password)) 
							authenticated = true; 
						else { 
							System.out.println("Wrong password. Please try again."); 
							isWrongSup = 1; 
							break; 
						} 
					} 
					else if (fypController != null && fypController.login(userIdInput)) { 
						System.out.println("Please enter your password:"); 
						String password = Helper.readString(); 
						if (fypController.authenticate(password)) 
							authenticated = true; 
						else { 
							System.out.println("Wrong password. Please try again."); 
							isWrongSup = 1; 
							break; 
						} 
					} 
					else { 
						System.out.println("Invalid user ID. Please try again."); 
						isWrongSup = 1; 
						break; 
					} 
				} 

				if (isWrongSup == 1) break; 

				if(userIdInput.equals("ASFLI")) { 
					System.out.println("Log in successful!\n"); 
					fypView.viewApp(); 
				} 
				else { 
					System.out.println("Log in successful!\n"); 
					supervisorView.viewApp(); 
				} 

				break;

			}

		}
	}

	/**
	 * Displays the main menu options for the FYPMS application.
	 */
	@Override
	public void printDisplay() {
		System.out.println("Please enter: ");
		System.out.println("(s) if you are a student");
		System.out.println("(f) if you are a faculty member");
	}
}

