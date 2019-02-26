package com.monkey.core.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@TableName("sale_user")
@Data
public class User  implements  Serializable {

    private static final long serialVersionUID = 1L;
    public  User(){}
    public  User(String account,String password,String userName,Integer isActive,Integer areaId){
        this.account=account;
        this.password=BCrypt.hashpw(password,BCrypt.gensalt());
        this.userName=userName;
        this.isActive=isActive;
        this.areaId=areaId;
    }
    public  User(String account,String password,String userName,Integer isActive){
        this.account=account;
        this.password=BCrypt.hashpw(password,BCrypt.gensalt());
        this.userName=userName;
        this.isActive=isActive;
    }
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 账户
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    private  Integer areaId;
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
     * 姓名
     */
    private String userName;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 1启用  0禁用
     */
    private Integer isActive;
    /**
     * 软删除  
     */
    private Integer isDeleted;
    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;

}
