package com;

/**
 * The StuToFYPReq class represents a request made by a student regarding a FYP project. <br>
 * It extends the Request class and includes specific information such as the student, FYP and project involved in the request.
*/
public class StuToFYPReq extends Request{
	protected Student student;
	protected FYP fyp;
	protected Project project;
	
	/**
	 * Creates a new instance of the StuToFYPReq class.
	 * @param project The project involved in the request.
	 * @param requestType The type of request made by the student.
	*/
	public StuToFYPReq(Project project, RequestType requestType) {
		super(project, requestType);
	}
	 
	/**
	 * Returns the student involved in the request.
	 * @return The student involved in the request.
	*/
	// Getter for student
    public Student getStudent() {
        return student;
    }
 
    /**
     * Sets the student involved in the request.
     * @param student The student involved in the request.
    */
    // Setter for student
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Returns the FYP project involved in the request.
     * @return The FYP project involved in the request.
    */
    // Getter for fyp
    public FYP getFyp() {
        return fyp;	
    }

    /**
     * Sets the FYP project involved in the request.
     * @param fyp The FYP project involved in the request.
    */
    // Setter for fyp
    public void setFyp(FYP fyp) {
        this.fyp = fyp;
    }
	
    /**
     * Returns the project involved in the request.
     * @return The project involved in the request.
    */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project involved in the request.
     * @param project The project involved in the request.
    */
    public void setProject(Project project) {
        this.project = project;
    }
}
