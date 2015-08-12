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
package ua.at.tsvetkov.data_processor.requests;

import java.io.IOException;
import java.io.InputStream;

import ua.at.tsvetkov.data_processor.processors.Processor;
import android.content.Context;
import android.content.res.AssetManager;

/**
 * The main class for the assets file request building. If not specified the request be built with basic configuration parameters specified
 * in {@link ua.at.tsvetkov.data_processor.DataProcessorConfiguration DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public class AssetsRequest extends Request {

   private InputStream  inputStream;
   private AssetManager assetManager;

   public AssetsRequest(Context context) {
      assetManager = context.getAssets();
   }

   /**
    * Return new instance of AssetsRequest.
    * 
    * @return
    */
   public static AssetsRequest newInstance(Context context) {
      return new AssetsRequest(context);
   }

   /*
    * (non-Javadoc)
    * @see ua.at.tsvetkov.data_processor.requests.Request#getInputStream()
    */
   @Override
   public InputStream getInputStream() throws IOException {
      if (!isBuild())
         throw new IllegalArgumentException(REQUEST_IS_NOT_BUILDED);

      printToLogUrl();

      startTime = System.currentTimeMillis();

      inputStream = assetManager.open(toString());
      statusCode = Processor.FILE_SUCCESS;
      return inputStream;
   }

   /**
    * Set path
    * 
    * @param path
    */
   public AssetsRequest setPath(String path) {
      this.path = path;
      return this;
   }

   /**
    * Set full path
    * 
    * @param path
    */
   public AssetsRequest setFullPath(String path) {
      this.url = path;
      return this;
   }

   @Override
   public AssetsRequest addProgressDialog(Context context, String title, String message) {
      setupProgress(context,title,message);
      return this;
   }

   /**
    * Release resources associated with this request. You must call this, or significant resources (sockets and memory) may be leaked.
    */
   @Override
   public void close() throws Exception {
      if (inputStream != null)
         inputStream.close();
   }

}
