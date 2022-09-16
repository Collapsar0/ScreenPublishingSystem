package top.showboard.response;

/**
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后续可补充
 */
public enum ResultCode implements IResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    ERROR(201, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    NO_SYS_USER_ACCOUNT(2002,"非管理员账户"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    DIFFERENT_PASSWORD(2004,"密码不一致"),
    USER_ACCOUNT_EXIST(2005, "用户已存在"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),
    NO_RIGHT_DATA(3002,"不存在符合要求的数据");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}