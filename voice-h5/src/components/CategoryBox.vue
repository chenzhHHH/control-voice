<template>
    <div class="containerBox" :style="{backgroundColor: activeBackgroundColor}">
        <div>雷达基础理论 {{testColor}}</div>
        <div style="margin-left:0.5rem"><van-image width="3rem" height="3rem" it="contain" :src="iconSrc"/></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                
            }
        },
        props: {
            iconSrc: String,
            activeBackgroundColor: String
        },
        methods: {

        }
    }
</script>

<style>
    .containerBox {
        width: 10.5rem;
        height: 5.5rem;
        border-radius: 0.5rem;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 1rem;
        color: white;
    }
</style>