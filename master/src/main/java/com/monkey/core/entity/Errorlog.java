package com.monkey.core.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2018-12-10
 */
@TableName("sale_errorlog")
@Data
public class Errorlog implements  Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    
    /**
     * guid
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 设备编号
     */
    private String deviceNum;
    /**
     * 设备名
     */
    private String deviceName;
    /**
     * 日志类型
     */
    private Integer logType;
    /**
     * 日志信息
     */
    private String Message;
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
     * 点位名
     */
    private String pointName;

}
