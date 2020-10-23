package com.users.example.service;

import com.users.example.gen.model.UpdateUserRequest;
import com.users.example.gen.model.UsersResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    UsersResponse getUser(Integer userId);

    UsersResponse updateUser(Integer userId, UpdateUserRequest request);

}
