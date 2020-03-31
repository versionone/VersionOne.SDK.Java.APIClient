package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public class WithVariablesBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if(query.getVariables() != null && !query.getVariables().isEmpty()) {
        	result.addQueryParameter("with", TextBuilder.join(query.getVariables(), ";"));
        }
    }
}
