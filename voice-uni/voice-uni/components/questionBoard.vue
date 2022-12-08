<template>
	<view class="question-board-container">
		<view :class="changeQuestionBoardContainerStyle">
			<view class="expand-control-box" v-if="isShowExpand" @click="changeExpandIndex()">
				<uni-icons type="bottom" :size="20" />
			</view>

			<view class="question-box">
				{{examQuestion}}
			</view>

			<view class="control-container" v-if="isExpand">
				<view class="control-box" v-if="!isRecording">
					<uni-row>
						<uni-col :span="8" :offset="1">
							<view class="sound-container" @click="getNormalVoice()">
								<view class="sound-box">
									<uni-icons type="sound-filled" :size="30" color="white" />
								</view>
							</view>
						</uni-col>

						<uni-col :span="6">
							<view class="mic-container">
								<view class="mic-box" @click="startRecord()">
									<uni-icons type="mic-filled" :size="50" color="white" />
								</view>
							</view>
						</uni-col>

						<uni-col :span="8">
							<view class="score-container">
								<view class="score-box">
									{{score}}
								</view>
							</view>
						</uni-col>
					</uni-row>
				</view>

				<view class="voice-control-box" v-if="isRecording">
					<uni-row>
						<uni-col :span="8" :offset="1">
							<view class="clear-container">
								<view class="clear-box" v-if="isRecorded" @click="closeRecord()">
									<uni-icons type="clear" :size="30" color="grey" />
								</view>
							</view>
						</uni-col>

						<uni-col :span="6">
							<view class="voice-container">
								<view class="pause-voice-box" v-if="!isRecorded" @click="stopRecord()">
									<view class="circle-box"></view>
									<uni-icons type="smallcircle" :size="30" color="#33c46c" class="circle-icon" />
								</view>

								<view class="sound-voice-box" v-if="isRecorded">
									<uni-icons type="sound" :size="30" color="white" />
								</view>
							</view>
						</uni-col>

						<uni-col :span="8">
							<view class="check-container">
								<view class="check-box" v-if="isRecorded" @click="evalRecord()">
									<uni-icons type="checkbox" :size="30" color="white" />
								</view>
							</view>
						</uni-col>
					</uni-row>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isRecording: false,
				isRecorded: false,
				score: ''
			}
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
				}
			}
		},
		methods: {
			getNormalVoice() {
				uni.showLoading({
					title: '获取音频...',
					mask: true
				});
				
				setTimeout(() => {
					uni.hideLoading();
				}, 2000);
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

				uni.showLoading({
					title: '测评中...',
					mask: true
				});

				setTimeout(() => {
					uni.hideLoading();
					that.score = '90';

					if (!that.isEval) {
						that.changeIsEval(true);
					}
				}, 2000);
			},
			changeExpandIndex() {
				let that = this;

				that.$emit('changeExpandIndex', that.examQuestionIndex);
				that.changeIsEval(false);
			},
			changeIsEval(isEval) {
				this.$emit('changeIsEval', isEval);
			},
		}
	}
</script>

<style lang="less">
	.question-board-container {

		// padding: 80rpx 40rpx;
		// background-color: #F2F4F4;
		.questionBoardContainerDefault {
			border-top: 4rpx solid #E5E7E9;
			padding: 60rpx 40rpx;
			background-color: #F2F4F4;
		}

		.questionBoardContainerExpand {
			padding: 80rpx 40rpx;
		}

		.expand-control-box {
			text-align: center;
			margin: -32rpx 0 0 0
		}

		.question-box {
			font-size: 60rpx;
			text-align: center;
		}

		.control-container {

			.control-box {
				margin-top: 60rpx;
				height: 172rpx;

				.sound-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.sound-box {
						height: 100rpx;
						width: 100rpx;
						border: 2rpx solid #186A3B;
						border-radius: 50%;
						background-color: #186A3B;
						text-align: center;
						line-height: 100rpx;
					}
				}

				.mic-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.mic-box {
						height: 160rpx;
						width: 160rpx;
						border: 2rpx solid #33c46c;
						border-radius: 50%;
						background-color: #33c46c;
						text-align: center;
						line-height: 160rpx;
					}
				}

				.score-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.score-box {
						height: 100rpx;
						width: 100rpx;
						border: 2rpx solid #186A3B;
						border-radius: 50%;
						background-color: #186A3B;
						color: aliceblue;
						text-align: center;
						line-height: 100rpx;
					}
				}
			}

			.voice-control-box {
				margin-top: 60rpx;
				height: 172rpx;

				.clear-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.clear-box {
						height: 100rpx;
						width: 100rpx;
						border: 2rpx solid gainsboro;
						border-radius: 50%;
						background-color: gainsboro;
						text-align: center;
						line-height: 100rpx;
					}
				}

				.voice-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.pause-voice-box {
						position: relative;
						display: flex;
						justify-content: center;
						align-items: center;
						height: 80rpx;
						width: 80rpx;
						border-radius: 50%;

						.circle-box {
							position: absolute;
							top: -20rpx;
							bottom: -20rpx;
							left: -20rpx;
							right: -20rpx;
							background: conic-gradient(transparent 0, #33c46c 100%);
							border-radius: 50%;
							animation: recordRotate 1s linear infinite;

							&:after {
								content: "";
								width: 16rpx;
								height: 10rpx;
								position: absolute;
								top: 6rpx;
								left: 50%;
								border-radius: 50%;
								background: #33c46c;
								transform: translate(-50%, -6rpx);
							}

							&:before {
								content: "";
								position: absolute;
								top: 10rpx;
								bottom: 10rpx;
								left: 10rpx;
								right: 10rpx;
								background-color: #F0F2F5;
								border-radius: 50%;
							}

							@keyframes recordRotate {
								100% {
									transform: rotate(360deg);
								}
							}
						}

						.circle-icon {
							z-index: 10;
						}
					}

					.sound-voice-box {
						height: 100rpx;
						width: 100rpx;
						border: 2rpx solid #33c46c;
						border-radius: 50%;
						background-color: #33c46c;
						text-align: center;
						line-height: 100rpx;
					}
				}

				.check-container {
					height: 172rpx;
					display: flex;
					justify-content: center;
					align-items: center;

					.check-box {
						height: 100rpx;
						width: 100rpx;
						border: 2rpx solid #33c46c;
						border-radius: 50%;
						background-color: #33c46c;
						text-align: center;
						line-height: 100rpx;
					}
				}
			}
		}
	}
</style>
