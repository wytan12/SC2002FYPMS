package com; 


/**
 * The Request class represents a request made for a project. <br>
 * It contains information about the project, the request status, the request type, and the request database.
*/
public class Request { 
 protected Project project; 
 protected ExcelData requestDatabase; 
 protected RequestStatus requestStatus; 
 protected RequestType requestType;
  
	 /**
	  * Creates a new request for the specified project and request type, with a pending status.
	  *
	  * @param project The project associated with the request.
	  * @param requestType The type of the request.
	  */
 	public Request(Project project, RequestType requestType) {
 		this.project = project;
 		this.requestType = requestType;
 		this.requestStatus = RequestStatus.PENDING;
 	}
  
 	/**
 	 * Gets the project associated with the request.
 	 *
 	 * @return The project associated with the request.
 	 */
    // Getter for project 
    public Project getProject() { 
        return project; 
    } 
 
    /**
     * Sets the project associated with the request.
     *
     * @param project The project to set.
     */
    // Setter for project 
    public void setProject(Project project) { 
        this.project = project; 
    } 
 
    /**
     * Gets the request database associated with the request.
     *
     * @return The request database associated with the request.
     */
    // Getter for requestDatabase 
    public ExcelData getRequestDatabase() { 
        return requestDatabase; 
    }  
 
    /**
     * Sets the request database associated with the request.
     *
     * @param request Database The request database to set.
     */
    // Setter for requestDatabase 
    public void setRequestDatabase(ExcelData requestDatabase) { 
        this.requestDatabase = requestDatabase; 
    } 
 
    /**
     * Gets the status of the request.
     *
     * @return The status of the request.
     */
    // Getter for requestStatus 
    public RequestStatus getRequestStatus() { 
        return requestStatus; 
    } 
 
    /**
     * Sets the status of the request.
     *
     * @param requestStatus The status to set.
     */
    // Setter for requestStatus 
    public void setRequestStatus(RequestStatus requestStatus) { 
        this.requestStatus = requestStatus; 
    }  
    
    /**
     * Gets the type of the request.
     *
     * @return The type of the request.
     */
    // Getter for requestType
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * Sets the type of the request.
     *
     * @param requestType The type to set.
     */
    // Setter for requestType
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}