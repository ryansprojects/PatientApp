package com.teamrehab.patientapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ExerciseFinish extends Activity {

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_finish);

		TextView totalTime = (TextView) findViewById(R.id.textView8);
		TextView maxSpeed = (TextView) findViewById(R.id.textView9);
		TextView maxRange = (TextView) findViewById(R.id.textView10);
		TextView yawRange = (TextView) findViewById(R.id.textView11);
		TextView pitchRange = (TextView) findViewById(R.id.textView12);
		TextView rollRange = (TextView) findViewById(R.id.textView13);

		int time = (int) savedInstanceState.getFloat("timerSave");
		int min = (int) Math.floor(time/60000);
		int sec = (int) Math.floor((time-min*60000)/1000);
		int spd = (int) savedInstanceState.getFloat("speed");
		int rom = (int) savedInstanceState.getFloat("rotation");
		int errors = (int) savedInstanceState.getFloat("errors");

		totalTime.setText(min+":"+sec);	
		maxSpeed.setText(spd+(char) 0x00B0+"/sec.");	
		maxRange.setText(rom+(char) 0x00B0);	
		
		if(errors == 0){
			yawRange.setText("good");	// update
			yawRange.setTextColor(Color.GREEN); // green
			pitchRange.setText("good");	// update
			pitchRange.setTextColor(Color.GREEN); // green
			rollRange.setText("good");	// update
			rollRange.setTextColor(Color.GREEN); // green
		}

		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise_finish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
