package com;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * The StudentView class represents the view for a student user. <br>
 * It implements the View interface and provides methods to display options and handle user input. <br>
 * It contains a Scanner object for user input, a Student object for the current user, and a StudentController object for controlling the actions of the student user.
 */
public class StudentView implements View{
	/**
	 * Scanner object used to read input from the console.
	 */	
	public static final Scanner sc = new Scanner(System.in);
	private Student student;
	private StudentController studentController;

	/**
	 * Constructs a new StudentView object with the given Student and StudentController objects.
	 * @param s the Student object representing the current user.
	 * @param studentcontroller the StudentController object for controlling the actions of the student user.
	 */
	public StudentView(Student s,StudentController studentcontroller) {
		this.student = s;
		this.studentController = studentcontroller;
	}

	/**
	 * Displays the options available to the student user.
	 */
	public void printDisplay() {
		System.out.println("What would you like to do ?");
		System.out.println("(1) Change password");
		System.out.println("(2) View available projects");
		System.out.println("(3) Select a project");
		System.out.println("(4) View my project");
		System.out.println("(5) View requests and status history");
		System.out.println("(6) Request title change");
		System.out.println("(7) Request to deregister FYP");
		System.out.println("(8) Log out");
	}

	/**
	 * Displays the options available to the student user.
	 */
	public void viewApp() {
		int choice, projectId = 0;
		do {
			this.printDisplay();
			System.out.println("Enter your choice: ");
			choice = Helper.readInt();
			switch (choice) {
			case 1:
				studentController = new StudentController(student);
				studentController.changePw(student);
				choice = 8;
				break;
			case 2:
				if(studentController.getStudent().getProject() == null) {
					ProjectController.viewAvailableProject();
				}
				else if (studentController.getStudent().getProject().getProjectStatus().equals(ProjectStatus.ALLOCATED)) { 
					System.out.println("You are currently allocated to a FYP and do not have access to available project list."); 
				} 
				else if(studentController.getStudent().isDeregisteredFYP()) {
					System.out.println("You are not allowed to make selection again as you deregistered your FYP.");
				}
				break;

			case 3: 
				if(studentController.getStudent().isDeregisteredFYP()) {
					System.out.println("You are not allowed to make selection again as you deregistered your FYP.");
				}
				else {
					int anyReq = 0, hasProject = 0;  
					for (StuToFYPReq r : ExcelData.stutofypreqDatabase.values()) {  
						if (r.getRequestStatus().equals(RequestStatus.PENDING)&& r.getStudent().equals(student)) {  
							anyReq++;  
						}  
						else if (r.getStudent().equals(student) && r.getStudent().getProject() != null) { 
							hasProject = 1; 
						} 
					}  
					try { 
						if (anyReq != 0 ) {  
							throw new Exception("Request pending! Cannot select project!");  
						} 
						if (hasProject == 1) { 
							throw new Exception("You currently have a project! Cannot select project!"); 
						} 
						ProjectController.viewAvailableProject();  
						System.out.println("Please enter project id to select a project!");  
						projectId = Helper.readInt();
						if(ExcelData.projectDatabase.containsKey(projectId)) {
							studentController.selectProject(projectId);
							StuToFYPReq alloReq = new StuToFYPReq(ExcelData.projectDatabase.get(projectId), RequestType.ALLOCATEPROJECT);  
							alloReq.setStudent(student);  
							alloReq.setFyp(ExcelData.FYPDatabase.get("ASFLI"));  
							alloReq.setProject(ExcelData.projectDatabase.get(projectId));  
							student.setStuToFYPReq(alloReq);  
							ExcelData.stutofypreqDatabase.put(ExcelData.stutofypreqDatabase.size(), alloReq);
							System.out.println("Your project is now reserved and waiting to be approved by the fyp coordinator \n");
						}
						else {
							throw new Exception("Invalid projectId.");
						}
						  
					} 
					catch (Exception e){ 
						System.out.println(e.getMessage()); 
					} 
				}
				break;
			case 4:
				ProjectController.viewRegisteredProject(student);
				break;

			case 5:
				studentController.viewReqHis(); 
				break;
			case 6: 
				try { 
					if (studentController.getStudent().getProject() == null && studentController.getStudent().getStuToFYPReq() == null) { 
						throw new Exception ("You need to first select a project!"); 
					} 
					else if (studentController.getStudent().getStuToFYPReq().getRequestStatus() == RequestStatus.PENDING){ 
						throw new Exception ("Your current project is pending approval!"); 
					} 
					else if (studentController.getStudent().getStuToSupReq() != null && studentController.getStudent().getStuToSupReq().getRequestStatus() == RequestStatus.PENDING) {
						throw new Exception ("You have already requested a title change and it is pending approval!");
					}
					System.out.println("Please enter the new title for your project:"); 
					String nTitle = Helper.readString(); 
					StuToSupReq changeTitleReq = new StuToSupReq(student.getProject(), RequestType.CHANGETITLE, nTitle, student.getProject().getprojectTitle()); 
					changeTitleReq.setStudent(student); 
					changeTitleReq.setSupervisor(student.getProject().getSupervisor()); 
					student.setStuToSupReq(changeTitleReq); 
					ExcelData.stutosupreqDatabase.put(ExcelData.stutosupreqDatabase.size(), changeTitleReq); 
				} 
				catch(Exception e) { 
					System.out.println(e.getMessage()); 
				} 
				break;
			case 7: 
				try { 
					if (studentController.getStudent().getProject() == null && studentController.getStudent().getStuToFYPReq() == null) { 
						throw new Exception ("You need to first select a project!"); 
					} 
					else if (studentController.getStudent().getStuToFYPReq().getRequestStatus() == RequestStatus.PENDING){ 
						throw new Exception ("Your current project is pending approval!"); 
					} 

					System.out.println("Please type (y) to confirm that you want to deregister:  " + student.getProject().getprojectTitle()); 
					System.out.println("Or press any other button to go back"); 
					char confirm = Helper.readChar(); 
					if (confirm == 'y') { 
						int deProject = student.getProject().getprojectId(); 
						StuToFYPReq deregReq = new StuToFYPReq(ExcelData.projectDatabase.get(deProject), RequestType.DEREGISTERPROJECT); 
						deregReq.setStudent(student); 
						deregReq.setFyp(ExcelData.FYPDatabase.get("ASFLI")); 
						deregReq.setProject(ExcelData.projectDatabase.get(deProject));  
						student.setStuToFYPReq(deregReq); 
						ExcelData.stutofypreqDatabase.put(ExcelData.stutofypreqDatabase.size(), deregReq); 
						System.out.println("Your request has been processed and waiting for the FYP coordinator to approve!"); 
					} 
				} 
				catch(Exception e) { 
					System.out.println(e.getMessage()); 
				} 
				break;
			case 8:
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
		}while (choice != 8);
	}
}
