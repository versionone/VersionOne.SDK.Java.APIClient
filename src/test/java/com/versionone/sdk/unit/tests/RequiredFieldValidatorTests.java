package com.versionone.sdk.unit.tests;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Asset;
import com.versionone.apiclient.RequiredFieldValidator;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.MetaException;
import com.versionone.apiclient.exceptions.OidException;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;

public class RequiredFieldValidatorTests extends ServicesTesterBase {

	@Override
    protected String getServicesTestKeys() {
    	return "RequiredFieldValidatorTester";
    }

    private static final String TitleAttributeName = "Name";
    private static final String ParentAttributeName = "Parent";
    private static final String DefectTypeName = "Defect";

    private Services services;
    private RequiredFieldValidator validator;

    @Before
    public void SetUp() {
        services = new Services(getMeta(), getDataConnector());
        validator = new RequiredFieldValidator(getMeta(), services);
    }

    @Test
    public void loadRequiredFieldsTest() throws ConnectionException, APIException, OidException, MetaException {
        Asset asset = new Asset(getAssetType(DefectTypeName));
        IAttributeDefinition defectNameDef = asset.getAssetType().getAttributeDefinition(TitleAttributeName);
        Assert.assertTrue(validator.isRequired(defectNameDef));

        IAttributeDefinition defectParentDef = asset.getAssetType().getAttributeDefinition(ParentAttributeName);
        Assert.assertFalse(validator.isRequired(defectParentDef));
    }

    @Test
    public void ValidateSingleFieldByNameTest() throws ConnectionException, APIException, OidException, MetaException {
        IAssetType defectType = getAssetType(DefectTypeName);

        Assert.assertTrue(validator.isRequired(defectType, TitleAttributeName));
        Assert.assertFalse(validator.isRequired(defectType, ParentAttributeName));
    }

    @Test
    public void ValidateSingleFieldByAttributeTest() throws APIException, ConnectionException, OidException {
        Asset asset = new Asset(getAssetType(DefectTypeName));
        IAttributeDefinition defectNameDef = asset.getAssetType().getAttributeDefinition(TitleAttributeName);
        Assert.assertFalse(validator.validate(asset, defectNameDef));

        asset.loadAttributeValue(defectNameDef, "Valid defect name");
        Assert.assertTrue(validator.validate(asset, defectNameDef));
    }

    @Test
    public void ValidateAssetFailureNotLoadedRequiredAttributeTest() throws APIException, ConnectionException, OidException {
        Asset asset = new Asset(getAssetType(DefectTypeName));
        List<IAttributeDefinition> invalidAttributes = validator.validate(asset);
        Assert.assertEquals(1, invalidAttributes.size());
    }

    @Test
    public void ValidateAssetFailureWrongAttributeValueTest() throws APIException, ConnectionException, OidException {
        Asset asset = getDefectWithLoadedName("");

        List<IAttributeDefinition> invalidAttributes = validator.validate(asset);
        Assert.assertEquals(1, invalidAttributes.size());

        asset = getDefectWithLoadedName(null);

        invalidAttributes = validator.validate(asset);
        Assert.assertEquals(1, invalidAttributes.size());
    }

    @Test
    public void ValidateAssetSuccessTest() throws APIException, ConnectionException, OidException {
        Asset asset = getDefectWithLoadedName("Valid Defect name");

        List<IAttributeDefinition> invalidAttributes = validator.validate(asset);
        Assert.assertEquals(0, invalidAttributes.size());
    }

    @Test
    public void ValidateAssetCollectionFailureTest() throws APIException, ConnectionException, OidException {
        Asset[] assetsToValidate = new Asset[] {
            getDefectWithLoadedName("One"),
            getDefectWithLoadedName(""),
            getDefectWithLoadedName("Two"),
        };

        Map<Asset, List<IAttributeDefinition>> results = validator.validate(assetsToValidate);


        Assert.assertEquals(3, results.size());
        Assert.assertEquals(0, results.get(assetsToValidate[0]).size());
        Assert.assertEquals(1, results.get(assetsToValidate[1]).size());
        Assert.assertEquals(0, results.get(assetsToValidate[2]).size());

        Asset invalidAsset = assetsToValidate[1];
        List<IAttributeDefinition> secondAssetResults = results.get(invalidAsset);
        IAttributeDefinition defectNameDef = invalidAsset.getAssetType().getAttributeDefinition(TitleAttributeName);
        Assert.assertTrue(secondAssetResults.contains(defectNameDef));
    }

    @Test
    public void ValidateAssetCollectionSuccessTest() throws APIException, ConnectionException, OidException {
        Asset[] assetsToValidate = new Asset[] {
            getDefectWithLoadedName("One"),
            getDefectWithLoadedName("Two"),
            getDefectWithLoadedName("Three"),
        };

        Map<Asset, List<IAttributeDefinition>> results = validator.validate(assetsToValidate);

        Assert.assertEquals(3, results.size());
        Assert.assertEquals(0, results.get(assetsToValidate[0]).size());
        Assert.assertEquals(0, results.get(assetsToValidate[1]).size());
        Assert.assertEquals(0, results.get(assetsToValidate[2]).size());
    }

    private Asset getDefectWithLoadedName(String name) throws APIException {
        Asset asset = new Asset(getAssetType(DefectTypeName));
        IAttributeDefinition defectNameDef = asset.getAssetType().getAttributeDefinition(TitleAttributeName);
        asset.loadAttributeValue(defectNameDef, name);

        return asset;
    }

}
