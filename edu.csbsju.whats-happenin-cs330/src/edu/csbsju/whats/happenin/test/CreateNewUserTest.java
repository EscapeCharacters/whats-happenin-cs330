package edu.csbsju.whats.happenin.test;

import android.test.AndroidTestCase;
import edu.csbsju.whats.happenin.CreateNewUser;

public class CreateNewUserTest extends AndroidTestCase{
	
	CreateNewUser cnu;
	
	protected void setUp(){
		cnu = new CreateNewUser();
	}
	
	//for these validation tests, returning an empty message is the same as passing validation.
	public void testValidateEmail(){
		String validEmailMsg = cnu.validateEmail("abc@csbsju.edu");
		assertTrue("Valid email failed validation.", validEmailMsg.equals(""));
		
		String tooShortEmailMsg = cnu.validateEmail("csbsju.edu");
		assertTrue("Too short email passed validation.", !tooShortEmailMsg.equals(""));
		
		String notCSBSJUEmailMsg = cnu.validateEmail("abc123@yahoo.com");
		assertTrue("An email not CSBSJU passed validation.", !notCSBSJUEmailMsg.equals(""));
		
		String everythingWrongEmailMsg = cnu.validateEmail("yahoo.com");
		assertTrue("An email not CSBSJU passed validation.", !everythingWrongEmailMsg.equals(""));
	}
	
	public void testValidatePassword(){
		String validPasswordMsg = cnu.validatePassword("password123", "password123");
		assertTrue("Valid password failed validation.", validPasswordMsg.equals(""));
		
		String nonconfirmedPasswordMsg = cnu.validatePassword("password123", "password124");
		assertTrue("Non-matching passwords passed validation.", !nonconfirmedPasswordMsg.equals(""));
		
		String tooShortPasswordMsg = cnu.validatePassword("pswd01", "pswd01");
		assertTrue("Non-matching passwords passed validation.", !tooShortPasswordMsg.equals(""));
		
		String noSymbolsPasswordMsg = cnu.validatePassword("password", "password");
		assertTrue("Password without a symbol passed validation.", !noSymbolsPasswordMsg.equals(""));
		
		String everythingWrongPasswordMsg = cnu.validatePassword("pswd", "password1");
		assertTrue("Horrible password passed validation.", !everythingWrongPasswordMsg.equals(""));
	}
	
	public void testValidateUsername(){
		String validUsernameMsg = cnu.validateUsername("username1");
		assertTrue("Valid username failed validation.", validUsernameMsg.equals(""));
		
		String tooShortUsernameMsg = cnu.validateUsername("usrnme");
		assertTrue("Valid username failed validation.", !tooShortUsernameMsg.equals(""));
	}
	
	public void testValidateName(){
		String validNameMsg = cnu.validateName("john doe");
		assertTrue("Valid name failed validation.", validNameMsg.equals(""));
		
		String noSpaceNameMsg = cnu.validateName("johndoe");
		assertTrue("Name with no spaces passed validation.", !noSpaceNameMsg.equals(""));
	}
}
