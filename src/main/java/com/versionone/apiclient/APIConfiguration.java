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
        static String UserName(){return "UserName";}
        static String Password(){return "Password";}
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

    public String getUserName() {
        return _properties.getProperty(ConfigurationProperties.UserName());
    }

    public String getPassword() {
        return _properties.getProperty(ConfigurationProperties.Password());
    }

}
