package com;

/**
 * A class representing a supervisor user in the FYP management system. <br>
 * Extends the User class.
 */
public class Supervisor extends User{
	Project project;
	StuToSupReq stuToSupReq;
	SupToFYPReq supToFypReq;
	int numOfSupervisedProjects;

	/**
	 * Constructor for creating a new Supervisor object.
	 * @param id The ID of the supervisor.
	 * @param nm The name of the supervisor.
	 */
	public Supervisor(String id, String nm) {
		super(id, nm);
	}

	/**
	 * Creates a new project with the given title and assigns the supervisor to the project.
	 * @param title The title of the project to be created.
	 * @return The new Project object that was created.
	 */
	public Project createProject(String title) {
		Project project = new Project(title);
		project.setSupervisor(this);
		return project;
	}
	
	/**
	 * Getter for project.
	 * @return The project object associated with this supervisor.
	*/
	public Project getProject() {
        return project;
    }

	/**
	 * Setter for project.
	 * @param project The project object to be associated with this supervisor.
	*/
    // Setter for project
    public void setProject(Project project) {
        this.project = project;
    }
    
    /**
     * Getter for StuToSupReq.
     * @return The request object for a student to request supervision from this supervisor.
    */
	public StuToSupReq getStuToSupReq() {
		return stuToSupReq;
	}

	/**
	 * Setter for StuToSupReq.
	 * @param stutosupreq The request object for a student to request supervision from this supervisor.
	*/
	public void setStuToSupReq(StuToSupReq stutosupreq) {
		this.stuToSupReq = stutosupreq;
	}

	/**
	 * Getter for SupToFYPReq.
	 * @return The request object for this supervisor to request a FYP from the department chair.
	*/
	public SupToFYPReq getSupToFYPReq() {
		return supToFypReq;
	}

	/**
	 * Setter for SupToFYPReq.
	 * @param suptofypreq The request object for this supervisor to request a FYP from the department chair.
	*/
	public void setSupToFYPReq(SupToFYPReq suptofypreq) {
		this.supToFypReq = suptofypreq;
	}

	/**
	 * Getter for numOfSupervisedProjects.
	 * @return The number of projects supervised by this supervisor.
	*/
	// Getter for numOfSupervisedProjects
    public int getNumOfSupervisedProjects() {
        return numOfSupervisedProjects;
    }

    /**
     * Setter for numOfSupervisedProjects.
     * @param numOfSupervisedProjects The new number of projects supervised by this supervisor.
    */
    // Setter for numOfSupervisedProjects
    public void setNumOfSupervisedProjects(int numOfSupervisedProjects) {
        this.numOfSupervisedProjects = numOfSupervisedProjects;
    }
    
    /**
     * Increases the number of supervised projects by 1.
    */
    public void increaseNumOfSupervisedProjects() {
    	this.numOfSupervisedProjects++;
    }
    
    /**
     * Decreases the number of supervised projects by 1.
    */
    public void decreaseNumOfSupervisedProjects() {
    	this.numOfSupervisedProjects--;
    }
    
}
