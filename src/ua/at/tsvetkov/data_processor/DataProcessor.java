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
package ua.at.tsvetkov.data_processor;

//import ua.at.tsvetkov.data_processor.interfaces.InputStreamDataInterface;
//import ua.at.tsvetkov.data_processor.interfaces.StringDataInterface;
import java.util.List;

import ua.at.tsvetkov.data_processor.processors.ProcessingCentre;
import ua.at.tsvetkov.data_processor.processors.ProcessingCentre.Callback;
import ua.at.tsvetkov.data_processor.requests.Request;
import ua.at.tsvetkov.data_processor.threads.DataProcessorThreadPool;
import ua.at.tsvetkov.netchecker.Net;
import ua.at.tsvetkov.netchecker.NetChecker;
import ua.at.tsvetkov.netchecker.NetStatus;
import ua.at.tsvetkov.util.Log;
import android.app.Activity;
import android.content.Context;

/**
 * @author lordtao
 */
public class DataProcessor {

   private static final String        ERROR_NOT_INIT              = "DataProcessor must be init with configuration before using.";
   private static final String        ERROR_INIT_CONFIG_WITH_NULL = "DataProcessor configuration can not be initialized with null.";
   private static final String        LOG_INIT_CONFIG             = "Initialize DataProcessor with configuration.";
   private static final String        WARNING_RE_INIT_CONFIG      = "Try to initialize DataProcessor which had already been initialized before.";

   private static DataProcessor       instance;

   private DataProcessorConfiguration configuration;
   private DataProcessorThreadPool    threadPool;

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
         if (configuration.isLogEnabled) {
            Log.d(LOG_INIT_CONFIG);
         }
         this.configuration = configuration;
      } else {
         Log.w(WARNING_RE_INIT_CONFIG);
      }
      if (configuration.isThreadPoolEnabled) {

         threadPool = new DataProcessorThreadPool();
      }
   }

   private synchronized void checkConfiguration() {
      if (configuration == null || configuration.httpUserAgent == null) {
         throw new IllegalStateException(ERROR_NOT_INIT);
      }
   }

   public synchronized NetStatus getInetStatus(Context context) {
      checkConfiguration();
      return NetChecker.checkNet(context, configuration.getTestServerUrl());
   }

   public synchronized boolean isSiteAccessible() {
      checkConfiguration();
      return Net.isAccessible(configuration.getTestServerUrl(), configuration.getTimeout());
   }

   public synchronized boolean isSiteAccessible(Activity activity) {
      checkConfiguration();
      return Net.isAccessible(activity, configuration.getTestServerUrl(), configuration.getTimeout());
   }

   public DataProcessorThreadPool getThreadPool() {
      return threadPool;
   }

   /**
    * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. Invocation has no
    * additional effect if already shut down.
    */
   public void shutdown() {
      threadPool.shutdown();
   }

   /**
    * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the tasks that were
    * awaiting execution. These tasks are drained (removed) from the task queue upon return from this method.
    * 
    * @return list of tasks that never commenced execution
    */
   public List<Runnable> shutdownNow() {
      return threadPool.shutdownNow();
   }

   // ******************************** Execution methods ********************************

   /**
    * Execute the request, process the results in instance of <b>clazz</b> and return result object
    * 
    * @param <T>
    * @param <T>
    * @param request
    * @param clazz
    * @return
    */
   public synchronized <T> T execute(Request request, Class<T> clazz) {
      checkConfiguration();
      return new ProcessingCentre<T>(this, request, clazz).execute();
   }

   /**
    * Execute async request, process the results in instance of <b>clazz</b> and return result in callback
    * 
    * @param <T>
    * @param <T>
    * @param request
    * @param clazz
    * @param handler
    */
   public synchronized <T> void executeAsync(Request request, Class<T> clazz) {
      checkConfiguration();
      new ProcessingCentre<T>(this, request, clazz, null).executeAsync();
   }

   /**
    * Execute async request, process the results in instance of <b>clazz</b> and return result in callback
    * 
    * @param <T>
    * @param request
    * @param clazz
    * @param handler
    */
   public synchronized <T> void executeAsync(Request request, Class<T> clazz, Callback<T> callback) {
      checkConfiguration();
      new ProcessingCentre<T>(this, request, clazz, callback).executeAsync();
   }

}
