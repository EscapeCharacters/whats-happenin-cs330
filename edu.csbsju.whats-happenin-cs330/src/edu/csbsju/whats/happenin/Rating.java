package edu.csbsju.whats.happenin;

import org.joda.time.DateTime;

public class Rating {

	private int id;
	private int rating;
	private int happId;
	private DateTime rateTime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getHappId() {
		return happId;
	}
	public void setHappId(int happId) {
		this.happId = happId;
	}
	public DateTime getRateTime() {
		return rateTime;
	}
	public void setRateTime(DateTime rateTime) {
		this.rateTime = rateTime;
	}
	
	
	
}
