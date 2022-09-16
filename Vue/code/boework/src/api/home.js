import request from '../utils/request'
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
// 获取节目列表
export const getProgramList = (current,size) => {
    return request({
        url: '/program/programService',
        method: 'GET',
        params: {
            current,
            size
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
// 返回设备分页列表
export const getMaterialSize = () => {
    return request({
        url: '/material/getAllMaterialSize',
        method: 'GET',
        params: {
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