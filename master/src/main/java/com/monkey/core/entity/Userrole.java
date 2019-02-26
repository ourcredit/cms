package com.monkey.core.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName("sale_userrole")
@Data
public class Userrole implements Serializable {

    private static final long serialVersionUID = 1L;
    public  Userrole(){
    }
    public  Userrole(Integer userId,Integer roleId){
    this.userId=userId;
    this.roleId=roleId;
    }

    /**
     * key
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    private Integer userId;
    private Integer roleId;
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
