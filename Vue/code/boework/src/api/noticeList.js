import request from '../utils/request'
// 返回设备分页列表
export const getNoticeList = (current,size) => {
    return request({
        url: '/notice/getAllNotice',
        method: 'GET',
        params: {
            current,
            size
        }
    })
}
// 获得设备列表
export const getem = (noticeID) => {
    return request({
        url: '/notice/getNoticeEquipmentList',
        method: 'GET',
        params: {
            noticeID
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
// 新建公告
export const addNotice = (equipmentIDList,authorID,backgroundColor,backgroundHeight,endTime,noticeContent,playbackMode,playbackSpeed,startTime,textColor,textPosition,textSize) => {
    return request({
        url: '/notice/addOneNotice',
        method: 'POST',
        params: {
            equipmentIDList,
            authorID,
            backgroundColor,
            backgroundHeight,
            endTime,
            noticeContent,
            playbackMode,
            playbackSpeed,
            startTime,
            textColor,
            textPosition,
            textSize
        }
    })
}
// 编辑公告
export const updateNotice = (equipmentIDList,authorID,backgroundColor,backgroundHeight,endTime,noticeContent,noticeID,playbackMode,playbackSpeed,startTime,textColor,textPosition,textSize) => {
    return request({
        url: '/notice/updateNotice',
        method: 'PUT',
        params: {
            equipmentIDList,
            authorID,
            backgroundColor,
            backgroundHeight,
            endTime,
            noticeContent,
            noticeID,
            playbackMode,
            playbackSpeed,
            startTime,
            textColor,
            textPosition,
            textSize
        }
    })
}
// 按ID删除公告
export const searchNotice = (current,noticeState,size    ) => {
    return request({
        url: '/notice/getNoticeByState',
        method: 'GET',
        params: {
            current,
            noticeState,
            size
        }
    })
}
