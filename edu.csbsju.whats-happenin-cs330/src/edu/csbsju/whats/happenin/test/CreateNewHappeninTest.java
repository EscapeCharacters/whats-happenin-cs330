package edu.csbsju.whats.happenin.test; 

import org.joda.time.DateTime;

import android.test.AndroidTestCase;

import edu.csbsju.whats.happenin.test.*;



public class CreateNewHappeninTest extends AndroidTestCase {

	CreateNewHappenin cnh;

	protected void setUp(){
		cnh = new CreateNewHappenin();
	}

	protected void tearDown(){}

	//an empty return string means a validation pass-through
	//a non-empty return string means validation failed and the string itself explains why
	public void testValidateLocation(){

		String result1 = cnh.validateLocation("This is an invalid location because there are too many characters.");
		assertTrue("A location longer than 30 characters passed validation.", !result1.equals(""));
		
		String result2 = cnh.validateLocation("This is a valid location.");
		assertTrue("A location shorter than 30 characters failed validation.", result2.equals(""));
	}
	
	public void testValidateName(){

		String result1 = cnh.validateName("This is an invalid name because there are too many characters.");
		assertTrue("A name longer than 50 characters passed validation.", !result1.equals(""));
		
		String result2 = cnh.validateName("This is a valid name.");
		assertTrue("A name shorter than 50 characters failed validation.", result2.equals(""));
	}
	
	public void testValidateTimes(){
		DateTime now = new DateTime();
		DateTime fiveMinutesAgo = now.minusMinutes(5);
		DateTime fiveDaysAgo = now.minusDays(5);
		DateTime inFiveMinutes = now.plusMinutes(5);
		DateTime inFiveDays = now.plusDays(5);
		
		String result1 = cnh.validateTimes(fiveMinutesAgo, fiveDaysAgo);
		assertTrue("Start time after end time, but validation passed.", !result1.equals(""));
		
		String result2 = cnh.validateTimes(fiveMinutesAgo, inFiveMinutes);
		assertTrue("Start time in the past, but validation passed.", !result3.equals(""));
		
		//don't need to test start time in the past (that would fall in one of the two above cases)
		
		String result3 = cnh.validateTimes(inFiveMinutes, inFiveDays);
		assertTrue("Valid times failed validation.", result3.equals(""));
		//unless we want to put a cap on the duration of any event
	}
}