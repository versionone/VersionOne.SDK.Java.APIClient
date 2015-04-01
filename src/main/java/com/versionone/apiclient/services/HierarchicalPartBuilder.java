package com.versionone.apiclient.services;

import com.versionone.apiclient.Query;


public class HierarchicalPartBuilder extends QueryBuilder {
	private boolean isV1connector =  false;
	
    public HierarchicalPartBuilder(boolean isV1connector) {
		this.isV1connector = isV1connector;
	}

	@Override
    protected void doBuild(Query query, BuildResult result) {
		if (!isV1connector == true)
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
