package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2019-03-13
 */
@TableName("saleForce")
public class SaleForce implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 创建时间
     */
    @TableField(value = "creationTime",fill = FieldFill.INSERT)
    private LocalDateTime creationTime;

    /**
     * 创建人id
     */
    @TableField(value = "creatorUserId",fill = FieldFill.INSERT)
    private Integer creatorUserId;

    /**
     * 从属用户id
     */
    @TableField("userId")
    private Integer userId;

    @TableField("showMonth")
    private String showMonth;

    @TableField("showForce")
    private Double showForce;

    /**
     * 实现
     */
    @TableField("comeTrue")
    private Double comeTrue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
    public Integer getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Integer creatorUserId) {
        this.creatorUserId = creatorUserId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setComeTrue(Double comeTrue) {
        this.comeTrue = comeTrue;
    }

    @Override
    public String toString() {
        return "SaleForce{" +
        "id=" + id +
        ", creationTime=" + creationTime +
        ", creatorUserId=" + creatorUserId +
        ", userId=" + userId +
        ", showMonth=" + getShowMonth() +
        ", showForce=" + getShowForce() +
        ", comeTrue=" + comeTrue +
        "}";
    }

    /**
     * 年月
     */
    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    /**
     * 目标
     */
    public Double getShowForce() {
        return showForce;
    }

    public void setShowForce(Double showForce) {
        this.showForce = showForce;
    }
}
