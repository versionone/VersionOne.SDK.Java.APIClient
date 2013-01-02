package com.versionone.apiclient;

import java.io.IOException;

public final class EnvironmentContext {

    private IModelsAndServices _modelsAndServices;

    public EnvironmentContext() throws IOException {
        _modelsAndServices = new ModelsAndServices();
    }

    //inject your own IModelsAndServices implementation if necessary
    public EnvironmentContext(IModelsAndServices modelsAndServices){
        if (null == modelsAndServices) throw new IllegalArgumentException("modelsAndServices cannot be null");
        _modelsAndServices = modelsAndServices;
    }

    public IMetaModel getMetaModel(){
        return _modelsAndServices.getMetaModel();
    }

    public IServices getServices(){
        return _modelsAndServices.getServices();
    }


}
