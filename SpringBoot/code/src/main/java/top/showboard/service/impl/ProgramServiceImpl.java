package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import top.showboard.entity.Materialgroup;
import top.showboard.entity.Program;
import top.showboard.entity.User;
import top.showboard.mapper.MaterialMapper;
import top.showboard.mapper.ProgramMapper;
import top.showboard.service.ProgramService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
@Service
public class ProgramServiceImpl extends ServiceImpl<ProgramMapper, Program> implements ProgramService {
    @Resource
    ProgramMapper programMapper;
    @Resource
    MaterialMapper materialMapper;

    /**
     * Json包数据类
     */
    public static class programJson{
        Integer materialID;
        Integer playbackTime;
    }

    /**
     * 解析JSON函数
     * @param json
     * @return
     */
    public List<programJson> getGsonList(String json){
        Gson gson = new Gson();
        String content = null;
        try {
            content = java.net.URLDecoder.decode(json,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return gson.fromJson(content, new TypeToken<List<programJson>>(){}.getType());
    }

    /**
     * 新增节目
     * @param program
     * @return
     */
    @Override
    public Program addOneProgram(Program program){
        //解析JSON
        List<programJson> programJsonList = getGsonList(program.getProgramcontent());
        //设定头图地址
        program.setProgrampic(materialMapper.selectById(programJsonList.get(0).materialID).getUrl());
        //设定总时长
        int programDuration = 0;
        for (programJson programJson:programJsonList) {
            programDuration = programDuration + programJson.playbackTime;
        }
        program.setProgramduration(programDuration +"s");
        program.setProgramstate("使用中");
        program.setUpdatedate(new Date());
        programMapper.insert(program);
        return program;
    }

    /**
     * 修改节目名
     * @param programID
     * @param newName
     * @return
     */
    @Override
    public Program updateProgramName(Integer programID,String newName){
        UpdateWrapper<Program> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("programID",programID).set("programName",newName);
        programMapper.update(null,updateWrapper);
        return programMapper.selectById(programID);
    }

    /**
     * 节目删除
     * @param ProgramID
     * @return
     */
    @Override
    public Boolean deleteProgramByID(Integer ProgramID){
        if(programMapper.deleteById(ProgramID) > 0 ){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 返回所有节目列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<Program> getAllProgram(Integer current, Integer size){
        QueryWrapper<Program> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("programID");
        Page<Program> programPage = new Page<>(current,size);
        return programMapper.selectPage(programPage,queryWrapper);
    }

    /**
     * 多条件联合查询节目
     * @param current
     * @param size
     * @param programName
     * @param resolvingPower
     * @param programState
     * @return
     */
    @Override
    public Page<Program> getProgramBySearchUnion(Integer current,Integer size,String programName,String resolvingPower,String programState){
        QueryWrapper<Program> queryWrapper = new QueryWrapper<>();
        if(!programName.isEmpty()){
            queryWrapper.like("programName",programName);
        }
        if(!resolvingPower.isEmpty()){
            queryWrapper.eq("resolvingPower",resolvingPower);
        }
        if(!programState.isEmpty()){
            queryWrapper.eq("programState",programState);
        }
        Page<Program> userPage = new Page<>(current,size);
        return programMapper.selectPage(userPage,queryWrapper);
    }

    /**
     * 编辑节目
     * @param programID
     * @param programContent
     * @return
     */
    @Override
    public Program updateProgram(Integer programID,String programName,
                                 String resolvingPower,String programData,
                                 String programContent){
        UpdateWrapper<Program> updateWrapper = new UpdateWrapper<>();
        //解析JSON并设定节目内容
        List<programJson> programJsonList = getGsonList(programContent);
        updateWrapper.eq("programID",programID).set("programContent",programContent);
        //设定头图地址
        updateWrapper.eq("programID",programID).set("programPic",
                materialMapper.selectById(programJsonList.get(0).materialID).getUrl());
        //设定总时长
        int programDuration = 0;
        for (programJson programJson:programJsonList) {
            programDuration = programDuration + programJson.playbackTime;
        }
        updateWrapper.eq("programID",programID).set("programDuration",programDuration+"s");
        updateWrapper.eq("programID",programID).set("programName",programName);
        updateWrapper.eq("programID",programID).set("resolvingPower",resolvingPower);
        updateWrapper.eq("programID",programID).set("programData",programData);
        updateWrapper.eq("programID",programID).set("updateDate",new Date());
        programMapper.update(null,updateWrapper);
        return programMapper.selectById(programID);
    }
}
