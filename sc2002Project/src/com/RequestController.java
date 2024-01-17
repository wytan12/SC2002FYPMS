package com;


/**
 * The RequestController class handles requests made by students and supervisors regarding FYP projects. <br>
 * It accepts or rejects these requests based on the type of request and updates the request status accordingly. <br>
 * It also creates instances of StudentController, FYPController, and SupervisorController to interact with the student, FYP, and supervisor data. <br>
*/
public class RequestController {
	private static StuToFYPReq stuToFypReq;
	private static StuToSupReq stuToSupReq;
	private static SupToFYPReq supToFypReq;
	private static RequestType reqType;
	private static SupervisorController supervisorController;
	private static StudentController studentController;
	private static FYPController fypController;

	/**
	 * Constructs a RequestController object to handle a student's request to change the FYP project.
	 * @param request The StuToFYPReq object representing the student's request to change the FYP project.
	*/
	public RequestController(StuToFYPReq request) {
		this.stuToFypReq =  request;
		studentController = new StudentController(request.getStudent());
		fypController = new FYPController(request.getFyp());
		reqType = request.getRequestType();
	}

	/**
	 * Constructs a RequestController object to handle a student's request to change the project supervisor.
	 * @param request The StuToSupReq object representing the student's request to change the project supervisor.
	*/
	public RequestController(StuToSupReq request) {
		this.stuToSupReq =  request;
		studentController = new StudentController(request.getStudent());
		supervisorController = new SupervisorController(request.getSupervisor());
		reqType = request.getRequestType();
	}

	/**
	 * Constructs a RequestController object to handle a supervisor's request to transfer a student to a new FYP project.
	 * @param request The SupToFYPReq object representing the supervisor's request to transfer a student to a new FYP project.
	*/
	public RequestController(SupToFYPReq request) {
		this.supToFypReq =  request;
		supervisorController = new SupervisorController(request.getSupervisor());
		fypController = new FYPController(request.getFyp());
		reqType = request.getRequestType();
	}  

	/**
	 * Accepts a request made by a student or supervisor and updates the request status accordingly.
	*/
	public void acceptReq() {
		switch (reqType) {
		case CHANGETITLE:
			stuToSupReq.getProject().setProjectTitle(stuToSupReq.getNewTitle());
			stuToSupReq.setRequestStatus(RequestStatus.APPROVED);
			break;
		case TRANSFERSTUDENT:
			fypController.changeSupervisor(supToFypReq.getProject().getprojectId(), supToFypReq.getNewSup().getUserId());
			supToFypReq.setRequestStatus(RequestStatus.APPROVED);
			break;
		case ALLOCATEPROJECT: 
			fypController.allocateProject(stuToFypReq.getProject().getprojectId(), studentController.getStudent());
			stuToFypReq.setRequestStatus(RequestStatus.APPROVED);
			break;
		case DEREGISTERPROJECT:
			fypController.deallocateProject(stuToFypReq.getProject().getprojectId());
			stuToFypReq.setRequestStatus(RequestStatus.APPROVED);
			break;
		}
	}

	/**
	 * Sets the request status to "REJECTED" based on the type of request.
	*/
	public void rejectReq() {
		switch (reqType) {
		case CHANGETITLE:
			stuToSupReq.setRequestStatus(RequestStatus.REJECTED);
			break;
		case TRANSFERSTUDENT:
			supToFypReq.setRequestStatus(RequestStatus.REJECTED);
			break;
		case ALLOCATEPROJECT: 
			stuToFypReq.setRequestStatus(RequestStatus.REJECTED);
			break;
		case DEREGISTERPROJECT:
			stuToFypReq.setRequestStatus(RequestStatus.REJECTED);
			break;
		}
	}

	//functions for Sup


	//functions for Stu
}
