package com.versionone.apiclient;

public class PagingBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getPaging().getStart() != 0 || query.getPaging().getPageSize() != Integer.MAX_VALUE) {
            result.querystringParts.add("page=" + query.getPaging().getToken());
        }
    }
}
