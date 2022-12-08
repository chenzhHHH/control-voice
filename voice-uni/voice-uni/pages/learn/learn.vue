<template>
	<view class="learn-progress-container">
		<uni-row>
			<uni-col :span="21">
				<view>
					<progress :percent="(this.finishIndex/this.words.length)*100" border-radius="30" stroke-width="5"
						font-size="10" activeColor="#09BB07" active-mode="forwards" class="progress-box" />
				</view>
			</uni-col>

			<uni-col :span="1" offset="1">
				<view class="progress-text-box">
					{{finishIndex}}/{{words.length}}
				</view>
			</uni-col>
		</uni-row>
	</view>

	<view>
		<question-board v-for="(word, index) in words" :examQuestion="word" :isExpand="index === expendIndex"
			:isShowExpand="index === expendIndex + 1 && isEval" :examQuestionIndex="index" :isEval="isEval"
			@changeExpandIndex="changeExpandIndex" @changeIsEval="changeIsEval">
		</question-board>
	</view>

	<view class="space-container"></view>

	<view class="button-container">
		<button :class="changeButtonBoxStyle" type="primary" :disabled="this.finishIndex !== this.words.length"
			@click="goTo()">
			完成并获取报告
		</button>
	</view>
</template>

<script>
	import questionBoard from '../../components/questionBoard.vue'

	export default {
		components: {
			questionBoard
		},
		data() {
			return {
				words: ['Alpha', 'Bravo', 'Charlie', 'Delta', 'Echo'],
				expendIndex: 0,
				finishIndex: 0,
				isEval: false
			}
		},
		onLoad() {

		},
		computed: {
			changeButtonBoxStyle() {
				let that = this;

				return {
					buttonBoxDisabled: that.finishIndex !== that.words.length,
					buttonBoxNotDisabled: that.finishIndex === that.words.length
				}
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
				uni.navigateTo({
					url: `../report/report`
				})
			}
		}
	}
</script>

<style lang="less">
	.learn-progress-container {
		margin: 20rpx 20rpx 20rpx 50rpx;

		.progress-box {
			line-height: 20rpx;
		}

		.progress-text-box {
			height: 20rpx;
			line-height: 20rpx;
			font-size: 20rpx;
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

		.buttonBoxDisabled {
			width: 560rpx;
			border-radius: 50rpx;
			background-color: #8bd8aa;
			color: white;
			font-size: 30rpx;
		}

		.buttonBoxNotDisabled {
			width: 560rpx;
			border-radius: 50rpx;
			background-color: #26ac28;
			color: white;
			font-size: 30rpx;
		}
	}
</style>
