package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

public class Login extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public void newUser(View view)throws InterruptedException, ExecutionException, TimeoutException{
		Intent intent = new Intent(this, CreateNewUser.class);
		startActivity(intent);
	}
	
	public void login(View view) throws InterruptedException, ExecutionException, TimeoutException{
		EditText userText = (EditText)findViewById(R.id.username);
		String username = userText.getText().toString();

		EditText pswdText = (EditText)findViewById(R.id.password);
		String password = pswdText.getText().toString();

		//SQL Query: SELECT * FROM Users u where u.username = username AND u.password = password

		User user = null;

		if(username.equals("") || password.equals("") ){
			toastErrorMsg("Please include both your username and password!");
		}
		else {
			try{
				user = SQLHelper.getUserByUsername(username);
				if(user == null){
					toastErrorMsg("Incorrect login info provided!");
				}
				else if(password.equals(user.getPassword()) && username.equals(user.getUsername())){
					Intent intent = new Intent(this, ViewHappenins.class);
					startActivity(intent);
				} else {
					toastErrorMsg("Incorrect login info provided!");
				}
			} catch(Exception e){
				toastErrorMsg("onnection to What's Happenin' unavailable! Please check your internet connection and try again later!");
			}
		}
	}
	
	public void toastErrorMsg(String error){
		Context context = getApplicationContext();
		CharSequence text = error;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
