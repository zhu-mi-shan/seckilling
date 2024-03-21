package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.PermissionMapper;
import com.example.rbac_back.POJO.Permission;
import com.example.rbac_back.Utils.Token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    public List<Permission> getAllPermissions(){
        return permissionMapper.getAllPermissions();
    }
    public String getPermissionNameById(int per_id){
        return permissionMapper.getPermissionNameById(per_id);
    }
    public int getPermissionIdByName(String per_name){return permissionMapper.getPermissionIdByName(per_name);}
    public Permission getPermissionByName(String per_name){return permissionMapper.getPermissionByName(per_name);}
    public void addPermission(Permission permission){
        permissionMapper.addPermission(permission);
    }
    public void deletePermission(int per_id){
        permissionMapper.deletePermission(per_id);
    }
    public Boolean hasPermission(String authHeader, String per_name){
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        int userid = userService.getUserByName(username).getUserid();
        List<String> role_names = userRoleService.getUserRole(userid);
        int per_id = getPermissionIdByName(per_name);
        for(String role_name:role_names){
            int role_id = roleService.getRoleIdByName(role_name);
            if(rolePermissionService.roleHasPermission(role_id, per_id)){
                return true;
            }
        }
        return false;
    }

    public Boolean hasUserPermission(String authHeader, int userid){
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        int auth_userid = userService.getUserByName(username).getUserid();
        if(auth_userid==userid)
            return true;
        else
            return false;
    }
}
