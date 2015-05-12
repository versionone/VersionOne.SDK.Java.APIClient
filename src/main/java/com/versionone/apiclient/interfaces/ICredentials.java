package com.versionone.apiclient.interfaces;

/**
 * @deprecated This interface has been deprecated. Please use V1Connector instead. 
 */
@Deprecated
public interface ICredentials {
    String getV1UserName();
    String getV1Password();
    String getProxyUserName();
    String getProxyPassword();
}
