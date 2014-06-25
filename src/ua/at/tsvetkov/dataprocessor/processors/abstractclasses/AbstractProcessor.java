package ua.at.tsvetkov.dataprocessor.processors.abstractclasses;

import java.io.InputStream;

public abstract class AbstractProcessor {

	/**
	 * Parse the input stream with implemented parser.
	 * 
	 * @param inputStream
	 * @throws Exception
	 */
	public abstract void parse(InputStream inputStream) throws Exception;

	/**
	 * Returns the result of processing query.
	 * 
	 * @return resulting object
	 */
	public abstract Object getResult();

}
