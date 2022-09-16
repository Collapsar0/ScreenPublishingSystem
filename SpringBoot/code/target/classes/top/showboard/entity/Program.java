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
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("program")
@ApiModel(value="Program对象", description="")
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节目ID")
    @TableId(value = "programID", type = IdType.AUTO)
    private Integer programid;

    @ApiModelProperty(value = "节目缩略图")
    @TableField("programPic")
    private String programpic;

    @ApiModelProperty(value = "节目名称")
    @TableField("programName")
    private String programname;

    @ApiModelProperty(value = "分辨率")
    @TableField("resolvingPower")
    private String resolvingpower;

    @ApiModelProperty(value = "节目时长")
    @TableField("programDuration")
    private String programduration;

    @ApiModelProperty(value = "节目大小")
    @TableField("programData")
    private String programdata;

    @ApiModelProperty(value = "节目状态")
    @TableField("programState")
    private String programstate;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateDate")
    private Date updatedate;

    @ApiModelProperty(value = "节目内容")
    @TableField("programContent")
    private String programcontent;


}
