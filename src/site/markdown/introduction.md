## Introduction

The VersionOne APIClient is a Java library that allows object-oriented access to the VersionOne Data API, which is inherently a REST-based XML web service. Through the APIClient, you can query for simple or complex sets of information, update the information, and execute system-defined operations, without having to construct HTTP requests and responses or deal with XML parsing.

Simple queries can request a single asset with several attributes. Complex queries can request multiple assets meeting a certain criteria, have the results sorted in a particular way, and even ask for a portion (a "page") of the overall results.

The VersionOne APIClient requires the Java 1.6 or higher and VersionOne 8.0 or higher. To access the library, include APIClient.jar in your Java library path. The APIClient can use either VersionOne or Windows Integrated Authentication.