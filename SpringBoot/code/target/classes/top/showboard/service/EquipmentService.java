package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.showboard.entity.Equipment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
public interface EquipmentService extends IService<Equipment> {
    //返回设备列表分页
    Page<Equipment> getAllEquipment(Integer current, Integer size);
    //根据条件模糊查询设备信息分页
    Page<Equipment> getEquipmentBySearch(Integer current, Integer size,String keywords,String fieldName);
    //添加新设备
    Equipment addOneEquipment(Equipment equipment);
    //多条件联合查询设备
    Page<Equipment> getEquipmentBySearchUnion(Integer current, Integer size, String equipmentName, String organization,
                                              String groupName, String macAddressWireless, String resolvingPower,String equipmentState,
                                              String systemUpdate,String nowPlan, String SNNumber);
    //根据id删除设备
    Boolean deleteEquipment(Integer equipmentID);
    //编辑设备
    Equipment updateEquipment(Integer equipmentID,String equipmentName,String equipmentGroup);
    //判断设备系统是否最新
    Boolean judgeEquipmentSystem(Integer equipmentID);
    //获得选择框列表
    List<Equipment> getDistinctList(String fieldName);
}
