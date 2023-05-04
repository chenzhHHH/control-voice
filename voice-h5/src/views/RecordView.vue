<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">句式</div>

    <div class="filter_box">
      <van-button class="filter_bt" :style="changeFilterBtStyle('')" round size="small" @click="filterSentence('')">全部({{ sentenceNum.sumNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('unFinish')" round size="small" @click="filterSentence('unFinish')">未完成({{ sentenceNum.unFinishNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('finish')" round size="small" @click="filterSentence('finish')">已完成({{ sentenceNum.finishNum }})</van-button>
    </div>
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
      filterType: "",
      sentenceNum: {
        sumNum: 0,
        unFinishNum: 0,
        finishNum: 0,
      },
    };
  },
  created() {
    this.initSentenceData(this.$route.params.wordId);

    this.initSentenceNum(this.$route.params.wordId);
  },
  computed: {
    changeFilterBtStyle() {
      let that = this;

      return (type) => ({
        backgroundColor: type === that.filterType ? "#F1948A" : "#ECF0F1",
      });
    },
  },
  methods: {
    initSentenceData(wordId) {
      let that = this;

      that.updateIsShowOverlay({ isShowOverlay: true, showOverlayText: "数据获取中" });

      let formData = {
        userId: localStorage.getItem("userId"),
        wordId: wordId,
        filterType: that.filterType,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getSentenceListByWordId", formData, config).then((response) => {
        that.sentenceList = response.data.data;
        that.updateIsShowOverlay({ isShowOverlay: false, showOverlayText: "数据获取中" });
      });
    },
    initSentenceNum(wordId) {
      let that = this;

      let formData = {
        userId: localStorage.getItem("userId"),
        wordId: wordId,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getSentenceNumByWordId", formData, config).then((response) => {
        that.sentenceNum = response.data.data;
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
      that.initSentenceNum(this.$route.params.wordId);
    },
    filterSentence(filterType) {
      let that = this;

      that.filterType = filterType;

      that.initSentenceData(this.$route.params.wordId);
      that.initSentenceNum(this.$route.params.wordId);
    },
  },
};
</script>
  
<style scoped lang="less">
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  .back_icon {
    margin: 1rem;
  }

  .title_box {
    margin: 1rem 0 1rem 0;
    text-align: center;
    font-size: 2rem;
  }

  .filter_box {
    display: flex;
    flex-direction: row;
    .filter_bt {
      margin: 0 0 0 1rem;
      color: black;
    }
  }
}
.container {
  margin-top: 11rem;
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