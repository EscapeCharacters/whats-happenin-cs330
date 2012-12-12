/**
 * 
 */
package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author awzurn
 * Will be linked from the Login Page, where a new user will be created,
 * validated, and will be submitted to the database.
 */
public class CreateNewUser extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}


	@SuppressWarnings("unused")
	/**
	 * Called from the button in the xml file that will create a new user within the database.
	 */
	public void newUser(View view) throws InterruptedException, ExecutionException, TimeoutException{
		User user = null;
		EditText nameText = (EditText)findViewById(R.id.new_name);
		String name = nameText.getText().toString().trim();
		
		EditText userText = (EditText)findViewById(R.id.new_username);
		String username = userText.getText().toString();

		EditText passwordText = (EditText)findViewById(R.id.new_password);
		String password = passwordText.getText().toString();
		
		EditText confirmPasswordText = (EditText)findViewById(R.id.confirm_new_password);
		String confirmedPassword = confirmPasswordText.getText().toString();
		
		EditText emailText = (EditText)findViewById(R.id.new_email);
		String email = emailText.getText().toString();
		
		user = null;
		boolean userTaken = false;
		user = SQLHelper.getUserByUsername(username);
		boolean hasFirstAndLast = name.contains(" ");
		
		if( name.equals("") || username.equals("") || password.equals("") || email.equals("")){
			toastErrorMsg("Please ensure all fields are filled");
		}
		
		//the following validation is pulled into seperate methods to allow for unit testing.
		// Whether the username is already taken cannot be unit tested becouse that requires info from the DB.

		String passwordValidationMessage = validatePassword(password, confirmedPassword);
		String emailValidationMessage = validateEmail(email);
		String usernameValidationMessage = validateUsername(username);
		String nameValidationMessage = validateName(name);
		
		if(!passwordValidationMessage.equals(""))
			toastErrorMsg(passwordValidationMessage);
		else if(!emailValidationMessage.equals(""))
			toastErrorMsg(emailValidationMessage);
		else if(!usernameValidationMessage.equals(""))
			toastErrorMsg(usernameValidationMessage);
		else if(!nameValidationMessage.equals(""))
			toastErrorMsg(nameValidationMessage);
		else if (user.getStatus() != User.Status.EMPTY) {
			toastErrorMsg("Username already taken.  Please try a new username!");
		}
		else{ 
			SQLHelper.createUser(username, confirmedPassword, email, name);
			Intent intent = new Intent(CreateNewUser.this, Login.class);
			toastErrorMsg("User created!  Please log in.");
			startActivity(intent);
		}
	}
	
	
	/**
	 * Validates the email address of the user, must be a @csbsju.edu address.
	 * @param email email address to be validated
	 * @return empty string on success, otherwise string containing error message.
	 */
	public String validateEmail(String email){
		if( email.length() < 11 || !email.substring(email.length()-10,email.length()).equals("csbsju.edu")){ //non-valid email
			return "Please enter a valid CSBSJU email address!";			
		}
		return "";
	}
	
	/**
	 * Validates the password against the confirmed password
	 * @param password the password
	 * @param confirmedPassword the confirmation password to be validated against
	 * @return empty string on success, otherwise string containing error message
	 */
	public String validatePassword(String password, String confirmedPassword){
		if( password.length() <= 6){ //password not long enough
			return "Password must be longer than 6 characters!";
		}
		if ( !password.equals(confirmedPassword) ){
			return "Password and confirmed password do not match!";
		} 
		String symbols = "0123456789-+_!@#$%^&*.,?";
		boolean hasSymbol = false;
		for(int i = 0; i<password.length();i++){
			if(symbols.contains(new Character(password.charAt(i)).toString())){
				hasSymbol = true;
				break;
			}
		}
		if( hasSymbol != true ){
			return "Password must contain one symbol or number!";
		}
		return "";
	}
	
	/**
	 * Validates the given username
	 * @param username the username
	 * @return empty string on success, otherwise string containing error message
	 */
	public String validateUsername(String username){
		if( username.length() <= 6){ //username not long enough
			return "Username must be longer than 6 characters!";
		}
		return "";
	}
	
	/**
	 * Validates the given name.
	 * @param name the name
	 * @return empty string on success, otherwise a string containing  error message.
	 */
	public String validateName(String name){
		if( !name.contains(" ")){ //does not contain a space, thus no last name (need to further checkt this)
			return "Please enter first and last name!";
		}
		return "";
	}
	
	/**
	 * Used to display a toast message on the screen.
	 * @param error the message to be displayed.
	 */
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
