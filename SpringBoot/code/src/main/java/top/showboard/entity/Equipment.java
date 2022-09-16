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
 * @since 2022-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("equipment")
@ApiModel(value="Equipment对象", description="")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId(value = "equipmentID", type = IdType.AUTO)
    private Integer equipmentid;

    @ApiModelProperty(value = "设备名称")
    @TableField("equipmentName")
    private String equipmentname;

    @ApiModelProperty(value = "设备型号")
    @TableField("equipmentType")
    private String equipmenttype;

    @ApiModelProperty(value = "所属机构")
    @TableField("organization")
    private Integer organization;

    @ApiModelProperty(value = "设备IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "有线Mac地址")
    @TableField("macAddressWired")
    private String macaddresswired;

    @ApiModelProperty(value = "无线Mac地址")
    @TableField("macAddressWireless")
    private String macaddresswireless;

    @ApiModelProperty(value = "分辨率")
    @TableField("resolvingPower")
    private String resolvingpower;

    @ApiModelProperty(value = "设备状态")
    @TableField("equipmentState")
    private String equipmentstate;

    @ApiModelProperty(value = "系统版本")
    @TableField("systemEdition")
    private String systemedition;

    @ApiModelProperty(value = "系统更新")
    @TableField("systemUpdate")
    private String systemupdate;

    @ApiModelProperty(value = "信发版本")
    @TableField("sendingEdition")
    private String sendingedition;

    @ApiModelProperty(value = "当前计划")
    @TableField("nowPlan")
    private String nowplan;

    @ApiModelProperty(value = "所属分组")
    @TableField("equipmentGroupID")
    private Integer equipmentGroupID;

    @ApiModelProperty(value = "SN号")
    @TableField("SNNumber")
    private String snnumber;

    @ApiModelProperty(value = "运行内存")
    @TableField("ram")
    private String ram;

    @ApiModelProperty(value = "储存空间")
    @TableField("rom")
    private String rom;

    @ApiModelProperty(value = "激活时间")
    @TableField("activationTime")
    private Date activationtime;

    @ApiModelProperty(value = "注册时间")
    @TableField("registerTime")
    private Date registertime;

    @ApiModelProperty(value = "最后心跳时间")
    @TableField("lastHeartbeatTime")
    private Date lastheartbeattime;

    @ApiModelProperty(value = "屏显方式")
    @TableField("screenDisplay")
    private String screendisplay;

    @ApiModelProperty(value = "安装位置")
    @TableField("installationPosition")
    private String installationposition;

    @ApiModelProperty(value = "设备运行时长")
    @TableField("runTime")
    private Date runtime;


}
