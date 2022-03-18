package com.versionone.sdk.unit.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.junit.Assert;
import org.junit.Test;

import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;

public class ManageErrorTests {
    public static final ProtocolVersion HTTP_11 = new ProtocolVersion("HTTP", 1, 1);

    @Test
    public void testReturn404() throws V1Exception, IOException {
        String inputText = "Not Found";
        V1Connector connector = createV1ConnectorStub(new BasicStatusLine(HTTP_11, 404, "Not Found"),
            inputText.getBytes(StandardCharsets.UTF_8), "text/plain");
        Reader response = connector.sendData("test-key", "empty-data", "application/octet-stream");
        Assert.assertTrue(IOUtils.toString(response).contains(inputText));
    }

    @Test
    public void testReturn500() throws V1Exception, IOException {
        String inputText = "Internal Server Error";
        V1Connector connector = createV1ConnectorStub(new BasicStatusLine(HTTP_11, 500, "Internal Server Error"),
            inputText.getBytes(StandardCharsets.UTF_8), "text/plain");
        Reader response = connector.sendData("test-key", "empty-data", "application/octet-stream");
        Assert.assertTrue(IOUtils.toString(response).contains(inputText));
    }

    @SuppressWarnings("SameParameterValue")
    private static V1Connector createV1ConnectorStub(final BasicStatusLine statusLine, final byte[] contentBytes,
        final String contentType) throws V1Exception, MalformedURLException {
        HttpRequestExecutor requestExecutor = new HttpRequestExecutor() {
            @Override
            public HttpResponse execute(HttpRequest request, HttpClientConnection conn, HttpContext context) {
                BasicHttpResponse basicResponse = new BasicHttpResponse(statusLine);
                BasicHttpEntity basicEntity = new BasicHttpEntity();
                basicEntity.setContent(new ByteArrayInputStream(contentBytes));
                basicEntity.setContentType(contentType);
                basicResponse.setEntity(basicEntity);
                return basicResponse;
            }
        };
        HttpClientBuilder mockHttpClient = HttpClientBuilder.create()
            .setRequestExecutor(requestExecutor);
        return V1Connector.withInstanceUrl("http://localhost:80")
            .withUserAgentHeader("Test-User-Agent", "1.0")
            .withAccessToken("test-access-token")
            .withHttpClient(mockHttpClient)
            .build();
    }
}
