package edu.csbsju.whats.happenin.test;

import java.util.ArrayList;

import org.joda.time.DateTime;

import android.test.AndroidTestCase;
import edu.csbsju.whats.happenin.Happenin;
import edu.csbsju.whats.happenin.Rating;



public class HappeninTest extends AndroidTestCase{
	
	Rating rate1 = new Rating();
	Rating rate2 = new Rating();
	Rating rate3 = new Rating();
	Rating rate4 = new Rating();
	Rating rate5 = new Rating();
	
	protected void setUp(){
		
		DateTime now = new DateTime();
		
		rate1.setRateTime(now.minusMinutes(5));
		rate1.setRating(2);
		
		rate2.setRateTime(now.minusMinutes(10));
		rate2.setRating(3);
		
		rate3.setRateTime(now.minusMinutes(15));
		rate3.setRating(4);
		
		rate4.setRateTime(now.minusMinutes(15));
		rate4.setRating(2);
		
		rate5.setRateTime(now.minusMinutes(5));
		rate5.setRating(5);
	}
	
	protected void TearDown(){}
	
	public void testGetAverageRating(){
		Happenin hap1 = new Happenin();
		Happenin hap2 = new Happenin();
		
		ArrayList<Rating> ratings1 = new ArrayList<Rating>();
		ratings1.add(rate1);
		ratings1.add(rate2);
		hap1.setRatingsList(ratings1);
		double rating1 = hap1.getAverageRating();

		ArrayList<Rating> ratings2 = new ArrayList<Rating>();
		ratings2.add(rate2);
		ratings2.add(rate3);
		hap2.setRatingsList(ratings2);
		double rating2 = hap2.getAverageRating();
		
		assertTrue("Hap with 2 rankings should have a total ranking between the two", rating1>2&&rating1<3);
		assertTrue("Hap with higher rankings should have a higher average ranking, even if they're older", rating1<rating2);
		
		ratings1 = new ArrayList<Rating>();
		ratings1.add(rate2);
		ratings1.add(rate4);
		hap1.setRatingsList(ratings1);
		rating1 = hap1.getAverageRating();
		
		ratings2 = new ArrayList<Rating>();
		ratings2.add(rate1);
		ratings2.add(rate2);
		ratings2.add(rate4);
		hap2.setRatingsList(ratings2);
		rating2 = hap2.getAverageRating();
		
		assertTrue("Hap with a new, low rank brings down ranking", rating1>rating2);
		
		ratings1 = new ArrayList<Rating>();
		ratings1.add(rate3);
		ratings1.add(rate4);
		hap1.setRatingsList(ratings1);
		rating1 = hap1.getAverageRating();
		
		ratings2 = new ArrayList<Rating>();
		ratings2.add(rate3);
		ratings2.add(rate4);
		ratings2.add(rate5);
		hap2.setRatingsList(ratings2);
		rating2 = hap2.getAverageRating();
		
		assertTrue("Hap with a new, high rank brings up ranking", rating1<rating2);
		
	}

	public void testGetTimeString(){
		
		Happenin hap1 = new Happenin();
		DateTime now = new DateTime();
		DateTime twoDaysAgo = now.minusDays(2);
		hap1.setStartTime(twoDaysAgo);
		hap1.setEndTime(now);
		
		String time1 = hap1.getTimeString();
		int num1 = numberOfDaysInString(time1);
		assertEquals("Event spanning 2 days should have more than one day of the week in the date string", 2, num1);
		
		Happenin hap2 = new Happenin();
		DateTime twoMinutesAgo = now.minusMinutes(2);
		hap2.setStartTime(twoMinutesAgo);
		hap2.setEndTime(now);
		
		String time2 = hap2.getTimeString();
		int num2 = numberOfDaysInString(time2);
		//note - don't run this test between midnight and 12:02
		assertEquals("Event spanning 2 minutes should only have one day of the week in the date string", 1, num2);
	}
	
	private int numberOfDaysInString(String dateString){
		ArrayList<String> daysOfWeek = new ArrayList<String>();
		daysOfWeek.add("Mon");
		daysOfWeek.add("Tue");
		daysOfWeek.add("Wed");
		daysOfWeek.add("Thu");
		daysOfWeek.add("Fri");
		daysOfWeek.add("Sat");
		daysOfWeek.add("Sun");
		
		int count = 0;
		
		for(int i=0; i<dateString.length()-4; i++){
			String threeChars = dateString.substring(i, i+3);
			for(String day : daysOfWeek){
				if(threeChars.equalsIgnoreCase(day))
					count++;
			}
		}
		
		return count;
	}
}
