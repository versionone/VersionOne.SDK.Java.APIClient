## Learn By Example: New Asset

When you create a new asset in the APIClient you need to specify the "context" of another asset that will be the parent. For example, if you create a new Story asset you can specify which Scope it should be created in.

### How to get a new Story asset template in the context of a Scope asset

This code will create a Story asset in the context of Scope with ID 1012:

```
public Asset AddNewAsset() throws Exception
{
    Oid projectId = Oid.fromToken("Scope:1012", metaModel);
    IAssetType storyType = metaModel.getAssetType("Story");
    Asset newStory = services.createNew(storyType, projectId);
    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
    newStory.setAttributeValue(nameAttribute, "My New Story");
    services.save(newStory);

    System.out.println(newStory.getOid().getToken());
    System.out.println(newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue());
    System.out.println(newStory.getAttribute(nameAttribute).getValue());
    /***** OUTPUT *****
     Story:1094
     Scope:1012
     My New Story
    ******************/

    return newStory;
}
```
