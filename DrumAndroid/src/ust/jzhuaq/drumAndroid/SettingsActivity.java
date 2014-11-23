package ust.jzhuaq.drumAndroid;

import ust.jzhuaq.drumAndroid.util.Constants;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	TextView sensitivityView;
	AlertDialog.Builder sensitivityDialog;
	TextView senValueView; // Show the sensitivity value in dialog.
	SeekBar senSeekBar; // Set the sensitivity value in dialog.
	int currSeekBarProgress;

	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		settings = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
		editor = settings.edit();
		initSensitivity();
	}

	private void initSensitivity() {
		sensitivityView = (TextView) findViewById(R.id.tv_settingsSensitivity);
		sensitivityView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialogSensitivity();
			}
		});
	}

	private void showDialogSensitivity() {
		sensitivityDialog = new AlertDialog.Builder(this);
		sensitivityDialog.setTitle(R.string.sensitivity);
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_sensitivity,
				(ViewGroup) findViewById(R.id.layout_dialog), false);
		sensitivityDialog.setView(layout);
		senValueView = (TextView) layout.findViewById(R.id.tv_sensitivity);
		senSeekBar = (SeekBar) layout.findViewById(R.id.sb_sensitivity);

		senValueView.setText(Integer.toString(settings.getInt(
				Constants.PREFS_KEY_SENSITIVITY, Constants.SENSITIVY_DEFAULT)));
		senSeekBar.setProgress(settings.getInt(Constants.PREFS_KEY_SENSITIVITY,
				Constants.SENSITIVY_DEFAULT) - 1); // The value range of seekbar
													// is from 0 to 9, but actual
													// value is from 1 to 10.
		senSeekBar.setMax(9);
		senSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				currSeekBarProgress = progress + 1;
				senValueView.setText(Integer.toString(currSeekBarProgress));
			}
		});
		sensitivityDialog.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						saveValuesOfInteger(Constants.PREFS_KEY_SENSITIVITY,
								currSeekBarProgress);
					}
				});
		sensitivityDialog.setNegativeButton(R.string.cancel, null);
		sensitivityDialog.show();
	}

	private void saveValuesOfInteger(String key, int value) {
		if (editor != null) {
			editor.putInt(key, value);
			editor.commit();
		}
	}
}
