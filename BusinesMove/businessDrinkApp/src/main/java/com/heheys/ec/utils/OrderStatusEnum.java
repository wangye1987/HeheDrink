package com.heheys.ec.utils;
/**
 * @author wangkui E-mail:wang6851317@sina.com
 * @version 1.0
 * @param 创建时间：2016-3-29 下午5:56:00
 *  类说明
 */
public enum OrderStatusEnum {
	/**
	 * 订单状态  
	 * 正常: 1 待付款(定金)，9 进行中，--成单操作-- 2 待付款(尾款)，-3 待付款(全额)-，--付款结束-- 10 受理中 ，--受理操作-- 6 备货中，--发货操作-- 7待收货， --确认收货-- 8 已完成  
	 * 异常: 21 已取消 22已退款 23  已失效
	 * */
	
	
	DAI_FU_KUAN_DINGJIN(1,"待付款—订金"),
	DAI_FU_KUAN_WEIKUAN(2,"待付款—尾款"),
	DAI_FU_KUAN_QUANE(3,"待付款"),//批发
	JIN_XING_ZHONG(9,"进行中"),//拼单进行中

	DAI_SHOU_HUO(7,"待收货"),
	YI_WAN_CHENG(8,"已完成"),//已签收 已收货
	YI_TUI_KUAN_QUANE(-1,"已退款-全额"),
	YI_TUI_KUAN_DINGJIN(-3,"已退款-订金"),
	YI_TUI_KUAN_WEIKUAN(-2,"已退款-尾款"),
	BEI_HUO_ZHONG(6,"备货中"),//待发货
	
	
	
	DAI_FU_KUAN_QUANE_OFFINE(4,"待付款(线下全额)"),
	YI_ZHI_FU(5,"已支付"),//暂时不用
	SHOU_LI_ZHONG(10,"受理中"),
	YI_PAI_DAN(12, "已派单"),


	YI_QU_XIAO(21,"已取消"),


	DAI_TUI_KUAN_DINGJIN(-4,"待退款-订金"),
	ZHI_FU_CHAO_SHI_WEIKUAN(-5,"支付尾款超时"),
	ZHI_FU_CHAO_SHI(-10,"支付超时"),

	
	ZHI_FU_CHENG_GONG_DINGJIN_NO(30,"支付(定金)成功,无库存"),//需要退款
	ZHI_FU_CHENG_GONG_QUANE_NO(31,"支付(全额)成功,无库存");//需要退款
	
	public int code;	//code码
	public String msg;	//注释
	private OrderStatusEnum(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
