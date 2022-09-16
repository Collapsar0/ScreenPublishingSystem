package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.Equipment;
import top.showboard.entity.Equipmentgroup;
import top.showboard.mapper.EquipmentMapper;
import top.showboard.mapper.EquipmentgroupMapper;
import top.showboard.service.EquipmentgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-27
 */
@Service
public class EquipmentgroupServiceImpl extends ServiceImpl<EquipmentgroupMapper, Equipmentgroup> implements EquipmentgroupService {

    @Resource
    EquipmentgroupMapper equipmentgroupMapper;
    @Resource
    EquipmentMapper equipmentMapper;

    /**
     * 返回所有设备分组列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page getAllEquipmentGroups(Integer current, Integer size){
        QueryWrapper<Equipmentgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("equipmentGroupID");
        Page<Equipmentgroup> equipmentgroupPage = new Page<>(current,size);
        Page page = equipmentgroupMapper.selectPage(equipmentgroupPage,queryWrapper);
        return page;
    }

    /**
     * 添加一个设备组
     * @param equipmentgroup
     * @return
     */
    @Override
    public Equipmentgroup addOneEquipmentGroup(Equipmentgroup equipmentgroup){
        equipmentgroup.setEquipmentcount(0);
        equipmentgroupMapper.insert(equipmentgroup);
        return equipmentgroup;
    }

    /**
     * 更换设备列表的设备组
     * @param equipmentIDList
     * @param groupID
     * @return
     */
    @Override
    public List<Boolean> updateEquipmentGroupList(List<Integer> equipmentIDList,Integer groupID){
        List<Boolean> result = new ArrayList<>();
        for (Integer integer : equipmentIDList) {
            result.add(updateOneEquipmentGroup(integer, groupID));
        }
        return result;
    }

    /**
     * 更换单个设备的设备组
     * @param equipmentID 设备号
     * @param groupID 组号
     * @return Boolean
     */
    @Override
    public Boolean updateOneEquipmentGroup(Integer equipmentID,Integer groupID){
        //获取旧设备号
        Integer oldEquipmentID = equipmentMapper.selectById(equipmentID).getEquipmentGroupID();
        if (equipmentgroupMapper.selectById(oldEquipmentID)==null){
            System.out.println("111");
            //设备id不存在
            return false;
        }

        //设备组号修改
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("equipmentID",equipmentID).set("equipmentGroupID",groupID);
        equipmentMapper.update(null,updateWrapper);

        //设备组count数更新
        updateEquipmentGroupCount();
        return true;
    }

    /**
     * 设备组count数更新
     */
    @Override
    public void updateEquipmentGroupCount(){
        QueryWrapper<Equipmentgroup> queryWrapperEG = new QueryWrapper<>();
        QueryWrapper<Equipment> queryWrapperE = new QueryWrapper<>();
        queryWrapperEG.select();
        List<Equipmentgroup> equipmentgroupList = equipmentgroupMapper.selectList(queryWrapperEG);
        List<Equipment> equipmentList = equipmentMapper.selectList(queryWrapperE);
        //遍历equipmentGroup表
        for (Equipmentgroup equipmentgroup: equipmentgroupList) {
            int equipmentCount = 0;
            //遍历equipment表，获取count值
            for (Equipment equipment: equipmentList) {
                if (Objects.equals(equipment.getEquipmentGroupID(),
                        equipmentgroup.getEquipmentgroupid())){
                    equipmentCount++;
                }
            }
            UpdateWrapper<Equipmentgroup> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("equipmentGroupID",equipmentgroup.getEquipmentgroupid()).set("equipmentCount",equipmentCount);
            equipmentgroupMapper.update(null,updateWrapper);
        }
    }

    /**
     * 获得选择框列表
     * @param fieldName
     * @return
     */
    @Override
    public List<Equipmentgroup> getDistinctList(String fieldName){
        QueryWrapper<Equipmentgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT "+fieldName);
        return equipmentgroupMapper.selectList(queryWrapper);
    }

    /**
     * 更新设备组
     * @param equipmentGroupID
     * @param organization
     * @param description
     * @return
     */
    @Override
    public Equipmentgroup updateEquipmentGroup(Integer equipmentGroupID,String groupName,String organization,String description){
        UpdateWrapper<Equipmentgroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("equipmentGroupID",equipmentGroupID).set("organization",organization)
                .set("description",description)
                .set("groupName",groupName);
        equipmentgroupMapper.update(null,updateWrapper);
        return equipmentgroupMapper.selectById(equipmentGroupID);
    }

    /**
     * 根据id删除设备组
     * @param equipmentGroupID
     * @return
     */
    @Override
    public Boolean deleteEquipmentGroup(Integer equipmentGroupID){
        int i = equipmentgroupMapper.deleteById(equipmentGroupID);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 多条件联合查询设备组
     * @param current
     * @param size
     * @param groupName
     * @param organization
     * @return
     */
    @Override
    public Page<Equipmentgroup> getEquipmentGroupBySearchUnion(Integer current, Integer size, String groupName, String organization){
        QueryWrapper<Equipmentgroup> queryWrapper = new QueryWrapper<>();
        if(!groupName.isEmpty()){
            queryWrapper.like("groupName",groupName);
        }
        if(!organization.isEmpty()){
            queryWrapper.eq("organization",organization);
        }
        Page<Equipmentgroup> equipmentgroupPage = new Page<>(current,size);
        return equipmentgroupMapper.selectPage(equipmentgroupPage,queryWrapper);
    }

}
