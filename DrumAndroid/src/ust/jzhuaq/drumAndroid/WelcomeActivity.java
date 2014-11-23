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
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		etAddress = (EditText) findViewById(R.id.etAddress);
	}

	public void connect(View v) {
		Log.i("TAG", "Click connect button.");
		Log.i("TAG", "Input IP is: " + etAddress.getText().toString());
		Log.i("TAG", "Port is: " + Constants.port);
		ipString = etAddress.getText().toString();
		InetAddressValidator iav = new InetAddressValidator();
		if ((ipString != null) && iav.isValid(ipString)) {
			new establishConnect().execute(etAddress.getText().toString());
		} else {
			IpValidationDialog ipValidationDialog = new IpValidationDialog(this);
			ipValidationDialog.show();
		}

	}

	private class establishConnect extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			try {
				sender = new OSCPortOut(InetAddress.getByName(etAddress
						.getText().toString()), Constants.port);
				Log.i("TAG", "Link established");
				Intent i = new Intent();
				i.setClass(WelcomeActivity.this, ControlPanel.class);
				startActivity(i);
			} catch (SocketException | UnknownHostException e) {
				e.printStackTrace();
				Log.e("OSC", "ERROR+ " + e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
		}

	}
}
