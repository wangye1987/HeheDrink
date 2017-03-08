package com.heheys.ec.netWorkHelper;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.AddCollectResultBean;
import com.heheys.ec.model.dataBean.AddShoppingCartBean;
import com.heheys.ec.model.dataBean.AddbusCardBean;
import com.heheys.ec.model.dataBean.AddressListBean;
import com.heheys.ec.model.dataBean.AlipayBaseBean;
import com.heheys.ec.model.dataBean.AllOrderBaseBean;
import com.heheys.ec.model.dataBean.BaseAddressBean;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.model.dataBean.BaseCardBean;
import com.heheys.ec.model.dataBean.BaseHehePayBean;
import com.heheys.ec.model.dataBean.BaseMessageBean;
import com.heheys.ec.model.dataBean.BaseRecordBean;
import com.heheys.ec.model.dataBean.BasebeanSign;
import com.heheys.ec.model.dataBean.BrandBaseBean;
import com.heheys.ec.model.dataBean.BrandWineBaseBean;
import com.heheys.ec.model.dataBean.BusinessCardBaseBean;
import com.heheys.ec.model.dataBean.BusinessDataBean;
import com.heheys.ec.model.dataBean.BuyMoreBean;
import com.heheys.ec.model.dataBean.CanUserCouponBean;
import com.heheys.ec.model.dataBean.CancleMsgBean;
import com.heheys.ec.model.dataBean.CityListBean;
import com.heheys.ec.model.dataBean.CollectBussinessResultBean;
import com.heheys.ec.model.dataBean.CollectGoodsResultBean;
import com.heheys.ec.model.dataBean.CouponBaseBean;
import com.heheys.ec.model.dataBean.CreatebaseOrderBean;
import com.heheys.ec.model.dataBean.CustomerBaseBean;
import com.heheys.ec.model.dataBean.DeleteShoppingCartProduct;
import com.heheys.ec.model.dataBean.DemandDetailBaseBean;
import com.heheys.ec.model.dataBean.DrinkInfoBaseBean;
import com.heheys.ec.model.dataBean.DrinksDemandBean;
import com.heheys.ec.model.dataBean.ExpressBaseBean;
import com.heheys.ec.model.dataBean.GetAdvertisementBean;
import com.heheys.ec.model.dataBean.GoodsMapList;
import com.heheys.ec.model.dataBean.GroupBuyProductlistBean;
import com.heheys.ec.model.dataBean.HeartResultBean;
import com.heheys.ec.model.dataBean.HeheMoneyBaseBean;
import com.heheys.ec.model.dataBean.HehePayBaseBean;
import com.heheys.ec.model.dataBean.ImBaseBean;
import com.heheys.ec.model.dataBean.ImageBaseBean;
import com.heheys.ec.model.dataBean.InvoiceHistoryBean;
import com.heheys.ec.model.dataBean.JDpayBean;
import com.heheys.ec.model.dataBean.MessageListBean;
import com.heheys.ec.model.dataBean.MoreSuitBean;
import com.heheys.ec.model.dataBean.MyBalanceBaseBean;
import com.heheys.ec.model.dataBean.MyCardBaseBean;
import com.heheys.ec.model.dataBean.MyCouponBean;
import com.heheys.ec.model.dataBean.MyHeheAccount;
import com.heheys.ec.model.dataBean.MyOrderBaseBean;
import com.heheys.ec.model.dataBean.MyPointBaseBean;
import com.heheys.ec.model.dataBean.MySalonDetailBean;
import com.heheys.ec.model.dataBean.MySalonListBean;
import com.heheys.ec.model.dataBean.MyhhMoneyBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewProductDetailInfor;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.OrderBaseBean;
import com.heheys.ec.model.dataBean.OrderBaseDetailBean;
import com.heheys.ec.model.dataBean.PlaceNameBaseBean;
import com.heheys.ec.model.dataBean.PointsBaseBean;
import com.heheys.ec.model.dataBean.ProductDetailInfor;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean;
import com.heheys.ec.model.dataBean.PushBean;
import com.heheys.ec.model.dataBean.RechargeResultBean;
import com.heheys.ec.model.dataBean.Rechargebasebean;
import com.heheys.ec.model.dataBean.SalonCustomerDetailBean;
import com.heheys.ec.model.dataBean.SalonDetailBean;
import com.heheys.ec.model.dataBean.SalonListBean;
import com.heheys.ec.model.dataBean.ScanResultBean;
import com.heheys.ec.model.dataBean.SeachBaseBean;
import com.heheys.ec.model.dataBean.SearchResultBean;
import com.heheys.ec.model.dataBean.ServicelineBaseBean;
import com.heheys.ec.model.dataBean.ShareBaseBean;
import com.heheys.ec.model.dataBean.ShareUrlResultBean;
import com.heheys.ec.model.dataBean.ShopBaseBean;
import com.heheys.ec.model.dataBean.ShopDetaileBaseBean;
import com.heheys.ec.model.dataBean.ShopListBaseBean;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.model.dataBean.ShoppingSubmitCartSelectBean;
import com.heheys.ec.model.dataBean.SignPoingBean;
import com.heheys.ec.model.dataBean.UserDefaultAdd;
import com.heheys.ec.model.dataBean.VersionInitdatabean;
import com.heheys.ec.model.dataBean.WXPayBaseBean;
import com.heheys.ec.model.dataBean.WholeBaseBean;
import com.heheys.ec.utils.Md5Util;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.heheys.ec.view.LoadingView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 作者 E-mail:wk
 * @version 创建时间：2015-9-29 上午10:00:22 类说明
 * @param网络连接工具类
 */
public class ApiHttpCilent {
	private static ApiHttpCilent mApi;
	private static Context context;

	private static String TAG = "Drink";
	public  static LoadingView loading;
//	private CookieStore cookieStore;
//
//	public CookieStore getCookieStore() {
//		return cookieStore;
//	}
//
//	public void setCookieStore(CookieStore cookieStore) {
//		this.cookieStore = cookieStore;
//	}

	public static ApiHttpCilent getInstance(Context _context) {
		context = _context;
		if (mApi == null) {
			mApi = new ApiHttpCilent();
		}
		return mApi;
	}

	/**
	 * 调取元数据接口获取版本数据
	 * 
	 * @return
	 */
	public void initVersiondata(Context baseContext,String android,
			BaseJsonHttpResponseHandler<VersionInitdatabean> callback) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("type", android);
//		saveCookie(BasicHttpClient.getInstance(baseContext).asyncHttpClient, baseContext);
		saveCookie(BasicHttpClient.getInstance(baseContext).asyncHttpClient, baseContext);
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.initdata, callback);
	}
	/**
	 * 调取登录接口获取登录数据
	 * 
	 * @return
	 */
	public void login(Context baseContext, String mobile, String password,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("password", Md5Util.MD5(password));
		paramsIn.put("mobile",mobile);
		saveCookie(BasicHttpClient.getInstance(baseContext).asyncHttpClient, baseContext);
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.login, callback);
	}

	protected  void saveCookie(AsyncHttpClient client, Context mContext) {
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		PersistentCookieStore caseCook = (PersistentCookieStore) SharedPreferencesUtil
				.getObject(mContext, "cookieStore");
		if (caseCook == null) {
			SharedPreferencesUtil.saveObject(mContext, cookieStore,
					"cookieStore");
			caseCook = cookieStore;
		} else {
			caseCook = (PersistentCookieStore) SharedPreferencesUtil.getObject(
					mContext, "cookieStore");
		}
		client.setCookieStore(caseCook);
		MyApplication.getInstance().setCookieStore(caseCook);
//		HttpContext httpContext = client.getHttpContext();
//		cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
//		setCookieStore(cookieStore);
	}
