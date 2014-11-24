package ust.jzhuaq.drumAndroid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.validator.routines.InetAddressValidator;

import ust.jzhuaq.drumAndroid.dialog.IpValidationDialog;
import ust.jzhuaq.drumAndroid.util.Constants;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * The welcome activity allow user to connect PC by entering IP address
 * 
 * @author Bryce
 * 
 */
public class WelcomeActivity extends ActionBarActivity {
	private EditText etAddress;
	private String ipString;

	public static OSCPortOut sender;
	
	private SharedPreferences myPrefs;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		etAddress = (EditText) findViewById(R.id.etAddress);
		myPrefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
		editor = myPrefs.edit();
		etAddress.setText(myPrefs.getString(Constants.PREFS_KEY_IP, ""));
	}

	public void toControl(View v) {
		Log.i("TAG", "Click connect button.");
		Log.i("TAG", "Input IP is: " + etAddress.getText().toString());
		Log.i("TAG", "Port is: " + Constants.PORT);
		ipString = etAddress.getText().toString();
		InetAddressValidator iav = new InetAddressValidator();
		if ((ipString != null) && iav.isValid(ipString)) {
			new ControlConnect().execute(etAddress.getText().toString());
			editor.putString(Constants.PREFS_KEY_IP, etAddress.getText().toString());
			editor.commit();
		} else {
			IpValidationDialog ipValidationDialog = new IpValidationDialog(this);
			ipValidationDialog.show();
		}

	}
	public void toKeyboard(View v) {
		ipString = etAddress.getText().toString();
		InetAddressValidator iav = new InetAddressValidator();
		if ((ipString != null) && iav.isValid(ipString)) {
			new KeyboardConnect().execute(etAddress.getText().toString());
			editor.putString(Constants.PREFS_KEY_IP, etAddress.getText().toString());
			editor.commit();
		} else {
			IpValidationDialog ipValidationDialog = new IpValidationDialog(this);
			ipValidationDialog.show();
		}

	}
	
	/**
	 * Connect to PC and turn to control page.
	 * @author Bryce
	 *
	 */
	private class ControlConnect extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			try {
				sender = new OSCPortOut(InetAddress.getByName(etAddress
						.getText().toString()), Constants.PORT);
				sender.send(new OSCMessage(Constants.ADDRESS_SELECTOR_CONNECT));
				Log.i("TAG", "Link established");
				Intent i = new Intent();
				i.setClass(WelcomeActivity.this, ControlActivity.class);
				startActivity(i);
			} catch (SocketException | UnknownHostException e) {
				e.printStackTrace();
				Log.e("OSC", "ERROR+ " + e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("OSC", "ERROR+ " + e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			
		}

	}
	
	private class KeyboardConnect extends AsyncTask<String, Context, String>{

		@Override
		protected String doInBackground(String... params) {
			try {
				sender = new OSCPortOut(InetAddress.getByName(etAddress
						.getText().toString()), Constants.PORT);
				sender.send(new OSCMessage(Constants.ADDRESS_SELECTOR_CONNECT));
				Log.i("TAG", "Link established");
				Intent i = new Intent();
				i.setClass(WelcomeActivity.this, KeyboardActivity.class);
				startActivity(i);
			} catch (SocketException | UnknownHostException e) {
				e.printStackTrace();
				Log.e("OSC", "ERROR+ " + e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("OSC", "ERROR+ " + e);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Context... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
	}
}
