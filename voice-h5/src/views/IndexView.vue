<template>
  <div id="app">
    <router-view />
  </div>

  <div style="height: 5rem;"></div>

  <van-tabbar v-model="active">
    <van-tabbar-item icon="home-o">首页</van-tabbar-item>
    <van-tabbar-item icon="search">分类</van-tabbar-item>
    <van-tabbar-item icon="guide-o">发现</van-tabbar-item>
    <van-tabbar-item icon="user-o">我的</van-tabbar-item>
  </van-tabbar>
</template>
  
<script>
  import { ref } from 'vue';

  export default {
    setup() {
      const active = ref(0);
      return { active };
    }
  };
</script>

<style>
  body {
    font-size: 16px;
    background-color: #f8f8f8;
    -webkit-font-smoothing: antialiased;
  }
</style>