package com.versionone.apiclient;

public interface IQueryBuilder {
    BuildResult build(Query query, BuildResult result);
}
