package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;
    public List<String> getUserRole(int userid){
        List<Integer> roleids = userRoleMapper.getUserRoles(userid);
        List<String> roleNames = new LinkedList<>();
        for (int roleid : roleids){
            String roleName = roleService.getRoleNameById(roleid);
            roleNames.add(roleName);
        }
        if (roleNames.isEmpty())
            roleNames.add("暂无");
        return roleNames;
    }

    public void addUserRole(int userid, int role_id){
        userRoleMapper.addUserRole(userid, role_id);
    }
    public void deleteUserRole(int userid){userRoleMapper.deleteUserRole(userid);}
    public void deleteRoleUser(int role_id){userRoleMapper.deleteRoleUser(role_id);}
}
