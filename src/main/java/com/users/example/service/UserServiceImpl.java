package com.users.example.service;

import com.users.example.validator.UserServiceValidator;
import com.users.example.enums.HttpStatusCodes;
import com.users.example.exception.BadRequestException;
import com.users.example.exception.ResourceNotFoundException;
import com.users.example.gen.model.Address;
import com.users.example.gen.model.ErrorDetail;
import com.users.example.gen.model.UpdateUserRequest;
import com.users.example.gen.model.UsersResponse;
import com.users.example.gen.model.UsersResponseData;
import com.users.example.persistent.User;
import com.users.example.repository.UserRepository;
import com.users.example.util.ErrorResponseBuilder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_MANAGEMENT = "user-management-service";
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired UserRepository userRepository;
    @Autowired UserServiceValidator userServiceValidator;

    @CircuitBreaker(name = USER_MANAGEMENT, fallbackMethod = "getUserFallback")
    @RateLimiter(name = USER_MANAGEMENT)
    @Bulkhead(name = USER_MANAGEMENT)
    @Retry(name = USER_MANAGEMENT)
    @Override
    public UsersResponse getUser(Integer userId) {
        log.debug("Called getUser for userId: " + userId);
        long timeStarted = System.currentTimeMillis();

        //validate request params
        List<ErrorDetail> errors = userServiceValidator.validateRequest(userId);

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder();

        if (!CollectionUtils.isEmpty(errors)) {
            log.error("Validation error in request.");
            //handle error response
            throw new BadRequestException(errorResponseBuilder.buildErrorResponse(errors));
        }

        //fetch user from repository.
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));

        //return error if user not found.
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException(errorResponseBuilder.buildErrorResponse(
                List.of(errorResponseBuilder.buildErrorDetail(HttpStatusCodes.NotFound.name(), "Could not found user with id: " + userId, ""))));
        }
        log.debug("returning user details for userId: " + userId);
        long timeElapsedInSec = (System.currentTimeMillis() - timeStarted) / 1000;
        log.debug("Get user took seconds: " + timeElapsedInSec);

        //return user response.
        return buildUserResponse(optionalUser.get());

    }

    @CircuitBreaker(name = USER_MANAGEMENT, fallbackMethod = "updateUserFallback")
    @RateLimiter(name = USER_MANAGEMENT)
    @Bulkhead(name = USER_MANAGEMENT)
    @Retry(name = USER_MANAGEMENT)
    @Override
    public UsersResponse updateUser(Integer userId, UpdateUserRequest request) {
        log.debug("Called updateUser for userId: " + userId);
        long timeStarted = System.currentTimeMillis();

        //validate request
        List<ErrorDetail> errors = userServiceValidator.validateRequest(userId, request);

        ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder();

        if (!CollectionUtils.isEmpty(errors)) {
            log.error("Validation error in request.");

            //handle error response
            throw new BadRequestException(errorResponseBuilder.buildErrorResponse(errors));
        }

        //find user from repository
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));

        //return error if user not found.
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException(errorResponseBuilder.buildErrorResponse(
                List.of(errorResponseBuilder.buildErrorDetail(HttpStatusCodes.NotFound.name(), "Could not found user with id: " + userId, ""))));
        }

        //update user in repository
        User updatedUser = userRepository.save(updateUserEntity(optionalUser.get(), request));

        log.debug("returning user details for userId: " + userId);
        long timeElapsedInSec = (System.currentTimeMillis() - timeStarted) / 1000;
        log.debug("Update user took seconds: " + timeElapsedInSec);

        //return response
        return buildUserResponse(updatedUser);
    }

    /*
     * Function to update user entity from api request
     */
    private User updateUserEntity(User user, UpdateUserRequest request) {
        if (StringUtils.isNotEmpty(request.getTitle())) {
            user.setTitle(request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNotEmpty(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (StringUtils.isNotEmpty(request.getGender())) {
            user.setGender(request.getGender());
        }

        if (request.getAddress() != null) {
            if (request.getAddress().getCity() != null) {
                user.setCity(request.getAddress().getCity());
            }
            if (request.getAddress().getStreet() != null) {
                user.setStreet(request.getAddress().getStreet());
            }
            if (request.getAddress().getState() != null) {
                user.setState(request.getAddress().getState());
            }
            if (request.getAddress().getPostcode() != null) {
                user.setPostcode(request.getAddress().getPostcode());
            }
        }
        return user;
    }

    /*
     * Function to build user api response
     */
    private UsersResponse buildUserResponse(User user) {

        UsersResponse usersResponse = new UsersResponse();
        UsersResponseData data = new UsersResponseData();

        if (user != null) {
            data.setId(user.getId());
            data.setTitle(user.getTitle());
            data.setFirstName(user.getFirstName());
            data.setLastName(user.getLastName());
            data.setGender(user.getGender());

            Address address = new Address();
            address.setCity(user.getCity());
            address.setState(user.getState());
            address.setPostcode(user.getPostcode());
            address.setStreet(user.getStreet());

            data.setAddress(address);
        }
        usersResponse.setData(data);
        return usersResponse;
    }

    //Fallback method for get user
    public UsersResponse getUserFallback(CallNotPermittedException e) {
        log.debug("inside getUserFallback");
        return new UsersResponse();
    }

    //Fallback method for update user
    public UsersResponse updateUserFallback(CallNotPermittedException e) {
        log.debug("inside updateUserFallback");
        return new UsersResponse();
    }

    public void setUserServiceValidator(UserServiceValidator userServiceValidator) {
        this.userServiceValidator = userServiceValidator;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
