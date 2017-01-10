package com.versionone.apiclient.querybuilder.interfaces;

import java.util.Map;

public interface IAssetBase extends IOidToken , Map<String, Object>{
	
		public static final String assetTypeName = ""; 
		public Object attributes = null;
		public void addRelatedAsset(String relationName, IAssetBase asset);
		//public Object this[String]; 

}
