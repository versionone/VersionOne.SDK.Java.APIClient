package com.versionone.apiclient;

import com.versionone.apiclient.IAPIConfiguration;
import com.versionone.apiclient.ICredentials;
import com.versionone.apiclient.IUrls;

public class ClientConfiguration implements IAPIConfiguration, IUrls, ICredentials {

    private String url;
    private String username;
    private String password;
    private String domain;

    public ClientConfiguration(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ClientConfiguration(String url, String username, String password, String domain) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.domain = domain;
    }

    @Override
    public String getV1Url() {
        return url;
    }

    @Override
    public String getDataUrl() {
        return createUrl("rest-1.v1");
    }

    @Override
    public String getMetaUrl() {
        return createUrl("meta.v1");
    }

    @Override
    public String getConfigUrl() {
        return createUrl("config.v1");
    }

    @Override
    public String getV1UserName() {
        return username;
    }

    @Override
    public String getV1Password() {
        return password;
    }

    @Override
    public String getProxyUrl() {
        return "http://127.0.0.1:3128";
    }

    @Override
    public String getProxyUserName() {
        return username;
    }

    @Override
    public String getProxyPassword() {
        return username;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    private String createUrl(String endpoint) {
        return getV1Url() + endpoint + "/";
    }
}
