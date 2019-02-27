package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {
    public Role() {
    }

    public Role(String name, String dis, Integer act, Integer sta) {
        roleName = name;
        displayName = dis;
        isActive = act;
        isStatic = sta;
    }

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * name
     */
    @TableField("roleName")
    private String roleName;

    /**
     * 显示名
     */
    @TableField("displayName")
    private String displayName;

    /**
     * 创建时间
     */
    @TableField("creationTime")
    private LocalDateTime creationTime;

    /**
     * 创建人id
     */
    @TableField("creatorUserId")
    private Integer creatorUserId;

    /**
     * 启用状态
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 是否静态
     */
    @TableField("isStatic")
    private Integer isStatic;

    /**
     * 描述
     */
    private String description;

    @TableField("isDeleted")
    private Integer isDeleted;


}
