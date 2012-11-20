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
	
	@SuppressWarnings("unused")
	public void newUser(View view) throws InterruptedException, ExecutionException, TimeoutException{
		User user = null;
		EditText nameText = (EditText)findViewById(R.id.new_name);
		String name = nameText.getText().toString();
		
		EditText userText = (EditText)findViewById(R.id.new_username);
		String username = userText.getText().toString();

		EditText pswdText = (EditText)findViewById(R.id.new_password);
		String password = pswdText.getText().toString();
		
		EditText cnfmPswdText = (EditText)findViewById(R.id.confirm_new_password);
		String confirmedPassword = cnfmPswdText.getText().toString();
		
		EditText emailText = (EditText)findViewById(R.id.new_email);
		String email = emailText.getText().toString();

		user = null;
		boolean userTaken = false;
		user = SQLHelper.getUserByUsername(username);
		boolean hasFirstAndLast = name.contains(" ");
		String symbols = "0123456789-+_!@#$%^&*.,?";
		boolean hasSymbol = false;
		for(int i = 0; i<password.length();i++){
			if(symbols.contains(new Character(password.charAt(i)).toString())){
				hasSymbol = true;
				break;
			}
		}
		if( name.equals("") || username.equals("") || password.equals("") || email.equals("")){
			toastErrorMsg("Please insure all fields are filled");
		}
		else if( email.length() < 11 || !email.substring(email.length()-10,email.length()).equals("csbsju.edu")){ //non-valid email
			toastErrorMsg("Please enter a valid CSBSJU email address!");
		}
		else if( password.length() <= 6){ //password not long enough
			toastErrorMsg("Password must be longer than 6 characters!");
		}
		else if( username.length() < 6){ //username not long enough
			toastErrorMsg("Username must be longer than 6 characters!");
		}
		else if( hasFirstAndLast == false){ //does not contain a space, thus no last name (need to further checkt this)
			toastErrorMsg("Please enter first and last name!");
		}
		else if( hasSymbol != true ){
			toastErrorMsg("Password must contain one symbol or number!");
		}
		else if ( !password.equals(confirmedPassword) ){
			toastErrorMsg("Password and confirmed password do not match!");
		} 
		else if (user.getStatus() != User.Status.EMPTY) {
			toastErrorMsg("Username already taken.  Please try a new username!");
		}
		else{ 
			//SQLHelper.createUser(username, confirmedPassword, email, name);
			Intent i = new Intent(CreateNewUser.this, Login.class);
			toastErrorMsg("User created!  Please log in.");
			startActivity(i);
		}
	}
	
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
