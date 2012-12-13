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
	
	/**
	 * Default constructor
	 */
	public User(){}
	
	/**
	 * Regular constructor
	 * @param name name of the user
	 * @param username username of the user
	 * @param password password for the user
	 * @param email email address for the user
	 */
	public User(String name, String username, String password, String email){
		this.name = name;
		this.username = username;
		this.password = password;
		this.emailAddress = email;
	}
	
	/**
	 * Get the username
	 * @return username of the user
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set the username
	 * @param username new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get the password
	 * @return password of the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set the password
	 * @param password new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Set the user ID
	 * @param userId new user ID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Get the user ID
	 * @return the user ID for the user
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * Set the email address
	 * @param emailAddress the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Get the email address
	 * @return the email address of the user
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	
	/**
	 * Get the name of the user
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the user
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the current values of the user in a string format.
	 */
	public String toString(){
		return ("Username: " + username + " Name: " + name + " Password: " 
				+ password + "Email Address: " + emailAddress + "UserID: " + userId);
	}
	
	/**
	 * Sets the enum status of the user
	 * @param status the new status
	 */
	public void setStatus(User.Status status) {
		this.status = status;
	}

	/**
	 * Gets the current status of the user
	 * @return the current enum status
	 */
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
