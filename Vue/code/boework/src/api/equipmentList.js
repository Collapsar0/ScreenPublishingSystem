import request from '../utils/request'

// 添加新设备
export const addOneEquipment = (SNNumber,equipmentName,macAddress,organizationId) => {
    return request({
        url: '/equipment/addOneEquipment',
        method: 'POST',
        params: {
            SNNumber,
            equipmentName,
            macAddress,
            organizationId
        }
    })
}

// 返回设备分页列表
export const getEquipmentList = (current,size) => {
    return request({
        url: '/equipment/getAllEquipment',
        method: 'GET',
        params: {
            current,
            size
        }
    })
}

// 返回设备分页列表
export const getEquipmentGroupByID = (equipmentID) => {
    return request({
        url: '/equipmentgroup/getEquipmentGroupByID',
        method: 'GET',
        params: {
            equipmentID
        }
    })
}

// 修改设备信息
export const modifyInfo = (equipmentGroup,equipmentID,equipmentName) => {
    return request({
        url: '/equipment/updateEquipment',
        method: 'PUT',
        params: {
            equipmentGroup,
            equipmentID,
            equipmentName
        }
    })
}

// 按ID删除设备
export const deleteEquipment = (equipmentID) => {
    return request({
        url: '/equipment/deleteEquipment',
        method: 'DELETE',
        params: {
            equipmentID
        }
    })
}

// 多条件联合查询
export const unionSearch = (SNNumber,current,equipmentName,equipmentState,groupName,macAddressWireless,nowPlan,organization,resolvingPower,size,systemUpdate) => {
    return request({
        url: '/equipment/getEquipmentBySearchUnion',
        method: 'GET',
        params: {
            SNNumber,  
            current,
            equipmentName,
            equipmentState,
            groupName, 
            macAddressWireless,
            nowPlan,
            organization,
            resolvingPower,
            size,
            systemUpdate
        }
    })
}

//获取选择框列表
export const getdList = (fieldName) => {
    return request({
        url:'/equipment/getDistinctList',
        method: 'GET',
        params: {	
            fieldName
        }
    })
}
