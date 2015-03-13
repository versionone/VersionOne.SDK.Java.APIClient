package com.versionone.apiclient.interfaces;

import java.net.URISyntaxException;

import com.versionone.apiclient.V1Configuration;

public interface IModelsAndServices {
        IMetaModel getMetaModel();
        IMetaModel getMetaModelWithProxy() throws URISyntaxException;
        IServices getServices();
        IServices getServicesWithProxy() throws URISyntaxException;
        V1Configuration getV1Configuration();
        V1Configuration getV1ConfigurationWithProxy() throws URISyntaxException;
}
