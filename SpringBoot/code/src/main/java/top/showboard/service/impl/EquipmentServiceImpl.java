package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.Equipment;
import top.showboard.entity.Equipmentgroup;
import top.showboard.mapper.EquipmentMapper;
import top.showboard.mapper.EquipmentgroupMapper;
import top.showboard.response.Result;
import top.showboard.service.EquipmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.showboard.service.EquipmentgroupService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-24
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private EquipmentgroupService equipmentgroupService;
    @Resource
    private EquipmentgroupMapper equipmentgroupMapper;

    /**
     * 返回所有设备列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<Equipment> getAllEquipment(Integer current, Integer size){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("equipmentID");
        Page<Equipment> equipmentPage = new Page<>(current,size);
        Page page = equipmentMapper.selectPage(equipmentPage,queryWrapper);
        return page;
    }

    /**
     * 根据条件模糊查询设备信息分页
     * @param current
     * @param size
     * @param keywords
     * @param fieldName
     * @return
     */
    @Override
    public Page<Equipment> getEquipmentBySearch(Integer current, Integer size,String keywords,String fieldName){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().like(fieldName,keywords);
        Page<Equipment> equipmentPage = new Page<>(current,size);
        Page page = equipmentMapper.selectPage(equipmentPage,queryWrapper);
        return page;
    }

    /**
     * 添加新设备
     * @param equipment
     * @return
     */
    @Override
    public Equipment addOneEquipment(Equipment equipment){
        equipment.setEquipmenttype("HiDPTAndroid Hi3751V553");
        equipment.setIp("192.168.113.212");
        equipment.setMacaddresswired("A0BB3ED3861D");
        equipment.setResolvingpower("1920x1080");
        equipment.setEquipmentstate("离线");
        equipment.setSystemedition("BOE_iGallery32_V13103_V5.2.0");
        equipment.setSendingedition("1.3.1");
        equipment.setNowplan("--");
        equipment.setEquipmentGroupID(1);
        equipment.setRam("0.96GB");
        equipment.setSystemupdate("已是最新");
        equipment.setRom("4.70GB可用(共5.39GB)");
        equipment.setActivationtime(new Date());
        equipment.setRegistertime(new Date());
        equipment.setLastheartbeattime(new Date());
        equipment.setScreendisplay("横屏");
        equipment.setInstallationposition("中国浙江省杭州市拱墅区上塘街道东教路");
        equipment.setRuntime(new Date());
        equipmentMapper.insert(equipment);
        equipmentgroupService.updateEquipmentGroupCount();
        return equipment;
    }

    /**
     * 多条件联合查询设备
     * @param current
     * @param size
     * @param equipmentName
     * @param organization
     * @param groupName
     * @param macAddressWireless
     * @param resolvingPower
     * @param equipmentState
     * @param systemUpdate
     * @param nowPlan
     * @param SNNumber
     * @return
     */
    @Override
    public Page<Equipment> getEquipmentBySearchUnion(Integer current, Integer size,
                                                     String equipmentName, String organization,
                                                     String groupName, String macAddressWireless,
                                                     String resolvingPower,String equipmentState,
                                                     String systemUpdate,String nowPlan,
                                                     String SNNumber){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        if(!equipmentName.isEmpty()){
            queryWrapper.like("equipmentName",equipmentName);
        }
        if(!organization.isEmpty()){
            queryWrapper.eq("organization",organization);
        }
        if(!groupName.isEmpty()){
            QueryWrapper<Equipmentgroup> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.select().eq("groupName",groupName);
            queryWrapper.eq("equipmentGroupID",equipmentgroupMapper
                    .selectList(groupQueryWrapper)
                    .get(0).getEquipmentgroupid());
        }
        if(!macAddressWireless.isEmpty()){
            queryWrapper.eq("macAddressWireless",macAddressWireless);
        }
        if(!resolvingPower.isEmpty()){
            queryWrapper.eq("resolvingPower",resolvingPower);
        }
        if(!equipmentState.isEmpty()){
            queryWrapper.eq("equipmentState",equipmentState);
        }
        if(!systemUpdate.isEmpty()){
            queryWrapper.eq("systemUpdate",systemUpdate);
        }
        if(!nowPlan.isEmpty()){
            queryWrapper.eq("nowPlan",nowPlan);
        }
        if(!SNNumber.isEmpty()){
            queryWrapper.eq("SNNumber",SNNumber);
        }
        Page<Equipment> equipmentPage = new Page<>(current,size);
        Page page = equipmentMapper.selectPage(equipmentPage,queryWrapper);
        return page;
    }

    /**
     * 根据id删除设备
     * @param equipmentID
     * @return
     */
    @Override
    public Boolean deleteEquipment(Integer equipmentID){
        int i = equipmentMapper.deleteById(equipmentID);
        if(i > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 编辑设备
     * @param equipmentID
     * @param equipmentName
     * @param equipmentGroup
     * @return
     */
    @Override
    public Equipment updateEquipment(Integer equipmentID,String equipmentName,String equipmentGroup){
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("equipmentID",equipmentID).set("equipmentName",equipmentName);
        QueryWrapper<Equipmentgroup> groupQueryWrapper = new QueryWrapper<>();
        groupQueryWrapper.select().eq("groupName",equipmentGroup);
        updateWrapper.eq("equipmentID",equipmentID).set("equipmentGroupID",equipmentgroupMapper
                .selectList(groupQueryWrapper)
                .get(0).getEquipmentgroupid());
        equipmentMapper.update(null,updateWrapper);
        equipmentgroupService.updateEquipmentGroupCount();
        return equipmentMapper.selectById(equipmentID);
    }

    /**
     * 判断系统是否最新
     * @param equipmentID
     * @return
     */
    @Override
    public Boolean judgeEquipmentSystem(Integer equipmentID){
        if(equipmentMapper.selectById(equipmentID).getSystemedition().equals("BOE_iGallery32_V13103_V5.2.0")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获得选择框列表
     * @param fieldName
     * @return
     */
    @Override
    public List<Equipment> getDistinctList(String fieldName){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT "+fieldName);
        return equipmentMapper.selectList(queryWrapper);
    }
}
