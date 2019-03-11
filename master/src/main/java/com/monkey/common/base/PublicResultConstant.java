package com.monkey.common.base;

/**
 * @author liugh
 * @since 2018-05-03
 */
public enum PublicResultConstant {

    /**
     * 异常
     */
    FAILED("90000001", "系统错误"),
    /**
     * 成功
     */
    SUCCESS("00000000", "success"),
    /**
     * 未登录/token过期
     */
    UNAUTHORIZED("90000002", "获取登录用户信息失败"),
    /**
     * 失败
     */
    ERROR("90000000", "操作失败"),
    /**
     * 失败
     */
    PARAM_ERROR("90000003", "参数错误"),

    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD("10000003", "用户名或密码错误"),
    INVALID_TENANT_NAME("10000099", "code不能为空"),
    INVALID_TENANT_STATE("10000098", "租户被禁用"),
    /**
     *
     */
    INVALID_RE_PASSWORD("10000010", "两次输入密码不一致"),
    /**
     * 用户名或密码错误
     */
    INVALID_PASSWORD("10000009", "旧密码错误"),
    /**
     * 用户名重复
     */
    USERNAME_ALREADY_IN("10000002", "用户已存在"),
    /**
     * 用户不存在
     */
    INVALID_USER("10000001", "用户不存在"),
    /**
     * 角色不存在
     */
    INVALID_ROLE("10000004", "角色不存在"),

    /**
     * 角色不存在
     */
    ROLE_USER_USED("10000008", "角色使用中，不可删除"),

    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST("10000005", "请求参数已存在"),
    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY("10000006", "请求参数为空"),
    /**
     * 没有权限
     */
    USER_NO_PERMITION("10000007", "当前用户无该接口权限"),
    CREATE_ORDER_ERROR("10000008", "创建订单失败"),
    ORDER_HAD_BIND("10001001", "订单已经被绑定"),
    ORDER_NOT_PAY("10001002", "该订单尚未支付"),
    ORDER_NOT_FOUND("10001004", "设备订单不存在"),
    DEVICE_OFFLINE("10001003", "设备不在线"),
    USER_BALANCE_EMPTY("10001003", "用户余额不足"),
    /**
     * 没有权限
     */
    NORESULTS("11000007", "暂无数据"),
    TOMUCHCOUNT("11000000", "超过组织机构最大层级")
    ;


    public String result;
    public String msg;

    PublicResultConstant(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
