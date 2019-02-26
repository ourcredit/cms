package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-27
 */
@TableName("sale_log")
@Data
public class Log  implements  Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 日志类型
     */
    private String logDescription;
    /**
     * 日志名称
     */
    private String actionArgs;
    /**
     * 用户id
     */
    private String userName;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String method;
    /**
     * ip
     */
    private String ip;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date creationTime;
    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorUserId;
    /**
     * 是否成功
     */
    private String succeed;
    /**
     * 备注
     */
    private String message;
}
