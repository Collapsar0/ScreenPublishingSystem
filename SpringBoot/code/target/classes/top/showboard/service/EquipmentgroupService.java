package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.Equipmentgroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-27
 */
public interface EquipmentgroupService extends IService<Equipmentgroup> {
    //返回所有设备分组列表分页
    Page<Equipmentgroup> getAllEquipmentGroups(Integer current, Integer size);
    //添加一个设备组
    Equipmentgroup addOneEquipmentGroup(Equipmentgroup equipmentgroup);
    //更换设备列表的设备组
    List<Boolean> updateEquipmentGroupList(List<Integer> equipmentIDList, Integer groupID);
    //更换单个设备的设备组
    Boolean updateOneEquipmentGroup(Integer equipmentID,Integer groupID);
    //获得选择框列表
    List<Equipmentgroup> getDistinctList(String fieldName);
    //更新设备组
    Equipmentgroup updateEquipmentGroup(Integer equipmentGroupID,String groupName,String organization,String description);
    //根据id删除设备组
    Boolean deleteEquipmentGroup(Integer equipmentGroupID);
    //多条件联合查询设备组
    Page<Equipmentgroup> getEquipmentGroupBySearchUnion(Integer current, Integer size, String groupName, String organization);
    //设备组count数更新
    void updateEquipmentGroupCount();
}
