package com.example.rbac_back.POJO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Ticket {
    private int ticket_id;
    private String ticket_name;
    private int ticket_cnt;
    private int ticket_price;
    private String ticket_start_time;
    private String ticket_end_time;
    private String ticket_des;
}
