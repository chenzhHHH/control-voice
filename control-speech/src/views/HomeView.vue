<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="aside_box">
        <SectorChat></SectorChat>
      </el-aside>

      <el-container>
        <el-header class="header_box">
          <SpecialWord></SpecialWord>
        </el-header>

        <el-main class="main_box">
          <DisposalCard></DisposalCard>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import SectorChat from "@/views/SectorChatView.vue";
import SpecialWord from "@/views/SpecialWordView.vue";
import DisposalCard from "@/views/DisposalCardView.vue";

export default {
  name: "HomeView",
  components: {
    SectorChat,
    SpecialWord,
    DisposalCard
  },
};
</script>

<style lang="scss" scoped>
.common-layout {
  width: 100vw;
  height: 100vh;
  .aside_box {
    padding: 0 10px 0 10px;
    width: 50vw;
  }
  .header_box {
    padding: 0 10px 0 10px;
    background-color: #3b4253;
    height: 50vh;
  }
  .main_box {
    padding: 0 10px 0 10px;
    background-color: #343a49;
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
