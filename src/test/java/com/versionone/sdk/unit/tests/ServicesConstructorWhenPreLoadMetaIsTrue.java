package com.versionone.sdk.unit.tests;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IServices;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class ServicesConstructorWhenPreLoadMetaIsTrue {

    private static IServices SUT;
    private static IAssetType AssetTypeType;
    private static IAssetType PrimaryRelationType;

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8282);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @BeforeClass
    public static void setUp() throws Exception {
        WireMock.configureFor("localhost", 8282);
        wireMockRule.stubFor(get(urlEqualTo("/meta.v1/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(MetaSamplePayload.FullSubset)));
        V1Connector connector = V1Connector.withInstanceUrl("http://localhost:8282")
                .withUserAgentHeader("ServicesConstructorTester", "0.0")
                .withUsernameAndPassword("admin", "admin")
                .build();
        SUT = new Services(connector, true);

        AssetTypeType = SUT.getMeta().getAssetType("AssetType");
        PrimaryRelationType = SUT.getMeta().getAssetType("PrimaryRelation");
    }

    @AfterClass
    public static void end() {
        wireMockRule.stop();
    }

    @Test
    public void itShouldCallFullMetaRoute() {
        wireMockRule.verify(1, getRequestedFor(urlEqualTo("/meta.v1/")));
    }

    @Test
    public void itShouldLetMeGetTheAssetTypeType() {
        Assert.assertNotNull(AssetTypeType);
    }

    @Test
    public void itShouldNotAccessTheAssetTypeRoute() {
        wireMockRule.verify(0, getRequestedFor(urlEqualTo("/meta.v1/AssetType")));
    }

    @Test
    public void itShouldLetMeGetThePrimaryRelationType() {
        Assert.assertNotNull(PrimaryRelationType);
    }

    @Test
    public void itShouldNotAccessThePrimaryRelationRoute() {
        wireMockRule.verify(0, getRequestedFor(urlEqualTo("/meta.v1/PrimaryRelation")));
    }
}
