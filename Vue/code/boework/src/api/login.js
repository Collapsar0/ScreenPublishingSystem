import request from '../utils/request'
// 登录判断
export const judgePassword = (account,password) => {
    return request({
        url: '/user/judgePassword',
        method: 'GET',
        params: {
            account,
            password
        }
    })
}