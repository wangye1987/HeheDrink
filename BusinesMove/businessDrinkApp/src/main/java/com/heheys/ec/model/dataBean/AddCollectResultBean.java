package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * 作者：wangkui on 2016/12/26 13:54
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class AddCollectResultBean implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String status;//状态码
    private AddResultBean result;//返回结果
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
    public AddResultBean getResult() {
        return result;
    }
    public void setResult(AddResultBean result) {
        this.result = result;
    }
    public static class AddResultBean implements Serializable{
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
