<template>
  <div class="record_container" :style="changeContainerColor">
    <div class="content_box">
      <span class="sentence">{{ sentence.sentence }}</span>
    </div>

    <div class="control_box">
      <van-button class="control_bt" round type="success" size="small" @click="startRecorder()" v-show="!isRecording">开始录音</van-button>

      <van-button class="control_bt" round type="danger" size="small" @click="stopRecorder()" v-show="isRecording">停止录音</van-button>

      <van-button class="control_bt" round type="primary" :disabled="!isExistVoice" size="small" @click="playRecorder()">回听音频</van-button>
    </div>
  </div>
</template>

<script>
import Recorder from "js-audio-recorder";
import { Loading, Overlay } from "vant";

export default {
  components: {
    [Loading.name]: Loading,
    [Overlay.name]: Overlay,
  },
  data() {
    return {
      isRecording: false,
      isExistVoice: false,
    };
  },
  props: {
    sentence: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  mounted() {
    this.getPermission();

    this.initRecorder();
  },
  created() {
    let that = this;

    that.isExistVoice = that.sentence.isRecord;
  },
  computed: {
    changeContainerColor() {
      let that = this;

      if (that.sentence.isRecord) {
        return "background-color: #D4EFDF";
      } else {
        return "background-color: #EAEDED";
      }
    },
  },
  methods: {
    getPermission() {
      let that = this;

      Recorder.getPermission().then(
        () => {
          // console.log("录音给权限了");
        },
        (error) => {
          that.$notify(`${error.name} : ${error.message}`);
          console.log(`${error.name} : ${error.message}`);
        }
      );
    },
    initRecorder() {
      let that = this;

      that.recorder = new Recorder({
        sampleBits: 16,
        sampleRate: 16000,
        numChannels: 1,
      });
    },
    getRecorder() {
      let map = new Map();
      map.set("duration", this.recorder.duration);
      map.set("fileSize", this.recorder.fileSize);
      map.set("PCMBlob", this.recorder.getPCMBlob());
      map.set("WAVBlob", this.recorder.getWAVBlob());
      return map;
    },
    startRecorder() {
      let that = this;
      console.log("开始录音>>>");

      that.recorder.start().then(
        () => {},
        (error) => {
          console.log(`${error.name} : ${error.message}`);
        }
      );

      that.isRecording = true;
    },
    stopRecorder() {
      let that = this;

      that.recorder.stop();

      that.triggerIsShowEvalOverlay(true);

      let recordResult = that.getRecorder();
      console.log("结束录音>>>", recordResult);

      let formData = {
        userId: sessionStorage.getItem('userId'),
        sentenceId: that.sentence.id,
        wordId: that.sentence.wordId,
        voiceFile: recordResult.get("WAVBlob"),
      };

      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      that.$http.post("/record/recordVoice", formData, config).then((response) => {
        console.log(response.data);
        that.triggerIsShowEvalOverlay(false);
      });

      that.isRecording = false;
    },
    playRecorder() {
      this.recorder.play();
    },
    triggerIsShowEvalOverlay(isShowEvalOverlay) {
      let that = this;
      that.$emit("triggerIsShowEvalOverlay", isShowEvalOverlay);
    },
  },
};
</script>

<style scoped lang="less">
.record_container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1.5rem 0 1.5rem 0;
  margin-bottom: 1rem;
  .content_box {
    width: 20rem;
    .sentence {
      font-size: 1rem;
    }
  }

  .control_box {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    padding: 1rem 0 0 0;
    .control_bt {
      margin: 0 1rem 0 1rem;
      font-size: 0.5rem;
    }
  }
}
</style>