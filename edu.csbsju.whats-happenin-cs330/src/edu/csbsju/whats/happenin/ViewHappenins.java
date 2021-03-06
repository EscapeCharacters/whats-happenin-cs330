package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
 * This class will create a list of all the happenins currently in the database, and will display them on the screen.
 * Will also allow a user to selectively choose a single happenin, which will start the new activity ViewHappenin.
 * @author EscapeCharacters
 * @date 12/12/12
 */
public class ViewHappenins extends Activity {

	ArrayList<Happenin> happenins = new ArrayList<Happenin>();
	private static int MY_KEY = 1111; 
	public static final int ERROR = -1, START_TIME = 0, END_TIME = 1;
	private DateTime startTime_DT = null, endTime_DT = null;


	private int userId;

	@Override
	/**
	 * Upon creation, will access the database and return all the happenin's within it.  Will then use a list and an adapter to
	 * display all of them on the screen.
	 */
	public void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		userId=intent.getIntExtra("userId", 0);

		//debugging
		//toastLong(""+userId);
		//    	Intent i = new Intent(this, CreateNewHappenin.class);
		//    	toastLong("Debug into Create New Happenin");
		//    	startActivity(i);



		try {
			happenins = SQLHelper.getHappenins();
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

		//populate the view with these happenins
		//this might be of help:  http://developer.android.com/guide/topics/ui/layout/listview.html#Loader

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_happenins);

