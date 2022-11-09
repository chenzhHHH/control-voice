<template>
    <div style="position: fixed; top: 0rem; left: 0;right: 0;">
        <van-icon name="arrow-left" size="20px" style="margin: 10px;" @click='goBack()'/>

        <div style="margin: 10px 0 10px 10px;">
            <van-row>
                <van-col span="20">
                    <van-progress :percentage="(this.finishIndex/this.exam.examQuestionList.length)*100" :stroke-width="10" :show-pivot="false" color="green" style="line-height: 10px;"/>
                </van-col>

                <van-col span="2" offset="1" style="height:10px; line-height: 10px; font-size: 10px;">{{finishIndex}}/{{exam.examQuestionList.length}}</van-col>
            </van-row>
        </div>
    </div>

    <div style="margin-top: 5rem">
        <voice-input v-for="(examQuestion, index) in exam.examQuestionList" :key="examQuestion.id" :value="examQuestion" 
            :is-expand="index === expendIndex" :is-show-expand="index === expendIndex + 1 && isEval" :exam-question="examQuestion" :exam-question-index="index" :exam-group-id="exam.examGroupId" 
            @changeExpandIndex="changeExpandIndex" @changeIsEval="changeIsEval">
        </voice-input>
    </div>

    <div style="height: 5rem;"></div>

    <div style="text-align: center;position: fixed;left: 0;right: 0;bottom: 20px;border-top: 1px black;">
        <van-button round type="success" style="width:280px;" @click='goTo()' :disabled="this.finishIndex !== this.exam.examQuestionList.length">完成并获取报告</van-button>
    </div>
</template>

<script>
    import VoiceInput from '@/components/VoiceInput.vue'
    import { Progress } from 'vant';

    export default {
        components: {
            VoiceInput,
            [Progress.name]: Progress
        },
        data() {
            return {
                exam: {
                    examGroupId: '',
                    examQuestionList: []
                },
                expendIndex: 0,
                finishIndex: 0,
                isEval: false
            }
        },
        created() {
            this.initWordData(this.$route.params.category);
        },
        methods: {
            initWordData(category) {
                let that = this;

                let config = {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }

                that.$http.post('/learn/getExamQuestion', {category: category}, config).then((response) => {
                    that.exam = response.data.data;
                })
            },
            changeExpandIndex(data) {
                this.expendIndex = data;
            },
            changeIsEval(data) {
                this.isEval = data;
                if(data) {
                    this.finishIndex += 1;
                }
            },
            goBack() {
                this.$router.replace('/course');
            },
            goTo() {
                let that = this;
                this.$router.replace('/report/' + that.exam.examGroupId);
            }
        }
    };
</script>