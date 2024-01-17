package com;

import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


/**
 * This class implements the View interface and represents the user interface for the Supervisor role. <br>
 * It allows the supervisor to change password, view available projects, change project title, create/update/view projects,
 * view requests and status history, request title change and request to deregister FYP.
 */
public class SupervisorView implements View{
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
	private Supervisor supervisor;
	private SupervisorController supervisorController;
	private RequestController requestController;

	/**
	 * Constructor for the SupervisorView class that takes a Supervisor object and a SupervisorController object as parameters.
	 * @param s a Supervisor object
	 * @param supervisorcontroller a SupervisorController object
	 */
	public SupervisorView(Supervisor s, SupervisorController supervisorcontroller) {
		this.supervisor = s;
		this.supervisorController = supervisorcontroller;
	}

	/**
	 * This method prints the display of available actions that the supervisor can perform.
	 */
	public void printDisplay() {
		int pending = 0;
		System.out.println("What would you like to do ?");
		System.out.println("(1) Change password");
		System.out.println("(2) Create/Update/View projects");
		for (Request r : ExcelData.stutosupreqDatabase.values()) {
			if (r.getRequestStatus() == RequestStatus.PENDING && r.getProject().getSupervisor().equals(supervisor))
				pending++;
		}
		if (pending > 0) 
			System.out.println("(3) View pending requests " + ANSI_RED + pending + " NEW REQUEST!!!" + ANSI_RESET);
		else 
			System.out.println("(3) View pending requests");
		System.out.println("(4) View requests and status history");
		System.out.println("(5) Request transfer student");
		System.out.println("(6) Log out");
	}

	/**
	 * This method allows the supervisor to view and interact with the available options on the user interface.
	 */
	public void viewApp() {
		int choice;
		do {
			this.printDisplay();  
			System.out.println("Enter your choice: ");
			choice = Helper.readInt();
			switch (choice) {
			case 1:
				supervisorController = new SupervisorController(supervisor);
				supervisorController.changePw(supervisor);
				choice = 8;
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
						throw new Exception( 
								"Error! Choice must be C/U/V."); 
					}  

					if (action == 'C' || action == 'c') {  
						supervisorController.createProject(supervisor); 
					} 
					else if (action == 'U' || action == 'u') { 
						System.out.println("Enter the projectId: ");
						int projectId = Helper.readInt();
						supervisorController.changeTitle(projectId,supervisor); 
					} 
					else if (action == 'V' || action == 'v') { 
						supervisorController.viewSupervisorProject(supervisor); 
					} 
				} 
				catch (Exception e){ 
					System.out.println(e.getMessage()); 
				}
				break;
			case 3: 

				int anyReq = 0;  
				for (StuToSupReq r : ExcelData.stutosupreqDatabase.values()) {  
					if (r.getRequestStatus().equals(RequestStatus.PENDING)&& r.getSupervisor().equals(supervisor)) {  
						anyReq +=1;  
					}  
				}  
				if (anyReq > 0) {  
					supervisorController.viewReqPending();  
					System.out.println("Enter the studentId to choose request");  
					String stuID = Helper.readString().toUpperCase();
					int hasReq = 0;
					for (StuToSupReq stsReq: ExcelData.stutosupreqDatabase.values() ) {
						if (stsReq.getStudent().getUserId().equals(stuID) && stsReq.getRequestStatus().equals(RequestStatus.PENDING)) {
							hasReq = 1;
						}
					}
					if (hasReq == 0) {
						System.out.println("Invalid studentID"); 
						break;
					}
					if(!ExcelData.studentDatabase.containsKey(stuID)) { 
						System.out.println("Invalid studentID"); 
						break;
					} 
					requestController = new RequestController(ExcelData.studentDatabase.get(stuID).getStuToSupReq());  
					System.out.println("Enter (y) to approve or enter (n) to reject this request");  
					Helper.replyYesOrNo(requestController);  
				}  
				else { 
					supervisorController.viewReqPending(); 
				} 
				break;
			case 4: 
				supervisorController.viewReqHis(); 
				break;
			case 5:
				System.out.println("Enter the student's id who you want to transfer: ");
				String stu = Helper.readString().toUpperCase();
				if(!ExcelData.studentDatabase.containsKey(stu)) {
					System.out.println("You have enter the wrong student ID!");
					break;
				}
				System.out.println("Enter the supervisor's id who you want to transfer to: ");
				String newSup = Helper.readString().toUpperCase();
				if(ExcelData.supervisorDatabase.containsKey(newSup)) {
					Supervisor sup = ExcelData.supervisorDatabase.get(newSup);
					if(sup.getNumOfSupervisedProjects() >= 2) {
						System.out.println("Cannot transfer project to this supervisor!");
					}
					else {
						SupToFYPReq transferReq = new SupToFYPReq(ExcelData.studentDatabase.get(stu).getProject(), RequestType.TRANSFERSTUDENT, ExcelData.supervisorDatabase.get(newSup));
						transferReq.setSupervisor(supervisor);
						transferReq.setFyp(ExcelData.FYPDatabase.get("ASFLI"));
						transferReq.setProject(ExcelData.studentDatabase.get(stu).getProject());
						supervisor.setSupToFYPReq(transferReq);
						ExcelData.suptofypreqDatabase.put(ExcelData.suptofypreqDatabase.size(), transferReq);
					}
				}
				else {
					System.out.println("You have enter the wrong supervisor ID!");
				}
				
				break;
			case 6:
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
				System.out.println("Invalid Choice!");
			} 
		}while (choice != 6);
	}
}
