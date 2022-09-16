package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.Equipment;
import top.showboard.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-30
 */
public interface NoticeService extends IService<Notice> {
    //新增一个新公告
    Notice addOneNotice(Notice notice, List<Integer> equipmentIDList);
    //返回所有公告列表分页
    Page<Notice> getAllNotice(Integer current, Integer size);
    //获取公告的设备列表
    List<Equipment> getNoticeEquipmentList(Integer noticeID);
    //编辑公告
    Notice updateNotice(Integer noticeID,Notice notice,List<Integer> equipmentIDList);
    //根据id删除公告
    Boolean deleteNoticeByID(Integer noticeID);
    //根据状态获取公告分页
    Page<Notice> getNoticeByState(Integer current,Integer size,String noticeState);
}
