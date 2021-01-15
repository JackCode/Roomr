package com.jackcode.Roomr.security.repository;

import com.jackcode.Roomr.security.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
