package com;
import java.util.Scanner;


/**
 * A class representing a Supervisor Controller that implements the UserController interface.
 * It includes methods to login, authenticate, allocate project, change project title, create a project, and view supervisor projects.
 */
public class SupervisorController implements UserController{
	/**
	 * Scanner object used to read input from the console.
	 */	
	public static final Scanner sc = new Scanner(System.in);
	private static Supervisor supervisor;

	/**
	 * Constructs a SupervisorController object with a Supervisor object.
	 * @param s the Supervisor object
	 */
	public SupervisorController(Supervisor s) {
		this.supervisor = s;
	}

	/**
	 * This method logs in the supervisor using their ID.
	 * 
	 * @param id the supervisor ID
	 * @return true if the ID is in the supervisor database; otherwise false
	 */
	public boolean login(String id) {
		if(ExcelData.supervisorDatabase.containsKey(id)) {
			supervisor = ExcelData.supervisorDatabase.get(id);
			return true;
		}
		else {
			return false;
		}
	} 

	/**
	 * This method authenticates the supervisor using their password.
	 * 
	 * @param password the supervisor password
	 * @return true if the password matches the supervisor password; otherwise false
	 */
	public boolean authenticate(String password) {
		if(supervisor.getPassword().equals(password)) {
			return true;
		}
		else 
			return false;
	}

	/**
	 * This method gets the supervisor object.
	 * 
	 * @return the supervisor object
	 */
	public static Supervisor getSupervisor() {
		return supervisor;
	}

	/**
	 * This method sets the supervisor object.
	 * 
	 * @param s the supervisor object
	 */
	public void setSupervisor(Supervisor s) {
		this.supervisor = s;
	}

	/**
	 * This method changes the supervisor's password.
	 * 
	 * @param supervisor the supervisor object
	 */
	public void changePw(Supervisor supervisor) {
		System.out.println("Enter your new password: ");
		String newPassword = sc.nextLine();
		supervisor.setPassword(newPassword); 
		System.out.println("Password changed successfully."); 
	}

	/**
	 * This method changes the project's title upon the student's request.
	 * 
	 * @param projectId the project ID
	 */
	public void changeTitle(int projectId, Supervisor supervisor) { //student request 
		if(ExcelData.projectDatabase.containsKey(projectId)){
			Project project = ExcelData.projectDatabase.get(projectId);
			if(project.getSupervisor().getName().equals(supervisor.getName())) {
				System.out.println("Enter the new project Title:");
				String projectTitle = sc.nextLine();
				project.setProjectTitle(projectTitle);
				ExcelData.projectDatabase.put(projectId, project);
			}
			else {
				System.out.println("You are not allowed to modify the title.");
			}

		}
	}

	/**
	 * This method creates a new project.
	 * 
	 * @param supr the supervisor object
	 */
	public void createProject(Supervisor supr) {
		int hashMapSize = ExcelData.projectDatabase.size();  
		System.out.println("Enter project title:"); 
		String projecttitle = sc.nextLine(); 
		Project newproject = new Project(hashMapSize+1, projecttitle, supr ); 
		newproject.setProjectTitle(projecttitle);
		newproject.setSupervisor(supr);
		ExcelData.projectDatabase.put(hashMapSize + 1, newproject);
		if(supr.getNumOfSupervisedProjects() == 2) {
			newproject.setProjectStatus(ProjectStatus.UNAVAILABLE);
		}
		else {
			newproject.setProjectStatus(ProjectStatus.AVAILABLE);
		}
	}

	/**
	 * This method views the projects supervised by a given supervisor
	 * 
	 * @param supvr A Supervisor object representing the supervisor whose projects are to be viewed.
	 */
	public void viewSupervisorProject(Supervisor supvr) { 
		boolean a = false;
		for (Project p : ExcelData.projectDatabase.values()) {
			if (p.getSupervisor().equals(supvr)) {
				a = true;
			}
		}
		if (a == false) { 
			System.out.println("You have no project!");
			return; 
		}
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
		System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Supervisor Email     |     Student Name     |     Student Email     | Project Status|");
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
		for (Project p : ExcelData.projectDatabase.values()) {
			if (p.getSupervisor().equals(supvr)) {
				if(p.getStudent()== null) {
					System.out.printf("| %-10s | %-83s | %-23s |%-23s | %-23s | %-23s | %-13s |%n",
							p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(),p.getSupervisor().getUserId()+"@NTU.EDU.SG","","", p.getProjectStatus());
				}
				else {
					System.out.printf("| %-10s | %-83s | %-23s |%-23s | %-23s | %-23s | %-13s |%n",
							p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(),p.getSupervisor().getUserId()+"@NTU.EDU.SG",p.getStudent().getName(),p.getStudent().getUserId()+"@e.ntu.edu.sg", p.getProjectStatus());
				}
			}
		}
		System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
	}

	public void viewReqHis() { 
		  int i = 1, hasRequest = 0;  
		  	System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		    System.out.println("| Request ID |                                             Request Type                                              |      Sender     |     Receiver    |  Request Status |");
		    System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		  for (StuToSupReq r : ExcelData.stutosupreqDatabase.values()) { 
		   if (!r.getRequestStatus().equals(RequestStatus.PENDING) && r.getSupervisor().equals(supervisor)) { 
		    System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n", 
		      i, r.getRequestType()+ " From " + r.getProject().getprojectTitle() + " To " + r.getNewTitle(), r.getStudent().getUserId(), r.getSupervisor().getUserId(), r.getRequestStatus()); 
		    i++; 
		    hasRequest++; 
		   } 
		  } 
		  for (SupToFYPReq r : ExcelData.suptofypreqDatabase.values()) { 
		   if (!r.getRequestStatus().equals(RequestStatus.PENDING) && r.getSupervisor().equals(supervisor)) { 
			   System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n"
					   , i, r.getRequestType() + " From " + r.getSupervisor().getUserId() + " To " +r.getNewSup().getUserId(), r.getSupervisor().getUserId(), r.getFyp().getUserId(), r.getRequestStatus()); 
		    i++; 
		    hasRequest++; 
		   } 
		  } 
		  if (hasRequest == 0) System.out.printf("| %s%-90s |%n", " ".repeat(45), "No request history!"); 
		  System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		 }

	public void viewReqPending() {  
		  int i = 1, hasPending = 0; 
		  	System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		    System.out.println("| Request ID |                                             Request Type                                              |      Sender     |     Receiver    |  Request Status |");
		    System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		  for (StuToSupReq r : ExcelData.stutosupreqDatabase.values()) { 
		   if (r.getRequestStatus().equals(RequestStatus.PENDING) && r.getSupervisor().equals(supervisor)) { 
			   System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n", 
		      i, r.getRequestType()+ " From " + r.getProject().getprojectTitle() + " To " + r.getNewTitle(), r.getStudent().getUserId(), r.getSupervisor().getUserId(), r.getRequestStatus()); 
		    i++; 
		    hasPending++; 
		   } 
		  } 
		  if (hasPending == 0) System.out.printf("| %s%-90s |%n", " ".repeat(45), "No pending requests!"); 
		  System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		 
		 }
	
	
	
}
