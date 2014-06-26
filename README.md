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
                  .setLogTag ("FB Login to server")
                  .addGetParam ("signature", "DH $ FHJDDBHJV3393n")
                  .setPath ("login.php")
                  .build ();
```

```java
Request request = PostRequest.newInstance ()
                  .addPostParam ("email", "some@gmail.com")
                  .addPostParam ("password", "any_password")
                  .setLogTag ("Login to server")
                  .addGetParam (VAR_SIG, SIGNATURE)
                  .setPath ("auth2.php")
                  .build ();
```

```java
Request request = MultipartRequest.newInstance ()
                  .addTextBody ("userName", "Alex")
                  .addTextBody ("email", "some@gmail.com")
                  .addTextBody ("password", "any_password")
                  .addTextBody ("sex", "male")
                  .addJPEG ("imagedata", bitmap, "image.jpg")
                  .setLogTag ("Create user")
                  .addGetParam (VAR_SIG, SIGNATURE)
                  .setPath ("createuser.php")
                  .build ();
```

The obtained data request can be processed by any of your favorite parser. The processed data is stored in the objects implementing interfaces InputStreamDataInterface, JsonDataInterface, StringDataInterface.

```java
public class LoginResult implements StringDataInterface {

public static String token = "";
public static String email = "";
public static String password = "";

@ Override
public void fillFromString (String src) throws Exception {
  JSONObject jsonObject = new JSONObject(src);
  token = jsonObject.getString ("token");
  email = jsonObject.getString ("email");
  password = jsonObject.getString ("password");
}
```

Running a request can be synchronous or asynchronous. Request will returns filled object through Handler in msg.obj or Exception object. Status code will be return in msg.what.

For HTTP request will return HttpStatus code or ERROR  in msg.what.

For local file request (using FileRequest) process will be return FILE_SUCCESS or ERROR in  in msg.what.

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
        if (msg.what == HttpStatus.SC_OK) {
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
