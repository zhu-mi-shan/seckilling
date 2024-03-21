package com.example.rbac_back.POJO;

import lombok.Data;

@Data
public class Permission {
    private int per_id;
    private String per_name;
    private String per_des;
    private int per_menu;
}
