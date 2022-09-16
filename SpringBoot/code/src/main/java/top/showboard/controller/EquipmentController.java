package top.showboard.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import top.showboard.entity.Equipment;
import top.showboard.mapper.EquipmentMapper;
import top.showboard.response.Result;
import top.showboard.service.EquipmentService;
import top.showboard.service.EquipmentgroupService;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/equipment")
@Api(value = "提供设备相关的接口",tags = "设备管理")
public class EquipmentController {
    //定义用户业务对象
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private EquipmentgroupService equipmentgroupService;

    @ApiOperation("返回设备列表分页")
    @GetMapping("/getAllEquipment")
    public Result getAllEquipment(Integer current, Integer size){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<Equipment> equipmentPage = equipmentService.getAllEquipment(current, size);
        return Result.ok().data("equipment",equipmentPage);
    }

    @ApiOperation("根据条件模糊查询设备信息分页")
    @GetMapping("/getEquipmentBySearch")
    public Result getEquipmentBySearch(Integer current, Integer size,String keywords,String fieldName){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<Equipment> equipmentPage = equipmentService.getEquipmentBySearch(current, size,keywords,fieldName);
        return Result.ok().data("equipment",equipmentPage);
    }

    @ApiOperation("添加新设备")
    @PostMapping("/addOneEquipment")
    public Result addOneEquipment(String equipmentName, String macAddress,Integer organizationId,String SNNumber){
        Equipment equipment = new Equipment();
        equipment.setEquipmentname(equipmentName);
        equipment.setMacaddresswireless(macAddress);
        equipment.setOrganization(organizationId);
        equipment.setSnnumber(SNNumber);
        Equipment newEquipment = equipmentService.addOneEquipment(equipment);
        return Result.ok().data("equipment",newEquipment);
    }

    @ApiOperation("多条件联合查询设备")
    @GetMapping("/getEquipmentBySearchUnion")
    public Result getEquipmentBySearchUnion(Integer current, Integer size, String equipmentName, String organization,
                                            String groupName, String macAddressWireless, String resolvingPower,String equipmentState,
                                            String systemUpdate,String nowPlan, String SNNumber){
        if(current==null||size==null){
            return Result.error().message("current和size不能为空！");
        }
        Page<Equipment> equipmentPage = equipmentService.getEquipmentBySearchUnion(current, size, equipmentName, organization,
                groupName, macAddressWireless, resolvingPower,equipmentState, systemUpdate,nowPlan, SNNumber);
        return Result.ok().data("equipment",equipmentPage);
    };

    @ApiOperation("获得选择框列表")
    @GetMapping("/getDistinctList")
    public Result getDistinctList(String fieldName){
        if (fieldName.equals("groupName")){
            return Result.ok().data("List",equipmentgroupService.getDistinctList(fieldName));
        }
        return Result.ok().data("List",equipmentService.getDistinctList(fieldName));
    }

    @ApiOperation("根据id删除设备")
    @DeleteMapping("/deleteEquipment")
    public Result deleteEquipment(Integer equipmentID){
        Boolean result = equipmentService.deleteEquipment(equipmentID);
        if(result){
            return Result.ok().message("删除成功！");
        }else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation("编辑设备")
    @PutMapping("/updateEquipment")
    public Result updateEquipment(Integer equipmentID,String equipmentName,String equipmentGroup){
        if(equipmentMapper.selectById(equipmentID)==null){
            return Result.error().message("设备不存在！");
        }
        Equipment equipment = equipmentService.updateEquipment(equipmentID,equipmentName,equipmentGroup);
        return Result.ok().data("equipment",equipment);
    }

    @ApiOperation("判断设备系统是否最新")
    @GetMapping("/judgeEquipmentSystem")
    public Result judgeEquipmentSystem(Integer equipmentID){
        if(equipmentMapper.selectById(equipmentID)==null){
            return Result.error().message("设备不存在！");
        }
        if(equipmentService.judgeEquipmentSystem(equipmentID)){
            return Result.ok().message("已是最新");
        }else {
            return Result.ok().message("系统可更新");
        }
    }

}

