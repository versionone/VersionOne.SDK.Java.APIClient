package com.versionone.apiclient;

import java.io.IOException;

public final class ModelsAndServices implements IModelsAndServices  {

    private IConnectors _connectors;
    private IMetaModel _metaModel;
    private IServices _services;

    public ModelsAndServices() throws IOException {
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

    public IServices getServices(){
        if (_services != null) return _services;
        _services = new com.versionone.apiclient.Services(
                getMetaModel(),
                _connectors.getDataConnector()
        );
        return _services;
    }

}
