package edu.csbsju.whats.happenin;

import org.joda.time.DateTime;

import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
* This class stores all the information about a comment.  It matches up with the comment table in the DB
* @author Andrew J. Thom
* @version 11/07/2012
*/
public class Comment {

	private String comment;
	private DateTime dateTime;
	private int happeninId;
	private int userId;
	private int commentId;
	
	/**
	 * Gets the comment
	 * @return The comment that was made
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Sets the comment
	 * @param comment The comment that you wish to use
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets the date and time of the comment
	 * @return The DateTime object for the comment
	 */
	public DateTime getDateTime() {
		return dateTime;
	}
	
	/**
	 * Sets the date and time of the comment
	 * @param dateTime The DateTime object for the comment that you want it to be set to
	 */
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	/**
	 * Gets the happenin ID that the comment is linked to
	 * @return The Happenin Id
	 */
	public int getHappeninId() {
		return happeninId;
	}
	
	/**
	 * Sets the happenin ID
	 * @param happeninId The ID to set
	 */
	public void setHappeninId(int happeninId) {
		this.happeninId = happeninId;
	}
	
	/**
	 * Gets the ID of the user that made the comment
	 * @return The User ID of the person who made the comment
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * Sets the User ID
	 * @param userId The user ID you want to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Sets the comment ID
	 * @param commentId The Comment ID you want to set
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	/**
	 * Gets the ID of the comment
	 * @return the ID of the comment
	 */
	public int getCommentId() {
		return commentId;
	}
	
	/**
	 * Returns the User object for the user who made this comment
	 * @return The User object for the user who made this comment
	 */
	public User getUser(){
		return SQLHelper.getUserById(getUserId());
	}
	
	/**
	 * Overrides the standard toString method
	 * @return A string representation of the comment
	 */
	public String toString(){
		return "" + getUser().getName() + ": " + getComment();
	}
}
