/**
 * 
 */
package ua.at.tsvetkov.dataprocessor;

/**
 * @author lordtao
 */
public final class DataProcessorConfiguration {

	public static final String	HTTP_ANDROID_USER_AGENT	= "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public static final int		DEFAULT_TIMEOUT			= 5000;

	protected boolean				isLogEnabled;
	protected boolean				isCheckingRequestStringEnabled;
	protected boolean				isShowProcessingTime;
	protected int					timeout;
	protected String				httpUserAgent;
	protected String				host;
	protected String				port;
	protected String				scheme;
	protected String				encoding;
	protected String				testServerUrl;

	private DataProcessorConfiguration(final Builder builder) {
		isLogEnabled = builder.isLogEnabled;
		timeout = builder.timeout;
		httpUserAgent = builder.httpUserAgent;
		host = builder.host;
		port = builder.port;
		scheme = builder.scheme;
		encoding = builder.encoding;
		isCheckingRequestStringEnabled = builder.isCheckingRequestStringEnabled;
		isShowProcessingTime = builder.isShowProcessingTime;
		testServerUrl = builder.testServerUrl;
	}

	public boolean isLogEnabled() {
		return isLogEnabled;
	}

	public boolean isCheckingRequestStringEnabled() {
		return isCheckingRequestStringEnabled;
	}

	public boolean isShowProcessingTime() {
		return isShowProcessingTime;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getHttpUserAgent() {
		return httpUserAgent;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getScheme() {
		return scheme;
	}

	public String getEncoding() {
		return encoding;
	}

	public String getTestServerUrl() {
		return testServerUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("DataProcessorConfiguration [isLogEnabled=");
		builder2.append(isLogEnabled);
		builder2.append(", isCheckingRequestStringEnabled=");
		builder2.append(isCheckingRequestStringEnabled);
		builder2.append(", isShowProcessingTime=");
		builder2.append(isShowProcessingTime);
		builder2.append(", timeout=");
		builder2.append(timeout);
		builder2.append(", httpUserAgent=");
		builder2.append(httpUserAgent);
		builder2.append(", host=");
		builder2.append(host);
		builder2.append(", port=");
		builder2.append(port);
		builder2.append(", scheme=");
		builder2.append(scheme);
		builder2.append(", encoding=");
		builder2.append(encoding);
		builder2.append(", testServerUrl=");
		builder2.append(testServerUrl);
		builder2.append("]");
		return builder2.toString();
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {

		private int			timeout									= 0;
		public boolean		isCheckingRequestStringEnabled	= false;
		private boolean	isLogEnabled							= false;
		public boolean		isShowProcessingTime					= false;
		private String		httpUserAgent							= null;
		private String		host										= null;
		private String		port										= null;
		private String		scheme									= null;
		private String		encoding									= null;
		public String		testServerUrl							= null;

		private Builder() {

		}

		public DataProcessorConfiguration build() {
			initWithDefaultValues();
			return new DataProcessorConfiguration(this);
		}

		public Builder setHttpUserAgent(String httpUserAgent) {
			this.httpUserAgent = httpUserAgent;
			return this;
		}

		public Builder setLogEnabled(boolean isEnabled) {
			isLogEnabled = isEnabled;
			return this;
		}

		public Builder setCheckingRequestStringEnabled(boolean isEnabled) {
			isCheckingRequestStringEnabled = isEnabled;
			return this;
		}

		public Builder setShowProcessingTime(boolean isEnabled) {
			isShowProcessingTime = isEnabled;
			return this;
		}

		public Builder setHost(String host) {
			this.host = host;
			return this;
		}

		public Builder setScheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		public Builder setPort(String port) {
			this.port = port;
			return this;
		}

		public Builder setTimeout(int timeout) {
			this.timeout = timeout;
			return this;
		}

		public Builder setTestServerUrl(String testServerUrl) {
			this.testServerUrl = testServerUrl;
			return this;
		}

		private void initWithDefaultValues() {
			if (host == null || host.length() == 0) {
				throw new IllegalArgumentException("Server host is empty. Set up it with setHost(String host) method.");
			}
			if (port == null) {
				port = "";
			}
			if (scheme == null) {
				scheme = Scheme.HTTP.getString();
			}
			if (encoding == null) {
				encoding = Encoding.UTF_8.getString();
			}
			if (timeout == 0) {
				timeout = DEFAULT_TIMEOUT;
			}
			if (httpUserAgent == null) {
				httpUserAgent = HTTP_ANDROID_USER_AGENT;
			}
			if (testServerUrl == null) {
				testServerUrl = scheme + host + port;
			}
		}

	}
}
