package com.versionone.apiclient;

public class QueryFind {
    public final String text;
    public final AttributeSelection attributes;

    public QueryFind(String text) {
        this(text, new AttributeSelection());
    }

    public QueryFind(String text, AttributeSelection sel) {
        this.text = text;
        attributes = sel;
    }
}
