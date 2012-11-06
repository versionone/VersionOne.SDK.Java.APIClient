package com.versionone.apiclient;

import java.util.ArrayList;
import java.util.List;

public class CompositeBuilder implements IQueryBuilder {
    public final List<IQueryBuilder> builders = new ArrayList<IQueryBuilder>();

    public BuildResult build(Query query, BuildResult result) {
        for (IQueryBuilder builder : builders) {
            builder.build(query, result);
        }

        return result;
    }
}
