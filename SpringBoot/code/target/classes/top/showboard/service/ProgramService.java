package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.showboard.entity.Program;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
public interface ProgramService extends IService<Program> {
    //新增节目
    Program addOneProgram(Program program);
    //修改节目名
    Program updateProgramName(Integer programID,String newName);
    //节目删除
    Boolean deleteProgramByID(Integer ProgramID);
    //返回所有节目列表分页
    Page<Program> getAllProgram(Integer current, Integer size);
    //多条件联合查询节目
    Page<Program> getProgramBySearchUnion(Integer current,Integer size,String programName,String resolvingPower,String programState);
    //编辑节目
    Program updateProgram(Integer programID,String programName,
                          String resolvingPower,String programData,
                          String programContent);
}
