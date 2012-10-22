package edu.csbsju.whats.happenin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    
    public void login(View view){
    	EditText userText = (EditText)findViewById(R.id.username);
    	String username = userText.getText().toString();
    	
    	EditText pswdText = (EditText)findViewById(R.id.password);
    	String password = pswdText.getText().toString();
    	
    	TextView tv = (TextView) findViewById(R.id.errorMsg);
    	
    	//if(username.equals("Sample") && password.equals("Sample")){
    		Intent intent = new Intent(this, ViewHappenins.class);
    		startActivity(intent);
    	//} else 
    		tv.setText("Wrong shit");
    		
    	
    }
}
