package ust.jzhuaq.drumAndroid.dialog;

import ust.jzhuaq.drumAndroid.R;
import android.app.AlertDialog;
import android.content.Context;
/**
 * A simple alert for invalid IP input
 * @author Bryce
 *
 */
public class IpValidationDialog extends AlertDialog.Builder {

	public IpValidationDialog(Context arg0) {
		super(arg0);
		super.setTitle(R.string.ip_invalid);
		super.setPositiveButton(R.string.ok, null);	//Do nothing
	}

}
