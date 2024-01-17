package com;


/**
 * An enum class representing the status of a project. <br>
 * It contains four possible states: AVAILABLE, RESERVED, UNAVAILABLE, and ALLOCATED.
*/
public enum ProjectStatus {
	/**
	 * The project is available and can be assigned to a student.
	 */
	AVAILABLE("Available"),
	
	/**
	 * The project is reserved by a student but not yet allocated.
	 */
	RESERVED("Reserved"),
	
	/**
	 * The project is unavailable and cannot be assigned to a student.
	 */
	UNAVAILABLE("Unavailable"),
	
	/**
	 * The project is allocated to a student.
	 */
	ALLOCATED("Allocated");
	
	/**
	 * A string representation of the project status.
	 */
	public final String projectStatus;
	
	/**
	 * Constructs a new ProjectStatus object with the specified string representation.
	 * @param projectStatus a string representation of the project status
	 */
	private ProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
}
