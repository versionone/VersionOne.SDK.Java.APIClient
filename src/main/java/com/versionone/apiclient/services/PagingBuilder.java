package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public class PagingBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getPaging().getStart() != 0 || query.getPaging().getPageSize() != Integer.MAX_VALUE) {
        	result.addQueryParameter("page", query.getPaging().getToken());
        }
    }
}
