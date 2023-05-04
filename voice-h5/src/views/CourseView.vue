<template>
  <div class="header">
    <!-- <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" /> -->
    <div class="title_box">特情词</div>
  </div>

  <div class="container">
    <div class="course_box" v-for="(item, index) in wordList" :key="index" :value="item">
      <van-row @click="goToLearnWord(item)">
        <van-col span="4">
          <div class="index_box" :style="changeNumBoxColor(item)">{{ index + 1 }}</div>
        </van-col>

        <van-col span="9">
          <div class="word_box">
            <span class="word">{{ item.word }}</span>
          </div>
        </van-col>

        <van-col span="8">
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
</template>

<script>
export default {
  data() {
    return {
      wordList: [],
    };
  },
  created() {
    this.initWordData();
  },
  computed: {
    changeNumBoxColor() {
      return (item) => ({
        backgroundColor: item.isFinish ? "#2ECC71" : "#95A5A6",
      });
    },
  },
  methods: {
    initWordData() {
      let that = this;

      let formData = {
        userId: localStorage.getItem("userId"),
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getWordList", formData, config).then((response) => {
        that.wordList = response.data.data;
      });
    },
    goBack() {
      // this.$router.replace('/home');
    },
    goToLearnWord(item) {
      this.$router.replace("/record/" + item.id);
    },
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
</style>