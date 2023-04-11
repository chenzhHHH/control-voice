<template>
  <div class="header">
    <!-- <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" /> -->
    <div class="title_box">特情词</div>
  </div>

  <div class="container">
    <div class="course_box" v-for="(item, index) in wordList" :key="index" :value="item">
      <van-row @click="goToLearnWord(item)">
        <van-col span="4">
          <div class="num_box">{{ index + 1 }}</div>
        </van-col>

        <van-col span="17">
          <div class="word_box">
            <span class="word">{{ item.word }}</span>
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
  methods: {
    initWordData() {
      let that = this;

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getWordList", {}, config).then((response) => {
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
    .num_box {
      height: 2rem;
      width: 2rem;
      text-align: center;
      line-height: 2rem;
      border: 0.1rem solid #2ecc71;
      border-radius: 2rem;
      background-color: #2ecc71;
      color: aliceblue;
      font-size: 1rem;
    }
    .word_box {
      line-height: 2rem;
      .word {
        font-size: 1.1rem;
        font-weight: bold;
      }
    }

    .in_icon_box {
      line-height: 2rem;
      .in_icon {
      }
    }
  }
}
</style>