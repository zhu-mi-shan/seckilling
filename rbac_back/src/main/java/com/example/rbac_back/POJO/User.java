package com.example.rbac_back.POJO;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private int userid;
    private String username;
    private String user_pwd;
    private String user_realname;
    private String user_sex;
    private String user_phone_number;
    private List<String> user_role;
    private List<String> user_permission;
    private List<Menu> user_menu;
}
