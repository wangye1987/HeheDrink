package com.heheys.ec.model.dataBean;

import java.io.Serializable;

/**
 * 作者：wangkui on 2016/12/29 11:11
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class BuyMoreBean implements Serializable{

    private static final long serialVersionUID = 1L;
    private String status;//状态码
    private BaseResult result;//返回结果
    private BaseErrorBean error;//返回失败结果
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public BaseErrorBean getError() {
        return error;
    }
    public void setError(BaseErrorBean error) {
        this.error = error;
    }
    public BaseResult getResult() {
        return result;
    }
    public void setResult(BaseResult result) {
        this.result = result;
    }

    public class BaseResult implements Serializable {
          String tips;

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
    public class BaseErrorBean implements Serializable{
        private String code;
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
