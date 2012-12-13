package com.versionone.apiclient;

import org.apache.http.message.BasicNameValuePair;

public class PagingBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getPaging().getStart() != 0 || query.getPaging().getPageSize() != Integer.MAX_VALUE) {
            result.querystringParts.add(new BasicNameValuePair("page", query.getPaging().getToken()));
            
        }
    }
}
