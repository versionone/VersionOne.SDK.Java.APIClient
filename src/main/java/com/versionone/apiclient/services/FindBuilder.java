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

        result.addQueryParameter("find", query.getFind().text);

        if (query.getFind().attributes == null ||
                query.getFind().attributes.size() == 0 ||
                V1Util.isNullOrEmpty(query.getFind().attributes.getToken())) return;

        result.addQueryParameter("findin", query.getFind().attributes.getToken());
    }
}
