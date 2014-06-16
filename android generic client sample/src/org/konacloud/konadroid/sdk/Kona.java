package org.konacloud.konadroid.sdk;

public class Kona {

	private String appId;
	private String server;
	private String userId;

	private static Kona me = new Kona();

	public void build() {

	}

	public static String buildUrlForCrud() {

		return me.server + "external/api/view/" + me.userId + "/" + me.appId;

	}

	public static void setUserId(String string) {
		me.userId = string;

	}

	public static void setAppId(String string) {
		me.appId = string;

	}

	public static void token(String string) {
		// TODO Auto-generated method stub

	}

	public static void setServer(String string) {
		me.server = string;

	}

	public static CharSequence getTitle() {

		return me.appId.toUpperCase();
	}

	public static String buildUrlForCrudCommit(String string) {
		return me.server + "api/" + me.userId + "/" + me.appId + "/mr_"
				+ string;
	}

	public static String buildUrlForListEntities(String type) {

		if (!type.contains("Collection"))
			return me.server + "external/api/entity/" + me.userId + "/"
					+ me.appId + "/" + type;

		String t = type.replace("Collection<", "");
		t = t.replace(">", "");
		return me.server + "external/api/entity/" + me.userId + "/" + me.appId
				+ "/" + t;

	}

}
