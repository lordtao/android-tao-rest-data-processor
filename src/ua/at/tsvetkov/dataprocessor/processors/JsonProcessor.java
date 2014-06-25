package ua.at.tsvetkov.dataprocessor.processors;

import org.json.JSONObject;

import ua.at.tsvetkov.dataprocessor.interfaces.JsonDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.StringAbstractProcessor;

public class JsonProcessor extends StringAbstractProcessor {

	private JsonDataInterface	object;

	public JsonProcessor(JsonDataInterface obj) {
		object = obj;
	}

	@Override
	public void process(String src) throws Exception {
		object.fillFromJSONObject(new JSONObject(src));
	}

	@Override
	public Object getResult() {
		return object;
	}

}
