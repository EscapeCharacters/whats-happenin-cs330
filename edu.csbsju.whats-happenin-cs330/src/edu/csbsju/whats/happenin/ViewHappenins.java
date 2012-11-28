package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

/**
 * 
 * @author EscapeCharacters
 * This class will create a list of all the happenins currently in the database, and will display them on the screen.
 * Will also allow a user to selectively choose a single happenin, which will start the new activity ViewHappenin.
 */
public class ViewHappenins extends Activity {
	
	HappeninsCollection happsCollection = new HappeninsCollection();
	ArrayList<Happenin> happs = new ArrayList<Happenin>();
	
	private int userID;

    @Override
    /**
     * Upon creation, will access the database and return all the happenin's within it.  Will then use a list and an adapter to
     * display all of them on the screen.
     */
    public void onCreate(Bundle savedInstanceState) {
    	happsCollection.initDummy();
    	Intent intent = getIntent();
    	userID=intent.getIntExtra("userID", 0);
    	
    	toastLong(""+userID);
    	
		try {
			happs = SQLHelper.getHappenins();
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
                i.putExtra("happId", happs.get(position).getId());
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });

        
        /*
         * Adapter for the list to be displayed
         */
        
        ArrayAdapter<Happenin> adapter = 
        		new ArrayAdapter<Happenin>
        			(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
        			happs);
        listView.setAdapter(adapter);
    }
    
    public void toastLong(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_LONG;
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

}
