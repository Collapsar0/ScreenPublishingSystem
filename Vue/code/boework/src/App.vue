<template>
  <router-view></router-view>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import login from '@/views/login.vue'
export default {
  components:{
    HelloWorld,
    login
  }
}
</script>

<style lang="less">
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: '微软雅黑';
}
a{
  color: #333;
  text-decoration: none;
}
</style>
