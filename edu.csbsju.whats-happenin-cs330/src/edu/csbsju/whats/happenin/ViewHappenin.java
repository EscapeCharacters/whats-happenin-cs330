package edu.csbsju.whats.happenin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ViewHappenin extends Activity {
	
	private int myHappLoc;
	private Happenin myHap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		//this is gonna need to go, but for now this should work for dummy perposes
		HappeninsCollection happsCollection = new HappeninsCollection();
		happsCollection.initDummy();
		
		//Get and set the happenin to display
		Intent myIntent = getIntent();
		myHappLoc = myIntent.getIntExtra("clicked", -1);
		myHap = happsCollection.getHappenins().get(myHappLoc);
		
		
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_happenin_dialog);
    }

    /**
     * Callback method defined by the View
     * @param v
     */
    public void finishDialog(View v) {
        ViewHappenin.this.finish();
    }
}
