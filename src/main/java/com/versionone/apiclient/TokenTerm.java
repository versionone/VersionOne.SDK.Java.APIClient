package com.versionone.apiclient;

import com.versionone.apiclient.filters.IFilterTerm;

public class TokenTerm implements IFilterTerm {
    private final String token;

    public TokenTerm(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getShortToken() {
        return token;
    }
}
