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
@TableName("notice")
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告ID")
    @TableId(value = "noticeID", type = IdType.AUTO)
    private Integer noticeid;

    @ApiModelProperty(value = "公告标题")
    @TableField("noticeTitle")
    private String noticetitle;

    @ApiModelProperty(value = "公告内容")
    @TableField("noticeContent")
    private String noticecontent;

    @ApiModelProperty(value = "公告状态")
    @TableField("noticeState")
    private String noticestate;

    @ApiModelProperty(value = "公告作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "播放时间")
    @TableField("playbackTime")
    private Date playbacktime;

    @ApiModelProperty(value = "开始时间")
    @TableField("startTime")
    private Date starttime;

    @ApiModelProperty(value = "结束时间")
    @TableField("endTime")
    private Date endtime;

    @ApiModelProperty(value = "播放模式")
    @TableField("playbackMode")
    private String playbackmode;

    @ApiModelProperty(value = "播放速度")
    @TableField("playbackSpeed")
    private String playbackspeed;

    @ApiModelProperty(value = "文字位置")
    @TableField("textPosition")
    private String textposition;

    @ApiModelProperty(value = "文字颜色")
    @TableField("textColor")
    private String textcolor;

    @ApiModelProperty(value = "文字字号")
    @TableField("textSize")
    private Integer textsize;

    @ApiModelProperty(value = "背景颜色")
    @TableField("backgroundColor")
    private String backgroundcolor;

    @ApiModelProperty(value = "背景高度")
    @TableField("backgroundHeight")
    private Integer backgroundheight;

    @ApiModelProperty(value = "设备id列表")
    @TableField("equipmentIDList")
    private String equipmentIDList;
}
