package com.versionone.apiclient.interfaces;

/**
 * @deprecated This interface has been deprecated. Please use V1Connector instead. 
 */
@Deprecated
public interface IAPIConfiguration {
    String getV1Url();
    String getDataUrl();
    String getMetaUrl();
    String getConfigUrl();
    String getV1UserName();
    String getV1Password();
    String getProxyUrl();
    String getProxyUserName();
    String getProxyPassword();
}
