package com.heheys.ec.model.dataBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/26 10:33
 * 邮箱：wangkui20090909@sina.com
 * 说明:
 */


public class CollectGoodsResultBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;//状态码
    private CollectGoodResultListBean result;//返回结果
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
    public CollectGoodResultListBean getResult() {
        return result;
    }
    public void setResult(CollectGoodResultListBean result) {
        this.result = result;
    }

    public static class CollectGoodResultListBean implements Serializable {
        private List<CollectGoods> list;
        private int count;
        public List<CollectGoods> getList() {
            return list;
        }

        public void setList(List<CollectGoods> list) {
            this.list = list;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public static class CollectGoods implements Serializable{
            private String name;
            private String pic;
            private String id;
            private String type;
            private String price;
            private String soldnum;
            private String kcnum;
            private String releaseArea;
            private String status;
            private String endTime;
            private String unit;
            private String diffTime;
            private String fid;//收藏ID
            private boolean isCheck;//是否选择

            public String getDiffTime() {
                return diffTime;
            }

            public void setDiffTime(String diffTime) {
                this.diffTime = diffTime;
            }

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

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getKcnum() {
                return kcnum;
            }

            public void setKcnum(String kcnum) {
                this.kcnum = kcnum;
            }

            public String getReleaseArea() {
                return releaseArea;
            }

            public void setReleaseArea(String releaseArea) {
                this.releaseArea = releaseArea;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
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
        }
    }
        //    result.count
//    result.list[i].name
//    result.list[i].price
//    result.list[i].pic
//    result.list[i].id
//    result.list[i].type
//    result.list[i].price
//    result.list[i].soldnum
//    result.list[i].kcnum
//    result.list[i].releaseArea
//    result.list[i].status
//    result.list[i].endTime
}