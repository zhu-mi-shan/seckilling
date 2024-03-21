package com.example.rbac_back.Controller;


import com.example.rbac_back.POJO.Order;
import com.example.rbac_back.POJO.Ticket;
import com.example.rbac_back.Service.OrderService;
import com.example.rbac_back.Service.PermissionService;
import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.Utils.RabbitMQ.RabbitMQSender;
import com.example.rbac_back.Utils.Token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AuthToken
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @GetMapping("/userGoodOrders")
    public CommonResult<?> userGoodOrders(@RequestHeader("Authorization") String authHeader, @RequestParam int userid){
        if(permissionService.hasPermission(authHeader,"活动数据"))
            return CommonResult.success(orderService.getAllUserGoodOrders(userid));
        if(!permissionService.hasPermission(authHeader, "查看活动"))
            return CommonResult.error(4001,"无权访问");
        if(!permissionService.hasUserPermission(authHeader,userid))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(orderService.getAllUserGoodOrders(userid));
    }

    @GetMapping("/GoodOrders")
    public CommonResult<?> GoodOrders(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader, "活动数据"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(orderService.getAllGoodOrders());
    }

    @GetMapping("/BadOrders")
    public CommonResult<?> BadOrders(@RequestHeader("Authorization") String authHeader){
        if(!permissionService.hasPermission(authHeader,"活动数据"))
            return CommonResult.error(4001,"无权访问");
        return CommonResult.success(orderService.getAllBadOrders());
    }

    @PostMapping("/deleteOrder")
    public CommonResult<?> deleteOrder(@RequestHeader("Authorization") String authHeader, @RequestParam int order_id){
        System.out.println("deleteOrderid: "+order_id);
        if(!permissionService.hasPermission(authHeader, "活动数据"))
            return CommonResult.error(4001,"无权访问");
        Order order=orderService.getOrderById(order_id);
        if(order.getOrder_status()!=1)
            return CommonResult.error(4001,"订单已删除");
        try {
            rabbitMQSender.sendOrderCancel(order.getTicket_id());
        }catch (Exception e){
            System.out.println(e);
        }
        orderService.setOrderStatus(order_id,-2);
        return CommonResult.success(null);
    }

    @PostMapping("/deleteUserOrder")
    public CommonResult<?> deleteUserOrder(@RequestHeader("Authorization") String authHeader, @RequestParam int order_id){
        System.out.println("deleteUserOrderid: "+order_id);
        Order order=orderService.getOrderById(order_id);
        if(!permissionService.hasPermission(authHeader, "活动数据")){
            if(!permissionService.hasUserPermission(authHeader,order.getUserid())){
                return CommonResult.error(4001,"无权访问");
            }
        }
        if(order.getOrder_status()!=1)
            return CommonResult.error(4001,"订单已删除");
        try {
            rabbitMQSender.sendOrderCancel(order.getTicket_id());
        }catch (Exception e){
            System.out.println(e);
        }
        orderService.setOrderStatus(order_id,-2);
        return CommonResult.success(null);
    }
}
