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
@TableName("sale_menu")
@Data
public class Menu  implements  Serializable {

    private static final long serialVersionUID = 1L;

    public  Menu(){}
    public  Menu(String name,String code,String url,Integer type,Integer parentId){
        this.name=name;
        this.code=code;
        this.url=url;
        this.type=type;
        this.parentId=parentId;
    }

    /**
     * 软删除
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 显示名
     */
    private String name;
    /**
     * url
     */
    private String url;
    /**
     * 1 菜单 2按钮
     */
    private Integer type;
    /**
     * 权限码
     */
    private String code;
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
