package com.example.rbac_back.Controller;

import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.POJO.Permission;
import com.example.rbac_back.Service.PermissionService;
import com.example.rbac_back.Utils.Token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AuthToken
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @GetMapping("/getAllPermissions")
    public CommonResult<?> getAllPermissions(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader, "查看权限"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(permissionService.getAllPermissions());
    }
    @PostMapping("/addPermission")
    public CommonResult<?> addPermission(@RequestBody Permission permission){
        permissionService.addPermission(permission);
        return CommonResult.success(null);
    }
    @DeleteMapping("/deletePermission")
    public CommonResult<?> deletePermission(@RequestParam int per_id){
        permissionService.deletePermission(per_id);
        return  CommonResult.success(null);
    }
}
