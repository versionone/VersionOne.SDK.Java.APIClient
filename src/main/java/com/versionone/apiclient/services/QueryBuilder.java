package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public abstract class QueryBuilder implements IQueryBuilder {
    public BuildResult build(Query query, BuildResult result) {
        doBuild(query, result);
        return result;
    }

    protected abstract void doBuild(Query query, BuildResult result);
}
