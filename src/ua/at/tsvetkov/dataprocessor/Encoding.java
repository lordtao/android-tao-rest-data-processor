package ua.at.tsvetkov.dataprocessor;

public enum Encoding {
	ISO_8859_1("ISO-8859-1"), US_ASCII("US-ASCII"), UTF_16("UTF-16"), UTF_16BE("UTF-16BE"), UTF_16LE("UTF-16LE"), UTF_8("UTF-8");

	private String	mEncoding;

	private Encoding(String encoding) {
		this.mEncoding = encoding;
	}

	public String getString() {
		return mEncoding;
	}

}
