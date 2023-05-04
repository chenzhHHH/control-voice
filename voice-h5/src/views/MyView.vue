<template>
  <div class="container">
    <div class="logout_box">
      <div class="logout_box"></div>

      <van-button class="logout_bt" round type="danger" @click="logout()">退出登录</van-button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {};
  },
  methods: {
    logout() {
      localStorage.clear();
      this.$router.replace("/home");
    },
  },
};
</script>

<style scoped lang="less">
.container {
  width: 20rem;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -70%);

  .logout_box {
    text-align: center;
    .logout_box {
      font-size: 3rem;
      color: #dc7633;
      margin-bottom: 2rem;
    }

    .logout_bt {
      width: 10rem;
      font-size: 1rem;
    }
  }
}
</style>