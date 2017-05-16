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
package ua.at.tsvetkov.data_processor;

import ua.at.tsvetkov.data_processor.helpers.Encoding;
import ua.at.tsvetkov.data_processor.helpers.Scheme;
import ua.at.tsvetkov.util.Log;

/**
 * @author lordtao
 */
public final class DataProcessorConfiguration {

   public static final String HTTP_ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
   public static final int DEFAULT_TIMEOUT = 5000;
   public static final int DEFAULT_CACHE_SIZE = 10;

   protected boolean isLogEnabled;
   protected boolean isCheckingRequestStringEnabled;
   protected boolean isShowProcessingTime;
   protected boolean isThreadPoolEnabled;
   protected int timeout;
   protected String httpUserAgent;
   protected String host;
   protected String port;
   protected String scheme;
   protected String encoding;
   protected String testServerUrl;
   private boolean isCacheEnabled;
   private int cacheSize;

   private DataProcessorConfiguration(final Builder builder) {
      isLogEnabled = builder.isLogEnabled;
      httpUserAgent = builder.httpUserAgent;
      timeout = builder.timeout;
      isThreadPoolEnabled = builder.isThreadPoolEnabled;
      host = builder.host;
      port = builder.port;
      scheme = builder.scheme;
      encoding = builder.encoding;
      isCheckingRequestStringEnabled = builder.isCheckingRequestStringEnabled;
      isShowProcessingTime = builder.isShowProcessingTime;
      testServerUrl = builder.testServerUrl;
      isCacheEnabled = builder.isCacheEnabled;
      cacheSize = builder.cacheSize;
      if (isLogEnabled) {
         Log.i("========= Data Processor Configuration ==========");
         Log.i("Host = " + scheme + host + port);
         Log.i("Test url = " + testServerUrl);
         Log.i("isShowProcessingTime = " + isShowProcessingTime);
         if (isCacheEnabled) {
            Log.i("isCacheEnabled = true, cacheSize = " + cacheSize);
         } else {
            Log.i("isCacheEnabled = false");
         }
         Log.i("httpUserAgent = " + httpUserAgent);
         Log.i("=================================================");
      }
   }

   public boolean isLogEnabled() {
      return isLogEnabled;
   }

   public boolean isCheckingRequestStringEnabled() {
      return isCheckingRequestStringEnabled;
   }

   public boolean isShowProcessingTime() {
      return isShowProcessingTime;
   }

   public boolean isCacheEnabled() {
      return isCacheEnabled;
   }

   public int getTimeout() {
      return timeout;
   }

   public int getCacheSize() {
      return cacheSize;
   }

   public String getHttpUserAgent() {
      return httpUserAgent;
   }

   public String getHost() {
      return host;
   }

   public String getPort() {
      return port;
   }

   public String getScheme() {
      return scheme;
   }

   public String getEncoding() {
      return encoding;
   }

   public String getTestServerUrl() {
      return testServerUrl;
   }

   @Override
   public String toString() {
      StringBuilder builder2 = new StringBuilder();
      builder2.append("DataProcessorConfiguration [isLogEnabled=");
      builder2.append(isLogEnabled);
      builder2.append(", isCheckingRequestStringEnabled=");
      builder2.append(isCheckingRequestStringEnabled);
      builder2.append(", isShowProcessingTime=");
      builder2.append(isShowProcessingTime);
      builder2.append(", timeout=");
      builder2.append(timeout);
      builder2.append(", httpUserAgent=");
      builder2.append(httpUserAgent);
      builder2.append(", host=");
      builder2.append(host);
      builder2.append(", port=");
      builder2.append(port);
      builder2.append(", scheme=");
      builder2.append(scheme);
      builder2.append(", encoding=");
      builder2.append(encoding);
      builder2.append(", testServerUrl=");
      builder2.append(testServerUrl);
      builder2.append("]");
      return builder2.toString();
   }

   public static Builder getBuilder() {
      return new Builder();
   }

   public static class Builder {

