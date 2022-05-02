package com.versionone.sdk.unit.tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
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
    public static final String UNIQUE_MESSAGE = "--=Any-unique-message-here";

    @Test
    public void testReturn404() throws V1Exception, IOException {
        V1Connector connector = createV1ConnectorStub(new BasicStatusLine(HTTP_11, 404, UNIQUE_MESSAGE),
            UNIQUE_MESSAGE.getBytes(StandardCharsets.UTF_8), "text/plain");
        Reader response = connector.sendData("test-key", "empty-data", "application/octet-stream");
        Assert.assertTrue(IOUtils.toString(response).contains(UNIQUE_MESSAGE));
    }

    @Test
    public void testReturn500() throws V1Exception, IOException {
        V1Connector connector = createV1ConnectorStub(new BasicStatusLine(HTTP_11, 500, UNIQUE_MESSAGE),
            UNIQUE_MESSAGE.getBytes(StandardCharsets.UTF_8), "text/plain");
        Reader response = connector.sendData("test-key", "empty-data", "application/octet-stream");
        Assert.assertTrue(IOUtils.toString(response).contains(UNIQUE_MESSAGE));
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
            .setConnectionManager(CONNECTION_MANAGER_MOCK)
            .setRequestExecutor(requestExecutor);
        return V1Connector.withInstanceUrl("http://localhost:65538/") // deliberately valid, but unusable URL
            .withUserAgentHeader("Test-User-Agent", "1.0")
            .withAccessToken("test-access-token")
            .withHttpClient(mockHttpClient)
            .build();
    }

    private static final HttpClientConnection CONNECTION_MOCK = new HttpClientConnection() {
        @Override
        public boolean isResponseAvailable(int i) {
            return false;
        }

        @Override
        public void sendRequestHeader(HttpRequest httpRequest) {
        }

        @Override
        public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        }

        @Override
        public HttpResponse receiveResponseHeader() {
            return null;
        }

        @Override
        public void receiveResponseEntity(HttpResponse httpResponse) {
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public boolean isStale() {
            return false;
        }

        @Override
        public void setSocketTimeout(int i) {
        }

        @Override
        public int getSocketTimeout() {
            return 0;
        }

        @Override
        public void shutdown() {
        }

        @Override
        public HttpConnectionMetrics getMetrics() {
            return null;
        }
    };

    private static final ConnectionRequest CONNECTION_REQUEST_MOCK = new ConnectionRequest() {
        @Override
        public HttpClientConnection get(long l, TimeUnit timeUnit) {
            return CONNECTION_MOCK;
        }

        @Override
        public boolean cancel() {
            return false;
        }
    };

    private static final HttpClientConnectionManager CONNECTION_MANAGER_MOCK = new HttpClientConnectionManager() {
        @Override
        public ConnectionRequest requestConnection(HttpRoute httpRoute, Object o) {
            return CONNECTION_REQUEST_MOCK;
        }

        @Override
        public void releaseConnection(HttpClientConnection httpClientConnection, Object o, long l, TimeUnit timeUnit) {
        }

        @Override
        public void connect(HttpClientConnection httpClientConnection, HttpRoute httpRoute, int i,
            HttpContext httpContext) {
        }

        @Override
        public void upgrade(HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpContext httpContext) {
        }

        @Override
        public void routeComplete(HttpClientConnection httpClientConnection, HttpRoute httpRoute,
            HttpContext httpContext) {
        }

        @Override
        public void closeIdleConnections(long l, TimeUnit timeUnit) {
        }

        @Override
        public void closeExpiredConnections() {
        }

        @Override
        public void shutdown() {
        }
    };
}
