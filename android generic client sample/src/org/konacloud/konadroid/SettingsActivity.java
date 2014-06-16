package org.konacloud.konadroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class SettingsActivity extends Activity {

	public static final String PREFS_NAME = "konacloud";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();

		setTitle("Settings");
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		final TextView ser = (TextView) findViewById(R.id.editText1);
		final TextView user = (TextView) findViewById(R.id.editText2);
		final TextView app = (TextView) findViewById(R.id.editText3);

		Button btn = (Button) findViewById(R.id.button1);

		String s = settings.getString("server", "http://kona.aws.af.cm/");
		String u = settings.getString("user", "");
		String a = settings.getString("app", "");

		ser.setText(s);
		user.setText(u);
		app.setText(a);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ser.getText() == null || user.getText() == null
						|| app.getText() == null) {

					return;
				}

				// armamos el kona settings

				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("server", ser.getText().toString());
				editor.putString("user", user.getText().toString());
				editor.putString("app", app.getText().toString());

				editor.commit();

				finish();
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
