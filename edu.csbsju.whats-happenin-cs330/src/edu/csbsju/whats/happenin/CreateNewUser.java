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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_new_user, menu);
//		return true;
//	}
	

/**
This is the method that handles the creating of a new user when the 'Create' button is pushed
*/
	@SuppressWarnings("unused")
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
	
	
	//Validation methods for incoming New User info	
	//These need not be public, except we need the test class to access them
	public String validateEmail(String email){
		if( email.length() < 11 || !email.substring(email.length()-10,email.length()).equals("csbsju.edu")){ //non-valid email
			return "Please enter a valid CSBSJU email address!";			
		}
		return "";
	}
	
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
	
	public String validateUsername(String username){
		if( username.length() <= 6){ //username not long enough
			return "Username must be longer than 6 characters!";
		}
		return "";
	}
	
	public String validateName(String name){
		if( !name.contains(" ")){ //does not contain a space, thus no last name (need to further checkt this)
			return "Please enter first and last name!";
		}
		return "";
	}
	
/*
A standard method to create a 'Toast' (popup) error message
*/
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
