package com.versionone.apiclient;

import org.apache.http.message.BasicNameValuePair;

public class WhereBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        IFilterTerm filter2token = query.getFilter();

        if (filter2token != null) {
            try {
                result.querystringParts.add(new BasicNameValuePair("where", filter2token.getToken()));
            } catch (APIException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
