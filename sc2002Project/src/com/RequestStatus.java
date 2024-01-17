package com;


/**
 * An enum class representing the status of a request. <br>
 * It contains four possible states: AVAILABLE, RESERVED, UNAVAILABLE, and ALLOCATED.
*/
public enum RequestStatus {
	/**
	 * The request is send and pending approval.
	 */
	PENDING("Pending"),
	
	/**
	 * The request was approved.
	 */
	APPROVED("Approved"),
	
	/**
	 * The request was rejected.
	 */
	REJECTED("Rejected");
	
	
	/**
	 * A string representation of the request status.
	 */
	public final String requestStatus;
	
	/**
	 * Constructs a new RequestStatus object with the specified string representation.
	 * @param requestStatus a string representation of the request status
	 */
	private RequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
}