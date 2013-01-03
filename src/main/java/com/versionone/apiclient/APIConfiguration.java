package com.versionone.apiclient;

import java.io.IOException;
import java.util.Properties;

public class APIConfiguration implements IAPIConfiguration {

    private Properties _properties;

    private static class ConfigurationProperties {
        static String ConfigFileName(){return "APIConfiguration.properties";}
        static String V1Url(){return "V1Url";}
        static String DataUrl(){return "DataUrl";}
        static String MetaUrl() {return "MetaUrl";}
        static String ConfigUrl() {return "ConfigUrl";}
        static String V1UserName(){return "V1UserName";}
        static String V1Password(){return "V1Password";}
        static String ProxyUrl(){return "ProxyUrl";}
        static String ProxyUserName() {return "ProxyUserName";}
        static String ProxyPassword() {return "ProxyPassword";}
    }

    public APIConfiguration() throws IOException {
        _properties = new Properties();
        _properties.load(APIConfiguration.class.getResourceAsStream(ConfigurationProperties.ConfigFileName()));
    }

    public String getV1Url() {
        return _properties.getProperty(ConfigurationProperties.V1Url());
    }

    public String getDataUrl() {
        return getV1Url().concat(_properties.getProperty(ConfigurationProperties.DataUrl()));
    }

    public String getMetaUrl() {
        return getV1Url().concat(_properties.getProperty(ConfigurationProperties.MetaUrl()));
    }

    public String getConfigUrl() {
        return getV1Url().concat(_properties.getProperty(ConfigurationProperties.ConfigUrl()));
    }

    public String getV1UserName() {
        return _properties.getProperty(ConfigurationProperties.V1UserName());
    }

    public String getV1Password() {
        return _properties.getProperty(ConfigurationProperties.V1Password());
    }

    public String getProxyUrl() {
        return _properties.getProperty(ConfigurationProperties.ProxyUrl());
    }

    public String getProxyUserName() {
        return _properties.getProperty(ConfigurationProperties.ProxyUserName());
    }

    public String getProxyPassword() {
        return _properties.getProperty(ConfigurationProperties.ProxyPassword());
    }

}
