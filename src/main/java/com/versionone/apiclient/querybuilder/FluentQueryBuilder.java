package com.versionone.apiclient.querybuilder;

import java.util.List;
import java.util.function.Function;

import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;

public class FluentQueryBuilder implements IFluentQueryBuilder {

	private Function<String, List<IAssetBase>> _executor = null;

	private String _assetTypeName;

	public FluentQueryBuilder(String assetSource,
			Function<String, List<IAssetBase>> execute) {
		this._executor = execute;
		this._assetTypeName = assetSource;
	}

	@Override
	public List<IAssetBase> retrieve() {
		String uri = this.toString();
		return this._executor.apply(uri);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(_assetTypeName);
		return builder.toString();
	}

	// public IAssetBase RetrieveFirst()
	// {
	// String uri = this.toString();
	// results = this._executor(uri);
	// if (results.t > 0) return results[0];
	// return null;
	// }

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

}
