package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public interface IQueryBuilder {
    BuildResult build(Query query, BuildResult result);
}
