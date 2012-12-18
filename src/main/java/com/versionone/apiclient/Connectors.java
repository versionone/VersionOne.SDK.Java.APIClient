package com.versionone.apiclient;

public class Connectors implements IConnectors {

    private V1APIConnector _dataConnector;
    private V1APIConnector _metaConnector;
    private IUrls _urls;
    private ICredentials _credentials;

    public Connectors(){

        _urls = new Urls();
        _credentials = new Credentials();

        createConnectors();
    }

    private void createConnectors(){

        _dataConnector = new V1APIConnector(
                _urls.getDataUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password()
        );

        _metaConnector = new V1APIConnector(_urls.getMetaUrl());

    }

    public Connectors(IUrls urls, ICredentials credentials){

        if (null == urls || null == credentials)
            throw new IllegalArgumentException("Urls and credentials are required to be non-null.");
        _urls = urls;
        _credentials = credentials;

        createConnectors();

    }

    public V1APIConnector getDataConnector(){
        return _dataConnector;
    }
    public V1APIConnector getMetaConnector(){
        return _metaConnector;
    }

}
