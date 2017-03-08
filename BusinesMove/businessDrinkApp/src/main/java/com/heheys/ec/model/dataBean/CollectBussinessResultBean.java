package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/26 16:14
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class CollectBussinessResultBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String status;//状态码
    private CollectBussinseeResultListBean result;//返回结果
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
    public CollectBussinseeResultListBean getResult() {
        return result;
    }
    public void setResult(CollectBussinseeResultListBean result) {
        this.result = result;
    }

    public static class CollectBussinseeResultListBean implements Serializable{
        private int count;
        private List<CollectBussiness> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<CollectBussiness> getList() {
            return list;
        }

        public void setList(List<CollectBussiness> list) {
            this.list = list;
        }
    }
    public static class CollectBussiness implements Serializable{
//        result.list[i].icon
//        result.list[i].name
//        result.list[i].pronum
//        result.list[i].shopid
//        result.list[i].shopstatus
//        result.list[i].shopURL
        private String icon;
        private String name;
        private String pronum;
        private String shopid;
        private String shopstatus;
        private String shopURL;
        private String fid;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPronum() {
            return pronum;
        }

        public void setPronum(String pronum) {
            this.pronum = pronum;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getShopstatus() {
            return shopstatus;
        }

        public void setShopstatus(String shopstatus) {
            this.shopstatus = shopstatus;
        }

        public String getShopURL() {
            return shopURL;
        }

        public void setShopURL(String shopURL) {
            this.shopURL = shopURL;
        }
    }
}
