package top.showboard.service;

import top.showboard.entity.Materialgroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-29
 */
public interface MaterialgroupService extends IService<Materialgroup> {
    //新建素材分组
    Materialgroup addOneMaterialGroup(Materialgroup materialgroup);
    //更新素材组名
    Materialgroup updateMaterialGroupName(Integer materialGroupID,String newName);
    //删除素材组
    Boolean deleteMaterialGroup(Integer materialGroupID);
    //获取所有素材组
    List<Materialgroup> getAllMaterialGroup();
}
