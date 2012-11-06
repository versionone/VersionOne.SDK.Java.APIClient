package com.versionone.apiclient;

public class SortBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getOrderBy().size() > 0) {
            result.querystringParts.add("sort=" + query.getOrderBy().getToken());
        }
    }
}
