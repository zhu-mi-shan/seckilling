package com.example.rbac_back.Mapper;

import com.example.rbac_back.POJO.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper {
    List<Menu> getSubMenu(int menu_id);
    Menu getParentMenu(int menu_parent_id);
    Menu getMenuById(int menu_id);
}
