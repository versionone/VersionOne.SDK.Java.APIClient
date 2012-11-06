package com.versionone.apiclient;

public class FindBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        if (query.getFind() != null) {
            String findText = query.getFind().text;

            if (findText != null && findText.trim().length()!=0) {
                result.querystringParts.add("find=\"" + findText + "\"");

                if (query.getFind().attributes.size() > 0) {
                    result.querystringParts.add("findin=" + query.getFind().attributes.getToken());
                }
            }
        }
    }
}
