package edu.csbsju.whats.happenin;

/**
* User class that has all the information about a user
* @author EscapeCharacters
* @version 11/19/2012
*/
public class User {

	// Private fields
	private String username;
	private String password;
	private int userId;
	private String emailAddress;
	private String name;
	private User.Status status;
	
	public User(){}
	
	public User(String name, String username, String password, String email){
		this.name = name;
		this.username = username;
		this.password = password;
		this.emailAddress = email;
	}
	
	/**
	* Below are the standard setters and getters
	*/ 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return ("Username: " + username + " Name: " + name + " Password: " 
				+ password + "Email Address: " + emailAddress + "UserID: " + userId);
	}
	public void setStatus(User.Status status) {
		this.status = status;
	}

	public User.Status getStatus() {
		return status;
	}
	/**
	* Used if the user is an empty one
	*/
	public enum Status {
		EMPTY;
	}
}
