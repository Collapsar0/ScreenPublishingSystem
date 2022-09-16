package top.showboard.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Collapsar
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("equipmentgroup")
@ApiModel(value="Equipmentgroup对象", description="")
public class Equipmentgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组ID")
    @TableId(value = "equipmentGroupID", type = IdType.AUTO)
    private Integer equipmentgroupid;

    @ApiModelProperty(value = "分组名称")
    @TableField("groupName")
    private String groupname;

    @ApiModelProperty(value = "所属机构")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "设备数量")
    @TableField("equipmentCount")
    private Integer equipmentcount;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "组内设备ID列表")
    @TableField("equipmentIDList")
    private String equipmentidlist;


}
