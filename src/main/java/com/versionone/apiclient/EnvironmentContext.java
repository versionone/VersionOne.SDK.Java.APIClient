package com.versionone.apiclient;

public final class EnvironmentContext {

    private static final class Urls {
        public static String getV1Url(){
            return "https://www14.v1host.com/v1sdktesting/";
        }
        public static String getDataUrl(){
            return getV1Url().concat("rest-1.v1/");
        }
        public static String getMetaUrl(){
            return getV1Url().concat("meta.v1/");
        }
    }

    private static final class Credentials{
        public static String getV1UserName(){
            return "admin";
        }
        public static String getV1Password(){
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

    private static final class Connectors{

        private V1APIConnector _dataConnector;
        private V1APIConnector _metaConnector;

        public Connectors(){
            _dataConnector = new V1APIConnector(
                    EnvironmentContext.Urls.getDataUrl(),
                    EnvironmentContext.Credentials.getV1UserName(),
                    EnvironmentContext.Credentials.getV1Password()
                    );
            _metaConnector = new V1APIConnector(
                    EnvironmentContext.Urls.getMetaUrl(),
                    EnvironmentContext.Credentials.getV1UserName(),
                    EnvironmentContext.Credentials.getV1Password()
                    );
        }

        private V1APIConnector getDataConnector(){
            return _dataConnector;
        }
        private V1APIConnector getMetaConnector(){
            return _metaConnector;
        }
    }

}
