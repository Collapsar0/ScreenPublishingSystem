import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "./utils/request";
import * as echarts from 'echarts'
const app = createApp(App)
app.config.globalProperties.$baseUrl = axios.defaults.baseURL
app.config.globalProperties.$echarts = echarts
app.use(store).use(router).use(ElementPlus).mount('#app')


