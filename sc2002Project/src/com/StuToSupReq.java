package com;

/**
 * The StuToSupReq class extends the Request class and represents a request from a student to their supervisor.
 * It contains information about the student, the supervisor, the project, and a new project title (if applicable).
*/
public class StuToSupReq extends Request{
	protected Student student;
	protected Supervisor supervisor;
	protected String newTitle;
	protected String oldTitle;

	/**
	 * Constructor for StuToSupReq class
	 * @param project - the project object associated with the request
	 * @param requestType - the type of request (e.g. ChangeTitle)
	 * @param newTitle - the new project title (if applicable) associated with the request
	 */
	public StuToSupReq(Project project, RequestType requestType, String newTitle, String oldTitle) {
		super(project, requestType);
		this.newTitle = newTitle;
		this.oldTitle = oldTitle; 
	}
	
	/**
	 * Getter for student
	 * @return student - the student object associated with the request
	 */
	// Getter for student
    public Student getStudent() {
        return student;
    } 

    /**
     * Setter for student
     * @param student - the student object to be associated with the request
     */
    // Setter for student
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Getter for supervisor
     * @return supervisor - the supervisor object associated with the request
     */
    // Getter for supervisor
    public Supervisor getSupervisor() {
        return supervisor;
    }

    /**
     * Setter for supervisor
     * @param supervisor - the supervisor object to be associated with the request
     */
    // Setter for supervisor
    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * Getter for newTitle
     * @return newTitle - the new project title (if applicable) associated with the request
     */
    // Getter for title
    public String getNewTitle() {
        return newTitle;
    }

    /**
     * Setter for newTitle
     * @param newTitle - the new project title (if applicable) to be associated with the request
     */
    // Setter for title
    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }	
    
    public String getOldTitle () {
    	return this.oldTitle;
    }
    
    public void setOldTitle(String oldTitle) {
    	this.oldTitle = oldTitle;
    }
}
