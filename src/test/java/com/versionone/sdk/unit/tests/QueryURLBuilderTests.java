package com.versionone.sdk.unit.tests;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.AttributeSelection;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.V1APIConnector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.filters.AndFilterTerm;
import com.versionone.apiclient.filters.FilterTerm;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IAttributeDefinition.AttributeType;
import com.versionone.apiclient.interfaces.IMetaModel;
import com.versionone.apiclient.services.OrderBy;
import com.versionone.apiclient.services.QueryFind;
import com.versionone.apiclient.services.QueryURLBuilder;
import com.versionone.apiclient.services.QueryVariable;

public class QueryURLBuilderTests {
	
    private final String versionOneUrl = "http://integsrv01/VersionOne12/";

    @Test
    public void simpleQuery() throws APIException {
        QueryURLBuilder testMe = new QueryURLBuilder(new Query(new MockAssetType()), false);
        Assert.assertEquals("Data/Mock?sel=", testMe.toString());
    }

    @Test
    public void simpleQueryWithAttributes() throws APIException {
        Query query = new Query(new MockAssetType());
        query.getSelection().add(new MockAttributeDefinition("DefaultRole"));
        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.getSelection().add(new MockAttributeDefinition("Nickname"));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.DefaultRole%2CMock.Name%2CMock.Nickname", testMe.toString());
    }

    @Test
    public void find1() {
        Query query = new Query(new MockAssetType());
        query.setFind(new QueryFind("TextToFind"));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=&find=TextToFind", testMe.toString());
    }

    @Test
    public void find2() {
        Query query = new Query(new MockAssetType());
        AttributeSelection findin = new AttributeSelection();
        findin.add(new MockAttributeDefinition("Name"));
        findin.add(new MockAttributeDefinition("Description"));
        query.setFind(new QueryFind("TextToFind", findin));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=&find=TextToFind&findin=Mock.Name%2CMock.Description", testMe.toString());
    }

    @Test
    public void filter() throws APIException {
        Query query = new Query(new MockAssetType());
        FilterTerm term = new FilterTerm(new MockAttributeDefinition("Name"));
        term.equal(new Object[]{"Jerry's Story"});
        query.setFilter(term);

        QueryURLBuilder testMe = new QueryURLBuilder(query,false);
        Assert.assertEquals("Data/Mock?sel=&where=Mock.Name%3D%27Jerry%27%27s+Story%27", testMe.toString());
    }

    @Test
    public void filterWithSpecialSymbol() throws APIException {
        Query query = new Query(new MockAssetType());
        FilterTerm term = new FilterTerm(new MockAttributeDefinition("Name"));
        term.equal(new Object[]{"test & #"});
        query.setFilter(term);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=&where=Mock.Name%3D%27test+%26+%23%27", testMe.toString());
    }

    @Test
    public void name() throws APIException {
        Query query = new Query(new MockAssetType());
        FilterTerm term = new FilterTerm(new MockAttributeDefinition("Name"));
        term.exists();
        query.setFilter(term);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=&where=%2BMock.Name", testMe.toString());
    }

