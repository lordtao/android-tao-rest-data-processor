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
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

import ua.at.tsvetkov.data_processor.helpers.Scheme;

/**
 * Delete Request builder.
 *
 * @author lordtao
 */
public class DeleteRequest extends WebRequest {

    /**
     * Return new instance of DeleteRequest.
     *
     * @return
     */
    public static DeleteRequest newInstance() {
        return new DeleteRequest();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!isBuild())
            throw new IllegalArgumentException(REQUEST_IS_NOT_BUILDED);
        startTime = System.currentTimeMillis();

        httpURLConnection = (HttpURLConnection) getURL().openConnection();
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.setReadTimeout(configuration.getTimeout());
        httpURLConnection.setConnectTimeout(configuration.getTimeout());

        printToLogUrl();

        InputStream stream = httpURLConnection.getInputStream();
        if(stream==null) {
            stream = httpURLConnection.getErrorStream();
        }
        return new BufferedInputStream(stream);
    }

    // ********************************************************************************

    /**
     * Directly assign full URL string. All other URL methods will be ignored
     *
     * @param url URL string
     */
    public DeleteRequest setUrl(String url) {
        this.url = url;
        return this;
    }


    /**
     * Set encoding
     *
     * @param encoding encoding string
     */
    public DeleteRequest setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    /**
     * Sets the scheme "http://".
     *
     * @return this request
     */
    public DeleteRequest setSchemeHttp() {
        this.scheme = Scheme.HTTP.toString();
        return this;
    }

    /**
     * Sets the scheme "https://".
     *
     * @return this request
     */
    public DeleteRequest setSchemeHttps() {
        this.scheme = Scheme.HTTPS.toString();
        return this;
    }

    /**
     * Sets the scheme "file://".
     *
     * @return this request
     */
    public DeleteRequest setSchemeFile() {
        this.scheme = Scheme.HTTPS.toString();
        return this;
    }

    /**
     * Sets your scheme.
     *
     * @param scheme new scheme
     * @return this request
     */
    public DeleteRequest setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Set User Info.
     *
     * @param username user name string
     * @param password user password string
     * @return this request
     */
    public DeleteRequest setUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     * Set host.
     *
     * @param host host string
     * @return this request
     */
    public DeleteRequest setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Set port.
     *
     * @param port port string
     * @return this request
     */
    public DeleteRequest setPort(String port) {
        this.port = port;
        return this;
    }

    /**
     * Set path
     *
     * @param path path string
     * @return this request
     */
    public DeleteRequest setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Tag for LogCat
     *
     * @param tag string
     * @return this request
     */
    public DeleteRequest setLogTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * Add to query GET parameter.
     *
     * @param key   string
     * @param value string
     * @return this request
     */
    public DeleteRequest addGetParam(String key, String value) {
        if (queries == null)
            queries = new HashMap<String, String>();
        queries.put(key, value);
        return this;
    }

    /**
     * Add to query GET parameter.
     *
     * @param key   string
     * @param value string
     * @return this request
     */
    public DeleteRequest addGetParam(String key, int value) {
        if (queries == null)
            queries = new HashMap<String, String>();
        queries.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Add to query GET parameter.
     *
     * @param key   string
     * @param value string
     * @return this request
     */
    public DeleteRequest addGetParam(String key, long value) {
        if (queries == null)
            queries = new HashMap<String, String>();
        queries.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Add to query GET parameter.
     *
     * @param key   string
     * @param value string
     * @return this request
     */
    public DeleteRequest addGetParam(String key, float value) {
        if (queries == null)
            queries = new HashMap<String, String>();
        queries.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Add to query GET parameter.
     *
     * @param key   string
     * @param value string
     * @return this request
     */
    public DeleteRequest addGetParam(String key, double value) {
        if (queries == null)
            queries = new HashMap<String, String>();
        queries.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Add fragment.
     *
     * @param fragment string
     * @return this request
     */
    public DeleteRequest addFragment(String fragment) {
        this.fragment = fragment;
        return this;
    }

    /**
     * Save received data to cache file. Skip it if exist.
     *
     * @param cacheFileName string
     * @return this request
     */
    public DeleteRequest saveToCacheFile(String cacheFileName) {
        this.cacheFileName = cacheFileName;
        this.isRewriteFile = false;
        return this;
    }

    /**
     * Save received data to cashe file. Rewrite it if exist.
     *
     * @param cacheFileName string
     * @return this request
     */
    public DeleteRequest rewriteCashedFile(String cacheFileName) {
        this.cacheFileName = cacheFileName;
        this.isRewriteFile = true;
        return this;
    }


    @Override
    public DeleteRequest addProgressDialog(Context context, String title, String message) {
        setupProgress(context, title, message);
        return this;
    }

}
