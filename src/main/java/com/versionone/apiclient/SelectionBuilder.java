package com.versionone.apiclient;

import java.net.URLEncoder;

import com.versionone.apiclient.services.BuildResult;
import com.versionone.apiclient.services.QueryBuilder;


public class SelectionBuilder extends QueryBuilder {
	
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getParentRelation() != null) {
            query.getSelection().add(query.getParentRelation());
        }

        if (query.getSelection().size() == 1 && !query.getOid().isNull()) {
            result.pathParts.add(query.getSelection().get(0).getName());
        } else if (query.getSelection().size() > 0) {
            result.querystringParts.add("sel=" + URLEncoder.encode(query.getSelection().getToken()));
        } else {
            result.querystringParts.add("sel=");
        }
    }
}
