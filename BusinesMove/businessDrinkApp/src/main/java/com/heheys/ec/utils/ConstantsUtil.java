package com.heheys.ec.utils;

/**
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-9-23 上午11:33:56 类说明 参数常用变量设置
 * @param
 */
public class ConstantsUtil {

	public static final int ACTIVITY_BACK = 100;// 回调requestcode
	public static final String HEHE_PAY = "0";// 喝喝支付方式
	public static final String ZFB_PAY = "1";// 支付宝支付方式
	public static final String WX_PAY = "2";// 微信支付方式
	public static final String JD_PAY = "3";// 京东支付方式
	public static final String HB_PAY = "6";// 喝喝币支付方式
	public static final String LINE_OFF = "-1";// 线下支付方式
	//微信appId(加密后)
	public static final String APP_ID = "vkyU5e3IVaU9DkW2+QVnkKXikHajJP9WlmpJePPABOw=";
//	public static final String APP_ID = "oyvtRGzc5V8+CvFLY2zwJJsWDrJYQQAepRsQ5XVPXFc=";
//	public static final String APP_ID = "FGokCuXyXfYkTkxTJAOMnmbh80G/fxNEbKji0XOpM7k=";
	public static final String APP_ID_WX = "wx119870bae7706fb6";
	//阿里聚安全的key值
	public static String JAQ_KEY ="c9f9b642-4466-4f47-b4d6-08002e68ee4a";
//	public static String JAQ_KEY ="005ff9bf-c899-424c-930a-2eb02b1cb05e";
//	public static String JAQ_KEY ="2f92c384-3b83-48e1-a697-b96993254ef1";
	//存储微信支付订单数据bean 的key值
	public static final String ORDERBEAN = "orderbean"; 
//	public static final String ORDERBEAN_DETAILR = "orderbean_dateil";
	public static final String ORDERBEAN_DETAILR_NEW = "orderbean_dateil_new"; 
	public static final String WINE_NEED = "wine";// 酒水需求
	public static final String ADD_MANAGER = "add_manager";// 地址
	public static final String SETTING = "setting";// 设置
	public static final String RETROACTION = "retroaction";// 问题反馈

	public static final int COUNT_DOWN_END = 88;// 完成标志
	public static final int COUNT_DOWNING = 99;// 未完成标志
	public static final int COUNT_DOWN_TIME = 1000;//
	public static int BACK_SECOND = 60;// 倒计时
	public static int TEL_LENGTH = 11;// 手机号码长度

	public static String wxAppId = "wx17e4df51dd029038";//商户平台微信appId
	public static final String LOGIIN_ACTION = "LOGIN_SUCCESS";// 登录发送广播

