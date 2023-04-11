<!-- <template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view/>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style> -->

<template>
  <van-nav-bar safe-area-inset-top />

  <van-number-keyboard safe-area-inset-bottom />

  <div id="app">
    <router-view />

    <div style="height: 5rem"></div>

    <van-tabbar v-model="active">
      <van-tabbar-item to="home" icon="home-o">首页</van-tabbar-item>
      <van-tabbar-item to="course" icon="service-o">录制</van-tabbar-item>
      <van-tabbar-item to="my" icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>

export default {
  data(){
    return{
      active: 0
    }
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