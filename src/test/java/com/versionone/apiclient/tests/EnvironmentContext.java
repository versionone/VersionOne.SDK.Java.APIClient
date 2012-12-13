package com.versionone.apiclient.tests;

public class EnvironmentContext {

    //urls that support integration and unit testing
    public static class Urls {
        public static String getV1Url(){
            return "https://www14.v1host.com/v1sdktesting/";
        }
        public static String getDataUrl(){
            return getV1Url().concat("rest-1.v1/");
        }
        public static String getMetaUrl(){
            return getV1Url().concat("meta.v1/");
        }
    }
    public static class Models{
    }
    public static class Services {
    }
    public static class Connectors{
    }
    //credentials that support integration and unit testing
    public static class Credentials{
        public static String getV1UserName(){
            return "admin";
        }
        public static String getV1Password(){
            return "admin";
        }
    }

}
