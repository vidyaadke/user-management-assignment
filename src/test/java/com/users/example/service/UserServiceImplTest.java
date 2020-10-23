package com.users.example.service;

import com.users.example.BaseTest;
import com.users.example.validator.UserServiceValidator;
import com.users.example.gen.model.UsersResponse;
import com.users.example.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    private UserRepository mockUserRepository = mock(UserRepository.class);

    private UserServiceValidator mockUserServiceValidator = mock(UserServiceValidator.class);

    private BaseTest baseTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl();
        userService.setUserServiceValidator(mockUserServiceValidator);
        userService.setUserRepository(mockUserRepository);
        baseTest = new BaseTest();
    }

    @Test
    void getUser() {
        Mockito.when(mockUserRepository.findById(anyLong()))
            .thenReturn(Optional.of(baseTest.buildUser()));

        Mockito.when(mockUserServiceValidator.validateRequest(anyInt()))
            .thenReturn(List.of());

        UsersResponse response = userService.getUser(1);
        assertThat(response, CoreMatchers.notNullValue());
        assertThat(response, hasProperty("data"));
        assertThat(response.getData(),
            allOf(hasProperty("id", equalTo(Long.valueOf(1))),
                hasProperty("title", equalTo("Mrs")),
                hasProperty("firstName", equalTo("Vidya")),
                hasProperty("lastName", equalTo("Jadhav")),
                hasProperty("gender", equalTo("Female")),
                hasProperty("address",
                    allOf(
                        hasProperty("city", Matchers.equalTo("Sydney")),
                        hasProperty("street", Matchers.equalTo("45 Lillian")),
                        hasProperty("postcode", Matchers.equalTo("2145")),
                        hasProperty("state", Matchers.equalTo("NSW"))
                ))));
    }

    @Test
    void updateUser() {
        Mockito.when(mockUserRepository.save(any()))
            .thenReturn(baseTest.buildUser());

        Mockito.when(mockUserRepository.findById(anyLong()))
            .thenReturn(Optional.of(baseTest.buildUser()));

        Mockito.when(mockUserServiceValidator.validateRequest(anyInt()))
            .thenReturn(List.of());

        UsersResponse response = userService.updateUser(1, baseTest.buildUpdateUserRequest());
        assertThat(response, CoreMatchers.notNullValue());
        assertThat(response, hasProperty("data"));
        assertThat(response.getData(),
            allOf(hasProperty("id", equalTo(Long.valueOf(1))),
                hasProperty("title", equalTo("Mrs")),
                hasProperty("firstName", equalTo("Vidya")),
                hasProperty("lastName", equalTo("Jadhav")),
                hasProperty("gender", equalTo("Female")),
                hasProperty("address",
                    allOf(
                        hasProperty("city", Matchers.equalTo("Sydney")),
                        hasProperty("street", Matchers.equalTo("45 Lillian")),
                        hasProperty("postcode", Matchers.equalTo("2145")),
                        hasProperty("state", Matchers.equalTo("NSW"))
                    ))));
    }
}