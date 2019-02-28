package com.monkey.core.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */
@TableName("userrole")
public class UserRole implements Serializable {
    public UserRole(){}
    public UserRole(Integer uId, Integer rId){
        userId=uId;
        roleId=rId;
    }
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("userId")
    private Integer userId;

    @TableField("roleId")
    private Integer roleId;

    @TableField(value = "creationTime",fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

     @TableField(value = "creatorUserId",fill = FieldFill.INSERT)
    private Integer creatorUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
        return "Userrole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                "}";
    }

}
