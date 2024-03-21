import request from '@/utils/request'

export function AllTickets(){
    return request({
        url: '/ticket/getAllTickets',
        method: 'get'
      })
}

export function buyATicket(order){
    console.log(order)
    return request({
        url: '/ticket/buyATicket',
        method: 'post',
        data: order
    })
}

export function getOrderResult(order_id){
    return request({
        url: '/ticket/getOrderResult',
        method: 'get',
        params:{
            order_id: order_id
        }
    })
}

export function addTicket(ticket){
    return request({
        url: '/ticket/addTicket',
        method: 'post',
        data: ticket
    })
}

export function UpdateTicket(ticket){
    return request({
        url: '/ticket/updateTicket',
        method: 'put',
        data: ticket
    })
}

export function DeleteTicket(ticket_id){
    return request({
        url: '/ticket/deleteTicket',
        method: 'delete',
        params:{
            ticket_id: ticket_id
        }
    })
}