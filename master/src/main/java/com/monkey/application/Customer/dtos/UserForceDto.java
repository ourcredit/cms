package com.monkey.application.Customer.dtos;

public class UserForceDto {
    private  Integer id;
    private String userName;
    private String mobile;
    private String orgName;
    private String showMonth;
    private double showForce;
    private double comeTrue;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }



    public double getComeTrue() {
        return comeTrue;
    }

    public void setComeTrue(double comeTrue) {
        this.comeTrue = comeTrue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public double getShowForce() {
        return showForce;
    }

    public void setShowForce(double showForce) {
        this.showForce = showForce;
    }
}
