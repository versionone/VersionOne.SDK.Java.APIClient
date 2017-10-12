package com.versionone.apiclient.services;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildResult {
    public final List<String> querystringParts = new ArrayList<String>();
    public final List<String> pathParts = new ArrayList<String>();

    public String toUrl() {
        String path = TextBuilder.join(pathParts, "/");
        String querystring = TextBuilder.join(querystringParts, "&");
        String result = path.concat(querystring != null ? "?" + querystring : "");
        result = result.replace(">", "%3E");
        result = result.replace("<", "%3C");

        /*
        result = result.replace(" ","%20");
        result = result.replace("[", "%5B");
        result = result.replace("]", "%5D");
        result = result.replace(">", "%3E");
        result = result.replace("<", "%3C");
        */

        return result;
    }
}


//package com.versionone.apiclient.services;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.lang.String.format;
//
//public class BuildResult {
//    public final List<String> querystringParts = new ArrayList<String>();
//    public final List<String> pathParts = new ArrayList<String>();
//    private final static Log logger = LogFactory.getLog(BuildResult.class);
//
//    public String toUrl() {
//        String path = TextBuilder.join(pathParts, "/");
//
//        //shim to ensure query string parts are properly encoded
//        encodeQuery();
//
//        String querystring = TextBuilder.join(querystringParts, "&");
//        return path.concat(querystring != null ? "?" + querystring : "");
//    }
//
//    private void encodeQuery(){
//
//        //URL Encode all Query String parts
//        int index = 0;
//        for(String querystring: querystringParts){
//            try {
//                final int indexOfEquals = querystring.indexOf("=");
//                final String queryParameter = querystring.substring(0, indexOfEquals);
//                String queryValue = querystring.substring(indexOfEquals+1, querystring.length());
//
//                //decode first in case the rest of the client did encode only part of the value (we don't want to encode values twice!)
//                queryValue = URLDecoder.decode(queryValue, "UTF-8");
//                //replace the query string with a properly encoded string making sure not to encode the HTTP parameter name
//                querystringParts.set(index, queryParameter + "=" + URLEncoder.encode(queryValue, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                logger.error(format("Failed to encode query part while building query string:  %s",querystringParts.get(index)), e);
//            }
//            index++;
//        }
//    }
//}
