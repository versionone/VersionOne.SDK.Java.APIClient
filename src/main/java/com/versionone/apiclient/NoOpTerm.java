package com.versionone.apiclient;

import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.filters.IFilterTerm;

public class NoOpTerm implements IFilterTerm {
    public String getToken() throws APIException {
        return null;
    }

    public String getShortToken() throws APIException {
        return null;
    }
}
