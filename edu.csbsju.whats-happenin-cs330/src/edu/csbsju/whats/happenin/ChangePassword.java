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
public class ChangePassword extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_new_user, menu);
//		return true;
//	}
	

/**
This is the method that handles the creating of a new user when the 'Create' button is pushed
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
