<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">审核句式</div>

    <div class="filter_box">
      <van-button class="filter_bt" :style="changeFilterBtStyle('')" round size="small" @click="filterSentence('')">全部({{ sentenceNum.totalNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('unFinish')" round size="small" @click="filterSentence('unFinish')">未审核({{ sentenceNum.unFinishNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('finish')" round size="small" @click="filterSentence('finish')">已审核({{ sentenceNum.finishNum }})</van-button>
    </div>

    <div class="check_box">
      <div class="check_all_checked_box">
        <van-checkbox v-model="isCheckAllChecked"></van-checkbox>
      </div>

      <div class="check_record_box">
        <van-popover v-model:show="showCheckPopover" :actions="checkActions" @select="checkAllSelect">
          <template #reference>
            <van-button class="check_record_bt" round type="default" size="small"> 审核 </van-button>
          </template>
        </van-popover>
      </div>
    </div>
  </div>

  <div class="container">
    <RecordInput
      v-for="(item, index) in sentenceList"
      :key="index"
      :sentence="item"
      :ref="'check_' + item.id"
      @triggerUpdateIsShowOverlay="updateIsShowOverlay"
      @triggerRefreshSentenceData="refreshSentenceData"
      :isCheckShow="true"
    ></RecordInput>
  </div>

  <van-overlay :show="overlay.isShowOverlay" z-index="2">
    <div class="overlay-wrapper">
      <div class="overlay-block">
        <van-icon size="1rem" color="#85929E" name="volume-o" v-if="overlay.icon === 'volume'" />
        <van-loading size="1rem" color="#85929E" v-else />

        <span class="overlay-text">{{ overlay.showOverlayText }}</span>
      </div>
    </div>
  </van-overlay>
</template>
    
<script>
import RecordInput from "@/components/RecordInput.vue";
import { Loading, Overlay, Popover, Checkbox } from "vant";

export default {
  components: {
    RecordInput,
    [Loading.name]: Loading,
    [Overlay.name]: Overlay,
    [Popover.name]: Popover,
    [Checkbox.name]: Checkbox,
  },
  data() {
    return {
      overlay: {
        isShowOverlay: false,
        showOverlayText: "",
        icon: "",
      },
      sentenceList: [],
      filterType: "",
      sentenceNum: {
        totalNum: 0,
        unFinishNum: 0,
        finishNum: 0,
      },
      showCheckPopover: false,
      checkActions: [
        { text: "合格", icon: "passed" },
        { text: "不合格", icon: "close" },
      ],
      isCheckAllChecked: false,
    };
  },
  created() {
    this.initData();
  },
  watch: {
    isCheckAllChecked(newVal) {
      let that = this;

      that.sentenceList.forEach((sentence) => {
        that.$refs["check_" + sentence.id][0].isCheckChecked = newVal;
      });
    },
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

      that.updateIsShowOverlay({ isShowOverlay: true, showOverlayText: "数据获取" });

      let formData = {
        userId: that.$route.params.userId,
        wordId: wordId,
        filterType: that.filterType,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getCheckSentenceList", formData, config).then((response) => {
        that.sentenceList = response.data.data;
        that.updateIsShowOverlay({ isShowOverlay: false, showOverlayText: "数据获取" });
      });
    },
    initSentenceNum(wordId) {
      let that = this;

      let formData = {
        userId: that.$route.params.userId,
        wordId: wordId,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getCheckSentenceNum", formData, config).then((response) => {
        that.sentenceNum = response.data.data;
      });
    },
    initData() {
      let that = this;

      that.initSentenceData(this.$route.params.wordId);
      that.initSentenceNum(this.$route.params.wordId);
    },
    goBack() {
      let that = this;

      that.$router.replace("/checkCourse/" + that.$route.params.userId);
    },
    updateIsShowOverlay(obj) {
      let that = this;

      that.overlay.isShowOverlay = obj.isShowOverlay;
      that.overlay.showOverlayText = obj.showOverlayText;
      that.overlay.icon = obj.icon;
    },
    refreshSentenceData() {
      let that = this;

      that.initData();
    },
    filterSentence(filterType) {
      let that = this;

      that.filterType = filterType;

      that.initData();
    },
    checkAllSelect(action) {
      let that = this;

      let checkRecordIdList = [];

      that.sentenceList.forEach((sentence) => {
        if (that.$refs["check_" + sentence.id][0].isCheckChecked && sentence.recordId) {
          checkRecordIdList.push(sentence.recordId);
        }
      });

      if (checkRecordIdList.length === 0) {
        that.$notify({ type: "warning", message: "请选择审核句式" });

        return;
      }

      let formData = {
        recordIds: checkRecordIdList,
        checkUserId: localStorage.getItem("userId"),
        checkType: action.text,
      };

      let config = {
        headers: {
          "Content-Type": "application/json;charset=utf-8",
        },
      };

      that.$http.post("/record/checkRecords", formData, config).then((response) => {
        if (response.data.code === "2000") {
          that.refreshSentenceData();

          that.$notify({ type: "success", message: response.data.data });
        }
      });
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
  z-index: 2;
  background-color: #f8f8f8;
  height: 14rem;
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

  .check_box {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 1rem;
    .check_all_checked_box {
    }

    .check_record_box {
      margin-left: 1rem;
    }
  }
}
.container {
  margin-top: 14rem;
}

.overlay-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  .overlay-block {
    width: 6.5rem;
    height: 6.5rem;
    border-radius: 2rem;
    background-color: #f2f4f4;
    display: flex;
    justify-content: center;
    align-items: center;
    opacity: 0.3;

    .overlay-text {
      color: #85929e;
      font-size: 1rem;
      margin-left: 0.5rem;
    }
  }
}
</style>