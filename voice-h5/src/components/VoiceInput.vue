<template>
    <div :class="changeWordDivStyle">
        <div style="text-align:center;">
            <div v-if="isShowExpand" style="margin: -0.8rem 0 0.8rem 0" @click="changeExpandIndex">
                <van-icon name="arrow-down" :size="20" color="#839192"/>
            </div>
            <span style="font-size:1.5rem;">{{examQuestion.content}}</span>
        </div>

        <div style="margin-top: 30px;" v-if="isExpand">
            <van-row>
                <van-col span="8" offset="1" @click.prevent="playRead">
                    <div style="text-align:center; line-height: 80px;">
                        <van-image width="70px" height="70px" it="contain" :src="require('../assets/begin.png')" style="vertical-align: middle;"/>
                    </div>
                </van-col>

                <van-col span="6" v-if="!isRefreshScore">
                    <div @touchstart.prevent="touchstart" @touchend.prevent="touchend" @touchmove.prevent="touchmove" style="height:80px; width:80px; position: absolute; left: 50%; transform:translate(-50%, 0); z-index: 8;"></div>
                    <div :class="changeVoiceDivStyle"></div>
                    <div style="text-align:center;">
                        <van-image width="80px" height="80px" it="contain" :src="require('../assets/record.png')" style="display: inline-block;"/>
                    </div>
                    <!-- <van-button type="primary" :onclick="touchstart">start</van-button>
                    <van-button type="success" :onclick="touchend">end</van-button> -->
                </van-col>
                <van-col span="6" v-if="isRefreshScore">
                    <div style="height:80px; width:80px; position: absolute; left: 50%; transform:translate(-50%, 0); z-index: 8;"></div>
                    <div style="height: 3.5rem;width: 2.7rem;position: absolute;left: 50%;transform:translate(-50%, 20%); background-color: grey;"></div>
                    <div style="text-align:center;">
                        <van-image width="80px" height="80px" it="contain" :src="require('../assets/record.png')" style="display: inline-block;"/>
                    </div>
                </van-col>

                <van-col span="8" style="display: flex;justify-content: center;align-items: center;" @click.prevent="playRecorder">
                    <div style="height:50px;width:50px;border:1px solid #186A3B;border-radius: 30px;background-color:#186A3B;color:aliceblue;text-align:center;line-height:50px;">{{score}}</div>
                </van-col>
            </van-row>

            <van-overlay :show="isShowEvalOverlay" z-index="16">
                <div class="overlay-wrapper" @click.stop>
                    <div class="overlay-block">
                        <van-loading size="3rem" color="#85929E">测评中...</van-loading>
                    </div>
                </div>
            </van-overlay>
        </div>
    </div>
</template>

