package com.versionone.apiclient;

public class NoOpTerm implements IFilterTerm {
    public String getToken() throws APIException {
        return null;
    }

    public String getShortToken() throws APIException {
        return null;
    }
}
