package edu.csbsju.whats.happenin;

import java.util.Date;

import org.joda.time.DateTime;

public class Comment {

	private User userEntered;
	private String comment;
	private DateTime dateTime;
	
	
	public User getUserEntered() {
		return userEntered;
	}
	public void setUserEntered(User userEntered) {
		this.userEntered = userEntered;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
}
