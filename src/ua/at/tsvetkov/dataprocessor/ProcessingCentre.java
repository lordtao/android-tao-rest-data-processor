/*******************************************************************************
 * Copyright (c) 2014 Alexandr Tsvetkov.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors:
 *     Alexandr Tsvetkov - initial API and implementation
 *
 * Project:
 *     TAO Data Processor
 *
 * License agreement:
 *
 * 1. This code is published AS IS. Author is not responsible for any damage that can be
 *    caused by any application that uses this code.
 * 2. Author does not give a garantee, that this code is error free.
 * 3. This code can be used in NON-COMMERCIAL applications AS IS without any special
 *    permission from author.
 * 4. This code can be modified without any special permission from author IF AND ONLY IF
 *    this license agreement will remain unchanged.
 ******************************************************************************/
package ua.at.tsvetkov.dataprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;

import org.apache.http.HttpStatus;

import ua.at.tsvetkov.dataprocessor.DataProcessor.Callback;
import ua.at.tsvetkov.dataprocessor.interfaces.InputStreamDataInterface;
import ua.at.tsvetkov.dataprocessor.interfaces.StringDataInterface;
import ua.at.tsvetkov.dataprocessor.processors.InputStreamProcessor;
import ua.at.tsvetkov.dataprocessor.processors.StringProcessor;
import ua.at.tsvetkov.dataprocessor.processors.abstractclasses.AbstractProcessor;
import ua.at.tsvetkov.dataprocessor.requests.Request;
import ua.at.tsvetkov.util.Const;
import ua.at.tsvetkov.util.Log;
import android.os.Handler;

public class ProcessingCentre {

   private static final String INVALID_CLASS_PARAMETER = "Invalid class parameter. A class for data processing must implement InputStreamDataInterface or StringDataInterface either extend AbstractProcessor";
   private static final String INVALID_PARAMETER       = "Invalid parameter. Request or Class can't be eq null";
   public static final int     BUFFER                  = 8 * Const.KB;
   /**
    * File was opened success.
    */
   public static final int     FILE_SUCCESS            = 0;
   /**
    * Processing ERROR code.
    */
   public static final int     ERROR                   = -1;

   private Request             request;
   private AbstractProcessor   processor;
   private InputStream         inputStream;
   private String              cacheFileName;
   private Class<?>            clazz;
   private Callback            callback;
   private Thread              thread;
   private final Handler       handler                 = new Handler();

   /**
    * @param request
    * @param clazz
    */
   public ProcessingCentre(Request request, Class<?> clazz) {
      if (request == null || clazz == null) {
         throw new InvalidParameterException(INVALID_PARAMETER);
      }
      this.request = request;
      this.clazz = clazz;
      this.callback = null;
      thread = Thread.currentThread();
   }

   /**
    * @param request
    * @param clazz
    * @param callback
    */
   public ProcessingCentre(Request request, Class<?> clazz, Callback callback) {
      if (request == null || clazz == null) {
         throw new InvalidParameterException(INVALID_PARAMETER);
      }
      this.request = request;
      this.clazz = clazz;
      this.callback = callback;
      thread = Thread.currentThread();
   }

   public Object execute() {
      if (!isCorrectClass()) {
         throw new InvalidParameterException(INVALID_CLASS_PARAMETER);
      }
      cacheFileName = request.getCacheFileName();
      try {
         if (cacheFileName != null && cacheFileName.length() > 0) {
            saveToFile();
         } else {
            inputStream = request.getInputStream();
         }
         if (request.getStatusCode() == FILE_SUCCESS || request.getStatusCode() == HttpStatus.SC_OK) {
            createProcessor();
         }
         if (processor != null) {
            processor.parse(inputStream);
            sendMessage(request.getStatusCode(), processor.getResult());
         } else {
            sendMessage(request.getStatusCode(), inputStream);
         }
      } catch (Exception e) {
         if (DataProcessor.getInstance().getConfiguration().isLogEnabled()) {
            Log.e(e);
         }
         sendMessage(ERROR, e);
      } finally {
         try {
            inputStream.close();
            request.close();
         } catch (Exception e) {
            Log.e(e);
         }
      }
      if (DataProcessor.getInstance().getConfiguration().isLogEnabled() && DataProcessor.getInstance().getConfiguration().isShowProcessingTime()) {
         long time = System.currentTimeMillis() - request.getStartTime();
         Log.v("Processing time = " + time + " ms. [ " + request + " ]");
      }
      return processor.getResult();
   }

   /**
	 * 
	 */
   @SuppressWarnings("rawtypes")
   private void createProcessor() {
      try {
         if (AbstractProcessor.class.isAssignableFrom(clazz)) {
            Constructor[] construct = clazz.getDeclaredConstructors();
            processor = (AbstractProcessor) construct[0].newInstance();
         } else if (InputStreamDataInterface.class.isAssignableFrom(clazz)) {
            Constructor[] construct = clazz.getDeclaredConstructors();
            processor = new InputStreamProcessor((InputStreamDataInterface) construct[0].newInstance());
         } else if (StringDataInterface.class.isAssignableFrom(clazz)) {
            Constructor[] construct = clazz.getDeclaredConstructors();
            processor = new StringProcessor((StringDataInterface) construct[0].newInstance());
         } else
            Log.e(INVALID_CLASS_PARAMETER);
      } catch (Exception e) {
         Log.e("Can't create " + clazz.getCanonicalName(), e);
      }
   }

   /**
    * @return
    */
   private boolean isCorrectClass() {
      if (InputStreamDataInterface.class.isAssignableFrom(clazz)) {
         return true;
      } else if (StringDataInterface.class.isAssignableFrom(clazz)) {
         return true;
      } else if (AbstractProcessor.class.isAssignableFrom(clazz)) {
         return true;
      } else
         return false;
   }

   private void sendMessage(final int statusCode, final Object obj) {
      if (callback != null) {
         if (thread == Thread.currentThread()) {
            callback.onFinish(obj, statusCode);
         } else {
            handler.post(new Runnable() {

               @Override
               public void run() {
                  callback.onFinish(obj, statusCode);
               }
            });
         }
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
      File f = new File(cacheFileName);
      if (!request.isNeedToRewriteFile() && f.exists() && f.length() > 0) {
         if (DataProcessor.getInstance().getConfiguration().isLogEnabled()) {
            Log.w("File exist: " + cacheFileName);
         }
         return;
      }
      FileOutputStream out = new FileOutputStream(cacheFileName);
      byte[] buffer = new byte[BUFFER];
      int bytesRead = -1;
      inputStream = request.getInputStream();
      while ((bytesRead = inputStream.read(buffer)) != -1) {
         out.write(buffer, 0, bytesRead);
      }
      out.flush();
      out.close();
      inputStream.close();
      inputStream = new FileInputStream(cacheFileName);
   }

}
