package com.users.example.repository;

import com.users.example.persistent.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface UserRepository extends CrudRepository<User, Long> {
}