//	protected void saveoneCookie(AsyncHttpClient client, Context mContext) {
////		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
////		PersistentCookieStore caseCook = (PersistentCookieStore) SharedPreferencesUtil
////				.getObject(mContext, "cookieStore");
////		if (caseCook == null) {
////			SharedPreferencesUtil.saveObject(mContext, cookieStore,
////					"cookieStore");
////			caseCook = cookieStore;
////		} else {
////			caseCook = (PersistentCookieStore) SharedPreferencesUtil.getObject(
////					mContext, "cookieStore");
////		}
////		client.setCookieStore(caseCook);
//		
//		HttpContext httpContext = client.getHttpContext();
//		cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
//		setCookieStore(cookieStore);
//	}

//	public List<Cookie> getCookie(Context mContext) {
//		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
//		List<Cookie> cookies = cookieStore.getCookies();
//		return cookies;
//	}

	public void clearCookie(Context mContext) {
		PersistentCookieStore cookieStore = new PersistentCookieStore(mContext);
		cookieStore.clear();
	}

	/**
	 * 调取退出登录接口
	 * 
	 * @return
	 */
	public void loginoOut(Context baseContext,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("whoami", "whoami");
		saveCookie(BasicHttpClient.getInstance(baseContext).asyncHttpClient, baseContext);
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.login_out, callback);
	}

	/*
	 * 等待提示框
	 */
	private void CreateLoading(Activity baseContext) {
		if (loading == null) {
			loading = new LoadingView(baseContext);
		} else {
			loading.dismiss();
			loading = null;
			loading = new LoadingView(baseContext);
		}
		if(baseContext != null){
			loading.initLoadingDialog(baseContext.getResources().getString(R.string.loading));
			loading.show();
		}
	}

	/**
	 * 调取注册短信接口获取短信
	 * 
	 * @return flag /1 注册 2 登录 3 找回密码 4 修改手机号
	 */
	public void ObtinCode(Context baseContext, String mobile,
			BaseJsonHttpResponseHandler<BaseBean> callback, String flag) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("mobile", mobile);
		paramsIn.put("code_type", flag);
		paramsIn.put("sms_type", "1");
		// "code_type":1, //1 注册 2 登录 3 找回密码 4 修改手机号 5申请提现 6设置收款账号 7余额支付
		// "sms_type":1, //获取验证码的渠道 1 短信 2 语音短信
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.register_code, callback);
	}

	/**
	 * 调取注册接口
	 * 
	 * @return
	 */
	public void RegisterApp(Context baseContext, String mobile,
			String password, String code,String yqm_st,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("mobile", mobile);
		paramsIn.put("password", Md5Util.MD5(password));
		paramsIn.put("code", code);
		//邀请码注册 非必填
		if(!StringUtil.isEmpty(yqm_st))
			paramsIn.put("invitecode", yqm_st);
		// mobile":"13222222222", //手机号
		// "password":"123456", //密码
		// "code":"123456"// 手机收到的验证码
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.register, callback);
	}

	/**
	 * 调取修改密码接口
	 * 
	 * @return
	 */
	public void EditPassword(Context baseContext, String mobile, String code,
			String password, BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) baseContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("mobile", mobile);
		paramsIn.put("new_password",  Md5Util.MD5(password));
		paramsIn.put("code", code);
		// "mobile":"13333333333"//手机号
		// "new_password":"123456",
		// "code":"123456"// 手机收到的验证码
		BasicHttpClient.getInstance(baseContext).post(baseContext, paramsIn,
				UrlsUtil.updatepwd, callback);
	}

	/**
	 * 
	 * Describe:获取城市列表接口
	 * 
	 * Date:2015-10-8
	 * 
	 * Author:liuzhouliang
	 */
	public void getCityList(Context mContext,
			BaseJsonHttpResponseHandler<CityListBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.hotCity, callback);
	}

	/**
	 * 
	 * Describe:获取购物车信息接口
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:liuzhouliang
	 */
	// public void getShoppingCartInfor(Context mContext,
	// BaseJsonHttpResponseHandler<GetShoppingCartInfor> callback) {
	// CreateLoading((Activity) mContext);
	// RequestParams paramsIn = new RequestParams();
	// BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
	// UrlsUtil.shoppingCart, callback);
	// }

	/**
	 * 
	 * Describe:获取基本信息接口
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:wk
	 */
	public void getUserInfo(Context mContext,
			BaseJsonHttpResponseHandler<ShopBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.shopinfo, callback);
	}

	/**
	 * 
	 * Describe:提交基本信息接口
	 * 
	 * Date:2015-10-9
	 * 
	 * Author:wk
	 */
	public void commitUserInfo(Context mContext, String st_name,
			String st_shopAdd, String st_leadname,
			String st_linkTel, String st_cardid, String st_blicense,
			String pic1, String pic2, String pic3,int privaceid,int cityid,
			BaseJsonHttpResponseHandler<ShopBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		/*
		 * "name":"",//店铺名称 "address":"",//店铺地址 "legal":"",//法人
		 * "contact":"",联系人信息 "contactmobile":""联系人方式 "cardid":"身份证",
		 * "blicense ":"营业执照号" "pic1":"",门头图片 "pic2":"",法人身份证 "pic3":"",营业执照
		 */
		if(!StringUtil.isEmpty(st_name))
		paramsIn.put("name", st_name);
		if(!StringUtil.isEmpty(st_shopAdd))
		paramsIn.put("address", st_shopAdd);
		if(!StringUtil.isEmpty(st_leadname))
		paramsIn.put("legal", st_leadname);
//		paramsIn.put("contact", st_linkName);
		if(!StringUtil.isEmpty(st_linkTel))
		paramsIn.put("contactmobile", st_linkTel);
		if(!StringUtil.isEmpty(st_cardid))
		paramsIn.put("cardid", st_cardid);
		if(!StringUtil.isEmpty(st_blicense))
		paramsIn.put("blicense", st_blicense);
		if(!StringUtil.isEmpty(pic1))
		paramsIn.put("pic1", pic1);
		if(!StringUtil.isEmpty(pic2))
		paramsIn.put("pic2", pic2);
		if(!StringUtil.isEmpty(pic3))
		paramsIn.put("pic3", pic3);
//		paramsIn.put("pic4", pic4);
//		paramsIn.put("pic5", pic5);
		paramsIn.put("province", privaceid);
		paramsIn.put("city", cityid);
//		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.shopinfocommit, callback);
	}

	/**
	 * 
	 * Describe:获取拼单活动列表
	 * 
	 * Date:2015-10-10
	 * 
	 * Author:liuzhouliang
	 */
	public void getProductList(Context mContext, String order, String city,
			int winetype, int start, int end, int top, int asc,
			String inputstr,int type,
			BaseJsonHttpResponseHandler<GroupBuyProductlistBean> callback) {
		CreateLoading((Activity) mContext);

		RequestParams paramsIn = new RequestParams();
		
		// "order":"", 排序 price/endtime
		paramsIn.put("order", order);
		// "top":1, //1 获取推荐的列表供首页展示 0 获取普通列表
		paramsIn.put("top", top);
		// "inputstr":""// 搜索条件
		if (!StringUtil.isEmpty(inputstr)) {
			paramsIn.put("inputstr", inputstr);
		}
		

		// "asc":"0" 0 正序 1 倒叙
		paramsIn.put("asc", asc);
		// "city":"1" 城市的id
		paramsIn.put("city", city);
		// "winetype":1 品牌
		if (winetype != 0) {
			paramsIn.put("winetype", winetype);
		}
		// 其中start_record是起始记录数，从1开始计数，缺省值为1
		paramsIn.put("start", start);
		// end_record是结束记录数，缺省值为50
		paramsIn.put("end", end);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.productList, callback);
	}
	
	/**
	 * 
	 * Describe:获取首页列表
	 * 
	 * Date:2016-03-22
	 * 
	 * Author:wangkui
	 */
	public void getHomeGoods(Context mContext,
			BaseJsonHttpResponseHandler<WholeBaseBean> callback){
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.home_goods, callback);
	}
	/**
	 * 
	 * Describe:获取批发活动列表
	 * 
	 * Date:2016-03-14
	 * 
	 * Author:wangkui
	 */
	public void getWholeSaleProductList(Context mContext, String order, String city,
			 String winetype, int start, int end, int asc,
			String inputstr,String place,String type,String recommend,
			BaseJsonHttpResponseHandler<WholeBaseBean> callback) {
		CreateLoading((Activity) mContext);
//		“type”:”0” 0拼单 1 批发 空的时候拼单和批发混合
//		"inputstr":""// 搜索条件  
//		"order": "price/sales/",  排序   价格、销量，空为默认排序
//		"asc":"0"    0  正序 1  倒叙
//		"city":"1"    APP定位的城市ID
//		"winetype":1,2   品类ID 
//		“place”: 1 产地ID
//		“index”:1  分页参数：1为第一页
//		“size”：10 分页大小，
		RequestParams paramsIn = new RequestParams();
		// "order":"", 排序 price/endtime
		if (!StringUtil.isEmpty(order)) {
			paramsIn.put("order", order);
		}
		if (!StringUtil.isEmpty(inputstr)) {
			paramsIn.put("inputstr", inputstr);
		}
		
		if (!StringUtil.isEmpty(place)) {
			paramsIn.put("place", place);
		}
		// "asc":"0" 0 正序 1 倒叙
		paramsIn.put("asc", asc);
		// "city":"1" 城市的id
		paramsIn.put("city", city);
		// "type":"1" 1 批发 0 拼单
		if (!StringUtil.isEmpty(type)) {
		paramsIn.put("type", type);
		}
		if (!StringUtil.isEmpty(recommend)) {
			paramsIn.put("recommend", recommend);
		}
		// "winetype":1 品牌
		if (!StringUtil.isEmpty(winetype)) {
			paramsIn.put("winetype", winetype);
		}
		// 其中start_record是起始记录数，从1开始计数，缺省值为1
		paramsIn.put("start", start);
		// end_record是结束记录数，缺省值为50
		paramsIn.put("end", end);
		
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.wholeList, callback);
	}

	/**
	 * 
	 * Describe:获取下游用户列表数据
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:wk
	 */
	public void GetCustomerList(Context mContext, int startIndex, int endIndex,
			BaseJsonHttpResponseHandler<CustomerBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start", startIndex);
		paramsIn.put("end", endIndex);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.downuser_list, callback);
	}

	/**
	 * 
	 * Describe:增加更新下游用户数据
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:wk
	 */
	public void AddUpdateCustomer(Context mContext, String id, String name,
			String mobile, String address, String remark, String btype,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		// "name":"", 姓名
		paramsIn.put("name", name);
		// mobile 手机
		paramsIn.put("mobile", mobile);
		// "address":""// 地址
		paramsIn.put("address", address);
		// "remark" 备注
		paramsIn.put("remark", remark);
		// "btype"类型
		paramsIn.put("btype", btype);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.downuser, callback);
	}

	/**
	 * 
	 * Describe:删除下游用户数据
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:wk
	 */
	public void DeleteCustomer(Context mContext, StringBuffer id,
			BaseJsonHttpResponseHandler<CustomerBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.downuser_delete, callback);
	}

	/**
	 * 
	 * Describe:获取商品详情
	 * 
	 * Date:2015-10-12
	 * 
	 * Author:liuzhouliang
	 */
	public void getProductDetailInfor(Context mContext, String id,

	BaseJsonHttpResponseHandler<ProductDetailInfor> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.productDetail, callback);
	}
	/**
	 * 
	 * Describe:获取商品详情新接口
	 * 
	 * Date:2016-03-18
	 * 
	 * Author:wangkui
	 */
	public void getNewProductDetailInfor(Context mContext, String id,
			
			BaseJsonHttpResponseHandler<NewProductDetailInfor> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
//		paramsIn.put("type", type);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.GOODS_PRODUCES, callback);
	}

	/**
	 * 
	 * Describe:添加购物车
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 */
	public void addShoppingCart(Context mContext,String aid,String num,
			int add,BaseJsonHttpResponseHandler<AddShoppingCartBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
//		paramsIn.put("iid", iid);
//		if(!StringUtil.isEmpty(shopid))
//			paramsIn.put("shopid",shopid);
		
		if(!StringUtil.isEmpty(aid))
			paramsIn.put("aid", aid);
		
		paramsIn.put("num", num);
		paramsIn.put("add", add);
//		paramsIn.put("type", type);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.addShopCart, callback);
	}

	/**
	 * 
	 * Describe:获取购物车详情
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 */

	public void getShoppingCartInfor(Activity mContext,

	BaseJsonHttpResponseHandler<NewShoppingCartInforBean> callback) {
		CreateLoading(mContext);
		RequestParams paramsIn = new RequestParams();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.getShopcartInfor, callback);
	}

	/**
	 * 
	 * Describe:删除购物车商品
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:liuzhouliang
	 */
	public void deleteShoppingCartInfor(Context mContext,
			List<ShoppingCartProductDeleteBean> dataList,

			BaseJsonHttpResponseHandler<DeleteShoppingCartProduct> callback) {
		CreateLoading((Activity) mContext);
		Gson json = new Gson();
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("list", json.toJson(dataList));
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.deleteShopcartProduct, callback);
	}

	/**
	 * 
	 * Describe:获取订单信息
	 * 
	 * Date:2015-10-13
	 * 
	 * Author:wk
	 */
	public void GetOrderInfo(Context mContext, String oid,String code,

	BaseJsonHttpResponseHandler<JDpayBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		Gson json = new Gson();
		paramsIn.put("oid", oid);
		if(!StringUtil.isEmpty(code))
		paramsIn.put("code", code);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn, UrlsUtil.jd_pay,
				callback);
	}

	/**
	 * 
	 * Describe:获取送货地址信息
	 * 
	 * Date:2015-10-14
	 * 
	 * Author:wk
	 */
	public void GetAddressInfo(Context mContext,

	BaseJsonHttpResponseHandler<BaseAddressBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.list_address, callback);
	}

	/**
	 * 
	 * Describe:生成预付订单
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:wk 
	 * 
	 * coupon 优惠券ID
	 */
	public void CreatPaymentOrder(Context mContext,String paytype,String isdefault,String score,int address,
			List<ShoppingCartSelectBean> list,String from,String coupon,
			BaseJsonHttpResponseHandler<NewOrderBaseBean> myCallBack) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		Gson json = new Gson();
		ArrayList<ShoppingSubmitCartSelectBean> listSubmit = new ArrayList<ShoppingSubmitCartSelectBean>();
		listSubmit.clear();
		if(list != null  && list.size() > 0){
	    	for(ShoppingCartSelectBean selectbean : list){
	    		ShoppingSubmitCartSelectBean submit  = new ShoppingSubmitCartSelectBean();
	    		submit.setAid(StringUtil.isEmpty(selectbean.getAid())? "":selectbean.getAid());
	    		submit.setNum(StringUtil.isEmpty(selectbean.getNum())? "":selectbean.getNum());
	    		submit.setSid(StringUtil.isEmpty(selectbean.getSid())? "":selectbean.getSid());
	    		listSubmit.add(submit);
		     }
		}
		paramsIn.put("item", json.toJson(listSubmit));
		paramsIn.put("from", from);
		if(!StringUtil.isEmpty(score))
		paramsIn.put("score", score);
		if(!StringUtil.isEmpty(paytype))
			paramsIn.put("paytype", paytype);
		if("1".equals(isdefault))
		paramsIn.put("isdefault", isdefault);
		if(!StringUtil.isEmpty(coupon))
			paramsIn.put("coupon", coupon);
		if(address != -1)
			paramsIn.put("address", address);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.create_payment, myCallBack);
	}

	/**
	 * 
	 * Describe:生成订单信息
	 * 
	 * Date:2015-10-15
	 * 
	 * Author:wk
	 * //			inbuyertype	是	发票用户类型(0,个人 1:单位)
		//			invoicetitle	是	发票title(不要发票,传空)
		//			invoicecontent	是	发票明细(0:明细 1:酒水)
	 */
	public void CreateOrder(Context mContext,String score,String from,
			List<ShoppingCartSelectBean> list, int addressid, String paytype,String unique_id,String coupon,
			int inbuyertype,String invoicetitle,int invoicecontent,
			BaseJsonHttpResponseHandler<CreatebaseOrderBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		Gson json = new Gson();
		paramsIn.put("item", json.toJson(list));
		paramsIn.put("address", addressid);
		paramsIn.put("unique_id", unique_id);
		paramsIn.put("from", from);
		if(!StringUtil.isEmpty(paytype))
		paramsIn.put("paytype", paytype);
		if(!StringUtil.isEmpty(score))
		paramsIn.put("score", score);
		
		if(inbuyertype != -1)
		paramsIn.put("inbuyertype", inbuyertype);
		if(!StringUtil.isEmpty(invoicetitle))
		paramsIn.put("invoicetitle", invoicetitle);
		if(invoicecontent != -1)
		paramsIn.put("invoicecontent", invoicecontent);
		if(!StringUtil.isEmpty(coupon))
			paramsIn.put("coupon", coupon);
//		if(!StringUtil.isEmpty(coin))
//			paramsIn.put("coin", coin);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.create_order, callback);
	}

	/**
	 * 
	 * Describe:获取订单列表信息
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:wk
	 */
	public void GetOrderList(Context mContext, int startIndex, int endIndex,
			String status,

			BaseJsonHttpResponseHandler<OrderBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("status", status);
		paramsIn.put("start", startIndex);
		paramsIn.put("end", endIndex);
		// PersistentCookieStore myCookieStore = new
		// PersistentCookieStore(context);
		// BasicHttpClient.getInstance(mContext).getHttpContext().
		// AsyncHttpClient client = AsyncHttpCilentUtil.getInstence();
		// HttpContext httpContext = client.getHttpContext();
		// CookieStore cookies = (CookieStore)
		// httpContext.getAttribute(ClientContext.COOKIE_STORE);//获取AsyncHttpClient中的CookieStore
		// AsyncHttpClient asyncHttpClient = BasicHttpClient.getInstance(mContext)
		// .getAsyncHttpClient();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.get_order_list, callback);
	}

	/**
	 * 
	 * Describe:获取订单详情
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:wk
	 */
	public void GetOrderDetail(Context mContext, int id,

	BaseJsonHttpResponseHandler<OrderBaseDetailBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.order_detail, callback);
	}

	/**
	 * 
	 * Describe:获取省市区元数据接口
	 * 
	 * Date:2015-10-16
	 * 
	 * Author:wk
	 */
	public void InitProvinceList(Context mContext,
			BaseJsonHttpResponseHandler<ProvinceListBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
//		saveoneCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.province_list, callback);
	}
	/**
	 * 
	 * Describe:获取客服电话数据接口
	 * 
	 * Date:2016-6-15
	 * 
	 * Author:wk
	 */
	public void InitBaseInfo(Context mContext,String cityId,
			BaseJsonHttpResponseHandler<ServicelineBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		if(!StringUtil.isEmpty(cityId)){
			paramsIn.put("cityId",cityId);
		}
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.baseinfo_list, callback);
	}

	/**
	 * 
	 * Describe:提交地址数据接口
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:wk
	 */
	public void CommitAddress(Context mContext, AddressListBean bean,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		// "id":"",
		// "name":"", 收货人姓名
		// "mobile":"",手机号码
		// "address":"",详细地址
		// "areacode":"",邮编
		// "province":,省
		// "city":,市
		// "county":,县
		RequestParams paramsIn = new RequestParams();
		if (bean.getId() == -1) {
			paramsIn.put("id", "");
		} else {
			paramsIn.put("id", bean.getId());
		}
		paramsIn.put("name", bean.getName());
		paramsIn.put("mobile", bean.getMobile());
		paramsIn.put("address", bean.getAddress());
		paramsIn.put("province", bean.getProvince());
		paramsIn.put("city", bean.getCity());
		paramsIn.put("county", bean.getCounty());
		paramsIn.put("provincename", bean.getProvincename());
		paramsIn.put("cityname", bean.getCityname());
		paramsIn.put("countyname", bean.getCountyname());
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.commit_address, callback);
	}

	/**
	 * 
	 * Describe:删除地址数据接口
	 * 
	 * Date:2015-10-19
	 * 
	 * Author:wk
	 */
	public void DeleteAddress(Context mContext, StringBuffer sb,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		// "id":"1,2,3",
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", sb.toString());
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.delete_address, callback);
	}

	/**
	 * Describe:沙龙活动报名
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void salonJoin(Context mContext, String sid,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("sid", sid);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.salon_join, callback);
	}

	/**
	 * Describe:获取沙龙活动列表
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void getSalonList(Context mContext, int startIndex, int endIndex,
			String uid, BaseJsonHttpResponseHandler<SalonListBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start", startIndex);
		paramsIn.put("end", endIndex);
		if (!StringUtil.isEmpty(uid)) {
			paramsIn.put("uid", uid);
		}
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.salon_list, callback);
	}

	/**
	 * Describe:获取沙龙活动详情
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void getSalonDetail(Context mContext, int id,
			BaseJsonHttpResponseHandler<SalonDetailBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.my_salon_detail, callback);
	}

	/**
	 * Describe:获取我的沙龙活动列表
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void getMySalonList(Context mContext, int startIndex, int endIndex,
			BaseJsonHttpResponseHandler<MySalonListBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start", startIndex);
		paramsIn.put("end", endIndex);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.my_salon_list, callback);
	}

	/**
	 * Describe:获取我的沙龙活动详情
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void getMySalonDetail(Context mContext, int id, String userid,
			BaseJsonHttpResponseHandler<MySalonDetailBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		if (!StringUtil.isEmpty(userid)) {
			paramsIn.put("uid", userid);
		}
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.my_salon_detail, callback);
	}

	/**
	 * Describe:请求交换联系方式
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void exchangeContanct(Context mContext, String uid, String sid,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("uid", uid);
		paramsIn.put("sid", sid);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.exchange_contact, callback);
	}

	/**
	 * Describe:同意交换联系方式
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void agreeExchangeContanct(Context mContext, String uid, String sid,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("uid", uid);
		paramsIn.put("sid", sid);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.agreed_exchange_contact, callback);
	}

	/**
	 * Describe:嘉宾详情
	 * 
	 * Date:2015-10-18
	 * 
	 * Author:liuzhouliang
	 * 
	 */
	public void salonCustomerDetail(Context mContext, String uid, String sid,
			BaseJsonHttpResponseHandler<SalonCustomerDetailBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("uid", uid);
		paramsIn.put("sid", sid);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.customer_detail, callback);
	}

	/**
	 * 
	 * Describe:获取广告
	 * 
	 * Date:2015-10-20
	 * 
	 * Author:liuzhouliang
	 */
	public void getAdvertisement(Context mContext,
			BaseJsonHttpResponseHandler<GetAdvertisementBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.get_advertisement, callback);
	}

	/**
	 * 
	 * Describe:确认收货
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:wk
	 */
	public void ConfirmGoods(Context mContext, int id,
			BaseJsonHttpResponseHandler<OrderBaseDetailBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.confirmGoods, callback);
	}

	/**
	 * 
	 * Describe:提交图片
	 * 
	 * Date:2015-10-21
	 * 
	 * Author:wk
	 */
	public void UploadImage(Context mContext, File file,
			BaseJsonHttpResponseHandler<ImageBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		try {
			paramsIn.put("imgFile ", file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.upload_image, callback);
	}

	/**
	 * 
	 * Describe:获取用户基本信息
	 * 
	 * Date:2015-10-28
	 * 
	 * Author:wk
	 */
	public void GetInfo(Context mContext,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn, UrlsUtil.info,
				callback);
	}

	/**
	 * 
	 * Describe:设置默认地址接口
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:wk
	 */
	public void SetDefaultAddress(Context mContext, int id,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.defaultAddress, callback);
	}

	/**
	 * 
	 * Describe:绑定推送cid接口
	 * 
	 * Date:2015-10-29
	 * 
	 * Author:wk
	 */
	public void BindCid(Activity mContext, String id, String cid,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading( mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("uid", id);
		paramsIn.put("cid", cid);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.bindcid, callback);
	}

	/**
	 * 
	 * Describe:消息推送接口
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:wk
	 */
	public void MsgPush(Context mContext,String start,String end,
			BaseJsonHttpResponseHandler<PushBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start", start);
		paramsIn.put("end", end);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.msgMain, callback);
	}

	/**
	 * 
	 * Describe:消息列表接口
	 * 
	 * Date:2015-11-2
	 * 
	 * Author:wk
	 */
	public void MsgList(Context mContext, int start, int end, int type,
			BaseJsonHttpResponseHandler<MessageListBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start", start);
		paramsIn.put("end", end);
		paramsIn.put("type", type);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.msgList, callback);
	}

	/**
	 * 
	 * Describe:个人名片列表接口
	 * 
	 * Date:2015-11-16
	 * 
	 * Author:wk
	 */
	public void BusinessCardList(Context mContext, long time,
			BaseJsonHttpResponseHandler<BusinessCardBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("time", time);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.businessCardList, callback);
	}

	/**
	 * 
	 * Describe:个人名片详情接口
	 * 
	 * Date:2015-11-17
	 * 
	 * Author:wk
	 */
	public void MyBusinessCard(Context mContext,
			BaseJsonHttpResponseHandler<MyCardBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.businessCardDetail, callback);
	}

	/**
	 * 
	 * Describe:添加个人名片接口
	 * 
	 * Date:2015-11-16
	 * 
	 * Author:wk
	 */
	public void AddBusinessCard(Context mContext, AddbusCardBean databean,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("name", databean.getName());
		paramsIn.put("mobile", databean.getMobile());
		paramsIn.put("weixin", databean.getWeixin());
		paramsIn.put("landline", databean.getLandline());
		paramsIn.put("company", databean.getCompany());
		paramsIn.put("position", databean.getPosition());
		paramsIn.put("address", databean.getAddress());
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.AddbusinessCard, callback);
	}

	/**
	 * 
	 * Describe:修改名片备注接口
	 * 
	 * Date:2015-11-16
	 * 
	 * Author:wk
	 */
	public void UpdateBusinessCard(Context mContext, String cid, String remark,
			BaseJsonHttpResponseHandler<BusinessCardBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("cid", cid);
		paramsIn.put("remark", remark);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.UpdatebusinessCard, callback);
	}

	/**
	 * 
	 * Describe:获取酒水需求列表
	 *
	 * Date:2015年11月24日下午3:06:40
	 *
	 * Author:LZL
	 *
	 */
	public void getDrinksDemandList(Context mContext,
			BaseJsonHttpResponseHandler<DrinksDemandBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksDemand, callback);

	}

	/**
	 * 
	 * Describe:获取业务数据
	 *
	 * Date:2015年11月25日下午5:04:12
	 *
	 * Author:LZL
	 *
	 */
	public void getBusinessData(Context mContext,
			BaseJsonHttpResponseHandler<BusinessDataBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.businessData, callback);

	}

	/**
	 * 
	 * Describe:获取酒水品牌列表
	 *
	 * Date:2015年11月24日下午3:06:40
	 *
	 * Author:wangkui
	 *
	 */
	public void getDrinksBrandList(Context mContext, String pid,
			BaseJsonHttpResponseHandler<BrandWineBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("pid", pid);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksBrand, callback);

	}

	/**
	 * 
	 * Describe:添加酒水需求
	 *
	 * Date:2015年11月26日上午10:49:15
	 *
	 * Author:LZL
	 *
	 */
	public void addDrinksDemand(Context mContext,
			BaseJsonHttpResponseHandler<BaseBean> callback,
			String drinksTypeId, String brandId, String degree,
			String drinksName, String drinksNums, String numsUnit,
			String drinksPrice, String deliverGoodsAreaIds,
			String startTime, String endTime, String tradeMark, String remark,String id_array) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("drinksTypeId", drinksTypeId);
		paramsIn.put("brandId", brandId);
		paramsIn.put("degree", degree);
		paramsIn.put("drinksName", drinksName);
		paramsIn.put("drinksNums", drinksNums);
		paramsIn.put("numsUnit", numsUnit);
		paramsIn.put("drinksPrice", drinksPrice);
		paramsIn.put("deliverGoodsAreaIds", deliverGoodsAreaIds);
		paramsIn.put("startTime", startTime);
		paramsIn.put("endTime", endTime);
		paramsIn.put("tradeMark", tradeMark);
		paramsIn.put("remark", remark);
		paramsIn.put("deliverGoodsAreaIdsArray", id_array);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksDemandAdd, callback);

	}
	/**
	 * 
	 * Describe:修改酒水需求
	 *
	 *
	 * Author:wk
	 *
	 */
	public void UpdateDrinksDemand(Context mContext,
			BaseJsonHttpResponseHandler<BaseBean> callback,
			String id,String drinksTypeId, String brandId, String degree,
			String drinksName, String drinksNums, String numsUnit,
			String drinksPrice, String deliverGoodsAreaIds,
			String startTime, String endTime, String tradeMark, String remark) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		paramsIn.put("drinksTypeId", drinksTypeId);
		paramsIn.put("brandId", brandId);
		paramsIn.put("degree", degree);
		paramsIn.put("drinksName", drinksName);
		paramsIn.put("drinksNums", drinksNums);
		paramsIn.put("numsUnit", numsUnit);
		paramsIn.put("drinksPrice", drinksPrice);
		paramsIn.put("deliverGoodsAreaIds", deliverGoodsAreaIds);
		paramsIn.put("startTime", startTime);
		paramsIn.put("endTime", endTime);
		paramsIn.put("tradeMark", tradeMark);
		paramsIn.put("remark", remark);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksDemandUpdate, callback);
	}
	/**
	 * 
	 * Describe:获取酒水需求详情
	 *
	 *
	 * Author:wk
	 *
	 */
	public void DrinksDemandDetail(Context mContext,
			BaseJsonHttpResponseHandler<DemandDetailBaseBean> callback,
			String id) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("id", id);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksDemanDetail, callback);
	}
	/**
	 * 
	 * Describe:获取酒讯列表
	 *
	 *
	 * Author:wk
	 *
	 */
	public void DrinksInfoList(Context mContext,
			BaseJsonHttpResponseHandler<DrinkInfoBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksInfoList, callback);
	}
	
	/**
	 * 
	 * Describe:获取酒讯详情
	 *
	 *
	 * Author:wk
	 *
	 */
	public void DrinksInfoDetail(Context mContext,
			BaseJsonHttpResponseHandler<DrinkInfoBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.drinksInfoList, callback);
	}
	/**
	 * 
	 * Describe:获取产地列表
	 *
	 *
	 * Author:wk
	 *
	 */
	public void PlaceNameList(Context mContext,
			BaseJsonHttpResponseHandler<PlaceNameBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.placeNameList, callback);
	}
	/**
	 * 
	 * Describe:获取品牌列表
	 *
	 *
	 * Author:wk
	 *
	 */
	public void BrandList(Context mContext,String cityId,
			BaseJsonHttpResponseHandler<BrandBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		if(StringUtil.isEmpty(cityId))
			cityId = "52";
		paramsIn.put("cityId",cityId);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.brandList, callback);
	}
	/**
	 * 
	 * Describe:阿里支付鉴权
	 *
	 *
	 * Author:wk
	 *
	 */
	public void AlipaySign(Context mContext,String oid,String code,String paytype,
		BaseJsonHttpResponseHandler<BasebeanSign> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("oid", oid);
		if(!StringUtil.isEmpty(code))
		paramsIn.put("code", code);
		paramsIn.put("paytype", paytype);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.alipay_sign, callback);
		}
	/**
	 * 
	 * Describe:获取我的余额
	 *
	 *
	 * Author:wk
	 *
	 */
	public void GetMyBalance(Context mContext,
			BaseJsonHttpResponseHandler<MyBalanceBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.user_balance, callback);
	}
	/**
	 * 
	 * Describe:获取我的默认卡号
	 *
	 *
	 * Author:wk
	 *
	 */
	public void GetUserCard(Context mContext,
			BaseJsonHttpResponseHandler<BaseCardBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.user_card, callback);
	}
	/**
	 * 
	 * Describe:获取我的默认卡号
	 *
	 *
	 * Author:wk
	 *
	 */
	public void setDefaultCard(Context mContext,String accountbank,String account,String accountname,String code,
			BaseJsonHttpResponseHandler<BaseBean> callback) {
//		account:”6224 2556 2254 5555” 卡号
//		accountbank:”开户行”	所属银行
//		accountname:”张” 卡号所有者
//		 “code”:”231231”,//验证码
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("accountbank", accountbank);
		paramsIn.put("account", account);
		paramsIn.put("accountname", accountname);
		paramsIn.put("code", code);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.set_default_card, callback);
	}
	/**
	 * 
	 * Describe:提现
	 *
	 *
	 * Author:wk
	 *
	 */
	public void getWithDraw(Context mContext,String amount,String code,
		BaseJsonHttpResponseHandler<HehePayBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("amount", amount);
		paramsIn.put("code", code);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.get_withdraw, callback);
		}
	/**
	 * 
	 * Describe:余额支付
	 *
	 *
	 * Author:wk
	 *
	 */
	public void hehpay(Context mContext,String oid,String code,
			BaseJsonHttpResponseHandler<HehePayBaseBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("oid", oid);
		paramsIn.put("code", code);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.hehe_pay, callback);
	}
	/**
	 * 
	 * Describe:获取提现列表
	 *
	 *
	 * Author:wk
	 *
	 */
	//提现状态：-1提现失败；0申请；1申请成功2已发放
		public void getPayListRecord(Context mContext,String status,int start,int end,
				BaseJsonHttpResponseHandler<BaseRecordBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(status)){
				paramsIn.put("status", status);
			}
			paramsIn.put("start", start);
			paramsIn.put("end", end);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.payed_record, callback);
			}
		/**
		 * 
		 * Describe:获取全部订单列表
		 *
		 *
		 * Author:wk
		 *
		 * @param "status":"0"  0 全部订单 1 待付款 2 进行中 3备货中 4 待收货 status不传 查询全部
		 */
		public void getAllOrderList(Context mContext,String status,int start,int end,
				BaseJsonHttpResponseHandler<AllOrderBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(status)){
				paramsIn.put("status", status);
			}
			paramsIn.put("start", start);
			paramsIn.put("end", end);
