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
package ua.at.tsvetkov.dataprocessor.requests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import ua.at.tsvetkov.dataprocessor.Scheme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class MultipartRequest extends WebRequest {

	private MultipartEntityBuilder	builder	= MultipartEntityBuilder.create();
	private HttpEntity					entity;

	private MultipartRequest() {

	}

	/**
	 * Return new instance of MultipartRequest. When building a {@link ua.at.tsvetkov.dataprocessor.requests.MultipartRequest
	 * MultipartRequest} methods must be called first, before {@link ua.at.tsvetkov.dataprocessor.requests.Request Request} methods
	 * 
	 * @return
	 */
	public static MultipartRequest newInstance() {
		return new MultipartRequest();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (!isBuild())
			throw new IllegalArgumentException(REQUEST_IS_NOT_BUILDED);
		startTime = System.currentTimeMillis();

		HttpConnectionParams.setConnectionTimeout(httpParameters, configuration.getTimeout());
		HttpConnectionParams.setSoTimeout(httpParameters, configuration.getTimeout());

		HttpPost httpPost = new HttpPost(toString());
		httpPost.setEntity(entity);
		httpPost.setParams(httpParameters);

		printToLogUrl();

		if (httpContext == null)
			return httpClient.execute(httpPost).getEntity().getContent();
		else
			return httpClient.execute(httpPost, httpContext).getEntity().getContent();
	}

	@Override
	public Request build() {
		entity = builder.build();
		return super.build();
	}

	/**
	 * Set custom HttpContext.
	 * 
	 * @return
	 */
	public MultipartRequest setHttpContext(HttpContext httpContext) {
		this.httpContext = httpContext;
		return this;
	}

	/**
	 * Add Bitmap to request as compressed image.
	 * 
	 * @param name
	 * @param bitmap
	 * @param compressFormat
	 * @param quality
	 * @param fileName
	 * @return
	 */
	public MultipartRequest addImage(String name, Bitmap bitmap, CompressFormat compressFormat, int quality, String fileName) {
		if (bitmap != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(compressFormat, quality, bos);
			byte[] imagedata = bos.toByteArray();
			// ByteArrayBody contentBody = new ByteArrayBody(imagedata, fileName);
			// builder.addPart(name, contentBody);
			builder.addBinaryBody(name, imagedata, ContentType.DEFAULT_BINARY, fileName);
		}
		return this;
	}

	public MultipartRequest addPNG(String name, Bitmap bitmap, String fileName) {
		return addImage(name, bitmap, CompressFormat.PNG, 0, fileName);
	}

	public MultipartRequest addJPEG(String name, Bitmap bitmap, String fileName) {
		return addImage(name, bitmap, CompressFormat.JPEG, 75, fileName);
	}

	public MultipartRequest addBinaryBody(String name, byte[] b) {
		builder.addBinaryBody(name, b);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, File file) {
		builder.addBinaryBody(name, file);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, InputStream stream) {
		builder.addBinaryBody(name, stream);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, byte[] b, ContentType contentType, String filename) {
		builder.addBinaryBody(name, b, contentType, filename);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, File file, ContentType contentType, String filename) {
		builder.addBinaryBody(name, file, contentType, filename);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, InputStream stream, ContentType contentType, String filename) {
		builder.addBinaryBody(name, stream, contentType, filename);
		return this;
	}

	public MultipartRequest addBinaryBody(String name, ContentBody contentBody) {
		builder.addPart(name, contentBody);
		return this;
	}

	public MultipartRequest addPart(String name, ContentBody contentBody) {
		builder.addPart(name, contentBody);
		return this;
	}

	public MultipartRequest addTextBody(String name, String text) {
		builder.addTextBody(name, text);
		return this;
	}

	public MultipartRequest addTextBody(String name, String text, ContentType contentType) {
		builder.addTextBody(name, text, contentType);
		return this;
	}

	public MultipartRequest setBoundary(String boundary) {
		builder.setBoundary(boundary);
		return this;
	}

	public MultipartRequest setCharset(Charset charset) {
		builder.setCharset(charset);
		return this;
	}

	public MultipartRequest setLaxMode() {
		builder.setLaxMode();
		return this;
	}

	public MultipartRequest setMode(HttpMultipartMode mode) {
		builder.setMode(mode);
		return this;
	}

	public MultipartRequest setStrictMode() {
		builder.setStrictMode();
		return this;
	}

	// ********************************************************************************

	/**
	 * Directly assign full URL string. All other URL methods will be ignored
	 * 
	 * @param url
	 */
	public MultipartRequest setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * Set custom HttpParams.
	 * 
	 * @return
	 */
	public MultipartRequest setHttpParameters(HttpParams httpParameters) {
		this.httpParameters = httpParameters;
		return this;
	}

	/**
	 * Set encoding
	 * 
	 * @param encoding
	 */
	public MultipartRequest setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	/**
	 * Sets the scheme "http://".
	 * 
	 * @return
	 */
	public MultipartRequest setSchemeHttp() {
		this.scheme = Scheme.HTTP.getString();
		return this;
	}

	/**
	 * Sets the scheme "https://".
	 * 
	 * @return
	 */
	public MultipartRequest setSchemeHttps() {
		this.scheme = Scheme.HTTPS.getString();
		return this;
	}

	/**
	 * Sets the scheme "file://".
	 * 
	 * @return
	 */
	public MultipartRequest setSchemeFile() {
		this.scheme = Scheme.HTTPS.getString();
		return this;
	}

	/**
	 * Sets your scheme.
	 * 
	 * @param scheme
	 * @return
	 */
	public MultipartRequest setScheme(String scheme) {
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
	public MultipartRequest setUserInfo(String username, String password) {
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
	public MultipartRequest setHost(String host) {
		this.host = host;
		return this;
	}

	/**
	 * Set port.
	 * 
	 * @param port
	 * @return
	 */
	public MultipartRequest setPort(String port) {
		this.port = port;
		return this;
	}

	/**
	 * Set path
	 * 
	 * @param path
	 * @return
	 */
	public MultipartRequest setPath(String path) {
		this.path = path;
		return this;
	}

	public MultipartRequest setLogTag(String tag) {
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
	public MultipartRequest addGetParam(String key, String value) {
		if (queries == null)
			queries = new HashMap<String, String>();
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
	public MultipartRequest addGetParam(String key, int value) {
		if (queries == null)
			queries = new HashMap<String, String>();
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
	public MultipartRequest addGetParam(String key, long value) {
		if (queries == null)
			queries = new HashMap<String, String>();
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
	public MultipartRequest addGetParam(String key, float value) {
		if (queries == null)
			queries = new HashMap<String, String>();
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
	public MultipartRequest addGetParam(String key, double value) {
		if (queries == null)
			queries = new HashMap<String, String>();
		queries.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * Add fragment.
	 * 
	 * @param fragment
	 * @return
	 */
	public MultipartRequest addFragment(String fragment) {
		this.fragment = fragment;
		return this;
	}

	/**
	 * Save received data to file. Skip it if exist.
	 * 
	 * @param fileName
	 */
	public MultipartRequest saveToFile(String fileName) {
		this.fileName = fileName;
		this.isRewriteFile = false;
		return this;
	}

	/**
	 * Save received data to file. Rewrite it if exist.
	 * 
	 * @param fileName
	 */
	public MultipartRequest rewriteFile(String fileName) {
		this.fileName = fileName;
		this.isRewriteFile = true;
		return this;
	}

}
