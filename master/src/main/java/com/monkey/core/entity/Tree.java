package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2018-08-02
 */
@TableName("sale_tree")
@Data
public class Tree implements Serializable {

    private static final long serialVersionUID = 1L;
    public Tree(){}
    public Tree(String name,Integer parentId,String code){
        this.name=name;
        this.parentId=parentId;
        this.levelCode=code;
    }
    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 节点名
     */
    private String name;
    private String levelCode;
    /**
     * 父级id
     */
    private Integer parentId;
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
