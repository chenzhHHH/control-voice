<template>
  <div class="container_box">
    <div class="title_box">当前扇区 AC4</div>

    <div class="chat_box">
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="false"></PerChat>
      <PerChat :isSelf="false"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
      <PerChat :isSelf="true"></PerChat>
    </div>
  </div>
</template>

<script>
import PerChat from "@/components/PerChat.vue";

export default {
  name: "SectorChat",
  components: {
    PerChat,
  },
};
</script>

<style lang="scss" scoped>
.container_box {
  .title_box {
    font-size: 2rem;
  }
  .chat_box {
    height: 85vh;
    overflow-y: scroll;
    padding: 20px;
    box-sizing: border-box;
    &::-webkit-scrollbar {
      width: 0; /* Safari,Chrome 隐藏滚动条 */
      height: 0; /* Safari,Chrome 隐藏滚动条 */
      display: none; /* 移动端、pad 上Safari，Chrome，隐藏滚动条 */
    }
  }
}
</style>