package com.versionone.apiclient;

import org.apache.http.message.BasicNameValuePair;

public class SortBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getOrderBy().size() > 0) {
            result.querystringParts.add(new BasicNameValuePair("sort", query.getOrderBy().getToken()));
        }
    }
}
