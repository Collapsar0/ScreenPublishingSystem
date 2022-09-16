package top.showboard.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Material;
import top.showboard.entity.Materialgroup;
import top.showboard.entity.User;
import top.showboard.mapper.MaterialMapper;
import top.showboard.mapper.MaterialgroupMapper;
import top.showboard.response.Result;
import top.showboard.service.MaterialService;
import top.showboard.service.MaterialgroupService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/materialgroup")
@Api(value = "提供素材组相关的接口",tags = "素材组管理")
public class MaterialgroupController {
    //定义用户业务对象
    @Resource
    MaterialgroupService materialgroupService;
    @Resource
    MaterialgroupMapper materialgroupMapper;
    @Resource
    MaterialMapper materialMapper;

    @ApiOperation("新建素材分组")
    @PostMapping("/addOneMaterialGroup")
    public Result addOneMaterialGroup(String materialGroupName,String materialGroupType){
        if(materialGroupName.isEmpty() || materialGroupType.isEmpty()){
            return Result.error().message("materialGroupName或materialGroupType为空！");
        }
        QueryWrapper<Materialgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("materialGroupName",materialGroupName);
        //判断素材组名是否重复
        if(materialgroupMapper.selectList(queryWrapper).size()!=0){
            return Result.error().message("素材组名已存在！");
        }
        Materialgroup materialgroup = new Materialgroup();
        materialgroup.setMaterialgroupname(materialGroupName);
        materialgroup.setMaterialgrouptype(materialGroupType);
        return Result.ok().data("materialGroup",materialgroupService.addOneMaterialGroup(materialgroup));
    }

    @ApiOperation("更新素材组名")
    @PutMapping("/updateMaterialGroupName")
    public Result updateMaterialGroupName(Integer materialGroupID,String newName){
        if (materialgroupMapper.selectById(materialGroupID)==null){
            return Result.error().message("素材组id不存在！");
        }
        //判断素材组名是否重复
        QueryWrapper<Materialgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("materialGroupName",newName);
        List<Materialgroup> materialGroupList = materialgroupMapper.selectList(queryWrapper);
        if(materialGroupList.size()!=0 && !Objects.equals(materialGroupList.get(0).getMaterialgroupid(), materialGroupID)){
            return Result.error().message("素材组名已存在！");
        }
        return Result.ok().data("material",materialgroupService.updateMaterialGroupName(materialGroupID,newName));
    }

    @ApiOperation("删除素材组")
    @DeleteMapping("/deleteMaterialGroup")
    public Result deleteMaterialGroup(Integer materialGroupID){
        if (materialgroupMapper.selectById(materialGroupID)==null){
            return Result.error().message("素材组id不存在");
        }
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("materialGroupID",materialGroupID);
        if (materialMapper.selectList(queryWrapper).size()!=0){
            return Result.error().message("该素材组中有内容，无法删除！");
        }
        if (materialgroupService.deleteMaterialGroup(materialGroupID)){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation("获取所有素材组")
    @GetMapping("/getAllMaterialGroup")
    public Result getAllMaterialGroup(){
        return Result.ok().data("MaterialGroup",materialgroupService.getAllMaterialGroup());
    }
}

