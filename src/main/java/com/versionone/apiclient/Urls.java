package com.versionone.apiclient;

public class Urls implements IUrls {
    public String getV1Url(){
        return "https://www14.v1host.com/v1sdktesting/";
    }
    public String getMetaUrl(){
        return getV1Url().concat("meta.v1/");
    }
    public String getDataUrl(){
        return getV1Url().concat("rest-1.v1/");
    }
}
