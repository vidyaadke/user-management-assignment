package com.users.example.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.users.example.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class AppIntegrationTest extends WireMockTestInitializer  {
    private static final Logger logger = LoggerFactory.getLogger(AppIntegrationTest.class);

    private static final int PORT = 9999;
    private static Integer ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    BaseTest baseTest = new BaseTest();;

    @Configuration
    public class DatabaseTestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:data.sql")
                .build();
        }
    }

    @BeforeAll
    static void beforeAll() throws Exception {
        wireMockServer = new WireMockServer(PORT);
        wireMockServer.start();
        setupStub();

        logger.info("Start wire mock server at port {}", PORT);
    }

    @AfterAll
    static void afterAll() {
        // Shutdown wire mock server
        logger.info("Shutdown wire mock server and finish test");
        wireMockServer.stop();
    }


    @Test
    void test_app() throws Exception {
        //success tests
        test_get_user();
        test_update_user();

        //auth related tests
        test_update_user_nonadmin();
        test_get_user_unauthorised();
        test_update_user_unauthorised();
        test_get_user_invalid_auth();
        test_update_user_invalid_auth();

        //non existing user tests
        test_update_for_nonexisting_user();
        test_get_for_nonexisting_user();
    }

    //get user using admin user authentication
    private void test_get_user() throws Exception {

        //get user details
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", ID)
            .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight())))
            .andExpect(status().isOk())
            .andReturn();

        String expectedJson = FileUtil.readFileByClasspath("response/get_user_response.json");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, contentAsString, JSONCompareMode.STRICT);

    }

    //get user using admin user authentication
    private void test_get_user_invalid_auth() throws Exception {

        //get user details
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", ID)
            .headers(baseTest.createHeaders(baseTest.getInValidCredentials().getLeft(),baseTest.getInValidCredentials().getRight())))
            .andExpect(status().isUnauthorized())
            .andReturn();
    }


    //get user without any authentication
    private void test_get_user_unauthorised() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", ID)).andExpect(status().isUnauthorized()).andReturn();
    }


    //update user using admin authentication
    private void test_update_user() throws Exception {
        // Update user details
        String requestJson = FileUtil.readFileByClasspath("request/update_user_request.json");

        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", ID)
            .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk()).andReturn();

        String expectedJson = FileUtil.readFileByClasspath("response/update_user_response.json");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, contentAsString, JSONCompareMode.STRICT);

    }

    //update user using invalid authentication
    private void test_update_user_invalid_auth() throws Exception {
        // Update user details
        String requestJson = FileUtil.readFileByClasspath("request/update_user_request.json");

        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", ID)
            .headers(baseTest.createHeaders(baseTest.getInValidCredentials().getLeft(),baseTest.getInValidCredentials().getRight()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isUnauthorized()).andReturn();

    }

    //update user using nonadmin authentication, only admin users are autorised to execute update service.
    private void test_update_user_nonadmin() throws Exception {
        // Update user details
        String requestJson = FileUtil.readFileByClasspath("request/update_user_request.json");

        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", ID)
            .headers(baseTest.createHeaders(baseTest.getUserValidCredentials().getLeft(),baseTest.getUserValidCredentials().getRight()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isForbidden()).andReturn();
    }

    //update user without any authentication
    private void test_update_user_unauthorised() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", ID)).andExpect(status().isUnauthorized()).andReturn();
    }

    //update user for not existing user
    private void test_update_for_nonexisting_user() throws Exception {
        // Update user details
        String requestJson = FileUtil.readFileByClasspath("request/update_user_request.json");

        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", 66666)
            .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().is4xxClientError()).andReturn();

        String expectedJson = FileUtil.readFileByClasspath("response/update_user_notfound_error.json");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, contentAsString, JSONCompareMode.STRICT);

    }

    private void test_get_for_nonexisting_user() throws Exception {
        // Update user details
        String requestJson = FileUtil.readFileByClasspath("request/update_user_request.json");

        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", 66666)
            .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().is4xxClientError()).andReturn();

        String expectedJson = FileUtil.readFileByClasspath("response/get_user_notfound_error.json");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, contentAsString, JSONCompareMode.STRICT);

    }

}
