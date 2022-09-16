package top.showboard.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Material;
import top.showboard.entity.Materialgroup;
import top.showboard.entity.User;
import top.showboard.mapper.MaterialMapper;
import top.showboard.mapper.MaterialgroupMapper;
import top.showboard.mapper.UserMapper;
import top.showboard.response.Result;
import top.showboard.service.MaterialService;

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
@RequestMapping("/material")
@Api(value = "提供素材相关的接口",tags = "素材管理")
public class MaterialController {
    //定义用户业务对象
    @Resource
    MaterialService materialService;
    @Resource
    MaterialMapper materialMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    MaterialgroupMapper materialgroupMapper;

    @ApiOperation("添加新素材")
    @PostMapping("/addOneMaterial")
    public Result addOneMaterial(String materialName,String materialType, String resolvingPower,
                                 double materialSize, Integer authorID,
                                 String url, Integer materialGroupID){
        if(userMapper.selectById(authorID)==null){
            return Result.error().message("authorID不存在！");
        }
        if(materialgroupMapper.selectById(materialGroupID)==null){
            return Result.error().message("materialGroupID不存在！");
        }
        Material material = new Material();
        material.setMaterialname(materialName);
        material.setMaterialtype(materialType);
        material.setResolvingpower(resolvingPower);
        material.setMaterialsize(materialSize);
        material.setUrl(url);
        material.setMaterialgroupid(materialGroupID);
        material.setAuthor(userMapper.selectById(authorID).getAccount());
        return Result.ok().data("material",materialService.addOneMaterial(material));
    }

    @ApiOperation("返回所有素材列表分页")
    @GetMapping("/getAllMaterial")
    public Result getAllMaterial(Integer current,Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("material",materialService.getAllMaterial(current,size));
    }

    @ApiOperation("根据条件模糊查询素材信息分页")
    @GetMapping("/getMaterialBySearch")
    public Result getMaterialBySearch(Integer current,Integer size,String keywords,String fieldName){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("material",materialService.getMaterialBySearch(current,size,keywords,fieldName));
    }

    @ApiOperation("多条件联合查询素材")
    @GetMapping("/getMaterialBySearchUnion")
    public Result getMaterialBySearchUnion(Integer current,Integer size,String materialName,String materialGroupName){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("material",materialService.getMaterialBySearchUnion(current,size,materialName,materialGroupName));
    }

    @ApiOperation("素材重命名")
    @PutMapping("/updateMaterialName")
    public Result updateMaterialName(Integer materialID,String newName){
        if(materialMapper.selectById(materialID)==null){
            return Result.error().message("素材id不存在！");
        }
        return Result.ok().data("material",materialService.updateMaterialName(materialID,newName));
    }

    @ApiOperation("素材删除")
    @DeleteMapping("/deleteMaterialByID")
    public Result deleteMaterialByID(Integer materialID){
        if(materialService.deleteMaterialByID(materialID)){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation("根据素材id返回URL")
    @GetMapping("/getMaterialURLByID")
    public Result getMaterialURLByID(Integer materialID){
        if (materialMapper.selectById(materialID)==null){
            return Result.error().message("素材id不存在!");
        }
        return Result.ok().data("equipmentGroup",materialService.getMaterialURLByID(materialID));
    }

    @ApiOperation("根据素材id返回素材")
    @GetMapping("/getMaterialByID")
    public Result getMaterialByID(Integer materialID){
        if (materialMapper.selectById(materialID)==null){
            return Result.error().message("素材id不存在!");
        }
        return Result.ok().data("equipmentGroup",materialMapper.selectById(materialID));
    }

    @ApiOperation("返回素材总大小")
    @GetMapping("/getAllMaterialSize")
    public Result getAllMaterialSize(){
        return Result.ok().data("material",materialService.getAllMaterialSize());
    }

}

