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
	public ArrayList<Rating> getRatingsList(){
		return ratingsList;
	}
	public void setRatingsList(ArrayList<Rating> list){
		ratingsList = list;
	}
	public void updateRatingsList(){
		ratingsList = SQLHelper.getRatingsByHappeninId(id);
	}
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
	
	public enum Status {
		EMPTY,
		VALID;
	}
	
}
