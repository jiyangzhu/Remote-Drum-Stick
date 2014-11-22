package ust.jzhuaq.drumAndroid.dialog;

import ust.jzhuaq.drumAndroid.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
/**
 * A simple alert for invalid IP input
 * @author Bryce
 *
 */
public class IpValidationDialog extends AlertDialog.Builder {

	public IpValidationDialog(Context arg0) {
		super(arg0);
		super.setTitle(R.string.ip_invalid);
		super.setPositiveButton(R.string.ok, null);
	}

}
