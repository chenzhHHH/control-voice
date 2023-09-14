<template>
  <div class="per_voice_chat_container_box">
    <div class="main_msg_box" :style="isSelf ? 'flex-direction: row' : 'flex-direction: row-reverse'">
      <div class="voice_box" :style="isSelf ? 'flex-direction: row' : 'flex-direction: row-reverse'">
        <div class="voice_icon_box">
          <el-icon color="green" size="30">
            <VideoPlay />
          </el-icon>
        </div>

        <div class="voice_msg_box" :style="isSelf ? 'padding: 0 2rem 0 0.5rem' : 'padding: 0 0.5rem 0 2rem'">12:30</div>
      </div>

      <div class="tag_box">复诵-缺失跑道20R</div>

      <div class="callsign_box" :style="isSelf ? 'padding: 0 0.5rem 0 2rem' : 'padding: 0 2rem 0 0.5rem'">CSN3301</div>

      <div class="signal_box">
        <el-icon color="#9b6c4f" size="30">
          <CaretRight />
        </el-icon>
      </div>
    </div>

    <div class="divider_box">
      <el-divider />
    </div>

    <div class="brief_msg_box">
      <div class="discribe_msg_box">南方3301<span style="color: green">进跑道</span>20右</div>
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
    isSelf: Boolean,
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
      .voice_msg_box {
        color: #969aa6;
      }
    }
    .tag_box {
      border: 0.1rem solid yellow;
      background-color: yellow;
      border-radius: 0.3rem;
      color: #000;
    }
    .callsign_box {
      color: #1cbc66;
    }
    .signal_box {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
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