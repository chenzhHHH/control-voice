"use strict";
var common_vendor = require("../common/vendor.js");
const _sfc_main = {
  data() {
    return {
      isRecording: false,
      isRecorded: false,
      score: ""
    };
  },
  props: {
    isExpand: Boolean,
    isShowExpand: Boolean,
    examQuestion: String,
    examQuestionIndex: String,
    examGroupId: String,
    isEval: Boolean
  },
  onLoad() {
  },
  computed: {
    changeQuestionBoardContainerStyle() {
      let that = this;
      return {
        questionBoardContainerDefault: !that.isExpand,
        questionBoardContainerExpand: that.isExpand
      };
    }
  },
  methods: {
    getNormalVoice() {
      common_vendor.index.showLoading({
        title: "\u83B7\u53D6\u97F3\u9891...",
        mask: true
      });
      setTimeout(() => {
        common_vendor.index.hideLoading();
      }, 2e3);
    },
    startRecord() {
      let that = this;
      that.isRecording = true;
    },
    closeRecord() {
      let that = this;
      that.isRecording = false;
      that.isRecorded = false;
    },
    stopRecord() {
      let that = this;
      that.isRecorded = true;
    },
    evalRecord() {
      let that = this;
      that.isRecording = false;
      that.isRecorded = false;
      common_vendor.index.showLoading({
        title: "\u6D4B\u8BC4\u4E2D...",
        mask: true
      });
      setTimeout(() => {
        common_vendor.index.hideLoading();
        that.score = "90";
        if (!that.isEval) {
          that.changeIsEval(true);
        }
      }, 2e3);
    },
    changeExpandIndex() {
      let that = this;
      that.$emit("changeExpandIndex", that.examQuestionIndex);
      that.changeIsEval(false);
    },
    changeIsEval(isEval) {
      this.$emit("changeIsEval", isEval);
    }
  }
};
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_uni_col2 = common_vendor.resolveComponent("uni-col");
  const _easycom_uni_row2 = common_vendor.resolveComponent("uni-row");
  (_easycom_uni_icons2 + _easycom_uni_col2 + _easycom_uni_row2)();
}
const _easycom_uni_icons = () => "../node-modules/@dcloudio/uni-ui/lib/uni-icons/uni-icons.js";
const _easycom_uni_col = () => "../node-modules/@dcloudio/uni-ui/lib/uni-col/uni-col.js";
const _easycom_uni_row = () => "../node-modules/@dcloudio/uni-ui/lib/uni-row/uni-row.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_uni_col + _easycom_uni_row)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $props.isShowExpand
  }, $props.isShowExpand ? {
    b: common_vendor.p({
      type: "bottom",
      size: 20
    }),
    c: common_vendor.o(($event) => $options.changeExpandIndex())
  } : {}, {
    d: common_vendor.t($props.examQuestion),
    e: $props.isExpand
  }, $props.isExpand ? common_vendor.e({
    f: !$data.isRecording
  }, !$data.isRecording ? {
    g: common_vendor.p({
      type: "sound-filled",
      size: 30,
      color: "white"
    }),
    h: common_vendor.o(($event) => $options.getNormalVoice()),
    i: common_vendor.p({
      span: 8,
      offset: 1
    }),
    j: common_vendor.p({
      type: "mic-filled",
      size: 50,
      color: "white"
    }),
    k: common_vendor.o(($event) => $options.startRecord()),
    l: common_vendor.p({
      span: 6
    }),
    m: common_vendor.t($data.score),
    n: common_vendor.p({
      span: 8
    })
  } : {}, {
    o: $data.isRecording
  }, $data.isRecording ? common_vendor.e({
    p: $data.isRecorded
  }, $data.isRecorded ? {
    q: common_vendor.p({
      type: "clear",
      size: 30,
      color: "grey"
    }),
    r: common_vendor.o(($event) => $options.closeRecord())
  } : {}, {
    s: common_vendor.p({
      span: 8,
      offset: 1
    }),
    t: !$data.isRecorded
  }, !$data.isRecorded ? {
    v: common_vendor.p({
      type: "smallcircle",
      size: 30,
      color: "#33c46c"
    }),
    w: common_vendor.o(($event) => $options.stopRecord())
  } : {}, {
    x: $data.isRecorded
  }, $data.isRecorded ? {
    y: common_vendor.p({
      type: "sound",
      size: 30,
      color: "white"
    })
  } : {}, {
    z: common_vendor.p({
      span: 6
    }),
    A: $data.isRecorded
  }, $data.isRecorded ? {
    B: common_vendor.p({
      type: "checkbox",
      size: 30,
      color: "white"
    }),
    C: common_vendor.o(($event) => $options.evalRecord())
  } : {}, {
    D: common_vendor.p({
      span: 8
    })
  }) : {}) : {}, {
    E: common_vendor.n($options.changeQuestionBoardContainerStyle)
  });
}
var Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/Users/chenzhihang/Desktop/project/voice-uni/voice-uni/components/questionBoard.vue"]]);
wx.createComponent(Component);
