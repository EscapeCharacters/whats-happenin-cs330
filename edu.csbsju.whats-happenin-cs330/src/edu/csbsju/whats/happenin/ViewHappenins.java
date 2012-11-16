package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

public class ViewHappenins extends Activity {
	
	HappeninsCollection happsCollection = new HappeninsCollection();
	ArrayList<Happenin> happs = new ArrayList<Happenin>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	happsCollection.initDummy();
    	
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
                i.putExtra("happid", position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_happenins, menu);
        return true;
    }

}
