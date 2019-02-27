package com.monkey.core.dtos;





/**
 * @author liugh
 */
public class NgUserModel {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    private String token;
    public NgUserModel() {
    }
    public NgUserModel(String token, UserDto user) {
        this.token = token;
    }

}
