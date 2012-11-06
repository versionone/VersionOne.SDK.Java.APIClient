package com.versionone.apiclient;

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
