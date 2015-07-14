package com.versionone.sdk.unit.tests;


import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Credentials;
import com.versionone.apiclient.interfaces.ICredentials;

public class CredentialsTests {

    private ICredentials _defaultTarget;

    @Before
    public void Setup() throws IOException {
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
