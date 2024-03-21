<template>
  <div>
    <el-card style="margin-bottom: 20px">
      <div slot="header">
        <span style="font-weight: bold">所有票夹</span>
      </div>
      <div class="order-list">
        <el-table :data="orders" style="width: 100%">
          <el-table-column prop="username" label="购买人"></el-table-column>
          <el-table-column prop="ticket_name" label="购买票名"></el-table-column>
          <el-table-column prop="order_time" label="购买时间"></el-table-column>
          <el-table-column prop="order_price" label="购买价格"></el-table-column>
          <el-table-column prop="order_id" label="操作" >
            <template slot-scope="scope">
              <button @click="delete_Order(scope.row.order_id)">退票</button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <el-card>
      <div slot="header">
        <span style="font-weight: bold">历史信息</span>
      </div>
      <div>
        <el-table :data="Bad_orders" style="width: 100%">
          <el-table-column prop="username" label="购买人"></el-table-column>
          <el-table-column prop="ticket_name" label="购买票名"></el-table-column>
          <el-table-column prop="order_time" label="购买时间"></el-table-column>
          <el-table-column prop="order_price" label="购买价格"></el-table-column>
          <el-table-column prop="order_status" label="订单状态" >
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import {fetchOrders,deleteOrder,fetchBadOrders} from "@/api/orderAdmin.js";

export default {

  data() {
    return {
      orders: [],
      Bad_orders: [],
    };
  },
  mounted() {
    fetchOrders().then((res) => {
      this.orders=res.data
    });
    fetchBadOrders().then((res)=>{
      this.Bad_orders=res.data
      for(var i = 0; i < this.Bad_orders.length;i++){
        if(this.Bad_orders[i].order_status == -1)
          this.Bad_orders[i].order_status="抢票失败"
        else if (this.Bad_orders[i].order_status==-2)
          this.Bad_orders[i].order_status="退票"
        else if(this.Bad_orders[i].order_status==-3)
          this.Bad_orders[i].order_status="该订单活动已删除"
        else if(this.Bad_orders[i].order_status == -4)
          this.Bad_orders[i].order_status ="该订单的用户已删除"
      }
    })

  },
  methods: {
    delete_Order(order_id){
      deleteOrder(order_id).then(res=>{
        console.log(res)
        if(res.code != 200)
          alert(res.message)
        else
          this.$message.success("订单删除成功!")
        fetchOrders().then((res) => {
          this.orders=res.data
        });
        fetchBadOrders().then((res)=>{
          this.Bad_orders=res.data
        });
      })
    }
  }
};
</script>

<style>
.order-list {
  padding: 20px;
}
</style>