      public int cacheSize = DEFAULT_CACHE_SIZE;
      public boolean isCacheEnabled = true;
      public boolean isThreadPoolEnabled = true;
      private int timeout = 0;
      public boolean isCheckingRequestStringEnabled = false;
      private boolean isLogEnabled = true;
      public boolean isShowProcessingTime = true;
      private String httpUserAgent = null;
      private String host = null;
      private String port = null;
      private String scheme = null;
      private String encoding = null;
      public String testServerUrl = null;

      private Builder() {

      }

      public DataProcessorConfiguration build() {
         initWithDefaultValues();
         return new DataProcessorConfiguration(this);
      }

      public DataProcessorConfiguration buildForAssets() {
         initWithAssetsValues();
         return new DataProcessorConfiguration(this);
      }

      public Builder setHttpUserAgent(String httpUserAgent) {
         this.httpUserAgent = httpUserAgent;
         return this;
      }

      public Builder setLogEnabled(boolean isEnabled) {
         isLogEnabled = isEnabled;
         return this;
      }

      public Builder setThreadPoolEnabled(boolean isEnabled) {
         isThreadPoolEnabled = isEnabled;
         return this;
      }

      public Builder setCheckingRequestStringEnabled(boolean isEnabled) {
         isCheckingRequestStringEnabled = isEnabled;
         return this;
      }

      public Builder setShowProcessingTime(boolean isEnabled) {
         isShowProcessingTime = isEnabled;
         return this;
      }

      public Builder setCacheEnabled(boolean isEnabled) {
         isCacheEnabled = isEnabled;
         return this;
      }

      public Builder setCacheSize(int size) {
         cacheSize = size;
         return this;
      }

      public Builder setHost(String host) {
         if (host.endsWith("/")) {
            this.host = host.substring(0, host.length() - 1);
         } else {
            this.host = host;
         }
         if (!isSchemePresent(Scheme.HTTP, host)) {
            if (!isSchemePresent(Scheme.HTTPS, host)) {
               if (!isSchemePresent(Scheme.FILE, host)) {
                  this.scheme = Scheme.ASSETS.toString();
               }
            }
         }
         return this;
      }

      private boolean isSchemePresent(Scheme scheme, String host) {
         if (host.toLowerCase().startsWith(scheme.name().toLowerCase())) {
            this.scheme = scheme.name();
            this.host = host.substring(this.scheme.length());
            return true;
         }
         return false;
      }

      public Builder setScheme(String scheme) {
         this.scheme = scheme;
         return this;
      }

      public Builder setPort(String port) {
         this.port = port;
         return this;
      }

      public Builder setTimeout(int timeout) {
         this.timeout = timeout;
         return this;
      }

      public Builder setTestServerUrl(String testServerUrl) {
         this.testServerUrl = testServerUrl;
         return this;
      }

      private void initWithDefaultValues() {
         if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Server host is empty. Set up it with setHost(String host) method.");
         }
         if (port == null) {
            port = "";
         }
         if (scheme == null) {
            scheme = Scheme.HTTP.toString();
         }
         if (encoding == null) {
            encoding = Encoding.UTF_8.getString();
         }
         if (timeout == 0) {
            timeout = DEFAULT_TIMEOUT;
         }
         if (httpUserAgent == null) {
            httpUserAgent = HTTP_ANDROID_USER_AGENT;
         }
         if (testServerUrl == null) {
            testServerUrl = scheme + host + port;
         }
      }

      private void initWithAssetsValues() {
         if (host == null) {
            host = "";
         }
         if (port == null) {
            port = "";
         }
         if (scheme == null) {
            scheme = Scheme.ASSETS.toString();
         }
         if (encoding == null) {
            encoding = Encoding.UTF_8.getString();
         }
         if (timeout == 0) {
            timeout = DEFAULT_TIMEOUT;
         }
         if (httpUserAgent == null) {
            httpUserAgent = "";
         }
         if (testServerUrl == null) {
            testServerUrl = "";
         }
      }
   }
}
