package com.example.rbac_back.Utils;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
