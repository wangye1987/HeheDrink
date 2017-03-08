package com.heheys.ec.utils; 

import android.view.View;
import android.widget.ImageView;

import com.heheys.ec.R;
import com.heheys.ec.lib.utils.StringUtil;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-10-16 下午1:52:31 
 * 类说明 
 * @param
 */
public class StatusString {

//	1待付款(1定金,2尾款,3全款) 5已支付(尾款,全款)
//	 5 已支付 6 备货中 7待发货 8 已完成 9 进行中 10 受理中 异常 21 已取消 22已退款 23已失效 24 已退货 
	
//	1 待付款(定金)，9 进行中，--成单操作-- 2 待付款(尾款)，-3 待付款(全额)-，--付款结束-- 10 受理中 ，--受理操作-- 6 备货中，--发货操作-- 7待收货， --确认收货-- 8 已完成  
//	 * 异常: 21 已取消 22已退款
	public static String getStatus(int status){
		switch (status) {
		case 1:
			return "付订金";
		case 2:
			return "付尾款";
		case 3:
			return "待付全款";
		case 5:
			return "已支付";
		case 6:
			return "备货中";
		case 7:
			return "等待收货";
		case 8:
			return "已完成";
		case 9:
			return "进行中";
		case 10:
			return "受理中 异常 ";
		case 21:
			return "已取消";
		case 22:
			return "已退款 ";
		case 23:
			return "已失效 ";
		case 24:
			return "已退货 ";
			
		default:
			break;
		}
		return "";
	}
	
	//盖戳
	public static void IsActivity(String status,ImageView iv,String type){
		if("2".equals(status)){
			/*
			 * 已抢光
			 * */
			if("1".equals(type)){
				iv.setImageResource(R.drawable.yishouqing);
			}else{
			   iv.setImageResource(R.drawable.yiqiangguang);
			}
		}else if("4".equals(status)){
			/*
			 * 已成单
			 * */
			iv.setImageResource(R.drawable.yichengdan);
		}else if("3".equals(status)){
			/*
			 * 未成单
			 * */
			iv.setImageResource(R.drawable.weichengdan);
		}else if("5".equals(status)){
			/*
			 * 已售罄
			 * */
			iv.setImageResource(R.drawable.yishouqing);
		}else if("6".equals(status)){
			/*
			 * 已下架
			 * */
			iv.setImageResource(R.drawable.yishouqing);
		}else if("7".equals(status)){
			/*
			 * 已结束
			 * */
			iv.setImageResource(R.drawable.activity_over);
		}else{
			iv.setVisibility(View.INVISIBLE);
		}
	}
	//过滤 null 和 ""
	public static String getSign(String input){
		if(StringUtil.isEmpty(input)){
			return "0";
		}else{
			return input;
		}
	}
}
 