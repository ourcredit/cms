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
public class Follow implements Serializable {

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
     * 下次跟进时间
     */
    @TableField("nextfollowTime")
    private LocalDateTime nextfollowTime;

    /**
     * 跟进内容
     */
    @TableField("visitContent")
    private String visitContent;

    /**
     * 选定跟进时间
     */
    @TableField("followTime")
    private LocalDateTime followTime;

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
