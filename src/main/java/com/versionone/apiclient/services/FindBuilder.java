package com.versionone.apiclient.services;

import java.net.URLEncoder;

import com.versionone.apiclient.Query;
import com.versionone.utils.V1Util;

public class FindBuilder extends QueryBuilder {

    private String _encoding = "UTF-8";

    @Override
    protected void doBuild(Query query, BuildResult result) {

        if (query == null || query.getFind() == null ||
                V1Util.isNullOrEmpty(query.getFind().text) == true) return;

        String part = String.format("find=%s", URLEncoder.encode(query.getFind().text), _encoding); //temporary encoding solution until apache httpclient is brought in from beta.
        result.querystringParts.add(part);

        if (query.getFind().attributes == null ||
                query.getFind().attributes.size() == 0 ||
                V1Util.isNullOrEmpty(query.getFind().attributes.getToken())) return;

        part = String.format("findin=%s", URLEncoder.encode(query.getFind().attributes.getToken()), _encoding); //temporary encoding solution until apache httpclient is brought in from beta.
        result.querystringParts.add(part);

    }
}
