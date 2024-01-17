package com;

/**
 * This class represents a supervisor to FYP request.
 * It extends the Request class and contains additional fields: Supervisor, FYP, Project and new Supervisor.
*/
public class SupToFYPReq extends Request{
	protected Supervisor sup;
	protected FYP fyp;
	protected Project project;
	protected Supervisor newSup;
	
	/**
	 * Constructor for the SupToFYPReq class.
	 * @param project the Project object related to the request
	 * @param requestType the type of the request
	 * @param ns the new Supervisor object to be assigned to the FYP
	 */
	public SupToFYPReq(Project project, RequestType requestType, Supervisor ns) {
		super(project, requestType);
		this.fyp = ExcelData.FYPDatabase.get("ASFLI");
		this.newSup = ns;
	}
	
	/**
	 * Getter for the supervisor field.
	 * @return the supervisor object
	 */
	// Getter for supervisor
    public Supervisor getSupervisor() {
        return sup;
    }

    /**
     * Setter for the supervisor field.
     * @param supervisor the supervisor object to be set
     */
    // Setter for supervisor
    public void setSupervisor(Supervisor supervisor) {
        this.sup = supervisor;
    }

    /**
     * Getter for the fyp field.
     * @return the FYP object
     */
    // Getter for fyp
    public FYP getFyp() {
        return fyp;
    }

    /**
     * Setter for the fyp field.
     * @param fyp the FYP object to be set
     */
    // Setter for fyp
    public void setFyp(FYP fyp) {
        this.fyp = fyp;
    }
    
    /**
     * Getter for the project field.
     * @return the Project object
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter for the project field.
     * @param project the Project object to be set
     */

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Getter for the newsup field.
     * @return the new Supervisor object
     */
    public Supervisor getNewSup() {
        return newSup;
    }

    /**
     * Setter for the newsup field.
     * @param newsup the new Supervisor object to be set
     */
    public void setNewSup(Supervisor newsup) {
        this.newSup = newsup;
    }
}
