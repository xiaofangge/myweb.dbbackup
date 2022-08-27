package com.common.vo;

import java.util.List;

public class MenuVo {
    private Long id;
    private Long pid;
    private String title;
    private String icon;
    private String href;
    private String target;
    private List<MenuVo> child;

    private MenuVo(Long id, Long pid, String title, String icon, String href, String target) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.target = target;
    }

    public static MenuVo menuVo(Long id, Long pid, String title, String icon, String href, String target) {
        return new MenuVo(id, pid, title, icon, href, target);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<MenuVo> getChild() {
        return child;
    }

    public void setChild(List<MenuVo> children) {
        this.child = children;
    }
}
