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
 * @author zhaohejing123
 * @since 2018-06-05
 */
@TableName("sale_role")
@Data
public class Role implements  Serializable {

    private static final long serialVersionUID = 1L;
    public Role(){}
    public Role( String roleName,String displayName,Integer isActive,Integer isStatic){
        this.roleName=roleName;
        this.displayName=displayName;
        this.isActive=isActive;
        this.isStatic=isStatic;
        this.isDeleted=0;
    }

    /**
     * 软删除
     */
    @TableLogic
    private Integer isDeleted;


    private Integer isStatic;
    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * name
     */
    private String roleName;
    private String description;
    /**
     * 显示名
     */
    private String displayName;
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


    private Integer isActive;

}
