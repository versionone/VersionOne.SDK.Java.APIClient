package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueProvider implements IValueProvider {
    private final List<Object> values = new ArrayList<Object>();

    public ValueProvider(Object[] values) {
        this.values.addAll(Arrays.asList(values));
    }

    public List<Object> getValues() {
        return values;
    }

    public String stringize() {
        return TextBuilder.join(getValues(), ",", TextBuilder.STRINGIZER_DELEGATE.build(new ValueStringizer(), "stringize"));
    }

    public void merge(IValueProvider valueProvider) {
        if(valueProvider.canMerge()) {
            for(Object value : valueProvider.getValues()) {
                values.add(value);
            }
        }
    }

    public Boolean canMerge() {
        return true;
    }
}
