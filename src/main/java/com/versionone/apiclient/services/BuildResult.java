package com.versionone.apiclient.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;


public class BuildResult {
    private final HashMap<String, String> queryParameters = new LinkedHashMap<String, String>();
    public final List<String> pathParts = new ArrayList<String>();

    public void addQueryParameter(String name, String value) {
    	queryParameters.put(name, value);
    }
    
    public int getQueryParameterCount() {
    	return queryParameters.size();
    }
    
    public String urlEncode(String valueString) {
    	try {
            valueString = URLEncoder.encode(valueString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            valueString = valueString.replace("+", "%2B");
            valueString = valueString.replace(" ", "+");
            valueString = valueString.replace("&", "%26");
            valueString = valueString.replace("#", "%23");
        }
    	return valueString;
    }
    
    public String toUrl() {
        List<String> encodedPathParts = new ArrayList<String>();
        for (Iterator<String> iterator = pathParts.iterator(); iterator.hasNext();) {
                String pathPart = iterator.next();
                encodedPathParts.add(urlEncode(pathPart));
        }
        String path = TextBuilder.join(encodedPathParts, "/");
        List<String> queryParts = new ArrayList<String>();
        for (Iterator<Entry<String, String>> iterator = queryParameters.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String> queryParameter = iterator.next();
			queryParts.add(String.format("%s=%s", queryParameter.getKey(), 
        								urlEncode(queryParameter.getValue())));
		}
        String querystring =  TextBuilder.join(queryParts, "&");
        String result = path.concat(querystring != null ? "?" + querystring : "");
        return result;
    }
}