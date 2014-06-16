package org.konacloud.konadroid.sdk.data;

import org.json.JSONException;
import org.json.JSONObject;

public class KonaAtt {

	private static final String STRING = "String";
	private static final String LONG = "Long";
	private static final String DOUBLE = "Double";
	private static final String BOOLEAN = "Boolean";
	private String name;
	private String type;

	public KonaAtt(JSONObject att) {
		try {
			name = att.getString("name");
			type = att.getString("type");
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isText() {

		if (type.equals(STRING) || type.equals(LONG) || type.equals(DOUBLE))
			return true;
		else {
			return false;
		}
	}

	public boolean isDate() {
		if (type.equals("Date"))
			return true;
		else {
			return false;
		}
	}

	public boolean isBoolean() {

		if (type.equals(BOOLEAN)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCustomType() {

		if (type.contains("Collection"))
			return false;

		if (type.equals(BOOLEAN) || type.equals(STRING) || type.equals(LONG)
				|| type.equals(DOUBLE))
			return false;
		
		return true;
	}

	public boolean isCollectionType() {
		if (type.contains("Collection"))
			return true;
		return false;
	}

}
