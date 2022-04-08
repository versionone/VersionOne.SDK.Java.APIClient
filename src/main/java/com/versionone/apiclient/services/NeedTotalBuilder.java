package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public class NeedTotalBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getNeedTotal()) {
        	result.addQueryParameter("needTotal", "true");
        }
    }
}
