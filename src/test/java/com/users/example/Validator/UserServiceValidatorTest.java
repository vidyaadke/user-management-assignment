package com.users.example.Validator;

import com.users.example.BaseTest;
import com.users.example.gen.model.ErrorDetail;
import com.users.example.validator.UserServiceValidator;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceValidatorTest {

    @Autowired UserServiceValidator userServiceValidator;
    BaseTest baseTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userServiceValidator = new UserServiceValidator();
        baseTest = new BaseTest();
    }


    @Test
    void test_validateRequest_get() {
        List<ErrorDetail> errors = userServiceValidator.validateRequest(null);
        assertThat(errors, CoreMatchers.notNullValue());
        assertEquals(1, errors.size());
        assertEquals("PARAMETER_NULL", errors.get(0).getErrorCode());
        assertEquals("Path Parameter 'userId' is null.", errors.get(0).getMessage());
        assertEquals("userId", errors.get(0).getParameter());
    }

    @Test
    void test_validateRequest_update() {
       List<ErrorDetail> errors = userServiceValidator.validateRequest(null,null);
        assertThat(errors, CoreMatchers.notNullValue());
        assertEquals(2, errors.size());
        assertEquals("PARAMETER_NULL", errors.get(0).getErrorCode());
        assertEquals("Path Parameter 'userId' is null.", errors.get(0).getMessage());
        assertEquals("userId", errors.get(0).getParameter());
        assertEquals("PARAMETER_NULL", errors.get(1).getErrorCode());
        assertEquals("Request is null or empty.", errors.get(1).getMessage());
        assertEquals("request", errors.get(1).getParameter());
    }

    @Test
    void test_validateRequest_update_invalid_state() {
        List<ErrorDetail> errors = userServiceValidator.validateRequest(1, baseTest.buildInvalidUpdateUserRequest());
        assertThat(errors, CoreMatchers.notNullValue());
        assertEquals(3, errors.size());

        assertEquals("PARAMETER_INVALID_VALUE", errors.get(0).getErrorCode());
        assertEquals("PARAMETER_INVALID_VALUE", errors.get(1).getErrorCode());
        assertEquals("PARAMETER_INVALID_VALUE", errors.get(2).getErrorCode());

        assertEquals("gender", errors.get(0).getParameter());
        assertEquals("title", errors.get(1).getParameter());
        assertEquals("address.state", errors.get(2).getParameter());

        assertEquals("Gender has invalid value, correct value is one of the : Male, Female", errors.get(0).getMessage());
        assertEquals("Title has invalid value, correct value is one of the : Mr, Mrs", errors.get(1).getMessage());
        assertEquals("state has invalid value, correct value is one of the : ACT, NSW, NT, QLD, SA, TAS, VIC, WA", errors.get(2).getMessage());
    }
}