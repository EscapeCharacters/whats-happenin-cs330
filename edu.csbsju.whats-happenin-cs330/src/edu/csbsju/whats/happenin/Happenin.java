package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

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
	private List<Comment> comments;
	private int id;
	private Happenin.Status status = Happenin.Status.VALID;
	
/*
Standard constructor
*/
	public Happenin(){
		startTime = new DateTime();
	}
	
/**
* Standard setters and getters
*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public DateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	public DateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public String toString() {
		return getName();
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setStatus(Happenin.Status status) {
		this.status = status;
	}
	public Happenin.Status getStatus() {
		return status;
	}
	public ArrayList<Rating> getRatings(){
		return SQLHelper.getRatingsByHappeninId(id);
	}
	public double getAverageRating(){
		ArrayList<Rating> ratings = getRatings();
		int total = 0;
		for(Rating r : ratings){
			total += r.getRating();
		}
		double avg = (double)total/(double)ratings.size();
		return avg;
	}
	
	public String getTimeString(){
		StringBuilder sb = new StringBuilder("");
		String startHour = startTime.hourOfDay().getAsText();
		String startMinute = startTime.minuteOfHour().getAsText();
		String endHour = endTime.hourOfDay().getAsText();
		String endMinute = endTime.minuteOfHour().getAsText();
		String dayOfWeek = startTime.dayOfWeek().getAsShortText();
		int month = startTime.monthOfYear().get();
		int day = startTime.dayOfMonth().get();
		
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
		sb.append(dayOfWeek);
		sb.append(". ");
		sb.append(month);
		sb.append("/");
		sb.append(day);
		sb.append(" ");
		sb.append(startHour);
		sb.append(":");
		sb.append(startMinute);
		sb.append(startAmPm);
		sb.append(" to ");
		sb.append(endHour);
		sb.append(":");
		sb.append(endMinute);
		sb.append(endAmPm);
		
		//returns the string
		return sb.toString();
	}
	
	public enum Status {
		EMPTY,
		VALID;
	}
	
}
