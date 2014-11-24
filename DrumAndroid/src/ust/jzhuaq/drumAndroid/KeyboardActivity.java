package ust.jzhuaq.drumAndroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import ust.jzhuaq.drumAndroid.util.Constants;

import com.illposed.osc.OSCMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class KeyboardActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboard);
	}

	@Override
	protected void onResume() {
		super.onResume();
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
			i.setClass(KeyboardActivity.this, SettingsActivity.class);
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
		sendMouseEvent(Constants.EVENT_CLICK, 0, 0, 0);
	}
	
	public void clickEsc(View v) {
		sendKeyboardEvent(Constants.KEY_ESC);
	}
	public void clickTaskSwitch(View v) {
		sendKeyboardEvent(Constants.KEY_TASKSWITCH);
	}
	public void clickPgUp(View v) {
		sendKeyboardEvent(Constants.KEY_PGUP);
	}
	public void clickUp(View v) {
		sendKeyboardEvent(Constants.KEY_UP);
	}
	public void clickPgDn(View v) {
		sendKeyboardEvent(Constants.KEY_PGDN);
	}
	public void clickLeft(View v) {
		sendKeyboardEvent(Constants.KEY_LEFT);
	}
	public void clickDown(View v) {
		sendKeyboardEvent(Constants.KEY_DOWN);
	}
	public void clickRight(View v) {
		sendKeyboardEvent(Constants.KEY_RIGHT);
	}
	
	private void sendKeyboardEvent(int key){
		Collection<Object> args = new ArrayList<Object>();

		args.add(key);
		Log.i("key", ""+key);
		OSCMessage msg = new OSCMessage(Constants.ADDRESS_SELECTOR_KEYBOARD, args);
		new SendMsg().execute(msg);
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
