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
@TableName("user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "userID", type = IdType.AUTO)
    private Integer userid;

    @ApiModelProperty(value = "用户账号(账户名)")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "账号状态")
    @TableField("accountState")
    private String accountstate;

    @ApiModelProperty(value = "所属组织")
    @TableField("organization")
    private String organization;

    @ApiModelProperty(value = "所属角色")
    @TableField("role")
    private String role;

    @ApiModelProperty(value = "真实姓名")
    @TableField("realName")
    private String realname;

    @ApiModelProperty(value = "电话")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "更新时间")
    @TableField("updateDate")
    private Date updatedate;

    @ApiModelProperty(value = "用户类型")
    @TableField("userType")
    private String usertype;


}
