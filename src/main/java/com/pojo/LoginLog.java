package com.pojo;


import java.util.Date;

public class LoginLog {

    /**
     * TODO 日志ID
     */
    private Integer id;

    /**
     * TODO 登录用户名
     */
    private String username;

    /**
     * TODO 登录时间
     */
    private Date loginTime;

    /**
     * TODO 登录IP
     */
    private String loginIp;

    /**
     * TODO 浏览器信息
     */
    private String browserInfo;

    /**
     * TODO 登录状态
     */
    private Byte loginState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime == null ? null : (Date) loginTime.clone();
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime == null ? null : (Date) loginTime.clone();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    public Byte getLoginState() {
        return loginState;
    }

    public void setLoginState(Byte loginState) {
        this.loginState = loginState;
    }
}
