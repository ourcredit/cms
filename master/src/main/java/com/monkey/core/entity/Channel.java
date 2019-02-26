package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("sale_channel")
@Data
public class Channel implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;//key
    private String code;
    private String channelName;//渠道名
    private String mobile;//手机
    private String level;//渠道级别
    private String type;//渠道类型
    private String workUnit;//工作单位
    private String post;//岗位
    private String business;//业务
    private String address;//地址
    private String from;//来源
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
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorName;
}
