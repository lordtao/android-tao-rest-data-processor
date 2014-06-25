package ua.at.tsvetkov.dataprocessor.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonDataInterface {

	public void fillFromJSONObject(JSONObject jsonObject) throws JSONException;

}
