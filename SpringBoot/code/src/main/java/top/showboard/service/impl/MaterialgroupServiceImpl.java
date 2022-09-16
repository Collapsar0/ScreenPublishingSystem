package top.showboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import top.showboard.entity.Materialgroup;
import top.showboard.mapper.MaterialgroupMapper;
import top.showboard.service.MaterialgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
@Service
public class MaterialgroupServiceImpl extends ServiceImpl<MaterialgroupMapper, Materialgroup> implements MaterialgroupService {
    @Resource
    MaterialgroupMapper materialgroupMapper;

    /**
     * 新建素材分组
     * @param materialgroup
     * @return
     */
    @Override
    public Materialgroup addOneMaterialGroup(Materialgroup materialgroup){
        materialgroupMapper.insert(materialgroup);
        return materialgroup;
    }

    /**
     * 更新素材组名
     * @param materialGroupID
     * @param newName
     * @return
     */
    @Override
    public Materialgroup updateMaterialGroupName(Integer materialGroupID,String newName){
        UpdateWrapper<Materialgroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("materialGroupID",materialGroupID).set("materialGroupName",newName);
        materialgroupMapper.update(null,updateWrapper);
        return materialgroupMapper.selectById(materialGroupID);
    }

    /**
     * 删除素材组
     * @param materialGroupID
     * @return
     */
    @Override
    public Boolean deleteMaterialGroup(Integer materialGroupID){
        return materialgroupMapper.deleteById(materialGroupID) > 0;
    }

    /**
     * 获取所有素材组
     * @return
     */
    @Override
    public List<Materialgroup> getAllMaterialGroup(){
        QueryWrapper<Materialgroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.select();
        return materialgroupMapper.selectList(queryWrapper);
    }
}
