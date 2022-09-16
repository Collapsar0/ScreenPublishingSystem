package top.showboard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.showboard.entity.Material;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
public interface MaterialService extends IService<Material> {
    //添加新素材
    Material addOneMaterial(Material material);
    //返回所有素材列表分页
    Page<Material> getAllMaterial(Integer current, Integer size);
    //根据条件模糊查询素材信息分页
    Page<Material> getMaterialBySearch(Integer current,Integer size,String keywords,String fieldName);
    //多条件联合查询素材
    Page<Material> getMaterialBySearchUnion(Integer current,Integer size,String materialName,String materialGroupName);
    //素材重命名
    Material updateMaterialName(Integer materialID,String newName);
    //素材删除
    Boolean deleteMaterialByID(Integer materialID);
    //根据素材id返回URL
    String getMaterialURLByID(Integer materialID);
    //返回素材总大小
    double getAllMaterialSize();
}
