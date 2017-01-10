package com.versionone.apiclient.querybuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;

public class FluentQueryBuilder implements IFluentQueryBuilder {



	public FluentQueryBuilder(String assetSource,
			Function<String, List<IAssetBase>> execute) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IFluentQueryBuilder Id(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFluentQueryBuilder select(Object... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFluentQueryBuilder where(String attributeName, String matchValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAssetBase> retrieve() {
		// TODO Auto-generated method stub
		return null;
	}

}
