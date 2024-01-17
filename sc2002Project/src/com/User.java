package com;

/**
* This class represents a user of the system.
*/
public abstract class User {
	private String name;
	private String userId;
	private String password;
	
	/**
	* Constructs a User object with the given ID and name.
	* The password is set to "password" by default.
	* @param id the ID of the user
	* @param nm the name of the user
	*/
	public User(String id, String nm){
		this.name = nm;
		this.userId = id;
		this.password = "password";
	}
	
	/**
	* Returns the ID of the user.
	* @return the ID of the user
	*/
	public String getUserId(){
		return this.userId;
	}
	
	/**
	* Returns the name of the user.
	* @return the name of the user
	*/
	public String getName() {
		return this.name;
	}
	/**
	* Returns the password of the user.
	* @return the password of the user
	*/
	public String getPassword() {
		return this.password;
	}
	
	/**
	* Sets the password of the user.
	* @param pw the new password of the user
	*/
	public void setPassword(String pw) {
		this.password = pw;
	}
	
//	public void setuserId(String id){
//		this.userId = id;
//	}
	
}
