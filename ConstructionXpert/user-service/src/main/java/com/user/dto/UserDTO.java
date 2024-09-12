package com.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserDTO {
    private String fullName;
    private String username;
    private String password;
    private String email;
}
