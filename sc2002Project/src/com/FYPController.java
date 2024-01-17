package com;

import java.util.Scanner;

/**
 * The FYPController class represents the controller responsible for handling FYP supervisor related operations
 */
public class FYPController implements UserController{

	/**
	 * Scanner object used to read input from the console.
	 */
	public static final Scanner sc = new Scanner(System.in);
	private static FYP fyp;

	/**
	 * Constructor for the FYPController class.
	 * @param f An FYP object representing the FYP supervisor.
	 */
	public FYPController(FYP f) {
		this.fyp = f;
	}

	/**
	 * Method to log in the FYP supervisor with the given ID.
	 * @param id A string representing the ID of the supervisor.
	 * @return A boolean value indicating if the login was successful or not.
	 */
	public boolean login(String id) {
		if(ExcelData.FYPDatabase.containsKey(id)) {
			return true;
		}
		else {
			return false;
		}  
	}

	/**
	 * Method to authenticate the FYP supervisor with the given password.
	 * @param password A string representing the password of the supervisor.
	 * @return A boolean value indicating if the authentication was successful or not.
	 */
	public boolean authenticate(String password) {
		if(fyp.getPassword().equals(password)) {
			return true;
		}
		else 
			return false;
	}

	/**
	 * Method to retrieve the FYP supervisor object.
	 * @return An FYP object representing the FYP supervisor.
	 */
	public static FYP getFYP() {
		return fyp;
	}

	public void setFYP(FYP f) {
		this.fyp = f;
	}

	/**
	 * Method to change the password for the given student.
	 * @param student A Student object representing the student whose password needs to be changed.
	 */
	//Need to change using downcast upcast stuff
	public void changePassword(FYP fyp) {
		System.out.println("Enter your new password: ");
		String newPassword = sc.nextLine();
		if(fyp != null)
			fyp.setPassword(newPassword);
		System.out.println("Password changed successfully."); 
	}

	/**
	 * This method approves the student's request to register for the project.
	 * 
	 * @param projectId the project ID
	 * @param student the student object
	 */
	public void allocateProject(int projectId, Student student) { //approve request for student register
		if(ExcelData.projectDatabase.containsKey(projectId)){
			Project project = ExcelData.projectDatabase.get(projectId);
			//check if the student has a project 
			if(student.getProject() != null && student.getProject().projectStatus.equals(ProjectStatus.ALLOCATED)) {
				System.out.println("This student already has a project.");
			}
			
			// Check if the project has already been allocated to another student
			if (project.getSupervisor().getNumOfSupervisedProjects() == 2) {
				System.out.println("This supervisor already has two project.");
				return;
			}
			if (project.getProjectStatus() == ProjectStatus.ALLOCATED) {
				System.out.println("Project has already been allocated to another student.");
				return;
			}
			// check if supervisor has supervise two projects
			project.setProjectId(projectId);
			project.setProjectStatus(ProjectStatus.ALLOCATED);
			project.setStudent(student);
			project.getStudent().setProject(ExcelData.projectDatabase.get(projectId));
			project.getSupervisor().increaseNumOfSupervisedProjects();
			
			if(project.getSupervisor().getNumOfSupervisedProjects() == 2) {
				Supervisor sup = project.getSupervisor();
				for (Project p : ExcelData.projectDatabase.values()) {
					if (p.getSupervisor().equals(sup) && !p.getProjectStatus().equals(ProjectStatus.ALLOCATED)) {
						p.setProjectStatus(ProjectStatus.UNAVAILABLE);
					}
				}
			}
		}
		else {
			System.out.println("Invalid Project ID.");
		}
	}

	public void changeSupervisor(int projectID, String supId) { 
	    Supervisor replacement = ExcelData.supervisorDatabase.get(supId); 
	    for (Project p : ExcelData.projectDatabase.values()) { 
	      if (p.getprojectId() == projectID) { 
	        p.getSupervisor().decreaseNumOfSupervisedProjects();
	        Supervisor sup = p.getSupervisor();
	        if (sup.getNumOfSupervisedProjects() < 2) {
	          for (Project pr : ExcelData.projectDatabase.values()) {
	            if (pr.getProjectStatus().equals(ProjectStatus.UNAVAILABLE) && pr.getSupervisor().equals(sup)) {
	              pr.setProjectStatus(ProjectStatus.AVAILABLE);
	            }
	          }
	        }
	        p.setSupervisor(replacement);
	        p.getSupervisor().increaseNumOfSupervisedProjects();
	        if(replacement.getNumOfSupervisedProjects() >=2) {
	          for (Project pr : ExcelData.projectDatabase.values()) {
	            if (pr.getProjectStatus().equals(ProjectStatus.AVAILABLE) && pr.getSupervisor().equals(replacement)) {
	              pr.setProjectStatus(ProjectStatus.UNAVAILABLE);
	            }
	          }
	        }
	        ExcelData.projectDatabase.put(projectID, p); 
	      } 
	    } 
	    
	  }

