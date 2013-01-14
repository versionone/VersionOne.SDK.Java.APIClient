package com.versionone.apiclient;

import java.net.URISyntaxException;

public interface IModelsAndServices {
        IMetaModel getMetaModel();
        IMetaModel getMetaModelWithProxy() throws URISyntaxException;
        IServices getServices();
        IServices getServicesWithProxy() throws URISyntaxException;
        V1Configuration getV1Configuration();
        V1Configuration getV1ConfigurationWithProxy() throws URISyntaxException;
}
