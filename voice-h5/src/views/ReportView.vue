<template>
    <div>
        <van-icon name="wap-home-o" size="20px" style="margin: 10px;" @click='goBack()'/>
    </div>

    <div style="padding: 3rem 0 3rem 0;">
        <div style="text-align: center;">
            <span style="font-size:1.7rem; font-weight:400;">顺利完成课程练习</span>
        </div>
        <div style="text-align: center;">
            <span style="font-size:1.1rem; font-weight:200;">CONGRATULATIONS</span>
        </div>
    </div>

    

    <div style="width: 20rem; height: 25rem; margin: 3.8rem auto 0 auto; border-radius: 1.5rem; background-color: #F2F4F4; position: relative;">
        <div style="margin: auto; width:6rem; height:6rem; position: absolute; top: -3.8rem; left: 0; right: 0;">
            <van-image width="6rem" height="6rem" it="contain" :src="require('../assets/trophy.png')"/>
        </div>
        
        <div style="position: absolute; top: 4rem; left: 2rem; width:16rem;">
            <div style="width:16rem; height:9rem; border-radius: 1.5rem; background-color: #A9DFBF;">
                <div style="position: absolute; top: 1.5rem; left: 1.2rem; font-size: 1rem; color:#145A32; font-weight: 350">此次课程练习总分</div>
                <div style="position: absolute; top: 3.5rem; left: 1.2rem; font-size: 3rem; color:#145A32; font-weight: 500">{{report.totalScore}}</div>
            </div>

            <div style="margin-top:1rem">
                <van-row style="padding: 0.5rem 0 0.5rem 1rem">
                    <van-col span="6">
                        <span style="line-height:1.6rem; font-weight: 200;">准确度</span>
                    </van-col>
                    <van-col span="16">
                        <van-rate v-model="report.accuracyScoreValue" :size="25" color="#ffd21e" void-icon="star" readonly allow-half/>
                    </van-col>
                </van-row>

                <van-row style="padding: 0.5rem 0 0.5rem 1rem" v-if="report.category === 'read_word'">
                    <van-col span="6">
                        <span style="line-height:1.6rem; font-weight: 200;">标准度</span>
                    </van-col>
                    <van-col span="16">
                        <van-rate v-model="report.standardScoreValue" :size="25" color="#ffd21e" void-icon="star" readonly allow-half/>
                    </van-col>
                </van-row>

                <van-row style="padding: 0.5rem 0 0.5rem 1rem" v-if="report.category === 'read_sentence'">
                    <van-col span="6">
                        <span style="line-height:1.6rem; font-weight: 200;">流利度</span>
                    </van-col>
                    <van-col span="16">
                        <van-rate v-model="report.fluencyScoreValue" :size="25" color="#ffd21e" void-icon="star" readonly allow-half/>
                    </van-col>
                </van-row>
            </div>
        </div>
    </div>

    <div style="height: 5rem;"></div>

    <div style="text-align: center;position: fixed;left: 0;right: 0; bottom: 20px; border-top: 1px black;">
        <van-button round color="#73C6B6" style="margin-right:20px;width:150px;">重新练习</van-button>
        <van-button round color="#2ECC71" style="width:150px;">学习下一节</van-button>
    </div>
</template>

<script>
    import { Rate } from 'vant';

    export default {
        components: {
            [Rate.name]: Rate
        },
        created() {
            this.getScore(this.$route.params.examGroupId);
        },
        data() {
            return {
                report: {}
            }
        },
        methods: {
            getScore(examGroupId) {
                let that = this;

                let config = {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }
                that.$http.post('/learn/getReport', {examGroupId: examGroupId}, config).then((response) => {
                    that.report = response.data.data;
                })
            },
            goBack() {
                this.$router.replace('/');
            }
        }
    };
</script>