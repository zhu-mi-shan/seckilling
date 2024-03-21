package com.example.rbac_back.POJO;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private int role_id;
    private String role_name;
    private String role_des;
    private List<String> role_permission;
}
