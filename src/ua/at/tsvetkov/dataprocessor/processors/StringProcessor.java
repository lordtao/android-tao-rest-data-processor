package ua.at.tsvetkov.dataprocessor.processors;

import ua.at.tsvetkov.dataprocessor.interfaces.StringDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.StringAbstractProcessor;

public class StringProcessor extends StringAbstractProcessor {

	private StringDataInterface	object;

	public StringProcessor(StringDataInterface obj) {
		object = obj;
	}

	@Override
	public void process(String src) throws Exception {
		object.fillFromString(src);
	}

	@Override
	public Object getResult() {
		return object;
	}

}
