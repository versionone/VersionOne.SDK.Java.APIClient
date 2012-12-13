package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class BuildResult {
    public final List<NameValuePair> querystringParts = new ArrayList<NameValuePair>();
    public final List<String> pathParts = new ArrayList<String>();

    public String toUrl() {
        String path = TextBuilder.join(pathParts, "/");
        String querystring = URLEncodedUtils.format(querystringParts, "UTF-8");
        
        if(querystring != null) {
        	return path.concat("?" + querystring);
        }
        return path;
    }
}
