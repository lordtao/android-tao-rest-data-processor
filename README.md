#Android Data Processor. Easy to build a REST request, to receive and processing data (XML, JSON, CSV and etc.)
===========================

The Data Processor is designed to perform simple RESTservice requests or to files locally. Requests can run synchronously or asynchronously.

###Easy possibility of building requests and processing results.

Initialize the Data Processor

To use the processor needs to initialize it using the configurator. Configurator allows you to set the basic parameters of a request under http://developer.android.com/reference/java/net/URL.html, encoding, timeout, etc. These data are basic and can be modified without problems in the construction of concrete request. You can initialization in the class to inherit from Application:

```java
private void initDataProcessor () {
DataProcessorConfiguration configuration = DataProcessorConfiguration
                                            . GetBuilder ()
                                            . SetHost ("google.com")
                                            . SetLogEnabled (true)
                                            . SetShowProcessingTime (true)
                                            . SetTimeout (4000)
                                            . Build ();
DataProcessor.getInstance (). Init (configuration);
}
```

Currently is possible to build GET, POST, MultipartRequest and processing locale files using FileRequest.

```java
Request request = GetRequest.newInstance ()
                  . SetLogTag ("FB Login to server")
                  . AddGetParam ("signature", "DH $ FHJDDBHJV3393n")
                  . SetPath ("login.php")
                  . Build ();
```

```java
Request request = PostRequest.newInstance ()
                  . AddPostParam ("email", "some@gmail.com")
                  . AddPostParam ("password", "any_password")
                  . SetLogTag ("Login to server")
                  . AddGetParam (VAR_SIG, SIGNATURE)
                  . SetPath ("auth2.php")
                  . Build ();
```

```java
Request request = MultipartRequest.newInstance ()
                  . AddTextBody ("userName", "Alex")
                  . AddTextBody ("email", "some@gmail.com")
                  . AddTextBody ("password", "any_password")
                  . AddTextBody ("sex", "male")
                  . AddJPEG ("imagedata", bitmap, "image.jpg")
                  . SetLogTag ("Create user")
                  . AddGetParam (VAR_SIG, SIGNATURE)
                  . SetPath ("createuser.php")
                  . Build ();
```

The obtained data request can be processed by any of your favorite parser. The processed data is stored in the objects implementing interfaces InputStreamDataInterface, JsonDataInterface, StringDataInterface.

```java
public class LoginResult extends BaseResult implements JsonDataInterface {

public static String token = "";
public static String email = "";
public static String password = "";

@ Override
public void fillFromString (String src) throws JSONException {
  JSONObject jsonObject = new JSONObject(src);
  if (isSucess (jsonObject)) {
    token = jsonObject.getString ("token");
    email = jsonObject.getString ("email");
    password = jsonObject.getString ("password");
  }
}
```

Running a request can be synchronous or asynchronous. Synchronous request will returns filled object. Asynchronous request returns the same object through Handler in msg.obj and the result (success or failure) in msg.what which may be equalProcessingCentre.SUCCESS or ProcessingCentre.ERROR

Request execution example:

```java
DataProcessor.getInstance (). ExecuteAsync (request, new LoginResult (), handler);
```

Sample processing Handler:

```java
private Handler getLoginHandler () {
return new Handler () {

      @ Override
      public void handleMessage (Message msg) {
        if (msg.what == ProcessingCentre.SUCCESS) {
          LoginResult resultObject = (LoginResult) msg.obj;
          ...
          } Else {
            Exception ex = (Exception) msg.obj;
            ...
            Log.w ("Can't login");
          }
        }
    };
}
```
