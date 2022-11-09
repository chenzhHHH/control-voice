import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import Router from './router'
import Store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'

import { 
        ConfigProvider,
        Button,
        Tabbar, 
        TabbarItem,
        Col,
        Row,
        Image as VanImage,
        Icon,
        Notify
    } from 'vant';

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080';

app.use(Store).use(Router).use(VueAxios, axios)

app
    .use(ConfigProvider)
    .use(Button)
    .use(Tabbar)
    .use(TabbarItem)
    .use(Col)
    .use(Row)
    .use(VanImage)
    .use(Icon)
    .use(Notify)

app.mount('#app')
