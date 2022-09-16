<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          @open="handleOpen"
          @close="handleClose"
        >
          <el-menu-item index="0" class="top">BOE 商用画屏发布系统</el-menu-item>
          <el-menu-item index="1"  @click="$router.push('/homeView')">
            <el-icon><HomeFilled /></el-icon>  首页概览
          </el-menu-item>
          <template v-for="(item , index) in menus " :key="index">
            <el-sub-menu :index="item.path">
              <template #title>
                <el-icon><document /></el-icon>
                <span>{{item.name}}</span>
              </template>
                <router-link :to="item.path + '' + child.path" v-for="(child , index ) in item.children" :key="index">
                  <el-menu-item :index="item.path + '/' + child.path" >
                    <el-icon><document /></el-icon>
                    {{child.name}}
                  </el-menu-item>
                </router-link>
            </el-sub-menu>
          </template>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="left" >
            <el-breadcrumb separator=">">
              <template v-for="(item, index) in breadList">
                <el-breadcrumb-item
                  v-if="item.name"
                  :key="index"
                >{{ item.name }}</el-breadcrumb-item>
              </template>
            </el-breadcrumb>
          </div>
          <div class="img-part">
            <div class="name" >
              {{username}}
            </div>
            <img src="../assets/head.png">
            <el-button @click="logoutVisible = true">退出登录</el-button>
          </div>
        </el-header>
        <el-main>
          <div class="content">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <!--  退出确认弹窗-->
  <el-dialog v-model="logoutVisible"  width="35%">
    <div>是否退出</div>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="logoutVisible = false">返回</el-button>
        <el-button type="primary" @click="$router.push('/')">确认</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { defineComponent } from 'vue'
import global from '../common.vue'
import {
  Document,
  HomeFilled,
  // Location,
  // Setting,
}from '@element-plus/icons-vue'
export default defineComponent({
  components: {
    Document,
    HomeFilled,
    // Location,
    // Setting,
  },
  data() {
    return {
      logoutVisible: false,
      username: global.name,
      breadList: [],
      menus: [
        // {
        //   name:"首页概览",
        //   path:"/",
        // },
        {
          name:"素材管理",
          path:"/material",
          children: [
            {
              name:"素材列表",
              path:"/list",
            },
          ],
        },
        {
          name:"节目制作与发布",
          path:"/program",
          children: [
            {
              name:"发布节目",
              path:"/list",
            },
            {
              name:"发布公告",
              path:"/noticelist",
            },
          ],
        },
        // {
        //   name:"计划管理",
        //   path:"/plan",
        //   children: [
        //     {
        //       name:"计划列表",
        //       path:"/list",
        //     },
        //     // {
        //     //   name:"计划审核",
        //     //   path:"/jhsh",
        //     // },
        //   ],
        // },
        {
          name:"设备管理",
          path:"/equipmentControl",
          children: [
            {
              name:"设备列表",
              path:"/equipmentList",
            },
            {
              name:"分组管理",
              path:"/groupControl",
            },
          ],
        },
        {
          name:"系统管理",
          path:"/itemControl",
          children: [
            {
              name:"账户管理",
              path:"/userControl",
            },
          ],
        },

      ]
    }
  },
  methods: {
    getMatched() {
      this.breadList = this.$route.matched;
    },
    handleOpen (){
      console.log()
    },
    handleClose(){
      console.log()
    },
  },
  created() {
    this.getMatched()
  },
  watch: {
    $route(to,from) {
      this.breadList = this.$route.matched;
      // console.log(this.breadList)
    }
  }

})

</script>

<style lang="less" scoped>
.common-layout{
  height: 100vh;
}
.common-layout .el-container{
  height: 100%;

}
.common-layout .el-menu{
  height: 100%;
  width: 100%;
  .top{
    font-size: 1rem;
  }
  border-right-width: 0;
}
.common-layout .el-container .el-container{
  background-color: #434950;//#232738
}
.common-layout .el-container .el-container .el-header{
  height: 2rem;
  background-color: #545c64;
  display: flex;
  justify-content: space-between;
  align-items:center;
  .left{
    .el-breadcrumb::v-deep .el-breadcrumb__inner{
      color: white;
    }

  }
  .img-part{
    display: flex;
    .name{
      padding:0 0.4rem;
      color:white;
    }
    img{
      width: 1.5rem;
      height: 1.5rem;
      border-radius: 1rem;
    }
    .el-button{
      width: 4.5rem;
      height: 1.5rem;
      color: red;
      margin-left: 1rem;
      font-size: 0.8rem;
      border-radius: 0.3rem;
    }
}

}

</style>
