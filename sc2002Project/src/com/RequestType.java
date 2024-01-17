package com;

/**
 * The RequestType enum represents the types of requests that can be made by students and supervisors regarding FYP projects. <br>
 * The possible request types are:
 * <ul>
 * <li> CHANGETITLE: Represents a request to change the title of a project.</li>
 * <li> TRANSFERSTUDENT: Represents a request to transfer a student to a different supervisor.</li>
 * <li> ALLOCATEPROJECT: Represents a request to allocate a project to a student.</li>
 * <li> DEREGISTERPROJECT: Represents a request to deregister a project from a student.</li>
 * </ul>
 * The request type is specified as a string and can be accessed using the {@code requestType} field.
*/
public enum RequestType {
	/**
	 * A request to change the title of a project.
	 */
	CHANGETITLE("ChangeTitle"),
	
	/**
	 * A request to transfer a student from one supervisor to another.
	 */
	TRANSFERSTUDENT("TransferStudent"),
	
	/**
	 * A request to allocate a project to a student.
	 */
	ALLOCATEPROJECT("AllocateProject"),
	
	/**
	 * A request to deregister from a project.
	 */
	DEREGISTERPROJECT("DeregisterProject");
	
	/**
	 * The string representation of the request type.
	 */	
	public final String requestType;
	
	/**
	 * Constructs a new RequestType with the given string representation.
	 * @param requestType the string representation of the request type
	 */	
	private RequestType(String requestType) {
		this.requestType = requestType;
	}
}