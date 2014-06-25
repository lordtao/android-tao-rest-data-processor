/**
 * 
 */
package ua.at.tsvetkov.dataprocessor;

import ua.at.tsvetkov.application.AppConfig;
import ua.at.tsvetkov.dataprocessor.interfaces.InputStreamDataInterface;
import ua.at.tsvetkov.dataprocessor.interfaces.JsonDataInterface;
import ua.at.tsvetkov.dataprocessor.interfaces.StringDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.AbstractProcessor;
import ua.at.tsvetkov.dataprocessor.requests.Request;
import ua.at.tsvetkov.netchecker.Net;
import ua.at.tsvetkov.netchecker.NetChecker;
import ua.at.tsvetkov.netchecker.NetStatus;
import ua.at.tsvetkov.util.Log;
import android.app.Activity;
import android.os.Handler;

/**
 * @author lordtao
 */
public class DataProcessor {

	private static final String			ERROR_NOT_INIT						= "DataProcessor must be init with configuration before using.";
	private static final String			ERROR_INIT_CONFIG_WITH_NULL	= "DataProcessor configuration can not be initialized with null.";
	private static final String			LOG_INIT_CONFIG					= "Initialize DataProcessor with configuration.";
	private static final String			WARNING_RE_INIT_CONFIG			= "Try to initialize DataProcessor which had already been initialized before.";

	private static DataProcessor			instance;

	private DataProcessorConfiguration	configuration;

	public static DataProcessor getInstance() {
		if (instance == null) {
			synchronized (DataProcessor.class) {
				if (instance == null) {
					instance = new DataProcessor();
				}
			}
		}
		return instance;
	}

	public DataProcessorConfiguration getConfiguration() {
		return configuration;
	}

	public synchronized void init(DataProcessorConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
		}
		if (this.configuration == null) {
			if (configuration.isLogEnabled)
				Log.d(LOG_INIT_CONFIG);
			this.configuration = configuration;
		} else {
			Log.w(WARNING_RE_INIT_CONFIG);
		}
	}

	private synchronized void checkConfiguration() {
		if (configuration == null) {
			throw new IllegalStateException(ERROR_NOT_INIT);
		}
	}

	public synchronized NetStatus getInetStatus() {
		checkConfiguration();
		return NetChecker.checkNet(AppConfig.getContext(), configuration.getTestServerUrl());
	}

	public synchronized boolean isSiteAccessible() {
		checkConfiguration();
		return Net.isAccessible(configuration.getTestServerUrl(), configuration.getTimeout());
	}

	public synchronized boolean isSiteAccessible(Activity activity) {
		checkConfiguration();
		return Net.isAccessible(activity, configuration.getTestServerUrl(), configuration.getTimeout());
	}

	// ******************************** Parsing methods ********************************

	/**
	 * Execute the request without data processing and without waiting an answer
	 * 
	 * @param request
	 * @param parser
	 * @return
	 */
	public synchronized void execute(Request request) {
		checkConfiguration();
		new ProcessingCentre(request).execute();
	}

	/**
	 * Execute the request, process the results in parser and return result object
	 * 
	 * @param request
	 * @param abstractProcessor
	 * @return
	 */
	public synchronized Object execute(Request request, AbstractProcessor abstractProcessor) {
		checkConfiguration();
		return new ProcessingCentre(request, abstractProcessor).execute();
	}

	/**
	 * Execute async request without data processing and without waiting an answer
	 * 
	 * @param request
	 */
	public synchronized void executeAsync(Request request) {
		checkConfiguration();
		new ProcessingCentre(request).executeAsync();
	}

	/**
	 * Execute async request, return InputStream object in handler {@link android.os.Message Message} object
	 * 
	 * @param request
	 * @param handler
	 */
	public synchronized void executeAsync(Request request, Handler handler) {
		checkConfiguration();
		new ProcessingCentre(request, handler).executeAsync();
	}

	/**
	 * Execute async request, process the results in parser and return result object in handler {@link android.os.Message Message} object
	 * 
	 * @param request
	 * @param abstractProcessor
	 * @param handler
	 */
	public synchronized void executeAsync(Request request, AbstractProcessor abstractProcessor, Handler handler) {
		checkConfiguration();
		new ProcessingCentre(request, abstractProcessor, handler).executeAsync();
	}

	// ************************ JSON processing methods ************************

	/**
	 * @param request
	 * @param objFillingFromJson
	 * @return
	 */
	public synchronized Object execute(Request request, JsonDataInterface objFillingFromJson) {
		checkConfiguration();
		return new ProcessingCentre(request, objFillingFromJson).execute();
	}

	/**
	 * @param request
	 * @param objFillingFromJson
	 * @param handler
	 */
	public synchronized void executeAsync(Request request, JsonDataInterface objFillingFromJson, Handler handler) {
		checkConfiguration();
		new ProcessingCentre(request, objFillingFromJson, handler).executeAsync();
	}

	// ************************ String processing methods ************************

	/**
	 * @param request
	 * @param objFillingFromString
	 * @return
	 */
	public synchronized Object execute(Request request, StringDataInterface objFillingFromString) {
		checkConfiguration();
		return new ProcessingCentre(request, objFillingFromString).execute();
	}

	/**
	 * @param request
	 * @param objFillingFromString
	 * @param handler
	 */
	public synchronized void executeAsync(Request request, StringDataInterface objFillingFromString, Handler handler) {
		checkConfiguration();
		new ProcessingCentre(request, objFillingFromString, handler).executeAsync();
	}

	// ************************ InputStream processing methods ************************
	/**
	 * @param request
	 * @param objFillingFromString
	 * @return
	 */
	public synchronized Object execute(Request request, InputStreamDataInterface objFillingFromInputStream) {
		checkConfiguration();
		return new ProcessingCentre(request, objFillingFromInputStream).execute();
	}

	/**
	 * @param request
	 * @param objFillingFromString
	 * @param handler
	 */
	public synchronized void executeAsync(Request request, InputStreamDataInterface objFillingFromInputStream, Handler handler) {
		checkConfiguration();
		new ProcessingCentre(request, objFillingFromInputStream, handler).executeAsync();
	}

}
