package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;

public class ViewComments extends Activity {
	ArrayList<Comment> comments = new ArrayList<Comment>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Intent i = getIntent();
    	int id = i.getIntExtra("happId", 0);
    	
		try {
			comments = SQLHelper.getCommentsByHappeninId(id);
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
        setContentView(R.layout.activity_comment);
        
        //setup list view and click listener
        ListView listView = (ListView) findViewById(R.id.commentList);
//        listView.setClickable(true);
//        listView.setOnItemClickListener( new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Intent i = new Intent(ViewComments.this, ViewHappenin.class);
//                i.putExtra("clicked", position);
//                i.putExtra("happId", happs.get(position).getId());
//                startActivity(i);
//            }
//        });

        
        /*
         * Adapter for the list to be displayed
         */
        
        ArrayAdapter<Comment> adapter = 
        		new ArrayAdapter<Comment>
        			(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
        			comments);
        listView.setAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_happenins, menu);
        return true;
    }

}
