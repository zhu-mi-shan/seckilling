package com.example.rbac_back.Controller;

import com.example.rbac_back.POJO.Order;
import com.example.rbac_back.POJO.Ticket;
import com.example.rbac_back.POJO.User;
import com.example.rbac_back.Service.OrderService;
import com.example.rbac_back.Service.PermissionService;
import com.example.rbac_back.Service.TicketService;
import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.Utils.Token.AuthToken;
import com.example.rbac_back.Utils.Token.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AuthToken
@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final PermissionService permissionService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OrderService orderService;
    @GetMapping("/getAllTickets")
    public CommonResult<?> getAllTickets(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader, "查看活动"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(ticketService.getTickets());
    }
    @PostMapping("/buyATicket")
    public CommonResult<?> buyAticket(@RequestHeader("Authorization") String authHeader ,@RequestBody Order order){
        if(!permissionService.hasPermission(authHeader, "查看活动"))
            return CommonResult.error(4001,"无权访问");
        System.out.println("sellOrder: "+order);
        int order_id = ticketService.sellATicket(order);
        return CommonResult.success(order_id);
    }
    @PostMapping("/addTicket")
    public CommonResult<?> addTicket(@RequestHeader("Authorization") String authHeader ,@RequestBody Ticket ticket){
        if(!permissionService.hasPermission(authHeader, "管理活动"))
            return CommonResult.error(4001,"无权访问");
        ticketService.addTicket(ticket);
        return CommonResult.success(null);
    }
    @GetMapping("/getOrderResult")
    public CommonResult<?> getOrderResult(@RequestHeader("Authorization") String authHeader ,@RequestParam int order_id){
        if(!permissionService.hasPermission(authHeader, "查看活动"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(ticketService.getOrderResult(order_id));
    }
    @PutMapping("/updateTicket")
    public CommonResult<?> updateTicket(@RequestHeader("Authorization") String authHeader, @RequestBody Ticket ticket){
        if(!permissionService.hasPermission(authHeader, "管理活动"))
            return CommonResult.error(4001,"无权访问");
        System.out.println(ticket);
        ticketService.updateATicket(ticket);
        return CommonResult.success(null);
    }
    @DeleteMapping("/deleteTicket")
    public CommonResult<?> deleteTicket(@RequestHeader("Authorization") String authHeader, @RequestParam int ticket_id){
        if(!permissionService.hasPermission(authHeader, "管理活动"))
            return CommonResult.error(4001,"无权访问");
        ticketService.deleteTicket(ticket_id);
        return CommonResult.success(null);
    }

    //测试用，用完注释掉
//    @PostMapping("/cancelTicket")
//    public CommonResult<?> cancelTicket(@RequestBody int ticket_id){
//        return CommonResult.success(ticketService.cancelATicket(ticket_id));
//    }
}
