package top.showboard.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Equipmentgroup;
import top.showboard.entity.User;
import top.showboard.mapper.EquipmentgroupMapper;
import top.showboard.mapper.UserMapper;
import top.showboard.response.Result;
import top.showboard.service.EquipmentgroupService;
import top.showboard.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/equipmentgroup")
@Api(value = "提供设备组的接口",tags = "设备组管理")
public class EquipmentgroupController {
    //定义用户业务对象
    @Resource
    private EquipmentgroupService equipmentgroupService;
    @Resource
    private EquipmentgroupMapper equipmentgroupMapper;

    @ApiOperation("返回所有设备分组列表分页")
    @GetMapping("/getAllEquipmentGroups")
    Result getAllEquipmentGroups(Integer current, Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("equipmentGroups",equipmentgroupService.getAllEquipmentGroups(current,size));
    }

    @ApiOperation("根据id删除设备组")
    @DeleteMapping("/deleteEquipmentGroup")
    Result deleteEquipmentGroup(Integer equipmentGroupID){
        if (equipmentgroupMapper.selectById(equipmentGroupID)==null){
            return Result.error().message("设备组id不存在！");
        }
        if (equipmentgroupMapper.selectById(equipmentGroupID).getEquipmentcount()!=0){
            return Result.error().message("该设备组中有设备，无法删除！");
        }
        Boolean result = equipmentgroupService.deleteEquipmentGroup(equipmentGroupID);
        if(result){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }


    @ApiOperation("添加一个设备组")
    @PostMapping("/addOneEquipmentGroup")
    Result addOneEquipmentGroup(String groupName, String organization, String description,@RequestParam(value ="equipmentID")List<Integer> equipmentID){
        //新建一个分组
        if (groupName.isEmpty()){
            return Result.error().message("组名不能为空！");
        }else if (organization.isEmpty()){
            return Result.error().message("所属机构不能为空！");
        }
        QueryWrapper<Equipmentgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("groupName",groupName);
        //判断用户名是否重复
        if(equipmentgroupMapper.selectList(queryWrapper).size()!=0){
            return Result.error().message("组名已存在！");
        }
        Equipmentgroup equipmentgroup = new Equipmentgroup();
        equipmentgroup.setGroupname(groupName);
        equipmentgroup.setOrganization(organization);
        equipmentgroup.setDescription(description);
        equipmentgroupService.addOneEquipmentGroup(equipmentgroup);
        if(equipmentgroupMapper.selectList(queryWrapper).size()==0){
            return Result.error().message("添加失败！");
        }
        //将List中的设备更改分组
        equipmentgroupService.updateEquipmentGroupList(equipmentID,equipmentgroupMapper.selectList(queryWrapper).get(0).getEquipmentgroupid());

        return Result.ok().data("equipmentGroup",equipmentgroupMapper.selectList(queryWrapper).get(0));
    }

    @ApiOperation("更换单个设备的设备组")
    @PutMapping("/updateOneEquipmentGroup")
    public Result updateOneEquipmentGroup(Integer equipmentID,Integer groupID){
        if(!equipmentgroupService.updateOneEquipmentGroup(equipmentID,groupID)){
            return Result.error().message("设置失败！");
        }else {
            return Result.ok().message("设置成功！");
        }
    }

    @ApiOperation("更新设备组和设备组号")
    @PutMapping("/updateEquipmentGroup")
    public Result updateEquipmentGroup(Integer equipmentGroupID,String groupName,String organization,String description,@RequestParam(value ="equipmentID")List<Integer> equipmentID){
        //判断设备组id是否存在
        if (equipmentgroupMapper.selectById(equipmentGroupID)==null){
            return Result.error().message("设备组id不存在");
        }
        if (groupName.isEmpty()){
            return Result.error().message("组名不能为空！");
        }
        if (organization.isEmpty()){
            return Result.error().message("所属机构不能为空！");
        }
        QueryWrapper<Equipmentgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().eq("groupName",groupName);
        //判断用户名是否重复
        if(equipmentgroupMapper.selectList(queryWrapper).size()!=0
                && !Objects.equals(equipmentgroupMapper.selectById(equipmentGroupID).getGroupname(), groupName)){
            return Result.error().message("组名已存在！");
        }
        equipmentgroupService.updateEquipmentGroup(equipmentGroupID,groupName,organization,description);
        equipmentgroupService.updateEquipmentGroupList(equipmentID,equipmentGroupID);
        return Result.ok().data("equipmentGroup",equipmentgroupMapper.selectById(equipmentGroupID));
    }

    @ApiOperation("获得选择框列表")
    @GetMapping("/getDistinctList")
    public Result getDistinctList(String fieldName){
        return Result.ok().data("List",equipmentgroupService.getDistinctList(fieldName));
    }

    @ApiOperation("多条件联合查询设备组")
    @GetMapping("/getEquipmentGroupBySearchUnion")
    public Result getEquipmentGroupBySearchUnion(Integer current, Integer size, String groupName, String organization){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        return Result.ok().data("equipmentGroup",equipmentgroupService.getEquipmentGroupBySearchUnion(current,size,groupName,organization));
    }

    @ApiOperation("根据id获取设备组")
    @GetMapping("/getEquipmentGroupByID")
    public Result getEquipmentGroupByID(Integer equipmentID){
        if (equipmentgroupMapper.selectById(equipmentID)==null){
            return Result.error().message("设备组id不存在!");
        }
        return Result.ok().data("equipmentGroup",equipmentgroupMapper.selectById(equipmentID));
    }
}

