<template>
  <div class="sector_chat_container_box">
    <div class="title_box">当前扇区 AC4</div>

    <div class="chat_box">
      <div class="per_chat_box" v-for="(item, index) in perChatDatas" :key="index" :value="item">
        <PerChat :perChatData="item"></PerChat>
      </div>
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
  data() {
    return {
      perChatDatas: [
        {
          isSelf: true,
          chatTime: "12:30",
          tag: "复诵-缺失跑道20R",
          callsign: "CSN3301",
          signal: "进",
          discribe: "南方3301<span style='color:#1dbf67'>进跑道</span>20右",
        },
        {
          isSelf: false,
          chatTime: "12:30",
          tag: "复诵-缺失跑道20R",
          callsign: "CSN3301",
          signal: "进",
          discribe: "<span style='color:#1dbf67'>进跑道</span>南方3301",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.sector_chat_container_box {
  .title_box {
    font-size: 2rem;
  }
  .chat_box {
    height: 85vh;
    overflow-y: scroll;
    padding: 1rem;
    box-sizing: border-box;

    &::-webkit-scrollbar {
      width: 0; /* Safari,Chrome 隐藏滚动条 */
      height: 0; /* Safari,Chrome 隐藏滚动条 */
      display: none; /* 移动端、pad 上Safari，Chrome，隐藏滚动条 */
    }

    .per_chat_box {
    }
  }
}
</style>