package com.users.example.e2e;

import ch.qos.logback.core.util.FileUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class WireMockTestInitializer {

    protected static WireMockServer wireMockServer;

    public static void setupStub() throws Exception {
        //todo:add platform and segment mock stubs
    }

}
