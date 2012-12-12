package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
* This is the login screen activity!  This is the first thing someone sees when they load the app
* @author EscapeCharacters
* @version 11/20/2012
*/
public class Login extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//makes debugging simpler:////////////////////////////////
//		Intent intent = new Intent(this, ViewHappenins.class);
//		intent.putExtra("userID", 16);
//		toastErrorMsg("Auto-logged in to Debug User");
//		startActivity(intent);
		//////////////////////////////////////////////////////////	
		//ABOUT "Debug User"////////////////////////////////////////////////////
		//user_id	|	name		|email						|	username  
		//16		|	Debug User	|notrealemail@csbsju.edu	|	debug     
		////////////////////////////////////////////////////////////////////////
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/**
	* Handles when someone presses the Create User button on the login screen
	*/
	public void newUser(View view) throws InterruptedException, ExecutionException, TimeoutException{
		Intent intent = new Intent(this, CreateNewUser.class);
		startActivity(intent);
	}
	
	/**
	* Handles when someone fills out the login screen and clicks Login
	*/
	public void login(View view) throws InterruptedException, ExecutionException, TimeoutException{
		EditText userText = (EditText)findViewById(R.id.username);
		String username = userText.getText().toString();

		EditText pswdText = (EditText)findViewById(R.id.password);
		String password = pswdText.getText().toString();

		User user = null;

		// Checks if both fields are filled in
		if(username.equals("") || password.equals("") ){
			toastErrorMsg("Please include both your username and password!");
		}
		else {
			try{
				// Gets user from DB
				user = SQLHelper.getUserByUsername(username);
				if(user == null){
					toastErrorMsg("Incorrect login info provided!");
				}
				//If good, then it logs you in
				else if(password.equals(user.getPassword()) && username.equals(user.getUsername())){
					Intent intent = new Intent(this, ViewHappenins.class);
					intent.putExtra("userID", user.getUserId());
					toastShort("Welcome, "+ user.getName());
					startActivity(intent);
					finish();
				} 
				// If not good, then provides error message
				else {
					toastErrorMsg("Incorrect login info provided!");
				}
			} catch(Exception e){
				// This is executed if internet connection is unavailable
				toastErrorMsg("Connection to What's Happenin' unavailable! Please check your internet connection and try again later!");
				
				//more debugging stuff//////////
				toastErrorMsg("Debug:\n"+e.toString());  //
				////////////////////////////////
			
			}
		}
	}
	
//	public void openGitHome(){
//		 Uri uri = Uri.parse("http://escapecharacters.github.com/whats-happenin-cs330/");
//		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		 startActivity(intent);
//	}
//	
//	public void openCSBSJUHome(){
//		Uri uri = Uri.parse("https://www.csbsju.edu/Computer-Science.htm");
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
//	}
	
	public void toastShort(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}




	/**
	* Helper method to create a 'Toast'
	*/
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_LONG;
		Toast.makeText(context, text, duration).show();
	}
}
