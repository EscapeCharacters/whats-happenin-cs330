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
    
    public void submit(View view) {
    	DatePicker dp = (DatePicker)findViewById(R.id.date_picker);
    	TimePicker tp = (TimePicker)findViewById(R.id.time_picker);
    	Intent data = new Intent();
    	data.putExtra("month", dp.getMonth());
    	data.putExtra("day", dp.getDayOfMonth());
    	data.putExtra("year", dp.getYear());
    	data.putExtra("hour", tp.getCurrentHour());
    	data.putExtra("minute", tp.getCurrentMinute());
    	
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
