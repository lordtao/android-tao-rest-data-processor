#Android Data Processor. Easy to build a REST request, to receive and processing data (XML, JSON, CSV and etc.)
===========================

##This project is no longer actively maintained.

The Data Processor is designed to perform simple RESTservice requests or to files locally. Requests can run synchronously or asynchronously. Used LruCache for store results and ThreadPool for async requests.

Download from Bintray: [ ![Download](https://api.bintray.com/packages/lordtao/maven/android-tao-rest-data-processor/images/download.svg) ](https://bintray.com/lordtao/maven/android-tao-rest-data-processor/_latestVersion)


###Easy possibility of building requests and processing results.

Initialize the Data Processor

To use the processor needs to initialize it using the configurator. Configurator allows you to set the basic parameters of a request under http://developer.android.com/reference/java/net/URL.html, encoding, timeout, etc. These data are basic and can be modified without problems in the construction of concrete request. You can initialization in the class to inherit from Application:

```java
private void initDataProcessor () {
DataProcessorConfiguration configuration = DataProcessorConfiguration
                                            .getBuilder ()
                                            .setHost ("google.com")
                                            .setLogEnabled (true)
                                            .setShowProcessingTime (true)
                                            .setTimeout (4000)
                                            .build ();
DataProcessor.getInstance (). Init (configuration);
}
```

Currently is possible to build GET, POST, PUT, DELETE, MultipartRequest and processing locale files using FileRequest.

```java
Request request = GetRequest.newInstance ()
                  .setLogTag ("FB Login to server")
                  .addGetParam ("signature", "DHFHJDDBHJV3393n")
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

The obtained data request can be processed by any of your favorite parser. The processed data is stored in the objects implementing interfaces InputStreamDataInterface, StringDataInterface.

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

Running a request can be synchronous or asynchronous. Request will returns filled object through DataProcessor.Callback or Exception object. Status code will be return also. The result Object will be created in the case of a successful call request.

For HTTP request will return HttpStatus code or ERROR.

For local file request (using FileRequest) process will be return FILE_SUCCESS or ERROR.

Request execution example:

```java
DataProcessor.getInstance ().ExecuteAsync (request, LoginResult.class, callback);
```

Sample of processing callback:

```java
private DataProcessor.Callback<LoginResult> getLoginCallback () {
return new DataProcessor.Callback<LoginResult>() {

      @Override
      public void onFinish(LoginResult item, int what) {
          acMainProgressLayout.setVisibility(View.GONE);
          if (what == HttpURLConnection.HTTP_OK) {
              // you code with items
          } else {
              Log.e("Error, What=" + what);
          }
     }
}
```

Example project you can find at https://github.com/lordtao/android-tao-data-processor-example

Add android-tao-core to your project
----------------------------
Android Data Processor is available on Bintray. Please ensure that you are using the latest versions by [ ![Download](https://api.bintray.com/packages/lordtao/maven/android-tao-rest-data-processor/images/download.svg) ](https://bintray.com/lordtao/maven/android-tao-rest-data-processor/_latestVersion)

Gradle dependency for your Android app:

add to general build.gradle
```
buildscript {
    repositories {
        jcenter()
        maven {
            url  "http://dl.bintray.com/lordtao/maven"
        }
    }
    ...
}

allprojects {
    repositories {
        jcenter()
        maven {
            url  "http://dl.bintray.com/lordtao/maven"
        }
    }
}
```
add to your module build.gradle
```
    compile 'ua.at.tsvetkov:taodataprocessor:1.2.10@aar'
```
