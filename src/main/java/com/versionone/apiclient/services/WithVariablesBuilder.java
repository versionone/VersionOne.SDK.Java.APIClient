package com.versionone.apiclient.services;


public class WithVariablesBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if(query.getVariables() != null && !query.getVariables().isEmpty()) {
            result.querystringParts.add("with=" +  TextBuilder.join(query.getVariables(), ";"));
        }
    }
}
