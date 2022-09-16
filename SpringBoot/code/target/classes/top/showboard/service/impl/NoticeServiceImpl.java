package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import top.showboard.entity.Equipment;
import top.showboard.entity.Notice;
import top.showboard.mapper.EquipmentMapper;
import top.showboard.mapper.NoticeMapper;
import top.showboard.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-30
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    EquipmentMapper equipmentMapper;

    /**
     * 新增一个新公告
     * @param notice
     * @return
     */
    @Override
    public Notice addOneNotice(Notice notice,List<Integer> equipmentIDList){
        notice.setNoticestate("发布中");
        notice.setEquipmentIDList(equipmentIDList.toString());
        noticeMapper.insert(notice);
        return notice;
    }

    /**
     * 编辑公告
     * @param noticeID
     * @param notice
     * @param equipmentIDList
     * @return
     */
    @Override
    public Notice updateNotice(Integer noticeID,Notice notice,List<Integer> equipmentIDList){
        notice.setNoticeid(noticeID);
        notice.setNoticestate("发布中");
        notice.setEquipmentIDList(equipmentIDList.toString());
        noticeMapper.updateById(notice);
        return noticeMapper.selectById(noticeID);
    }

    /**
     * 返回所有公告列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<Notice> getAllNotice(Integer current,Integer size){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("noticeID");
        Page<Notice> noticePage = new Page<>(current,size);
        return noticeMapper.selectPage(noticePage,queryWrapper);
    }

    /**
     * 获取公告的设备列表
     * @param noticeID
     * @return
     */
    @Override
    public List<Equipment> getNoticeEquipmentList(Integer noticeID){
        List<Integer> equipmentIDList = new ArrayList<>();
        List<Equipment> equipmentList = new ArrayList<>();
        String equipmentIDString = noticeMapper.selectById(noticeID).getEquipmentIDList();
        //获取List中的值
//        System.out.println(equipmentIDString);
        equipmentIDString = equipmentIDString.replaceAll(" ","");
        equipmentIDString = equipmentIDString.substring(1,equipmentIDString.length()-1);
        String[] result = equipmentIDString.split(",");
        for (String str: result) {
            if (str!=null){
//                System.out.println(str);
                equipmentIDList.add(Integer.valueOf(str));
            }
        }
        //将List中的值导入并回传
        for (Integer integer:equipmentIDList) {
            equipmentList.add(equipmentMapper.selectById(integer));
        }
        return equipmentList;
    }

    /**
     * 根据id删除公告
     * @param noticeID
     * @return
     */
    @Override
    public Boolean deleteNoticeByID(Integer noticeID){
        return noticeMapper.deleteById(noticeID) > 0;
    }

    /**
     * 根据状态获取公告分页
     * @param current
     * @param size
     * @param noticeState
     * @return
     */
    @Override
    public Page<Notice> getNoticeByState(Integer current,Integer size,String noticeState){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("noticeState",noticeState);
        return noticeMapper.selectPage(new Page<>(current,size),queryWrapper);
    }
}
