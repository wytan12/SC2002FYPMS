package com;

import java.util.Scanner;


/**
 * The StudentController class implements the UserController interface 
 * and provides methods to log in a student, authenticate password, change password,
 * and select a project.
*/
public class StudentController implements UserController{
	
	/**
	 * Scanner object used to read input from the console.
	 */	
	public static final Scanner sc = new Scanner(System.in);
	private static Student student;
  
	/**
	* Constructor for the StudentController class.
	* @param s the student object to be associated with this controller.
	*/
	public StudentController(Student s){
		this.student = s;
	}

	/**
	* Logs in a student with the given id.
	* @param id the id of the student to be logged in.
	* @return true if the student is successfully logged in, false otherwise.
	*/
	public boolean login(String id) {
		if(ExcelData.studentDatabase.containsKey(id)) {
			student = ExcelData.studentDatabase.get(id);
			return true;
	    }
	    else {
    		return false;
	    }
	}
	
	/**
	* Authenticates the password of the currently logged in student.
	* @param password the password to be authenticated.
	* @return true if the password is correct, false otherwise.
	*/
	public boolean authenticate(String password) {
		if(student.getPassword().equals(password)) {
			return true;
    }
    else 
    	return false;
	}
  
	/**
	 * Returns the student object associated with this controller.
	 * @return the student object associated with this controller.
	*/
	public Student getStudent() {
		return student;
	}
  
	/**
	 * Sets the student object associated with this controller.
	 * @param s the student object to be associated with this controller.
	*/
	public void setStudent(Student s) {
		this.student = s;
	}
  
	/**
	* Changes the password of the currently logged in student.
	* Prompts the user to enter a new password, sets the password of the student object
	* associated with this controller, and prints a success message.
	* @param student the student whose password will be changed.
	*/
	public void changePw(Student student) {
	    System.out.println("Enter your new password: ");
	    String newPassword = sc.nextLine();
	    student.setPassword(newPassword);
	    System.out.println("Password changed successfully."); 
	}
   
	/**
	* Selects a project for the currently logged in student.
	* Sets the project status of the selected project to RESERVED, and sets the
	* student object associated with this controller as the student for the project.
	* Prints an error message if the projectId is invalid.
	* @param projectId the id of the project to be selected.
	*/
	public void selectProject(int projectId) { //request for register
		Project project = ExcelData.projectDatabase.get(projectId);
		if(project.getProjectStatus().equals(ProjectStatus.AVAILABLE)) {
			project.setProjectId(projectId);
			project.setProjectStatus(ProjectStatus.RESERVED);
			project.setStudent(student);
		}
	}
	
	public void viewReqHis() { 
		int i = 1, hasReq = 0;  
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+"); 
		System.out.println("| Request ID |                                    Request Type                                    |     Receiver     |     Sender     | Request Status|"); 
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+"); 
		for (StuToFYPReq r : ExcelData.stutofypreqDatabase.values()) { 
			if ((!r.getRequestStatus().equals(RequestStatus.PENDING)) && r.getStudent().equals(student)) { 
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n", 
						i, r.getRequestType() + " " + r.getProject().getprojectTitle() + " To " + r.getStudent().getName(), r.getStudent().getUserId(), r.getFyp().getUserId(), r.getRequestStatus()); 
				i++; 
				hasReq++;
			} 
		} 
		for (StuToSupReq r : ExcelData.stutosupreqDatabase.values()) { 
			if ((!r.getRequestStatus().equals(RequestStatus.PENDING)) && r.getStudent().equals(student)) { 
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n", 
						i, r.getRequestType(), r.getStudent().getUserId(), r.getSupervisor().getUserId(), r.getRequestStatus()); 
				i++; 
				hasReq++;
			} 
		} 
		if(hasReq == 0)System.out.println("No Request History!");
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+"); 
	}
}