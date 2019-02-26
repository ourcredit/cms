package com.monkey.core.dtos;

public class TodayStatical{
    private double sale;
    private  Integer count;
    private  Integer device;
    private  Integer error;
    private  double off;

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public double getOff() {
        return off;
    }

    public void setOff(double off) {
        this.off = off;
    }
}
