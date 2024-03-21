package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.OrderMapper;
import com.example.rbac_back.POJO.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TicketService ticketService;
    List<Order> getOrders(){
        return orderMapper.getOrders();
    }
    public Order getOrderById(int order_id){return orderMapper.getOrderById(order_id);}
    public void addOrder(Order order){
        orderMapper.addOrder(order);
    }
    public void deleteOrder(int order_id){
        orderMapper.deleteOrder(order_id);
    }
    public void setOrderStatus(int order_id, int order_status){orderMapper.setOrderStatus(order_id, order_status);}
    public List<Order> getAllUserGoodOrders(int userid){return orderMapper.getAllUserGoodOrders(userid);}
    public List<Order> getAllGoodOrders(){return orderMapper.getAllGoodOrders();}
    public List<Order> getAllBadOrders(){return orderMapper.getAllBadOrders();}
    public void deleteTicketOrders(int ticket_id){ orderMapper.deleteTicketOrders(ticket_id); }
    public void deleteUserOrders(int userid){orderMapper.deleteUserOrders(userid);}
}
