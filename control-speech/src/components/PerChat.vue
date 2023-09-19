<template>
  <div class="per_chat_container_box" :style="perChatData.isSelf ? 'flex-direction: row-reverse' : 'flex-direction: row'">
    <div class="profile_img_box">
      <el-avatar :src="require('@/assets/' + perChatData.personImg)" />
    </div>

    <div :class="perChatData.isSelf ? 'content_box_self' : 'content_box_not_self'">
      <PerVoiceChat :perChatData="perChatData"></PerVoiceChat>
    </div>
  </div>
</template>

<script>
import PerVoiceChat from "@/components/PerVoiceChat.vue";

export default {
  name: "PerChat",
  components: {
    PerVoiceChat,
  },
  data() {
    return {};
  },
  props: {
    perChatData: {
      type: Object,
      default() {
        return {};
      },
    },
  },
};
</script>

<style lang="scss" scoped>
.per_chat_container_box {
  width: 100%;
  float: left;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 0.5rem 0 0.5rem 0;
  .profile_img_box {
  }
  .content_box_self {
    margin: 0 1.5rem 0 1.5rem;
    padding: 0.3rem 0.3rem 0.3rem 0.3rem;
    border-radius: 0.5rem;
    background-color: #353b48;
    position: relative;

    &::before {
      content: "";
      border-top: 0.3rem solid transparent;
      border-bottom: 0.8rem solid transparent;
      border-left: 0.8rem solid #353b48;
      position: absolute;
      top: 0.8rem;
      right: -0.6rem;
    }
  }

  .content_box_not_self {
    margin: 0 1.5rem 0 1.5rem;
    padding: 0.3rem 0.3rem 0.3rem 0.3rem;
    border-radius: 0.5rem;
    background-color: #575e70;
    position: relative;

    &::before {
      content: "";
      border-top: 0.3rem solid transparent;
      border-bottom: 0.8rem solid transparent;
      border-right: 0.8rem solid #575e70;
      position: absolute;
      top: 0.8rem;
      left: -0.6rem;
    }
  }
}
</style>