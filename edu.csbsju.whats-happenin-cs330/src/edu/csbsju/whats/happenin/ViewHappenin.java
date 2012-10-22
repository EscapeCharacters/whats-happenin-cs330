package edu.csbsju.whats.happenin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHappenin extends Activity {
	
	private int myHappLoc;
	private Happenin myHap;
	private HappeninsCollection happsCollection;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		//this is gonna need to go, but for now this should work for dummy purposes
		happsCollection = new HappeninsCollection();
		happsCollection.initDummy();
		
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_happenin_dialog);
        setupMyHappenin();
    }
	
	private void setupMyHappenin(){
		//Get and set the happenin to display
				Intent myIntent = getIntent();
				myHappLoc = myIntent.getIntExtra("clicked", -1);
				myHap = happsCollection.getHappenins().get(myHappLoc);
				
				TextView title = new TextView(ViewHappenin.this), 
						description = new TextView(ViewHappenin.this);
				title = (TextView)findViewById(R.id.title);
				description = (TextView)findViewById(R.id.description);
				
				title.setText(myHap.getName());
				description.setText(myHap.getDescription());
	}

    /**
     * Callback method defined by the View
     * @param v
     */
    public void finishDialog(View v) {
        ViewHappenin.this.finish();
    }
}
