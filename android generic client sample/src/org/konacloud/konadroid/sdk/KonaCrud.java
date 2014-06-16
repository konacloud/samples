package org.konacloud.konadroid.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.konacloud.konadroid.CrudActivity.CrudViewFragment;
import org.konacloud.konadroid.MainActivity;
import org.konacloud.konadroid.sdk.data.KonaAtt;
import org.konacloud.konadroid.sdk.data.KonaItem;
import org.konacloud.konadroid.sdk.data.KonaView;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class KonaCrud {

	private ListFragment list;
	private Context context;
	private ListView selectedListView;
	private CrudViewFragment selected;
	private Spinner spinner;
	private boolean spinnerMultiple;

	public void insertListView(ListFragment list2, String url, Context con) {

		this.context = con;
		this.list = list2;
		CrudTaskGetViews task = new CrudTaskGetViews();

		task.execute(new String[] { Kona.buildUrlForCrud() });
	}

	private class CrudTaskGetViews extends
			AsyncTask<String, Void, List<KonaView>> {

		@Override
		protected List<KonaView> doInBackground(String... urls) {
			String url = urls[0];
			JSONObject json = KonaUtil.getJson(url);

			List<KonaView> list = new ArrayList<KonaView>();

			try {
				JSONArray views = json.getJSONArray("data");
				for (int i = 0; i < views.length(); i++) {
					JSONObject c = views.getJSONObject(i);
					KonaView view;
					try {
						view = new KonaView(c);
						list.add(view);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println(list);
			return list;
		}

		@Override
		protected void onPostExecute(List<KonaView> result) {

			final ArrayAdapter<KonaView> adapter = new ArrayAdapter<KonaView>(
					context, android.R.layout.simple_list_item_1, result);

			list.setListAdapter(adapter);
		}
	}

	private class CrudTaskCommit extends AsyncTask<String, Void, HttpResponse> {

		@Override
		protected HttpResponse doInBackground(String... urls) {
			String url = urls[0];
			String json = urls[1];

			return makeRequest(url, json);
		}

		@Override
		protected void onPostExecute(HttpResponse result) {

			InputStream content;
			try {
				content = result.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				String response = "";
				try {
					while ((s = buffer.readLine()) != null) {
						response += s;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println(response);
				JSONObject obj = new JSONObject(response);

				if (!obj.getBoolean("success")) {
					Toast.makeText(context, obj.getString("msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e1) {

				e1.printStackTrace();
			}

		}
	}

	public static HttpResponse makeRequest(String uri, String json) {
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(json));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			return new DefaultHttpClient().execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void commitForm(KonaView view, Map<KonaAtt, View> fields)
			throws JSONException {

		String url = Kona.buildUrlForCrudCommit(view.getModelId());

		JSONObject json = new JSONObject();

		for (KonaAtt att : fields.keySet()) {
			Object obj = "";
			if (att.isText()) {
				obj = ((EditText) fields.get(att)).getText().toString();
			} else if (att.isDate()) {
				obj = "12/12/2009";
			} else if (att.isBoolean()) {
				CheckBox ch = (CheckBox) fields.get(att);
				obj = ch.isChecked();
			} else {

			}
			json.put(att.getName(), obj);
		}

		System.out.println(json);
		CrudTaskCommit task = new CrudTaskCommit();
		task.execute(new String[] { url, json.toString() });

	}

	public void insertItemsFromModel(CrudViewFragment fragment, String modelId) {

		this.selected = fragment;

		CrudTaskList c = new CrudTaskList();
		c.execute(Kona.buildUrlForCrudCommit(modelId));
	}

	private class CrudTaskList extends
			AsyncTask<String, JSONObject, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... urls) {
			String url = urls[0];

			System.out.println("url for model "+url);
			JSONObject json = KonaUtil.getJson(url);
			System.out.println(json);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			try {
				JSONArray array = result.getJSONArray("data");
				List<KonaItem> list = new ArrayList<KonaItem>();
				for (int i = 0; i < array.length(); i++) {
					JSONObject c = array.getJSONObject(i);

					try {
						KonaItem item = new KonaItem(c, KonaUtil.view);
						list.add(item);
					} catch (Exception e) {
						// si 1 no anda, entonces no lo agregamos a la lista
						e.printStackTrace();
					}
				}

				System.out.println(list);
				final ArrayAdapter<KonaItem> adapter = new ArrayAdapter<KonaItem>(
						selected.getActivity(),
						android.R.layout.simple_list_item_1, list);

				selected.setListAdapter(adapter);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	private class CrudTaskEntityesFromModel extends
			AsyncTask<String, JSONObject, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... urls) {
			String url = urls[0];
			JSONArray json = KonaUtil.getJsonArray(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONArray result) {

			try {

				List<KonaItem> list = new ArrayList<KonaItem>();
				for (int i = 0; i < result.length(); i++) {
					JSONObject c = result.getJSONObject(i);
					try {
						JSONObject data = c.getJSONObject("data");
						KonaItem item = new KonaItem(data, null);
						list.add(item);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				final ArrayAdapter<KonaItem> adapter = new ArrayAdapter<KonaItem>(
						context, android.R.layout.simple_list_item_1, list);

				if (spinnerMultiple) {
					adapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);
				} else {
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				}

				spinner.setAdapter(adapter);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public void insertEntityesInSpinner(KonaAtt att, Spinner spinner,
			FragmentActivity activity, boolean b) {

		this.context = activity;
		this.spinner = spinner;
		this.spinnerMultiple = b;

		CrudTaskEntityesFromModel c = new CrudTaskEntityesFromModel();
		c.execute(Kona.buildUrlForListEntities(att.getType()));

	}
}
