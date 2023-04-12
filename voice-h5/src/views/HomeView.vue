<template>
  <div class="container">
    <div class="enter_box">
      <div class="enter_box">语音录制</div>

      <van-button class="enter_bt" round type="success" @click="toRecord()">开始录制</van-button>
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
    toRecord() {
      this.$router.replace("/course");
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

  .enter_box {
    text-align: center;
    .enter_box {
      font-size: 3rem;
      color: #dc7633;
      margin-bottom: 2rem;
    }

    .enter_bt {
      width: 10rem;
      font-size: 1rem;
    }
  }
}
</style>