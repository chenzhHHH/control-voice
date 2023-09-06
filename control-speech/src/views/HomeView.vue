<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="aside_box">
        <SectorChat></SectorChat>
      </el-aside>

      <el-container>
        <el-header class="header_box">通信失效相关特情词</el-header>

        <el-main class="main_box">处置卡</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import SectorChat from '@/views/SectorChat.vue'

export default {
  name: "HomeView",
  components: {
    SectorChat
  },
};
</script>

<style lang="scss" scoped>
.common-layout {
  width: 100vw;
  height: 100vh;
  .aside_box{
    padding: 0 10px 0 10px;
    width: 50vw;
  }
  .header_box{
    background-color: red;
    height: 50vh;
  }
  .main_box{
    background-color: blue;
    height: 50vh;
  }
}
// .home {
//   width: 90vw;
//   height: 90vh;
//   background-color: rgb(39, 42, 55);
//   border-radius: 15px;
//   position: absolute;
//   left: 50%;
//   top: 50%;
//   transform: translate(-50%, -50%);
// }
</style>
