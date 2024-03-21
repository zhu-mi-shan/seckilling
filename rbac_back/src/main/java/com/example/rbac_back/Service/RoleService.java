package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.RoleMapper;
import com.example.rbac_back.POJO.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;
    public List<Role> getAllRoles(){
        List<Role> roles = roleMapper.getAllRoles();
        for(Role role: roles){
            role.setRole_permission(
                    rolePermissionService.getRolePermissionsById(role.getRole_id())
            );
        }
        return roles;
    }
    public String getRoleNameById(int role_id){return roleMapper.getRoleNameById(role_id);}
    public int getRoleIdByName(String role_name){return roleMapper.getRoleIdByName(role_name);}
    public void addRole(Role role){
        roleMapper.addRole(role);
        int role_id = role.getRole_id();
        List<String> role_permissions = role.getRole_permission();
        for(String permission: role_permissions){
            int per_id = permissionService.getPermissionIdByName(permission);
            rolePermissionService.addRolePermission(role_id ,per_id );
        }
    }

    public void deleteRole(int role_id){
        rolePermissionService.deleteRolePermission(role_id);
        userRoleService.deleteRoleUser(role_id);
        roleMapper.deleteRole(role_id);
    }

    public void updateRole(Role role){
        int role_id = role.getRole_id();
        roleMapper.updateRole(role);
        List<String> role_permissions = role.getRole_permission();
        rolePermissionService.deleteRolePermission(role_id);
        for(String permission: role_permissions){
            int per_id = permissionService.getPermissionIdByName(permission);
            rolePermissionService.addRolePermission(role_id, per_id);
        }
    }
}
