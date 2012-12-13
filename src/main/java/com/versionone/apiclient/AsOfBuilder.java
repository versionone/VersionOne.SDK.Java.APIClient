package com.versionone.apiclient;

import java.text.SimpleDateFormat;

import org.apache.http.message.BasicNameValuePair;

public class AsOfBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if(query.getAsOf().compareTo(Query.MIN_DATE) > 0) {
   			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            //result.querystringParts.add("asof=" + df.format(query.getAsOf()));
            result.querystringParts.add(
            		new BasicNameValuePair(
            				"asof", df.format(query.getAsOf()) //query.getAsOf().toString()
            				));
        }
    }
}
