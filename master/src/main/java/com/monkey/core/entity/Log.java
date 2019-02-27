package com.monkey.core.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
    @TableField("creationTime")
    private LocalDateTime creationTime;

    /**
     * 创建人id
     */
    @TableField("creatorUserId")
    private Integer creatorUserId;


}
