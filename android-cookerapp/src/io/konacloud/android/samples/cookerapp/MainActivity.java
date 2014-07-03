package io.konacloud.android.samples.cookerapp;

import io.konacloud.android.samples.cookerapp.data.DataReceta;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kona.andorid.HTTPMethod;
import org.kona.andorid.KonaRequest;
import org.kona.andorid.KonaResponse;
import org.kona.andorid.buckets.KonaBucket;
import org.kona.andorid.buckets.KonaBucket.KonaCallBack;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	protected static final String ACCES_TOKEN = "89e36f59-2cf0-479d-a332-355393a1ff28";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {

			dispatchTakePictureIntent();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			final ListView listview = (ListView) rootView
					.findViewById(R.id.listView1);

			KonaRequest request = new KonaRequest() {
				{
					this.url = "https://app.konacloud.io/api/diego/CookerApp/mr_Recipie";
					this.method = HTTPMethod.GET;
					this.accessToken = ACCES_TOKEN;
				}

				@Override
				public void onSuccess(String jsonObject) {

					List<DataReceta> values = new ArrayList<DataReceta>();
					try {
						JSONArray arrayValues = new JSONArray(jsonObject);

						for (int i = 0; i < arrayValues.length(); i++) {

							JSONObject json = arrayValues.getJSONObject(i);

							DataReceta data = new DataReceta();
							data.setName(json.get("name").toString());
							data.setImg(json.get("image").toString());

							try {
								JSONArray ingredientes = json
										.getJSONArray("ingredients");
								String ing = uglyHack(ingredientes);
								data.setIngredientes(ing);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							values.add(data);
						}

						RecetasArrayAdapter adapter = new RecetasArrayAdapter(
								getActivity(), values);

						listview.setAdapter(adapter);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(KonaResponse res) {
					Toast.makeText(getActivity(), res.getMsg(),
							Toast.LENGTH_LONG).show();
				}
			};
			request.make();

			return rootView;
		}

		protected String uglyHack(JSONArray ingredientes) {
			String ing = ingredientes.toString();
			ing = ing.replace("[", "");
			ing = ing.replace("\"", "");
			ing = ing.replace("]", "");
			ing = ing.replace(",", " ,");
			return ing;
		}

		public class RecetasArrayAdapter extends ArrayAdapter<DataReceta> {
			private final Context context;
			private final List<DataReceta> values;

			public RecetasArrayAdapter(Context context, List<DataReceta> values) {
				super(context, R.layout.adapter_list_2_text, values);
				this.context = context;
				this.values = values;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowView = inflater.inflate(R.layout.adapter_list_2_text,
						parent, false);

				TextView textViewName = (TextView) rowView
						.findViewById(R.id.name);
				TextView textViewDescription = (TextView) rowView
						.findViewById(R.id.description);

				ImageView imageView = (ImageView) rowView
						.findViewById(R.id.img);

				System.out.println(values.get(position).getImg());
				try {
					new DownloadImageTask(imageView).execute((values
							.get(position).getImg()));
				} catch (Exception e) {
					e.printStackTrace();
				}

				String name = values.get(position).getName();
				String ingredientes = values.get(position).getIngredientes();

				textViewName.setText(name);
				textViewDescription.setText(ingredientes);

				return rowView;
			}

			private class DownloadImageTask extends
					AsyncTask<String, Void, Bitmap> {
				ImageView bmImage;

				public DownloadImageTask(ImageView bmImage) {
					this.bmImage = bmImage;
				}

				protected Bitmap doInBackground(String... urls) {
					String urldisplay = urls[0];
					Bitmap mIcon11 = null;
					try {
						InputStream in = new java.net.URL(urldisplay)
								.openStream();
						mIcon11 = BitmapFactory.decodeStream(in);
					} catch (Exception e) {
						Log.e("Error", e.getMessage());
						e.printStackTrace();
					}
					return mIcon11;
				}

				protected void onPostExecute(Bitmap result) {
					Bitmap resized2 = Bitmap.createScaledBitmap(result, 70, 70,
							true);

					bmImage.setImageBitmap(resized2);
				}
			}
		}
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;

	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			// mImageView.setImageBitmap(imageBitmap);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
			byte[] byte_arr = stream.toByteArray();

			try {

				KonaCallBack callback = new KonaCallBack() {

					@Override
					public void receive(String url) {
						Log.v(this.getClass().toString(), "url: " + url);

						Intent intent = new Intent(MainActivity.this,
								SimpleRecetaActiviti.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
				};
				KonaBucket
						.getInstance()
						.uploadImage(
								"http://bucket.konacloud.io/external/api/bucket/taio/hello/b1",
								byte_arr, callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
