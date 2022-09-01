package com.gfg.majorProject.UserService.manager;

import com.gfg.majorProject.UserService.Entities.UserResponse;
import com.gfg.majorProject.UserService.Entities.UserServiceRequest;

public interface UserService{
    void create(UserServiceRequest userServiceRequest);
    UserResponse get(String username);
}
