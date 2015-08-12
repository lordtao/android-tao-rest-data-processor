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

import android.app.ProgressDialog;
import android.content.Context;

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

    private static final String CALL_URL = " CALL URL: ";
    private static final String REQUEST_IS_NOT_BUILD = "Request is not build and eq null.";
    private static final String WRONG_URL = "Wrong URL";
    private static final String PASSWORD_IS_NOT_SPECIFIED = "Username is available in the request, but the password is not specified";
    protected static final String CONFIGURATION_ERROR = "DataProcessor configuration is not initialized.";
    protected static final String REQUEST_IS_NOT_BUILDED = "Request is not builded";

    protected DataProcessorConfiguration configuration = DataProcessor.getInstance().getConfiguration();
    protected HashMap<String, String> queries;
    protected StringBuilder sb;
    protected String scheme;
    protected String url;
    protected String username;
    protected String password;
    protected String host;
    protected String port;
    protected String path;
    protected String fragment;
    protected String encoding;
    protected String cacheFileName;
    protected String tag;
    protected boolean isRewriteFile;
    protected long startTime;
    protected int statusCode;

    protected boolean isNeedProgressDialog = false;
    protected Context context;
    protected String progressDialogTitle;
    protected String progressDialogMessage;
    protected ProgressDialog progressDialog;

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
     * Add and show during execution standard ProgressDialog.
     *
     * @param context
     * @param title
     * @param message
     */
    public Request addProgressDialog(Context context, String title, String message) {
        setupProgress(context, title, message);
        return this;
    }

    /**
     * Add and show during execution standard ProgressDialog. For internal setup.
     *
     * @param context
     * @param title
     * @param message
     */
    protected void setupProgress(Context context, String title, String message) {
        this.isNeedProgressDialog = true;
        this.context = context;
        this.progressDialogTitle = title;
        this.progressDialogMessage = message;
    }

    /**
     * Show standard ProgressDialog.
     */
    public void showProgressDialog() {
        if(isNeedProgressDialog && progressDialog == null) {
            progressDialog = ProgressDialog.show(context, progressDialogTitle, progressDialogMessage);
        }
    }

    /**
     * Dismiss standard ProgressDialog.
     */
    public void dismissProgressDialog() {
        if( progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cacheFileName == null) ? 0 : cacheFileName.hashCode());
        result = prime * result + ((encoding == null) ? 0 : encoding.hashCode());
        result = prime * result + ((fragment == null) ? 0 : fragment.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + (isRewriteFile ? 1231 : 1237);
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        result = prime * result + ((queries == null) ? 0 : queries.hashCode());
        result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
        result = prime * result + (int) (startTime ^ (startTime >>> 32));
        result = prime * result + statusCode;
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Request other = (Request) obj;
        if (cacheFileName == null) {
            if (other.cacheFileName != null) {
                return false;
            }
        } else if (!cacheFileName.equals(other.cacheFileName)) {
            return false;
        }
        if (encoding == null) {
            if (other.encoding != null) {
                return false;
            }
        } else if (!encoding.equals(other.encoding)) {
            return false;
        }
        if (fragment == null) {
            if (other.fragment != null) {
                return false;
            }
        } else if (!fragment.equals(other.fragment)) {
            return false;
        }
        if (host == null) {
            if (other.host != null) {
                return false;
            }
        } else if (!host.equals(other.host)) {
            return false;
        }
        if (isRewriteFile != other.isRewriteFile) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        if (port == null) {
            if (other.port != null) {
                return false;
            }
        } else if (!port.equals(other.port)) {
            return false;
        }
        if (queries == null) {
            if (other.queries != null) {
                return false;
            }
        } else if (!queries.equals(other.queries)) {
            return false;
        }
        if (scheme == null) {
            if (other.scheme != null) {
                return false;
            }
        } else if (!scheme.equals(other.scheme)) {
            return false;
        }
        if (startTime != other.startTime) {
            return false;
        }
        if (statusCode != other.statusCode) {
            return false;
        }
        if (tag == null) {
            if (other.tag != null) {
                return false;
            }
        } else if (!tag.equals(other.tag)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
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
