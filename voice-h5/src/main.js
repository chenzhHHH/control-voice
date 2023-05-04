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
    Notify,
    Form,
    Field,
    CellGroup
} from 'vant';

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:8080';

axios.interceptors.request.use(config => {
    config.headers.Authorization = "Bearer " + localStorage.getItem('token')

    return config
});

axios.interceptors.response.use(function (response) {
    if (response.data.code === "4021") {
        Notify({ type: "warning", message: "登陆超时，请重新登录" });
        localStorage.clear();
        Router.replace("/login");
    } else if (response.data.code === "4031") {
        Notify({ type: "warning", message: "权限认证失败" });
    }

    return response
});

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
    .use(Form)
    .use(Field)
    .use(CellGroup)

app.mount('#app')
