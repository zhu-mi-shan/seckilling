package com.example.rbac_back.Controller;

import com.example.rbac_back.Service.PermissionService;
import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.POJO.Role;
import com.example.rbac_back.Service.RoleService;
import com.example.rbac_back.Utils.Token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AuthToken
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @GetMapping("/getAllRoles")
    public CommonResult<?> getAllRoles(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader, "查看角色"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(roleService.getAllRoles());
    }

    @PostMapping("/addRole")
    public CommonResult<?> addRole(@RequestHeader("Authorization") String authHeader, @RequestBody Role role){
        if(!permissionService.hasPermission(authHeader, "编辑角色"))
            return CommonResult.error(4001,"无权访问");
        roleService.addRole(role);
        return CommonResult.success(null);
    }
    @PutMapping("/updateRole")
    public  CommonResult<?> updateRole(@RequestHeader("Authorization") String authHeader, @RequestBody Role role){
        if(!permissionService.hasPermission(authHeader, "编辑角色"))
            return CommonResult.error(4001,"无权访问");
        roleService.updateRole(role);
        return  CommonResult.success(null);
    }
    @DeleteMapping("/deleteRole")
    public CommonResult<?> deleteRole(@RequestHeader("Authorization") String authHeader, int role_id){
        if(!permissionService.hasPermission(authHeader, "编辑角色"))
            return CommonResult.error(4001,"无权访问");
        roleService.deleteRole(role_id);
        return CommonResult.success(null);
    }
}
