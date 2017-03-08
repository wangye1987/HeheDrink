package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * Created by wangkui on 2016/12/8.
 */

public class HeartBaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;//状态码
    private Result result;//返回结果
    private ErrorBean error;//返回失败结果
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public ErrorBean getError() {
        return error;
    }
    public void setError(ErrorBean error) {
        this.error = error;
    }
    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }

    public class Result implements Serializable{

    }
}
