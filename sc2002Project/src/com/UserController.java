package com;

/**
* The UserController interface provides methods to authenticate and log in a user.
*/
public interface UserController {
	
	/**
	* This method takes in a password string and returns true if the provided password matches the user's password, else false.
	* @param password the password string to be authenticated
	* @return True if the provided password matches the user's password, else false
	*/
	public boolean authenticate(String password);
	
	/**
	* This method takes in a userid string and returns true if the user is successfully logged in, else false.
	* @param userid the userid string of the user trying to log in
	* @return True if the user is successfully logged in, else false
	*/
	public boolean login(String userid);
	
}
