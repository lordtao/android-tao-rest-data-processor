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

//import ua.at.tsvetkov.dataprocessor.interfaces.InputStreamDataInterface;
//import ua.at.tsvetkov.dataprocessor.interfaces.StringDataInterface;
import ua.at.tsvetkov.dataprocessor.requests.Request;
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

   // ******************************** Execution methods ********************************

   /**
    * Execute the request, process the results in instance of <b>clazz</b> and return result object
    * 
    * @param request
    * @param clazz
    * @return
    */
   public synchronized Object execute(Request request, Class<?> clazz) {
      checkConfiguration();
      return new ProcessingCentre(request, clazz).execute();
   }

   /**
    * Execute async request, process the results in instance of <b>clazz</b> and return result in callback
    * 
    * @param request
    * @param clazz
    * @param handler
    */
   public synchronized void executeAsync(Request request, Class<?> clazz) {
      checkConfiguration();
      new ProcessingCentre(request, clazz, null).executeAsync();
   }

   /**
    * Execute async request, process the results in instance of <b>clazz</b> and return result in callback
    * 
    * @param request
    * @param clazz
    * @param handler
    */
   public synchronized void executeAsync(Request request, Class<?> clazz, Callback callback) {
      checkConfiguration();
      new ProcessingCentre(request, clazz, callback).executeAsync();
   }

   public static interface Callback {

      /**
       * Runs on the UI thread.
       * 
       * @param obj
       * @param what
       */
      public abstract void onFinish(Object obj, int what);

   }

}
