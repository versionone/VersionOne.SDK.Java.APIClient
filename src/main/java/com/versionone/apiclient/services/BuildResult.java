package com.versionone.apiclient.services;

import java.util.ArrayList;
import java.util.List;

public class BuildResult {
    public final List<String> querystringParts = new ArrayList<String>();
    public final List<String> pathParts = new ArrayList<String>();

    public String toUrl() {
        String path = TextBuilder.join(pathParts, "/");
        String querystring = TextBuilder.join(querystringParts, "&");
        String result = path.concat(querystring != null ? "?" + querystring : "");

        result = result.replace(" ","%20");
        result = result.replace("[", "%5B");
        result = result.replace("]", "%5D");

        return result;
    }
}
