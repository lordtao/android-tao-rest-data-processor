/*******************************************************************************
 * Copyright (c) 2015 Alexandr Tsvetkov.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors:
 *     Alexandr Tsvetkov - initial API and implementation
 *
 * Project:
 *     tao-data-processor
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
package ua.at.tsvetkov.data_processor.threads;

import java.util.concurrent.ThreadFactory;

import ua.at.tsvetkov.util.Log;

/**
 * @author lordtao
 */
public class DataProcessingThreadFactory implements ThreadFactory {

   /**
    * Lower priority then UI thread priority (5)
    */
   private static final int    THREAD_PRIORITY = 4;
   public static final String THREAD_NAME     = "Data Processor async request";

   /*
    * (non-Javadoc)
    * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
    */
   @Override
   public Thread newThread(Runnable r) {
      Thread thread = new Thread(r);
      thread.setPriority(THREAD_PRIORITY);
      thread.setName(THREAD_NAME);
      thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

         @Override
         public void uncaughtException(Thread thread, Throwable ex) {
            Log.threadInfo(thread, ex);
         }

      });
      return thread;
   }

}
