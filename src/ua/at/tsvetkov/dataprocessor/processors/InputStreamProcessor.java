package ua.at.tsvetkov.dataprocessor.processors;

import java.io.InputStream;

import ua.at.tsvetkov.dataprocessor.interfaces.InputStreamDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.AbstractProcessor;

public class InputStreamProcessor extends AbstractProcessor {

	private InputStreamDataInterface	object;

	public InputStreamProcessor(InputStreamDataInterface obj) {
		object = obj;
	}

	@Override
	public void parse(InputStream inputStream) throws Exception {
		object.fillFromInputStream(inputStream);
	}

	@Override
	public Object getResult() {
		return object;
	}

}
