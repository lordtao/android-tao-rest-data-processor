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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import android.net.http.AndroidHttpClient;

/**
 * Abstract class for a Web Request building using {@link android.net.http.AndroidHttpClient AndroidHttpClient}.</br> If not specified the
 * request be built with basic configuration parameters specified in {@link ua.at.tsvetkov.data_processor.DataProcessorConfiguration
 * DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public abstract class WebRequest extends Request {

   protected AndroidHttpClient httpClient;
   protected HttpParams        httpParameters;
   protected HttpContext       httpContext;

   public WebRequest() {
      httpClient = AndroidHttpClient.newInstance(configuration.getHttpUserAgent());
   }

   @Override
   public abstract InputStream getInputStream() throws IOException;

   @Override
   public Request build() {
      if (httpParameters == null)
         httpParameters = new BasicHttpParams();
      return super.build();
   }

   /**
    * Return HttpContext. If the request is not builded then will be return null.
    * 
    * @return
    */
   public HttpContext getHttpContext() {
      return httpContext;
   }

   /**
    * Return HttpParams. If the request is not builded then will be return null.
    * 
    * @return
    */
   public HttpParams getHttpParameters() {
      return httpParameters;
   }

   /**
    * Release resources associated with this request. You must call this, or significant resources (sockets and memory) may be leaked.
    */
   @Override
   public void close() throws Exception {
      httpClient.close();
   }

   protected InputStream getResponce(HttpUriRequest httpRequest) throws IOException {
      HttpResponse responce;
      if (httpContext == null)
         responce = httpClient.execute(httpRequest);
      else
         responce = httpClient.execute(httpRequest, httpContext);
      statusCode = responce.getStatusLine().getStatusCode();
      return responce.getEntity().getContent();
   }

}
