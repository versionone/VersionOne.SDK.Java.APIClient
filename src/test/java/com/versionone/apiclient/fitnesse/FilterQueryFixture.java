package com.versionone.apiclient.fitnesse;

import java.util.ArrayList;
import java.util.List;

import com.versionone.apiclient.*;

import fit.RowFixture;

public class FilterQueryFixture extends RowFixture {

    @SuppressWarnings("unchecked")
    @Override
    public Class getTargetClass() {
        return FilterQueryFixture.RowData.class;
    }

    @Override
    // args[0] - URL
    // args[1] - user Id
    // args[2] - password
    // args[3] - Asset type
    // args[4] - Where
    public Object[] query() throws Exception {
        String basicUrl = args[0];

        MetaModel metaModel = new MetaModel(new V1APIConnector(basicUrl + "/meta.v1/"));

        ClientConfiguration config = new ClientConfiguration(basicUrl, args[1], args[2]);
        Services service = new Services(metaModel, new V1APIConnector(basicUrl + "/rest-1.v1/", config));

        Query query = new Query(metaModel.getAssetType(args[3]));

        query.setFilter(parse(metaModel, args[3], args[4]));
        QueryResult queryResults = service.retrieve(query);
        Asset[] results = queryResults.getAssets();

        RowData[] rc = new RowData[results.length];

        for (int i = 0; i < results.length; ++i) {
            rc[i] = new RowData(results[i]);
        }

        return rc;
    }

    public class RowData {
        public String oid;

        RowData(Asset asset) {
            oid = asset.getOid().toString();
        }
    }

    private IFilterTerm parse(MetaModel model, String assetType, String where) throws V1Exception {
        if (where.contains(";")) {
            return parseAnd(model, assetType, where);
        } else if (where.contains("|")) {
            return parseOr(model, assetType, where);
        } else {
            return parseOneTerm(model, assetType, where);
        }
    }

    private IFilterTerm parseAnd(MetaModel model, String assetType, String where) throws V1Exception {
        List<IFilterTerm> allTerms = new ArrayList<IFilterTerm>();
        String[] terms = where.split(";");
        for (String oneTerm : terms) {
            allTerms.add(parse(model, assetType, oneTerm));
        }
        return new AndFilterTerm(allTerms.toArray(new IFilterTerm[allTerms.size()]));
    }

    private IFilterTerm parseOr(MetaModel model, String assetType, String where) throws V1Exception {
        List<IFilterTerm> allTerms = new ArrayList<IFilterTerm>();
        String[] terms = where.split("\\|");
        for (String oneTerm : terms) {
            allTerms.add(parse(model, assetType, oneTerm));
        }
        return new OrFilterTerm(allTerms.toArray(new IFilterTerm[allTerms.size()]));
    }

    private IFilterTerm parseOneTerm(MetaModel model, String assetType, String where) throws V1Exception {
        FilterTerm oneTerm = null;
        String[] terms = null;
        if (where.contains("!=")) {
            terms = where.split("!=");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.notEqual(terms[1].replace("'", ""));
        } else if (where.contains("<=")) {
            terms = where.split("<=");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.lessOrEqual(terms[1].replace("'", ""));
        } else if (where.contains(">=")) {
            terms = where.split(">=");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.greaterOrEqual(terms[1].replace("'", ""));
        } else if (where.contains("<")) {
            terms = where.split("<");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.less(terms[1].replace("'", ""));
        } else if (where.contains(">")) {
            terms = where.split(">");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.greater(terms[1].replace("'", ""));
        } else {
            terms = where.split("=");
            oneTerm = new FilterTerm(model.getAttributeDefinition(assetType + "." + terms[0]));
            oneTerm.equal(terms[1].replace("'", ""));
        }

        return oneTerm;
    }
}
