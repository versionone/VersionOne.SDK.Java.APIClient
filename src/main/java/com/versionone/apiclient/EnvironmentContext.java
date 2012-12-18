package com.versionone.apiclient;

public final class EnvironmentContext {

    private class Urls implements IUrls {
        public String getV1Url(){
            return "https://www14.v1host.com/v1sdktesting/";
        }
        public String getMetaUrl(){
            return getV1Url().concat("meta.v1/");
        }
        public String getDataUrl(){
            return getV1Url().concat("rest-1.v1/");
        }
    }

    private class Credentials implements ICredentials {
        public String getV1UserName(){
            return "admin";
        }
        public String getV1Password(){
            return "admin";
        }
    }

    public final class ModelsAndServices implements IModelsAndServices  {

        private Connectors _connectors;
        private IMetaModel _metaModel;
        private IServices _services;

        public ModelsAndServices(){
            _connectors = new Connectors();
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

    private class Connectors{

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

        private V1APIConnector getDataConnector(){
            return _dataConnector;
        }
        private V1APIConnector getMetaConnector(){
            return _metaConnector;
        }
    }

}
