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

    @TableField("creationTime")
    private LocalDateTime creationTime;

    @TableField("creatorUserId")
    private Integer creatorUserId;

    @TableField("isDeleted")
    private Integer isDeleted;


}
