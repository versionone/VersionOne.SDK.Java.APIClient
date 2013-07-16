## Learn By Example: Updates

Updating assets through the APIClient involves calling the Save method on the IServices object.

### How to update a scalar attribute on an asset

Updating a scalar attribute on an asset is accomplished by calling the SetAttribute method on an asset, specifying the IAttributeDefinition of the attribute you wish to change and the new scalar value. This code will update the Name attribute on the Story with ID 1094:

```
public Asset UpdateScalarAttribute() throws Exception
{
    Oid storyId = Oid.fromToken("Story:1094", metaModel);
    Query query = new Query(storyId);
    IAssetType storyType = metaModel.getAssetType("Story");
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    query.getSelection().add(nameAttribute);
    QueryResult result = services.retrieve(query);
    Asset story = result.getAssets()[0];
    String oldName = story.getAttribute(nameAttribute).getValue().toString();
    story.setAttributeValue(nameAttribute, GetNewName());
    services.save(story);

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

Updating a single-value relation is accomplished by calling the SetAttribute method on an asset, specifying the IAttributeDefinition of the attribute you wish to change and the ID for the new relation. This code will change the source of the Story with ID 1094:

```
public Asset UpdateSingleValueRelation() throws Exception
{
    Oid storyId = Oid.fromToken("Story:1094", metaModel);
    Query query = new Query(storyId);
    IAssetType storyType = metaModel.getAssetType("Story");
    IAttributeDefinition sourceAttribute = storyType.getAttributeDefinition("Source");
    query.getSelection().add(sourceAttribute);
    QueryResult result = services.retrieve(query);
    Asset story = result.getAssets()[0];
    String oldSource = story.getAttribute(sourceAttribute).getValue().toString();
    story.setAttributeValue(sourceAttribute, GetNextSourceID(oldSource));
    services.save(story);

    System.out.println(story.getOid().getToken());
    System.out.println(oldSource);
    System.out.println(story.getAttribute(sourceAttribute).getValue());
    /***** OUTPUT *****
     Story:1094:1446
     StorySource:148
     StorySource:149
    ******************/

    return story;
}
```

### How to add and remove values from a multi-value relation

Updating a multi-value relation is accomplished by calling either the RemoveAttributeValue or AddAttributeValue method on an asset, specifying the IAttributeDefinition of the attribute you wish to change and the ID of the relation you wish to add or remove. This code will add one Member and remove another Member from the Story with ID 1094:

```
public Asset UpdateMultiValueRelation() throws Exception
{
    Oid storyId = Oid.fromToken("Story:1094", metaModel);
    Query query = new Query(storyId);
    IAssetType storyType = metaModel.getAssetType("Story");
    IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
    query.getSelection().add(ownersAttribute);
    QueryResult result = services.retrieve(query);
    Asset story = result.getAssets()[0];
    List&lt;Object&gt; oldOwners = new ArrayList&lt;Object&gt;();
    oldOwners.addAll(Arrays.asList(story.getAttribute(ownersAttribute).getValues()));
    story.removeAttributeValue(ownersAttribute, getOwnerToRemove(oldOwners));
    story.addAttributeValue(ownersAttribute, getOwnerToAdd(oldOwners));
    services.save(story);

    System.out.println(story.getOid().getToken());
    Iterator&lt;Object&gt; iter = oldOwners.iterator();
    while(iter.hasNext())
    {
    	Oid oid = (Oid) iter.next();
        System.out.println(oid.getToken());
    }
    for(Object o : story.getAttribute(ownersAttribute).getValues())
    {
    	Oid oid = (Oid)o;
        System.out.println(oid.getToken());
    }
    /***** OUTPUT *****
     Story:1094:1446
     Member:1003
     Member:1000
    ******************/

    return story;
}
```