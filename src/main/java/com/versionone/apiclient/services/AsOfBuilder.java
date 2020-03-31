package com.versionone.apiclient.services;

import java.text.SimpleDateFormat;

import com.versionone.apiclient.Query;

public class AsOfBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if(query.getAsOf().compareTo(Query.MIN_DATE) > 0) {
   			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
   			result.addQueryParameter("asof", df.format(query.getAsOf()));
        }
    }
}
