package top.showboard.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@TableName("material")
@ApiModel(value="Material对象", description="")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "素材id")
    @TableId(value = "materialID", type = IdType.AUTO)
    private Integer materialid;

    @ApiModelProperty(value = "素材名")
    @TableField("materialName")
    private String materialname;

    @ApiModelProperty(value = "素材类型")
    @TableField("materialType")
    private String materialtype;

    @ApiModelProperty(value = "分辨率")
    @TableField("resolvingPower")
    private String resolvingpower;

    @ApiModelProperty(value = "素材大小")
    @TableField("materialSize")
    private Double materialsize;

    @ApiModelProperty(value = "引用节目数目")
    @TableField("programCount")
    private Integer programcount;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateTime")
    private Date updatetime;

    @ApiModelProperty(value = "素材地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "素材分组")
    @TableField("materialGroupID")
    private Integer materialgroupid;


}
