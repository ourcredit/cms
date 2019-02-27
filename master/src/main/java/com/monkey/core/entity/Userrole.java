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
public class Userrole implements Serializable {
    public Userrole(){}
    public Userrole(Integer uId,Integer rId){
        userId=uId;
        roleId=rId;
    }
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String id;

    @TableField("userId")
    private Integer userId;

    @TableField("roleId")
    private Integer roleId;

    @TableField("creationTime")
    private LocalDateTime creationTime;

    @TableField("creatorUserId")
    private Integer creatorUserId;


}
