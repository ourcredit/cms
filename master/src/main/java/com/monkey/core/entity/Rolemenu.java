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
@TableName("sale_rolemenu")
@Data
public class Rolemenu implements  Serializable {

    private static final long serialVersionUID = 1L;
    public Rolemenu(){
    }
    public Rolemenu(Integer roleId,Integer menuId){
        this.roleId=roleId;
        this.menuId=menuId;
    }

    /**
     * guid
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private Integer roleId;
    private Integer menuId;


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
