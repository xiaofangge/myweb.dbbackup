package com.common.vo;

public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private Long count;


    public static R<Object> success(String msg) {
        return new R<>(200, msg, null, null);
    }

    public static R<Object> fail(String msg) {
        return new R<>(500, msg, null, null);
    }

    public static R<Object> layuiTable(Object data, Long count) {
        return new R<>(0, "", data, count);
    }


    private R(Integer code, String msg, T data, Long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
