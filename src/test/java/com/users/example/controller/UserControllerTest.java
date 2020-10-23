package com.users.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.example.BaseTest;
import com.users.example.exception.ResourceNotFoundException;
import com.users.example.gen.model.ErrorResponse;
import com.users.example.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static int ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    BaseTest baseTest = new BaseTest();

    //get user for not exist userId : ResourceNotFoundException
    @Test
    void test_getUser_notfound() throws Exception {

        Mockito.when(userService.getUser(ID))
            .thenThrow(new ResourceNotFoundException(new ErrorResponse()));

        MvcResult mvcResult = mockMvc.perform(
            get("/users/{userId}", ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError()).andReturn();

    }

    //get user
    @Test
    void test_getUser_success() throws Exception {

        String expectedResponse = "{\n" + "    \"data\": {\n" + "        \"id\": 1,\n" + "        \"title\": \"Mrs\",\n"
            + "        \"firstName\": \"Vidya\",\n" + "        \"lastName\": \"Jadhav\",\n" + "        \"gender\": \"Female\",\n"
            + "        \"address\": {\n" + "            \"street\": \"45 Lillian\",\n" + "            \"city\": \"Sydney\",\n"
            + "            \"state\": \"NSW\",\n" + "            \"postcode\": \"2145\"\n" + "        }\n" + "    }\n" + "}";

        Mockito.when(userService.getUser(ID))
            .thenReturn(baseTest.buildUserResponse());

        MvcResult mvcResult = mockMvc.perform(
            get("/users/{userId}", ID)
                .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andReturn();

        String mockResponse = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedResponse, mockResponse, JSONCompareMode.STRICT);

    }

    //update user
    @Test
    void test_updateUser_success() throws Exception {

        String expectedResponse = "{\n" + "    \"data\": {\n" + "        \"id\": 1,\n" + "        \"title\": \"Mrs\",\n"
            + "        \"firstName\": \"Vidya\",\n" + "        \"lastName\": \"Jadhav\",\n" + "        \"gender\": \"Female\",\n"
            + "        \"address\": {\n" + "            \"street\": \"45 Lillian\",\n" + "            \"city\": \"Sydney\",\n"
            + "            \"state\": \"NSW\",\n" + "            \"postcode\": \"2145\"\n" + "        }\n" + "    }\n" + "}";

        Mockito.when(userService.updateUser(ID, baseTest.buildUpdateUserRequest()))
            .thenReturn(baseTest.buildUserResponse());

        MvcResult mvcResult = mockMvc.perform(
            put("/users/{userId}", ID)
                .content(objectMapper.writeValueAsString(baseTest.buildUpdateUserRequest()))
                .headers(baseTest.createHeaders(baseTest.getAdminValidCredentials().getLeft(),baseTest.getAdminValidCredentials().getRight()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andReturn();

        String mockResponse = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedResponse, mockResponse, JSONCompareMode.STRICT);

    }

    //update user for not exist userId : ResourceNotFoundException
    @Test
    void test_updateUser_notfound() throws Exception {

        Mockito.when(userService.updateUser(ID,baseTest.buildUpdateUserRequest()))
            .thenThrow(new ResourceNotFoundException(new ErrorResponse()));

        MvcResult mvcResult = mockMvc.perform(
            put("/users/{userId}", ID)
                .content(objectMapper.writeValueAsString(baseTest.buildUpdateUserRequest()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().is4xxClientError()).andReturn();
    }

}
