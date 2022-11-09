<template>
    <van-icon name="arrow-left" size="20px" style="margin: 10px;" @click='goBack()'/>

    <van-row style="background-color:aliceblue; padding: 12px 0;">
        <van-col span="10" style="text-align: center;">
            <van-image width="100"  :src="require('../assets/leida.png')"/>
        </van-col>

        <van-col span="14" style="padding: 10px 50px 0 0">
            <div style="font-size: 18px;font-weight: bold;margin-bottom: 10px;">雷达基础理论</div>
            <div style="font-size: 10px;">学习目标：<br/>学习雷达管制基础理论，练习基础理论相关短语或单词的标准发音。</div>
        </van-col>
    </van-row>

    <div style="margin: 20px 0 0 20px;">
        <span style="font-size:14px">完成进度&nbsp;&nbsp;0/2</span>
    </div>

    <div>
        <van-row style="margin: 25px 0 0 20px;" @click='goToLearnWord()'>
            <van-col span="4">
                <div style="height:30px;width:30px;text-align:center;line-height:30px;border:1px solid #2ECC71;border-radius: 30px; background-color:#2ECC71; color:aliceblue; font-size:15px">1</div>
            </van-col>
            
            <van-col span="17">
                <div style="line-height:30px;">
                    <span style="font-size:18px;font-weight: bold;">基本词语</span>
                </div>
            </van-col>
            
            <van-col span="3" style="line-height:30px;">
                <van-icon name="arrow" size="20px" />
            </van-col>
        </van-row>

        <van-row style="margin: 25px 0 0 20px;" @click='goToLearnSentence()'>
            <van-col span="4">
                <div style="height:30px;width:30px;text-align:center;line-height:30px;border:1px solid #2ECC71;border-radius: 30px; background-color:#2ECC71; color:aliceblue; font-size:15px">2</div>
            </van-col>
            
            <van-col span="17">
                <div style="line-height:30px;">
                    <span style="font-size:18px;font-weight: bold;">对话</span>
                </div>
            </van-col>
            
            <van-col span="3" style="line-height:30px;">
                <van-icon name="arrow" size="20px" />
            </van-col>
        </van-row>
    </div>

    <div style="height: 5rem;"></div>

    <div style="text-align: center;position: fixed;left: 0;right: 0;bottom: 20px;border-top: 1px black;">
        <van-button round type="success" style="width:280px;" @click='goToLearnWord()'>开始学习</van-button>
    </div>
</template>

<script>
    export default {
    methods: {
        goBack() {
            this.$router.replace('/home');
        },
        goToLearnWord() {
            this.$router.replace('/learn/read_word');
        },
        goToLearnSentence() {
            this.$router.replace('/learn/read_sentence');
        }
    }
  };
</script>