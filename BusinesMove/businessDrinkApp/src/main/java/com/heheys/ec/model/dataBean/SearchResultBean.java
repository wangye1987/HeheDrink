package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/22 15:03
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */

public class SearchResultBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;//状态码
    private SearchResultListBean result;//返回结果
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
    public SearchResultListBean getResult() {
        return result;
    }
    public void setResult(SearchResultListBean result) {
        this.result = result;
    }

    public class SearchResultListBean implements Serializable{
        private List<SearchBean> list;

        public List<SearchBean> getList() {
            return list;
        }

        public void setList(List<SearchBean> list) {
            this.list = list;
        }
    }

    public class SearchBean implements Serializable {
//        result.list[i].id	活动ID
//        result.list[i].type	活动类型	0:合伙 1:甩卖 3:发行价
//        result.list[i].title	活动名称
//        result.list[i].pic	活动图片
//        result.list[i].price	活动价格	合伙价/发行价/特卖价
//        result.list[i].soldnum	已售量
//        result.list[i].kcnum	库存量
//        result.list[i].releaseArea	发行区域
//        result.list[i].startTime	发行开始日期	时间戳
        private String id;

        private String type;
        private String unit;

        private String title;
        private String status;
        private String pic;
        private String price;
        private String soldnum;
        private String kcnum;
        private String releaseArea;
        private String diffTime;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getKcnum() {
            return kcnum;
        }

        public void setKcnum(String kcnum) {
            this.kcnum = kcnum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSoldnum() {
            return soldnum;
        }

        public void setSoldnum(String soldnum) {
            this.soldnum = soldnum;
        }

        public String getReleaseArea() {
            return releaseArea;
        }

        public void setReleaseArea(String releaseArea) {
            this.releaseArea = releaseArea;
        }

        public String getDiffTime() {
            return diffTime;
        }

        public void setDiffTime(String diffTime) {
            this.diffTime = diffTime;
        }
    }}
