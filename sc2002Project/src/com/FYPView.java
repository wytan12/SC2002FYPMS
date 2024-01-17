package com;

import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


/**
 * The FYPView class is responsible for displaying the options available for FYPs and handling user input related to FYPs. <br>
 * It implements the View interface and contains a reference to the FYP and the FYPController class.
 * 
 */
public class FYPView implements View{

	/**
	 * Scanner object used to read input from the console.
	 */
	public static final Scanner sc = new Scanner(System.in);
	/**
	 *The ANSI_RED constant is a string representation of the ANSI escape code for setting the text color to red in a terminal.
	*/
	public static final String ANSI_RED = "\u001B[31m";
	/**
	 * The ANSI_RESET constant is a string representation of the ANSI escape code for resetting the text color in a terminal.
	*/
	public static final String ANSI_RESET = "\u001B[0m";
	private FYP fyp;
	private FYPController fypController;
	private RequestController requestController;
	private SupervisorController supervisorController;

	/**
	 * Creates a new FYPView object with the specified FYP object.
	 * @param f the FYP object associated with this FYPView object
	 */
	public FYPView(FYP f, FYPController fypController) {
		this.fyp = f;
		this.fypController = fypController;
		this.supervisorController = new SupervisorController(fyp);
	}

	/**
	 * Displays the available options for FYPs.
	 */
	public void printDisplay() { 
		int pending = 0;
		System.out.println("What would you like to do ?"); 
		System.out.println("(1) Change password"); 
		System.out.println("(2) Create/Update/View projects");
		System.out.println("(3) View all projects");  
		for (Request r : ExcelData.stutofypreqDatabase.values()) {
			if (r.getRequestStatus() == RequestStatus.PENDING)
				pending++;
		}
		for (Request r : ExcelData.suptofypreqDatabase.values()) {
			if (r.getRequestStatus() == RequestStatus.PENDING)
				pending++;
		}
		if (pending > 0) 
			System.out.println("(4) View pending requests " + ANSI_RED + pending + " NEW REQUEST!!!" + ANSI_RESET);
		else 
			System.out.println("(4) View pending requests");
		System.out.println("(5) View requests and status history"); 
		System.out.println("(6) Approve/reject requests"); 
		System.out.println("(7) Log out"); 
	}

	/**
	 * Displays the FYPView and handles user input for FYP-related options until the user chooses to log out.
	 */
	public void viewApp() {
		int choice;
		do {
			this.printDisplay();
			System.out.println("Enter your choice: ");
			choice = Helper.readInt();
			switch (choice) {
			case 1: 
				fypController = new FYPController(fyp);
				fypController.changePassword(fyp);
				choice = 7;
				break;
			case 2:
				System.out.println("Create/Update/View projects");
				System.out.println("Please type: ");
				System.out.println(" (C) to create project");
				System.out.println(" (U) to update project");
				System.out.println(" (V) to view project");
				try { 
					char action = sc.next().charAt(0); 
					if (action != 'C' && action != 'c' && action != 'U' && action != 'u' && action !='v' && action != 'V') { 
						System.out.println( 
								"Error! Choice must be C/U/V."); 
					}  

					if (action == 'C' || action == 'c') {  
						supervisorController.createProject(fyp); 
					} 
					else if (action == 'U' || action == 'u') { 
						System.out.println("Enter the projectId: ");
						int projectId = Helper.readInt();
						supervisorController.changeTitle(projectId,fyp); 
					} 
					else if (action == 'V' || action == 'v') { 
						supervisorController.viewSupervisorProject(fyp); 
					} 
				}
				catch (Exception e){ 
					System.out.println(e.getMessage()); 
				}
				break;
			case 3: 
				System.out.println("View projects : "); 
				System.out.println("(1) Status"); 
				System.out.println("(2) Supervisor"); 
				int opt = Helper.readInt();
				if(opt == 1) {
					System.out.println("Filter by status (enter 'all' to view all projects):");
					String statusFilter = Helper.readString().toUpperCase();
					ProjectController.viewProjectByStatus(statusFilter);
				}
				else if(opt == 2) {
					System.out.println("Filter by supervisor ID (enter 'all' to view all supervisors' projects):");
					String supervisorFilter = Helper.readString().toUpperCase();
					ProjectController.viewProjectBySupervisor(supervisorFilter);
				}
				else {
					System.out.println("Invalid Choice");
				}
				break;  
			case 4:
				// Display error message if no pending req
				fypController.viewReqPending(); 
				break;
			case 5:  
				// Display error message if no req history
				fypController.viewReqHis();
				break; 
			case 6:  
				HashMap<Student, Request> stureqDatabase = new HashMap<Student, Request>();
				HashMap<Supervisor, Request> supreqDatabase = new HashMap<Supervisor, Request>();
				int hasPending = 0; 
				for (StuToFYPReq r : ExcelData.stutofypreqDatabase.values()) { 
					if (r.getRequestStatus().equals(RequestStatus.PENDING)) { 
						hasPending += 1; 
						Student stu = r.getStudent();
						stureqDatabase.put(stu, r);
					} 
				} 
				for (SupToFYPReq r : ExcelData.suptofypreqDatabase.values()) { 
					if (r.getRequestStatus().equals(RequestStatus.PENDING)) { 
						hasPending += 1; 
						Supervisor sup = r.getSupervisor();
						supreqDatabase.put(sup, r);
					} 
				} 
			     
			    try { 
			    	if (hasPending == 0)  
			    		throw new Exception("No pending requests!"); 

			    	fypController.viewReqPending(); 
			    	System.out.println("Enter the SenderId to choose request");  
			    	String id = Helper.readString().toUpperCase(); 
			    	if(stureqDatabase.containsKey(ExcelData.studentDatabase.get(id)))
			    		requestController = new RequestController(ExcelData.studentDatabase.get(id).getStuToFYPReq()); 
			    	else if(supreqDatabase.containsKey(ExcelData.supervisorDatabase.get(id))) 
			    		requestController = new RequestController(ExcelData.supervisorDatabase.get(id).getSupToFYPReq()); 
			    	else 
			    		throw new Exception("You have enter the wrong SenderId!");
			    	System.out.println("Enter (y) to approve or enter (n) to reject this request"); 
			    	Helper.replyYesOrNo(requestController); 
			    } 
			    catch (Exception e) { 
			    	System.out.println(e.getMessage());  
			    } 
			    stureqDatabase.clear();
			    supreqDatabase.clear();
			    break;
			case 7:
				try {
					ExcelData.exportExcel_student();
					ExcelData.exportExcel_supervisor();
					ExcelData.exportExcel_project();
					ExcelData.exportExcel_StuToFYPReq();
					ExcelData.exportExcel_StuToSupReq();
					ExcelData.exportExcel_SupToFYPReq();
				} catch (InvalidFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default: 
				System.out.println("Invalid Choice");
			} 
		}while (choice != 7);


	}
}