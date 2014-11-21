package ust.jzhuaq.drumAndroid;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class SensorInfo extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mGyroscope;

	EditText etAccelerometer;
	EditText etGyroscope;
	EditText etUncalGyroscope;

	StringBuilder sensorBuilder = null;

	private int mCycle = 50; // milliseconds
	private long mEventCycle = 50; // milliseconds

	private float oMAccuracyX = 0;
	private float oMAccuracyY = 0;
	private long oLastUpdate = -1;
	private long oLastEvent = -1;

	private float oX = -999f;
	private float oY = -999f;
	private double x = 0, y = 0;
	private float mAccuracyX = 0.1f;
	private float mAccuracyY = 0.1f;

	/**
	 * 
	 */
	private static final float NS2S = 1.0E-9f;
	private long timestamp;
	private float[] mAngle;
	private float mGyroMultiValue;
	private long currentTime;
	private long lastTime = -1;
	private float addition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor);

		etAccelerometer = (EditText) findViewById(R.id.etAccelerometer);
		etGyroscope = (EditText) findViewById(R.id.etGyroscope);
		etUncalGyroscope = (EditText) findViewById(R.id.etUncalGyroscope);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mAngle = new float[3];
		for (int i = 0; i < 3; i++) {
			mAngle[i] = 5.0f;
		}
		mGyroMultiValue = 200.0f; // 9.0f 200.0f
		addition = 0.0f;		//180.0f
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor sensor = event.sensor;
		int type = sensor.getType();

		switch (type) {
		case Sensor.TYPE_GYROSCOPE:
			currentTime = System.currentTimeMillis();
			if(lastTime == -1 || (currentTime - lastTime) > mEventCycle){
				lastTime = currentTime;
				//Log.i("currentTime", ""+currentTime);
				if(this.timestamp != 0f){
					float f = NS2S * (float)(event.timestamp - this.timestamp);
					//Log.i("f", "" + f);
					this.mAngle[0] += f * event.values[0];
					this.mAngle[1] += f * event.values[1];
					this.mAngle[2] += f * event.values[2];
					Log.i("mAngle[0]", ""+mAngle[0]);
					/*SensorInfo.this.updateGyroscope(
							(180.0f + SensorInfo.this.mAngle[0])
									* SensorInfo.this.mGyroMultiValue, // 200f or 9f
							(180.0f + SensorInfo.this.mAngle[1])
									* SensorInfo.this.mGyroMultiValue,
							(180.0f + SensorInfo.this.mAngle[2])
									* SensorInfo.this.mGyroMultiValue);*/
					
				}
				this.timestamp = event.timestamp;
				sensorBuilder = new StringBuilder();
				sensorBuilder.append("Value 0: ");
				sensorBuilder.append((int)((addition + this.mAngle[0])
						* SensorInfo.this.mGyroMultiValue));
				sensorBuilder.append("\nValue 1: ");
				sensorBuilder.append((int)((addition + this.mAngle[1])
						* SensorInfo.this.mGyroMultiValue));
				sensorBuilder.append("\nValue 2: ");
				sensorBuilder.append((int)((addition + this.mAngle[2])
						* SensorInfo.this.mGyroMultiValue));
				if (sensorBuilder != null)
					etGyroscope.setText(sensorBuilder.toString());
			}
			
			break;
		/*
		 * case Sensor.TYPE_LINEAR_ACCELERATION: onLinearAccel(event.values);
		 * 
		 * break; case Sensor.TYPE_GYROSCOPE_UNCALIBRATED: sensorBuilder = new
		 * StringBuilder(); sensorBuilder.append("Value 0: ");
		 * sensorBuilder.append(event.values[0]);
		 * sensorBuilder.append("\nValue 1: ");
		 * sensorBuilder.append(event.values[1]);
		 * sensorBuilder.append("\nValue 2: ");
		 * sensorBuilder.append(event.values[2]); if (sensorBuilder != null)
		 * etUncalGyroscope.setText(sensorBuilder.toString()); break;
		 */
		}
	}

	private void onLinearAccel(float[] values) {
		long curTime = System.currentTimeMillis();

		if (oLastUpdate == -1 || (curTime - oLastUpdate) > mCycle) {
			oLastUpdate = curTime;

			float oLastX = oX;

			float oLastY = oY;

			if (Math.abs(values[0]) < 0.2)
				oX = 0;
			else
				oX = values[0];
			if (Math.abs(values[1]) < 0.2)
				oY = 0;
			else
				oY = values[1];

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
		double lastX = x, lastY = y;

		x = oX;
		y = oY;

		if ((mAccuracyX >= 0 && Math.abs(x - lastX) > mAccuracyX)
				|| (mAccuracyY >= 0 && Math.abs(y - lastY) > mAccuracyY)) {
			sensorBuilder = new StringBuilder();
			sensorBuilder.append("Value 0: ");
			sensorBuilder.append(x);
			sensorBuilder.append("\nValue 1: ");
			sensorBuilder.append(y);
			sensorBuilder.append("\nValue 2: ");
			if (sensorBuilder != null)
				etAccelerometer.setText(sensorBuilder.toString());
			try {
				int xxx = (int) ((x - lastX) / 2);
				int yyy = (int) ((y - lastY) / 2);

				// this.sendMouseEvent(2, (float) xxx * 20, (float) yyy * 20);
			} catch (Exception e) {
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_GAME);
		/*mSensorManager.registerListener(this, mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mSensorManager
				.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED),
				SensorManager.SENSOR_DELAY_GAME);*/
	}
}
