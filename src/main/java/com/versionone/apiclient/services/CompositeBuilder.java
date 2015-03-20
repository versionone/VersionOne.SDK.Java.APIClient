package com.versionone.apiclient.services;

import java.util.ArrayList;
import java.util.List;

import com.versionone.apiclient.Query;

public class CompositeBuilder implements IQueryBuilder {
    public final List<IQueryBuilder> builders = new ArrayList<IQueryBuilder>();

    public BuildResult build(Query query, BuildResult result) {
        for (IQueryBuilder builder : builders) {
            builder.build(query, result);
        }

        return result;
    }
}
