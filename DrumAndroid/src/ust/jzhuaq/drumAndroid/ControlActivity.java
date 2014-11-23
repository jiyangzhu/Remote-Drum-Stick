package ust.jzhuaq.drumAndroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import ust.jzhuaq.drumAndroid.util.Constants;

import com.illposed.osc.OSCMessage;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * The main control activity to detect motion and send msg to PC
 * 
 * @author Bryce
 * 
 */
public class ControlActivity extends Activity implements SensorEventListener {

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mMagnetometer;
	private Sensor mGyroscope;

	/**
	 * 
	 */
	private static final float NS2S = 1.0E-9f;
	private long timestamp;
	private float[] mAngle;
	private float mGyroMultiValue;
	private long currentTime;
	private long lastTime = -1;
	private long eventCycle = 50;
	private float addition;

	private int lastX = 0;
	private int lastY = 0;
	private int lastZ = 0;
	private int currX = 0;
	private int currY = 0;
	private int currZ = 0;

	private int sensivity;
	private SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		myPrefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
		sensivity = myPrefs.getInt(Constants.PREFS_KEY_SENSITIVITY,
				Constants.SENSITIVY_DEFAULT);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mAngle = new float[3];
		for (int i = 0; i < 3; i++) {
			mAngle[i] = 5.0f;
		}
		mGyroMultiValue = 1000.0f; // 9.0f 200.0f
		addition = 0.0f; // 180.0f
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mGyroscope,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	/**
	 * Disconnect and unregister sensorManager listener when user leaving this
	 * activity.
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		disconnect(getCurrentFocus());
		mSensorManager.unregisterListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent();
			i.setClass(ControlActivity.this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * When click the hit img button
	 * 
	 * @param v
	 */
	public void click(View v) {
		// Log.i("TAG", "Click button click.");
		sendMouseEvent(Constants.EVENT_CLICK, 0, 0, 0);
	}

	/**
	 * When click the disconnect button
	 * 
	 * @param v
	 */
	public void disconnect(View v) {
		// Log.i("TAG", "Click button click.");
		sendConnEvent(Constants.ADDRESS_SELECTOR_DISCONNECT);
		this.finish();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		int type = sensor.getType();
		switch (type) {
		case Sensor.TYPE_GYROSCOPE:
			onGyroscope(event.values, event.timestamp);
			break;
		default:
			break;
		}
	}

	private void onGyroscope(float[] values, long eventTimestamp) {
		currentTime = System.currentTimeMillis();
		if (lastTime == -1 || (currentTime - lastTime) > eventCycle) {
			lastTime = currentTime;
			if (this.timestamp != 0f) {
				float f = NS2S * (float) (eventTimestamp - this.timestamp);
				this.mAngle[0] += f * values[0];
				this.mAngle[1] += f * values[1];
				this.mAngle[2] += f * values[2];

				lastX = currX;
				lastY = currY;
				lastZ = currZ;
				currX = (int) ((addition + this.mAngle[0]) * this.mGyroMultiValue);
				currY = (int) ((addition + this.mAngle[1]) * this.mGyroMultiValue);
				currZ = (int) ((addition + this.mAngle[2]) * this.mGyroMultiValue);
			}
			this.timestamp = eventTimestamp;

			if ((lastX == 0) && (lastY == 0) && (lastZ == 0)) { // for the first
																// time
				lastX = currX;
				lastY = currY;
				lastZ = currZ;
			}
			sendMouseEvent(Constants.EVENT_CURSOR, currX - lastX,
					currY - lastY, currZ - lastZ);

		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * Send "Connect" or "Disconnect" event.
	 * 
	 * @param selector
	 *            address selector
	 */
	private void sendConnEvent(String selector) {
		OSCMessage msg = new OSCMessage(selector);
		new SendMsg().execute(msg);
	}

	/**
	 * 
	 * @param type
	 * @param x
	 * @param y
	 * @param z
	 */
	private void sendMouseEvent(int type, int x, int y, int z) {

		Collection<Object> args = new ArrayList<Object>();

		x *= sensivity;
		y *= sensivity;
		z *= sensivity;
		args.add(1);
		args.add(2);
		args.add(type);
		args.add(x);
		args.add(y);
		args.add(z);

		OSCMessage msg = new OSCMessage(Constants.ADDRESS_SELECTOR_MSG, args);
		new SendMsg().execute(msg);

	}

	private class SendMsg extends AsyncTask<OSCMessage, Void, String> {

		@Override
		protected String doInBackground(OSCMessage... params) {

			try {
				WelcomeActivity.sender.send(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
		}

	}
}
