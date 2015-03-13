package com.versionone.apiclient.services;


public interface IQueryBuilder {
    BuildResult build(Query query, BuildResult result);
}
