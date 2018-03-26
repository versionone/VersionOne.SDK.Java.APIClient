package com.versionone.sdk.unit.tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IServices;
import org.junit.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ServicesConstructorWhenPreLoadMetaIsFalse {

    private IServices SUT;
    private IAssetType AssetTypeType;
    private IAssetType PrimaryRelationType;
    private WireMockServer server;

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8282);
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @Before
    public void setUp() throws Exception {
        WireMock.configureFor(8282);
        wireMockRule.stubFor(get(urlEqualTo("/meta.v1/AssetType?xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(MetaSamplePayload.AssetTypeType)));
        wireMockRule.stubFor(get(urlEqualTo("/meta.v1/PrimaryRelation?xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(MetaSamplePayload.PrimaryRelationType)));
        V1Connector connector = V1Connector.withInstanceUrl("http://127.0.0.1:8282")
                .withUserAgentHeader("ServicesConstructorTester", "0.0")
                .withUsernameAndPassword("admin", "admin")
                .build();
        SUT = new Services(connector, false);

        AssetTypeType = SUT.getMeta().getAssetType("AssetType");
        PrimaryRelationType = SUT.getMeta().getAssetType("PrimaryRelation");
    }

    @AfterClass
    public static void end() {
        wireMockRule.stop();
    }

    @Test
    public void itShouldNotCallFullMetaRoute() {
        wireMockRule.verify(0, getRequestedFor(urlEqualTo("/meta.v1/")));
    }

    @Test
    public void itShouldLetMeGetTheAssetTypeType() {
        Assert.assertNotNull(AssetTypeType);
    }

    @Test
    public void itShouldAccessTheAssetTypeRoute() {
        wireMockRule.verify(1, getRequestedFor(urlEqualTo("/meta.v1/AssetType?xml")));
    }

    @Test
    public void itShouldLetMeGetThePrimaryRelationType() {
        Assert.assertNotNull(PrimaryRelationType);
    }

    @Test
    public void itShouldAccessThePrimaryRelationRoute() {
        wireMockRule.verify(1, getRequestedFor(urlEqualTo("/meta.v1/PrimaryRelation?xml")));
    }
}
