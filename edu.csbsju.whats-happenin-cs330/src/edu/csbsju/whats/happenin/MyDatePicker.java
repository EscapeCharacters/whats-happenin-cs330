package edu.csbsju.whats.happenin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class MyDatePicker extends Activity {
	
	private int startOrEnd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_date_picker);
        
        Intent me = getIntent();
        startOrEnd = me.getIntExtra("startOrEnd",-1);
        
    }
    
    /**
     * Handles when a user selects the time and date from the xml date/time picker file
     * @param view
     */
    public void submit(View view) {
    	DatePicker datePicker = (DatePicker)findViewById(R.id.date_picker);
    	TimePicker timePicker = (TimePicker)findViewById(R.id.time_picker);
    	Intent data = new Intent();
    	data.putExtra("month", datePicker.getMonth());
    	data.putExtra("day", datePicker.getDayOfMonth());
    	data.putExtra("year", datePicker.getYear());
    	data.putExtra("hour", timePicker.getCurrentHour());
    	data.putExtra("minute", timePicker.getCurrentMinute());
    	
    	setResult(startOrEnd, data);
    	finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_date_picker, menu);
        return true;
    }
    
    
    @Override
    public void onBackPressed()
    {
     // Return to main and close activity.
     setResult(-1);
     finish();
     
     return ;
    }

    
}
