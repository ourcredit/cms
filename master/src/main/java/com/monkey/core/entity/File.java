package com.monkey.core.entity;

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
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 大小
     */
    private Long size;

    /**
     * 后缀
     */
    private String ext;

    /**
     * 路径
     */
    private String path;

    /**
     * 租户id
     */
    @TableField("tenantId")
    private Integer tenantId;

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
