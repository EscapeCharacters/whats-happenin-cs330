package edu.csbsju.whats.happenin;

public class User {

	private String username;
	private String password;
	private int userId;
	private String emailAddress;
	private String name;
	
	public User(){}
	
	public User(String name, String username, String password, String email){
		this.name = name;
		this.username = username;
		this.password = password;
		this.emailAddress = email;
	}
	
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
	public enum Status {
		EMPTY;
	}
}
