package com.versionone.apiclient.tests;


import com.versionone.apiclient.Credentials;
import com.versionone.apiclient.ICredentials;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class CredentialsTester {

    private ICredentials _defaultTarget;

    @Before
    public void Setup(){
        _defaultTarget = new Credentials();
    }

    @Test
    public void GetV1UserNameTest(){
        Assert.assertEquals("admin", _defaultTarget.getV1UserName());
    }

    @Test
    public void GetV1PasswordTest(){
        Assert.assertEquals("admin", _defaultTarget.getV1Password());
    }

}
