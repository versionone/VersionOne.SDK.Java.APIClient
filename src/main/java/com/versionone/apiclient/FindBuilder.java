package com.versionone.apiclient;

import com.versionone.util.StringUtility;

import java.net.URLEncoder;

public class FindBuilder extends QueryBuilder {

    private String _encoding = "UTF-8";

    @Override
    protected void doBuild(Query query, BuildResult result) {

        if (query == null || query.getFind() == null ||
                StringUtility.IsNullOrEmpty(query.getFind().text) == true) return;

        String part = String.format("find=%s", URLEncoder.encode(query.getFind().text), _encoding); //temporary encoding solution until apache httpclient is brought in from beta.
        result.querystringParts.add(part);

        if (query.getFind().attributes == null ||
                query.getFind().attributes.size() == 0 ||
                StringUtility.IsNullOrEmpty(query.getFind().attributes.getToken())) return;

        part = String.format("findin=%s", URLEncoder.encode(query.getFind().attributes.getToken()), _encoding); //temporary encoding solution until apache httpclient is brought in from beta.
        result.querystringParts.add(part);

    }
}
