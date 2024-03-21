package com.example.rbac_back.Mapper;

import com.example.rbac_back.POJO.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    List<Order> getOrders();
    Order getOrderById(int order_id);
    void addOrder(Order order);
    void deleteOrder(int order_id);
    void setOrderStatus(int order_id, int order_status);
    List<Order> getAllUserGoodOrders(int userid);
    List<Order> getAllGoodOrders();
    List<Order> getAllBadOrders();
    void deleteTicketOrders(int ticket_id);
    void deleteUserOrders(int userid);
}
