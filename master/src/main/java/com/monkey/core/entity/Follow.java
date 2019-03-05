package com.monkey.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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

public class Follow implements Serializable {

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
     * 下次跟进时间
     */
    @TableField("nextfollowTime")
    private LocalDateTime nextfollowTime;

    /**
     * 跟进内容
     */
    @TableField("visitContent")
    private String visitContent;
    private  Integer time;
    /**
     * 选定跟进时间
     */
    @TableField("followTime")
    private LocalDateTime followTime;

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
    public LocalDateTime getNextfollowTime() {
        return nextfollowTime;
    }

    public void setNextfollowTime(LocalDateTime nextfollowTime) {
        this.nextfollowTime = nextfollowTime;
    }
    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent;
    }
    public LocalDateTime getFollowTime() {
        return followTime;
    }

    public void setFollowTime(LocalDateTime followTime) {
        this.followTime = followTime;
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
        return "Follow{" +
                "id=" + id +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", nextfollowTime=" + nextfollowTime +
                ", visitContent=" + visitContent +
                ", followTime=" + followTime +
                ", type=" + type +
                ", objectId=" + objectId +
                "}";
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
