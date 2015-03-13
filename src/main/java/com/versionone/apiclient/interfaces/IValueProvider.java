package com.versionone.apiclient.interfaces;

import java.util.List;

public interface IValueProvider {
    List<Object> getValues();
    String stringize();
    void merge(IValueProvider valueProvider);
    Boolean canMerge();
}
