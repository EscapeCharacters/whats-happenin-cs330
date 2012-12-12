package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
 * 
 * @author EscapeCharacters
 * This class is intended to display one happenin, and the info associated with that happenin on the screen.
 * Will also allow the user to access and display the comment associated with that happenin.
 */
public class ViewHappenin extends Activity {

	//private int myHappLoc;
	private Happenin myHap;
	private HappeninsCollection happsCollection;
	private int userID;

	@Override
	/**
	 * Will set up the screen to display the specific happenin that a user clicked on.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		//this is gonna need to go, but for now this should work for dummy purposes

		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
    	userID=intent.getIntExtra("userID", 0);
    	//debugging
    	//toastLong(""+userID);
		
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
	
	/**
	 * Sets up the Happenin by accessing the database and returning the info associated with happId.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
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
			TextView ratingField = (TextView)findViewById(R.id.rating);
			
			double avg = myHap.getAverageRating();
			String formattedAverage = String.format("%.1f", avg);
			
			title.setText(myHap.getName());
			description.setText(myHap.getDescription());
			location.setText(myHap.getLocation());
			when.setText(myHap.getTimeString());	
			ratingField.setText("Rating: " + formattedAverage);
			
			final RatingBar ratingsBar = (RatingBar)findViewById(R.id.ratingBar);
			ratingsBar.setStepSize(0.1f);
			ratingsBar.setRating((float)avg);
			ratingsBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		 
					ratingsBar.setStepSize(1.0f);
		 
				}
			});
			
		} else {
			TextView title = new TextView(ViewHappenin.this), 
			description = new TextView(ViewHappenin.this);
			TextView location = new TextView(ViewHappenin.this);
			TextView when = new TextView(ViewHappenin.this);
			TextView rating = new TextView(ViewHappenin.this);
			
			location = (TextView)findViewById(R.id.where);
			title = (TextView)findViewById(R.id.title);
			description = (TextView)findViewById(R.id.description);
			when = (TextView)findViewById(R.id.when);
			rating = (TextView)findViewById(R.id.rating);
			
			title.setText("Error loading data.");  
			description.setText("Please check your internet connection and try again later.");
			location.setText("");
			when.setText("");
			rating.setText("");
			
			final RatingBar ratingsBar = (RatingBar)findViewById(R.id.ratingBar);
			ratingsBar.setVisibility(RatingBar.GONE);
			
			Button commentsButton = (Button)findViewById(R.id.view_comments);
			commentsButton.setVisibility(Button.GONE);
			
			Button createRating = (Button)findViewById(R.id.create_rating);
			createRating.setVisibility(Button.GONE);
		}
			
	}
	
	public void toastLong(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_LONG;
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * Callback method defined by the View
	 * @param v
	 */
	public void finishDialog(View v) {
		ViewHappenin.this.finish();
	}
	
	/**
	 * A method that send the user to the ViewComments screen.
	 * @param v
	 */
	public void viewComments(View v){
		Intent i = new Intent(ViewHappenin.this, ViewComments.class);
		i.putExtra("happId", myHap.getId());
		i.putExtra("userID", userID);
        startActivity(i);
	}
	
	public void createRating(View v){
		Intent intent = getIntent();
    	userID=intent.getIntExtra("userID", 0);
    	
    	RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    	float rating = ratingBar.getRating();
    	
    	int happId = myHap.getId();
    	
    	try {
			SQLHelper.createRating((int)rating, userID, happId);
			toastLong("Rating sumbitted.");
			
			TextView ratingField = new TextView(ViewHappenin.this);
			ratingField = (TextView)findViewById(R.id.rating);
			double avg = myHap.getAverageRating();
			String formattedAverage = String.format("%.1f", avg);
			ratingField.setText("Rating: " +formattedAverage);
			
		} catch (Exception e){
			toastLong("Error connecting to What's Happenin'.  Please check your Internet connection and try again later.");
		}
	}
	
}
