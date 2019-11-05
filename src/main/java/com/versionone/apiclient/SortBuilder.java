package com.versionone.apiclient;

import com.versionone.apiclient.services.BuildResult;
import com.versionone.apiclient.services.QueryBuilder;

public class SortBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getOrderBy().size() > 0) {
        	result.addQueryParameter("sort", query.getOrderBy().getToken());
        }
    }
}
