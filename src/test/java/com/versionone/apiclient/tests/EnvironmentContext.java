package com.versionone.apiclient.tests;

import com.versionone.apiclient.*;

public class EnvironmentContext {

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
        public static IMetaModel getMetaModel(){
            return new MetaModel(
                    EnvironmentContext.Connectors.getMetaConnector()
            );
        }
    }
    public static class Services {
        public static IServices getServices(){
            return new com.versionone.apiclient.Services(
                    EnvironmentContext.Models.getMetaModel(),
                    EnvironmentContext.Connectors.getDataConnector()
            );
        }
    }
    public static class Connectors{
        public static V1APIConnector getDataConnector(){
            return new V1APIConnector(
                    EnvironmentContext.Urls.getDataUrl(),
                    EnvironmentContext.Credentials.getV1UserName(),
                    EnvironmentContext.Credentials.getV1Password()
            );
        }
        public static V1APIConnector getMetaConnector(){
            return new V1APIConnector(
                    EnvironmentContext.Urls.getMetaUrl(),
                    EnvironmentContext.Credentials.getV1UserName(),
                    EnvironmentContext.Credentials.getV1Password()
            );
        }
    }
    public static class Credentials{
        public static String getV1UserName(){
            return "admin";
        }
        public static String getV1Password(){
            return "admin";
        }
    }

}
