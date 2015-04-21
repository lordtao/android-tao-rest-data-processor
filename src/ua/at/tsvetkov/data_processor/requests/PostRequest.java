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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import ua.at.tsvetkov.data_processor.helpers.Scheme;
import ua.at.tsvetkov.util.Log;

public class PostRequest extends WebRequest {

   private List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
   private Header                   header;

   private PostRequest() {

   }

   /**
    * Return new instance of PostRequest. When building a {@link ua.at.tsvetkov.data_processor.requests.PostRequest PostRequest} methods
    * must be called first, before {@link ua.at.tsvetkov.data_processor.requests.Request Request} methods
    * 
    * @return
    */
   public static PostRequest newInstance() {
      return new PostRequest();
   }

   @Override
   public InputStream getInputStream() throws IOException {
      if (!isBuild()) {
         throw new IllegalArgumentException(REQUEST_IS_NOT_BUILDED);
      }
      startTime = System.currentTimeMillis();

      HttpConnectionParams.setConnectionTimeout(httpParameters, configuration.getTimeout());
      HttpConnectionParams.setSoTimeout(httpParameters, configuration.getTimeout());

      HttpPost httpPost = new HttpPost(toString());
      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
      httpPost.setParams(httpParameters);
      if (header != null) {
         httpPost.addHeader(header);
      }

      printToLogUrl();
      printToLogPairs();

      return getResponce(httpPost);
   }

   /**
    * Added POST parameter
    * 
    * @param name
    * @param value
    * @return
    */
   public PostRequest addPostParam(String name, String value) {
      nameValuePairs.add(new BasicNameValuePair(name, value));
      return this;
   }

   // ********************************************************************************

   /**
    * Directly assign full URL string. All other URL methods will be ignored
    * 
    * @param url
    */
   public PostRequest setUrl(String url) {
      this.url = url;
      return this;
   }

   /**
    * Set custom HttpParams.
    * 
    * @return
    */
   public PostRequest setHttpParameters(HttpParams httpParameters) {
      this.httpParameters = httpParameters;
      return this;
   }

   /**
    * Set custom HttpContext.
    * 
    * @return
    */
   public PostRequest setHttpContext(HttpContext httpContext) {
      this.httpContext = httpContext;
      return this;
   }

   /**
    * Set encoding
    * 
    * @param encoding
    */
   public PostRequest setEncoding(String encoding) {
      this.encoding = encoding;
      return this;
   }

   /**
    * Sets the scheme "http://".
    * 
    * @return
    */
   public PostRequest setSchemeHttp() {
      this.scheme = Scheme.HTTP.toString();
      return this;
   }

   /**
    * Sets the scheme "https://".
    * 
    * @return
    */
   public PostRequest setSchemeHttps() {
      this.scheme = Scheme.HTTPS.toString();
      return this;
   }

   /**
    * Sets the scheme "file://".
    * 
    * @return
    */
   public PostRequest setSchemeFile() {
      this.scheme = Scheme.HTTPS.toString();
      return this;
   }

   /**
    * Sets your scheme.
    * 
    * @param scheme
    * @return
    */
   public PostRequest setScheme(String scheme) {
      this.scheme = scheme;
      return this;
   }

   /**
    * Set User Info.
    * 
    * @param username
    * @param password
    * @return
    */
   public PostRequest setUserInfo(String username, String password) {
      this.username = username;
      this.password = password;
      return this;
   }

   /**
    * Set host.
    * 
    * @param host
    * @return
    */
   public PostRequest setHost(String host) {
      this.host = host;
      return this;
   }

   /**
    * Set port.
    * 
    * @param port
    * @return
    */
   public PostRequest setPort(String port) {
      this.port = port;
      return this;
   }

   /**
    * Set path
    * 
    * @param path
    * @return
    */
   public PostRequest setPath(String path) {
      this.path = path;
      return this;
   }

   public PostRequest setLogTag(String tag) {
      this.tag = tag;
      return this;
   }

   public PostRequest setHeader(Header header) {
      this.header = header;
      return this;
   }

   /**
    * Add to query GET parameter.
    * 
    * @param key
    * @param value
    * @return
    */
   public PostRequest addGetParam(String key, String value) {
      if (queries == null) {
         queries = new HashMap<String, String>();
      }
      queries.put(key, value);
      return this;
   }

   /**
    * Add to query GET parameter.
    * 
    * @param key
    * @param value
    * @return
    */
   public PostRequest addGetParam(String key, int value) {
      if (queries == null) {
         queries = new HashMap<String, String>();
      }
      queries.put(key, String.valueOf(value));
      return this;
   }

   /**
    * Add to query GET parameter.
    * 
    * @param key
    * @param value
    * @return
    */
   public PostRequest addGetParam(String key, long value) {
      if (queries == null) {
         queries = new HashMap<String, String>();
      }
      queries.put(key, String.valueOf(value));
      return this;
   }

   /**
    * Add to query GET parameter.
    * 
    * @param key
    * @param value
    * @return
    */
   public PostRequest addGetParam(String key, float value) {
      if (queries == null) {
         queries = new HashMap<String, String>();
      }
      queries.put(key, String.valueOf(value));
      return this;
   }

   /**
    * Add to query GET parameter.
    * 
    * @param key
    * @param value
    * @return
    */
   public PostRequest addGetParam(String key, double value) {
      if (queries == null) {
         queries = new HashMap<String, String>();
      }
      queries.put(key, String.valueOf(value));
      return this;
   }

   /**
    * Add fragment.
    * 
    * @param fragment
    * @return
    */
   public PostRequest addFragment(String fragment) {
      this.fragment = fragment;
      return this;
   }

   /**
    * Save received data to cache file. Skip it if exist.
    * 
    * @param cacheFileName
    */
   public PostRequest saveToCacheFile(String cacheFileName) {
      this.cacheFileName = cacheFileName;
      this.isRewriteFile = false;
      return this;
   }

   /**
    * Save received data to cache file. Rewrite it if exist.
    * 
    * @param cacheFileName
    */
   public PostRequest rewriteCacheFile(String cacheFileName) {
      this.cacheFileName = cacheFileName;
      this.isRewriteFile = true;
      return this;
   }

   protected void printToLogPairs() {
      if (configuration.isLogEnabled()) {
         for (int i = 0; i < nameValuePairs.size(); i++) {
            BasicNameValuePair pair = nameValuePairs.get(i);
            Log.v("ValuePair " + pair.getName() + " = " + pair.getValue());
         }
      }
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((nameValuePairs == null) ? 0 : nameValuePairs.hashCode());
      return result;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (!super.equals(obj)) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      PostRequest other = (PostRequest) obj;
      if (nameValuePairs == null) {
         if (other.nameValuePairs != null) {
            return false;
         }
      } else if (!nameValuePairs.equals(other.nameValuePairs)) {
         return false;
      }
      return true;
   }

}
