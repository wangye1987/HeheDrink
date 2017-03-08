package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * 作者：wangkui on 2016/12/23 11:26
 * 邮箱：wangkui20090909@sina.com
 * 说明:扫描返回结果
 */

public class ScanResultBean implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String status;//状态码
    private ResultScan result;//返回结果
    private ErrorScan error;//返回失败结果
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public ErrorScan getError() {
        return error;
    }
    public void setError(ErrorScan error) {
        this.error = error;
    }
    public ResultScan getResult() {
        return result;
    }
    public void setResult(ResultScan result) {
        this.result = result;
    }

    public class ResultScan implements Serializable{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public static class ErrorScan implements Serializable{
        /*错误代码*/
        private String code;
        /*错误信息*/
        private String info;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
