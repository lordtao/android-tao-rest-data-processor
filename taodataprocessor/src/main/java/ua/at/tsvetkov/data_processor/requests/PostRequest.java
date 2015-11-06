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

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ua.at.tsvetkov.data_processor.helpers.Scheme;
import ua.at.tsvetkov.util.Log;


public class PostRequest extends WebRequest {

    private HashMap<String, String> postData = new HashMap<String, String>();
    private HashMap<String, String> requestProperties = new HashMap<String, String>();

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

        String postDataString = getPostDataString();

        httpURLConnection = (HttpURLConnection) getURL().openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setChunkedStreamingMode(0);
        httpURLConnection.setReadTimeout(configuration.getTimeout());
        httpURLConnection.setConnectTimeout(configuration.getTimeout());
        httpURLConnection.setRequestProperty("Content-Length", Integer.toString(postDataString.length()));
        setRequestProperties();

        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(postDataString);

        writer.flush();
        writer.close();

        printToLogUrl();
        printToLogPairs();

        return new BufferedInputStream(httpURLConnection.getInputStream());
    }

    private void setRequestProperties() {
        for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private String getPostDataString() {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : postData.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            try {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                String value = entry.getValue();
                if (value == null) {
                    result.append(URLEncoder.encode("", "UTF-8"));
                } else {
                    result.append(URLEncoder.encode(value, "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    /**
     * Sets the value of the specified request header field. The value will only be used by the current URLConnection instance. This method can only be called before the connection is established.
     *
     * @param key
     * @param value
     * @return
     */
    public PostRequest addRequestProperty(String key, String value) {
        requestProperties.put(key, value);
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, String value) {
        postData.put(name, value);
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, int value) {
        postData.put(name, String.valueOf(value));
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, float value) {
        postData.put(name, String.valueOf(value));
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, double value) {
        postData.put(name, String.valueOf(value));
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, long value) {
        postData.put(name, String.valueOf(value));
        return this;
    }

    /**
     * Added POST parameter
     *
     * @param name
     * @param value
     * @return
     */
    public PostRequest addPostParam(String name, boolean value) {
        postData.put(name, String.valueOf(value));
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

    @Override
    public PostRequest addProgressDialog(Context context, String title, String message) {
        setupProgress(context, title, message);
        return this;
    }

    protected void printToLogPairs() {
        if (configuration.isLogEnabled()) {
            for (Map.Entry<String, String> entry : postData.entrySet()) {
                Log.v(entry.getKey() + "=" + entry.getValue());
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
        result = prime * result + ((postData == null) ? 0 : postData.hashCode());
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
        if (postData == null) {
            if (other.postData != null) {
                return false;
            }
        } else if (!postData.equals(other.postData)) {
            return false;
        }
        return true;
    }

}
