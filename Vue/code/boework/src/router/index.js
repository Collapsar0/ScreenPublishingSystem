import { createRouter, createWebHistory } from 'vue-router'
import Helloworld from '../components/HelloWorld'
import HomeView from '../views/HomeView.vue'
import materialList from '../components/materialList'
import xtControl from "@/components/xtControl"
import groupControl from '@/views/groupControl'
import equipmentList from "@/views/equipmentList/equipmentList"
import equipmentControl from "@/views/equipmentControl/equipmentControl";
import programMakeRelease from "@/views/programMakeRelease/programMakeRelease";
import releaseProgram from "@/views/releaseProgram/releaseProgram";
import releaseNotice from "@/views/releaseNotice/releaseNotice";
// import planList from "@/views/planControl/planList"
// import planListnew from "@/views/planControl/planListnew"
// import planListequipment from "@/views/planControl/planListequipment"
import login from "@/views/login"

const routes = [
  {
    path: '/',
    name: '登录界面',
    component: login,
  },
  {
    path: '/homeView',
    component: Helloworld,
    redirect:'/home',

    children:[
      {
        path: '/home',
        name: '首页概览',
        component: HomeView
      },
      {
        path: '/material',
        name: '素材管理',
        component: materialList,
        children:[
          {
            path: '/material/list',
            name: '素材列表',
            component: materialList
          }
        ]
      },
      {
        path: '/itemControl',
        name: '系统管理',
        component: xtControl,
        children:[
          {
            path: '/itemControl/userControl',
            name: '账户管理',
            component: xtControl
          }
        ]
      },
      // {
      //   path: '/plan',
      //   name: '计划管理',
      //   component: planList,
      //   children:[
      //     {
      //       path: '/plan/list',
      //       name: '计划列表',
      //       component: planList,
      //     },
      //
      //
      //   ]
      // },
      // {
      //   path: '/plan/list/new',
      //   component: planListnew,
      // },
      // {
      //   path: '/plan/list/equipment',
      //   component: planListequipment,
      // },
      {
        path: '/equipmentControl',
        name: '设备管理',
        component: equipmentControl,
        children:[
          {
            path: '/equipmentControl/equipmentList',
            name: '设备列表',
            component: equipmentList
          },
          {
            path: '/equipmentControl/groupControl',
            name: '分组管理',
            component: groupControl
          }
        ]
      },
      {
        path: '/program',
        name: '节目制作与发布',
        component: programMakeRelease,
        children:[
          {
            path: '/program/list',
            name: '发布节目',
            component: releaseProgram
          },
          {
            path: '/program/noticelist',
            name: '发布公告',
            component: releaseNotice
          }
        ]
      },
    ]
  },



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
