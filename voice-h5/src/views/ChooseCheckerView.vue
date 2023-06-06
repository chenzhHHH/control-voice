<template>
  <div class="header">
    <van-icon name="arrow-left" size="20px" class="back_icon" @click="goBack()" />

    <div class="title_box">选择检查员</div>
  </div>

  <div class="container">
    <div class="choose_checker_box" v-for="(item, index) in userRelCheckerList" :key="index" :value="item">
      <div class="choose_ckecker_item" :style="changeChooseCheckerItemColor(item.checkername)" @click="chooseChecker(item)">
        <van-row class="choose_ckecker_item_row">
          <van-col span="10">{{ item.username }}</van-col>
          <van-col span="7" offset="4" :style="changeCheckernameColor(item.checkername)">{{ item.checkername ? item.checkername : "未选择" }}</van-col>
          <van-col span="3">
            <div class="in_icon_box">
              <van-icon class="in_icon" name="arrow" size="20px" />
            </div>
          </van-col>
        </van-row>
      </div>
    </div>
  </div>

  <van-popup v-model:show="showChooseCheckerPicker" round position="bottom">
    <van-picker :columns="chooseCheckerList" value-key="cnName" @cancel="cancelChooseCheckerPicker" @confirm="confirmChooseCheckerPicker" />
  </van-popup>
</template>
    
<script>
import { Cell, CellGroup, Picker, Popup } from "vant";

export default {
  components: {
    [Cell.name]: Cell,
    [CellGroup.name]: CellGroup,
    [Picker.name]: Picker,
    [Popup.name]: Popup,
  },
  data() {
    return {
      userRelCheckerList: [],
      showChooseCheckerPicker: false,
      chooseCheckerList: [],
      userRelChecker: {},
    };
  },
  created() {
    let that = this;

    that.initUserRelCheckerList();
    that.initUserListData();
  },
  computed: {
    changeChooseCheckerItemColor() {
      return (val) => {
        if (val) {
          return "background-color: #D4EFDF";
        } else {
          return "background-color: #EAEDED";
        }
      };
    },
    changeCheckernameColor() {
      return (val) => {
        if (val) {
          return "color: black";
        } else {
          return "color: #E74C3C";
        }
      };
    },
  },
  methods: {
    goBack() {
      this.$router.replace("/my");
    },
    chooseChecker(userRelChecker) {
      let that = this;

      that.userRelChecker = userRelChecker;
      that.showChooseCheckerPicker = true;
    },
    cancelChooseCheckerPicker() {
      let that = this;

      that.userRelChecker = {};
      that.showChooseCheckerPicker = false;
    },
    confirmChooseCheckerPicker(checker) {
      let that = this;

      let formData = {
        id: that.userRelChecker.id,
        userId: that.userRelChecker.userId,
        checkerId: checker.id,
      };

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/user/insertOrUpdateUserRelChecker", formData, config).then((response) => {
        that.initUserRelCheckerList();
        that.$notify({ type: "success", message: response.data.data });
      });

      that.userRelChecker = {};
      that.showChooseCheckerPicker = false;
    },
    initUserRelCheckerList() {
      let that = this;

      let formData = {};

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/user/getUserRelCheckerList", formData, config).then((response) => {
        that.userRelCheckerList = response.data.data;
      });
    },
    initUserListData() {
      let that = this;

      let formData = {};

      let config = {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      };

      that.$http.post("/user/getUserList", formData, config).then((response) => {
        that.chooseCheckerList = response.data.data;
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
  height: 11rem;
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
  padding: 2rem;
  margin-top: 11rem;

  .choose_checker_box {
    .choose_ckecker_item {
      height: 1.5rem;
      padding: 1rem;
      margin-bottom: 1rem;
      border-radius: 1rem;
      font-size: 1.1rem;

      .choose_ckecker_item_row {
        line-height: 1.5rem;
      }
    }
  }
}
</style>