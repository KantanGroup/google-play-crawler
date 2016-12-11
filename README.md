Google Play Crawler JAVA API
============================

google-play-crawler is a tool to download APKs from the Google Play Store to your desktop computer.

Now you can dowload applications with single click from web to your desktop. 

It also has checkin ability to generate ANDROID-ID for you. 

While doing checkin with your account, it uses Galaxy S3 properties. So only Galaxy S3 compatible applications will be retrieved.
If you try to download incompatible application for Galaxy S3, you will get an exception like this : 
```java
"The item you were attempting to purchase could not be found."
```


This project is available thanks to this project : https://github.com/egirault/googleplay-api. 


## Building and running

###SBT

Make sure you hava protoc installed (version 2.5!).
On OSX:
```
brew install protobuf
```

Ubuntu/Debian:
```
sudo apt-get install protobuf-compiler libprotobuf-java
```

Install SBT:
http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html

To build:
```
sbt assembly
```
Note: This will generate the protobuf java files and compile them as part of the project.

###Maven

Install Maven 3 & protobuf compiler (version 2.5!)
```
sudo apt-get install maven protobuf-compiler libprotobuf-java
```
There are two ways to build the target executable:
```
# To build without testing, run:
mvn package -Dmaven.test.skip=true

OR

# To build with testing, fill out email, password, and androidid in "src/test/resources/login.conf" and run:
mvn package

```

This will ignore the tests and pack for you. If you want to run tests, you need to edit login.conf with your google account credentials.
Please note that the "androidid" is really the Google Service Framework (GSF) ID has replaced the Android ID which is now deprecated.


## Usage

### Maven Reference
```
<dependency>
    <groupId>com.akdeniz</groupId>
	<artifactId>googleplaycrawler</artifactId>
	<version>0.2</version>
</dependency>

<repository>
	<id>github.release.repo</id>
	<url>https://raw.github.com/Akdeniz/akdeniz-repo/master/repo/releases/</url>
</repository>
```

### General

    java -jar googleplay.jar --help
    usage: googleplay [-h] [-f [CONF]]
                      {download,checkin,list,categories,search,permissions,reviews,register,usegcm}
                      ...

    Play with Google Play API :)
    
    optional arguments:
      -h, --help             show this help message and exit
      -f [CONF], --conf [CONF]
                             Configuration file contains the login information, such as
                             the androidid (Google Service Framework ID), email, password,
                             and user agent. The user agent provided works with any androidid.
    
    subcommands:
      Command to be executed.
    
      {download,checkin,list,categories,search,permissions,reviews,register, usegcm}

### Sub-commands

You can get usage of sub-commands like this :

    java -jar googleplay.jar list --help
    usage: googleplay list [-h] [-s SUBCATEGORY] [-o OFFSET] [-n NUMBER]
                      category
    
    Lists sub-categories and applications within them!
    
    positional arguments:
      category               defines category
    
    optional arguments:
      -h, --help             show this help message and exit
      -s SUBCATEGORY, --subcategory SUBCATEGORY
                             defines sub-category
      -o OFFSET, --offset OFFSET
                             offset to define where list begins
      -n NUMBER, --number NUMBER
                             how many app will be listed

### About Login & Proxy Arguments

Login, proxy, and user agent arguments must be defined in a configuration file, as shown below.
If you want to use the proxy feature, just uncomment and enter a proxy host and port!
The user agent determines if an app is compatible with an Android device. The user agent provided can be used with any combination of email and androidid.

    # Login Information
    email = xxxxxxxxx@gmail.com
    password = xxxxxxxxx
    androidid = xxxxxxxxxxxxxxxx
    # security token only needed for "usegcm" option
    securitytoken = xxxxxxxx
    
    # Proxy Information
    #host=localhost
    #port=8888

    # User Agent
    #   See "get_user_agent_details.sh" for more information
    versionName=5.5.12
    versionCode=80351200
    sdk=22
    device=zeroflteatt
    hardware=samsungexynos7420
    product=zeroflteatt
    build=LMY47X:user
    
You can use this file like this:

    java -jar googleplay.jar --conf login.conf ...

Note that "usegcm" option does not operate on HTTP, so it won't be proxified by this configuration.  
    
### About Account Page Registration

To see your checked-in device at your account page (https://play.google.com/store/account), you can register it as follows:

    java -jar googleplay.jar -f crawler.conf register

and **download a few application after registration!** (same behaviour of android market application!)

~~Of course this does not allow you to click and download from web page! It is just for information right now!~~

~~But I will see if I can simulate Android GCM(Google Cloud Messaging) push-in mechanism when I have time! So stay tuned.. ;)~~

### About "usegcm" Option

You can use ``usegcm`` option to download applications from web to your desktop like installing to your phone.

All you have to do register your checkined device as described above and execute google-play-crawler like this:

    java -jar googleplay.jar -f crawler.conf usegcm

Now you can login to your account from web browser and try to install any application.


License
----
    Copyright (c) 2012, Akdeniz
    All rights reserved.
    
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met: 
    
    1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer. 
    2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution. 

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
    ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
    ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
    
    The views and conclusions contained in the software and documentation are those
    of the authors and should not be interpreted as representing official policies, 
    either expressed or implied, of the FreeBSD Project.
    
