package com.versionone.apiclient.example;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.versionone.Oid;
import com.versionone.apiclient.APIException;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.AssetState;
import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.FilterTerm;
import com.versionone.apiclient.IAPIConnector;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IOperation;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.OrderBy;
import com.versionone.apiclient.ProxyProvider;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;

/**
 * This class contains the examples used : the API documentation
 *
 * @author Jerry D. Odenwelder Jr.
 */
public class DataExamples {
    private final String v1Url = "http://localhost/VersionOne/";
    private final String dataUrl = v1Url + "rest-1.v1/";
    private final String metaUrl = v1Url + "meta.v1/";
    private final String username = "admin";
    private final String password = "admin";
    private final static String proxyAddress = "http://proxy:3128";
    private final static String proxyUserName = "user1";
    private final static String proxyPassword = "user1";
    private IMetaModel metaModel;
    private IServices services;

    public DataExamples() {
        V1APIConnector dataConnector = new V1APIConnector(dataUrl, username, password);
        V1APIConnector metaConnector = new V1APIConnector(metaUrl);
        metaModel = new MetaModel(metaConnector);
        services = new Services(metaModel, dataConnector);
    }

    public void SetUpServicesV1Authentication() {
        V1APIConnector dataConnector = new V1APIConnector("http://server/v1instance/rest-1.v1/", "username", "password");
        V1APIConnector metaConnector = new V1APIConnector("http://server/v1instance/meta.v1/");
        IMetaModel metaModel = new MetaModel(metaConnector);
        @SuppressWarnings("unused")
        IServices services = new Services(metaModel, dataConnector);
    }

    public void SetUpServicesWindowsAuthenticationLoggedInUser() {
        V1APIConnector dataConnector = new V1APIConnector("http://server/v1instance/rest-1.v1/");
        V1APIConnector metaConnector = new V1APIConnector("http://server/v1instance/meta.v1/");
        IMetaModel metaModel = new MetaModel(metaConnector);
        @SuppressWarnings("unused")
        IServices services = new Services(metaModel, dataConnector);
    }

