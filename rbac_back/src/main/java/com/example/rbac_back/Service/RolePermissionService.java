package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    public List<String> getRolePermissionsById(int role_id){
        List<Integer> per_ids = rolePermissionMapper.getRolePermissionsById(role_id);
        List<String> per_names = new ArrayList<>();
        for(int per_id: per_ids){
            String per_name = permissionService.getPermissionNameById(per_id);
            per_names.add(per_name);
        }
        return per_names;
    }

    public List<String> getRolePermissionsByName(String role_name){
        int role_id = roleService.getRoleIdByName(role_name);
         return getRolePermissionsById(role_id);
    }
    public void deleteRolePermission(int role_id){
        rolePermissionMapper.deleteRolePermission(role_id);
    }

    public void addRolePermission(int role_id, int per_id){
        rolePermissionMapper.addRolePermission(role_id, per_id);
    }

    public Boolean roleHasPermission(int role_id, int per_id){
        return rolePermissionMapper.roleHasPermission(role_id, per_id);
    }
}
