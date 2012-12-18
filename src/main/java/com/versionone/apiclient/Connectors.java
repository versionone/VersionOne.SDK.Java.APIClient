package com.versionone.apiclient;

public class Connectors implements IConnectors {

    private V1APIConnector _dataConnector;
    private V1APIConnector _metaConnector;
    private IUrls _urls;
    private ICredentials _credentials;

    public Connectors(){

        _urls = new Urls();
        _credentials = new Credentials();
        _dataConnector = new V1APIConnector(
                _urls.getDataUrl(),
                _credentials.getV1UserName(),
                _credentials.getV1Password()
        );

        _metaConnector = new V1APIConnector(_urls.getMetaUrl());

    }

    public V1APIConnector getDataConnector(){
        return _dataConnector;
    }
    public V1APIConnector getMetaConnector(){
        return _metaConnector;
    }

}
