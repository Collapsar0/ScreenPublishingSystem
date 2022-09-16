import request from '../utils/request'

// 返回设备分页列表
export const getMaterialList = (current,size) => {
    return request({
        url: '/material/getAllMaterial',
        method: 'GET',
        params: {
            current,
            size
        }
    })
}
// 返回设备分页列表
export const renameMaterial = (	materialID,newName) => {
    return request({
        url: '/material/updateMaterialName',
        method: 'PUT',
        params: {
            materialID,
            newName
        }
    })
}

// 添加新素材
export const addOneMaterial = (authorID,materialGroupID,materialName,materialSize,materialType,resolvingPower,url) => {
    return request({
        url: '/material/addOneMaterial',
        method: 'POST',
        params: {
            authorID,
            materialGroupID,
            materialName,
            materialSize,
            materialType,
            resolvingPower,
            url
        }
    })
}

// 多条件联合查询
export const unionSearch = (current,materialGroupName,materialName,size) => {
    return request({
        url: '/material/getMaterialBySearchUnion',
        method: 'GET',
        params: {  
            current,
            materialGroupName,
            materialName,
            size
        }
    })
}
// 按ID删除素材
export const deletematerial = (materialID) => {
    return request({
        url: '/material/deleteMaterialByID',
        method: 'DELETE',
        params: {
            materialID
        }
    })
}
