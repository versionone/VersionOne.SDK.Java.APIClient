package com.versionone.apiclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class V1Connector implements IAPIConnector {

	@Override
	public Reader getData() throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getData(String path) throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader sendData(String path, String data) throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream beginRequest(String path, String contentType)
			throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream endRequest(String path) throws ConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

}
