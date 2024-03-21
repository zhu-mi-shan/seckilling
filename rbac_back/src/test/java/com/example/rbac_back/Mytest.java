package com.example.rbac_back;

import com.example.rbac_back.Mapper.TicketMapper;
import com.example.rbac_back.POJO.Menu;
import com.example.rbac_back.POJO.Ticket;
import com.example.rbac_back.Service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.DateFormatter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class Mytest {
    @Autowired
    private TicketMapper ticketMapper;
    @Test
    public void Mytest(){
        List<Ticket> re = ticketMapper.getTickets();
        System.out.println(re);
    }
    @Test
   public void addTicket(){
//        ticketMapper.addTicket("writing class23",2,12,"2023-12-12 00:00:00","2023-12-16 00:00:00");

        int re = "2023-12-12 12:00:00".compareTo("2023-12-11 00:00:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(LocalDateTime.now()));
   }

}
