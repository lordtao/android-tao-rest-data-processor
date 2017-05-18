package ua.at.tsvetkov.data_processor.helpers;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.HttpURLConnection;

public final class ConnectionConstants {

    /**
     * File was opened success.
     */
    public static final int FILE_SUCCESS = 0;
    /**
     * No internet connection
     */
    public static final int NO_INTERNET_CONNECTION = 1001;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            FILE_SUCCESS,
            NO_INTERNET_CONNECTION,
            HttpURLConnection.HTTP_ACCEPTED,
            HttpURLConnection.HTTP_BAD_GATEWAY,
            HttpURLConnection.HTTP_BAD_METHOD,
            HttpURLConnection.HTTP_BAD_REQUEST,
            HttpURLConnection.HTTP_CLIENT_TIMEOUT,
            HttpURLConnection.HTTP_CONFLICT,
            HttpURLConnection.HTTP_CREATED,
            HttpURLConnection.HTTP_ENTITY_TOO_LARGE,
            HttpURLConnection.HTTP_FORBIDDEN,
            HttpURLConnection.HTTP_GATEWAY_TIMEOUT,
            HttpURLConnection.HTTP_GONE,
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_LENGTH_REQUIRED,
            HttpURLConnection.HTTP_MOVED_PERM,
            HttpURLConnection.HTTP_MOVED_TEMP,
            HttpURLConnection.HTTP_MULT_CHOICE,
            HttpURLConnection.HTTP_NOT_ACCEPTABLE,
            HttpURLConnection.HTTP_NOT_AUTHORITATIVE,
            HttpURLConnection.HTTP_NOT_FOUND,
            HttpURLConnection.HTTP_NOT_IMPLEMENTED,
            HttpURLConnection.HTTP_NOT_MODIFIED,
            HttpURLConnection.HTTP_NO_CONTENT,
            HttpURLConnection.HTTP_OK,
            HttpURLConnection.HTTP_PARTIAL,
            HttpURLConnection.HTTP_PAYMENT_REQUIRED,
            HttpURLConnection.HTTP_PRECON_FAILED,
            HttpURLConnection.HTTP_PROXY_AUTH,
            HttpURLConnection.HTTP_REQ_TOO_LONG,
            HttpURLConnection.HTTP_RESET,
            HttpURLConnection.HTTP_SEE_OTHER
    })
    public @interface ConnectionResponseCode {}

    private ConnectionConstants() {}
}
