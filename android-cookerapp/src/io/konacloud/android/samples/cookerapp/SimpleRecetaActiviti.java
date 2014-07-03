package io.konacloud.android.samples.cookerapp;

import org.json.JSONException;
import org.json.JSONObject;
import org.kona.andorid.HTTPMethod;
import org.kona.andorid.KonaRequest;
import org.kona.andorid.KonaResponse;
import org.kona.andorid.buckets.KonaBucket;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SimpleRecetaActiviti extends Activity {

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_receta_activiti);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.url = extras.getString("url");
		}

		PlaceholderFragmentReceta fragment = new PlaceholderFragmentReceta();
		fragment.setArguments(extras);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_receta_activiti, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragmentReceta extends Fragment {

		public PlaceholderFragmentReceta() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			final String url = getArguments().getString("url");

			View rootView = inflater.inflate(
					R.layout.fragment_simple_receta_activiti, container, false);

			ImageView img = (ImageView) rootView.findViewById(R.id.imageView1);
			KonaBucket.getInstance().loadImage(url, img);

			final EditText text = (EditText) rootView
					.findViewById(R.id.editText1);
			Button btn = (Button) rootView.findViewById(R.id.button1);

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (text.getText() == null)
						return;

					final JSONObject json = new JSONObject();

					try {
						json.put("name", text.getText().toString());
						json.put("image", url);
						KonaRequest request = new KonaRequest() {
							{
								this.url = "http://app.konacloud.io/api/diego/CookerApp/mr_Recipie";
								this.method = HTTPMethod.POST;
								this.data = json.toString();
								this.accessToken = "89e36f59-2cf0-479d-a332-355393a1ff28";
							}

							@Override
							public void onSuccess(String jsonObject) {
								getActivity().finish();
							}

							@Override
							public void onFailure(KonaResponse res) {
								getActivity().finish();

							}
						};
						request.make();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			});
			return rootView;
		}
	}

}
