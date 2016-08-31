package com.wonders.xlab.cardbag.data.entity;

/**
 * Created by hua on 16/8/31.
 * LeanCloud 错误信息
 */

public class LCBaseEntity {

    /**
     * code : 401
     * error : Unauthorized.
     */

    private int code;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
