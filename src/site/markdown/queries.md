## Learn By Example: Queries

This section is a series of examples, starting with simpler queries and moving to more advanced queries.	 You'll need to create an instance of both IMetaModel and IServices, as outlined above, to perform the queries.

### How to query a single asset

Retrieve the Member with ID 20:
```
public Asset SingleAsset() throws Exception
{
    Oid memberId = Oid.fromToken("Member:20", metaModel);
    Query query = new Query(memberId);
    QueryResult result = services.retrieve(query);
    Asset member = result.getAssets()[0];

    System.out.println(member.getOid().getToken()); 
    /***** OUTPUT *****
     Member:20
    ******************/

    return member;
}
```
**Remarks**

In this example, the asset will have its Oid populated, but will not have any other attributes populated. This is to minimize the size of the data sets returned. The next example shows how to ask for an asset with specific attributes populated.

### How to query for specific attributes

Retrieve an asset with populated attributes by using the Selection property of the Query object.
```
public Asset SingleAssetWithAttributes() throws Exception
{
    Oid memberId = Oid.fromToken("Member:20", metaModel);
    Query query = new Query(memberId);
    IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Name");
    IAttributeDefinition emailAttribute = metaModel.getAttributeDefinition("Member.Email");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(emailAttribute);
    QueryResult result = services.retrieve(query);
    Asset member = result.getAssets()[0];

    System.out.println(member.getOid().getToken());
    System.out.println(member.getAttribute(nameAttribute).getValue());
    System.out.println(member.getAttribute(emailAttribute).getValue());

    /***** OUTPUT *****
     Member:20
     Administrator
     admin@company.com
    ******************/

    return member;
}
```

### How to get a list of all Story assets

```
public Asset[] ListOfAssets()  throws Exception
{
    IAssetType storyType = metaModel.getAssetType("Story");
    Query query = new Query(storyType);
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(estimateAttribute);
    QueryResult result = services.retrieve(query);

    for(Asset story : result.getAssets())
    {
        System.out.println(story.getOid().getToken());
        System.out.println(story.getAttribute(nameAttribute).getValue());
        System.out.println(story.getAttribute(estimateAttribute).getValue());
        System.out.println();
    }
    /***** OUTPUT *****
     Story:1083
     View Daily Call Count
     5

     Story:1554
     Multi-View Customer Calendar
     1 ...
    ******************/

    return result.getAssets();
}
```
**Remarks**

Depending on your security role, you may not be able to see all the Story assets in the entire system.

### How to filter a query

Use the Filter property of the Query object to filter the results that are returned. This query will retrieve only Story assets with a To Do of zero:
```
public Asset[] FilterListOfAssets()  throws Exception
{
    IAssetType taskType = metaModel.getAssetType("Task");
    Query query = new Query(taskType);
    IAttributeDefinition nameAttribute = taskType.getAttributeDefinition("Name");
    IAttributeDefinition todoAttribute = taskType.getAttributeDefinition("ToDo");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(todoAttribute);
    
    FilterTerm toDoTerm = Query.term(todoAttribute);
    toDoTerm.Equal(0);
    query.setFilter(toDoTerm);
    QueryResult result = services.retrieve(query);

    for(Asset task : result.getAssets())
    {
        System.out.println(task.getOid().getToken());
        System.out.println(task.getAttribute(nameAttribute).getValue());
        System.out.println(task.getAttribute(todoAttribute).getValue());
        System.out.println();
    }
    /***** OUTPUT *****
     Task:1153
     Code Review
     0

     Task:1154
     Design Component
     0 ...
    ******************/

    return result.getAssets();
}
```

### How to sort a query

Use the OrderBy property of the Query object to sort the results. This query will retrieve Story assets sorted by increasing Estimate:
```public Asset[] SortListOfAssets()  throws Exception
{
    IAssetType storyType = metaModel.getAssetType("Story");
    Query query = new Query(storyType);
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(estimateAttribute);
    query.getOrderBy().minorSort(estimateAttribute, OrderBy.Order.Ascending);
    QueryResult result = services.retrieve(query);

    for(Asset story : result.getAssets())
    {
        System.out.println(story.getOid().getToken());
        System.out.println(story.getAttribute(nameAttribute).getValue());
        System.out.println(story.getAttribute(estimateAttribute).getValue());
        System.out.println();
    }
    /***** OUTPUT *****
     Story:1073
     Add Order Line
     1

     Story:1068
     Update Member
     2 ...
    ******************/

    return result.getAssets();
}
```
**Remarks**

