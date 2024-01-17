package com;


/**
 * The Student class represents a student user in the system <br>
 * It extends the User class and inherits its properties and methods.
*/
public class Student extends User{
	Project project;
	StuToSupReq stuToSupReq;
	StuToFYPReq stuToFypReq;
	boolean deregisteredFYP;
	
	/**
	 * Constructs a new Student object with the given name and ID.
	 * 
	 * @param nm the name of the student
	 * @param id the ID of the student
	 */
	public Student(String nm, String id) {
		// getters and setters
		super(nm, id);
	}
	
	/**
	 * Returns the project associated with the student.
	 * @return the project associated with the student
	*/
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with the student.
     * @param project the project associated with the student
    */
    public void setProject(Project project) {
        this.project = project;
    }
    
    /**
     * Returns the student's request to a supervisor.
     * @return the student's request to a supervisor
    */
    public StuToSupReq getStuToSupReq() {
        return stuToSupReq;
    }

    /**
     * Sets the student's request to a supervisor.
     * @param stutosupreq the student's request to a supervisor
    */
    public void setStuToSupReq(StuToSupReq stutosupreq) {
        this.stuToSupReq = stutosupreq;
    }

    /**
     * Returns the student's request to a FYPcoordinator.
     * @return the student's request to a FYPcoordinator
    */
    public StuToFYPReq getStuToFYPReq() {
        return stuToFypReq;
    }

    /**
     * Sets the student's request to a FYPcoordinator.
     * @param stutofypreq the student's request to a FYPcoordinator
    */
    public void setStuToFYPReq(StuToFYPReq stutofypreq) {
        this.stuToFypReq = stutofypreq;
    }
    
    /**
     * Indicates whether the student has deregistered from the final year project.
     * @return true if the student has deregistered from the final year project, false otherwise
    */
    // Getter for deregisteredFYP
    public boolean isDeregisteredFYP() {
        return deregisteredFYP;
    }

    /**
     * Sets whether the student has deregistered from the final year project.
     * @param deregisteredFYP true if the student has deregistered from the final year project, false otherwise
    */
    // Setter for deregisteredFYP
    public void setDeregisteredFYP(boolean deregisteredFYP) {
        this.deregisteredFYP = deregisteredFYP;
    }
}
