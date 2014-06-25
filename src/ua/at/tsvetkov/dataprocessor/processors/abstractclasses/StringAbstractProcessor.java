package ua.at.tsvetkov.dataprocessor.processors.abstractclasses;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import ua.at.tsvetkov.util.Log;

/**
 * Base String parser. Can be implemented for parse JSON, CSV and etc. data.
 * 
 * @author lordtao
 */
public abstract class StringAbstractProcessor extends AbstractProcessor {

	@Override
	public void parse(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			Log.w("InputStream is null. Parsing aborted.");
			return;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		process(buffer.toString().trim());
	}

	@Override
	public abstract Object getResult();

	/**
	 * Processing the received string.
	 * 
	 * @param src
	 */
	public abstract void process(String src) throws Exception;

}
