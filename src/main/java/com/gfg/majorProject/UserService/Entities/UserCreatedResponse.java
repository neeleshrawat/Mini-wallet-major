package com.gfg.majorProject.UserService.Entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCreatedResponse {
    private String username;
    private String email;
}
