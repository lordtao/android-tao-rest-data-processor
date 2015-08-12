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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import ua.at.tsvetkov.data_processor.helpers.Scheme;

/**
 * Get Request builder.
 *
 * @author lordtao
 */
public class GetRequest extends WebRequest {

    /**
     * Return new instance of GetRequest.
     *
     * @return
     */
    public static GetRequest newInstance() {
        return new GetRequest();
    }

    @SuppressWarnings("deprecation")
    private Header header;

    @Override
    @SuppressWarnings("deprecation")
    public InputStream getInputStream() throws IOException {
        if (!isBuild()) {
            throw new IllegalArgumentException(REQUEST_IS_NOT_BUILDED);
        }
        startTime = System.currentTimeMillis();

        HttpConnectionParams.setConnectionTimeout(httpParameters, configuration.getTimeout());
        HttpConnectionParams.setSoTimeout(httpParameters, configuration.getTimeout());

        HttpGet httpPost = new HttpGet(toString());
        httpPost.setParams(httpParameters);
        if (header != null) {
            httpPost.addHeader(header);
        }

        printToLogUrl();

        return getResponce(httpPost);
    }

    // ********************************************************************************

    /**
     * Directly assign full URL string. All other URL methods will be ignored
     *
     * @param url
     */
    public GetRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Set custom HttpParams.
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public GetRequest setHttpParameters(HttpParams httpParameters) {
        this.httpParameters = httpParameters;
        return this;
    }

    /**
     * Set custom HttpContext.
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public GetRequest setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
        return this;
    }

    /**
     * Set encoding
     *
     * @param encoding
     */
    public GetRequest setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * Sets the scheme "http://".
     *
     * @return
     */
    public GetRequest setSchemeHttp() {
        this.scheme = Scheme.HTTP.toString();
        return this;
    }

    /**
     * Sets the scheme "https://".
     *
     * @return
     */
    public GetRequest setSchemeHttps() {
        this.scheme = Scheme.HTTPS.toString();
        return this;
    }

    /**
     * Sets the scheme "file://".
     *
     * @return
     */
    public GetRequest setSchemeFile() {
        this.scheme = Scheme.HTTPS.toString();
        return this;
    }

    /**
     * Sets your scheme.
     *
     * @param scheme
     * @return
     */
    public GetRequest setScheme(String scheme) {
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
    public GetRequest setUserInfo(String username, String password) {
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
    public GetRequest setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Set port.
     *
     * @param port
     * @return
     */
    public GetRequest setPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * Set path
     *
     * @param path
     * @return
     */
    public GetRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public GetRequest setLogTag(String tag) {
        this.tag = tag;
        return this;
    }

    @SuppressWarnings("deprecation")
    public GetRequest setHeader(Header header) {
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
    public GetRequest addGetParam(String key, String value) {
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
    public GetRequest addGetParam(String key, int value) {
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
    public GetRequest addGetParam(String key, long value) {
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
    public GetRequest addGetParam(String key, float value) {
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
    public GetRequest addGetParam(String key, double value) {
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
    public GetRequest addFragment(String fragment) {
        this.fragment = fragment;
        return this;
    }

    /**
     * Save received data to cache file. Skip it if exist.
     *
     * @param cacheFileName
     */
    public GetRequest saveToCacheFile(String cacheFileName) {
        this.cacheFileName = cacheFileName;
        this.isRewriteFile = false;
        return this;
    }

    /**
     * Save received data to cache file. Rewrite it if exist.
     *
     * @param cacheFileName
     */
    public GetRequest rewriteCacheFile(String cacheFileName) {
        this.cacheFileName = cacheFileName;
        this.isRewriteFile = true;
        return this;
    }

    @Override
    public GetRequest addProgressDialog(Context context, String title, String message) {
        setupProgress(context, title, message);
        return this;
    }
}
