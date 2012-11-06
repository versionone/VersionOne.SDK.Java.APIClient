package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.List;

public class BuildResult {
    public final List<String> querystringParts = new ArrayList<String>();
    public final List<String> pathParts = new ArrayList<String>();

    public String toUrl() {
        String path = TextBuilder.join(pathParts, "/");
        String querystring = TextBuilder.join(querystringParts, "&");
        String result = path.concat(querystring != null ? "?" + querystring : "");

        //TODO find a to replace following with proper encoding (.Net HttpWebRequest do it by itself,
        // maybe Java HttpUrlConnection can be forced to do it)
        result = result.replace(" ","%20");
        result = result.replace("[", "%5B");
        result = result.replace("]", "%5D");

        return result;
    }
}