		//setup list view and click listener
		ListView listView = (ListView) findViewById(R.id.mylist);
		listView.setClickable(true);
		listView.setOnItemClickListener( new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent i = new Intent(ViewHappenins.this, ViewHappenin.class);
				i.putExtra("clicked", position);
				i.putExtra("happId", happenins.get(position).getId());
				i.putExtra("userId", userId);
				startActivity(i);
			}
		});


		/*
		 * Adapter for the list to be displayed
		 */

		ArrayAdapter<Happenin> adapter = 
				new ArrayAdapter<Happenin>
		(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
				happenins);
		listView.setAdapter(adapter);

		/*
		 * Setup for the Create New Happenins drawer
		 */
		startTimeListener();
		endTimeListener();
		
	}

	private void startTimeListener(){
		EditText startTime = (EditText) findViewById(R.id.new_happenin_start_time);
		startTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent picker = new Intent(ViewHappenins.this, MyDatePicker.class);
				picker.putExtra("startOrEnd", START_TIME);
				startActivityForResult(picker, MY_KEY);
			}
		});
	}
	
	private void endTimeListener(){
		EditText endTime = (EditText) findViewById(R.id.new_happenin_end_time);
		endTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent picker = new Intent(ViewHappenins.this, MyDatePicker.class);
				picker.putExtra("startOrEnd", END_TIME);
				startActivityForResult(picker, MY_KEY);
			}
		});
	}

	@Override
	/**
	 * This is what handles the information returned from the Date and Time picker
	 */
	protected void onActivityResult(int pRequestCode, int resultCode, Intent data)
	{
		if (resultCode == START_TIME && pRequestCode == MY_KEY){
			toastLong("Made it!");
			int startMonth, startDay, startYear, startHour, startMinute;
			startMonth = data.getIntExtra("month", -1)+1;
			startDay = data.getIntExtra("day", -1);
			startYear = data.getIntExtra("year", -1);
			startHour = data.getIntExtra("hour", -1);
			startMinute = data.getIntExtra("minute", -1);
			
			startTime_DT = new DateTime(startYear, startMonth, startDay, startHour, startMinute);
			EditText startTime_ET = (EditText)findViewById(R.id.new_happenin_start_time);
			startTime_ET.setText(startMonth + "/"
								+startDay + "/"
								+startYear + "   "
								+startHour + ":"
								+startMinute);
		
		}
		else if (resultCode == END_TIME && pRequestCode == MY_KEY){
			int endMonth, endDay, endYear, endHour, endMinute;
			endMonth = data.getIntExtra("month", -1)+1;
			endDay = data.getIntExtra("day", -1);
			endYear = data.getIntExtra("year", -1);
			endHour = data.getIntExtra("hour", -1);
			endMinute = data.getIntExtra("minute", -1);
			
			endTime_DT = new DateTime(endYear, endMonth, endDay, endHour, endMinute);
			EditText endTime_ET = (EditText)findViewById(R.id.new_happenin_end_time);
			endTime_ET.setText(endMonth + "/"
								+endDay + "/"
								+endYear + "   "
								+endHour + ":"
								+endMinute);
		}
		
		startTimeListener();
		 
	}


	/**
	 * This is called when a new happenin is created
	 * @param view
	 */
	public void createHappenin(View view) {
		//finish this; it was just copy pasted
		
		EditText title_ET = (EditText)findViewById(R.id.new_happenin_title),
				 startTime_ET = (EditText)findViewById(R.id.new_happenin_start_time),
				 endTime_ET = (EditText)findViewById(R.id.new_happenin_end_time),
				 location_ET = (EditText)findViewById(R.id.new_happenin_location),
				 description_ET = (EditText)findViewById(R.id.new_happenin_description);
		
		String title = title_ET.getText().toString().trim(),
			   startTime = startTime_ET.getText().toString().trim(),
			   endTime = endTime_ET.getText().toString().trim(),
			   location = location_ET.getText().toString().trim(),
			   description = description_ET.getText().toString().trim();
		
		if (title.length() != 0) {
			if(startTime.length() != 0 && endTime.length() != 0) {
				if(location.length() != 0) {
					if(description.length() != 0){
						String nameValMsg = validateTitle(title);
						String locationValMsg = validateLocation(location);
						String timesValMsg = validateTimes(startTime_DT, endTime_DT);
						String descriptionValMsg = validateDescription(description);
						
						if(!nameValMsg.equals(""))
							toastLong(nameValMsg);
						else if(!locationValMsg.equals(""))
							toastLong(locationValMsg);
						else if(!timesValMsg.equals(""))
							toastLong(timesValMsg);
						else if(!descriptionValMsg.equals(""))
							toastLong(descriptionValMsg);
						else{ 
							
							try {
								SQLHelper.insertHappenin(title, description, startTime_DT,
										  endTime_DT, location);
								closeKeyboard();
								closeMakeCommentDrawer();
								toastShort("Happenin created!");
								
								title_ET.setText("");
								startTime_ET.setText("");
								endTime_ET.setText("");
								location_ET.setText("");
								description_ET.setText("");
								
								refreshHappeninsList();
								
							}
							catch(Exception e) {
								toastLong("Debug: "+e.toString());
							}
							
						}
					}
					else {
						toastLong("At least give a brief description of your Happenin!");
					}
				}
				else {
					toastLong("Where is your Happenin going to be?");
				}
			}
			else {
				toastLong("Be sure to select both a start and end time!");
			}
		} 
		else {
			toastLong("Please give your Happenin a title!");
		}
	}
	
	/**
	 * Validates the title of the Happenin
	 * @param name the Title of the happenin
	 * @return An error string if the happenin title is more than 50 characters
	 */
	public String validateTitle(String name){
		if(name.length()>50)
			return "Happenin name must be 50 characters or less. Yours is "+name.length()+".";
		else 
			return "";
	}
	
	/**
	 * Validates the location entered by the user
	 * @param location the location of the event
	 * @return empty string on success, or error message contained in a string
	 */
	public String validateLocation(String location){
		if(location.length()>50)
			return "Location must be 50 characters or less. Yours is "+location.length()+".";
		else 
			return "";
	}

	/**
	 * Validates the time of the event
	 * @param startTime the start time of the event
	 * @param endTime the end time of the event
	 * @return empty string on success, or an error message contained in a string
	 */
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
	
	/**
	 * Validates the description of the event
	 * @param description the description of the event
	 * @return empty string on success, or error message contained in a string
	 */
	public String validateDescription(String description){
		if(description.length()>500)
			return "Description must be 500 characters or less. Yours is "+description.length()+".";
		else 
			return "";
	}


	public void openMakeCommentDrawer(){
		SlidingDrawer drawer = (SlidingDrawer) findViewById(R.id.happenin_drawer);
		drawer.animateOpen();
	}

	public void closeMakeCommentDrawer(){
		SlidingDrawer drawer = (SlidingDrawer) findViewById(R.id.happenin_drawer);
		drawer.animateClose();
	}

	public void closeKeyboard(){
		InputMethodManager inputManager = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}



	
	public void toastLong(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_LONG;
		Toast.makeText(context, text, duration).show();
	}
	
	public void toastShort(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}

	@Override
	/**
	 * This method will add the view_happenins to the menu area.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_happenins, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.view_happenins_menu){ //Refresh the list of happenins
			ListView listView = (ListView) findViewById(R.id.mylist);
			try {
				happenins = SQLHelper.getHappenins();
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
			ArrayAdapter<Happenin> adapter = 
					new ArrayAdapter<Happenin>
			(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
					happenins);
			listView.setAdapter(adapter);
			refreshHappeninsList();
			return true;
		} else if(item.getItemId() == R.id.change_password_menu){ //Sends a user to the change password screen
			Intent intent = new Intent(ViewHappenins.this, ChangePassword.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			return true;
		} else if(item.getItemId() == R.id.log_out_menu){ //Logs out the user
			Intent intent = new Intent(getApplicationContext(), Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			toastLong("Log out successful");
			finish();
		}
		return false;
	}
	
	@Override
	/**
	 * Used to insure that the user cannot return to the login screen without first logging out.
	 */
	public void onBackPressed() {
	}
	
	public void refreshHappeninsList(){
		ListView listView = (ListView) findViewById(R.id.mylist);
		try {
			happenins = SQLHelper.getHappenins();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		ArrayAdapter<Happenin> adapter = 
				new ArrayAdapter<Happenin>
		(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
				happenins);
		listView.setAdapter(adapter);
	}

}
