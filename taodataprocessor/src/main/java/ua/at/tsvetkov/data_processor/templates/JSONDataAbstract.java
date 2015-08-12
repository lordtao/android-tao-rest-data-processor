/**
 *
 */
package ua.at.tsvetkov.data_processor.templates;

import org.json.JSONArray;
import org.json.JSONObject;

import ua.at.tsvetkov.data_processor.interfaces.StringDataInterface;
import ua.at.tsvetkov.util.Log;

/**
 * @author Alexandr Tsvetkov 2015
 */
public abstract class JSONDataAbstract implements StringDataInterface {

   public static final int DEFAULT_INDENT_SPACE = 2;

   private static int indentSpaces = DEFAULT_INDENT_SPACE;

   private String  message           = "Empty message";
   private boolean isSuccess         = false;
   private boolean isShowObjectInLog = true;

   /**
    * Return indent spaces for log format the json data.
    *
    * @return indent spaces
    */
   public static int getIndentSpaces() {
      return indentSpaces;
   }

   /**
    * Set indent spaces for log format the json data. 2 by default.
    *
    * @param indentSpaces
    */
   public static void setIndentSpaces(int indentSpaces) {
      JSONDataAbstract.indentSpaces = indentSpaces;
   }

   @Override
   public void fillFromString(String src) throws Exception {
      setIndentSpaces(2);
      if (src.startsWith("{")) {
         JSONObject jsonObject = null;
         try {
            jsonObject = new JSONObject(src);
            if (isShowObjectInLog) {
               Log.v("\n" + jsonObject.toString(indentSpaces));
            }
            parse(jsonObject);
         } catch (Exception e) {
            Log.w("The source string is not JSON object: '" + src + "'");
            message = "Server sent wrong data.";
         }
      }
      if (src.startsWith("[")) {
         JSONArray jsonArray = null;
         try {
            jsonArray = new JSONArray(src);
            if (isShowObjectInLog) {
               Log.v("\n" + jsonArray.toString(indentSpaces));
            }
            parse(jsonArray);
         } catch (Exception e) {
            Log.w("The source string is not JSON array: '" + src + "'");
            message = "Server sent wrong data.";
         }
      }
   }

   /**
    * Enables printout obtained JSON object to the log, true by default. Must be set up in constructor.
    *
    * @param isShow
    */
   public void setShowObjectInLog(boolean isShow) {
      isShowObjectInLog = isShow;
   }

   /**
    * Parse income json object. If the data is array to do nothing.
    *
    * @param jsonObject income json object
    * @throws Exception
    */
   public abstract void parse(JSONObject jsonObject) throws Exception;

   /**
    * Parse income json array. If the data is not array then to do nothing.
    *
    * @param jsonArray income json array
    * @throws Exception
    */
   public abstract void parse(JSONArray jsonArray) throws Exception;

   /**
    * Return error or another message about request result
    *
    * @return a message
    */
   public String getMessage() {
      return message;
   }

   /**
    * Set error or another message about request result
    *
    * @param msg a message
    */
   public void setMessage(String msg) {
      message = msg;
   }

   /**
    * Is the request was successful
    *
    * @return is the request was successful
    */
   public boolean isSuccess() {
      return isSuccess;
   }

   /**
    * Assign the result of a successful request
    *
    * @param isSuccess is the request was successful
    */
   public void setSuccess(boolean isSuccess) {
      this.isSuccess = isSuccess;
   }
}