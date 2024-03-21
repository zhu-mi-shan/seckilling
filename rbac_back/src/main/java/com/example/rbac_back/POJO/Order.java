package com.example.rbac_back.POJO;

import lombok.Data;

@Data
public class Order {
    int order_id;
    int userid;
    int ticket_id;
    //订单状态{0为未处理，1为抢购成功，-1为抢购失败,-2为退单，-3为活动取消}
    String username;
    String ticket_name;
    String order_time;
    int order_price;
    int order_status;
}
