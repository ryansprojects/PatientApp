package com.teamrehab.patientapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ExerciseBegin extends Activity {

	Boolean clicked = false;
	long timerSave;
	long maxRot=0, maxSpd=0;
	int errorVal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_begin);

		TextView rotation = (TextView) findViewById(R.id.rotation_textview);
		TextView speed = (TextView) findViewById(R.id.speed_textview);
		TextView feedback = (TextView) findViewById(R.id.feedback_textview);
		final TextView timer = (TextView) findViewById(R.id.timer);

		// update CountDownTimer with new total time (in ms)
		final long timerInit = 30000; // update this with passed data
		timerSave = timerInit;
		int min = (int) Math.floor(timerSave/60000);
		int sec = (int) Math.floor((timerSave-min*60000)/1000);
		timer.setText(min+":"+sec);


		final CountDownTimer cdTimer = new CountDownTimer(timerInit, 1000) {
			public void onTick(long centisUntilFinished) {
				timerSave = centisUntilFinished;
				int min = (int) Math.floor(timerSave/60000);
				int sec = (int) Math.floor((timerSave-min*60000)/1000);
				timer.setText(min+":"+sec);          
			}
			public void onFinish() {
				timer.setText("done!");
			}
			
		};

		// Add code to continuously read in processes data and display in variables above
		int spd = (int) Math.round(150.772);		// read angular velocity and round
		if(spd>maxSpd){
			maxSpd = spd;
		}
		int rot = (int) Math.round(83.245);		// read in range of motion and round
		if(rot>maxRot){
			maxRot = rot;
		}
		
		speed.setText(Double.toString(spd)+" "+(char) 0x00B0);
		rotation.setText(Double.toString(rot)+" "+(char) 0x00B0);


		// Update feedback
		feedback.setText(getString(R.string.feedback_ok));
		errorVal = 0;

		// Button functionality
		Button startButton = (Button) findViewById(R.id.start_button);
		Button resetButton = (Button) findViewById(R.id.reset_button);
		Button stopButton = (Button) findViewById(R.id.stop_button);

		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cdTimer.start();			
			}
		});

		stopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cdTimer.cancel();		// record data, transmit via bluetooth
				// set in timerSave value for time completed
				Intent intent = new Intent(ExerciseBegin.this, ExerciseFinish.class);
				Bundle bundle = new Bundle();
				bundle.putLong("timerSave", timerSave);
				bundle.putLong("rotation", maxRot);
				bundle.putLong("speed", maxSpd);
				bundle.putLong("errors", errorVal);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		resetButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					// clear data, and start timer again
				cdTimer.start();
			}
		});
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
		getMenuInflater().inflate(R.menu.exercise_begin, menu);
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
