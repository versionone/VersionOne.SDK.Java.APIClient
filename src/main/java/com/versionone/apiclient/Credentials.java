package com.versionone.apiclient;

import java.io.IOException;

public final class Credentials implements ICredentials {

    private IAPIConfiguration _config;

    public Credentials() throws IOException {
        _config = new APIConfiguration();
    }

    public String getV1UserName(){
        return _config.getV1UserName();
    }

    public String getV1Password(){
        return _config.getV1Password();
    }

    public String getProxyUserName(){
        return _config.getProxyUserName();
    }

    public String getProxyPassword(){
        return _config.getV1Password();
    }

}