There are two methods you can call on the OrderBy class to sort your results: MinorSort and MajorSort. If you are sorting by only one field, it does not matter which one you use. If you want to sort by multiple fields, you need to call either MinorSort or MajorSort multiple times. The difference is: Each time you call MinorSort, the parameter will be added to the end of the OrderBy statement. Each time you call MajorSort, the parameter will be inserted at the beginning of the OrderBy statement.

### How to select a portion of query results

Retrieve a "page" of query results by using the Paging propery of the Query object. This query will retrieve the first 3 Story assets:
```
public Asset[] PageListOfAssets() throws Exception
{
    IAssetType storyType = metaModel.getAssetType("Story");
    Query query = new Query(storyType);
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(estimateAttribute);
    query.getPaging().setPageSize(3);
    query.getPaging().setStart(0);
    QueryResult result = services.retrieve(query);

    for(Asset story : result.getAssets())
    {
        System.out.println(story.getOid().getToken());
        System.out.println(story.getAttribute(nameAttribute).getValue());
        System.out.println(story.getAttribute(estimateAttribute).getValue());
        System.out.println();
    }
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

    return result.getAssets();
}
```
**Remarks**

The PageSize property shown asks for 3 items, and the Start property indicates to start at 0. The next 3 items can be retrieve with PageSize=3, Start=3.

### How to query the history of a single asset

This query will retrieve the history of the Member asset with ID 1000.
```
public Asset[] HistorySingleAsset() throws Exception
{
    IAssetType memberType = metaModel.getAssetType("Member");
    Query query = new Query(memberType, true);
    IAttributeDefinition idAttribute = memberType.getAttributeDefinition("ID");
    IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
    IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
    query.getSelection().add(changeDateAttribute);
    query.getSelection().add(emailAttribute);
    FilterTerm idTerm = Query.term(idAttribute);
    idTerm.Equal("Member:1000");
    query.setFilter(idTerm);
    QueryResult result = services.retrieve(query);
    Asset[] memberHistory = result.getAssets();

    for(Asset member : memberHistory)
    {
        System.out.println(member.getOid().getToken());
        System.out.println(member.getAttribute(changeDateAttribute).getValue());
        System.out.println(member.getAttribute(emailAttribute).getValue());
        System.out.println();
    }
    /***** OUTPUT *****
     Member:1000:105
     4/2/2007 1:22:03 PM
     andre.agile@company.com
     
     Member:1000:101
     3/29/2007 4:10:29 PM
     andre@company.net
    ******************/

    return memberHistory;
}
```
**Remarks**

To create a history query, provide a boolean "true" second argument to the Query constructor.

### How to query the history of many assets

This query will retrieve history for all Member assets:
```
public Asset[] HistoryListOfAssets() throws Exception
{
    IAssetType memberType = metaModel.getAssetType("Member");
    Query query = new Query(memberType, true);
    IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
    IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
    query.getSelection().add(changeDateAttribute);
    query.getSelection().add(emailAttribute);
    QueryResult result = services.retrieve(query);
    Asset[] memberHistory = result.getAssets();

    for(Asset member : memberHistory)
    {
        System.out.println(member.getOid().getToken());
        System.out.println(member.getAttribute(changeDateAttribute).getValue());
        System.out.println(member.getAttribute(emailAttribute).getValue());
        System.out.println();
    }
    /***** OUTPUT *****             
     Member:1010:106
     4/2/2007 3:27:23 PM
     tammy.coder@company.com
      
     Member:1000:105
     4/2/2007 1:22:03 PM
     andre.agile@company.com
     
     Member:1000:101
     3/29/2007 4:10:29 PM
     andre@company.net
    ******************/

    return memberHistory;
}
```
**Remarks**

Again, the response is a list of historical assets. There will be multiple Asset objects returned for an asset that has changed previously.

All of the previously demonstrated query properties can be used with historical queries also.

### How to query an asset "as of" a specific time

Use the AsOf property of the Query object to retrieve data as it existed at some point in time. This query finds the version of each Story asset as it existed seven days ago:
```
public Asset[] HistoryAsOfTime() throws Exception
{
    IAssetType storyType = metaModel.getAssetType("Story");
    Query query = new Query(storyType, true);
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
    query.getSelection().add(nameAttribute);
    query.getSelection().add(estimateAttribute);
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, -7);
    query.setAsOf(c.getTime()); // query.AsOf = DateTime.Now.AddDays(-7); //7 days ago
    QueryResult result = services.retrieve(query);

    for(Asset story : result.getAssets())
    {
        System.out.println(story.getOid().getToken());
        System.out.println(story.getAttribute(nameAttribute).getValue());
        System.out.println(story.getAttribute(estimateAttribute).getValue());
        System.out.println();
    }
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

    return result.getAssets();
}
```