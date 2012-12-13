package edu.csbsju.whats.happenin;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import edu.csbsju.whats.happenin.dataAccess.SQLHelper;
import android.view.inputmethod.InputMethodManager;

/**
 * This class is used to display the comments associated with an event on the user's screen.
 * @author EscapeCharacters
 * @date 12/12/12
 */
public class ViewComments extends Activity {
	ArrayList<Comment> comments = new ArrayList<Comment>();
	int myHappeninID, userId;

    @Override
    /**
     * When created, will access the database and return an comments associated with happId.
     * Will then use and Adapter module to display them in a list on the screen.
     */
    public void onCreate(Bundle savedInstanceState) {
    	Intent i = getIntent();
    	myHappeninID = i.getIntExtra("happId", 0);
    	userId = i.getIntExtra("userId", 0);
    	
    	//debugging
    	//toastLong(""+userId);
    	
		try {
			comments = SQLHelper.getCommentsByHappeninId(myHappeninID);
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
    
    /**
     * Handles the operation of adding a comment to a happenin when the user clicks on the button
     * on the Comment activity
     * @param view
     */
    public void postComment(View view) {
		EditText commentText = (EditText)findViewById(R.id.comment);
		String comment = commentText.getText().toString(); 

		if (comment.length() != 0) {
			try {
				SQLHelper.insertComment(userId, myHappeninID, comment);
				closeKeyboard();
				closeMakeCommentDrawer();
				toastShort("Comment posted");
				commentText.setText("");
			}
			catch(Exception e) {
				toastLong("Debug: "+e.toString());
			}
		}
		refreshCommentsList();

	}

	public void openMakeCommentDrawer(){
		SlidingDrawer drawer = (SlidingDrawer) findViewById(R.id.comment_drawer);
		drawer.animateOpen();
	}

	public void closeMakeCommentDrawer(){
		SlidingDrawer drawer = (SlidingDrawer) findViewById(R.id.comment_drawer);
		drawer.animateClose();
	}

	public void closeKeyboard(){
		InputMethodManager inputManager = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void toastShort(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}

	public void toastLong(String message){
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_LONG;
		Toast.makeText(context, text, duration).show();
	}

    @Override
    /**
     * Adds the item view_happenins to the menu area
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_comments, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.log_out_menu_comments){
			Intent intent = new Intent(getApplicationContext(), Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			toastLong("Log out successful");
		} else if(item.getItemId() == R.id.refresh_comments_list_menu){
			refreshCommentsList();
		}
		return false;
	}
	
	
	/**
	 * Handles the operation of refreshing the comments list after a comment is added, or when
	 * the refresh button is pressed.
	 */
	public void refreshCommentsList(){
		ListView listView = (ListView) findViewById(R.id.commentList);
		try {
			comments = SQLHelper.getCommentsByHappeninId(myHappeninID);
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
        ArrayAdapter<Comment> adapter = 
    		new ArrayAdapter<Comment>
    			(this, android.R.layout.simple_list_item_1, android.R.id.text1, 
    			comments);
        listView.setAdapter(adapter);   
	}

}
