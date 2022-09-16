import axios from 'axios'
import {ElMessage} from "element-plus";

// //本地URL
// axios.defaults.baseURL = 'http://localhost:8081'

//服务端URL
axios.defaults.baseURL = 'http://47.99.58.44:8081'

// //服务端URL
// axios.defaults.baseURL = 'http://10.66.10.145:8082'

//post请求头
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
// 允许跨域
axios.defaults.headers.post["Access-Control-Allow-Origin-Type"] = "*";

//超时时间
axios.defaults.timeout = 5000
//跨域凭证
axios.defaults.withCredentials = false
//响应和请求 拦截器配置


// 请求拦截器
axios.interceptors.request.use(config => {
    return config
})

// 响应拦截器
axios.interceptors.response.use(
    response => {
        // console.log("response ->" + response)
        let res = response.data
        if (res.code === 200) {
            return response
        } else {
            // Element.Message.error(!res.message ? '系统异常' : res.message)
            ElMessage({
                message: !res.message ? '系统异常' : res.message,
                type: 'error',
            })
            return Promise.reject(response.data.message)
        }
    },
    error => {
        console.log("下面是错误返回")
        console.log(error.response)
        if (error.response.data) {
            error.massage = error.response.data.message
        }
        // if (error.response.status === 401 || error.response.status === 403) {
        //     router.push("/login")
        // }
        //Element.Message.error(error.massage, {duration: 3000})
        ElMessage({
            message: error.massage,
            type: 'error',
        })

        return Promise.reject(error)
    }
)

export default axios
