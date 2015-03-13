package com.versionone.apiclient;

import com.versionone.apiclient.services.BuildResult;
import com.versionone.apiclient.services.Query;
import com.versionone.apiclient.services.QueryBuilder;

public class SortBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getOrderBy().size() > 0) {
            result.querystringParts.add("sort=" + query.getOrderBy().getToken());
        }
    }
}
