package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
Class that stores a Happenin.  Matches up with everything in the database
@author Chris Norby
@version 10/15/2012
 */
public class Happenin {

	private String name;
	private String location;
	private DateTime startTime;
	private DateTime endTime;
	private String description;
	private Double rating;
	private ArrayList<Rating> ratingsList;
	private List<Comment> comments;
	private int id;
	private Happenin.Status status = Happenin.Status.VALID;


	/**
	 * Standard constructor
	 */
	public Happenin(){
		startTime = new DateTime();
	}

	/**
	 * Gets the name of the Happenin
	 * @return the name of the happenin
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the happenin
	 * @param name the name you want to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the location of the happenin
	 * @return The location of the happenin
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the location of the happenin
	 * @param location The location of the happenin
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the start DateTime of the Happenin
	 * @return The DateTime object representing the start time of thee Happenin
	 */
	public DateTime getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets the start DateTime for this happenin
	 * @param startTime the DateTime to set for the start time
	 */
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Gets the end time
	 * @return the end time
	 */
	public DateTime getEndTime() {
		return endTime;
	}
	
	/**
	 * Sets the end time
	 * @param endTime the end date and time
	 */
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Gets the description of this happenin
	 * @return the description of this happenin
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of this happenin
	 * @param description the description of this happenin
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the rating of this happening
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}
	
	/**
	 * Sets the rating of this happening
	 * @param rating The rating
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	/**
	 * Gets the list of comments for this happening
	 * @return the list of comments for this happening
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	public String toString() {
		return getName();
	}
	
	/**
	 * Sets the ID of this happenin
	 * @param id the ID to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the ID of this happenin
	 * @return the ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the status of this happenin
	 * @param status the Status to set
	 */
	public void setStatus(Happenin.Status status) {
		this.status = status;
	}
	
	/**
	 * Gets the status for this happenin
	 * @return the Status for this happenin
	 */
	public Happenin.Status getStatus() {
		return status;
	}
	
	/**
	 * Gets a list of ratings relating to this happening
	 * @return the list of ratings for this happening
	 */
	public ArrayList<Rating> getRatingsList(){
		return ratingsList;
	}
	
	/**
	 * Sets the list of ratings for this happening
	 * @param list the list of ratings for this happening
	 */
	public void setRatingsList(ArrayList<Rating> list){
		ratingsList = list;
	}
	
	/**
	 * Updates the ratingsList with the most recent ratings
	 */
	public void updateRatingsList(){
		ratingsList = SQLHelper.getRatingsByHappeninId(id);
	}
	
	/**
	 * Gets the average weighted rating for this happenin
	 * @return the average weighted rating for this happenin
	 */
	public double getAverageRating(){
		if(getRatingsList() == null)
			updateRatingsList();
		ArrayList<Rating> ratings = getRatingsList();
		double totalWeight = 0;
		DateTime now = new DateTime();

		for (Rating r: ratings){
			Integer staleness = Minutes.minutesBetween(now, r.getRateTime()).getMinutes();
			double weight = 1.0/staleness; 
			totalWeight+=weight;
		}

		double weightedTotal = 0;
		for (Rating r: ratings){
			Integer staleness = Minutes.minutesBetween(now, r.getRateTime()).getMinutes();
			double weight = 1.0/staleness;
			double weightedRanking = weight*r.getRating();
			weightedTotal += weightedRanking;
		}

		return weightedTotal/totalWeight;
	}

	/**
	 * Gets the time string for use in the view
	 * @return time string for use in the view
	 */
	public String getTimeString(){
		StringBuilder sb = new StringBuilder("");
		String startHour = startTime.hourOfDay().getAsText();
		String startMinute = startTime.minuteOfHour().getAsText();
		String endHour = endTime.hourOfDay().getAsText();
		String endMinute = endTime.minuteOfHour().getAsText();
		String startDayOfWeek = startTime.dayOfWeek().getAsShortText();
		String endDayOfWeek = endTime.dayOfWeek().getAsShortText();
		int startMonth = startTime.monthOfYear().get();
		int endMonth = endTime.monthOfYear().get();
		int startDay = startTime.dayOfMonth().get();
		int endDay = endTime.dayOfMonth().get();

		//converts start time from military time to 12 hour time
		String startAmPm = "am";
		int startHourI = Integer.parseInt(startHour);
		if (startHourI > 12){
			startHourI -= 12;
			startAmPm = "pm";
		} else if (startHourI == 12)
			startAmPm = "pm";
		else if (startHourI == 0){
			startHourI = 12;
		}

		//converts end time from military time to 12 hour time
		String endAmPm = "am";
		int endHourI = Integer.parseInt(endHour);
		if (endHourI > 12){
			endHourI -= 12;
			endAmPm = "pm";
		} else if (endHourI == 12)
			endAmPm = "pm";
		else if (endHourI == 0){
			endHourI = 12;
		}

		startHour = "" + startHourI;
		endHour = "" + endHourI;

		//checks the minute values - if the strings are length 1, then it puts a 0 in front of them
		startMinute = startMinute.length() > 1 ? startMinute : "0"+startMinute;
		endMinute = endMinute.length() > 1 ? endMinute : "0"+endMinute;


		//builds the string
		sb.append(startDayOfWeek);
		sb.append(". ");
		sb.append(startMonth);
		sb.append("/");
		sb.append(startDay);
		sb.append(" ");
		sb.append(startHour);
		sb.append(":");
		sb.append(startMinute);
		sb.append(startAmPm);
		sb.append(" to ");

		if(!(startMonth==endMonth&&startDay==endDay)){
			sb.append(endDayOfWeek);
			sb.append(". ");
			sb.append(endMonth);
			sb.append("/");
			sb.append(endDay);
			sb.append(" ");
		}

		sb.append(endHour);
		sb.append(":");
		sb.append(endMinute);
		sb.append(endAmPm);

		//returns the string
		return sb.toString();
	}

	/**
	 * This enumeration is used to say whether the happenin is valid or empty
	 * @author Andrew J. Thom
	 * @version 11/07/2012
	 */
	public enum Status {
		EMPTY,
		VALID
	}

}
