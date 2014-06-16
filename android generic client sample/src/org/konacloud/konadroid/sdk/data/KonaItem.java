package org.konacloud.konadroid.sdk.data;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class KonaItem {

	private String str;
	private JSONObject json;

	public KonaItem(JSONObject c, KonaView view) throws JSONException {
		json = c;

		// armamos el toString, como es para un list view tomamos como maximo
		// los 2 primeros del visibleColumn
		// mas de 2 seria mucho para un list view.

		if (view != null) {
			String columns = view.getVisiblesColumns();
			String[] split = columns.split(",");

			str = json.toString();

			if (split.length > 1) {

				if (json.has(split[0]) && json.has(split[1])) {
					str = json.get(split[0]) + ", " + json.get(split[1]);
				} else if (json.has(split[0])) {
					str = json.get(split[0]).toString();
				} else if (json.has(split[1])) {
					str = json.get(split[1]).toString();
				}
			} else if (split.length == 1) {

				if (json.has(split[0])) {
					str = json.get(split[0]).toString();
				}
			}
		} else {
			
			//obtenemos el primero
			Iterator it = json.keys();
			String name = it.next().toString();
			str = json.get(name).toString();
			if (it.hasNext()){
				name = it.next().toString();
				str = str + ", " + json.get(name).toString();
			}
			
		}

	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

}
