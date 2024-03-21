package com.example.rbac_back.Service;

import com.example.rbac_back.Mapper.TicketMapper;
import com.example.rbac_back.POJO.Order;
import com.example.rbac_back.POJO.Ticket;
import com.example.rbac_back.Utils.CommonResult;
import com.example.rbac_back.Utils.RabbitMQ.RabbitMQReceiver;
import com.example.rbac_back.Utils.RabbitMQ.RabbitMQSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Autowired
    private RabbitMQReceiver rabbitMQReceiver;
    //保存商品id以便在redis中查找
    public List<Ticket> getTickets(){
        //获取keys
        Set<String> keys = redisTemplate.opsForSet().members("ticket_ids");
        List<Ticket> res = new ArrayList<>();
        for(String key: keys){
            String ticketObj = redisTemplate.opsForValue().get("ticket:"+key);
            Ticket ticket;
            try{
                //在缓存中读取
                ticket = objectMapper.readValue(ticketObj, Ticket.class);
            }catch (Exception e){
                //数据库读取
                ticket = this.getTicketById(Integer.parseInt(key));
                try{
                    //存入缓存
                    redisTemplate.opsForValue().set("ticket:" + key, objectMapper.writeValueAsString(ticket));
                }catch (Exception e2){
                    System.out.println(e2);
                }
            }
            res.add(ticket);
        }
        return res;
    }
    public void addTicket(Ticket ticket){
        ticketMapper.addTicket(ticket);
        int ticket_id = ticket.getTicket_id();
        redisTemplate.opsForSet().add("ticket_ids", ""+ticket_id);
        //添加到redis中
        try{
            String str = objectMapper.writeValueAsString(ticket);
            redisTemplate.opsForValue().set("ticket:" + ticket_id, str);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void deleteTicket(int ticket_id){
        try{
            redisTemplate.delete("ticket:" + ticket_id);
            orderService.deleteTicketOrders(ticket_id);
        }catch (Exception e){
            System.out.println(e);
        }
        redisTemplate.opsForSet().remove("ticket_ids", ""+ticket_id);
        ticketMapper.deleteTicket(ticket_id);
    }
    public Ticket getTicketById(int ticket_id){
        return ticketMapper.getTicketById(ticket_id);
    }
    public int sellATicket(Order order){
        //创建新的订单
        order.setOrder_status(0);
        orderService.addOrder(order);
        int res = order.getOrder_id();
        //放到消息队列
        try {
            String orderObj = objectMapper.writeValueAsString(order);
            rabbitMQSender.sendOrder(orderObj);
        }catch (Exception e){
            System.out.println(e);
        }
        //返回orderid
        System.out.println("sellATicket: res: " + res);
        return res;
    }
    public int getOrderResult(int order_id){
        Order order = orderService.getOrderById(order_id);
        return order.getOrder_status();
    }

    public void updateATicket(Ticket ticket){
        ticketMapper.updateATicket(ticket);
        int ticket_id = ticket.getTicket_id();
        try{
            String str = objectMapper.writeValueAsString(ticket);
            redisTemplate.opsForValue().set("ticket:" + ticket_id, str);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //测试用，用完注释掉
//    public int cancelATicket(int ticket_id){ return ticketMapper.cancelATicket(ticket_id);}
}
