package com.pojo;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    /**
     * TODO 日志ID
     */
    private Long id;
    /**
     * TODO 操作用户ID
     */
    private Integer userId;
    /**
     * TODO 消息
     */
    private String message;
    /**
     * TODO 日志类型
     */
    private String operation;
    /**
     * TODO 请求方法
     */
    private String method;
    /**
     * TODO 请求参数
     */
    private String params;
    /**
     * TODO 请求IP
     */
    private String ip;

    /**
     * TODO 请求路由
     */
    private String requestUrl;

    /**
     * TODO 请求方式
     */
    private String requestWay;
    /**
     * TODO 请求时间
     */
    private Date requestTime;
    /**
     * TODO 总耗时长
     */
    private Long totalTime;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestWay() {
        return requestWay;
    }

    public void setRequestWay(String requestWay) {
        this.requestWay = requestWay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getRequestTime() {
        return requestTime == null ? null : (Date) requestTime.clone();
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime == null ? null : (Date) requestTime.clone();
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
}
