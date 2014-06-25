package ua.at.tsvetkov.dataprocessor;

/**
 * Protocol for URL like "http" or "file". This is also known as the scheme. The returned string is lower case.
 * @author lordtao
 *
 */
public enum Scheme { HTTP("http://"), HTTPS("https://"), FILE("file://");
	
	private String	mType;

	private Scheme(String type) {
		this.mType = type;
	}

	/**
	 * Returns the protocol for URL like "http://" or "file://". This is also known as the scheme. The returned string is lower case.
	 * @return
	 */
	public String getString() {
		return mType;
	}
}
