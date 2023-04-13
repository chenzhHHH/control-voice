<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">句式</div>
  </div>

  <div class="container">
    <RecordInput v-for="(item, index) in sentenceList" :key="index" :sentence="item" @triggerUpdateIsShowOverlay="updateIsShowOverlay" @triggerRefreshSentenceData="refreshSentenceData"></RecordInput>
  </div>

  <van-overlay :show="isShowOverlay" z-index="2">
    <div class="overlay-wrapper">
      <div class="overlay-block">
        <van-loading size="2rem" color="#85929E">{{ showOverlayText }}</van-loading>
      </div>
    </div>
  </van-overlay>
</template>
  
<script>
import RecordInput from "@/components/RecordInput.vue";
import { Loading, Overlay } from "vant";

export default {
  components: {
    RecordInput,
    [Loading.name]: Loading,
    [Overlay.name]: Overlay,
  },
  data() {
    return {
      isShowOverlay: false,
      showOverlayText: "",
      sentenceList: [],
    };
  },
  created() {
    this.initSentenceData(this.$route.params.wordId);
  },
  methods: {
    initSentenceData(wordId) {
      let that = this;

      let formData = {
        userId: sessionStorage.getItem("userId"),
        wordId: wordId,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getSentenceListByWordId", formData, config).then((response) => {
        that.sentenceList = response.data.data;
      });
    },
    goBack() {
      this.$router.replace("/course");
    },
    updateIsShowOverlay(obj) {
      let that = this;

      that.isShowOverlay = obj.isShowOverlay;
      that.showOverlayText = obj.showOverlayText;
    },
    refreshSentenceData() {
      let that = this;

      that.initSentenceData(this.$route.params.wordId);
    }
  },
};
</script>
  
<style scoped lang="less">
.header {
  .back_icon {
    margin: 1rem;
  }

  .title_box {
    margin: 1rem 0 1rem 0;
    text-align: center;
    font-size: 2rem;
  }
}
.container {
}

.overlay-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  .overlay-block {
    width: 8rem;
    height: 8rem;
    border-radius: 2rem;
    background-color: #f2f4f4;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>