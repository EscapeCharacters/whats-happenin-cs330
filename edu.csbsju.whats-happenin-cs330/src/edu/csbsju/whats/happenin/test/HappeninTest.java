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

}
