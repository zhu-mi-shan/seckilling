package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.UserMapper;
import com.example.rbac_back.POJO.Order;
import com.example.rbac_back.POJO.User;
import com.example.rbac_back.Utils.LoginRequest;
import com.example.rbac_back.Utils.RabbitMQ.RabbitMQSender;
import com.example.rbac_back.Utils.Token.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private  RolePermissionService rolePermissionService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    public User getUserByName(String username){
        User user = userMapper.getUserByName(username);
        if(user!=null)
            user.setUser_role(userRoleService.getUserRole(user.getUserid()));
        return user;
    }
    public User getUserById(int userid){
        return userMapper.getUserById(userid);
    }

    public List<User> getAllUsers(){
        List<User> users = userMapper.getAllUsers();
        for(User user: users){
            List<String> roleName = userRoleService.getUserRole(user.getUserid());
            user.setUser_role(roleName);
        }
        return users;
    }
    public void addUser(User user){
        //自增id返回到了原对象中
        userMapper.addUser(user);
        int userid = user.getUserid();
        List<String> user_role = user.getUser_role();
        for(String rolename:user_role){
            int role_id = roleService.getRoleIdByName(rolename);
            userRoleService.addUserRole(userid, role_id);
        }
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
        int userid = user.getUserid();
        userRoleService.deleteUserRole(userid);
        for(String role_name:user.getUser_role()){
            int role_id = roleService.getRoleIdByName(role_name);
            userRoleService.addUserRole(userid, role_id);
        }
    }
    public void deleteUser(int userid){
        try {
            List<Order> orders = orderService.getAllUserGoodOrders(userid);
            System.out.println("AllUserGoodOrders: "+orders);
            for (Order order: orders){
                rabbitMQSender.sendOrderCancel(order.getTicket_id());
            }
            orderService.deleteUserOrders(userid);
        }catch (Exception e){
            System.out.println(e);
        }
        userRoleService.deleteUserRole(userid);
        userMapper.deleteUser(userid);
    }

    public List<String> getUserPermissions(int userid){
        List<String> role_names = userRoleService.getUserRole(userid);
        List<String> user_permission = new ArrayList<>();
        for(String role_name: role_names){
            List<String> role_permissions = rolePermissionService.getRolePermissionsByName(role_name);
            user_permission.addAll(role_permissions);
        }
        return new ArrayList<>(
                new HashSet<>(user_permission)//去重
        ) ;
    }

}
