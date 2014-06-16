package org.konacloud.konadroid.sdk.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KonaView {

	private String modelId;
	private String visiblesColumns;
	private String visibleFormsFields;
	private String requiredFieds;
	private String captionFields;
	private String description;
	private List<KonaAtt> atributes = new ArrayList<KonaAtt>();

	
	public KonaView(JSONObject c) throws JSONException {

		JSONObject page = c.getJSONObject("page");

		modelId = page.getString("modelId");
		visiblesColumns = page.getString("visibleColumns");
		visibleFormsFields = page.getString("visibleProperties");
		requiredFieds = page.getString("requeriedProperties");
		captionFields = page.getString("captionProperties");

		JSONObject model = c.getJSONObject("_model");
		JSONObject modelData = model.getJSONObject("data");

		JSONArray array = modelData.getJSONArray("att");

		for (int i = 0; i < array.length(); i++) {
			JSONObject att = array.getJSONObject(i);
			KonaAtt atribute = new KonaAtt(att);
			atributes.add(atribute);
		}

	}

	public List<KonaAtt> getAtributes() {
		return atributes;
	}

	public void setAtributes(List<KonaAtt> atributes) {
		this.atributes = atributes;
	}

	@Override
	public String toString() {

		if (modelId != null) {
			return modelId;
		} else {
			return "N/A";
		}

	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getVisiblesColumns() {
		return visiblesColumns;
	}

	public void setVisiblesColumns(String visiblesColumns) {
		this.visiblesColumns = visiblesColumns;
	}

	public String getVisibleFormsFields() {
		return visibleFormsFields;
	}

	public void setVisibleFormsFields(String visibleFormsFields) {
		this.visibleFormsFields = visibleFormsFields;
	}

	public String getRequiredFieds() {
		return requiredFieds;
	}

	public void setRequiredFieds(String requiredFieds) {
		this.requiredFieds = requiredFieds;
	}

	public String getCaptionFields() {
		return captionFields;
	}

	public void setCaptionFields(String captionFields) {
		this.captionFields = captionFields;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
