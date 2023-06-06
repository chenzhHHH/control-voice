<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">人员</div>
  </div>

  <div class="container">
    <div class="choose_user_box" v-for="(item, index) in userRelCheckerList" :key="index" :value="item">
      <div class="choose_user_item" :style="changeChooseUserItemColor(item.finishNum, item.totalNum)" @click="chooseUser(item)">
        <van-row class="choose_user_item_row">
          <van-col span="10">{{ item.username }}</van-col>
          <van-col span="7" offset="4">{{ item.finishNum }}/{{ item.totalNum }}</van-col>
          <van-col span="3">
            <div class="in_icon_box">
              <van-icon class="in_icon" name="arrow" size="20px" />
            </div>
          </van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  components: {},
  data() {
    return {
      userRelCheckerList: [],
    };
  },
  created() {
    let that = this;

    that.initUserRelCheckerList();
  },
  computed: {
    changeChooseUserItemColor() {
      return (finishNum, totalNum) => {
        if (finishNum === totalNum) {
          return "background-color: #D4EFDF";
        } else {
          return "background-color: #EAEDED";
        }
      };
    },
  },
  methods: {
    goBack() {
      this.$router.replace("/my");
    },
    initUserRelCheckerList() {
      let that = this;

      let formData = {
        checkerId: localStorage.getItem("userId"),
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/user/getUserRelCheckerListByCheckerId", formData, config).then((response) => {
        that.userRelCheckerList = response.data.data;
      });
    },
    chooseUser(userRelChecker) {
      this.$router.replace("/checkCourse/" + userRelChecker.userId);
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
  height: 11rem;
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
  padding: 2rem;
  margin-top: 11rem;

  .choose_user_box {
    .choose_user_item {
      height: 1.5rem;
      padding: 1rem;
      margin-bottom: 1rem;
      border-radius: 1rem;
      font-size: 1.1rem;

      .choose_user_item_row {
        line-height: 1.5rem;
      }
    }
  }
}
</style>