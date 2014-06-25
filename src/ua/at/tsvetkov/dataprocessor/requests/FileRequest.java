package ua.at.tsvetkov.dataprocessor.requests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ua.at.tsvetkov.util.Log;

/**
 * The main class for the file request building. If not specified the request be built with basic configuration parameters specified in
 * {@link ua.at.tsvetkov.dataprocessor.DataProcessorConfiguration DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public abstract class FileRequest extends Request {

	private FileInputStream	inputStream;

	protected FileRequest() {

	}

	/**
	 * Starts the request and returns a response data as InputStream
	 * 
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException {
		inputStream = new FileInputStream(new File(toString()));
		return inputStream;
	}

	@Override
	public Request build() {
		scheme = "";
		return super.build();
	}

	/**
	 * Release resources associated with this request. You must call this, or significant resources (sockets and memory) may be leaked.
	 */
	@Override
	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			Log.e(e);
		}
	}

}
