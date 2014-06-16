package org.konacloud.konadroid.sdk.fields;

import java.util.Calendar;

import org.konacloud.konadroid.sdk.data.KonaAtt;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private TextView textUpdate;
	private KonaAtt att;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		
		String str = att.getName() + ": "+ year + "/" + month + "/" + day;
		textUpdate.setText(str);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {

		String str = year + "/" + month + "/" + day;
		textUpdate.setText(str);
	}

	public void setTextToUpdate(TextView text) {

		this.textUpdate = text;
	}

	public void setAtt(KonaAtt att) {
		
		this.att = att;
	}
}
