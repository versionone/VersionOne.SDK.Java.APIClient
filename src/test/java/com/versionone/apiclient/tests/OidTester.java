package com.versionone.apiclient.tests;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.DB;
import com.versionone.Oid;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.OidException;

public class OidTester extends MetaTesterBase {

	@Override
	protected String getMetaTestKeys() {
		return "OidTester";
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void InvalidAssetType()
    {
		new Oid(null, null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void InvalidID()
	{
		new Oid(getStoryAssetType(), new DB.Int(), new DB.Int());
	}
	
	
	@Test public void AssetType()
	{
		Oid o = new Oid(getStoryAssetType(), 5, new DB.Int());
		Assert.assertEquals("Story",o.getAssetType().getToken());
	}
	
	@Test public void Key()
	{
		IAssetType assetType = getStoryAssetType();
		Oid o = new Oid(assetType, 5, new DB.Int());
		Assert.assertEquals(5,o.getKey());
	}		
	
	@Test public void Moment()
	{
		IAssetType assetType = getStoryAssetType();	
		Oid o = new Oid(assetType, 5, 10);
		Assert.assertEquals(10,o.getMoment());
	}
	
	@Test public void NullToken()
	{
		Assert.assertEquals("NULL", Oid.Null.getToken());
	}
	
	@Test public void MomentedToken()
	{
		IAssetType assetType = getStoryAssetType();	
		Oid o = new Oid(assetType, 5, 10);
		Assert.assertTrue(o.hasMoment());
		Assert.assertEquals("Story:5:10",o.getToken());
	}
	
	@Test public void MomentlessOid()
	{
		IAssetType assetType = getStoryAssetType();
		Oid o = new Oid(assetType,5,10);
		Oid m = new Oid(assetType,5);
		Oid oo = o.getMomentless();
		Assert.assertEquals(m,oo);
	}
	
	@Test public void OidNotEqual()
	{
		IAssetType assetType = getStoryAssetType();
		Oid o = new Oid(assetType,5,10);
		Oid m = new Oid(assetType,5,new DB.Int());
		Assert.assertTrue(m != o);
	}
	
	@Test public void OidWithoutMomentIsMomentless()
	{
        Oid o = new Oid(getStoryAssetType(), 5, new DB.Int());
        Assert.assertEquals(o.getMomentless(), o);
        Assert.assertSame(o.getMomentless(), o);
    }

	private IAssetType getStoryAssetType()
    {
        return getMeta().getAssetType("Story");
	}
	
	@Test public void OidNotNull()
	{
		Oid o = new Oid(getStoryAssetType(),5,new DB.Int());
		Assert.assertFalse(o.equals(null));
	}

	@Test public void OidEqualSelf()
	{
		Oid o = new Oid(getStoryAssetType(),5,new DB.Int());
		Assert.assertTrue(o.equals(o));
	}
	
	@Test public void FromTokenIsNull() throws OidException {
        Oid o = Oid.fromToken("NULL", getMeta());
        Assert.assertEquals(Oid.Null, o);
        Assert.assertSame(Oid.Null, o);
        Assert.assertEquals(Oid.Null.hashCode(), o.hashCode());
    }
	
	@Test public void FromToken() throws OidException
    {
		Oid o = Oid.fromToken("Story:5", getMeta());
		Assert.assertEquals("Story:5",o.getToken());
	}
	
	@Test public void FromTokenWithMoment() throws OidException
    {
		Oid o = Oid.fromToken("Story:5:6", getMeta());
		Assert.assertEquals("Story:5:6",o.getToken());
	}
	
	@Test(expected = OidException.class) public void InvalidOidToken() throws OidException
    {
		Oid o = Oid.fromToken("Blah:5:6", getMeta());
		Assert.assertNull(o);
	}		
	
	@Test(expected = OidException.class) public void InvalidOidTokenBadId() throws OidException
    {
        Oid o = Oid.fromToken("Story", getMeta());
        Assert.assertNull(o);
    }
	
	@Test
	public void HashCodeAndEqualsTest() throws OidException
    {
        Oid oid = new Oid(getMeta().getAssetType("Story"), 5, null);
        Oid oid2 = Oid.fromToken("Story:5", getMeta());
        Assert.assertEquals(oid, oid2);
        Assert.assertEquals(oid.hashCode(), oid2.hashCode());
    }

	@Test
	public void HashCodeAndEqualsWithMomentsTest() throws OidException
    {
        Oid oid = new Oid(getMeta().getAssetType("Story"), 5, 555);
        Oid oid2 = Oid.fromToken("Story:5:555", getMeta());
        Oid anotherOid = Oid.fromToken("Story:5:666", getMeta());
        Oid momentlessOid = Oid.fromToken("Story:5", getMeta());
        Assert.assertTrue(oid.equals(oid2));
        Assert.assertEquals(oid.hashCode(), oid2.hashCode());
        Assert.assertFalse(oid.equals(anotherOid));
        Assert.assertFalse(oid.equals(momentlessOid));
        Assert.assertFalse(momentlessOid.equals(anotherOid));
    }

	@Test
	public void NullHashCodeTest()
	{
		Assert.assertEquals(0,Oid.Null.hashCode());
	}

    @Test
    public void NullEqualsTest() throws OidException
    {
        Assert.assertFalse(Oid.Null.equals(Oid.fromToken("Story:5", getMeta())));
        Assert.assertFalse(Oid.fromToken("Story:5", getMeta()).equals(Oid.Null));
    }
}
