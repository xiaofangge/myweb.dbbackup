package com.pojo;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable {
    private MenuKey menuKey;

    private Long pid;
    private String icon;
    private String target;
    private Integer sort;
    private Byte status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;

    public MenuKey getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(MenuKey menuKey) {
        this.menuKey = menuKey;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime == null ? null : (Date) createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime == null ? null : (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return updateTime == null ? null : (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
    }

    public Date getDeleteTime() {
        return deleteTime == null ? null : (Date) deleteTime.clone();
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime == null ? null : (Date) deleteTime.clone();
    }
}
