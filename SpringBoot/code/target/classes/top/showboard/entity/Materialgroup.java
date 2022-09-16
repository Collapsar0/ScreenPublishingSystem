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
 * @since 2022-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("materialgroup")
@ApiModel(value="Materialgroup对象", description="")
public class Materialgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "素材组id")
    @TableId(value = "materialGroupID", type = IdType.AUTO)
    private Integer materialgroupid;

    @ApiModelProperty(value = "素材组名")
    @TableField("materialGroupName")
    private String materialgroupname;

    @ApiModelProperty(value = "素材组类型")
    @TableField("materialGroupType")
    private String materialgrouptype;


}
