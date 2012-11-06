package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryVariable implements IValueProvider {
    public final String name;
    private final List<Object> values = new ArrayList<Object>();

    public QueryVariable(String name, Object... values) {
        Collections.addAll(this.values, values);
        this.name = name;
    }

    public List<Object> getValues() {
        return values;
    }

    public String getToken() {
        return  String.format("$%s", name);
    }

    public String stringize() {
        return getToken();
    }

    public void merge(IValueProvider valueProvider) {
        throw new UnsupportedOperationException("We do not merge variables with anything else until we're sure we should do.");
    }

    public Boolean canMerge() {
        return false;
    }
    
    public String toString(){
        return String.format("%s=%s", getToken(), TextBuilder.join(values, ",", TextBuilder.STRINGIZER_DELEGATE.build(new ValueStringizer(""), "stringize")));
    }
}