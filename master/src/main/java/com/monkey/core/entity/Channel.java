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
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 渠道名
     */
    @TableField("channelName")
    private String channelName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 级别
     */
    private String level;

    /**
     * 类型
     */
    private String type;

    /**
     * 工作单位
     */
    @TableField("workUnit")
    private String workUnit;

    /**
     * 岗位
     */
    private String post;

    /**
     * 业务类型
     */
    private String business;

    /**
     * 地址
     */
    private String address;

    /**
     * 来源
     */
    private String from;

    /**
     * 编码
     */
    private String code;

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


}
