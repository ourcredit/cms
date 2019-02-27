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
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 拜访时间
     */
    @TableField("visitTime")
    private LocalDateTime visitTime;

    /**
     * 拜访地点
     */
    @TableField("visitPlace")
    private String visitPlace;

    /**
     * 拜访目的
     */
    @TableField("visitForce")
    private String visitForce;

    /**
     * 拜访方式
     */
    @TableField("visitType")
    private String visitType;

    /**
     * 拜访结果
     */
    @TableField("visitResult")
    private String visitResult;

    /**
     * 类型字段
     */
    private Integer type;

    /**
     * 对应类型的主id
     */
    @TableField("objectId")
    private Integer objectId;


}
