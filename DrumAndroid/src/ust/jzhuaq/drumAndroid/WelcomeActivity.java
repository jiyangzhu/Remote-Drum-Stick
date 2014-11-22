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
 * @author Bryce
 *
 */
public class WelcomeActivity extends ActionBarActivity {
	private EditText etAddress;
	private EditText etMSG;
	private String ipString;
	
	public static OSCPortOut sender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etMSG = (EditText) findViewById(R.id.etMSG);
	}
	public void connect(View v) {
		Log.i("TAG", "Click connect button.");
		Log.i("TAG", "Input IP is: " + etAddress.getText().toString());
		Log.i("TAG", "Port is: " + Constants.port);
		ipString = etAddress.getText().toString();
		InetAddressValidator iav = new InetAddressValidator();
		if((ipString != null)&&iav.isValid(ipString)){
			new establishConnect().execute(etAddress.getText().toString());
		} else {
			IpValidationDialog ipValidationDialog = new IpValidationDialog(this);
			ipValidationDialog.show();
		}
		
		/*
		 * try { sender = new
		 * OSCPortOut(InetAddress.getByName(etAddress.getText() .toString()),
		 * Config.port); Log.i("TAG", "Link connected"); } catch
		 * (SocketException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnknownHostException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		/*
		 * try { //socket = new Socket(etAddress.getText().toString(),
		 * Config.port); //Toast.makeText(this, "Link connected",
		 * Toast.LENGTH_SHORT).show(); Log.i("TAG", "Link connected"); } catch
		 * (UnknownHostException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

	}

	public void send(View v) {
		Log.i("TAG", "Input MSG is: " + etMSG.getText().toString());
		Collection<Object> args = new ArrayList<Object>();
		args.add(etMSG.getText().toString());

		//args[0] = etMSG.getText().toString();
		OSCMessage msg = new OSCMessage("/msg", args);

		new sendMsg().execute(msg);
	}

	private class establishConnect extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

				try {
					sender = new OSCPortOut(InetAddress.getByName(etAddress
							.getText().toString()), Constants.port);
					Log.i("TAG", "Link established");
					Intent i = new Intent();
					i.setClass(WelcomeActivity.this, ControlPanel.class);
					startActivity(i);
				} catch (SocketException | UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("OSC", "ERROR+ " + e);
				}
				

			Log.i("TAG", "Link connected");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.i("TAG", "Link connected2");
		}

	}

	public class sendMsg extends AsyncTask<OSCMessage, Void, String> {

		@Override
		protected String doInBackground(OSCMessage... params) {
			// TODO Auto-generated method stub

			try {
				
				sender.send(params[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("TAG", "MSG sent successfully.");

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.i("TAG", "MSG sent successfully.2");
		}

	}
}
