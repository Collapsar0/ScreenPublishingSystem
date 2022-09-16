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
 * @since 2022-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("planexamine")
@ApiModel(value="Planexamine对象", description="")
public class Planexamine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划审核序号")
    @TableId(value = "planExamineID", type = IdType.AUTO)
    private Integer planexamineid;

    @ApiModelProperty(value = "缩略图")
    @TableField("planExaminePic")
    private String planexaminepic;

    @ApiModelProperty(value = "使用设备数")
    @TableField("usedEquipmentCount")
    private Integer usedequipmentcount;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateDate")
    private Date updatedate;


}
