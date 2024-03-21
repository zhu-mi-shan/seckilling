package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.MenuMapper;
import com.example.rbac_back.Mapper.PermissionMapper;
import com.example.rbac_back.POJO.Menu;
import com.example.rbac_back.POJO.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PermissionService permissionService;
    public List<Menu> getMenuByPermission(List<String> permissions){
        List<Menu> resMenu = new ArrayList<>();
        for(String permission: permissions){
            if(permission.equals("访问主页"))
                continue;
            //permission对应的menuid
            int menu_id = permissionService.getPermissionByName(permission).getPer_menu();
            Menu currentMenu = menuMapper.getMenuById(menu_id);
            Menu parentMenu = menuMapper.getParentMenu(currentMenu.getMenu_parent_id());

            boolean flag = false;
            for(Menu menu: resMenu){
                if(menu.getMenu_id()== parentMenu.getMenu_id()){
                    menu.addchildren(currentMenu);
                    flag = true;
                }
            }
            //res中没有父菜单
            if(!flag){
                parentMenu.addchildren(currentMenu);
                resMenu.add(parentMenu);
            }

        }
        return resMenu;
    }
}
