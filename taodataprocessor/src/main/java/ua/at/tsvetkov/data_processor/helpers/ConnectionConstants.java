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
   /**
    * HTTP Status-Code 202: Accepted.
    */
   public static final int HTTP_ACCEPTED = HttpURLConnection.HTTP_ACCEPTED;
   /**
    * HTTP Status-Code 502: Bad Gateway.
    */
   public static final int HTTP_BAD_GATEWAY = HttpURLConnection.HTTP_BAD_GATEWAY;
   /**
    * HTTP Status-Code 405: Method Not Allowed.
    */
   public static final int HTTP_BAD_METHOD = HttpURLConnection.HTTP_BAD_METHOD;
   /**
    * HTTP Status-Code 400: Bad Request.
    */
   public static final int HTTP_BAD_REQUEST = HttpURLConnection.HTTP_BAD_REQUEST;
   /**
    * HTTP Status-Code 408: Request Time-Out.
    */
   public static final int HTTP_CLIENT_TIMEOUT = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
   /**
    * HTTP Status-Code 409: Conflict.
    */
   public static final int HTTP_CONFLICT = HttpURLConnection.HTTP_CONFLICT;
   /**
    * HTTP Status-Code 201: Created.
    */
   public static final int HTTP_CREATED = HttpURLConnection.HTTP_CREATED;
   /**
    * HTTP Status-Code 413: Request Entity Too Large.
    */
   public static final int HTTP_ENTITY_TOO_LARGE = HttpURLConnection.HTTP_ENTITY_TOO_LARGE;
   /**
    * HTTP Status-Code 403: Forbidden.
    */
   public static final int HTTP_FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN;
   /**
    * HTTP Status-Code 504: Gateway Timeout.
    */
   public static final int HTTP_GATEWAY_TIMEOUT = HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
   /**
    * HTTP Status-Code 410: Gone.
    */
   public static final int HTTP_GONE = HttpURLConnection.HTTP_GONE;
   /**
    * HTTP Status-Code 500: Internal Server Error.
    */
   public static final int HTTP_INTERNAL_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR;
   /**
    * HTTP Status-Code 411: Length Required.
    */
   public static final int HTTP_LENGTH_REQUIRED = HttpURLConnection.HTTP_LENGTH_REQUIRED;
   /**
    * HTTP Status-Code 301: Moved Permanently.
    */
   public static final int HTTP_MOVED_PERM = HttpURLConnection.HTTP_MOVED_PERM;
   /**
    * HTTP Status-Code 302: Temporary Redirect.
    */
   public static final int HTTP_MOVED_TEMP = HttpURLConnection.HTTP_MOVED_TEMP;
   /**
    * HTTP Status-Code 300: Multiple Choices.
    */
   public static final int HTTP_MULT_CHOICE = HttpURLConnection.HTTP_MULT_CHOICE;
   /**
    * HTTP Status-Code 406: Not Acceptable.
    */
   public static final int HTTP_NOT_ACCEPTABLE = HttpURLConnection.HTTP_NOT_ACCEPTABLE;
   /**
    * HTTP Status-Code 203: Non-Authoritative Information.
    */
   public static final int HTTP_NOT_AUTHORITATIVE = HttpURLConnection.HTTP_NOT_AUTHORITATIVE;
   /**
    * HTTP Status-Code 404: Not Found.
    */
   public static final int HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND;
   /**
    * HTTP Status-Code 501: Not Implemented.
    */
   public static final int HTTP_NOT_IMPLEMENTED = HttpURLConnection.HTTP_NOT_IMPLEMENTED;
   /**
    * HTTP Status-Code 304: Not Modified.
    */
   public static final int HTTP_NOT_MODIFIED = HttpURLConnection.HTTP_NOT_MODIFIED;
   /**
    * HTTP Status-Code 204: No Content.
    */
   public static final int HTTP_NO_CONTENT = HttpURLConnection.HTTP_NO_CONTENT;
   /**
    * HTTP Status-Code 200: OK.
    */
   public static final int HTTP_OK = HttpURLConnection.HTTP_OK;
   /**
    * HTTP Status-Code 206: Partial Content.
    */
   public static final int HTTP_PARTIAL = HttpURLConnection.HTTP_PARTIAL;
   /**
    * HTTP Status-Code 402: Payment Required.
    */
   public static final int HTTP_PAYMENT_REQUIRED = HttpURLConnection.HTTP_PAYMENT_REQUIRED;
   /**
    * HTTP Status-Code 412: Precondition Failed.
    */
   public static final int HTTP_PRECON_FAILED = HttpURLConnection.HTTP_PRECON_FAILED;
   /**
    * HTTP Status-Code 407: Proxy Authentication Required.
    */
   public static final int HTTP_PROXY_AUTH = HttpURLConnection.HTTP_PROXY_AUTH;
   /**
    * HTTP Status-Code 414: Request-URI Too Large.
    */
   public static final int HTTP_REQ_TOO_LONG = HttpURLConnection.HTTP_REQ_TOO_LONG;
   /**
    * HTTP Status-Code 205: Reset Content.
    */
   public static final int HTTP_RESET = HttpURLConnection.HTTP_RESET;
   /**
    * HTTP Status-Code 303: See Other.
    */
   public static final int HTTP_SEE_OTHER = HttpURLConnection.HTTP_SEE_OTHER;

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

   public @interface ConnectionResponseCode {
   }

   private ConnectionConstants() {}

}
