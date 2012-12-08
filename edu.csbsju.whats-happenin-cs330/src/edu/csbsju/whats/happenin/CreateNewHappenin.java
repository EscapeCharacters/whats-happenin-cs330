package edu.csbsju.whats.happenin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
 * @author cdnorby
 * Will be linked from the View Happenins screen, where a new happenin will be created,
 * validated, and will be submitted to the database.
 */
public class CreateNewHappenin extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}
	
	
	
	
	/**
	This is the method that handles the creating of a new happenin when the 'Create' button is pushed
	*/
		@SuppressWarnings("unused")
		public void newHappenin(View view) throws InterruptedException, ExecutionException, TimeoutException{
			Happenin happenin = null;
			String name = "";
			EditText nameText = (EditText)findViewById(R.id.newHappenin_name);
			name = nameText.getText().toString().trim();
			
			String location = "";
			EditText locationText = (EditText)findViewById(R.id.newHappenin_location);
			location = locationText.getText().toString().trim();
			
			/*pull in start and end times from view*/
			DateTime startTime = new DateTime();
			DateTime endTime = new DateTime();
			
			String description = "";
			EditText descriptionText = (EditText)findViewById(R.id.newHappenin_description);
			description = descriptionText.getText().toString().trim();

			String nameValMsg = validateName(name);
			String locationValMsg = validateLocation(location);
			String timesValMsg = validateTimes(startTime, endTime);
			String descriptionValMsg = validateDescription(description);
			
			if(!nameValMsg.equals(""))
				toastErrorMsg(nameValMsg);
			else if(!locationValMsg.equals(""))
				toastErrorMsg(locationValMsg);
			else if(!timesValMsg.equals(""))
				toastErrorMsg(timesValMsg);
			else{ 
				SQLHelper.createHappenin();
				Intent i = new Intent(CreateNewHappenin.this, ViewHappenins.class);
				toastErrorMsg("Happenin created!");
				startActivity(i);
			}
		}
		
		public String validateName(String name){
			if(name.length()>50)
				return "Happenin name must be 50 characters or less. Yours is "+name.length()+".";
			else 
				return "";
		}
		
		public String validateLocation(String location){
			if(location.length()>50)
				return "Location must be 50 characters or less. Yours is "+location.length()+".";
			else 
				return "";
		}

		public String validateTimes(DateTime startTime, DateTime endTime){
			if(startTime.isAfter(endTime))
				return "Start time must be before end time.";
			if(startTime.isBeforeNow())
				return "Happenin cannot start in the past.";
			Hours duration = Hours.hoursBetween(startTime, endTime);
			if(duration.getHours()>48)
				return "Happenin cannot have a duration of more than 48 hours.";
			return "";
		}
		
		public String validateDescription(String description){
			if(description.length()>500)
				return "Description must be 500 characters or less. Yours is "+description.length()+".";
			else 
				return "";
		}

		public void toastErrorMsg(String error){
			Context context = getApplicationContext();
			CharSequence text = error;
			int duration = Toast.LENGTH_SHORT;
			Toast.makeText(context, text, duration).show();
		}
}
