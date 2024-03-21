package com.example.rbac_back.POJO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    int menu_id;
    int menu_parent_id;
    String path;
    String name;
    String component;
    String icon;
    List<Menu> children = new ArrayList<>();
    public void addchildren(Menu menu){
        this.children.add(menu);
    }
}
