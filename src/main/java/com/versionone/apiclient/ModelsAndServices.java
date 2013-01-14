package com.versionone.apiclient;

import com.versionone.DB;

import java.io.IOException;
import java.net.URISyntaxException;

public final class ModelsAndServices implements IModelsAndServices  {

    private IConnectors _connectors;
    private IMetaModel _metaModel;
    private IMetaModel _metaModelWithProxy;
    private IServices _services;
    private IServices _servicesWithProxy;
    private V1Configuration _v1Config;
    private V1Configuration _v1ConfigWithProxy;

    public ModelsAndServices() throws Exception {
        _connectors = new Connectors();
    }

    public ModelsAndServices(IConnectors connectors){
        _connectors = connectors;
    }

    public IMetaModel getMetaModel(){
        if (_metaModel != null) return _metaModel;
        _metaModel = new MetaModel(_connectors.getMetaConnector());
        return _metaModel;
    }

    public IMetaModel getMetaModelWithProxy() throws URISyntaxException {
        if (_metaModelWithProxy != null) return _metaModelWithProxy;
        _metaModelWithProxy = new MetaModel(_connectors.getMetaConnectorWithProxy());
        return _metaModelWithProxy;
    }

    public IServices getServices(){
        if (_services != null) return _services;
        _services = new com.versionone.apiclient.Services(
                getMetaModel(),
                _connectors.getDataConnector()
        );
        return _services;
    }

    public IServices getServicesWithProxy() throws URISyntaxException {
        if (_servicesWithProxy != null) return _servicesWithProxy;
        _servicesWithProxy = new Services(
                getMetaModelWithProxy(),
                _connectors.getDataConnectorWithProxy());
        return _servicesWithProxy;
    }


    public V1Configuration getV1Configuration(){
        if (_v1Config != null) return _v1Config;
        _v1Config = new V1Configuration(_connectors.getConfigConnector());
        return _v1Config;
    }

    public V1Configuration getV1ConfigurationWithProxy() throws URISyntaxException {
        if (_v1Config != null) return _v1Config;
        _v1Config = new V1Configuration(_connectors.getConfigConnectorWithProxy());
        return _v1Config;
    }

}
