package com;

/**
 * The ProjectController class manages the functionality related to projects.<br>
 * Functionality includes viewing, creating, assigning and deregistering projects
 *
 */
public class ProjectController {
	private static Project p;
    private ExcelData projectDatabase;

    /**
     * Constructs a new instance of ProjectController
     *
     */
    public ProjectController() {}
   
    /**
     * Displays information about <u>ALL</u> projects in the project database.
     *
     */
    public static void viewProject() {
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+");
        System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     | Project Status|");
        System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+");
        for (Project p : ExcelData.projectDatabase.values()) {
            System.out.printf("| %-10s | %-83s | %-23s | %-13s |%n",
                              p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(), p.getProjectStatus());
        }
        System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+---------------+");
	}
    
    public static void viewProjectByStatus(String statusFilter) {
    	boolean a = false;
    	for (Project p : ExcelData.projectDatabase.values()) {
    		if (statusFilter == null || statusFilter.equals("") || statusFilter.equalsIgnoreCase("all") || p.getProjectStatus().toString().equalsIgnoreCase(statusFilter)) {
    			a = true;
    		}
    	}
    	if (a == false) { 
			System.out.println("There is no project with this status!");
			return; 
		}
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    	System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Supervisor Email     |     Student Name     |     Student Email     | Project Status|");
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    	for (Project p : ExcelData.projectDatabase.values()) {
    		if (statusFilter == null || statusFilter.equals("") || statusFilter.equalsIgnoreCase("all") || p.getProjectStatus().toString().equalsIgnoreCase(statusFilter)) {
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
    
    public static void viewProjectBySupervisor(String supervisorFilter) {
    	boolean a = false;
    	for (Project p : ExcelData.projectDatabase.values()) {
    		if (supervisorFilter == null || supervisorFilter.equals("") || supervisorFilter.equalsIgnoreCase("all") || p.getSupervisor().getUserId().equalsIgnoreCase(supervisorFilter)) {
    			a = true;
    		}
    	}
    	if (a == false) { 
    		System.out.println("This supervisor has no project!");
    		return; 
    	}
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    	System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Supervisor Email     |     Student Name     |     Student Email     | Project Status|");
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    	for (Project p : ExcelData.projectDatabase.values()) {
    		if (supervisorFilter == null || supervisorFilter.equals("") || supervisorFilter.equalsIgnoreCase("all") || p.getSupervisor().getUserId().equalsIgnoreCase(supervisorFilter)) {
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
    
    /**
     * Displays information about <u>AVILABLE</u> projects in the project database.
     *
     */ 
    public static void viewAvailableProject() {
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+---------------+");
    	System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Supervisor Email     | Project Status|");
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+---------------+");
        for (Project p : ExcelData.projectDatabase.values()) {
            if (p.getProjectStatus() == ProjectStatus.AVAILABLE ) {
                System.out.printf("| %-10s | %-83s | %-23s | %-23s | %-13s |%n",
                        p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(),p.getSupervisor().getUserId()+"@NTU.EDU.SG", p.getProjectStatus());
            }
        }
        System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+---------------+");
    }
    
    /**
     * Displays information about <u>ALLOCATED</u> projects in the project database.
     *
     */
    public static void viewAllocatedProject() {
    	boolean a = false;
    	for (Project p : ExcelData.projectDatabase.values()) {
    		if (p.getProjectStatus() == ProjectStatus.ALLOCATED) {
    			a = true;
    		}
    	}
    	if (a == false) { 
			System.out.println("There is no project allocated!");
			return; 
		}
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    	System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Supervisor Email     |     Student Name     |     Student Email     | Project Status|");
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
        for (Project p : ExcelData.projectDatabase.values()) {
        	if (p.getProjectStatus() == ProjectStatus.ALLOCATED) {
        		System.out.printf("| %-10s | %-83s | %-23s | %-23s | %-23s | %-23s | %-13s |%n",
                              p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(),p.getSupervisor().getUserId()+"@NTU.EDU.SG",p.getStudent().getName(),p.getStudent().getUserId()+"@e.ntu.edu.sg", p.getProjectStatus());
        	}
        }
        System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+--------------------------+----------------------+-----------------------+---------------+");
    }
    
    /**
     * Displays information about a specific project that a student has registered for.
     * @param student The student who has registered for the project.
     * @param projectId The ID of the project to view.
     *
     */
    public static void viewRegisteredProject(Student student) {
    	boolean a = false;
    	for (Project p : ExcelData.projectDatabase.values()) {
    		Student projectStudent = p.getStudent();
        	if (projectStudent != null && projectStudent.equals(student)) {
    			a = true;
    		}
    	}
    	if (a == false) {
			System.out.println("You have not selected a project!");
			return; 
		}
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+----------------------+---------------+");
    	System.out.println("| Project ID |                                    Project Title                                    |     Supervisor Name     |     Student Name     | Project Status|");
    	System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+----------------------+---------------+");
        for (Project p : ExcelData.projectDatabase.values()) {
        	Student projectStudent = p.getStudent();
        	if (projectStudent != null && projectStudent.equals(student)) {
        		System.out.printf("| %-10s | %-83s | %-23s | %-20s | %-13s |%n",
        				p.getprojectId(), p.getprojectTitle(), p.getSupervisor().getName(), student.getName(),p.getProjectStatus());
        	}
        }
        System.out.println("+------------+-------------------------------------------------------------------------------------+-------------------------+----------------------+---------------+");
    }
}

