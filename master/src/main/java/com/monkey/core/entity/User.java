package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;


import org.mindrot.jbcrypt.BCrypt;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */

public class User implements Serializable {
    public User(String acc, String pwd, String name, Integer active) {
        account = acc;
        password = BCrypt.hashpw(pwd,BCrypt.gensalt());
        userName = name;
        isActive = active;
    }

    public User(String acc, String pwd, String name, Integer active, Integer aId) {
        account = acc;
        password = BCrypt.hashpw(pwd,BCrypt.gensalt());
        userName = name;
        isActive = active;
        areaId = aId;
    }

    public User() {
    }

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    @TableField("creationTime")
    private LocalDateTime creationTime;

    /**
     * 创建人id
     */
     @TableField(value = "creatorUserId",fill = FieldFill.INSERT)
    private Integer creatorUserId;

    /**
     * 姓名
     */
    @TableField("userName")
    private String userName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 1启用  0禁用
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 软删除
     */
    @TableField("isDeleted")
    private Integer isDeleted;

    /**
     * 最后登陆时间
     */
    @TableField("lastLoginTime")
    private LocalDateTime lastLoginTime;

    /**
     * 区域id
     */
    @TableField("areaId")
    private Integer areaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account=" + account +
                ", password=" + password +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", userName=" + userName +
                ", mobile=" + mobile +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", lastLoginTime=" + lastLoginTime +
                ", areaId=" + areaId +
                "}";
    }
}