    @Test
    public void multiple() throws APIException {
        Query query = new Query(new MockAssetType());
        query.getSelection().add(new MockAttributeDefinition("DefaultRole"));
        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.getSelection().add(new MockAttributeDefinition("Nickname"));
        FilterTerm term = new FilterTerm(new MockAttributeDefinition("Name"));
        term.operate(FilterTerm.Operator.Equal, new Object[]{"Jerry"});
        query.setFilter(term);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.DefaultRole%2CMock.Name%2CMock.Nickname&where=Mock.Name%3D%27Jerry%27",
                testMe.toString());
    }

    @Test
    public void nameAndEstimate() {
        IAttributeDefinition estimateAttribute = new MockAttributeDefinition("Estimate");
        Query query = new Query(new MockAssetType());

        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.getSelection().add(estimateAttribute);
        query.getOrderBy().minorSort(estimateAttribute, OrderBy.Order.Ascending);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.Name%2CMock.Estimate&sort=Mock.Estimate", testMe.toString());
    }

    @Test
    public void storyByName() throws APIException {
        Oid storyId = new Oid(new MockAssetType("Story"), 1094);
        Query query = new Query(storyId);
        query.getSelection().add(new MockAttributeDefinition("Name"));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Story/1094/Name?", testMe.toString());
    }

    @Test
    public void storyWithMomentByName() throws APIException {
        Oid storyId = new Oid(new MockAssetType("Story"), 1094, 15);
        Query query = new Query(storyId);
        query.getSelection().add(new MockAttributeDefinition("Name"));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Story/1094/15/Name?", testMe.toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void storyFailure() {
        Oid storyId = new Oid(new MockAssetType("Story"), 1094, 15);
        @SuppressWarnings("unused")
        Query query = new Query(storyId, true);
    }

    @Test
    public void task() throws Exception {
        Query query = new Query(new MockAssetType("Task"), new MockAttributeDefinition("Story", AttributeType.Relation));

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Task?sel=Mock.Story", testMe.toString());
    }

    @Test
    public void nameWithPaging() throws APIException {
        Query query = new Query(new MockAssetType());
        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.getPaging().setStart(5);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.Name&page=2147483647%2C5", testMe.toString());

        query.getPaging().setPageSize(10);
        testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.Name&page=10%2C5", testMe.toString());
    }

    @Test
    public void needTotal() throws APIException {
        Query query = new Query(new MockAssetType());
        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.setNeedTotal(true);

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.Name&needTotal=true", testMe.toString());
    }

    @Test
    public void date() throws APIException {
        Calendar testDate = Calendar.getInstance();
        // date we want is 10/1/2007 at 3:00pm
        testDate.set(2007, 9, 1, 3, 00, 00);
        Query query = new Query(new MockAssetType());
        query.setAsOf(testDate.getTime());

        QueryURLBuilder testMe = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=&asof=2007-10-01T03%3A00%3A00", testMe.toString());
    }

    @Test
    public void datePrecision() {
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JUNE, 21, 15, 17, 53);
        cal.set(Calendar.MILLISECOND, 504);
        IAttributeDefinition changeDateAttribute = new MockAttributeDefinition("ChangeDate");

        Query query = new Query(new MockAssetType());
        query.getSelection().add(new MockAttributeDefinition("Name"));
        FilterTerm filter = new FilterTerm(changeDateAttribute);
        filter.greater(cal.getTime());
        QueryURLBuilder builder = new QueryURLBuilder(query, false);
        query.setFilter(filter);

        String queryString = builder.toString();
        Assert.assertEquals("Data/Mock?sel=Mock.Name&where=Mock.ChangeDate%3E%272012-06-21T15%3A17%3A53.504%27", queryString);
    }

    @Test
    public void datePrecisionInVariable() {
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JUNE, 21, 15, 17, 53);
        cal.set(Calendar.MILLISECOND, 504);
        IAttributeDefinition changeDateAttribute = new MockAttributeDefinition("ChangeDate");

        Query query = new Query(new MockAssetType());
        QueryVariable variable = new QueryVariable("requestedDate", cal.getTime());
        query.getSelection().add(new MockAttributeDefinition("Name"));
        query.getVariables().add(variable);
        FilterTerm filter = new FilterTerm(changeDateAttribute);
        filter.operate(FilterTerm.Operator.Equal, variable);
        QueryURLBuilder builder = new QueryURLBuilder(query, false);
        query.setFilter(filter);

        String queryString = builder.toString();
        Assert.assertEquals("Data/Mock?sel=Mock.Name&where=Mock.ChangeDate%3D%24requestedDate&with=%24requestedDate%3D2012-06-21T15%3A17%3A53.504", queryString);
    }

    @Test
    public void queryWithVariables() {
        QueryVariable variable = new QueryVariable("Name", "Name1", "Name2", "Name3");
        IAttributeDefinition nameAttribute = new MockAttributeDefinition("Name");

        Query query = new Query(new MockAssetType());
        query.getVariables().add(variable);
        FilterTerm filter = new FilterTerm(nameAttribute);
        filter.operate(FilterTerm.Operator.Equal, variable);
        query.setFilter(filter);
        query.getSelection().add(new MockAttributeDefinition("Reference"));

        QueryURLBuilder builder = new QueryURLBuilder(query, false);
        Assert.assertEquals("Data/Mock?sel=Mock.Reference&where=Mock.Name%3D%24Name&with=%24Name%3DName1%2CName2%2CName3", builder.toString());
    }

    @Test
    @Ignore("Integration test, requires V1 Server")
    public void QueryTrackedEpicsByProject() throws APIException {
        IAPIConnector metaConnector = new V1APIConnector(versionOneUrl + "meta.v1/", "admin", "admin");
        IMetaModel metaModel = new MetaModel(metaConnector);

        IAssetType epicType = metaModel.getAssetType("Epic");
        IAssetType scopeType = metaModel.getAssetType("Scope");

        Query query = new Query(epicType);
        String scopeId = "Scope:1025";

        IAttributeDefinition notClosedScopeAttribute = scopeType.getAttributeDefinition("AssetState");
        IAttributeDefinition notClosedEpicAttribute = epicType.getAttributeDefinition("AssetState");

        FilterTerm notClosedEpicTerm = new FilterTerm(notClosedEpicAttribute);
        notClosedEpicTerm.notEqual("Closed");

        FilterTerm notClosedScopeTerm = new FilterTerm(notClosedScopeAttribute);
        notClosedScopeTerm.notEqual("Closed");
        IAttributeDefinition scopeAttribute = epicType.getAttributeDefinition("Scope.ParentMeAndUp");
        scopeAttribute = scopeAttribute.filter(notClosedScopeTerm);

        FilterTerm scopeTerm = new FilterTerm(scopeAttribute);
        scopeTerm.equal(scopeId);
        IAttributeDefinition superAndUpAttribute = epicType.getAttributeDefinition("SuperAndUp");
        superAndUpAttribute = superAndUpAttribute.filter(scopeTerm);
        FilterTerm superAndUpTerm = new FilterTerm(superAndUpAttribute);
        superAndUpTerm.notExists();

        AndFilterTerm filter = new AndFilterTerm(scopeTerm, notClosedEpicTerm, superAndUpTerm);
        query.setFilter(filter);

        QueryURLBuilder builder = new QueryURLBuilder(query, false);
        String result = builder.toString();
        Assert.assertEquals("Data/Epic?sel=&where=(Epic.Scope.ParentMeAndUp[AssetState!=%27Closed%27]=%27Scope%3A1025%27;" +
                "Epic.AssetState!=%27Closed%27;-Epic.SuperAndUp[Scope.ParentMeAndUp[AssetState!=%27Closed%27]=%27Scope:1025%27])", result);
    }

    @Test
    @Ignore("Integration test, requires V1 Server")
    public void QueryTrackedEpicsForMultipleProjectsUsingVariables() throws APIException {
        IAPIConnector metaConnector = new V1APIConnector(versionOneUrl + "meta.v1/", "admin", "admin");
        IMetaModel metaModel = new MetaModel(metaConnector);

        QueryVariable scopeVariable = new QueryVariable("Scope", "Scope:2176");

        IAssetType epicType = metaModel.getAssetType("Epic");

        Query query = new Query(epicType);

        IAttributeDefinition notClosedEpicAttribute = epicType.getAttributeDefinition("AssetState");

        FilterTerm notClosedEpicTerm = new FilterTerm(notClosedEpicAttribute);
        notClosedEpicTerm.notEqual("Closed");

        IAttributeDefinition scopeAttribute = epicType.getAttributeDefinition("Scope");
        FilterTerm scopeTerm = new FilterTerm(scopeAttribute);
        scopeTerm.operate(FilterTerm.Operator.Equal, scopeVariable);

        IAttributeDefinition superAndUpAttribute = epicType.getAttributeDefinition("SuperAndUp").filter(scopeTerm);
        FilterTerm superAndUpTerm = new FilterTerm(superAndUpAttribute);
        superAndUpTerm.notExists();

        AndFilterTerm filter = new AndFilterTerm(notClosedEpicTerm, scopeTerm, superAndUpTerm);
        query.setFilter(filter);
        query.getVariables().add(scopeVariable);

        QueryURLBuilder builder = new QueryURLBuilder(query, false);
        String result = builder.toString();
        Assert.assertEquals("Data/Epic?sel=&where=(Epic.AssetState!=%27Closed%27;Epic.Scope=$Scope;-Epic.SuperAndUp[Scope=$Scope])" +
                "&with=$Scope=Scope%3A2176", result);
    }
}
