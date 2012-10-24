package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

public class ViewHappenin extends Activity {

	//private int myHappLoc;
	private Happenin myHap;
	private HappeninsCollection happsCollection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//this is gonna need to go, but for now this should work for dummy purposes

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_happenin_dialog);
		try {
			setupMyHappenin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setupMyHappenin() throws InterruptedException, ExecutionException, TimeoutException{
		//Get and set the happenin to display
		Intent myIntent = getIntent();
		//myHappLoc = myIntent.getIntExtra("clicked", -1);
		int myHappId = myIntent.getIntExtra("happId", -1);
		myHap = SQLHelper.getHappeninById(myHappId);

		if (myHap.getStatus() != Happenin.Status.EMPTY){
			TextView title = new TextView(ViewHappenin.this), 
			description = new TextView(ViewHappenin.this);
			TextView location = new TextView(ViewHappenin.this);
			TextView when = new TextView(ViewHappenin.this);
			location = (TextView)findViewById(R.id.where);
			title = (TextView)findViewById(R.id.title);
			description = (TextView)findViewById(R.id.description);
			when = (TextView)findViewById(R.id.when);

			title.setText(myHap.getName());
			description.setText(myHap.getDescription());
			location.setText(myHap.getLocation());
			when.setText(myHap.getTimeString());	
		} else {
			TextView title = new TextView(ViewHappenin.this), 
			description = new TextView(ViewHappenin.this);
			TextView location = new TextView(ViewHappenin.this);
			location = (TextView)findViewById(R.id.where);
			title = (TextView)findViewById(R.id.title);
			description = (TextView)findViewById(R.id.description);
			TextView when = new TextView(ViewHappenin.this);
			when = (TextView)findViewById(R.id.when);
			
			title.setText("Error loading data.");
			description.setText("Please check your internet connection and try again later.");
			location.setText("");
			when.setText("");
		}
			
	}

	/**
	 * Callback method defined by the View
	 * @param v
	 */
	public void finishDialog(View v) {
		ViewHappenin.this.finish();
	}
	
	public void makeComment(View v){
		Intent i = new Intent(ViewHappenin.this, ViewComments.class);
		i.putExtra("happId", myHap.getId());
        startActivity(i);
	}
	
}
