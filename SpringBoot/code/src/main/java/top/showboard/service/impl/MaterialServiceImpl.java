package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.showboard.entity.Equipmentgroup;
import top.showboard.entity.Material;
import top.showboard.entity.Materialgroup;
import top.showboard.entity.User;
import top.showboard.mapper.MaterialMapper;
import top.showboard.mapper.MaterialgroupMapper;
import top.showboard.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Resource
    MaterialMapper materialMapper;
    @Resource
    MaterialgroupMapper materialgroupMapper;

    /**
     * 添加新素材
     * @param material
     * @return
     */
    @Override
    public Material addOneMaterial(Material material){
        material.setProgramcount(0);
        material.setUpdatetime(new Date());
        materialMapper.insert(material);
        return material;
    }

    /**
     * 返回所有素材列表分页
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<Material> getAllMaterial(Integer current,Integer size){
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().orderByAsc("materialID");
        Page<Material> materialPage = new Page<>(current,size);
        Page page = materialMapper.selectPage(materialPage,queryWrapper);
        return page;
    }

    /**
     * 根据条件模糊查询素材信息分页
     * @param current
     * @param size
     * @param keywords
     * @param fieldName
     * @return
     */
    @Override
    public Page<Material> getMaterialBySearch(Integer current,Integer size,String keywords,String fieldName){
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().like(fieldName,keywords);
        Page<Material> materialPage = new Page<>(current,size);
        return materialMapper.selectPage(materialPage,queryWrapper);
    }

    /**
     * 多条件联合查询素材
     * @param current
     * @param size
     * @param materialName
     * @param materialGroupName
     * @return
     */
    @Override
    public Page<Material> getMaterialBySearchUnion(Integer current,Integer size,String materialName,String materialGroupName){
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        if(!materialName.isEmpty()){
            queryWrapper.like("materialName",materialName);
        }
        if(!materialGroupName.isEmpty()){
            QueryWrapper<Materialgroup> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.select().eq("materialGroupName",materialGroupName);
            if (materialgroupMapper.selectList(groupQueryWrapper).size() > 0){
                queryWrapper.eq("equipmentGroupID",materialgroupMapper
                        .selectList(groupQueryWrapper)
                        .get(0).getMaterialgroupid());
            } else {
                return new Page<>(current,size);
            }
        }
        Page<Material> materialPage = new Page<>(current,size);
        return materialMapper.selectPage(materialPage,queryWrapper);
    }

    /**
     * 素材重命名
     * @param materialID
     * @param newName
     * @return
     */
    @Override
    public Material updateMaterialName(Integer materialID,String newName){
        UpdateWrapper<Material> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("materialID",materialID).set("materialName",newName);
        materialMapper.update(null,updateWrapper);
        return materialMapper.selectById(materialID);
    }

    /**
     * 素材删除
     * @param materialID
     * @return
     */
    @Override
    public Boolean deleteMaterialByID(Integer materialID){
        if(materialMapper.deleteById(materialID) > 0 ){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据素材id返回URL
     * @param materialID
     * @return
     */
    @Override
    public String getMaterialURLByID(Integer materialID){
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("url").eq("materialID",materialID);
        return materialMapper.selectList(queryWrapper).get(0).getUrl();
    }

    /**
     * 返回素材总大小
     * @return
     */
    @Override
    public double getAllMaterialSize(){
        QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(materialSize) as materialSize");
        return materialMapper.selectList(queryWrapper).get(0).getMaterialsize();
    }

}
