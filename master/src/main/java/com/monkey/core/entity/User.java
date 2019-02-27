package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mindrot.jbcrypt.BCrypt;

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
    @TableField("creatorUserId")
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
}
