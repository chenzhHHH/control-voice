<template>
  <div class="header">
    <div class="title_box">特情词</div>

    <div class="filter_box">
      <van-button class="filter_bt" :style="changeFilterBtStyle('')" round size="small" @click="filterSentence('')">全部({{ wordNum.sumNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('unFinish')" round size="small" @click="filterSentence('unFinish')">未完成({{ wordNum.unFinishNum }})</van-button>

      <van-button class="filter_bt" :style="changeFilterBtStyle('finish')" round size="small" @click="filterSentence('finish')">已完成({{ wordNum.finishNum }})</van-button>
    </div>
  </div>

  <div class="container">
    <div class="course_box" v-for="(item, index) in wordList" :key="index" :value="item">
      <van-row @click="goToLearnWord(item)">
        <van-col span="4">
          <div class="index_box" :style="changeNumBoxColor(item)">{{ index + 1 }}</div>
        </van-col>

        <van-col span="10">
          <div class="word_box">
            <span class="word">{{ item.word }}</span>
          </div>
        </van-col>

        <van-col span="4" offset="3">
          <div class="num_box">
            <span class="num">{{ item.finishNum }}/{{ item.unfinishNum + item.finishNum }}</span>
          </div>
        </van-col>

        <van-col span="3">
          <div class="in_icon_box">
            <van-icon class="in_icon" name="arrow" size="20px" />
          </div>
        </van-col>
      </van-row>
    </div>
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
import { Loading, Overlay } from "vant";

export default {
  components: {
    [Loading.name]: Loading,
    [Overlay.name]: Overlay,
  },
  data() {
    return {
      isShowOverlay: false,
      showOverlayText: "",
      wordList: [],
      filterType: "unFinish",
      wordNum: {
        sumNum: 0,
        unFinishNum: 0,
        finishNum: 0,
      },
    };
  },
  created() {
    this.initWordData();

    this.initWordNum();
  },
  computed: {
    changeNumBoxColor() {
      return (item) => ({
        backgroundColor: item.isFinish ? "#2ECC71" : "#95A5A6",
      });
    },
    changeFilterBtStyle() {
      let that = this;

      return (type) => ({
        backgroundColor: type === that.filterType ? "#F1948A" : "#ECF0F1",
      });
    },
  },
  methods: {
    initWordData() {
      let that = this;

      that.updateIsShowOverlay({ isShowOverlay: true, showOverlayText: "数据获取中" });

      let formData = {
        userId: localStorage.getItem("userId"),
        filterType: that.filterType,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getWordList", formData, config).then((response) => {
        that.wordList = response.data.data;
        that.updateIsShowOverlay({ isShowOverlay: false, showOverlayText: "数据获取中" });
      });
    },
    initWordNum() {
      let that = this;

      let formData = {
        userId: localStorage.getItem("userId"),
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getWordNum", formData, config).then((response) => {
        that.wordNum = response.data.data;
      });
    },
    updateIsShowOverlay(obj) {
      let that = this;

      that.isShowOverlay = obj.isShowOverlay;
      that.showOverlayText = obj.showOverlayText;
    },
    goToLearnWord(item) {
      this.$router.replace("/record/" + item.id);
    },
    filterSentence(filterType) {
      let that = this;

      that.filterType = filterType;

      that.initWordData();

      that.initWordNum();
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
  height: 8rem;
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
  margin-top: 8rem;
  .course_box {
    margin: 1.2rem 0 1.2rem 1.2rem;
    .index_box {
      height: 2.2rem;
      width: 2.2rem;
      text-align: center;
      line-height: 2.2rem;
      border-radius: 2.2rem;
      color: aliceblue;
      font-size: 1rem;
    }
    .word_box {
      line-height: 2.2rem;
      .word {
        font-size: 1.1rem;
        font-weight: bold;
      }
    }

    .num_box {
      line-height: 2.2rem;
      .num {
        font-size: 1rem;
      }
    }

    .in_icon_box {
      line-height: 2.2rem;
      .in_icon {
      }
    }
  }
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