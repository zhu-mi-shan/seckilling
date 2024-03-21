import request from '@/utils/request'
import { Promise } from 'core-js';

export async function fetchUserOrders(userid){
    const response = await request({
        url: '/order/userGoodOrders',
        method: 'get',
        params:{
            userid:userid
        }
    });

    return response
}

export async function fetchOrders(){
    const response = await request({
        url: '/order/GoodOrders',
        method: 'get'
    });
    return response;
}

export async function fetchBadOrders(){
    const response = await request({
        url: '/order/BadOrders',
        method: 'get'
    });
    return response;
}


export async function deleteOrder(order_id){
    console.log(order_id)
    return request({
        url: '/order/deleteOrder',
        method: 'post',
        params:{
            order_id:order_id
        }
    });
}

export async function deleteUserOrder(order_id){
    console.log(order_id)
    return request({
        url: '/order/deleteUserOrder',
        method: 'post',
        params:{
            order_id:order_id
        }
    });
}