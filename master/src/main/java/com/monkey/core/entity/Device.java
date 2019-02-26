package com.monkey.core.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.monkey.common.wechatsdk.PayConfig;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhaohejing
 * @since 2018-07-26
 */
@TableName("sale_device")
public class Device extends Model<Device>  {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 点位名称
     */
    private String deviceName;
    @TableField(exist = false)
    private Integer online;
     /**
     * @param online the online to set
     */
    public void setOnline(Integer online) {
        this.online = online;
    }
    public Integer getOnline(){
        return this.online;
    }
  
    @TableField(exist = false)
    private String url;
    public String getUrl() {
        return PayConfig.SOURCEURL+ "down/v_"+this.version+".apk";
    }
    /**
     * 设备编码
     */
    private String deviceNum;
    private Integer version;

    private Integer count;
     public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    /**
     * 设备类型
     */
    private String passWord;
    /**
     * 从属点位
     */
    private Integer pointId;
    private     String pointName;
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
     * 软删除  
     */
    @TableLogic
    private Integer isDeleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }



    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Integer creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Device{" +
        "id=" + id +
        ", deviceName=" + deviceName +
        ", deviceNum=" + deviceNum +
        ", passWord=" + passWord +
        ", pointId=" + pointId +
        ", creationTime=" + creationTime +
        ", creatorUserId=" + creatorUserId +
        ", isDeleted=" + isDeleted +
        "}";
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
