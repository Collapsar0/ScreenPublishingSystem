package top.showboard.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Program;
import top.showboard.entity.User;
import top.showboard.mapper.ProgramMapper;
import top.showboard.mapper.UserMapper;
import top.showboard.response.Result;
import top.showboard.service.ProgramService;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/program")
@Api(value = "提供节目相关的接口",tags = "节目管理")
public class ProgramController {
    @Resource
    ProgramService programService;
    @Resource
    ProgramMapper programMapper;
    @Resource
    UserMapper userMapper;

    @ApiOperation("新增节目")
    @PostMapping("/addOneProgram")
    public Result addOneProgram(String programName, String programData,
                                Integer userID, String programContent,
                                String resolvingPower){
        if (userMapper.selectById(userID)==null){
            return Result.error().message("用户id不存在！");
        }
        QueryWrapper<Program> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("programName",programName);
        //判断节目名是否重复
        if(programMapper.selectList(queryWrapper).size()!=0){
            return Result.error().message("节目名已存在！");
        }
        Program program = new Program();
        program.setProgramname(programName);
        program.setProgramdata(programData);
        program.setResolvingpower(resolvingPower);
        program.setAuthor(userMapper.selectById(userID).getAccount());
        program.setProgramcontent(programContent);
        return Result.ok().data("program",programService.addOneProgram(program));
    }

    @ApiOperation("修改节目名")
    @PutMapping("/updateProgramName")
    public Result updateProgramName(Integer programID,String newName){
        if (programMapper.selectById(programID)==null){
            return Result.error().message("节目id不存在！");
        }
        return Result.ok().data("program",programService.updateProgramName(programID,newName));
    }

    @ApiOperation("节目删除")
    @DeleteMapping("/deleteProgramByID")
    public Result deleteProgramByID(Integer programID){
        if(programService.deleteProgramByID(programID)){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation("返回所有节目列表分页")
    @GetMapping("/programService")
    public Result getAllProgram(Integer current, Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("program",programService.getAllProgram(current,size));
    }

    @ApiOperation("多条件联合查询节目")
    @GetMapping("/getProgramBySearchUnion")
    public Result getProgramBySearchUnion(Integer current,Integer size,String programName,String resolvingPower,String programState){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("program",programService.getProgramBySearchUnion(current,size,
                programName,resolvingPower,programState));
    }

    @ApiOperation("编辑节目")
    @PutMapping("/updateProgram")
    public Result updateProgram(Integer programID,String programName,
                                        String resolvingPower,String programData,
                                        String programContent){
        if (programMapper.selectById(programID)==null){
            return Result.error().message("节目id不存在!");
        }
        return Result.ok().data("program",programService.updateProgram(programID,programName, resolvingPower, programData, programContent));
    }

    @ApiOperation("根据id获取节目")
    @GetMapping("/getProgramByID")
    public Result getProgramByID(Integer programID){
        if (programMapper.selectById(programID)==null){
            return Result.error().message("节目id不存在!");
        }
        return Result.ok().data("program",programMapper.selectById(programID));
    }
}

