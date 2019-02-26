package com.monkey.web.controller.dtos;

import java.util.Date;

public class StaticalInput {
    public StaticalInput(){}
    public StaticalInput(String point,Date start,Date end){
        this.pointName=point;
        this.start=start;
        this.end=end;
    }
    public String deviceName;
    public String pointName;
    public String productName;
    public Date start;
    public Date end;
}
