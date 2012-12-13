package com.versionone.apiclient;

import org.apache.http.message.BasicNameValuePair;

public class FindBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getFind() != null) {
            String findText = query.getFind().text;

            if (findText != null && findText.trim().length()!=0) {
                result.querystringParts.add(new BasicNameValuePair("find", "\"" + findText + "\""));

                if (query.getFind().attributes.size() > 0) {
                     result.querystringParts.add(new BasicNameValuePair("findin", query.getFind().attributes.getToken()));
                }
            }
        }
    }
}
