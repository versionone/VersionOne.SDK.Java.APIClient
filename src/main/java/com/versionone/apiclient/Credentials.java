package com.versionone.apiclient;

import java.io.IOException;

public final class Credentials implements ICredentials {

    private IAPIConfiguration _config;

    public Credentials() throws IOException {
        _config = new APIConfiguration();
    }

    public String getV1UserName(){
        return _config.getUserName();
    }

    public String getV1Password(){
        return _config.getPassword();
    }
}

