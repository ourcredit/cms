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
 * @since 2019-03-14
 */
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String number;

    /**
     * 合同编码
     */
    @TableField("contractCode")
    private String contractCode;

    /**
     * 客户编码
     */
    @TableField("customerCode")
    private String customerCode;

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

    /**
     * 客户来源
     */
    private String resource;

    /**
     * 渠道名
     */
    @TableField("channelName")
    private String channelName;

    /**
     * 渠道
     */
    private Integer channel;

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
     * 团队
     */
    private String term;

    /**
     * 部门
     */
    @TableField("orgName")
    private String orgName;
    @TableField("channelMobile")
    private String channelMobile;

    /**
     * 是否共享
     */
    @TableField("isShare")
    private Integer isShare;

    /**
     * 贷款对象
     */
    private String daikuanxinxi;

    /**
     * 服务费
     */
    private String fuwufei;
    private String type;
    @TableField("customerId")
    private String customerId;

    /**
     * 合作机构
     */
    private String hezuojigou;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
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
    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }
    public String getDaikuanxinxi() {
        return daikuanxinxi;
    }

    public void setDaikuanxinxi(String daikuanxinxi) {
        this.daikuanxinxi = daikuanxinxi;
    }
    public String getFuwufei() {
        return fuwufei;
    }

    public void setFuwufei(String fuwufei) {
        this.fuwufei = fuwufei;
    }
    public String getHezuojigou() {
        return hezuojigou;
    }

    public void setHezuojigou(String hezuojigou) {
        this.hezuojigou = hezuojigou;
    }

    @Override
    public String toString() {
        return "Contract{" +
        "id=" + id +
        ", number=" + number +
        ", contractCode=" + contractCode +
        ", customerCode=" + customerCode +
        ", customerName=" + customerName +
        ", idCard=" + idCard +
        ", gender=" + gender +
        ", maritalStatus=" + maritalStatus +
        ", level=" + level +
        ", resource=" + resource +
        ", channelName=" + channelName +
        ", channel=" + channel +
        ", business=" + business +
        ", mobile2=" + mobile2 +
        ", telphone=" + telphone +
        ", mobile1=" + mobile1 +
        ", creationTime=" + creationTime +
        ", creatorUserId=" + creatorUserId +
        ", creatorName=" + creatorName +
        ", term=" + term +
        ", orgName=" + orgName +
        ", isShare=" + isShare +
        ", daikuanxinxi=" + daikuanxinxi +
        ", fuwufei=" + fuwufei +
        ", hezuojigou=" + hezuojigou +
        "}";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChannelMobile() {
        return channelMobile;
    }

    public void setChannelMobile(String channelMobile) {
        this.channelMobile = channelMobile;
    }
}
