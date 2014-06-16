package org.konacloud.konadroid;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.konacloud.konadroid.sdk.KonaCrud;
import org.konacloud.konadroid.sdk.KonaUtil;
import org.konacloud.konadroid.sdk.data.KonaAtt;
import org.konacloud.konadroid.sdk.data.KonaView;
import org.konacloud.konadroid.sdk.fields.DatePickerFragment;

import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CrudActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crud);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crud, menu);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			if (position == 0) {
				return new InputSectionSectionFragment();
			}

			CrudViewFragment fragment = new CrudViewFragment();
			// obtenemos el adapter y se lo seteamos

			KonaCrud k = new KonaCrud();
			k.insertItemsFromModel(fragment, KonaUtil.view.getModelId());
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "Add " + KonaUtil.view.getModelId();
			case 1:
				return "List of " + KonaUtil.view.getModelId();
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	public static class CrudViewFragment extends
			android.support.v4.app.ListFragment {

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {

			// KonaView view = (KonaView) l.getAdapter().getItem(position);
			// Abrimos la ventana asociada a este ABM, no es mas que otra lista
			// con todos los items y una opcion de agergar nuevos

		}

		public CrudViewFragment() {

		}

	}

	public static class InputSectionSectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_crud_dummy,
					container, false);

			LinearLayout verticalLayout = (LinearLayout) rootView
					.findViewById(R.id.relativa1);

			KonaView view = KonaUtil.view;

			final Map<KonaAtt, View> fields = new HashMap<KonaAtt, View>();

			for (KonaAtt att : view.getAtributes()) {
				View v = null;
				if (att.isText()) {
					v = buildTextField(att, getActivity());
				} else if (att.isDate()) {
					v = buildDateField(att, getActivity());
				} else if (att.isBoolean()) {
					v = buildCheckField(att, getActivity());
				} else if (att.isCustomType()) {
					v = buildCustomTypeField(att, getActivity());
				} else if (att.isCollectionType()) {
					v = buildCustomCollectionTypeField(att, getActivity());
				} else {
					v = buildTextField(att, getActivity());
				}
				verticalLayout.addView(v);
				fields.put(att, v);
			}

			Button btn = new Button(getActivity());
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// commit the form
					KonaCrud k = new KonaCrud();
					try {
						k.commitForm(KonaUtil.view, fields);
						getActivity().finish();
					} catch (JSONException e) {

						Toast.makeText(getActivity(),
								e.getMessage().toString(), Toast.LENGTH_SHORT);
					}
				}
			});

			btn.setText("Send");
			btn.setPadding(20, 20, 20, 20);

			verticalLayout.addView(btn);

			return rootView;
		}
	}

	public static View buildTextField(KonaAtt att, Context c) {
		EditText t1 = new EditText(c);
		t1.setHint(att.getName());
		t1.setPadding(20, 20, 20, 20);

		return t1;
	}

	public static View buildCustomTypeField(KonaAtt att,
			FragmentActivity activity) {

		// hay que llamar al servicio para traer a las entidades para el split
		Spinner spinner = new Spinner(activity,Spinner.MODE_DIALOG); //Spinner s1 = new Spinner(this, Spinner.MODE_DIALOG);
		KonaCrud k = new KonaCrud();
		k.insertEntityesInSpinner(att, spinner, activity, false);

		return spinner;
	}

	public static View buildCustomCollectionTypeField(KonaAtt att,
			FragmentActivity activity) {

		// hay que llamar al servicio para traer a las entidades para el split
		Spinner spinner = new Spinner(activity);
		KonaCrud k = new KonaCrud();
		k.insertEntityesInSpinner(att, spinner, activity, true);

		return spinner;
	}

	public static View buildCheckField(KonaAtt att, FragmentActivity c) {

		CheckBox t1 = new CheckBox(c);
		t1.setText(att.getName());
		t1.setPadding(20, 20, 20, 20);

		return t1;

	}

	public static View buildDateField(final KonaAtt att,
			final FragmentActivity c) {

		LinearLayout horizontalLayout = new LinearLayout(c);
		final TextView text = new TextView(c);

		final Calendar c1 = Calendar.getInstance();
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int day = c1.get(Calendar.DAY_OF_MONTH);

		String str = att.getName() + ": " + year + "/" + month + "/" + day;
		text.setText(str);

		Button btn = new Button(c);
		btn.setText("Set Date");

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DatePickerFragment newFragment = new DatePickerFragment();
				newFragment.setTextToUpdate(text);
				newFragment.setAtt(att);

				newFragment.show(c.getFragmentManager(), "datePicker");
			}
		});

		horizontalLayout.addView(text);
		horizontalLayout.addView(btn);

		text.setPadding(20, 20, 20, 20);
		btn.setPadding(20, 20, 20, 20);

		// horizontalLayout.setPadding(20, 20, 20, 20);

		return horizontalLayout;
	}

}
