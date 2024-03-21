package com.example.rbac_back.Utils.RabbitMQ;

import com.example.rbac_back.Mapper.TicketMapper;
import com.example.rbac_back.POJO.Order;
import com.example.rbac_back.POJO.Ticket;
import com.example.rbac_back.Service.OrderService;
import com.example.rbac_back.Service.TicketService;
import com.example.rbac_back.Utils.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @RabbitListener(queues = "hello")
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }

    @RabbitListener(queues = "order")
    public void receiveOrder(String orderObj){
        //尝试卖出这张票(乐观锁)
        try {
            Order order = objectMapper.readValue(orderObj, Order.class);
            int ticket_id = order.getTicket_id();
            int order_id = order.getOrder_id();
            boolean flag = true;
            int res = ticketMapper.sellATicket(ticket_id);
            if(res == 1){
                //存入缓存
                Ticket ticket = ticketService.getTicketById(ticket_id);
                redisTemplate.opsForValue().set("ticket:"+ticket_id, objectMapper.writeValueAsString(ticket));
                //完成秒杀订单
                orderService.setOrderStatus(order_id, 1);
            }
            else
                orderService.setOrderStatus(order_id, -1);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @RabbitListener(queues = "orderCancel")
    public void receiveOrderCancel(int ticket_id){
        try{
            int res = ticketMapper.cancelATicket(ticket_id);
            System.out.println("orderCancel: ticket_id: "+ ticket_id);
            if (res == 1){
                Ticket ticket = ticketService.getTicketById(ticket_id);
                redisTemplate.opsForValue().set("ticket:"+ticket_id, objectMapper.writeValueAsString(ticket));
            }else{
                System.out.println("ERROR! FAIL TO CANCEL A TICKET!");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

}