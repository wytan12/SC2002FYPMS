package com;

/**
 * The Project class represents a project in the system.  <br>
 * It contains inforation about the project such as its ID, title, supervisor, student and project status. 
 *
 */
public class Project {
	protected int projectId;
	protected String projectTitle;
	protected Supervisor supervisor;
	protected Student student;
	protected ProjectStatus projectStatus;
	
	/**
	 * Constructs a Project object wth a specified project title and sets its status to AVAILABLE
	 * @param projectTitle the title of the project.
	 * 
	 */
	public Project(String projectTitle){
		this.projectTitle = projectTitle;
		setProjectStatus(ProjectStatus.AVAILABLE);
		this.supervisor = SupervisorController.getSupervisor();
	}
	
	/**
	 * Constructs a Project object with a specified project ID, project title, and supervisor.
	 * @param projectId the ID of the project.
	 * @param projectTitle the title of the project.
	 * @param supervisor the supervisor of the project.
	 * 
	 */
	public Project(int projectId, String projectTitle, Supervisor supervisor){
	    this.projectId = projectId;
	    this.supervisor = supervisor;
	    this.projectTitle = projectTitle;
	    this.projectStatus = ProjectStatus.AVAILABLE;
	  }
	
	//--------------------------SETTER----------------------------------------
	/**
	 * Sets the title of the project
	 * @param projectTitle the title of the project.
	 * 
	 */
	public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
	
	/**
	 * Sets the ID of the project.
	 * @param projectId the ID of the project.
	 * 
	 */
	public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
	
	/**
	 * Sets the status of the project.
	 * @param projectStatus the status of the project.
	 * @return true if the project status is successfully set.
	 * 
	 */
	public boolean setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
		return true;
	}
	
	/**
	 * Sets the student working on the project.
	 * @param s the student working on the project.
	 * 
	 */
	public void setStudent(Student s) {
		this.student = s;
	}
	
	/**
	 * Sets the supervisor of the project.
	 * @param s the supervisor of the project.
	 * 
	 */
	public void setSupervisor(Supervisor s) {
		this.supervisor = s;
	}
	
	//--------------------------GETTER----------------------------------------
	/**
	 * Returns the supervisor of the project
	 * @return The supervisor of the project.
	 * 
	 */
	public Supervisor getSupervisor() {
        return this.supervisor;
    }
    
	/**
	 * Returns the student working on the project
	 * @return The student working on the project.
	 * 
	 */
    public Student getStudent() {
        return this.student;
    }
    
	/**
	 * Returns the status of the project
	 * @return The status of the project
	 * 
	 */
    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }
	
	/**
	 * Returns the ID of the project
	 * @return The ID of the project
	 * 
	 */
    public int getprojectId() {
    	return this.projectId;
    }
    
	/**
	 * Returns the Title of the project
	 * @return The Title of the project
	 * 
	 */
    public String getprojectTitle() {
    	return this.projectTitle;
    }  
}