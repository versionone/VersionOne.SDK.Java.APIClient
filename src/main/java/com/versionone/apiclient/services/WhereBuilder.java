package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.filters.IFilterTerm;

public class WhereBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        IFilterTerm filter2token = query.getFilter();

        if (filter2token != null) {
            try {
                result.querystringParts.add("where=" + filter2token.getToken());
            } catch (APIException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
