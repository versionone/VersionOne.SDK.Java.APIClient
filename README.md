# VersionOne Java SDK
Copyright (c) 2008-2015 [VersionOne](http://versionone.com/).
All rights reserved.

The VersionOne Java SDK is a free and open-source library that accelerates software development of applications that integrate with VersionOne. The SDK serves as a wrapper to the VersionOne REST API, eliminating the need to code the infrastructure necessary for direct handling of HTTP requests and responses.

The Java SDK is open source and is licensed under a modified BSD license, which reflects our intent that software built with a dependency on the SDK can be for commercial and/or open source products.

## System Requirements

* Java Development Kit 1.8

## Supported IDEs for Development

* Eclipse or IntelliJ IDEA

## Adding the Java SDK to your Project

The compiled version of the Java SDK is available as a downloadable ZIP file from the [VersionOne Application Catalog](http://appcatalog.versionone.com/VersionOne.SDK.Java.APIClient). When you extract the ZIP file, you will find a jar file named VersionOne.SDK.Java.APIClient-XXX.jar that you can reference in your Java project. In addition, you will also find a folder named "dependencies" that contains all of the libraries that the SDK depends on.

Alternatively, you can use [Maven](http://maven.apache.org/guides/introduction/introduction-to-the-pom.html) to import the Java SDK and it's dependencies from [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22VersionOne.SDK.Java.APIClient%22) by adding the following dependency to your project's POM file:

```xml
<dependency>
    <groupId>com.versionone</groupId>
    <artifactId>VersionOne.SDK.Java.APIClient</artifactId>
    <version>XX.X.X</version>
</dependency>
```

## Vewing the Documentation
Documentation for the Java SDK comes in two forms:

1. [Conceptual with Code Examples](https://community.versionone.com/Developers/Developer-Library/Documentation/Java_SDK): Describes how to create a connection to a VersionOne instance and then use the SDK to query, create, update, and perform operations against VersionOne assets.
2. [JavaDocs API Reference](http://versionone.github.io/VersionOne.SDK.Java.APIClient/): Full JavaDocs for all classes and methods contained in the SDK.

## Getting Help

While we strive to make the SDK as easy to use as possible, you may still occasionally need some help, and there are a few different ways you can get it:  

- [Code Samples](https://community.versionone.com/Developers/Developer-Library/Sample_Code): A growing list of recipes for working with the VersionOne API. Check here first to see if an answer already exists.  
- [StackOverflow](http://stackoverflow.com/questions/tagged/versionone): For asking question of the VersionOne Developer Community.  
- [VersionOne Support](): Our support team is well versed in the VersionOne API, including accessing it via the SDK.  
- [VersionOne Technical Services](http://www.versionone.com/training/technical_services/): A paid support offering, this team provides API training, development pairing, and complete custom development services.  
