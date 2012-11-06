package com.versionone.apiclient;

/**
 * Responsible for building the query
 *
 * @author jerry
 */
public class QueryURLBuilder extends CompositeBuilder {
    private final Query query;

    public QueryURLBuilder(Query query) {
        this.query = query;

        builders.add(new HierarchicalPartBuilder());
        builders.add(new SelectionBuilder());
        builders.add(new WhereBuilder());
        builders.add(new SortBuilder());
        builders.add(new PagingBuilder());
        builders.add(new AsOfBuilder());
        builders.add(new FindBuilder());
        builders.add(new WithVariablesBuilder());
    }

    @Override
    public String toString() {
        return build(query, new BuildResult()).toUrl();
    }
}