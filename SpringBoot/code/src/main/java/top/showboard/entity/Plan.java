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
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plan")
@ApiModel(value="Plan对象", description="")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "计划ID")
    @TableId(value = "planID", type = IdType.AUTO)
    private Integer planid;

    @ApiModelProperty(value = "计划名称")
    @TableField("planName")
    private String planname;

    @ApiModelProperty(value = "计划缩略图")
    @TableField("planPic")
    private String planpic;

    @ApiModelProperty(value = "计划状态")
    @TableField("planState")
    private String planstate;

    @ApiModelProperty(value = "播放模式")
    @TableField("playPattern")
    private String playpattern;

    @ApiModelProperty(value = "开始日期")
    @TableField("startDate")
    private Date startdate;

    @ApiModelProperty(value = "结束日期")
    @TableField("endDate")
    private Date enddate;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "审核人")
    @TableField("checker")
    private String checker;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateDate")
    private Date updatedate;

    @ApiModelProperty(value = "计划内容")
    @TableField("planContent")
    private String plancontent;

    @ApiModelProperty(value = "设备列表")
    @TableField("equipmentList")
    private String equipmentlist;

    @ApiModelProperty(value = "播放内容Json")
    @TableField("playbackList")
    private String playbacklist;

    @ApiModelProperty(value = "开始时间")
    @TableField("startTime")
    private Date starttime;

    @ApiModelProperty(value = "结束时间")
    @TableField("endTime")
    private Date endtime;


}
