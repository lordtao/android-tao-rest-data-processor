package ua.at.tsvetkov.dataprocessor.requests;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import ua.at.tsvetkov.dataprocessor.DataProcessor;
import ua.at.tsvetkov.dataprocessor.DataProcessorConfiguration;
import ua.at.tsvetkov.util.Log;

/**
 * The main class for the request building. If not specified the request be built with basic configuration parameters specified in
 * {@link ua.at.tsvetkov.dataprocessor.DataProcessorConfiguration DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public abstract class Request {

	protected static final String				REQUEST_IS_NOT_BUILDED	= "Request is not builded";

	protected DataProcessorConfiguration	configuration				= DataProcessor.getInstance().getConfiguration();
	protected HashMap<String, String>		queries;
	protected StringBuilder						sb;
	protected String								scheme;
	protected String								url;
	protected String								username;
	protected String								password;
	protected String								host;
	protected String								port;
	protected String								path;
	protected String								fragment;
	protected String								encoding;
	protected String								fileName;
	protected String								tag;
	protected boolean								isRewriteFile;
	protected long									startTime;

	protected Request() {

	}

	/**
	 * Starts the request and returns a response data as InputStream
	 * 
	 * @return
	 * @throws IOException
	 */
	public abstract InputStream getInputStream() throws IOException;

	/**
	 * Release resources associated with this request.
	 */
	public abstract void close();

	/**
	 * Build prepared request.
	 * 
	 * @return
	 */
	public Request build() {
		sb = new StringBuilder();
		if (url == null) {
			if (scheme == null) {
				scheme = configuration.getScheme();
			}
			if (encoding == null) {
				encoding = configuration.getEncoding();
			}
			sb.append(scheme);
			if (username != null) {
				sb.append(username);
				sb.append(':');
				if (password != null && configuration.isLogEnabled()) {
					Log.w("Username is available in the request, but the password is not specified");
				}
				sb.append(password);
				sb.append('@');
			}
			if (host == null) {
				host = configuration.getHost();
			}
			sb.append(host);
			if (port == null) {
				port = configuration.getPort();
			}
			sb.append(port);
			if (path != null) {
				sb.append(checkForSlash(path));
			}
			if (queries != null) {
				sb.append('?');
				int count = queries.size();
				for (Entry<String, String> query : queries.entrySet()) {
					count--;
					sb.append(query.getKey());
					sb.append('=');
					sb.append(query.getValue());
					if (count > 0)
						sb.append('&');
				}
				if (fragment != null) {
					sb.append('#');
					sb.append(fragment);
				}
			}
		} else {
			sb.append(url);
		}
		return this;
	}

	/**
	 * Returns the name of the file to write request data.
	 * 
	 * @return
	 */
	public String getNewFileName() {
		return fileName;
	}

	/**
	 * Returns whether to overwrite received data request to file if it exist.
	 * 
	 * @return
	 */
	public boolean isNeedToRewriteFile() {
		return isRewriteFile;
	}

	/**
	 * Return start processing time
	 * 
	 * @return
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Checking query building
	 * 
	 * @return
	 */
	public boolean isBuild() {
		return sb != null;
	}

	/**
	 * Return composed URL string from parts or null if not builded
	 */
	@Override
	public String toString() {
		if (sb != null) {
			if (configuration.isCheckingRequestStringEnabled()) {
				try {
					return URLEncoder.encode(sb.toString(), encoding);
				} catch (UnsupportedEncodingException e) {
					Log.e("Wrong URL", e);
					return "";
				}
			} else {
				return sb.toString();
			}
		} else {
			if (configuration.isLogEnabled()) {
				Log.w("Request is not build and eq null.");
			}
			return null;
		}
	}

	protected void printToLogUrl() {
		if (configuration.isLogEnabled()) {
			if (tag == null)
				Log.v("-> CALL URL: " + toString());
			else
				Log.v("-> " + tag + " : " + toString());
		}
	}

	/**
	 * Checks the string for slash at the beginning of the string. Adds it if it does not.
	 * 
	 * @param src
	 * @return
	 */
	private String checkForSlash(String src) {
		if (src != null && !src.startsWith("/"))
			return '/' + src;
		else
			return src;
	}

}
