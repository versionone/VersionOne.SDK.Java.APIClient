## Learn By Example: Operations

An operation is an action that is executed against a single asset. For example, to delete an asset you must execute the Delete operation on the asset. To close or inactivate a Workitem, you must use the Inactivate Operation. Available operations for each asset are listed at the bottom of the the meta data description for that asset, for instance:
```
http://localhost/VersionOne/meta.v1/Story?xsl=api.xsl
```

### How to delete a Story asset

Get the Delete operation from the IMetaModel, and use IServices to execute it against a story Oid.
```
public Oid DeleteAsset() throws Exception
{
    Asset story = AddNewAsset();
    IOperation deleteOperation = metaModel.getOperation("Story.Delete");
    Oid deletedID = services.executeOperation(deleteOperation, story.getOid());
    Query query = new Query(deletedID.getMomentless());
    try {
		QueryResult result = services.retrieve(query);
    }
    catch(ConnectionException e) {
        if(404 == e.getServerResponseCode())
			System.out.println("Story has been deleted: " + story.getOid().getMomentless());        	
    }

    /***** OUTPUT *****
    Story has been deleted: Story:1049
    ******************/

	return deletedID;

}
```
The delete operation returns the Oid, with the new Moment, of the deleted asset. Future current info queries will automatically exclude deleted assets from results.

Currently, there is no support for undeleting a deleted asset.

### How to close a Story asset

Get the Inactivate operation from the IMetaModel, and use IServices to execute it against a story Oid.
```
public Asset CloseAsset() throws Exception
{ 
    Asset story = AddNewAsset();
    IOperation closeOperation = metaModel.getOperation("Story.Inactivate");
    Oid closeID = services.executeOperation(closeOperation, story.getOid());

    Query query = new Query(closeID.getMomentless());
    IAttributeDefinition assetState = metaModel.getAttributeDefinition("Story.AssetState");
    query.getSelection().add(assetState);
    QueryResult result = services.retrieve(query);
    Asset closeStory = result.getAssets()[0];
    AssetState state = AssetState.valueOf(((Integer)closeStory.getAttribute(assetState).getValue()).intValue());
    
    System.out.println(closeStory.getOid());
    System.out.println(state.toString());
    /***** OUTPUT *****
     Story:1050
     Closed
    ******************/

    return closeStory;
}
```
The AssetState attribute is the internal state of an asset.

### How to reopen a Story asset

Get the Reactivate operation from the IMetaModel, and use IServices to execute it against a story Oid.
```
public Asset ReOpenAsset() throws Exception
{
    Asset story = CloseAsset();
    IOperation activateOperation = metaModel.getOperation("Story.Reactivate");
    Oid activeID = services.executeOperation(activateOperation, story.getOid());

    Query query = new Query(activeID.getMomentless());
    IAttributeDefinition assetState = metaModel.getAttributeDefinition("Story.AssetState");
    query.getSelection().add(assetState);
    QueryResult result = services.retrieve(query);
    Asset activeStory = result.getAssets()[0];

	AssetState state = AssetState.valueOf(((Integer)activeStory.getAttribute(assetState).getValue()).intValue());

    System.out.println(activeStory.getOid());
    /***** OUTPUT *****
     Story:1051
     Active
    ******************/

    return activeStory;
}
```