	public static String SERVICE_TEL = "01085782811";// 客服手机号码85782811
	// 浏览历史长度限制为20条数据（最近浏览的20条数据）
	public static final int BROWSE_HISTORY_SIZE_LIMIT = 20;
	// 默认搜索商品从第几页开始
	public static final int DEFAULT_PAGE_NO = 1;
	// 默认搜索每页的商品数量
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_PAGE_START_INDEX = 1;
	// 默认分类商品品牌推荐每页数量
	public static final int DEFAULT_CATEGORY_PPTJ_PAGE_SIZE = 20;
	// ScrollView滑动速度设置(代表为原标准速度的N分之一)
	public static final int DEFAULT_SCROLL_SPEED = 2;
	// 地址错误码
	public static final int NULL_ADDRESS = -1;
	public static final int ALI_BACK_CODE = 700;//阿里支付
	public static final int HTTP_SUCCESS_LATER = 102;// handler 102 成功
	public static final int HTTP_SUCCESS_LOGIN_LAST = 101;// handler 101 成功
	public static final int HTTP_SUCCESS_LOGIN = 100;// handler 100 成功
	public static final int HTTP_SUCCESS_WXPAY = 201;// 微信支付 成功
	public static final int HTTP_SUCCESS = 200;// handler 200 成功
	public static final int HTTP_SUCCESS_HEHEPAY = 202;// 全额喝喝币支付
	public static final int HTTP_NEED_LOGIN = 300;// handler 300 成功
	public static final int HTTP_FAILE = 400;// handler 400 失败
	public static final int HTTP_FAILE_LOGIN = 500;// handler 500 失败
	public static final int HTTP_FAILE_CONNECTTIMEOUT = 600;// 网络连接超时
	//1 注册 2 登录 3 找回密码 4 修改手机号 5申请提现 6设置收款账号 7余额支付 8 喝喝币支付
	public static final String CODE_FLAG_1 = "1";// 
	public static final String CODE_FLAG_2 = "2";//
	public static final String CODE_FLAG_3 = "3";//
	public static final String CODE_FLAG_4 = "4";//
	public static final String CODE_FLAG_5 = "5";//
	public static final String CODE_FLAG_6 = "6";//
	public static final String CODE_FLAG_7 = "7";//
	public static final String CODE_FLAG_8 = "8";//
	public static final String CODE_FLAG_10 = "10";//喝喝币支付
	// 保存选择城市信息SP名称
	public static final String SAVE_CHECK_CITY_INFOR = "save_check_city_infor";
	// 商品ID对应KEY
	public static final String PRODUCT_ID_KEY = "product_id_key";
	// 是否批发还是拼单对应KEY
	public static final String PRODUCT_TYPE_KEY = "product_type_key"; //0:拼单 1:批发
	public static final String PRODUCT_TYPE_KEY_JX = "recommend"; // 1:精选
	public static final String PRODUCT_TYPE_KEY_SHARE = "shareType"; // 1:精选
	// activity 请求resuestcode
	public static final int REQUEST_CODE = 200;
	public static final int REQUEST_CODE_TWO = 300;
	public static final int REQUEST_CODE_FOUR = 400;
	public static final int REQUEST_CODE_FIVE = 500;
	public static final int REQUEST_CODE_SIX = 600;
	public static final int REQUEST_CODE_SEVEN = 700;
	public static final String MAIN_TAB_TYPE_KEY = "main_tab_type_key";
	public static final String MAIN_TAB_HOME = "main_tab_home";
	public static final String MAIN_TAB_SHOP_CAR = "main_tab_shop_car";
	public static final String MAIN_TAB_ORDER = "main_tab_order";
	public static final String MAIN_TAB_FIND = "main_tab_FIND";
	public static final String MAIN_TAB_USER = "main_tab_user";
	public static final String SHOPCAR_PRODUCT_LIST_KEY = "shopcar_product_list_key";
	public static final String ORDER_CREATEPREPARE_KEY = "order_createprepare_key";// 预付订单key
	public static final String COOKIE_KEY = "cookie_key";
	//阿里百川key
	public static String APP_KEY = "23361740";
//	public static String APP_KEY = "23322226";
	public static final String ERROE_LOGIN_CODE = "100";// 未登录 需要登录码
	public static final String IS_FORCE_UPDATE_KEY = "is_force_update";
	public static final String UPDATE_DATA_KEY = "update_data_key";
	public static final String LOGIN_OBJ_NAME = "login_obj_name";
	public static final String USER_ID_KEY = "user_id_key";
	public static final String CHECK_CITY_FROM_KEY = "check_city_from_key";
	public static final String CHECK_CITY_FROM_MAIN = "check_city_from_main";
	public static final String CHECK_CITY_FROM_GUIDE = "check_city_from_guide";
	public static final String CHECK_CITY_SHOW_KEY = "check_city_show_key";
	public static final String CHECK_CITY_NOT_SHOW_BACK = "check_city_not_show_back";
	public static final String CHECK_CITY_SHOW_BACK = "check_city_show_back";
	public static final String SHOPPING_CART_IS_SHOW_BACK_KEY = "shopping_cart_is_show_back_key";
	public static final String SHOPPING_CART_NOT_SHOW_BACK = "shopping_cart_not_show_back";
	public static final String SHOPPING_CART_SHOW_BACK = "shopping_cart_show_back";
}
