import request from '../utils/request'

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

// 添加新节目
export const addOneProgram = (programContent,programData,programName,resolvingPower,userID) => {
    return request({
        url: '/program/addOneProgram',
        method: 'POST',
        params: {
            programContent,
            programData,
            programName,
            resolvingPower,
            userID
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
export const getProgramBySearchUnion = (current,programName,programState,resolvingPower,size) => {
    return request({
        url: '/program/getProgramBySearchUnion',
        method: 'GET',
        params: {
            current,
            programName,
            programState,
            resolvingPower,
            size
        }
    })
}

// 编辑节目
export const editorProgramContent = (programContent,programData,programID,programName,resolvingPower) => {
    return request({
        url: '/program/updateProgram',
        method: 'PUT',
        params: {
            programContent,
            programData,
            programID,
            programName,
            resolvingPower
        }
    })
}

// 删除节目
export const deleteProgramByID = (programID) => {
    return request({
        url: '/program/deleteProgramByID',
        method: 'DELETE',
        params: {
            programID
        }
    })
}

// 获取素材URL
export const getUrlById = (materialID) => {
    return request({
        url: '/material/getMaterialURLByID',
        method: 'GET',
        params: {
            materialID
        }
    })
}

// 通过ID获取素材内容
export const getMaterialById = (materialID) => {
    return request({
        url: '/material/getMaterialByID',
        method: 'GET',
        params: {
            materialID
        }
    })
}
