Sample for java.lang.IllegalStateException
==============

This simple sample reproduces the _java.lang.IllegalStateException: Only one connection receive subscriber allowed_
error when using webflux OAuth2 client credentials.

    mvn clean install
    java -jar target/oauth2-client-webflux-0.0.1-SNAPSHOT.jar
    curl localhost:8080/test
    
Version
=======
Spring boot 2.1.0.RELEASE