	public void deallocateProject(int projectId) { //view request before deallocate   
		if(ExcelData.projectDatabase.containsKey(projectId)){ 
			Project project = ExcelData.projectDatabase.get(projectId); 
			if (project.getProjectStatus() != ProjectStatus.ALLOCATED) { 
				System.out.println("Cannot deregister project that is not allocated!"); 
			} 
			else if (project.getProjectStatus() == ProjectStatus.ALLOCATED) { 
				Supervisor sup = project.getSupervisor();
				for(Project p : ExcelData.projectDatabase.values()) {
					if(p.getSupervisor().equals(sup) && p.getProjectStatus().equals(ProjectStatus.UNAVAILABLE)) {
						p.setProjectStatus(ProjectStatus.AVAILABLE);
					}
				}
				project.setProjectStatus(ProjectStatus.AVAILABLE); 
				project.getStudent().setDeregisteredFYP(true);
				project.setStudent(null); 
				project.getSupervisor().decreaseNumOfSupervisedProjects();
			} 
		} 
		else { 
			System.out.println("Invalid Project ID."); 
		} 
	}

	//functions for FYP

	public void viewReqHis() {
		int i = 1, hasReq = 0;	
		System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
	    System.out.println("| Request ID |                                             Request Type                                              |      Sender     |     Receiver    |  Request Status |");
	    System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		for (StuToFYPReq r : ExcelData.stutofypreqDatabase.values()) {
			if (!r.getRequestStatus().equals(RequestStatus.PENDING)) {
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n",
						i, r.getRequestType() + " " + r.getProject().getprojectTitle() + " To " + r.getStudent().getName(), r.getStudent().getUserId(), r.getFyp().getUserId(), r.getRequestStatus());
				i++;
				hasReq++;
			}
		}
		for (SupToFYPReq r : ExcelData.suptofypreqDatabase.values()) {
			if (!r.getRequestStatus().equals(RequestStatus.PENDING)) {
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n",
						i, r.getRequestType() + " From " + r.getSupervisor().getUserId() + " To " +r.getNewSup().getUserId(), r.getSupervisor().getUserId(), r.getFyp().getUserId(), r.getRequestStatus());
				i++;
				hasReq++;
			}
		}
		for (StuToSupReq r : ExcelData.stutosupreqDatabase.values()) {
			if (!r.getRequestStatus().equals(RequestStatus.PENDING)) {
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n",
						i, r.getRequestType()+ " From " + r.getOldTitle() + " To " + r.getNewTitle(), r.getStudent().getUserId(), r.getSupervisor().getUserId(), r.getRequestStatus());
				i++;
				hasReq++;
			}
		}
		if (hasReq == 0) System.out.printf("| %s%-90s |%n", " ".repeat(45), "No request history!");
		System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
	}

	/**
	 * This method displays all pending requests in a formatted table.
	 */
	public void viewReqPending() {	
		int i = 1, hasPending = 0;
		System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
	    System.out.println("| Request ID |                                             Request Type                                              |      Sender     |     Receiver    |  Request Status |");
	    System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");
		for (StuToFYPReq r : ExcelData.stutofypreqDatabase.values()) {
			if (r.getRequestStatus().equals(RequestStatus.PENDING)) {
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n",
						i, r.getRequestType() + " " + r.getProject().getprojectTitle() + " To " + r.getStudent().getName(), r.getStudent().getUserId(), r.getFyp().getUserId(), r.getRequestStatus());
				i++;
				hasPending++;
			}
		}
		for (SupToFYPReq r : ExcelData.suptofypreqDatabase.values()) {
			if (r.getRequestStatus().equals(RequestStatus.PENDING)) {
				System.out.printf("| %-10s | %-101s | %-15s | %-15s | %-15s |%n",
						i, r.getRequestType() + " " + r.getProject().getStudent().getName() + " To " + r.getNewSup().getName(), r.getSupervisor().getUserId(), r.getFyp().getUserId(), r.getRequestStatus());
				i++;
				hasPending++;
			}
		}
		if (hasPending == 0) System.out.printf("| %s%-90s |%n", " ".repeat(45), "No pending requests!");
		System.out.println("+------------+-------------------------------------------------------------------------------------------------------+-----------------+-----------------+-----------------+");

	}

}
