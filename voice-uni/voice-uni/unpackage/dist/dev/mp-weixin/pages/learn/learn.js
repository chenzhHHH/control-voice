"use strict";
var common_vendor = require("../../common/vendor.js");
const questionBoard = () => "../../components/questionBoard.js";
const _sfc_main = {
  components: {
    questionBoard
  },
  data() {
    return {
      words: ["Alpha", "Bravo", "Charlie", "Delta", "Echo"],
      expendIndex: 0,
      finishIndex: 0,
      isEval: false
    };
  },
  onLoad() {
  },
  computed: {
    changeButtonBoxStyle() {
      let that = this;
      return {
        buttonBoxDisabled: that.finishIndex !== that.words.length,
        buttonBoxNotDisabled: that.finishIndex === that.words.length
      };
    }
  },
  methods: {
    changeExpandIndex(data) {
      let that = this;
      that.expendIndex = data;
    },
    changeIsEval(data) {
      let that = this;
      that.isEval = data;
      if (data) {
        that.finishIndex += 1;
      }
    },
    goTo() {
      common_vendor.index.navigateTo({
        url: `../report/report`
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_col2 = common_vendor.resolveComponent("uni-col");
  const _easycom_uni_row2 = common_vendor.resolveComponent("uni-row");
  const _component_question_board = common_vendor.resolveComponent("question-board");
  (_easycom_uni_col2 + _easycom_uni_row2 + _component_question_board)();
}
const _easycom_uni_col = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-col/uni-col.js";
const _easycom_uni_row = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-row/uni-row.js";
if (!Math) {
  (_easycom_uni_col + _easycom_uni_row)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: this.finishIndex / this.words.length * 100,
    b: common_vendor.p({
      span: 21
    }),
    c: common_vendor.t($data.finishIndex),
    d: common_vendor.t($data.words.length),
    e: common_vendor.p({
      span: 1,
      offset: "1"
    }),
    f: common_vendor.f($data.words, (word, index, i0) => {
      return {
        a: "49864d99-3-" + i0,
        b: common_vendor.p({
          examQuestion: word,
          isExpand: index === $data.expendIndex,
          isShowExpand: index === $data.expendIndex + 1 && $data.isEval,
          examQuestionIndex: index,
          isEval: $data.isEval
        })
      };
    }),
    g: common_vendor.o($options.changeExpandIndex),
    h: common_vendor.o($options.changeIsEval),
    i: common_vendor.n($options.changeButtonBoxStyle),
    j: this.finishIndex !== this.words.length,
    k: common_vendor.o(($event) => $options.goTo())
  };
}
var MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/Users/chenzhihang/Desktop/project/voice-uni/voice-uni/pages/learn/learn.vue"]]);
wx.createPage(MiniProgramPage);