<script>
    import Recorder from 'js-audio-recorder'
    import { Loading, Overlay } from 'vant';
    // let Base64 = require('js-base64').Base64;

    export default {
        components: {
            [Loading.name]: Loading,
            [Overlay.name]: Overlay
        },
        data() {
            return {
                blackBoxSpeak: false,
                startY: '',
                recorder: null,
                isShowvVoiceAnimation: false,
                score: '',
                isRefreshScore: false,
                getScoreST: null,
                isShowEvalOverlay: false
            }
        },
        props: {
            isExpand: Boolean,
            isShowExpand: Boolean,
            examQuestion: Object,
            examQuestionIndex: String,
            examGroupId: String
        },
        mounted() {
            this.getPermission();

            this.initRecorder();
        },
        created() {
            
        },
        computed: {
            changeVoiceDivStyle: function(){
                let that = this;
                return {
                    voiceBox: true,
                    voiceAnimation: that.isShowvVoiceAnimation
                }
            },
            changeWordDivStyle: function(){
                let that = this;
                return {
                    wordBoxDefault: !that.isExpand,
                    wordBoxExpand: that.isExpand
                }
            }
        },
        methods: {
            getPermission(){
                Recorder.getPermission().then(() => {
                    // this.$notify('录音给权限了');
                    // console.log('录音给权限了');
                }, (error) => {
                    this.$notify(`${error.name} : ${error.message}`);
                    console.log(`${error.name} : ${error.message}`);
                });
            },
            initRecorder() {
                this.recorder = new Recorder({
                    sampleBits: 16,
                    sampleRate: 16000,
                    numChannels: 1
                })
            },
            startRecorder() {
                this.recorder.start().then(() => {
                    
                }, (error) => {
                    console.log(`${error.name} : ${error.message}`);
                });
            },
            stopRecorder() {
                this.recorder.stop();
            },
            playRecorder() {
                this.recorder.play();
            },
            playRead() {
                let that = this;

                let formData = {
                    content: that.examQuestion.content
                }

                let config = {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }

                that.$http.post('/learn/transferToVoice', formData, config).then((response) => {
                    let dataTemp = response.data.data;
                    let readAudio = new Audio(`data:audio/x-wav;base64, ` + dataTemp);
                    readAudio.play();
                })
            },
            getRecorder(){
                let map = new Map();
                map.set('duration', this.recorder.duration);
                map.set('fileSize', this.recorder.fileSize);
                map.set('PCMBlob', this.recorder.getPCMBlob());
                map.set('WAVBlob', this.recorder.getWAVBlob());
                return map;
            },
            touchstart(e) {
                this.startY = e.changedTouches[0].clientY;

                this.startRecorder();
                this.isShowvVoiceAnimation = true;
            },
            touchend() {
                let that = this;

                that.$notify.clear();

                that.stopRecorder();
                that.isShowvVoiceAnimation = false;

                if(!that.blackBoxSpeak) {
                    let recordResult = that.getRecorder();
                    console.log('松开>>>', recordResult);
                    
                    let formData = {
                        examGroupId: that.examGroupId,
                        examQuestionId: that.examQuestion.id,
                        category: that.examQuestion.category,
                        voiceFile: recordResult.get('PCMBlob')
                    }
                    let config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                    that.$http.post('/learn/evalVoice', formData, config).then((response) => {
                        console.log(response.data);
                        that.getScore(that.examGroupId, that.examQuestion.content);
                        that.isShowEvalOverlay = true;
                    })
                }
            },
            touchmove(e) {
                const endY = e.changedTouches[0].clientY;
                this.blackBoxSpeak = this.startY > endY;
                if(this.blackBoxSpeak) {
                    this.$notify({type: 'warning', message: '向上松开取消录音'});
                } else {
                    this.$notify.clear();
                }
            },
            changeExpandIndex() {
                let that = this;
                that.$emit('changeExpandIndex', that.examQuestionIndex);

                this.changeIsEval(false);
            },
            changeIsEval(isEval) {
                this.$emit('changeIsEval', isEval);
            },
            getScore(examGroupId, content) {
                let that = this;

                let formData = {
                    examGroupId: examGroupId,
                    examQuestionId: that.examQuestion.id,
                }

                let config = {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }
                that.$http.post('/learn/getScore', formData, config).then((response) => {
                    if(response.data.data === "-1"){
                        that.getScoreST = setTimeout(() => {
                            clearTimeout(that.getScoreST)
                            that.getScore(examGroupId, content)
                        }, 1000)
                    } else {
                        that.score = response.data.data;
                        that.isRefreshScore = true;
                        that.isShowEvalOverlay = false;
                        that.changeIsEval(true);
                    }
                })
            }
        }
    }
</script>

<style>
    .wordBoxDefault {
        border-top: 0.1rem solid #E5E7E9; background-color: #F2F3F4; padding: 1.8rem 1rem;
    }

    .wordBoxExpand {
        padding: 2rem 1rem;
    }

    .voiceBox {
        height: 3.5rem;
        width: 2.7rem;
        position: absolute;
        left: 50%;
        transform:translate(-50%, 20%);
    }
    
    .voiceAnimation {
		animation: voiceMove 1.1s infinite;
	}

    @keyframes voiceMove {
        0% {
            background: linear-gradient(to top, #1E8449 0%, white 50%);
        }
        10% {
            background: linear-gradient(to top, #1E8449 20%, white 50%);
        }
        20% {
            background: linear-gradient(to top, #1E8449 40%, white 50%);
        }
        30% {
            background: linear-gradient(to top, #1E8449 60%, white 50%);
        }
        40% {
            background: linear-gradient(to top, #1E8449 80%, white 50%);
        }
        50% {
            background: linear-gradient(to top, #1E8449 100%, white 50%);
        }
        60% {
            background: linear-gradient(to top, #1E8449 80%, white 50%);
        }
        70% {
            background: linear-gradient(to top, #1E8449 60%, white 50%);
        }
        80% {
            background: linear-gradient(to top, #1E8449 40%, white 50%);
        }
        90% {
            background: linear-gradient(to top, #1E8449 20%, white 50%);
        }
        100% {
            background: linear-gradient(to top, #1E8449 0%, white 50%);
        }
    }

    .overlay-wrapper {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }

    .overlay-block {
        width: 8rem;
        height: 8rem;
        border-radius: 1.5rem;
        background-color: #F2F4F4;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>