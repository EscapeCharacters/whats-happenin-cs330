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
 * @author EscapeCharacters
 * This class is used as the activity to change your password.
 */
public class ChangePassword extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}
	
	/**
	 * The method called from button in the xml file that then processes and changes a users password.
	 */
	public void changePassword(View view) throws InterruptedException, ExecutionException, TimeoutException{
		User user = null;
		
		Intent i = getIntent();
		int userId = i.getIntExtra("userId", -1);
		
		EditText oldPasswordText = (EditText)findViewById(R.id.old_password);
		String oldPassword = oldPasswordText.getText().toString();

		EditText passwordText = (EditText)findViewById(R.id.change_password_new_password);
		String password = passwordText.getText().toString();
		
		EditText confirmedPasswordText = (EditText)findViewById(R.id.change_password_confirm_new_password);
		String confirmedPassword = confirmedPasswordText.getText().toString();
		
		user = SQLHelper.getUserById(userId);
		
		if(!oldPassword.equals(user.getPassword())){
			toastErrorMsg("Old password incorrect.  Please try again!");
		}
		else if(!validatePassword(password, confirmedPassword).equals("")){
			toastErrorMsg(validatePassword(password, confirmedPassword));
		}
		else {
			SQLHelper.updatePassword(userId, password);
			toastErrorMsg("Password successfully updated");
			finish();
		}
		
	}
	
	/**
	 * Validates the password given by the user.
	 * @param password the new password
	 * @param confirmedPassword password used to validate the new password
	 * @return empty string on success, otherwise a string containing an error message.
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
	 * Used to display a toast message on the screen.
	 * @param error the message.
	 */
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
