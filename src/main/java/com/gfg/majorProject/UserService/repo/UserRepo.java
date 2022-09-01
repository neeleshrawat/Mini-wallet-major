package com.gfg.majorProject.UserService.repo;

import com.gfg.majorProject.UserService.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
