<template>
<div class="body">
  <div class="left">
    <img src="../assets/login.png">
  </div>
  <div class="right">
    <div class="box">
      <div class="hello">Hello!</div>
      <div class="welcome">
        <div class="wleft" style="color:#c0c0c0">欢迎登录</div>
        <div class="wright" style="color:#0080ff">BOE商用画屏发布系统</div>
      </div>
      <div class="inputbox">
        <el-input v-model="inputName" placeholder="请输入账户名"></el-input>
        <el-input v-model="inputPassword" placeholder="请输入密码" show-password autocomplete="off"></el-input>
      </div>

        <el-button type="primary" @click="loginSystem()">登录</el-button>


    </div>

  </div>
</div>

</template>

<script>
import {onMounted, reactive, toRefs } from "vue";
import { judgePassword } from "@/api/login";
import { ElTable,ElMessage,UploadProps,UploadUserFile  } from 'element-plus';
import { useRouter } from 'vue-router'
import global from '../common.vue'
export default {
  setup(){
    const router = useRouter()
    const state = reactive({
      inputName:'',
      inputPassword:'',
    })
    const loginSystem = () => {
      judgePassword(state.inputName,state.inputPassword).then( (res) => {
        if(res.data.code === 200){
          ElMessage({
            message: '登录成功!',
            type: 'success',
          })
          global.name=state.inputName
          console.log( global.name)
          router.push({path:'/homeView'} )

        }
      })
    }
    return{
      ...toRefs(state),
      loginSystem,
    }
  }
}
</script>

<style lang="less">
.body{
    display: flex;
  .left{
    img{
      width: 50rem;
      height: 44rem;
    }
  }
  .right{
    .box{
      margin-top: 9rem;
      margin-left: 10rem;
      width: 25rem;
      height: 30rem;
      .hello{
        font-size:4rem
      }
      .welcome{
        display: flex;
        font-size: 1.2rem;
      }
      .inputbox{
        margin-top: 1rem;
        .el-input{
          margin-top: 1rem;
          height: 4rem;
          border-radius: 5rem;
        }
      }
      .el-button{
        margin-top: 4.5rem;
        height: 3.5rem;
        width: 15rem;
        border-radius: 4rem;
      }
    }
 }
}

</style>
