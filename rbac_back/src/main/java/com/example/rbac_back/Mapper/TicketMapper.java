package com.example.rbac_back.Mapper;

import com.example.rbac_back.POJO.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface TicketMapper {
    void addTicket(Ticket ticket);
    List<Ticket> getTickets();
    Ticket getTicketById(int ticket_id);
    int sellATicket(int ticket_id);
    void deleteTicket(int ticket_id);
    void updateATicket(Ticket ticket);
    int cancelATicket(int ticket_id);
}
