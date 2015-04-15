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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import ua.at.tsvetkov.data_processor.DataProcessor;
import ua.at.tsvetkov.data_processor.DataProcessorConfiguration;
import ua.at.tsvetkov.data_processor.helpers.Scheme;
import ua.at.tsvetkov.util.Const;
import ua.at.tsvetkov.util.Log;

/**
 * Abstract class for a Request building. If not specified the request be built with basic configuration parameters specified in
 * {@link ua.at.tsvetkov.data_processor.DataProcessorConfiguration DataProcessorConfiguration}.
 * 
 * @author lordtao
 */
public abstract class Request {

   private static final String          CALL_URL                  = " CALL URL: ";
   private static final String          REQUEST_IS_NOT_BUILD      = "Request is not build and eq null.";
   private static final String          WRONG_URL                 = "Wrong URL";
   private static final String          PASSWORD_IS_NOT_SPECIFIED = "Username is available in the request, but the password is not specified";
   protected static final String        CONFIGURATION_ERROR       = "DataProcessor configuration is not initialized.";
   protected static final String        REQUEST_IS_NOT_BUILDED    = "Request is not builded";

   protected DataProcessorConfiguration configuration             = DataProcessor.getInstance().getConfiguration();
   protected HashMap<String, String>    queries;
   protected StringBuilder              sb;
   protected String                     scheme;
   protected String                     url;
   protected String                     username;
   protected String                     password;
   protected String                     host;
   protected String                     port;
   protected String                     path;
   protected String                     fragment;
   protected String                     encoding;
   protected String                     cacheFileName;
   protected String                     tag;
   protected boolean                    isRewriteFile;
   protected long                       startTime;
   protected int                        statusCode;

   protected Request() {
      if (configuration == null || configuration.getHttpUserAgent() == null) {
         throw new IllegalStateException(CONFIGURATION_ERROR);
      }
   }

   /**
    * Starts the request and returns a response data as InputStream
    * 
    * @return
    * @throws IOException
    */
   public abstract InputStream getInputStream() throws IOException;

   /**
    * Release resources associated with this request.
    * 
    * @throws Exception
    */
   public abstract void close() throws Exception;

   /**
    * Build prepared request.
    * 
    * @return
    */
   public Request build() {
      sb = new StringBuilder();
      if (url == null) {
         if (scheme == null) {
            scheme = configuration.getScheme();
         }
         if (encoding == null) {
            encoding = configuration.getEncoding();
         }
         sb.append(scheme);
         if (username != null) {
            sb.append(username);
            sb.append(':');
            if (password != null && configuration.isLogEnabled()) {
               Log.w(PASSWORD_IS_NOT_SPECIFIED);
            }
            sb.append(password);
            sb.append('@');
         }
         if (host == null) {
            host = configuration.getHost();
         }
         sb.append(host);
         if (port == null) {
            port = configuration.getPort();
         }
         sb.append(port);
         if (path != null) {
            sb.append(checkForSlash(path));
         }
         if (queries != null) {
            sb.append('?');
            int count = queries.size();
            for (Entry<String, String> query : queries.entrySet()) {
               count--;
               sb.append(query.getKey());
               sb.append('=');
               sb.append(query.getValue());
               if (count > 0) {
                  sb.append('&');
               }
            }
            if (fragment != null) {
               sb.append('#');
               sb.append(fragment);
            }
         }
      } else {
         sb.append(url);
      }
      return this;
   }

   /**
    * Returns the name of cache file.
    * 
    * @return
    */
   public String getCacheFileName() {
      return cacheFileName;
   }

   /**
    * Returns whether to overwrite received data request to file if it exist.
    * 
    * @return
    */
   public boolean isNeedToRewriteFile() {
      return isRewriteFile;
   }

   /**
    * Return start processing time
    * 
    * @return
    */
   public long getStartTime() {
      return startTime;
   }

   /**
    * Checking query building
    * 
    * @return
    */
   public boolean isBuild() {
      return sb != null;
   }

   /**
    * Return status code. For example HTTP errors 401,403 or
    * 
    * @return
    */
   public int getStatusCode() {
      return statusCode;
   }

   /**
    * Return composed URL string from parts or null if not builded
    */
   @Override
   public String toString() {
      if (sb != null) {
         if (configuration.isCheckingRequestStringEnabled()) {
            try {
               return URLEncoder.encode(sb.toString(), encoding);
            } catch (UnsupportedEncodingException e) {
               Log.e(WRONG_URL, e);
               return "";
            }
         } else {
            return sb.toString();
         }
      } else {
         if (configuration.isLogEnabled()) {
            Log.w(REQUEST_IS_NOT_BUILD);
         }
         return null;
      }
   }

   /**
    * Print to log builded URL with tag.
    */
   protected void printToLogUrl() {
      if (configuration.isLogEnabled()) {
         if (tag == null) {
            Log.v(Const.AR_R + CALL_URL + toString());
         } else {
            Log.v(Const.AR_R + " " + tag + " : " + toString());
         }
      }
   }

   /**
    * Checks the string for slash at the beginning of the string. Adds it if it does not.
    * 
    * @param src
    * @return
    */
   private String checkForSlash(String src) {
      if (scheme.equals(Scheme.ASSETS.toString())) { // For ASSETS file names
         if (src.startsWith(File.separator)) {
            if (src.length() > 0) {
               return src.substring(1);
            } else {
               return "";
            }
         } else {
            return src;
         }
      }
      if (src != null && !src.startsWith("/")) {
         return '/' + src;
      } else {
         return src;
      }
   }

}
