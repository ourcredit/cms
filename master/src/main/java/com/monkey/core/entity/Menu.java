package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;



/**
 * <p>
 * <p>
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */

public class Menu implements Serializable {
    public Menu() {
    }

    public Menu(String n, String c, String u, Integer t, Integer parent) {
        name = n;
        code = c;
        url = u;
        type = t;
        parentId = parent;
    }

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 显示名
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 1 菜单 2按钮
     */
    private Integer type;

    /**
     * 权限码
     */
    private String code;

    /**
     * 父级id
     */
    @TableField("parentId")
    private Integer parentId;

    @TableField(value = "creationTime",fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

     @TableField(value = "creatorUserId",fill = FieldFill.INSERT)
    private Integer creatorUserId;

    @TableField("isDeleted")
    private Integer isDeleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name=" + name +
                ", url=" + url +
                ", type=" + type +
                ", code=" + code +
                ", parentId=" + parentId +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