    public Asset SingleAsset() throws Exception {
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

    public Asset SingleAssetWithAttributes() throws Exception {
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

    public Asset[] ListOfAssets() throws Exception {
        IAssetType storyType = metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        QueryResult result = services.retrieve(query);

        for (Asset story : result.getAssets()) {
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

    public Asset[] FilterListOfAssets() throws Exception {
        IAssetType taskType = metaModel.getAssetType("Task");
        Query query = new Query(taskType);
        IAttributeDefinition nameAttribute = taskType.getAttributeDefinition("Name");
        IAttributeDefinition todoAttribute = taskType.getAttributeDefinition("ToDo");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(todoAttribute);

        FilterTerm toDoTerm = new FilterTerm(todoAttribute);
        toDoTerm.equal(0);
        query.setFilter(toDoTerm);
        QueryResult result = services.retrieve(query);

        for (Asset task : result.getAssets()) {
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

    public Asset[] SortListOfAssets() throws Exception {
        IAssetType storyType = metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        query.getOrderBy().minorSort(estimateAttribute, OrderBy.Order.Ascending);
        QueryResult result = services.retrieve(query);

        for (Asset story : result.getAssets()) {
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

    public Asset[] PageListOfAssets() throws Exception {
        IAssetType storyType = metaModel.getAssetType("Story");
        Query query = new Query(storyType);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        IAttributeDefinition estimateAttribute = storyType.getAttributeDefinition("Estimate");
        query.getSelection().add(nameAttribute);
        query.getSelection().add(estimateAttribute);
        query.getPaging().setPageSize(3);
        query.getPaging().setStart(0);
        QueryResult result = services.retrieve(query);

        for (Asset story : result.getAssets()) {
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

    public Asset[] HistorySingleAsset() throws Exception {
        IAssetType memberType = metaModel.getAssetType("Member");
        Query query = new Query(memberType, true);
        IAttributeDefinition idAttribute = memberType.getAttributeDefinition("ID");
        IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
        IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
        query.getSelection().add(changeDateAttribute);
        query.getSelection().add(emailAttribute);
        FilterTerm idTerm = new FilterTerm(idAttribute);
        idTerm.equal("Member:1000");
        query.setFilter(idTerm);
        QueryResult result = services.retrieve(query);
        Asset[] memberHistory = result.getAssets();

        for (Asset member : memberHistory) {
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

    public Asset[] HistoryListOfAssets() throws Exception {
        IAssetType memberType = metaModel.getAssetType("Member");
        Query query = new Query(memberType, true);
        IAttributeDefinition changeDateAttribute = memberType.getAttributeDefinition("ChangeDate");
        IAttributeDefinition emailAttribute = memberType.getAttributeDefinition("Email");
        query.getSelection().add(changeDateAttribute);
        query.getSelection().add(emailAttribute);
        QueryResult result = services.retrieve(query);
        Asset[] memberHistory = result.getAssets();

        for (Asset member : memberHistory) {
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

    public Asset[] HistoryAsOfTime() throws Exception {
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

        for (Asset story : result.getAssets()) {
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

    public Asset UpdateScalarAttribute() throws Exception {
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

    public Asset UpdateSingleValueRelation() throws Exception {
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

    public Asset UpdateMultiValueRelation() throws Exception {
        Oid storyId = Oid.fromToken("Story:1094", metaModel);
        Query query = new Query(storyId);
        IAssetType storyType = metaModel.getAssetType("Story");
        IAttributeDefinition ownersAttribute = storyType.getAttributeDefinition("Owners");
        query.getSelection().add(ownersAttribute);
        QueryResult result = services.retrieve(query);
        Asset story = result.getAssets()[0];
        List<Object> oldOwners = new ArrayList<Object>();
        oldOwners.addAll(Arrays.asList(story.getAttribute(ownersAttribute).getValues()));
        story.removeAttributeValue(ownersAttribute, getOwnerToRemove(oldOwners));
        story.addAttributeValue(ownersAttribute, getOwnerToAdd(oldOwners));
        services.save(story);

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
        /***** OUTPUT *****
         Story:1094:1446
         Member:1003
         Member:1000
         ******************/

        return story;
    }

    public Asset AddNewAsset() throws Exception {
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

    public Oid DeleteAsset() throws Exception {
        Asset story = AddNewAsset();
        IOperation deleteOperation = metaModel.getOperation("Story.Delete");
        Oid deletedID = services.executeOperation(deleteOperation, story.getOid());
        Query query = new Query(deletedID.getMomentless());
        try {
            @SuppressWarnings("unused")
            QueryResult result = services.retrieve(query);
        } catch (ConnectionException e) {
            if (404 == e.getServerResponseCode())
                System.out.println("Story has been deleted: " + story.getOid().getMomentless());
        }

        /***** OUTPUT *****
         Story has been deleted: Story:1049
         ******************/

        return deletedID;
    }

    public Asset CloseAsset() throws Exception {
        Asset story = AddNewAsset();
        IOperation closeOperation = metaModel.getOperation("Story.Inactivate");
        Oid closeID = services.executeOperation(closeOperation, story.getOid());

        Query query = new Query(closeID.getMomentless());
        IAttributeDefinition assetState = metaModel.getAttributeDefinition("Story.AssetState");
        query.getSelection().add(assetState);
        QueryResult result = services.retrieve(query);
        Asset closeStory = result.getAssets()[0];
        AssetState state = AssetState.valueOf(((Integer) closeStory.getAttribute(assetState).getValue()).intValue());

        System.out.println(closeStory.getOid());
        System.out.println(state.toString());
        /***** OUTPUT *****
         Story:1050
         Closed
         ******************/

        return closeStory;
    }

    public Asset ReOpenAsset() throws Exception {
        Asset story = CloseAsset();
        IOperation activateOperation = metaModel.getOperation("Story.Reactivate");
        Oid activeID = services.executeOperation(activateOperation, story.getOid());

        Query query = new Query(activeID.getMomentless());
        IAttributeDefinition assetState = metaModel.getAttributeDefinition("Story.AssetState");
        query.getSelection().add(assetState);
        QueryResult result = services.retrieve(query);
        Asset activeStory = result.getAssets()[0];
        @SuppressWarnings("unused")
        AssetState state = AssetState.valueOf(((Integer) activeStory.getAttribute(assetState).getValue()).intValue());

        System.out.println(activeStory.getOid());
        /***** OUTPUT *****
         Story:1051
         Active
         ******************/

        return activeStory;
    }

    public IServices connectionWithProxy() throws URISyntaxException {
        URI proxy = new URI(proxyAddress);
        ProxyProvider proxyProvider = new ProxyProvider(proxy, proxyUserName, proxyPassword);

        IAPIConnector metaConnectorWithProxy = new V1APIConnector(metaUrl, proxyProvider);
        IMetaModel metaModelWithProxy = new MetaModel(metaConnectorWithProxy);
        IAPIConnector dataConnectorWithProxy = new V1APIConnector(dataUrl, username, password, proxyProvider);
        IServices servicesWithProxy = new Services(metaModelWithProxy, dataConnectorWithProxy);

        return servicesWithProxy;
    }

    public Asset createStoryThroughProxy(String storyName, String projectToken) throws APIException, URISyntaxException, ConnectionException, OidException {
        IServices service = connectionWithProxy();
        IAttributeDefinition nameDef = service.getAttributeDefinition("Story.Name");
        IAttributeDefinition scopeDef = service.getAttributeDefinition("Story.Scope");

        final Asset asset = new Asset(service.getAssetType("Story"));
        asset.setAttributeValue(nameDef, storyName);
        asset.setAttributeValue(scopeDef, service.getOid(projectToken));
        service.save(asset);
        return asset;
    }


    IMetaModel getMetaModel() {
        return metaModel;
    }

    IServices getServices() {
        return services;
    }


    private static String GetNewName() {
        return java.util.UUID.randomUUID().toString(); // Guid.NewGuid().toString();
    }

    private String GetNextSourceID(String oldSource) {
        if (oldSource == "StorySource:148") return "StorySource:149";
        if (oldSource == "StorySource:149") return "StorySource:150";
        return "StorySource:148";
    }

    private String getOwnerToAdd(List<Object> oids) {
        for (Object o : oids.toArray()) {
            Oid oid = (Oid) o;
            if (oid.getToken() == "Member:1003") return "Member:1000";
        }
        return "Member:1003";
    }

    private String getOwnerToRemove(List<Object> oids) {
        for (Object o : oids.toArray()) {
            Oid oid = (Oid) o;
            if (oid.getToken() == "Member:1003") return "Member:1003";
        }
        return "Member:1000";
    }
}
