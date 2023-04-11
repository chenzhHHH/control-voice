<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">句式</div>
  </div>

  <div class="container">
    <RecordInput v-for="(item, index) in sentenceList" :key="index" :sentence="item"></RecordInput>
  </div>
</template>
  
<script>
import RecordInput from "@/components/RecordInput.vue";

export default {
  components: {
    RecordInput,
  },
  data() {
    return {
      sentenceList: [],
    };
  },
  created() {
    this.initSentenceData(this.$route.params.wordId);
  },
  methods: {
    initSentenceData(wordId) {
      let that = this;

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/record/getSentenceListByWordId", { wordId: wordId }, config).then((response) => {
        that.sentenceList = response.data.data;
      });
    },
    goBack() {
      this.$router.replace("/course");
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
}
</style>