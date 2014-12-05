# VersionOne Java SDK
Copyright (c) 2008-2014 [VersionOne](http://versionone.com/).
All rights reserved.

The VersionOne Java SDK is a free and open-source library that accelerates software development of applications that integrate with VersionOne. The SDK serves as a wrapper to the VersionOne REST API, eliminating the need to code the infrastructure necessary for direct handling of HTTP requests and responses.

The Java SDK is open source and is licensed under a modified BSD license, which reflects our intent that software built with a dependency on the SDK can be for commercial and/or open source products.

## System Requirements

* Java Development Kit 1.7

## Supported IDEs for Development

* Eclipse or IntelliJ IDEA

## Adding the Java SDK to your project

The compiled version of the Java SDK is available as a downloadable ZIP file from the [VersionOne Application Catalog](http://v1appcatalog.azurewebsites.net/app/index.html#/Details/VersionOne.SDK.Java.APIClient). When you extract the ZIP file, you will find a jar file named VersionOne.SDK.Java.APIClient-XXX.jar that you can reference in your Java project.

Alternatively, you can use [Maven](http://maven.apache.org/guides/introduction/introduction-to-the-pom.html) to import the Java SDK and it's dependencies from [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22VersionOne.SDK.Java.APIClient%22) by adding the following dependency to your project's POM file:

```xml
<dependency>
    <groupId>com.versionone</groupId>
    <artifactId>VersionOne.SDK.Java.APIClient</artifactId>
    <version>XX.X.X</version>
</dependency>
```

## Connecting  to VersionOne

Using the Java SDK is as simple as making a reference to the Java SDK jar file in your project, then providing the appropriate connection information to the V1APIConnector class.

### Creating a connection using V1APIConnector

Before attempting to connect, you should determine if your VersionOne instance uses Basic (username and password) or Windows Integrated Authentication (NTLM). Once you have that information and the URL of your VersionOne instance, you'll need to create an instance of the **IMetaModel** and **IServices** classes, providing them with connection information via separate instances of the **V1APIConnector** class as in the following example:

```java
V1APIConnector _dataConnector = new V1APIConnector("http://localhost/versionone/rest-1.v1/", "admin", "admin");
V1APIConnector _metaConnector = new V1APIConnector("http://localhost/versionone/meta.v1/");
	    
IMetaModel _metaModel = new MetaModel(_metaConnector);
IServices _services = new Services(_metaModel, _dataConnector);
```
> The example above is based on having VersionOne installed locally.

### Creating a connection using EnvironmentContext

As an alternative to using the **V1APIConnector** class, the Java SDK provides an **EnvironmentContext** class that reads information from a Java properties file.

Open (not extract) the .jar file using an file archiving tool. Navigate to "\com\versionone\apiclient\", and edit the APIConfiguration.properties file to match your environment using these property definitions:

<table summary="System Names" cellspacing="0" cellpadding="0" border="0">
<thead>
    <tr>
        <th>Property</th>
        <th>Description</th>
    </tr>
</thead>
<tbody>
    <tr>
        <td>V1Url</td>
        <td>The URL of your instance of the VersionOne software. It's the URL you use to login.</td>
    </tr>
    <tr>
        <td>V1UserName</td>
        <td>The VersionOne user name that the API will impersonate as it executes.  Must be an existing user in the system. Leave this blank if you are using Windows authentication.</td>
    </tr>
    <tr>
        <td>V1Password</td>
        <td>The VersionOne user password that the API will need to login to the instance.  Leave this blank if you are using Windows authentication.</td>
    </tr>
    <tr>
        <td>MetaUrl</td>
        <td>The URI path to meta. You should not need to change this setting under normal circumstances.</td>
    </tr>
    <tr>
        <td>DataUrl</td>
        <td>The URI path to retrieve data. You should not need to change this setting under normal circumstances.</td>
    </tr>
    <tr>
        <td>ConfigUrl</td>
        <td>The URI path to retrieve VersionOne configuration info through the API. You should not need to change this setting under normal circumstances.</td>
    </tr>
    <tr>
        <td>ProxyUrl</td>
        <td>If you use a proxy server to logon to your VersionOne instance then provide the address here.</td>
    </tr>
    <tr>
        <td>ProxyUserName</td>
        <td>The user that should authenticate to the proxy server.</td>
    </tr>
    <tr>
        <td>ProxyPassword</td>
        <td>The password for the proxy user above.</td>
    </tr>
</tbody>
</table>

This example shows how to connect using the **EnvironmentContext** class:

```java
public class DataExamples {

    private EnvironmentContext _context;

    private IMetaModel _metaModel;
    private IMetaModel _metaModelWithProxy;
    private IServices _services;
    private IServices _servicesWithProxy;
    private V1Configuration _config;

    public DataExamples() throws Exception {

        _context = new EnvironmentContext();

        _metaModel = _context.getMetaModel();
        _metaModelWithProxy = _context.getMetaModelWithProxy();  //if you use a proxy server
        _services = _context.getServices();
        _servicesWithProxy = _context.getSerivcesWithProxy();  //if you use a proxy server
        _config = _context.getV1Configuration();
    }
```
### Using Windows Integrated Authentication

If your VersionOne instance uses Windows Integrated Authentication, and you wish to connect to the API using the credentials of the user running your program, you can omit the username and password arguments in the  **V1APIConnector** class or in APIConfiguration.properties file as described above.


### Using a custom user-agent header
When making using the Java SDK for VersionOne API calls, it is recommended that you create a custom user-agent header to pass along to the API. The V1APIConnector class in the Java SDK has a setUserAgentHeader method that can be used to pass a custom header to the VersionOne API like this:

```java
_dataConnector.setUserAgentHeader("YourAppName", "1.0.0");
```

> For more information about custom user-agent strings, see [HTTP User-Agent Header](https://community.versionone.com/Developers/Developer-Library/Concepts/HTTP_User-Agent_Header) in the VersionOne Developer Library.

## Querying Data

This section is a series of examples, starting with simpler queries and moving to more advanced queries. You'll need to create an instance of both **IMetaModel** and **IServices**, as outlined above, to perform the queries.

### How to query configuration information

```java
public boolean IsEffortTrackingEnabled() throws Exception {

        return _config.isEffortTracking();

        /***** OUTPUT *****
         False
         ******************/
    }
    
public void StoryAndDefectTrackingLevel() throws Exception {

        System.out.println(_config.getStoryTrackingLevel());
        System.out.println(_config.getDefectTrackingLevel());

        /***** OUTPUT *****
         Off
         On
         ******************/
    }
```

### How to query for an asset

Retrieve the Member with ID 20:

```java
public Asset SingleAsset() throws Exception {

        Oid memberId = Oid.fromToken("Member:20", _metaModel);
        Query query = new Query(memberId);
        QueryResult result = _services.retrieve(query);
        Asset member = result.getAssets()[0];

        System.out.println(member.getOid().getToken());
        return member;

        /***** OUTPUT *****
         Member:20
         ******************/
    }
```

In this example, the asset will have its Oid populated, but will not have any other attributes populated. This is to minimize the size of the data sets returned. The next example shows how to ask for an asset with specific attributes populated.

### How to query for specific attributes

Retrieve an asset with populated attributes by using the **Selection** property of the **Query** class.

```java
public Asset SingleAssetWithAttributes() throws Exception {

        Oid memberId = Oid.fromToken("Member:20", _metaModel);
        Query query = new Query(memberId);
        IAttributeDefinition nameAttribute = _metaModel.getAttributeDefinition("Member.Name");
        IAttributeDefinition emailAttribute = _metaModel.getAttributeDefinition("Member.Email");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(emailAttribute);
        QueryResult result = _services.retrieve(query);
        Asset member = result.getAssets()[0];

        System.out.println(member.getOid().getToken());
        System.out.println(member.getAttribute(nameAttribute).getValue());
        System.out.println(member.getAttribute(emailAttribute).getValue());
        return member;

        /***** OUTPUT *****
         Member:20
         Administrator
         admin@company.com
         ******************/
    }
```

### How to get a list of all Story assets

```java
public Asset[] ListOfAssets() throws Exception {

        IAssetType storyType = _metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        QueryResult result = _services.retrieve(query);

        for (Asset story : result.getAssets()) {
            System.out.println(story.getOid().getToken());
            System.out.println(story.getAttribute(nameAttribute).getValue());
            System.out.println(story.getAttribute(estimateAttribute).getValue());
            System.out.println();
        }

        return result.getAssets();

        /***** OUTPUT *****
         Story:1083
         View Daily Call Count
         5

         Story:1554
         Multi-View Customer Calendar
         1 ...
         ******************/
    }
```

Depending on your security role, you may not be able to see all the Story assets in the entire system.

### How to filter a query

Use the **setFilter** property of the **Query** class to filter the results that are returned. This query will retrieve only Task assets with a ToDo value of zero:

```java
public Asset[] FilterListOfAssets() throws Exception {

	IAssetType taskType = _metaModel.getAssetType("Task");
    Query query = new Query(taskType);
    IAttributeDefinition nameAttribute = taskType.getAttributeDefinition("Name");
    IAttributeDefinition todoAttribute = taskType.getAttributeDefinition("ToDo");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(todoAttribute);

    FilterTerm toDoTerm = new FilterTerm(todoAttribute);
    toDoTerm.equal(0);
    query.setFilter(toDoTerm);
    QueryResult result = _services.retrieve(query);

    for (Asset task : result.getAssets()) {
        System.out.println(task.getOid().getToken());
        System.out.println(task.getAttribute(nameAttribute).getValue());
        System.out.println(task.getAttribute(todoAttribute).getValue());
        System.out.println();
    }

    return result.getAssets();

    /***** OUTPUT *****
     Task:1153
     Code Review
     0

     Task:1154
     Design Component
     0 ...
     ******************/
}
```

### How to filter a query with multiple attributes

To filter on multiple attributes, use the **GroupFilterTerm** class to combine filter terms. This example shows how to retreive a set of Defects that are in a specific project and have a ToDo value of zero:

```java
public Asset[] FilterListOfAssetsWithMultipleAttributes(String projectOid) throws Exception {

    IAssetType assetType = _metaModel.getAssetType("Defect");
     
    //Create the query and set the attributes to select.
    Query query = new Query(assetType);
    IAttributeDefinition projectAttribute = assetType.getAttributeDefinition("Scope");
    IAttributeDefinition todoAttribute = assetType.getAttributeDefinition("ToDo");
    query.getSelection().add(projectAttribute);
    query.getSelection().add(todoAttribute);
        
    //Set the terms to filter with.
    FilterTerm projectTerm = new FilterTerm(projectAttribute);
    projectTerm.equal(projectOid);
    FilterTerm todoTerm = new FilterTerm(todoAttribute);
    todoTerm.equal(0);

    //Create the group filter.
    GroupFilterTerm groupFilter = new AndFilterTerm(projectTerm, todoTerm);
    query.setFilter(groupFilter);
        
    //Execute the query and display the results.
    QueryResult result = _services.retrieve(query);	

    for (Asset task : result.getAssets()) {
        System.out.println(task.getOid().getToken());
        System.out.println(task.getAttribute(projectAttribute).getValue());
        System.out.println(task.getAttribute(todoAttribute).getValue());
        System.out.println();
    }

    return result.getAssets();

    /***** OUTPUT *****
	 Defect:37396
	 Scope:37367
	 0.0
			
	 Defect:39675
	 Scope:37367
	 0.0
    ******************/
}
```

### How to use find in a query

Use the **Find** property of the **Query** class to search for text. This query will retrieve only Request assets with the word "Urgent" in the name:

```java
public Asset[] FindListOfAssets() throws Exception {

        IAssetType requestType = _metaModel.getAssetType("Story");
        Query query = new Query(requestType);
        IAttributeDefinition nameAttribute = requestType.getAttributeDefinition("Name");

        query.getSelection().add(nameAttribute);

        AttributeSelection selection = new AttributeSelection();
        selection.add(nameAttribute);
        //query.setFind(new QueryFind("Urgent", selection)); //if you'd like find only stories marked as urgent, for example.

        QueryResult result = _services.retrieve(query);

        for (Asset request : result.getAssets())
        {
            System.out.println(request.getOid().getToken());
            System.out.println(request.getAttribute(nameAttribute).getValue());
            System.out.println();
        }

        return result.getAssets();

        /***** OUTPUT *****
         Request:1195
         Urgent!  Filter by owner

         Task:1244
         Urgent: improve search performance ...
         ******************/
    }
```

### How to sort a query

Use the **OrderBy** property of the **Query** class to sort the results. This query will retrieve Story assets sorted by increasing Estimate:

```java
public Asset[] SortListOfAssets() throws Exception {

        IAssetType storyType = _metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        query.getOrderBy().minorSort(estimateAttribute, OrderBy.Order.Ascending);
        QueryResult result = _services.retrieve(query);

        for (Asset story : result.getAssets()) {
            System.out.println(story.getOid().getToken());
            System.out.println(story.getAttribute(nameAttribute).getValue());
            System.out.println(story.getAttribute(estimateAttribute).getValue());
            System.out.println();
        }

        return result.getAssets();

        /***** OUTPUT *****
         Story:1073
         Add Order Line
         1

         Story:1068
         Update Member
         2 ...
         ******************/
    }
```

There are two methods you can call on the **OrderBy** class to sort your results: **MinorSort** and **MajorSort**. If you are sorting by only one field, it does not matter which one you use. If you want to sort by multiple fields, you need to call either **MinorSort** or **MajorSort** multiple times. The difference is: Each time you call **MinorSort**, the parameter will be added to the end of the OrderBy statement. Each time you call **MajorSort**, the parameter will be inserted at the beginning of the OrderBy statement.

### How to select a portion of query results

Retrieve a "page" of query results by using the **Paging** propery of the **Query** class. This query will retrieve the first 3 Story assets:

```java
public Asset[] PageListOfAssets() throws Exception {

        IAssetType storyType = _metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        query.getPaging().setPageSize(3);
        query.getPaging().setStart(0);
        QueryResult result = _services.retrieve(query);

        for (Asset story : result.getAssets()) {
            System.out.println(story.getOid().getToken());
            System.out.println(story.getAttribute(nameAttribute).getValue());
            System.out.println(story.getAttribute(estimateAttribute).getValue());
            System.out.println();
        }

        return result.getAssets();

        /***** OUTPUT *****
         Story:1063
         Logon
         2

         Story:1064
         Add Customer Details
         2

         Story:1065
         Add Customer Header
         3
         ******************/
    }
```

The **PageSize** property shown asks for 3 items, and the **Start** property indicates to start at 0. The next 3 items can be retrieve with PageSize=3, Start=3.

###How to query the history of a single asset

This query will retrieve the history of the Member asset with ID 1000.

```java
public Asset[] HistorySingleAsset() throws Exception {

        IAssetType memberType = _metaModel.getAssetType("Member");
        Query query = new Query(memberType, true);
        IAttributeDefinition idAttribute = memberType.getAttributeDefinition("ID");
        IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
        IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
        query.getSelection().add(changeDateAttribute);
        query.getSelection().add(emailAttribute);
        FilterTerm idTerm = new FilterTerm(idAttribute);
        idTerm.equal("Member:20");
        query.setFilter(idTerm);
        QueryResult result = _services.retrieve(query);
        Asset[] memberHistory = result.getAssets();

        for (Asset member : memberHistory) {
            System.out.println(member.getOid().getToken());
            System.out.println(member.getAttribute(changeDateAttribute).getValue());
            System.out.println(member.getAttribute(emailAttribute).getValue());
            System.out.println();
        }

        return memberHistory;

        /***** OUTPUT *****
         Member:1000:105
         4/2/2007 1:22:03 PM
         andre.agile@company.com

         Member:1000:101
         3/29/2007 4:10:29 PM
         andre@company.net
         ******************/
    }
```

To create a history query, provide a boolean "true" second argument to the **Query** class constructor.

### How to query the history of many assets

This query will retrieve history for all Member assets:

```java
 public Asset[] HistoryListOfAssets() throws Exception {

        IAssetType memberType = _metaModel.getAssetType("Member");
        Query query = new Query(memberType, true);
        IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
        IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
        query.getSelection().add(changeDateAttribute);
        query.getSelection().add(emailAttribute);
        QueryResult result = _services.retrieve(query);
        Asset[] memberHistory = result.getAssets();

        for (Asset member : memberHistory) {
            System.out.println(member.getOid().getToken());
            System.out.println(member.getAttribute(changeDateAttribute).getValue());
            System.out.println(member.getAttribute(emailAttribute).getValue());
            System.out.println();
        }

        return memberHistory;

        /***** OUTPUT *****
         Member:20:0
         Thu Nov 30 19:00:00 EST 1899
         null

         Member:20:17183
         Fri Nov 09 09:46:25 EST 2012
         versionone@mailinator.com

         Member:20:17190
         Sun Nov 11 22:59:23 EST 2012
         versionone@mailinator.com

         Member:20:17191
         Sun Nov 11 22:59:47 EST 2012
         versionone@mailinator.com
         ******************/
    }
```

Again, the response is a list of historical assets. There will be multiple Asset objects returned for an asset that has changed previously.

All of the previously demonstrated query properties can be used with historical queries also.

### How to query an asset "as of" a specific time

Use the **AsOf** property of the **Query** class to retrieve data as it existed at some point in time. This query finds the version of each Story asset as it existed seven days ago:

```java
 public Asset[] HistoryAsOfTime() throws Exception {

        IAssetType storyType = _metaModel.getAssetType("Story");
        Query query = new Query(storyType, true);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7);
        query.setAsOf(c.getTime()); // query.AsOf = DateTime.Now.AddDays(-7); //7 days ago
        QueryResult result = _services.retrieve(query);

        for (Asset story : result.getAssets()) {
            System.out.println(story.getOid().getToken());
            System.out.println(story.getAttribute(nameAttribute).getValue());
            System.out.println(story.getAttribute(estimateAttribute).getValue());
            System.out.println();
        }

        return result.getAssets();

        /***** OUTPUT *****
         Story:1063
         Logon
         3

         Story:1064
         Add Customer Details
         1

         Story:1065
         Add Customer Header
         3
         ******************/
    }

```

## Updating Data

Updating assets with the Java SDK involves calling the **Save** method on the **IServices** class.

### How to update a scalar attribute on an asset

Updating a scalar attribute on an asset is accomplished by calling the **SetAttribute** method on an asset, specifying the **IAttributeDefinition** of the attribute you wish to change and the new scalar value. This code will update the Name attribute on the Story with ID 1094:

```java
public Asset UpdateScalarAttribute() throws Exception {
        Oid storyId = Oid.fromToken("Story:1094", _metaModel);
        Query query = new Query(storyId);
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        query.getSelection().add(nameAttribute);
        QueryResult result = _services.retrieve(query);
        Asset story = result.getAssets()[0];
        String oldName = story.getAttribute(nameAttribute).getValue().toString();
        story.setAttributeValue(nameAttribute, GetNewName());
        _services.save(story);

        System.out.println(story.getOid().getToken());
        System.out.println(oldName);
        System.out.println(story.getAttribute(nameAttribute).getValue());
        /***** OUTPUT *****
         Story:1094:1446
         Logon
         New Name
         ******************/

        return story;
    }
```

### How to update a single-value relation on an asset

Updating a single-value relation is accomplished by calling the **SetAttribute** method on an asset, specifying the **IAttributeDefinition** of the attribute you wish to change and the ID for the new relation. This code will change the source of the Story with ID 1094:

```java
public Asset UpdateSingleValueRelation() throws Exception {

        Oid storyId = Oid.fromToken("Story:1094", _metaModel);
        Query query = new Query(storyId);
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
        query.getSelection().add(sourceAttribute);
        QueryResult result = _services.retrieve(query);
        Asset story = result.getAssets()[0];
        String oldSource = story.getAttribute(sourceAttribute).getValue().toString();
        story.setAttributeValue(sourceAttribute, GetNextSourceID(oldSource));
        _services.save(story);

        System.out.println(story.getOid().getToken());
        System.out.println(oldSource);
        System.out.println(story.getAttribute(sourceAttribute).getValue());
        return story;

        /***** OUTPUT *****
         Story:1094:1446
         StorySource:148
         StorySource:149
         ******************/
    }
```

### How to add and remove values from a multi-value relation

Updating a multi-value relation is accomplished by calling either the **RemoveAttributeValue** or **AddAttributeValue** method on an asset, specifying the **IAttributeDefinition** of the attribute you wish to change and the ID of the relation you wish to add or remove. This code will add one Member and remove another Member from the Story with ID 1094:

```java
public Asset UpdateMultiValueRelation() throws Exception {

        Oid storyId = Oid.fromToken("Story:1124", _metaModel);
        Query query = new Query(storyId);
        IAssetType storyType = _metaModel.getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = _services.retrieve(query);
        Asset story = result.getAssets()[0];
        List<Object> oldOwners = new ArrayList<Object>();
        oldOwners.addAll(Arrays.asList(story.getAttribute(ownersAttribute).getValues()));
        story.removeAttributeValue(ownersAttribute, getOwnerToRemove(oldOwners));
        story.addAttributeValue(ownersAttribute, getOwnerToAdd(oldOwners));
        _services.save(story);

        System.out.println(story.getOid().getToken());
        Iterator<Object> iter = oldOwners.iterator();
        while (iter.hasNext()) {
            Oid oid = (Oid) iter.next();
            System.out.println(oid.getToken());
        }
        for (Object o : story.getAttribute(ownersAttribute).getValues()) {
            Oid oid = (Oid) o;
            System.out.println(oid.getToken());
        }

        return story;

        /***** OUTPUT *****
         Story:1094:1446
         Member:1003
         Member:1000
         ******************/
    }

```

## Creating New Assets

When you create a new asset using the Java SDK, you need to specify the "context" of another asset that will be the parent. For example, if you create a new Story asset you can specify which Scope it should be created in.

### How to get a new Story asset template in the context of a Scope asset

This code will create a Story asset in the context of Scope with ID 1012:

```java
public Asset AddNewAsset() throws Exception {

        Oid projectId = Oid.fromToken("Scope:0", _metaModel);
        IAssetType storyType = _metaModel.getAssetType("Story");
        Asset newStory = _services.createNew(storyType, projectId);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        newStory.setAttributeValue(nameAttribute, "My New Story");
        _services.save(newStory);

        System.out.println(newStory.getOid().getToken());
        System.out.println(newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue());
        System.out.println(newStory.getAttribute(nameAttribute).getValue());
        return newStory;

        /***** OUTPUT *****
         Story:1094
         Scope:1012
         My New Story
         ******************/
    }
```

## Executing Operations

An operation is an action that is executed against a single asset. For example, to delete an asset you must execute the Delete operation on the asset. To close or inactivate a Workitem, you must use the Inactivate Operation. Available operations for each asset are listed at the bottom of the the meta data description for that asset, for instance:

```url
https://www.myserver.com/VersionOne/meta.v1/Story?xsl=api.xsl
```

### How to delete a Story asset

Get the Delete operation from the **IMetaModel**, and use **IServices** to execute it against a story Oid.

```java
public Oid DeleteAsset() throws Exception {

        Asset story = AddNewAsset();
        IOperation deleteOperation = _metaModel.getOperation("Story.Delete");
        Oid deletedID = _services.executeOperation(deleteOperation, story.getOid());
        Query query = new Query(deletedID.getMomentless());

        try {
            @SuppressWarnings("unused")
            QueryResult result = _services.retrieve(query);
        } catch (ConnectionException e) {
            if (404 == e.getServerResponseCode())
                System.out.println("Story has been deleted: " + story.getOid().getMomentless());
        }

        return deletedID;

        /***** OUTPUT *****
         Story has been deleted: Story:1049
         ******************/
    }
```

The delete operation returns the Oid, with the new Moment, of the deleted asset. Future current info queries will automatically exclude deleted assets from results.

> Currently, there is no support for undeleting a deleted asset.

### How to close a Story asset

Get the Inactivate operation from the **IMetaModel**, and use **IServices** to execute it against a story Oid.

```java
public Asset CloseAsset() throws Exception {

        Asset story = AddNewAsset();
        IOperation closeOperation = _metaModel.getOperation("Story.Inactivate");
        Oid closeID = _services.executeOperation(closeOperation, story.getOid());

        Query query = new Query(closeID.getMomentless());
        IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
        query.getSelection().add(assetState);
        QueryResult result = _services.retrieve(query);
        Asset closeStory = result.getAssets()[0];
        AssetState state = AssetState.valueOf(((Integer) closeStory.getAttribute(assetState).getValue()).intValue());

        System.out.println(closeStory.getOid());
        System.out.println(state.toString());
        return closeStory;

        /***** OUTPUT *****
         Story:1050
         Closed
         ******************/
    }
```

> The AssetState attribute is the internal state of an asset.

### How to reopen a Story asset

Get the Reactivate operation from the **IMetaModel**, and use **IServices** to execute it against a story Oid.

```java
    public Asset ReOpenAsset() throws Exception {

        Asset story = CloseAsset();
        IOperation activateOperation = _metaModel.getOperation("Story.Reactivate");
        Oid activeID = _services.executeOperation(activateOperation, story.getOid());

        Query query = new Query(activeID.getMomentless());
        IAttributeDefinition assetState = _metaModel.getAttributeDefinition("Story.AssetState");
        query.getSelection().add(assetState);
        QueryResult result = _services.retrieve(query);
        Asset activeStory = result.getAssets()[0];
        @SuppressWarnings("unused")
        AssetState state = AssetState.valueOf(((Integer) activeStory.getAttribute(assetState).getValue()).intValue());

        System.out.println(activeStory.getOid());
        return activeStory;

        /***** OUTPUT *****
         Story:1051
         Active
         ******************/
    }
```

## Getting System Settings

Some system settings are exposed (read-only) to the Java SDK to allow client-side data validation. Specifically, the system settings for Effort Tracking, Story Tracking Level and Defect Tracking Level are available to the Java SDK so that entry of Effort, Detail Estimate, and ToDo can be done consistently with the way VersionOne is configured. Using the **V1Configuration** class, you can get the system's configured state, and apply these settings appropriately in code.

## The VersionOne Information Model

Practically all data in VersionOne is stored in the form of assets, which have attributes. Each asset is classified by an asset type, which describes a number of attribute definitions, operations, rules, and possibly an inheritance from another asset type. A list of all the types within VersionOne can be obtained by accessing the meta data url of your VersionOne instance. Additionally, VersionOne comes with an xsl stylesheet, which can be referenced as a parameter to the meta data url and makes it easier to read the response:

```url
https://www14.v1host.com/v1sdktesting/meta.v1/?xsl=api.xsl
```

Individual types can also be viewed through the meta url:

```url
https://www14.v1host.com/v1sdktesting/meta.v1/Story?xsl=api.xsl
```

You must use the system name for the type you would like to retrieve. This is true whether using the API directly or the Java SDK. For instance, in the example above the system name is "Story", which certain methodology templates display as "Backlog Item" or "Requirement". Here is a list of some of the most important system names and their corresponding default display names in the available methodology templates:

### System Names
<table summary="System Names" cellspacing="0" cellpadding="0" border="0">
<thead>
    <tr>
        <th>System Name</th>
        <th>XP Display Name</th>
        <th>Scrum Display Name</th>
        <th>AgileUP Display Name</th>
        <th>DSDM Display Name</th>
    </tr>
</thead>
<tbody>
    <tr>
        <td>Scope</td>
        <td>Project</td>
        <td>Project</td>
        <td>Project</td>
        <td>Project</td>
    </tr>
    <tr>
        <td>Timebox</td>
        <td>Iteration</td>
        <td>Sprint</td>
        <td>Iteration</td>
        <td>Iteration</td>
    </tr>
    <tr>
        <td>Theme</td>
        <td>Theme</td>
        <td>Feature Group</td>
        <td>Use Case</td>
        <td>Feature Group</td>
    </tr>
    <tr>
        <td>Story</td>
        <td>Story</td>
        <td>Backlog Item</td>
        <td>Requirement</td>
        <td>Requirement</td>
    </tr>
    <tr>
        <td>Defect</td>
        <td>Defect</td>
        <td>Defect</td>
        <td>Defect</td>
        <td>Defect</td>
    </tr>
    <tr>
        <td>Task</td>
        <td>Task</td>
        <td>Task</td>
        <td>Task</td>
        <td>Task</td>
    </tr>
    <tr>
        <td>Test</td>
        <td>Test</td>
        <td>Test</td>
        <td>Test</td>
        <td>Test</td>
    </tr>
    <tr>
        <td>RegressionTest</td>
        <td>RegressionTest</td>
        <td>RegressionTest</td>
        <td>RegressionTest</td>
        <td>RegressionTest</td>
    </tr>
    <tr>
        <td>RegressionPlan</td>
        <td>RegressionPlan</td>
        <td>RegressionPlan</td>
        <td>RegressionPlan</td>
        <td>RegressionPlan</td>
    </tr>
    <tr>
        <td>RegressionSuite</td>
        <td>RegressionSuite</td>
        <td>RegressionSuite</td>
        <td>RegressionSuite</td>
        <td>RegressionSuite</td>
    </tr>
    <tr>
        <td>TestSet</td>
        <td>TestSet</td>
        <td>TestSet</td>
        <td>TestSet</td>
        <td>TestSet</td>
    </tr>
    <tr>
        <td>Environment</td>
        <td>Environment</td>
        <td>Environment</td>
        <td>Environment</td>
        <td>Environment</td>
    </tr>
</tbody>
</table>

### Asset Type

Asset types describe the "classes" of business data available. Asset types form an inheritance hierarchy, such that each asset type inherits attribute definitions, operations, and rules from it's "parent" asset type. Those asset types at the leaves of this hierarchy are concrete, whereas asset types with "children" asset types are abstract. Assets are all instances of concrete asset types. Asset types are identified by unique names.

By way of example, Story and Defect are concrete asset types. On the other hand, Workitem is an abstract asset type, from which Story and Defect ultimately derive.

### Attribute Definition

Attribute definitions describe the properties that "make up" each asset type. An attribute definition defines the type of its value, whether it is required and/or read-only, and many other qualities. Attribute definitions are identified by a name that is unique within its asset type.

Attribute definitions are defined as either scalars or relations to other assets. Further, relation attribute definitions can be either single-value or multi-value. For example, the Estimate attribute definition on the Workitem asset type is a scalar (specifically, a floating-point number). On the other hand, the Workitem asset type's Scope attribute definition is a single-value relation (to a Scope asset). The reverse relation, Workitems on the Scope asset type, is a multi-value relation (to Workitem assets).

### Asset

Actual business objects in VersionOne are assets, which are instances of concrete asset types. Each asset is uniquely identified by it's asset type and ID (an integer). For example, Member:20 identifies the Member asset with ID of 20.

### Attribute

On every asset are a number of attributes, which attach specific values to the attribute definitions defined in the asset type. If the attribute's definition is a relation, then the value(s) of the attribute are references to an asset(s).

### Moment

As data changes in VersionOne, a history is maintained. Every change to every asset is tracked within the system, and assigned a chronologically-increasing integer called a moment. A past version of an asset is uniquely identified by it's asset type, ID, and Moment. A past version of a relation attribute will refer to the past version of it's target asset. For example, Member:20:563 identifies the Member asset with ID of 20, as it was at the time of moment 563.

## Getting Help
To learn more about the VersionOne API, please visit the [VersionOne Developer Community](https://community.versionone.com/Developers).

To ask questions of the VersionOne developer community, please visit [StackOverflow](http://stackoverflow.com/questions/tagged/versionone).

For dedicated assistance, contact [VersionOne Technical Services](http://www.versionone.com/training/technical_services/).