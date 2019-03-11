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
@TableName("customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户编码
     */
    private String code;

    /**
     * 客户姓名
     */
    @TableField("customerName")
    private String customerName;

    /**
     * 身份证
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 性别
     */
    private String gender;

    /**
     * 婚否
     */
    @TableField("maritalStatus")
    private String maritalStatus;

    /**
     * 客户级别
     */
    private String level;

    private String resource;

    private Integer channel;

    @TableField("channelName")
    private String channelName;

    /**
     * 业务类型
     */
    private String business;

    /**
     * 手机2
     */
    private String mobile2;

    /**
     * 座机
     */
    private String telphone;

    /**
     * 手机1

     */
    private String mobile1;

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
    /**
     * 渠道电话
     */
    @TableField("channelMobile")
    private String channelMobile;
    /**
     * 备注
     */
    private String remark;

    private  String term;
    private  String department;
    /**
     * 扩展属性
     */
    @TableField("extendField")
    private String extendField;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }
    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
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
    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", code=" + code +
                ", customerName=" + customerName +
                ", idCard=" + idCard +
                ", gender=" + gender +
                ", maritalStatus=" + maritalStatus +
                ", level=" + level +
                ", resource=" + getResource() +
                ", channel=" + getChannel() +
                ", business=" + business +
                ", mobile2=" + mobile2 +
                ", telphone=" + telphone +
                ", mobile1=" + mobile1 +
                ", creationTime=" + creationTime +
                ", creatorUserId=" + creatorUserId +
                ", creatorName=" + creatorName +
                ", remark=" + remark +
                ", extendField=" + extendField +
                "}";
    }

    /**
     * 客户来源
     */
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getChannelMobile() {
        return channelMobile;
    }

    public void setChannelMobile(String channelMobile) {
        this.channelMobile = channelMobile;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 渠道
     */
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
