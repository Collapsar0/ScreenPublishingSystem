package top.showboard.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Notice;
import top.showboard.mapper.EquipmentMapper;
import top.showboard.mapper.NoticeMapper;
import top.showboard.mapper.UserMapper;
import top.showboard.response.Result;
import top.showboard.service.NoticeService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/notice")
@Api(value = "提供公告相关的接口",tags = "公告管理")
public class NoticeController {
    @Resource
    NoticeService noticeService;
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    EquipmentMapper equipmentMapper;

    @ApiOperation("新增一个新公告")
    @PostMapping("/addOneNotice")
    public Result addOneNotice(String noticeContent, Integer authorID, String playbackMode, String startTime, String endTime,
                               String playbackSpeed, String textPosition, String textColor, Integer textSize,
                               String backgroundColor, Integer backgroundHeight,
                               @RequestParam(value ="equipmentIDList") List<Integer> equipmentIDList) throws ParseException {
        if (userMapper.selectById(authorID)==null){
            return Result.error().message("作者id不存在!");
        }
        if (textColor.equals(backgroundColor)){
            return Result.error().message("文字和背景色不可以一致!");
        }
        for (Integer integer:equipmentIDList) {
            if (equipmentMapper.selectById(integer)==null){
                return Result.error().message("设备id:"+integer+"不存在！");
            }
        }
        Notice notice = new Notice();
        notice.setNoticecontent(noticeContent);
        notice.setAuthor(userMapper.selectById(authorID).getAccount());
        notice.setPlaybackmode(playbackMode);
        //根据播放模式不同添加时段
        if (!playbackMode.equals("时段播放") && !playbackMode.equals("持续播放")){
            return Result.error().message("播放模式出错!");
        } else if (playbackMode.equals("时段播放")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            notice.setStarttime(simpleDateFormat.parse(startTime));
            notice.setEndtime(simpleDateFormat.parse(endTime));
        }
        notice.setPlaybackspeed(playbackSpeed);
        notice.setPlaybacktime(new Date());
        notice.setTextposition(textPosition);
        notice.setTextcolor(textColor);
        notice.setTextsize(textSize);
        notice.setBackgroundcolor(backgroundColor);
        notice.setBackgroundheight(backgroundHeight);
        return Result.ok().data("notice",noticeService.addOneNotice(notice,equipmentIDList));
    }

    @ApiOperation("返回所有公告列表分页")
    @GetMapping("/getAllNotice")
    public Result getAllNotice(Integer current, Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("notice",noticeService.getAllNotice(current,size));
    }

    @ApiOperation("获取公告的设备列表")
    @GetMapping("/getNoticeEquipmentList")
    public Result getNoticeEquipmentList(Integer noticeID){
        if (noticeMapper.selectById(noticeID)==null){
            return Result.error().message("noticeID不存在！");
        }
        if (noticeMapper.selectById(noticeID).getEquipmentIDList().isEmpty()){
            return Result.error().message("notice EquipmentIDList 不存在！");
        }
        return Result.ok().data("notice",noticeService.getNoticeEquipmentList(noticeID));
    }

    @ApiOperation("编辑公告")
    @PutMapping("/updateNotice")
    public Result updateNotice(Integer noticeID,String noticeContent, Integer authorID, String playbackMode, String startTime, String endTime,
                               String playbackSpeed, String textPosition, String textColor, Integer textSize,
                               String backgroundColor, Integer backgroundHeight,
                               @RequestParam(value ="equipmentIDList") List<Integer> equipmentIDList) throws ParseException {
        if (userMapper.selectById(authorID)==null){
            return Result.error().message("作者id不存在!");
        }
        if (textColor.equals(backgroundColor)){
            return Result.error().message("文字和背景色不可以一致!");
        }
        for (Integer integer:equipmentIDList) {
            if (equipmentMapper.selectById(integer)==null){
                return Result.error().message("设备id:"+integer+"不存在！");
            }
        }
        Notice notice = new Notice();
        notice.setNoticecontent(noticeContent);
        notice.setAuthor(userMapper.selectById(authorID).getAccount());
        notice.setPlaybackmode(playbackMode);
        //根据播放模式不同添加时段
        if (!playbackMode.equals("时段播放") && !playbackMode.equals("持续播放")){
            return Result.error().message("播放模式出错!");
        } else if (playbackMode.equals("时段播放")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            notice.setStarttime(simpleDateFormat.parse(startTime));
            notice.setEndtime(simpleDateFormat.parse(endTime));
        }
        notice.setPlaybacktime(new Date());
        notice.setPlaybackspeed(playbackSpeed);
        notice.setTextposition(textPosition);
        notice.setTextcolor(textColor);
        notice.setTextsize(textSize);
        notice.setBackgroundcolor(backgroundColor);
        notice.setBackgroundheight(backgroundHeight);
        return Result.ok().data("notice",noticeService.updateNotice(noticeID,notice,equipmentIDList));
    }

    @ApiOperation("根据id删除公告")
    @DeleteMapping("/deleteNoticeByID")
    public Result deleteNoticeByID(Integer noticeID){
        Boolean result = noticeService.deleteNoticeByID(noticeID);
        if(result){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation("根据状态获取公告分页")
    @GetMapping("getNoticeByState")
    public Result getNoticeByState(Integer current,Integer size,String noticeState){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        if (noticeState.isEmpty()){
            return Result.ok().data("notice",noticeService.getAllNotice(current,size));
        }
        return Result.ok().data("notice",noticeService.getNoticeByState(current,size,noticeState));
    }

    @ApiOperation("根据id获取公告")
    @GetMapping("/getNoticeByID")
    public Result getNoticeByID(Integer noticeID){
        if (noticeMapper.selectById(noticeID) == null){
            return Result.error().message("noticeID不存在！");
        }
        return Result.ok().data("notice",noticeMapper.selectById(noticeID));
    }
}

