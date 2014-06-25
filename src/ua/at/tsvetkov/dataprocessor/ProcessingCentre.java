package ua.at.tsvetkov.dataprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ua.at.tsvetkov.dataprocessor.interfaces.InputStreamDataInterface;
import ua.at.tsvetkov.dataprocessor.interfaces.JsonDataInterface;
import ua.at.tsvetkov.dataprocessor.interfaces.StringDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.InputStreamProcessor;
import ua.at.tsvetkov.dataprocessor.processors.JsonProcessor;
import ua.at.tsvetkov.dataprocessor.processors.StringProcessor;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.AbstractProcessor;
import ua.at.tsvetkov.dataprocessor.requests.Request;
import ua.at.tsvetkov.util.Log;
import ua.at.tsvetkov.util.Const;
import android.os.Handler;
import android.os.Message;

public class ProcessingCentre {

	public static final int		BUFFER	= 8 * Const.KB;
	public static final int		SUCCESS	= 0;
	public static final int		ERROR		= 1;

	private Request				request;
	private Handler				handler;
	private AbstractProcessor	abstractProcessor;
	private InputStream			inputStream;
	private String	fileName;

	public ProcessingCentre(Request request, AbstractProcessor abstractProcessor, Handler handler) {
		this.request = request;
		this.abstractProcessor = abstractProcessor;
		this.handler = handler;
	}

	public ProcessingCentre(Request request, AbstractProcessor abstractProcessor) {
		this.request = request;
		this.abstractProcessor = abstractProcessor;
		this.handler = null;
	}

	public ProcessingCentre(Request request, Handler handler) {
		this.request = request;
		this.abstractProcessor = null;
		this.handler = handler;
	}

	public ProcessingCentre(Request request) {
		this.request = request;
		this.abstractProcessor = null;
		this.handler = null;
	}

	public ProcessingCentre(Request request, JsonDataInterface objFillingFromJson) {
		this.request = request;
		this.abstractProcessor = new JsonProcessor(objFillingFromJson);
		this.handler = null;
	}

	public ProcessingCentre(Request request, JsonDataInterface objFillingFromJson, Handler handler) {
		this.request = request;
		this.abstractProcessor = new JsonProcessor(objFillingFromJson);
		this.handler = handler;
	}

	public ProcessingCentre(Request request, StringDataInterface objFillingFromString) {
		this.request = request;
		this.abstractProcessor = new StringProcessor(objFillingFromString);
		this.handler = null;
	}

	public ProcessingCentre(Request request, StringDataInterface objFillingFromString, Handler handler) {
		this.request = request;
		this.abstractProcessor = new StringProcessor(objFillingFromString);
		this.handler = handler;
	}

	public ProcessingCentre(Request request, InputStreamDataInterface objFillingFromInputStream) {
		this.request = request;
		this.abstractProcessor = new InputStreamProcessor(objFillingFromInputStream);
		this.handler = null;
	}

	public ProcessingCentre(Request request, InputStreamDataInterface objFillingFromInputStream, Handler handler) {
		this.request = request;
		this.abstractProcessor = new InputStreamProcessor(objFillingFromInputStream);
		this.handler = handler;
	}

	public Object execute() {
		fileName = request.getNewFileName();
		try {
			if (fileName != null && fileName.length() > 0) {
				saveToFile();
			} else {
				inputStream = request.getInputStream();
			}
			if (abstractProcessor != null) {
				abstractProcessor.parse(inputStream);
				sendMessage(SUCCESS, abstractProcessor.getResult());
			} else {
				sendMessage(SUCCESS, inputStream);
			}
		} catch (Exception e) {
			Log.e(e);
			sendMessage(ERROR, null);
		} finally {
			try {
				inputStream.close();
				request.close();
			} catch (IOException e) {
				Log.e(e);
			}
		}
		if (DataProcessor.getInstance().getConfiguration().isLogEnabled() && DataProcessor.getInstance().getConfiguration().isShowProcessingTime()) {
			long time = System.currentTimeMillis() - request.getStartTime();
			Log.v("Processing time = " + (float) (time / 1000) + "ms. [ " + request + " ]");
		}
		return abstractProcessor.getResult();
	}

	private void sendMessage(int result, Object what) {
		if (handler != null) {
			Message msg = new Message();
			msg.what = result;
			msg.obj = what;
			handler.sendMessage(msg);
		}
	}

	public void executeAsync() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				execute();
			}

		}, "").start();

	}

	private void saveToFile() throws IOException {
		File f = new File(fileName);
		if (!request.isNeedToRewriteFile() && f.exists() && f.length() > 0) {
			if (DataProcessor.getInstance().getConfiguration().isLogEnabled()) {
				Log.w("File exist: " + fileName);
			}
			return;
		}
		FileOutputStream out = new FileOutputStream(fileName);
		byte[] buffer = new byte[BUFFER];
		int bytesRead = -1;
		inputStream = request.getInputStream();
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		out.flush();
		out.close();
		inputStream.close();
		inputStream = new FileInputStream(fileName);
	}

}
