package com.versionone.apiclient;

import java.io.IOException;

public final class Urls implements IUrls {

    private IAPIConfiguration _config;

    public Urls() throws IOException {
        _config = new APIConfiguration();
    }

    public String getV1Url(){
        return _config.getV1Url();
    }
    public String getMetaUrl(){
        return _config.getMetaUrl();
    }
    public String getDataUrl(){
        return _config.getDataUrl();
    }
    public String getConfigUrl(){
        return _config.getConfigUrl();
    }
    public String getProxyUrl() {
        return _config.getProxyUrl();
    }
}
