package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangkui on 2016/12/15.
 */

public class SeachBaseBean implements Serializable {
    private String status;// 状态码
    private BrandResultBean result;// 返回结果
    private ErrorBean error;// 返回失败结果

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BrandResultBean getResult() {
        return result;
    }

    public void setResult(BrandResultBean result) {
        this.result = result;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public class BrandResultBean implements Serializable{
           private List<BrandSearchList> list;

        public List<BrandSearchList> getList() {
            return list;
        }

        public void setList(List<BrandSearchList> list) {
            this.list = list;
        }
    }

    public class BrandSearchList implements Serializable{
        private String name;
        private String logo;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
