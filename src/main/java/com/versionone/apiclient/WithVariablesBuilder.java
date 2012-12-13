package com.versionone.apiclient;

import org.apache.http.message.BasicNameValuePair;

public class WithVariablesBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if(query.getVariables() != null && !query.getVariables().isEmpty()) {
            result.querystringParts.add(new BasicNameValuePair("with", TextBuilder.join(query.getVariables(), ";")));
        }
    }
}
