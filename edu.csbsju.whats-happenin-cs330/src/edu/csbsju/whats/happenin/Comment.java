package edu.csbsju.whats.happenin;

import org.joda.time.DateTime;

import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

public class Comment {

	private User userEntered;
	private String comment;
	private DateTime dateTime;
	private int happeninId;
	private int userId;
	private int commentId;
	
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
	public int getHappeninId() {
		return happeninId;
	}
	public void setHappeninId(int happeninId) {
		this.happeninId = happeninId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getCommentId() {
		return commentId;
	}
	public String toString(){
		return comment;
	}
}
