<template>
<div>
    <el-button @click="Open_ticket_drawer()" type="primary" style="margin-left: 16px;">
        我的票夹
    </el-button>

    <el-drawer
        title="我的票夹"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
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
    </el-drawer>

    <div class="wrapper">
    
    <el-card v-for="(item, index) in tickets" :key="index" :body-style="{ padding: '0px' }" class="ticket">
        <img src="@/assets/pic1.jpg" width="250px" height="100px">
        <div class="upper">
            <div class="mainInfo">
                <div class="title">
                    {{ item.ticket_name }}
                </div>
                <div class="description">
                    介绍: {{ item.ticket_des }}
                </div>
                
            </div>
        </div>
        <el-divider class="divider"></el-divider>
        <div class="tag">
            <el-tag size="mini" effect="plain">购票信息</el-tag>
        </div>
        <div class="below">
            <div>开始时间: {{ item.ticket_start_time }}</div>
            <div>结束时间: {{ item.ticket_end_time }}</div>
            <div class="option">
                <div>
                    票价: {{ item.ticket_price }}
                    余票数: {{ item.ticket_cnt }}
                </div>
                <div style="padding-right: 40px">
                    <el-button @click="order(item)" size="mini" type="success" plain>抢票</el-button>
                </div>
            </div>
            <div class="below" v-if = "editPermission" label="操作">
                    <el-button size="mini" @click="editTicket(index,item)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="deleteTicket(index, item)">删除</el-button>
            </div>
        </div>

    </el-card>

    <el-dialog title="编辑活动" :visible.sync="dialogVisible" width="50%">
        <span>活动信息</span>
        <el-form ref="form" :model="dialogTicket" label-width="100px">
            <el-form-item label="活动名称" prop="ticket_name">
                <el-input v-model="dialogTicket.ticket_name"></el-input>
            </el-form-item>
            <el-form-item label="票数量" prop="ticket_cnt">
                <el-input v-model="dialogTicket.ticket_cnt"></el-input>
            </el-form-item>
            <el-form-item label="购票价格" prop="ticket_price">
                <el-input v-model="dialogTicket.ticket_price"></el-input>
            </el-form-item>
            <el-form-item label="售票时间" required>
                <el-col :span="11">
                    <el-form-item prop="ticket_start_time">
                        <el-date-picker
                        style="width: 100%;"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        v-model="dialogTicket.ticket_start_time"
                        type="datetime"
                        placeholder="开始时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col class="line" :span="2">——</el-col>
                <el-col :span="11">
                    <el-form-item prop="ticket_end_time">
                        <el-date-picker
                        style="width: 100%;"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        v-model="dialogTicket.ticket_end_time"
                        type="datetime"
                        placeholder="结束时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-form-item>
            <el-form-item label="票数量" prop="ticket_des">
                <el-input v-model="dialogTicket.ticket_des"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateTicket()">确 定</el-button>
        </span>
    </el-dialog>

    <el-dialog title="滑动验证" :visible.sync="sliderVisible" width="50%">
        <span>请在3秒内按住滑块拖动到最右边完成验证：</span>
        <el-slider
        v-model="sliderValue"
        :disabled="sliderDisabled"
        :show-input="false"
        :min="0"
        :max="100"
        :step="1"
        @drag-end="handleSliderDragEnd">
        </el-slider>
    </el-dialog>

  </div>
</div>
</template>

<script>
import { AllTickets, buyATicket, getOrderResult, UpdateTicket, DeleteTicket } from '@/api/TicketAdmin';
import { getUserPermission } from '@/api/PermissionAdmin';
import {fetchUserOrders,deleteUserOrder} from "@/api/orderAdmin.js";

