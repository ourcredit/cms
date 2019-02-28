package com.monkey.core.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;


/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */

public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 日志类型
     */
    @TableField("logDescription")
    private String logDescription;

    /**
     * 日志名称
     */
    @TableField("actionArgs")
    private String actionArgs;

    /**
     * 用户id
     */
    @TableField("userName")
    private String userName;

    /**
     * 类名称
     */
    @TableField("className")
    private String className;

    /**
     * 方法名称
     */
    private String method;

    private String ip;

    /**
     * 是否成功
     */
    private String succeed;

    /**
     * 备注
     */
    private String message;

    /**
     * 创建时间
     */
    @TableField(value = "creationTime",fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

    /**
     * 创建人id
     */
     @TableField(value = "creatorUserId",fill = FieldFill.INSERT)
    private Integer creatorUserId;

    public void setId(String id) {
        this.id = id;
    }
    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }
    public String getActionArgs() {
        return actionArgs;
    }

    public void setActionArgs(String actionArgs) {
        this.actionArgs = actionArgs;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
    public Integer getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Integer creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", logDescription=" + logDescription +
                ", actionArgs=" + actionArgs +
                ", userName=" + userName +
                ", className=" + className +
                ", method=" + method +
                ", ip=" + ip +
                ", succeed=" + succeed +
                ", message=" + message +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                "}";
    }

}
