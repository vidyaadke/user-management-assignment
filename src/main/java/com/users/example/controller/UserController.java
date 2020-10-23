package com.users.example.controller;

import com.users.example.gen.model.UpdateUserRequest;
import com.users.example.gen.model.UsersResponse;
import com.users.example.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController public class UserController {

    @Autowired UserService userService;

    /**
     * GET /users/{userId} : Users Management
     * This service allows us to get users data.
     *
     * @param userId An unique user identifier. (required)
     * @return Successful (status code 200)
     * or Bad Request (status code 400)
     * or Notfound Request (status code 404)
     * or Server Error (status code 500)
     */
    @RequestMapping(value = "/users/{userId}", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<UsersResponse> getUser(@ApiParam(value = "An unique user identifier.", required = true) @PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    /**
     * PUT /users/{userId} : Update a user
     * Updates user’s details like first and last name etc.
     *
     * @param userId  A unique identifier of a user. (required)
     * @param request (required)
     * @return Successful (status code 200)
     * or Bad Request (status code 400)
     * or Notfound Request (status code 404)
     * or Server Error (status code 500)
     */
    @RequestMapping(value = "/users/{userId}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
    ResponseEntity<UsersResponse> updateUser(@ApiParam(value = "A unique identifier of a user.", required = true) @PathVariable("userId") Integer userId,
        @ApiParam(value = "", required = true) @Valid @RequestBody UpdateUserRequest request) {
        return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
    }

}
