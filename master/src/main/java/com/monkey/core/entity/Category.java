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
 * @since 2018-08-27
 */
@TableName("sale_category")
@Data
public class Category implements  Serializable {
    private static final long serialVersionUID = 1L;
    public  Category(){}
    public  Category(String name){
        this.name=name;
    }
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 分类名
     */
    private String name;
    private String parent;
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
}
