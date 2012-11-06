package com.versionone.apiclient;

public class HierarchicalPartBuilder extends QueryBuilder {
    @Override
    protected void doBuild(Query query, BuildResult result) {
        result.pathParts.add(query.isHistorical() ? "Hist" : "Data");
        result.pathParts.add(query.getAssetType().getToken());

        if (!query.getOid().isNull()) {
            result.pathParts.add(query.getOid().getKey().toString());
        }

        if (query.getOid().hasMoment()) {
            result.pathParts.add(query.getOid().getMoment().toString());
        }
    }
}