//			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
//					"order/stateList.jhtml?", callback);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.order_list, callback);
//			http://192.168.0.147/api/order/stateList.jhtml
		}
		/**
		 * 
		 * Describe:获取订单详情
		 *
		 *
		 * Author:wk
		 *
		 */
		public void getOrderDetail(Context mContext,String oid,
				BaseJsonHttpResponseHandler<MyOrderBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.order_detail, callback);
		}
		/**
		 *
		 * Describe:获取扫码订单支付
		 *
		 *
		 * Author:wk
		 *
		 */
		public void getScanOrderDetail(Context mContext,String oid,
				BaseJsonHttpResponseHandler<MyOrderBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.SCAN_ORDER_DETAIL, callback);
		}
		/**
		 * 
		 * Describe:获取快递信息详情
		 *
		 *
		 * Author:wk
		 *
		 */
		public void getExpressDetail(Context mContext,String oid,
				BaseJsonHttpResponseHandler<ExpressBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.order_express_detail, callback);
		}
		/**
		 * 
		 * Describe:取消订单
		 *
		 *
		 * Author:wk
		 *
		 */
		public void CancleOrder(Context mContext,String oid,String reason,
				BaseJsonHttpResponseHandler<BaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			if(!StringUtil.isEmpty(reason))
			paramsIn.put("reason", reason);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.cancle_order, callback);
		}
		/**
		 * 
		 * Describe:确认收货
		 *
		 *
		 * Author:wk
		 *
		 */
		public void GetGoodsOk(Context mContext,String oid,
				BaseJsonHttpResponseHandler<BaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.sure_order, callback);
		}
		/**
		 * 
		 * Describe:首页商品推荐
		 *
		 *
		 * Author:wk
		 *
		 */
		public void HomeGoods(Context mContext, String order, String city,
				 String winetype, int start, int end, int asc,
					String inputstr,String place,String type,
					BaseJsonHttpResponseHandler<WholeBaseBean> callback) {
				CreateLoading((Activity) mContext);
//				“type”:”0” 0拼单 1 批发 空的时候拼单和批发混合
//				"inputstr":""// 搜索条件  
//				"order": "price/sales/",  排序   价格、销量，空为默认排序
//				"asc":"0"    0  正序 1  倒叙
//				"city":"1"    APP定位的城市ID
//				"winetype":1,2   品类ID 
//				“place”: 1 产地ID
//				“index”:1  分页参数：1为第一页
//				“size”：10 分页大小，
				RequestParams paramsIn = new RequestParams();
				// "order":"", 排序 price/endtime
				if (!StringUtil.isEmpty(order)) {
					paramsIn.put("order", order);
				}
				// "top":1, //1 获取推荐的列表供首页展示 0 获取普通列表
//				paramsIn.put("top", top);
				// "inputstr":""// 搜索条件
				if (!StringUtil.isEmpty(inputstr)) {
					paramsIn.put("inputstr", inputstr);
				}
				
				if (!StringUtil.isEmpty(place)) {
					paramsIn.put("place", place);
				}
				// "asc":"0" 0 正序 1 倒叙
				paramsIn.put("asc", asc);
				// "city":"1" 城市的id
				paramsIn.put("city", city);
				// "type":"1" 1 批发 0 拼单
				paramsIn.put("type", type);
				// "winetype":1 品牌
				if (!StringUtil.isEmpty(winetype)) {
					paramsIn.put("winetype", winetype);
				}
				// 其中start_record是起始记录数，从1开始计数，缺省值为1
				paramsIn.put("start", start);
				// end_record是结束记录数，缺省值为50
				paramsIn.put("end", end);
				
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.home_goods, callback);
		}
		
		
		/**
		 * 
		 * Describe:商铺列表
		 *
		 *
		 * Author:wk
		 *
		 */
		public void ShoppingList(Context mContext,String name,String winetype,int start,int end,
				BaseJsonHttpResponseHandler<ShopListBaseBean> callback) {
//			name:”xxxx” 索引商铺名称
//			winetype:0 酒品类Id
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(name))
				paramsIn.put("name", name);
			if(!StringUtil.isEmpty(winetype))
				paramsIn.put("winetype", winetype);
			paramsIn.put("start", start);
			paramsIn.put("end", end);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.shop_list, callback);
		}
		/**
		 * 
		 * Describe:商铺详情
		 *
		 *
		 * Author:wk
		 *
		 */
		public void ShoppingDetaile(Context mContext,String shopid,
				BaseJsonHttpResponseHandler<ShopDetaileBaseBean> callback) {
//			name:”xxxx” 索引商铺名称
//			winetype:0 酒品类Id
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("shopid", shopid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.shop_goods_item, callback);
		}
		/**
		 * 
		 * Describe:商铺详情界面列表
		 *
		 *
		 * Author:wk
		 *
		 */
		public void ShoppingDetaileList(Context mContext,String shopid,String pname,String order,
			String asc,int start,int end,BaseJsonHttpResponseHandler<WholeBaseBean> callback) {
//			shopid:”1”;云店ID
//			pname:”商品名称” 输入框
//			order: "price/sales" //价格 销量， “”为默认
//			asc : 0,1  0倒叙 1正序
//			start: 0 0为开始第一条记录
//			end: 为每页记录数
				CreateLoading((Activity) mContext);
				RequestParams paramsIn = new RequestParams();
					paramsIn.put("shopid", shopid);
				if( !StringUtil.isEmpty(pname))
					paramsIn.put("pname", pname);
				if( !StringUtil.isEmpty(order))
					paramsIn.put("order", order);
					paramsIn.put("asc", asc);
					paramsIn.put("start", start);
					paramsIn.put("end", end);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.shop_goods_list, callback);
		}
		/**
		 * 
		 * Describe:获取支付宝异步支付结果
		 *
		 *
		 * Author:wk
		 *
		 */
		public void SignPayResult(Context mContext,String orderid,BaseJsonHttpResponseHandler<AlipayBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("orderid", orderid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.alipay_result, callback);
		}
		/**
		 * 
		 * Describe:获取一键分享数据
		 *
		 *
		 * Author:wk
		 *
		 */
		public void RequsetShare(Context mContext,String aid,BaseJsonHttpResponseHandler<ShareBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("aid", aid);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.share_url, callback);
		}
		/**
		 * 
		 * Describe:在线客服
		 *
		 *
		 * Author:wk
		 *
		 */
		public void RequsetIM(Context mContext,BaseJsonHttpResponseHandler<ImBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
//			paramsIn.put("aid", aid)s;
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.im_list, callback);
		}
		/**
		 * 
		 * Describe:微信支付获取微信端鉴权结果
		 *
		 *
		 * Author:wk
		 *
		 * oid 订单id
		 * 
		 * 
		 */
		public void WXPaySign(Context mContext,String oid,String code,String paytype,BaseJsonHttpResponseHandler<WXPayBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid", oid);
			if(!StringUtil.isEmpty(code))
			paramsIn.put("code", code);
			paramsIn.put("paytype", paytype);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.wx_pay, callback);
		}
		/**
		 * 
		 * Describe:获取优惠券消息列表
		 *
		 *
		 * Author:wk
		 *
		 * 2016 6.2
		 * 
		 */
		public void CouponMessage(Context mContext,BaseJsonHttpResponseHandler<CouponBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.coupon_msg, callback);
		}
		/**
		 * 
		 * Describe:获取我的优惠券
		 *
		 *
		 * Author:wk
		 *
		 * 2016 6.2
		 * 
		 */
		public void CouponMy(Context mContext,BaseJsonHttpResponseHandler<MyCouponBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.coupon_list, callback);
		}
		
		/**
		 * 
		 * Describe:获取可用优惠券
		 *
		 *
		 * Author:wk
		 *
		 * 2016 6.2
		 * 
		 */
		public void CouponCanuser(Context mContext,String baseamount,BaseJsonHttpResponseHandler<CanUserCouponBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(baseamount))
				paramsIn.put("baseamount", baseamount);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.coupon_user, callback);
		}
		/**
		 * 
		 * Describe:获取当前优惠发票历史信息
		 *
		 *
		 * Author:wk
		 *
		 * 2016 6.23
		 * 
		 */
		public void invoiceHistory(Context mContext,String oid,BaseJsonHttpResponseHandler<InvoiceHistoryBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(oid))
			paramsIn.put("oid", oid);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.invoice_bean, callback);
		}
		/**
		 * 
		 * Describe:获取当前用户可以送货地址
		 *
		 *
		 * Author:wk
		 *
		 * 2016 6.27
		 * 
		 */
		public void UserDefaultAdd(Context mContext,BaseJsonHttpResponseHandler<UserDefaultAdd> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.default_add, callback);
		}
		/**
		 * 
		 * Describe:获取当前用户积分
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.7
		 * 
		 */
		public void UserPoints(Context mContext,BaseJsonHttpResponseHandler<MyPointBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.user_points, callback);
		}
		/**
		 * 
		 * Describe:获取当前用户积分明细
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.11
		 * 
		 */
		public void UserPointsDetail(Context mContext,int start,int end,BaseJsonHttpResponseHandler<PointsBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("start", start);
			paramsIn.put("end", end);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.user_points_item, callback);
		}
		/**
		 * 
		 * Describe:签到
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.11
		 * 
		 */
		public void PonitSign(Context mContext,BaseJsonHttpResponseHandler<SignPoingBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.user_sign, callback);
		}
		/**
		 * 
		 * Describe:取消订单
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.14
		 * 
		 */
		public void CancleOrderList(Context mContext,String ordernum,BaseJsonHttpResponseHandler<CancleMsgBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("ordernum", ordernum);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.order_cancle, callback);
		}

		/**
		 * 
		 * Describe:获取当前用户喝喝币明细
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void UserHeheMoney(Context mContext,BaseJsonHttpResponseHandler<MyHeheAccount> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.my_coin, callback);
		}
		/**
		 * 
		 * Describe:获取当前可以充值的喝喝币面值
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void erchangeHeheMoney(Context mContext,BaseJsonHttpResponseHandler<MyhhMoneyBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.my_coin_cz, callback);
		}
		/**
		 * 
		 * Describe:获取当前用户喝喝币明细
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void UserHeheMoneyDetail(Context mContext,int start,int end,BaseJsonHttpResponseHandler<HeheMoneyBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("start", start);
			paramsIn.put("end", end);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.user_hehemoney_item, callback);
		}
		/**
		 * 
		 * Describe:充值喝喝币
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void crchangeCard(Context mContext,String coinid,String num,String payType,BaseJsonHttpResponseHandler<Rechargebasebean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("coinid",coinid);
			if(!StringUtil.isEmpty(num))
			paramsIn.put("num",num);
			paramsIn.put("paytype",payType);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.rechange_card, callback);
		}
		/**
		 * 
		 * Describe:查询充值喝喝币结果
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void ResultRecharge(Context mContext,String ordernum,BaseJsonHttpResponseHandler<RechargeResultBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("ordernum",ordernum);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.rechange_result, callback);
		}
		/**
		 * 
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void PayHeheMoney(Context mContext,String oid,String code,BaseJsonHttpResponseHandler<BaseHehePayBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			paramsIn.put("oid",oid);
			if(!StringUtil.isEmpty(code))
			paramsIn.put("code",code);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.heheall_pay, callback);
		}
		/**
		 * 
		 *
		 *
		 * Author:wk
		 *
		 * 2016 7.25
		 * 
		 */
		public void SubmitLineOff(Context mContext,String oid,BaseJsonHttpResponseHandler<CreatebaseOrderBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(oid))
			paramsIn.put("oid",oid);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.line_off, callback);
		}
		/**
		 * 
		 * 套装列表
		 *
		 * Author:wk
		 *
		 * 2016 10.25
		 * 
		 */
		public void getSuitList(Context mContext,String aid,BaseJsonHttpResponseHandler<MoreSuitBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(aid))
			paramsIn.put("aid",aid);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.suit_list, callback);
		}
		
		/**
		 * 
		 * 套装加入购物车
		 *
		 * Author:wk
		 *
		 * 2016 10.25
		 * 
		 * id 套装id
		 * num 个数
		 */
		public void AddSuitToShopping(Context mContext,String id,String num,BaseJsonHttpResponseHandler<AddShoppingCartBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(id))
				paramsIn.put("id",id);
			if(!StringUtil.isEmpty(num))
				paramsIn.put("num",num);
			paramsIn.put("add","1");
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.suit_shopping, callback);
		}
		/**
		 * 发送心跳 位置
		 *
		 * */
