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
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户编码
     */
    private String code;

    /**
     * 客户姓名
     */
    @TableField("customerName")
    private String customerName;

    /**
     * 身份证
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 婚否
     */
    @TableField("maritalStatus")
    private Integer maritalStatus;

    /**
     * 客户级别
     */
    private String level;

    /**
     * 客户来源
     */
    private String from;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 业务类型
     */
    private String business;

    /**
     * 手机2
     */
    private String mobile2;

    /**
     * 座机
     */
    private String telphone;

    /**
     * 手机1

     */
    private String mobile1;

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
     * 创建人姓名
     */
    @TableField("creatorName")
    private String creatorName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 扩展属性
     */
    @TableField("extendField")
    private String extendField;


}
