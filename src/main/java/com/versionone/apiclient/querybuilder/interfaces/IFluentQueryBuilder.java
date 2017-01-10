/**
 * 
 */
package com.versionone.apiclient.querybuilder.interfaces;

import java.util.List;

public interface IFluentQueryBuilder {

		IFluentQueryBuilder Id(Object id);
		IFluentQueryBuilder select(Object...  fields);
		IFluentQueryBuilder where(String attributeName, String matchValue);
		List<IAssetBase> retrieve();

}
