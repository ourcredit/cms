package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2019-02-27
 */

public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    /**
     * 拜访时间
     */
    @TableField("visitTime")
    private LocalDateTime visitTime;

    /**
     * 拜访地点
     */
    @TableField("visitPlace")
    private String visitPlace;

    /**
     * 拜访目的
     */
    @TableField("visitForce")
    private String visitForce;

    /**
     * 拜访方式
     */
    @TableField("visitType")
    private String visitType;

    /**
     * 拜访结果
     */
    @TableField("visitResult")
    private String visitResult;

    /**
     * 类型字段
     */
    private Integer type;

    /**
     * 对应类型的主id
     */
    @TableField("objectId")
    private Integer objectId;

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
    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }
    public String getVisitPlace() {
        return visitPlace;
    }

    public void setVisitPlace(String visitPlace) {
        this.visitPlace = visitPlace;
    }
    public String getVisitForce() {
        return visitForce;
    }

    public void setVisitForce(String visitForce) {
        this.visitForce = visitForce;
    }
    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }
    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", visitTime=" + visitTime +
                ", visitPlace=" + visitPlace +
                ", visitForce=" + visitForce +
                ", visitType=" + visitType +
                ", visitResult=" + visitResult +
                ", type=" + type +
                ", objectId=" + objectId +
                "}";
    }

}
