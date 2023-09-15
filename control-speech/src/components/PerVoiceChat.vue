<template>
  <div class="per_voice_chat_container_box">
    <div class="main_msg_box" :style="perChatData.isSelf ? 'flex-direction: row' : 'flex-direction: row-reverse'">
      <div class="voice_box" :style="perChatData.isSelf ? 'flex-direction: row' : 'flex-direction: row-reverse'">
        <div class="voice_icon_box">
          <el-icon color="green" size="30">
            <VideoPlay />
          </el-icon>
        </div>

        <div class="chat_time_box" :style="perChatData.isSelf ? 'padding: 0 2rem 0 0.5rem' : 'padding: 0 0.5rem 0 2rem'">{{ perChatData.chatTime }}</div>
      </div>

      <div class="tag_box" v-show="perChatData.tag">{{ perChatData.tag }}</div>

      <div class="callsign_box" :style="perChatData.isSelf ? 'padding: 0 0.5rem 0 2rem' : 'padding: 0 2rem 0 0.5rem'">{{ perChatData.callsign }}</div>

      <div class="signal_box">
        <div class="signal_text_box">{{ perChatData.signal }}</div>
      </div>
    </div>

    <div class="divider_box">
      <el-divider />
    </div>

    <div class="brief_msg_box">
      <div class="discribe_msg_box" v-html="perChatData.discribe"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PerVoiceChat",
  components: {},
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
.per_voice_chat_container_box {
  padding: 1rem 1rem 1rem 1rem;
  .main_msg_box {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    .voice_box {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      .voice_icon_box {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
      }
      .chat_time_box {
        color: #969aa6;
      }
    }
    .tag_box {
      border: 0.1rem solid yellow;
      background-color: yellow;
      border-radius: 0.3rem;
      color: #000;
      font-weight: 600;
    }
    .callsign_box {
      font-size: 1.5rem;
      color: #1cbc66;
      font-weight: 600;
    }
    .signal_box {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      .signal_text_box {
        width: 1.8rem;
        height: 1.8rem;
        border-radius: 0.9rem;
        background-color: #9b6c4f;
        text-align: center;
        line-height: 1.8rem;
        font-weight: 600;
      }
    }
  }

  .divider_box {
  }

  .brief_msg_box {
    .discribe_msg_box {
    }
  }
}
</style>