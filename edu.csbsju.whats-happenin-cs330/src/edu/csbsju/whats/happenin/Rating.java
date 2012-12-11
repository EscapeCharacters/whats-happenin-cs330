package edu.csbsju.whats.happenin;

import org.joda.time.DateTime;

/**
* Class that matches up with the Rating table in the DB
* @author Andrew J. Thom
* @version 11/19/2012
*/
public class Rating {

	private int id;
	private int rating;
	private int happId;
	private DateTime rateTime;
	
	/**
	* Gets the ID of this rating
	* @return the ID
	*/
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the Id of this happenin
	 * @param id the int you want to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * gets the rating
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Sets the rating
	 * @param rating the int you want to set the rating to
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/**
	 * Gets the happenin ID
	 * @return happenin ID
	 */
	public int getHappId() {
		return happId;
	}
	
	/**
	 * sets the happenin ID
	 * @param happId the ID of the happenin you want to link this rating to
	 */
	public void setHappId(int happId) {
		this.happId = happId;
	}
	
	/**
	 * Gets the date and time of this comment
	 * @return date and time of this comment
	 */
	public DateTime getRateTime() {
		return rateTime;
	}
	
	/**
	 * Sets the date and time of this rating
	 * @param rateTime the dateTime of the rating you want to set 
	 */
	public void setRateTime(DateTime rateTime) {
		this.rateTime = rateTime;
	}
	
	
	
}
