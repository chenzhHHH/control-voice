<template>
  <div class="record-container" :style="changeContainerColor">
    <div class="content-box">
      <span class="sentence" v-show="!isEdit">{{ sentence.sentence }}</span>

      <van-cell-group inset>
        <van-field v-model="editContent" rows="1" autosize type="textarea" v-show="isEdit" />
      </van-cell-group>
    </div>

    <div class="control-box">
      <div class="record-control-box" v-show="!isEdit">
        <van-button class="record-control-bt" round type="success" size="small" @click="startRecorder()" v-show="!isRecording">开始录音</van-button>

        <van-button class="record-control-bt" round type="danger" size="small" @click="stopRecorder()" v-show="isRecording">停止录音</van-button>

        <van-button class="record-control-bt" round type="primary" :disabled="!isExistVoice" size="small" @click="playRecorder()">回听音频</van-button>
      </div>

      <div class="record-edit-box">
        <van-button class="record-edit-bt" round type="default" size="small" @click="editSentence()" v-show="!isEdit && sentence.isEdit">修改句子</van-button>

        <van-button class="record-edit-bt" round type="success" size="small" @click="confirmEdit()" v-show="isEdit">确定</van-button>

        <van-button class="record-edit-bt" round type="danger" size="small" @click="cancelEdit()" v-show="isEdit">取消</van-button>
      </div>

      <div class="check_record_box">
        <van-popover v-model:show="showCheckPopover" :actions="checkActions" @select="checkSelect">
          <template #reference>
            <van-button class="check_record_bt" round type="default" size="small">审核</van-button>
          </template>
        </van-popover>
      </div>
    </div>
  </div>
</template>

<script>
import Recorder from "js-audio-recorder";
import { Loading, Overlay, Dialog, Popover } from "vant";

export default {
  components: {
    [Loading.name]: Loading,
    [Overlay.name]: Overlay,
    [Dialog.name]: Dialog,
    [Popover.name]: Popover,
  },
  data() {
    return {
      isRecording: false,
      isExistVoice: false,
      isEdit: false,
      editContent: "",
      showCheckPopover: false,
      checkActions: [
        { text: "合格", icon: "passed" },
        { text: "不合格", icon: "close" },
      ],
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
  watch: {
    sentence(newSentence) {
      let that = this;

      that.isExistVoice = newSentence.isRecord;
    },
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

      that.$notify({ type: "success", message: "开始录音" });

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

      that.triggerUpdateIsShowOverlay({ isShowOverlay: true, showOverlayText: "语音提交" });

      let recordResult = that.getRecorder();

      that.$notify({ type: "danger", message: "停止录音" });

      let formData = {
        recordId: that.sentence.recordId,
        userId: localStorage.getItem("userId"),
        sentenceId: that.sentence.id,
        wordId: that.sentence.wordId,
        voiceFile: recordResult.get("WAVBlob"),
      };

      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      // eslint-disable-next-line
      that.$http.post("/record/recordVoice", formData, config).then((response) => {
        that.triggerUpdateIsShowOverlay({ isShowOverlay: false, showOverlayText: "语音提交" });
        that.triggerRefreshSentenceData();
      });

      that.isRecording = false;
    },
    playRecorder() {
      let that = this;

      that.triggerUpdateIsShowOverlay({ isShowOverlay: true, showOverlayText: "正在播放", icon: "volume" });

      let formData = {
        userId: localStorage.getItem("userId"),
        sentenceId: that.sentence.id,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getVoice", formData, config).then((response) => {
        that.$notify({ type: "success", message: "音频获取成功" });

        let dataTemp = response.data.data;
        let readAudio = new Audio(`data:audio/x-wav;base64, ` + dataTemp);
        readAudio.play();

        readAudio.onended = () => {
          that.triggerUpdateIsShowOverlay({ isShowOverlay: false, showOverlayText: "正在播放", icon: "volume" });
        };
      });
    },
    editSentence() {
      let that = this;

      that.editContent = that.sentence.sentence;
      that.isEdit = true;
    },
    confirmEdit() {
      let that = this;

      Dialog.confirm({
        title: "修改",
        message: "如果修改该句子，原有的对应该句子的管制员录音记录将清空。",
      })
        .then(() => {
          that.isEdit = false;

          let formData = {
            sentenceId: that.sentence.id,
            sentenceText: that.editContent,
          };

          let config = {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
            },
          };

          that.$http.post("/record/editSentence", formData, config).then((response) => {
            if (response.data.code === "2000") {
              that.editContent = "";
              that.$notify({ type: "success", message: "修改成功" });
              that.triggerRefreshSentenceData();
            } else {
              that.$notify({ type: "warning", message: response.data.msg });
            }
          });
        })
        .catch(() => {});
    },
    cancelEdit() {
      let that = this;

      that.isEdit = false;
      that.editContent = "";
    },
    triggerUpdateIsShowOverlay(obj) {
      this.$emit("triggerUpdateIsShowOverlay", obj);
    },
    triggerRefreshSentenceData() {
      this.$emit("triggerRefreshSentenceData");
    },
    checkSelect(action) {
      // let that = this;

      console.log(action.text)
    }
  },
};
</script>

<style scoped lang="less">
.record-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 1.5rem 0 1.5rem 0;
  margin-bottom: 1rem;
  .content-box {
    width: 80%;
    .sentence {
      font-size: 1rem;
    }
    .sentence-textarea {
      font-size: 1rem;
      width: 100%;
      height: auto;
    }
  }

  .control-box {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    padding: 1rem 0 0 0;
    .record-control-box {
      .record-control-bt {
        margin: 0 1rem 0 1rem;
        font-size: 0.5rem;
      }
    }
    .record-edit-box {
      .record-edit-bt {
        margin: 0 1rem 0 1rem;
        font-size: 0.5rem;
      }
    }

    .check_record_box {
      .check_record_bt{
        margin: 0 1rem 0 1rem;
        font-size: 0.5rem;
      }
    }
  }
}
</style>