package com.versionone.apiclient.fitnesse;

import java.util.ArrayList;
import java.util.Map;

import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attribute;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.services.QueryResult;

import fit.RowFixture;

public class QueryFixture extends RowFixture {

	@SuppressWarnings("unchecked")
	@Override
	public Class getTargetClass() {
		return QueryFixture.RowData.class;
	}

	@Override
	// args[0] - URL
	// args[1] - user Id
	// args[2] - password
	// args[3] - Asset type
	// args[{4 and above}] - Attributes to query - don't forget to update RowData
	public Object[] query() throws Exception {		
		String basicUrl = args[0];
		
		MetaModel metaModel = new MetaModel(new V1APIConnector(basicUrl + "/meta.v1/"));
		
		Services service = new Services(metaModel, new V1APIConnector(basicUrl + "/rest-1.v1/", args[1], args[2]));
		
		Query query = new Query(metaModel.getAssetType(args[3]));
		
		if(3 < args.length) {
			for(int i = 4; i < args.length; i++) {
				query.getSelection().add(metaModel.getAttributeDefinition(args[3] + "." + args[i]));
			}
		}

		QueryResult queryResults = service.retrieve(query);
		Asset[] results = queryResults.getAssets();
		
		RowData[] rc = new RowData[results.length];
		
		for(int i = 0; i < results.length; ++i) {
			rc[i] = new RowData(results[i]);
		}
		
		return rc;
	}

	/**
	 * This class necessary for the Parse.  The Fitness docs indicated
	 * this could be done with just a list but it failed in Java
	 * @author jerry
	 *
	 */
	public static class ScopeData extends ArrayList<String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ScopeData() {
		}
		
		public static Object parse(String s) {
			ScopeData rc = new ScopeData();
			String[] items = s.replace("[", "").replace("]", "").split(",");
			for(int i = 0; i < items.length; ++i) {
				rc.add(items[i]);
			}
			return rc;
		}
	}
	
	public static class RowData {
		
		public String assetType;
		public String oid;
		public String defaultRole = "defaultRole";
		public String name = "name";
		public String nickName = "nickName";
		public ScopeData scopes = new ScopeData();
		public int scopeCount = 0;
				
		public RowData(Asset asset) throws V1Exception {
			oid = asset.getOid().toString();
			assetType = asset.getAssetType().getToken();
			Map<String, Attribute> attributes = asset.getAttributes();
			setDefaultRole(attributes);
			setName(attributes);
			setNickName(attributes);
			setScopes(attributes);
		}
		
		private void setScopes(Map<String, Attribute> attributes) {
			String key = assetType + ".Scopes";
			if(attributes.containsKey(key)) {
				Attribute attrs = attributes.get(key);
				Object[] values = attrs.getValues();
				scopeCount = values.length;
				for(int i = 0; i < scopeCount; ++i) {					
					scopes.add(values[i].toString());
				}
				
			}
		}

		private void setDefaultRole(Map<String, Attribute> attributes) {
			String key = assetType + ".DefaultRole";
			if(attributes.containsKey(key)){
				defaultRole = getValue(attributes, key);
			}
		}
		
		private void setNickName(Map<String, Attribute> attributes) {
			String key = assetType + ".Nickname";
			if(attributes.containsKey(key)){
				nickName = getValue(attributes, key);
			}			
		}

		private void setName(Map<String, Attribute> attributes) {
			String key = assetType + ".Name";
			if(attributes.containsKey(key)){
				name = getValue(attributes, key);
			}						
		}

		private String getValue(Map<String, Attribute> attributes, String key) {
			try {
				Attribute attr = attributes.get(key);
				Object o = attr.getValue();				
				return o.toString();
			} catch (Exception e) {
				return e.getMessage();
			}			
		}		
	}
}
