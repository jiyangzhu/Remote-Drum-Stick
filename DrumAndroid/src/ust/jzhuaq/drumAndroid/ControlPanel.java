package ust.jzhuaq.drumAndroid;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ust.jzhuaq.drumAndroid.util.Constants;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import android.R.integer;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * The main control activity to detect motion and send msg to PC
 * @author Bryce
 *
 */
public class ControlPanel extends Activity implements SensorEventListener {

	private OSCPortOut sender;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mMagnetometer;
	private Sensor mGyroscope;

	private float[] mLastAccelerometer = new float[3];
	private float[] mLastMagnetometer = new float[3];
	private boolean mLastAccelerometerSet = false;
	private boolean mLastMagnetometerSet = false;

	private float[] mR = new float[9];
	private float[] mOrientation = new float[3];

	private int mCycle = 100; // milliseconds
	private int mEventCycle = 100; // milliseconds

	private float oMAccuracyX = 0;
	private float oMAccuracyY = 0;
	private long oLastUpdate = -1;
	private long oLastEvent = -1;

	private float oX = -999f;
	private float oY = -999f;
	private float x = 0, y = 0;
	private float mAccuracyX = 0.2f;
	private float mAccuracyY = 0.2f;

	private int multiPara = 200; // multiply the mouse move vector

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
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

	/**
	 * When click the button
	 * 
	 * @param v
	 */
	public void click(View v) {
		//Log.i("TAG", "Click button click.");
		sendMouseEvent(Constants.EVENT_CLICK, 0, 0, 0);
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
		
		/*if (event.sensor == mAccelerometer) {
			System.arraycopy(event.values, 0, mLastAccelerometer, 0,
					event.values.length);
			mLastAccelerometerSet = true;
		} else if (event.sensor == mMagnetometer) {
			System.arraycopy(event.values, 0, mLastMagnetometer, 0,
					event.values.length);
			mLastMagnetometerSet = true;
		}
		if (mLastAccelerometerSet && mLastMagnetometerSet) {
			SensorManager.getRotationMatrix(mR, null, mLastAccelerometer,
					mLastMagnetometer);
			SensorManager.getOrientation(mR, mOrientation);
			onOrientation(mOrientation);
		}*/
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
				currX = (int)((addition + this.mAngle[0]) * this.mGyroMultiValue);
				currY = (int)((addition + this.mAngle[1]) * this.mGyroMultiValue);
				currZ = (int)((addition + this.mAngle[2]) * this.mGyroMultiValue);
			}
			this.timestamp = eventTimestamp;

			if((lastX == 0) && (lastY == 0) && (lastZ == 0)){	//for the first time
				lastX = currX;
				lastY = currY;
				lastZ = currZ;
			}
			sendMouseEvent(Constants.EVENT_CURSOR,
					currX - lastX, currY - lastY, currZ - lastZ);

		}
	}

	private void onOrientation(float[] values) {

		long curTime = System.currentTimeMillis();
		// Log.i("valuesX.96", "" + values[0]);
		if (oLastUpdate == -1 || (curTime - oLastUpdate) > mCycle) {
			// Log.i("valuesX.98", "" + values[0]);
			oLastUpdate = curTime;

			float oLastX = oX;

			float oLastY = oY;

			if (Math.abs(values[0]) < 0.5)
				oX = 0;
			else
				oX = values[0];
			if (Math.abs(values[1]) < 0.5)
				oY = 0;
			else
				oY = values[1];
			Log.i("valuesX.113", "" + oX);
			if (oLastEvent == -1 || (curTime - oLastEvent) > mEventCycle) {

				if (

				(oMAccuracyX >= 0 && Math.abs(oX - oLastX) > oMAccuracyX)

				|| (oMAccuracyY >= 0 && Math.abs(oY - oLastY) > oMAccuracyY)

				) {

					oLastEvent = curTime;

				} else
					return;
			} else
				return;
		} else
			return;
		float lastX = x, lastY = y;

		x = oX;
		y = oY;

		if ((mAccuracyX >= 0 && Math.abs(x - lastX) > mAccuracyX)
				|| (mAccuracyY >= 0 && Math.abs(y - lastY) > mAccuracyY)) {
			Log.i("valuesX.139", "" + x);
			Log.i("valuesX.140", "" + lastX);
			float xxx = (float) ((x - lastX) / 2);
			float yyy = (float) ((y - lastY) / 2);
			Log.i("Mouse Move From Sender", "" + xxx + " - " + yyy);
			this.sendMouseEvent(Constants.EVENT_CURSOR, (int) xxx * multiPara,
					(int) yyy * multiPara, 0);
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mMagnetometer,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mGyroscope,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	private void sendMouseEvent(int type, int x, int y, int z) {

		Collection<Object> args = new ArrayList<Object>();

		args.add(1);
		args.add(2);
		args.add(type);
		args.add(x);
		args.add(y);
		args.add(z);

		OSCMessage msg = new OSCMessage("/msg", args);
		//Log.i("send msg 0", "" + msg.getArguments().get(0));
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
			//Log.i("TAG", "MSG sent successfully.");

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			//Log.i("TAG", "MSG sent successfully.2");
		}

	}
}
