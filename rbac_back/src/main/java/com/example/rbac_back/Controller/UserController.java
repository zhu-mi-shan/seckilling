package com.example.rbac_back.Controller;

import com.example.rbac_back.Mapper.MenuMapper;
import com.example.rbac_back.POJO.Menu;
import com.example.rbac_back.Service.MenuService;
import com.example.rbac_back.Service.PermissionService;
import com.example.rbac_back.Utils.Token.AuthToken;
import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.POJO.User;
import com.example.rbac_back.Service.UserService;
import com.example.rbac_back.Utils.LoginRequest;
import com.example.rbac_back.Utils.Token.JwtTokenUtil;
import com.example.rbac_back.Utils.Token.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AuthToken
@RestController
@RequestMapping("/user")
//构造未初始化fianl对象，不用使用@Autowired
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final MenuService menuService;
    @Resource
    private Environment environment;
    @PostMapping("/register")
    public CommonResult<?> UserRegister(@RequestBody User user){
        if (userService.getUserByName(user.getUsername())!=null){
            return CommonResult.error(50008, "用户名已存在");
        }
        user.setUser_role(Arrays.asList("普通用户"));
        userService.addUser(user);
        return CommonResult.success(null);
    }
    @PostMapping ("/login")
    public CommonResult<?> Userlogin(@RequestBody LoginRequest loginUser){
        User user = userService.getUserByName(loginUser.getUsername());
        if (user == null){
            return CommonResult.error(50007, "账号密码不正确");
        }
        if (!loginUser.getPassword().equals(user.getUser_pwd())){
            return CommonResult.error(50007, "账号密码不正确");
        }
        String username = loginUser.getUsername();

        String accessToken = jwtTokenUtil.generateAccessToken(username);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        Long accessTokenTTL = Long.valueOf(environment.getProperty("jwt.access_token.expiration"));
        Long refreshTokenTTL = Long.valueOf(environment.getProperty("jwt.refresh_token.expiration"));
        redisTemplate.opsForValue().set(accessToken, "accessToken", accessTokenTTL, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(refreshToken, "refreshToken", refreshTokenTTL);

        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);
        return CommonResult.success(tokenResponse);
    }

    @GetMapping("/getInfo")
    public CommonResult<?> getUserInfo(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getUserByName(username);
        List<String> user_permission = userService.getUserPermissions(user.getUserid());
        user.setUser_permission(user_permission);
        List<Menu> menus = menuService.getMenuByPermission(user_permission);
        user.setUser_menu(menus);
        return CommonResult.success(user);
    }

    @GetMapping("/Allusers")
    public CommonResult<?> getAllUsers(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader, "查看用户"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(userService.getAllUsers());
    }

    @PostMapping("/addUser")
    public CommonResult<?> addUser(@RequestHeader("Authorization") String authHeader, @RequestBody User user){
        if(!permissionService.hasPermission(authHeader, "编辑用户"))
            return CommonResult.error(4001,"无权访问");
        userService.addUser(user);
        return CommonResult.success(null);
    }
    @PutMapping("/updateUser")
    public CommonResult<?> updateUser(@RequestHeader("Authorization") String authHeader, @RequestBody User user){
        if(!permissionService.hasPermission(authHeader, "编辑用户"))
            return CommonResult.error(4001,"无权访问");
        userService.updateUser(user);
        return CommonResult.success(null);
    }
    @DeleteMapping("/deleteUser")
    public CommonResult<?> deleteUser(@RequestHeader("Authorization") String authHeader, @RequestParam int userid){
        if(!permissionService.hasPermission(authHeader, "编辑用户"))
            return CommonResult.error(4001,"无权访问");
        userService.deleteUser(userid);
        return CommonResult.success(null);
    }

    @PostMapping("/refreshToken")
    public CommonResult<?> refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        if (redisTemplate.opsForValue().get(refreshToken)!=null){
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            String access_token = jwtTokenUtil.generateAccessToken(username);
            redisTemplate.opsForValue().set(access_token, "accessToken",
                   Long.valueOf(environment.getProperty("jwt.access_token.expiration")), TimeUnit.SECONDS);
            return  CommonResult.success(access_token);
        }
        else
            return CommonResult.error(401, "refreshToken已失效");
    }
}
