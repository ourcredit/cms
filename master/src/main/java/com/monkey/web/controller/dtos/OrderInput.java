package com.monkey.web.controller.dtos;


public class OrderInput {

    /**
     * 烘干类型(纯棉 化纤 其他)
     */
    public Integer dryType;
    /**
     * 设备id
     */
    public String deviceNum;
    /**
     * 支付类型  微信 支付宝
     */
    public Integer payType;
}
