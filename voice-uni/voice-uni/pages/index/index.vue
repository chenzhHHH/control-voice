<template>
	<view class="search-container">
		<uni-search-bar placeholder="请输入搜索关键词" :radius="30"></uni-search-bar>
	</view>

	<view class="swiper-container">
		<swiper class="swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :circular="true">
			<swiper-item>
				<image class="swiper-image" src="../../static/home1.png"></image>
			</swiper-item>

			<swiper-item>
				<image class="swiper-image" src="../../static/home1.png"></image>
			</swiper-item>

			<swiper-item>
				<image class="swiper-image" src="../../static/home1.png"></image>
			</swiper-item>
		</swiper>
	</view>

	<view class="container">
		<uni-section title="轻松练" type="line">
			<uni-row>
				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box" @click='goTo()'>
							<uni-icons type="image" :size="30" />
							<text class="item-text">雷达基础理论</text>
						</view>
					</view>
				</uni-col>

				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="image" :size="30" />
							<text class="item-text">机场管制</text>
						</view>
					</view>
				</uni-col>

				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="image" :size="30" />
							<text class="item-text">进近管制</text>
						</view>
					</view>
				</uni-col>
			</uni-row>

			<uni-row>
				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="image" :size="30" />
							<text class="item-text">区域管制</text>
						</view>
					</view>
				</uni-col>

				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="image" :size="30" />
							<text class="item-text">特情</text>
						</view>
					</view>
				</uni-col>

				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="image" :size="30" />
							<text class="item-text">管制英语</text>
						</view>
					</view>
				</uni-col>
			</uni-row>

			<uni-row>
				<uni-col :span="8">
					<view class="container-item">
						<view class="item-box">
							<uni-icons type="plus" :size="30" />
							<text class="item-text">自定义添加</text>
						</view>
					</view>
				</uni-col>
			</uni-row>
		</uni-section>
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
					url: `../course/course`
				})
			}
		}
	}
</script>

<style lang="less">
	.search-container {
		background-color: #54bf8e;
	}

	.swiper-container {
		padding: 10rpx 0 30rpx 0;
		background-color: #54bf8e;


		.swiper {
			width: 600rpx;
			height: 400rpx;
			text-align: center;
			margin: auto;

			.swiper-image {
				width: 600rpx;
				height: 400rpx;
			}
		}
	}

	.container {
		.container-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			padding: 20rpx 0;

			.item-box {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;

				width: 200rpx;
				height: 200rpx;
				border: 1rpx solid #c0c0c0;
				border-radius: 30rpx;

				.item-text {
					margin-top: 10rpx;
				}
			}
		}
	}
</style>
