<template>
	<view class="course-introduce-container">
		<uni-row>
			<uni-col :span="10">
				<view class="introduce-image-box">
					<image class="image-box" src="../../static/leida.png"></image>
				</view>
			</uni-col>

			<uni-col :span="14">
				<view class="title-text">
					雷达基础理论
				</view>

				<view>
					学习目标：<br />学习雷达管制基础理论，练习基础理论相关短语或单词的标准发音。
				</view>
			</uni-col>
		</uni-row>
	</view>

	<view class="course-container">
		<uni-section title="学习任务" type="line">
			<view class="course-progress-box">
				<progress :percent="50" show-info border-radius="30" stroke-width="5" font-size="10"
					activeColor="#09BB07" />
			</view>

			<view class="course-box" @click="goTo('word')">
				<uni-row>
					<uni-col :span="4">
						<view class="course-index-box">
							1
						</view>
					</uni-col>

					<uni-col :span="17">
						<view class="course-name-box">
							基本词语
						</view>
					</uni-col>

					<uni-col :span="3">
						<view class="course-icon-box">
							<uni-icons type="right" :size="20" />
						</view>
					</uni-col>
				</uni-row>
			</view>

			<view class="course-box" @click="goTo('sentence')">
				<uni-row>
					<uni-col :span="4">
						<view class="course-index-box">
							2
						</view>
					</uni-col>

					<uni-col :span="17">
						<view class="course-name-box">
							对话
						</view>
					</uni-col>

					<uni-col :span="3">
						<view class="course-icon-box">
							<uni-icons type="right" :size="20" />
						</view>
					</uni-col>
				</uni-row>
			</view>
		</uni-section>
	</view>

	<view class="space-container"></view>

	<view class="button-container">
		<button class="button-box" type="default" @click="goTo()">开始学习</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		onLoad() {

		},
		methods: {
			goTo() {
				uni.navigateTo({
					url: `../learn/learn`
				})
			}
		}
	}
</script>

<style lang="less">
	.course-introduce-container {
		background-color: aliceblue;
		padding: 12px 0;
		font-size: 30rpx;

		.introduce-image-box {
			text-align: center;

			.image-box {
				width: 200rpx;
				height: 350rpx;
			}
		}

		.title-text {
			font-weight: bold;
			margin-bottom: 20rpx;
		}
	}

	.course-container {
		.course-progress-box {
			margin: 10rpx 30rpx 0 30rpx;
		}

		.course-box {
			margin: 25px 0 0 20px;

			.course-index-box {
				height: 60rpx;
				width: 60rpx;
				text-align: center;
				line-height: 60rpx;
				border: 1px solid #2ECC71;
				border-radius: 60rpx;
				background-color: #2ECC71;
				color: aliceblue;
				font-size: 30rpx
			}

			.course-name-box {
				line-height: 60rpx;
				font-size: 36rpx;
				font-weight: bold;
			}

			.course-icon-box {
				line-height: 60rpx;
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

		.button-box {
			width: 560rpx;
			border-radius: 50rpx;
			background-color: #26ac28;
			color: white;
			font-size: 30rpx;
		}
	}
</style>
