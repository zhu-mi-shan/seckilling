<template>
  <el-card>
    <div slot="header">
      <span style="font-weight: bold">添加活动</span>
    </div>
    <el-form ref="form" :model="ticket" :rules="rules" label-width="100px">
      <el-form-item label="活动名称" prop="ticket_name">
        <el-input v-model="ticket.ticket_name"></el-input>
      </el-form-item>
      <el-form-item label="活动介绍" prop="ticket_des">
        <el-input v-model="ticket.ticket_des"></el-input>
      </el-form-item>
      <el-form-item label="票价" prop="ticket_price">
        <el-input v-model="ticket.ticket_price"></el-input>
      </el-form-item>
      <el-form-item label="票数量" prop="ticket_cnt">
        <el-input v-model="ticket.ticket_cnt"></el-input>
      </el-form-item>
      <el-form-item label="售票时间" required>
        <el-row type="flex">
          <el-col :span="11">
            <el-form-item prop="ticket_start_time">
              <el-date-picker
                  style="width: 100%;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  v-model="ticket.ticket_start_time"
                  type="datetime"
                  placeholder="开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">
            ———
          </el-col>
          <el-col :span="11">
            <el-form-item prop="ticket_end_time">
              <el-date-picker
                  style="width: 100%;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  v-model="ticket.ticket_end_time"
                  type="datetime"
                  placeholder="结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { addTicket } from '@/api/TicketAdmin';
export default {
name: "AddTicket",
data() {
    return {
    ticket:{

    },
    rules: {
        ticket_name: [{ required: true, message: "请输入活动名称", trigger: 'blur' },],
        ticket_des: [{ required: true, message: "请输入活动介绍", trigger: "blur" }],
        ticket_price: [{ required: true, message: "请输入票价", trigger: "blur" }],
        ticket_cnt: [{ required: true, message: "请输入票数量", trigger: "blur" }],
        ticket_start_time:[{required: true, message: "请输入售票开始时间", trigger: "change"}],
        ticket_end_time:[{required: true, message: "请输入售票结束时间", trigger: "change"}],
    },
    };
},
mounted(){

},
methods: {
    submitForm() {
      this.$refs.form.validate(valid => {
          if (valid) {
              addTicket(this.ticket).then(res =>{
                  this.$message.success("添加成功！")
                  this.resetForm()
              }).catch(()=>{
                  this.$message.error("添加失败！")
              })
          } else {
            window.alert("form validation failed");
          }
      });
    },
    resetForm() {
    this.$refs.form.resetFields();
    },
},
};
</script>

<style>
.el-form-item__label {
  padding-right: 15px;
  font-weight: bold;
  text-align: right;
}
.line {
  display: grid;
  place-items: center;
  height: 100%;
}
</style>
  