//		public void SendHeartLocation(Context mContext, LatLng point, BaseJsonHttpResponseHandler<HeartBaseBean> callback) {
//			CreateLoading((Activity) mContext);
//			RequestParams paramsIn = new RequestParams();
//			if(point != null)
//				paramsIn.put("point",point);
//			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
//			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
//					UrlsUtil.send_location, callback);
//		}
		/**
		 * 获取当前区域品牌列表
		 *
		 * */
		public void GridBrandList(Context mContext, String areaId,String wineType, BaseJsonHttpResponseHandler<SeachBaseBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(StringUtil.isEmpty(areaId))
				areaId = "52";
				paramsIn.put("areaId",areaId);
				paramsIn.put("wineType",wineType);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.CURRT_BRAND, callback);
		}
		/**
		 * 获取当前商品在地图分布
		 *
		 * */
		public void GoodsMapList(Context mContext, String aid, BaseJsonHttpResponseHandler<GoodsMapList> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(aid))
				paramsIn.put("aid",aid);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.GOODS_MAP, callback);
		}
		/**
		 * 获取品牌检索结果
		 *brand品牌id
		 * cityId 城市id
		 * */
		public void SearcheResult(Context mContext, String brand,String cityId,int start,int end, BaseJsonHttpResponseHandler<SearchResultBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(brand))
				paramsIn.put("brand",brand);
			if(!StringUtil.isEmpty(cityId))
				paramsIn.put("cityId",cityId);
			// 其中start_record是起始记录数，从1开始计数，缺省值为1
			paramsIn.put("start", start);
			// end_record是结束记录数，缺省值为10
			paramsIn.put("end", end);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.SEARCH_RESULT, callback);
		}
		/**
		 * 获取二维码扫描结果
		 * */
		public void ScanZxing(Context mContext, String content, BaseJsonHttpResponseHandler<ScanResultBean> callback) {
			CreateLoading((Activity) mContext);
			RequestParams paramsIn = new RequestParams();
			if(!StringUtil.isEmpty(content))
				paramsIn.put("content",content);
			saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
			BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
					UrlsUtil.SCAN_RESULT, callback);
		}
	   /**
	 	* 获取需要分享的结果
	 	* */
       public void getShareUrl(Context mContext, String aid, BaseJsonHttpResponseHandler<ShareUrlResultBean> callback) {
		   CreateLoading((Activity) mContext);
		   RequestParams paramsIn = new RequestParams();
		   if(!StringUtil.isEmpty(aid))
			   paramsIn.put("aid",aid);
		   saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		   BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				   UrlsUtil.SHAER_RESULT, callback);
        }
	/**
	 * 获取收藏商品的结果
	 * */
    public void GoodsCollectApi(Context mContext,int start,int end, BaseJsonHttpResponseHandler<CollectGoodsResultBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("start",start);
		paramsIn.put("end",end);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.COLLECT_GOOD_RESULT, callback);
    }

	/**
	 * 获取收藏商家的结果
	 * */
	public void BussinessCollectApi(Context mContext,String name,String winetype,int start,int end, BaseJsonHttpResponseHandler<CollectBussinessResultBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		if(!StringUtil.isEmpty(name))
			paramsIn.put("name",name);
		if(!StringUtil.isEmpty(winetype))
			paramsIn.put("winetype",winetype);
		paramsIn.put("start",start);
		paramsIn.put("end",end);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.COLLECT_BUSSINESS_RESULT, callback);
	}
	/**
	 * 添加收藏商家的结果
	 * */
	public void AddCollectApi(Context mContext,String type,String fid, String flag,BaseJsonHttpResponseHandler<AddCollectResultBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		if(!StringUtil.isEmpty(type))
			paramsIn.put("type",type);
		if(!StringUtil.isEmpty(fid))
			paramsIn.put("fid",fid);
		if(!StringUtil.isEmpty(flag))
			paramsIn.put("flag",flag);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.ADD_COLLECT_RESULT, callback);
	}
	/**
	 * 取消收藏商家的结果
	 * */
	public void CancleCollectApi(Context mContext, String fids, BaseJsonHttpResponseHandler<AddCollectResultBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		if(!StringUtil.isEmpty(fids))
			paramsIn.put("fids",fids);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.CANCLE_COLLECT_RESULT, callback);
	}

	/**
	 * 发送心跳
	 * flag时间
	 * 2016 12.28
	 * */
	public void SendLonLat(Context mContext, double lng,double lat, BaseJsonHttpResponseHandler<HeartResultBean> callback) {
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("lng",lng);
		paramsIn.put("lat",lat);
		paramsIn.put("flag",System.currentTimeMillis());
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).postHeart(mContext, paramsIn,
				UrlsUtil.HEART_RESULT, callback);
	}
	/**
	 *
	 * Describe:取消订单
	 *
	 *
	 * Author:wk
	 *
	 * 2016 12.29
	 *
	 */
	public void DeleteOrderList(Context mContext,String oid,BaseJsonHttpResponseHandler<BaseMessageBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("oid", oid);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.ORDER_DELETE, callback);
	}
	/**
	 *
	 * Describe:再次购买
	 *
	 *
	 * Author:wk
	 *
	 * 2016 12.29
	 *
	 */
	public void BuyMoreOrderList(Context mContext,String orderNum,String type,BaseJsonHttpResponseHandler<BuyMoreBean> callback) {
		CreateLoading((Activity) mContext);
		RequestParams paramsIn = new RequestParams();
		paramsIn.put("orderNum", orderNum);
		if(!StringUtil.isEmpty(type))
		paramsIn.put("type", type);
		saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
		BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
				UrlsUtil.BUY_MORE, callback);
	}
    /**
     *
     * Describe:修改密码
     *
     *
     * Author:wk
     *
     * 2016 12.29
     *
     */
    public void UpdatePassWord(Context mContext,String oldpassword,String newpassword,BaseJsonHttpResponseHandler<BaseMessageBean> callback) {
        CreateLoading((Activity) mContext);
        RequestParams paramsIn = new RequestParams();
        paramsIn.put("oldpassword", Md5Util.MD5(oldpassword));
        paramsIn.put("newpassword",  Md5Util.MD5(newpassword));
        saveCookie(BasicHttpClient.getInstance(mContext).asyncHttpClient, mContext);
        BasicHttpClient.getInstance(mContext).post(mContext, paramsIn,
                UrlsUtil.UPDATE_PAW, callback);
    }
}
