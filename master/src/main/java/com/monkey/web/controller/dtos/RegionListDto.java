package com.monkey.web.controller.dtos;


import java.sql.Timestamp;
import java.util.Date;

public class RegionListDto {
    private Integer id;
    private String provence;
    private String city;
    private  String country;
    private Integer price;
    private Date creationTime;
    private String code;
    private Integer enable;
    public Integer getId() {
        return id;
    }

    /**
     * @return the enable
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
