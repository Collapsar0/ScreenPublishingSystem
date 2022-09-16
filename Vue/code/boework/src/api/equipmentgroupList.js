import request from '../utils/request'

// 添加设备组
export const addOneEquipmentgroup = (equipmentID,groupName,organization,description) => {
    return request({
        url: '/equipmentgroup/addOneEquipmentGroup',
        method: 'POST',
        params: {
            equipmentID,
            groupName,
            organization,
            description
        }
    })
}

// 返回设备分组分页列表
export const getEquipmentgroupList = (current,size) => {
    return request({
        url: '/equipmentgroup/getAllEquipmentGroups',
        method: 'GET',
        params: {
            current,
            size
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

// 修改设备信息
export const modifyInfo = (equipmentID,description,equipmentGroupID,groupName,organization) => {
    return request({
        url: '/equipmentgroup/updateEquipmentGroup',
        method: 'PUT',
        params: {
            equipmentID,
            description,
            equipmentGroupID,
            groupName,
            organization
        }
    })
}

// 按ID删除设备组
export const deleteEquipmentgroup = (equipmentGroupID) => {
    return request({
        url: '/equipmentgroup/deleteEquipmentGroup',
        method: 'DELETE',
        params: {
            equipmentGroupID
        }
    })
}

// 多条件联合查询
export const unionSearch = (current,groupName,organization,size) => {
    return request({
        url: '/equipmentgroup/getEquipmentGroupBySearchUnion',
        method: 'GET',
        params: {
            current,
            groupName, 
            organization,
            size
        }
    })
}

//获取选择框列表
export const getdList = (fieldName) => {
    return request({
        url:'/equipmentgroup/getDistinctList',
        method: 'GET',
        params: {	
            fieldName
        }
    })
}

// 多条件联合查询
export const emunionSearch = (SNNumber,current,equipmentName,equipmentState,groupName,macAddressWireless,nowPlan,organization,resolvingPower,size,systemUpdate) => {
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

// 修改设备的分组
export const modifyInfo2 = (equipmentID,equipmentGroupID) => {
    return request({
        url: '/equipmentgroup/updateOneEquipmentGroup',
        method: 'PUT',
        params: {
            equipmentID,
            equipmentGroupID,
        }
    })
}