export default {
    mounted(){
        AllTickets().then(res =>{
            this.tickets = res.data
        })
        // console.log(getUserPermission())
        if(getUserPermission().includes("管理活动")){
            // console.log("yes")
            this.editPermission = true
        }
    },
    data(){
        return{
            drawer: false,
            direction: 'ttb',

            orders: [],

            tickets: [],
            dialogTicket: [],
            dialogVisible: false,
            editPermission: false,

            sliderDisabled: true,
            sliderValue: 0, // 滑块的当前值
            sliderVisible: false, // 滑块是否禁用
            expectedValue: 100, // 预期的滑块值，可以根据需要进行调整
            start_dragging: false
        }
    },
    methods:{
        Open_ticket_drawer(){
            const userid=JSON.parse(localStorage.getItem("userInfo")).userid
            fetchUserOrders(userid).then((res) => {
                this.orders=res.data
            });
            this.drawer=true
        },
        delete_Order(order_id){
            deleteUserOrder(order_id).then(res=>{
                console.log(res)
                if(res.code != 200)
                alert(res.message)
                else
                this.$message.success("订单删除成功!")
                const userid=JSON.parse(localStorage.getItem("userInfo")).userid
                fetchUserOrders(userid).then((res) => {
                this.orders=res.data
                });
            })
        },
        handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
        },
        handleSliderDragEnd() {
            return new Promise((resolve,reject) =>{
                this.sliderValue=0
                this.sliderVisible=true
                this.sliderDisabled=false
                setTimeout(() => {
                    // 延迟执行的代码
                     if (this.sliderValue == this.expectedValue) {
                        // 滑块验证成功
                        this.$message.success("验证通过!")
                        this.sliderDisabled=true // 禁用滑块，防止多次验证
                        this.sliderVisible = false 
                        this.start_dragging=false
                        resolve(true)
                    }else{
                        // 滑块验证失败
                        this.$message.error("超时，验证失败!")
                        this.sliderDisabled=true // 禁用滑块，防止多次验证
                        this.sliderVisible = false 
                        this.start_dragging=false
                        resolve(false)
                    }
                }, 3000); // 延迟时间为 2000 毫秒（2秒）
            })
        },
        validateTime(ticket) {
            return new Promise((resolve, reject) => {
                // 获取当前时间
                var currentTime = new Date()
                // 设定活动开始时间
                var activityStartTime = new Date(ticket.ticket_start_time)
                var activityEndTime = new Date(ticket.ticket_end_time)
                // 进行时间验证
                if (currentTime >= activityStartTime && currentTime <= activityEndTime) {
                    // 时间验证通过，进行相应操作
                    // alert("现在是抢票时间！")
                    resolve(true)
                } else {
                    // 时间验证未通过，进行相应操作
                    this.$message.error("不在抢票时间范围！")
                    resolve(false)
                }
            })
        },
        getCurTime(){
            const now = new Date();
            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const day = String(now.getDate()).padStart(2, '0');
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');

            const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

            console.log(formattedDate);
            return formattedDate
        },
        order(ticket){
            let order = {
                userid: JSON.parse(localStorage.getItem("userInfo")).userid,
                ticket_id: ticket.ticket_id, 
                username: JSON.parse(localStorage.getItem("userInfo")).username,
                ticket_name: ticket.ticket_name,
                order_time: this.getCurTime(),
                order_price: ticket.ticket_price,
                order_status: 0
            }
            this.handleSliderDragEnd().then(res=>{
                if(res){
                    this.validateTime(ticket).then(res=>{
                        console.log(res)
                        
                        if(res){
                            buyATicket(order).then(res=>{
                                console.log(order)
                                let order_id = res.data
                                const loading = this.$loading({
                                    lock: true,
                                    text: '抢票中，请稍候...',
                                    spinner: 'el-icon-loading',
                                    background: 'rgba(0, 0, 0, 0.7)'
                                });
                                let status = 0
                                this.getResult(order_id).then(res=>{
                                    if(res == 1){
                                        this.$message.success("抢票成功!")
                                    }else
                                        this.$message.error("票已售罄!")
                                    AllTickets().then(res =>{
                                        this.tickets = res.data
                                    })
                                    loading.close(); 
                                })
                            }).catch(err=>{
                                console.log(err)
                                this.$message.error("操作失败!")
                            })
                        }
                    })
                }
            })
        },
        getResult(order_id){
            return new Promise((resolve, reject)=>{
                function checkOrderStatus(){
                    getOrderResult(order_id).then(res=>{
                        let order_status = res.data
                        if(order_status == 0)
                            setTimeout(checkOrderStatus, 1000)
                        else {
                            resolve(order_status)
                        }  
                    }).catch(err =>{
                        console.log("gg")
                        reject(err)
                    })
                }
                checkOrderStatus()  
            })
        },
        editTicket(index, row){
            this.dialogVisible = true
            this.dialogTicket = row
        },
        updateTicket(){
            const ticket=this.dialogTicket
            UpdateTicket(ticket).then((res)=>{
                if(res.code == 200){
                    this.$message.success("编辑成功")
                    this.dialogVisible = false
                    AllTickets().then((res)=>{
                        this.tickets = res.data
                    })
                }
            })
        },
        deleteTicket(index, row){
            const ticket_id = row.ticket_id
            // console.log(ticket_id)
            DeleteTicket(ticket_id).then((res)=>{
                if(res.code == 200){
                    this.$message.success("删除成功")
                    AllTickets().then((res)=>{
                        this.tickets = res.data
                    })
                }
            })    
        }
    }
}
</script>

<style lang="scss">
.wrapper{
    display: flex;
    flex-flow: wrap;
    align-content: flex-start;
    overflow: auto;
}
.ticket{
    height: 350px;
    width: 250px;
    margin: 40px 30px;
    display: flex;
    flex-direction: column;
    .upper{
        padding: 5px;
        display: flex;
        .mainInfo{
            .title{
                font-weight: 700;
                font-size: 20px;
            }
            .description{
                color: #909399;
                font-size: 14px;
            }
        }
    }
    .divider{
        margin: 0 0 5px 0;
    }
    .tag{
        padding: 0 5px;
    }
    .below{
        div{
            font-size: 14px;
            padding: 5px;
        }
        .option{
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
    }
}
</style>