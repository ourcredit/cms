package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("roleMenu")
public class RoleMenu implements Serializable {

    public RoleMenu(Integer r, Integer m) {
        roleId = r;
        menuId = m;
    }

    private static final long serialVersionUID = 1L;

    /**
     * guid
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField("roleId")
    private Integer roleId;

    @TableField("menuId")
    private Integer menuId;

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


}
