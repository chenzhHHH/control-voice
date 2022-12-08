<template>
	<view class="title-container">
		<view class="cn-title-container">
			顺利完成课程练习
		</view>

		<view class="en-title-container">
			CONGRATULATIONS
		</view>
	</view>

	<view class="report-result-container">
		<view class="logo-container">
			<image class="logo-box" src="../../static/trophy.png"></image>
		</view>

		<view class="result-container">
			<view class="total-container">
				<view class="total-text-box">
					此次课程练习总分
				</view>

				<view class="total-score-box">
					98
				</view>
			</view>

			<view class="result-detail-container">
				<view class="detail-box">
					<uni-row>
						<uni-col :span="6">
							<view class="detail-text-box">
								准确度
							</view>
						</uni-col>

						<uni-col :span="16">
							<view class="detail-score-box">
								<uni-rate :disabled="true" disabledColor="#ffc247" :value="4.3" />
							</view>
						</uni-col>
					</uni-row>
				</view>

				<view class="detail-box">
					<uni-row>
						<uni-col :span="6">
							<view class="detail-text-box">
								标准度
							</view>
						</uni-col>

						<uni-col :span="16">
							<view class="detail-score-box">
								<uni-rate :disabled="true" disabledColor="#ffc247" :value="4" />
							</view>
						</uni-col>
					</uni-row>
				</view>
			</view>
		</view>
	</view>

	<view class="space-container"></view>

	<view class="button-container">
		<button class="retry-button-box" type="default">重新练习</button>
		<button class="next-button-box" type="default">学习下一节</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		//#ifdef H5
		onBackPress(e) {
			uni.switchTab({
				url: "../index/index"
			})

			return true;
		},
		//#endif
		onLoad() {

		},
		//#ifdef MP-WEIXIN
		onUnload() {
			uni.switchTab({
				url: "../index/index"
			})
		},
		//#endif
		methods: {
			nextLearn() {
				uni.navigateTo({
					url: `../course/course`
				})
			}
		}
	}
</script>

<style lang="less">
	.title-container {
		padding: 60rpx 0 60rpx 0;

		.cn-title-container {
			text-align: center;
			font-size: 50rpx;
			font-weight: 400;
		}

		.en-title-container {
			text-align: center;
			font-size: 32rpx;
			font-weight: 200;
		}
	}

	.report-result-container {
		width: 580rpx;
		height: 726rpx;
		margin: 90rpx auto 0 auto;
		border-radius: 66rpx;
		background-color: #F2F4F4;
		position: relative;

		.logo-container {
			width: 174rpx;
			height: 174rpx;
			margin: auto;
			position: absolute;
			top: -110rpx;
			left: 0;
			right: 0;

			.logo-box {
				width: 174rpx;
				height: 174rpx;
				;
			}
		}

		.result-container {
			width: 464rpx;
			position: absolute;
			top: 116rpx;
			left: 58rpx;

			.total-container {
				width: 464rpx;
				height: 250rpx;
				border-radius: 44rpx;
				background-color: #A9DFBF;

				.total-text-box {
					position: absolute;
					top: 44rpx;
					left: 46rpx;
					font-size: 30rpx;
					color: #145A32;
					font-weight: 350
				}

				.total-score-box {
					position: absolute;
					top: 102rpx;
					left: 46rpx;
					font-size: 90rpx;
					color: #145A32;
					font-weight: 500;
				}
			}

			.result-detail-container {
				margin-top: 30rpx;

				.detail-box {
					padding: 15rpx 0 15rpx 30rpx;

					.detail-text-box {
						// line-height: 42rpx;
						font-size: 32rpx;
						font-weight: 200;
					}

					.detail-score-box {}
				}
			}
		}
	}

	.space-container {
		height: 200rpx;
	}

	.button-container {
		position: fixed;
		left: 50%;
		bottom: 40rpx;
		border-top: 2rpx;
		transform: translate(-50%, 0);
		display: flex;

		.retry-button-box {
			width: 300rpx;
			border-radius: 50rpx;
			background-color: #26ac28;
			color: white;
			font-size: 30rpx;
			margin-right: 40rpx;
		}

		.next-button-box {
			width: 300rpx;
			border-radius: 50rpx;
			background-color: #26ac28;
			color: white;
			font-size: 30rpx;
		}
	}
</style>
