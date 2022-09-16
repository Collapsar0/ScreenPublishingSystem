import request from '../utils/request'

// 新增用户
export const addOneuser = (account,password,organization,role,accountState,realName,email, telephone) => {
    return request({
        url: '/user/addOneUser',
        method: 'POST',
        params: {
            account,
            password,
            organization,
            role,
            accountState,
            realName,
            email, 
            telephone
        }
    })
}

// 返回用户分页列表
export const getuserList = (current,size) => {
    return request({
        url: '/user/getAllUser',
        method: 'GET',
        params: {
            current,
            size
        }
    })
}

// 修改用户信息
export const modifyInfo = (account,password,organization,role,accountState,realName,email,telephone,userid) => {
    return request({
        url: '/user/updateOneUser',
        method: 'PUT',
        params: {
            account,
            password,
            organization,
            role,
            accountState,
            realName,
            email, 
            telephone,
            userid
        }
    })
}

// 按ID删除用户
export const deleteUser = (userid) => {
    return request({
        url: '/user/deleteAccount',
        method: 'DELETE',
        params: {
            userid
        }
    })
}

// 多条件联合查询
export const unionSearch = (account,organization,role,accountState,current,size) => {
    return request({
        url: '/user/getUserBySearchUnion',
        method: 'GET',
        params: {
            account,
            organization,
            role,
            accountState,
            current,
            size
        }
    })
}

// 更改用户状态
export const changeUser = (accountStates,userid) => {
    return request({
        url: '/user/updateAccountStates',
        method: 'PUT',
        params: {
            accountStates,
            userid
        }
    })
}

//获取选择框列表
export const getdList = (fieldName) => {
    return request({
        url:'/user/getDistinctList',
        method: 'GET',
        params: {	
            fieldName
        }
    })
}