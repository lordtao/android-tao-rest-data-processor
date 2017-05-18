/*******************************************************************************
 * Copyright (c) 2014 Alexandr Tsvetkov.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p/>
 * Contributors:
 * Alexandr Tsvetkov - initial API and implementation
 * <p/>
 * Project:
 * TAO Data Processor
 * <p/>
 * License agreement:
 * <p/>
 * 1. This code is published AS IS. Author is not responsible for any damage that can be
 * caused by any application that uses this code.
 * 2. Author does not give a garantee, that this code is error free.
 * 3. This code can be used in NON-COMMERCIAL applications AS IS without any special
 * permission from author.
 * 4. This code can be modified without any special permission from author IF AND ONLY IF
 * this license agreement will remain unchanged.
 ******************************************************************************/
package ua.at.tsvetkov.data_processor.requests;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import ua.at.tsvetkov.data_processor.helpers.ConnectionConstants;
import ua.at.tsvetkov.util.Log;

/**
 * Abstract class for a Web Request building using {@link java.net.HttpURLConnection HttpURLConnection}.</br> If not specified the
 * request be built with basic configuration parameters specified in {@link ua.at.tsvetkov.data_processor.DataProcessorConfiguration
 * DataProcessorConfiguration}.
 *
 * @author lordtao
 */
public abstract class WebRequest extends Request {

   protected HttpURLConnection httpURLConnection;

   public WebRequest() {

   }

   protected BufferedInputStream getStream() {
      InputStream stream = null;
      try {
         stream = httpURLConnection.getInputStream();
      } catch (Exception e) {
         Log.w("Using ErrorStream data");
      }
      if (stream == null) {
         stream = httpURLConnection.getErrorStream();
      }
      return new BufferedInputStream(stream);
   }

   @Override
   public abstract InputStream getInputStream() throws IOException;

   @Override
   public Request build() {
      return super.build();
   }

   /**
    * Release resources associated with this request. You must call this, or significant resources (sockets and memory) may be leaked.
    */
   @Override
   public void close() throws Exception {
      httpURLConnection.disconnect();
   }


   /**
    * TODO
    *
    * @return
    */
   public HttpURLConnection getHttpURLConnection() {
      return httpURLConnection;
   }

   @Override
   public int getStatusCode() {
      try {
         statusCode = httpURLConnection.getResponseCode();
      } catch (IOException e) {
         Log.e("IO error during the retrieval response code.", e);
         statusCode = ConnectionConstants.NO_INTERNET_CONNECTION;
      }
      return statusCode;
   }

   @Override
   public String getStatusMessage() {
      String message = "";
      try {
         message = httpURLConnection.getResponseMessage();
      } catch (IOException e) {
         Log.e("IO error during the retrieval response message.", e);
         message = "No internet connection.";
      }
      return message;
   }

}
