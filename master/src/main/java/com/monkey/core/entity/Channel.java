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
 * @since 2019-02-27
 */
@TableName("channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 渠道名
     */
    @TableField("channelName")
    private String channelName;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 级别
     */
    private String level;

    /**
     * 类型
     */
    private String type;

    /**
     * 工作单位
     */
    @TableField("workUnit")
    private String workUnit;

    /**
     * 岗位
     */
    private String post;

    /**
     * 业务类型
     */
    private String business;

    /**
     * 地址
     */
    private String address;
    //来源
    private String source;

    /**
     * 编码
     */
    private String code;

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
     * 创建人姓名
     */
    @TableField("creatorName")
    private String creatorName;
    @TableField("orgName")
    private  String orgName;


    @TableField("visitTime")
    private  LocalDateTime visitTime;
    @TableField("visitCount")
    private  String visitCount;
    @TableField("lunchTime")
    private  LocalDateTime lunchTime;
    @TableField("lunchCount")
    private  String lunchCount;
    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", channelName=" + channelName +
                ", mobile=" + mobile +
                ", level=" + level +
                ", type=" + type +
                ", workUnit=" + workUnit +
                ", post=" + post +
                ", business=" + business +
                ", address=" + address +
                ", source=" + getSource() +
                ", code=" + code +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", creatorName=" + creatorName +
                ", remark=" + remark +
                "}";
    }

    /**
     * 来源
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(String visitCount) {
        this.visitCount = visitCount;
    }

    public LocalDateTime getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(LocalDateTime lunchTime) {
        this.lunchTime = lunchTime;
    }

    public String getLunchCount() {
        return lunchCount;
    }

    public void setLunchCount(String lunchCount) {
        this.lunchCount = lunchCount;
    }
}
