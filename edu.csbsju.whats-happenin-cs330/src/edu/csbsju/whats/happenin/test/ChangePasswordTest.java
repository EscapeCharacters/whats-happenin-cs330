package edu.csbsju.whats.happenin.test;

import android.test.AndroidTestCase;
import edu.csbsju.whats.happenin.ChangePassword;

public class ChangePasswordTest extends AndroidTestCase{

	ChangePassword changePassword;
	
	protected void setUp(){
		changePassword = new ChangePassword(); 
	}
	
	public void testValidatePassword(){
		String validPasswordMsg = changePassword.validatePassword("password123", "password123");
		assertTrue("Valid password failed validation.", validPasswordMsg.equals(""));
		
		String nonconfirmedPasswordMsg = changePassword.validatePassword("password123", "password124");
		assertTrue("Non-matching passwords passed validation.", !nonconfirmedPasswordMsg.equals(""));
		
		String tooShortPasswordMsg = changePassword.validatePassword("pswd01", "pswd01");
		assertTrue("Too short passwords passed validation.", !tooShortPasswordMsg.equals(""));
		
		String noSymbolsPasswordMsg = changePassword.validatePassword("password", "password");
		assertTrue("Password without a symbol passed validation.", !noSymbolsPasswordMsg.equals(""));
		
		String everythingWrongPasswordMsg = changePassword.validatePassword("pswd", "password1");
		assertTrue("Horrible password passed validation.", !everythingWrongPasswordMsg.equals(""));
	}
}
