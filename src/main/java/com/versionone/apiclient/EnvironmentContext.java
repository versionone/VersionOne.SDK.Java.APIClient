package com.versionone.apiclient;

public final class EnvironmentContext {

    private IModelsAndServices _modelsAndServices;

    public EnvironmentContext(){
        _modelsAndServices = new ModelsAndServices();
    }

    public IMetaModel getMetaModel(){
        return _modelsAndServices.getMetaModel();
    }

    public IServices getServices(){
        return _modelsAndServices.getServices();
    }


}
