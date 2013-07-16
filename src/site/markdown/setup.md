## Learn By Example: APIClient Setup

Using the APIClient is as simple as making a reference to the APIClient.dll in your .Net project, then providing connection information to the main service objects within the APIClient. There are three possible ways to connect to your VersionOne instance using the APIClient. Before you attempt to connect, find out whether your VersionOne instance uses VersionOne authentication or Windows Integrated Authentication. You need to create an instance of IMetaModel and and instance of IServices and provide them with connection information via instances of the V1APIConnector. The first example uses VersionOne authentication:

```
V1APIConnector dataConnector = new V1APIConnector("http://server/v1instance/rest-1.v1/", "username", "password");
V1APIConnector metaConnector = new V1APIConnector("http://server/v1instance/meta.v1/");
metaModel = new MetaModel(metaConnector);
services = new Services(metaModel, dataConnector);
```

If your VersionOne instance uses Windows Integrated Authentication, and you wish to connect to the API using the credentials of the user running your program, you can omit the username and password arguments to the V1APIConnector:

```
V1APIConnector dataConnector = new V1APIConnector("http://server/v1instance/rest-1.v1/");
V1APIConnector metaConnector = new V1APIConnector("http://server/v1instance/meta.v1/");
IMetaModel metaModel = new MetaModel(metaConnector);
IServices services = new Services(metaModel, dataConnector);
```
