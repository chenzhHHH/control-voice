<template>
  <van-nav-bar safe-area-inset-top />

  <van-number-keyboard safe-area-inset-bottom />

  <div id="app">
    <router-view />

    <div style="height: 5rem"></div>

    <van-tabbar v-model="active" v-if="$route.meta.showTab">
      <van-tabbar-item to="home" icon="home-o">首页</van-tabbar-item>
      <van-tabbar-item to="course" icon="service-o">录制</van-tabbar-item>
      <van-tabbar-item to="my" icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  setup() {
    const active = ref(0);
    return { active };
  },
  watch: {
    $route(to) {
      this.updateActive(to);
    },
  },
  data() {
    return {};
  },
  methods: {
    updateActive(to) {
      switch (to.name) {
        case "home":
          this.active = 0;
          break;
        case "course":
          this.active = 1;
          break;
        case "record":
          this.active = 1;
          break;
        case "my":
          this.active = 2;
          break;
        default:
          this.active = 0;
      }
    },
  },
};
</script>

<style>
body {
  font-size: 16px;
  background-color: #f8f8f8;
  -webkit-font-smoothing: antialiased;
}
</style>