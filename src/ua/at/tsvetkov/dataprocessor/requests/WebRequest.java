package ua.at.tsvetkov.dataprocessor.requests;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;

/**
 * The main class for the request building. If not specified the request be built with basic configuration parameters specified in
 * {@link ua.at.tsvetkov.dataprocessor.DataProcessorConfiguration DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public abstract class WebRequest extends Request {

	protected AndroidHttpClient	httpClient	= AndroidHttpClient.newInstance(configuration.getHttpUserAgent());
	protected HttpParams				httpParameters;

	protected WebRequest() {

	}

	@Override
	public abstract InputStream getInputStream() throws IOException;

	@Override
	public Request build() {
		if (httpParameters == null)
			httpParameters = new BasicHttpParams();
		return super.build();
	}

	/**
	 * Return HttpParams. If the request is not builded then will be return null.
	 * 
	 * @return
	 */
	public HttpParams getHttpParameters() {
		return httpParameters;
	}

	/**
	 * Release resources associated with this request. You must call this, or significant resources (sockets and memory) may be leaked.
	 */
	@Override
	public void close() {
		httpClient.close();
	}

}
