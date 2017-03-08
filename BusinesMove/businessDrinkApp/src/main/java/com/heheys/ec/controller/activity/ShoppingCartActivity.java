package com.heheys.ec.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.adapter.NewShoppingCartFragmentAdapter;
import com.heheys.ec.model.dataBean.DeleteShoppingCartProduct;
import com.heheys.ec.model.dataBean.ErrorBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder;
import com.heheys.ec.model.dataBean.NewOrderBaseBean.ErrorBeanorder.shoppingbean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewProductInfo;
import com.heheys.ec.model.dataBean.NewShoppingCartInforBean.NewShoppingCartInfor;
import com.heheys.ec.model.dataBean.ShoppingCartProductDeleteBean;
import com.heheys.ec.model.dataBean.ShoppingCartSelectBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.IsLogin;
import com.heheys.ec.utils.LogUtil;
import com.heheys.ec.utils.ToastNoNetWork;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.utils.Utils;
import com.heheys.ec.view.AlertDialogCustom;
import com.heheys.ec.view.RefreshableView;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * Created by wangkui on 2016/12/12.
 */

public class ShoppingCartActivity extends BaseActivity  {

    private static final String TAG = ShoppingCartActivity.class.getName();
    private ListView lvListView;
    private NewShoppingCartFragmentAdapter mAdapter;
    public Button btSettlement;
    //底部全选按钮
    public CheckBox cbSelectAllBox;
    public static RelativeLayout rlBottom;
    private LinearLayout llTotalLayout;
//    private LinearLayout base_fragment_shopcart_nodata;
    // 标记是否为编辑状态，默认为false，编辑状态为true
    public boolean isEditType;
    private boolean isRefresh;
    // 购物车商品数据
    public static List<NewProductInfo> productListData;
    private NewShoppingCartInforBean.NewShoppingCartInfor shoppingCartInfor;
    private TextView tvTotalPriceTextView;
    private TextView tvTotalNumPriceTextView;
    // 获取购物车信息消息
    private  shoppingCartMessageThisHandler shoppingCartMessageThisHandler;
    // 删除购物车商品信息消息处理
    private static DeleteProductMessageHandler deleteProductMessageHandler;
    // 缓存非编辑状态下勾选商品的信息
    public static List<ShoppingCartSelectBean> selectProductNotInEdit;
    // 编辑状态下缓存选择删除的商品信息
    public List<ShoppingCartProductDeleteBean> deleteProductList;
    public static NewShoppingCartInforBean shoppingCartData;
    // 预付订单数据bean
    private NewOrderBaseBean.OrderList paymentbean;
    private View footerView;
    //	private View emptyView;
    private LinearLayout linear_no_login;
    private LinearLayout linear_need_buy;
    private Button tv_no_login;
//    private   BaseActivity  baseActivity;
    public boolean isCancelAll = true;
    private TextView no_shopcart_buy;
    private ImageView ivShoppingCart;
    public  static List<NewProductInfo> mydateList;
    private DeleteShoppingCartProduct deleteshoppingCartData;
    private NewOrderBaseBean.OrderList bean;
    private int startIndex;
    private int endIndex;
    int num;//当前商品总数
    private boolean islist;
    private AlertDialogCustom mDialogCoustom;
    private List<ShoppingCartSelectBean> selectProductNotInEditToNext;//存储当前提交成功的商品列表
    private List<ShoppingCartSelectBean> selectProductNotInEditTemp;
    private TextView tv_allcheck;
    private RefreshableView refreshableView;
    private boolean isLogin;

//    public ShoppingCartActivity( baseActivity  baseActivity,ImageView ivShoppingCart) {
//        super();
//        // TODO Auto-generated constructor stub
//        this. baseActivity =  baseActivity;
//        this.ivShoppingCart = ivShoppingCart;
//    }
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        // TODO Auto-generated method stub
//        if(!hidden){
//            initData();
//            getNetData();
//        }
//    }
//    @Override
//    protected View setContentView(LayoutInflater inflater, ViewGroup container,
//                                  Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_shopping_cart,
//                container, true);
//        initView(view);
//        return view;
//    }

//    @Override
//    @SuppressLint("InflateParams")
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        // TODO Auto-generated method stub
//        super.onAttach(context);
//    }

    @Override
    public void onStart() {
        // LogUtil.d(TAG, "onStart");
        super.onStart();
        if(mAdapter!=null && productListData!=null){
            mAdapter.setNewData(productListData);
            mAdapter.notifyDataSetChanged();
        }
        isLogin = IsLogin.isLogin( baseActivity);
    }


    /**
     *
     * Describe:初始化控件
     *
     * Date:2015-9-25
     *
     * Author:liuzhouliang
     */
    private void initView() {
        linear_need_buy = (LinearLayout)findViewById(R.id.base_fragment_shopcart_nodata);
        no_shopcart_buy = (TextView)findViewById(R.id.base_activity_no_shopcart_buy);//去逛逛按钮
        tv_no_login = (Button)findViewById(R.id.base_activity_no_shopcart_login);//登录按钮
        linear_no_login = (LinearLayout)findViewById(R.id.base_acticity_no_login_parent);//需要登录视图
        rlBottom = (RelativeLayout)findViewById(R.id.fragment_shopping_cart_bottom_parent);
        tvTotalPriceTextView = (TextView)findViewById(R.id.fragment_shopping_cart_bottom_totalprice_num);
        tvTotalNumPriceTextView = (TextView)findViewById(R.id.fragment_shopping_cart_bottom_num);
        lvListView = (ListView) findViewById(R.id.fragment_shopping_cart_lv);
        btSettlement = (Button) findViewById(R.id.fragment_shopping_cart_bottom_settlement);
        cbSelectAllBox = (CheckBox)findViewById(R.id.fragment_shopping_cart_bottom_cb);
        tv_allcheck = (TextView) findViewById(R.id.tv_allcheck);
        llTotalLayout = (LinearLayout) findViewById(R.id.fragment_shopping_cart_bottom_totalprice);
        footerView = LinearLayout.inflate(baseActivity,R.layout.footview_white, null);
        refreshableView = (RefreshableView)  findViewById(R.id.refreshable_view);
        ivBack.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        no_shopcart_buy.setOnClickListener(this);
        btSettlement.setOnClickListener(this);
        cbSelectAllBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String, String> map = new HashMap<String, String>();
                if (isChecked) {
                    /**
                     * 全选择
                     */
                    map.put("allchioce", "");
                    MobclickAgent.onEvent(baseActivity, "0043", map);
                    if(mAdapter != null) {
                        if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
                            selectAllProduct();
                        } else {
                        }
                    }
                } else {
                    /**
                     * 取消全选择
                     */
                    map.put("allchiocecancle", "");
                    MobclickAgent.onEvent(baseActivity, "0044", map);
                    if (isCancelAll) {
                        cancelSelectkAll();
                    }

                }
            }
        });
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                Message message = Message.obtain();
                message.what = ConstantsUtil.HTTP_SUCCESS_HEHEPAY;// 错误
                shoppingCartMessageThisHandler.sendMessage(message);
            }
        }, 1);
    }

    /**
     *
     * Describe:初始化数据
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    private void initData() {
        // 初始化默认非编辑状态
        isEditType = false;
        selectProductNotInEdit = new ArrayList<ShoppingCartSelectBean>();
        deleteProductList = new ArrayList<ShoppingCartProductDeleteBean>();
        deleteProductMessageHandler = new DeleteProductMessageHandler(this);
        shoppingCartMessageThisHandler = new shoppingCartMessageThisHandler(this);
//		orderMessageHandler = new MyOrderHandler(this);
        productListData = new ArrayList<NewProductInfo>();
        mDialogCoustom = new AlertDialogCustom();
//        HaseTelMenu();
    }

    /*
     * 删除商品事件
     * */
    private void DeleteGoods(){
        MobclickAgent.onEvent(baseActivity,"C_SC_2");
        if(!NetWorkState.isNetWorkConnection(baseActivity)){
            ToastNoNetWork.ToastError(baseActivity);
            return ;
        }
        if (deleteProductList != null&& deleteProductList.size() > 0) {
            //删除商品
            if(mDialogCoustom != null ){
                mDialogCoustom.AlertShoppingError(2, baseActivity, "确认要删除这"+deleteProductList.size()+"种商品吗?", new AlertDialogCustom.UpdateOrNot() {
                    @Override
                    public void setResult(int modle) {
                        // TODO Auto-generated method stub
                        if(2 == modle){
                            deleteProductRequest();
                        }
                        mDialogCoustom.Demiss();
                    }
                });
            }
        } else {
            ToastUtil.showToast(baseActivity, "请先选中商品");
        }
    }

    /*
     * 结算商品事件
     * */
    private void PayGoods(){
        if(NewShoppingCartFragmentAdapter.cancommit){
            if (selectProductNotInEdit != null && selectProductNotInEdit.size() > 0) {
                for(ShoppingCartSelectBean selectbean:selectProductNotInEdit){
                    if(selectbean.getNum().equals("0")){
                        ToastUtil.showToast(baseActivity, "商品数量填写错误,请重新填写");
                        return;
                    }
                }
                // 生成预付订单 提交aid
                Preprview(selectProductNotInEdit);
            } else {
                ToastUtil.showToast(baseActivity, "请先选中商品");
            }
        }else{
            ToastUtil.showToast(baseActivity, "商品数量填写错误,请您重新填写");
        }
    }

    @SuppressLint("NewApi") @Override
    public void onClick(View v) {
        super.onClick(v);
        final HashMap<String, String> map = new HashMap<String, String>();
        Intent intent = new Intent();
        if (ToastNoNetWork.ToastError(baseActivity))
            return;
        switch (v.getId()) {
            case R.id.base_activity_title_right_righttv:
                map.put("edit", "");
                MobclickAgent.onEvent(baseActivity, "0038", map);
                /**
                 * 编辑事件
                 */
                if ((Boolean) btSettlement.getTag()) {
                    /**
                     * 显示编辑视图===================
                     */
                    isEditType = true;
                    MobclickAgent.onEvent(baseActivity,"C_SC_1");
                    if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
                        cancelSelectkAll();
                        cbSelectAllBox.setChecked(false);
                        cbSelectAllBox.setVisibility(View.VISIBLE);
                        tv_allcheck.setVisibility(View.VISIBLE);
                        btSettlement.setTag(false);
                        btSettlement.setText("删除");
                        btSettlement.setEnabled(true);
                        btSettlement.setBackgroundResource(R.drawable.login_button);
                        tvRight.setText("完成");
                        llTotalLayout.setVisibility(View.INVISIBLE);
                        rlBottom.setVisibility(View.VISIBLE);
                    } else {

                        btSettlement.setTag(false);
                        rlBottom.setVisibility(View.GONE);
                        tvRight.setText("完成");
                    }
                } else {
                    /**
                     * 显示非编辑视图====================
                     */
                    MobclickAgent.onEvent(baseActivity,"C_SC_3");
                    isEditType = false;
                    if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
                        selectAllProduct();
                        btSettlement.setTag(true);
//					btSettlement.setText("去结算");
//					btSettlement.setEnabled(true);
//					tvTotalNumPriceTextView.setText("(共"+NewShoppingCartFragmentAdapter.totalNum+"件)");
                        tvRight.setText("编辑");
                        llTotalLayout.setVisibility(View.VISIBLE);
                    } else {
                        btSettlement.setTag(true);
                        tvRight.setText("编辑");
                        rlBottom.setVisibility(View.GONE);
//					 baseActivity.btProductNum.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.fragment_shopping_cart_bottom_settlement:
                MobclickAgent.onEvent(baseActivity,"C_SC_6");
                /**
                 *
                 * 结算事件和删除事件
                 * 如果是编辑状态下是删除商品
                 */
                if(!(Boolean)btSettlement.getTag()){
                    DeleteGoods();
                }else{
                    PayGoods();
                }
                break;
            case R.id.base_activity_no_shopcart_buy:
                /**
                 * 购物车去逛逛
                 */

                intent.setClass(baseActivity,  MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                StartActivityUtil.startActivity(baseActivity, intent);
                baseActivity.finish();
                break;
            default:
                break;
        }
    }
    //生成预览订单
    private void Preprview(List<ShoppingCartSelectBean> selectProductNotInEdit) {
        selectProductNotInEditToNext = selectProductNotInEdit;
        ApiHttpCilent.getInstance(getApplicationContext()).CreatPaymentOrder(baseActivity,"","1",null,-1,selectProductNotInEdit, "1",null,new MyShoppingCallBack());
    }



    //预览订单返回数据
    class MyShoppingCallBack extends BaseJsonHttpResponseHandler<NewOrderBaseBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, NewOrderBaseBean arg4) {
            Dimess();
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              NewOrderBaseBean arg3) {
            Dimess();
        }

        @Override
        protected NewOrderBaseBean parseResponse(String response, boolean arg1)
                throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            NewOrderBaseBean bean = new NewOrderBaseBean();
            try{
                bean = gson.fromJson(response, NewOrderBaseBean.class);

            }catch(Exception e){
                e.printStackTrace();
            }
            Message message = Message.obtain();
            if ("1".equals(bean.getStatus())) {// 正常
                message.what = ConstantsUtil.ALI_BACK_CODE;
                message.obj = bean.getResult();
            } else {
//                if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
//                        .getCode())) {
//                    message.what = ConstantsUtil.HTTP_FAILE_LOGIN;
//                    message.obj = bean.getError();
//                } else {
                    message.what = ConstantsUtil.HTTP_FAILE_LOGIN;// 错误
                    message.obj = bean.getError();
//                }
            }
            shoppingCartMessageThisHandler.sendMessage(message);
            return bean;
        }
    }
    /**
     *
     * Describe:删除购物车商品请求
     *
     * Date:2015-10-15
     *
     * Author:liuzhouliang
     */
    private void deleteProductRequest() {
        ApiHttpCilent.getInstance(getApplicationContext()).deleteShoppingCartInfor(
                baseActivity, deleteProductList, new DeleteProductCallBack());
    }

    /**
     *
     * Describe:删除购物车商品请求回调
     *
     * Date:2015-10-15
     *
     * Author:liuzhouliang
     */
    public  class DeleteProductCallBack extends
            BaseJsonHttpResponseHandler<DeleteShoppingCartProduct> {

        @SuppressWarnings("deprecation")
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, DeleteShoppingCartProduct arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            deleteProductMessageHandler.sendMessage(message);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              DeleteShoppingCartProduct arg3) {
            Dimess();
        }

        @Override
        protected DeleteShoppingCartProduct parseResponse(String response,
                                                          boolean arg1) throws Throwable {
            Dimess();
            Gson gson = new Gson();
            deleteshoppingCartData = gson.fromJson(
                    response, DeleteShoppingCartProduct.class);
            Message message = Message.obtain();
            if ("1".equals(deleteshoppingCartData.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = deleteshoppingCartData.getError().getInfo();
            }
            deleteProductMessageHandler.sendMessage(message);
            return deleteshoppingCartData;
        }

    }

    /**
     *
     * Describe:删除购物车请求消息回调
     *
     * Date:2015-10-15
     *
     * Author:liuzhouliang
     */
    public static class DeleteProductMessageHandler extends
            WeakHandler<ShoppingCartActivity> {

        public DeleteProductMessageHandler(ShoppingCartActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    /**
                     * 处理成功的数据
                     */
                    ToastUtil.showToast(getReference().baseActivity, "删除成功");
                    getReference().getNetData();
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    /**
                     * 处理失败的数据
                     */
                    getReference().linear_need_buy.setVisibility(View.GONE);
                    getReference().linear_no_login.setVisibility(View.GONE);
                    getReference().tvRight.setVisibility(View.VISIBLE);
                    ErrorBean errorBean = getReference().deleteshoppingCartData.getError();
                    if(errorBean!=null){
                        if(errorBean.getCode().equals("100")){
//					StartActivityUtil.startActivity( baseActivity, LoginActivity.class);
//					LoginSampleHelper.getInstance().loginOut_Sample(getReference().baseActivity);
                            getReference(). baseActivity.finish();
                        }else{
                            ToastUtil.showToast(getReference().baseActivity, StringUtil.isEmpty(errorBean.getInfo())?"删除失败,请稍后重试":errorBean.getInfo());
                        }
                    }else{
                        ToastUtil.showToast(getReference().baseActivity, "数据异常，请稍后重试");
                    }

                    break;
                default:
                    break;
            }
        }

    }

    /**
     *
     * Describe:删除购物车中商品
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    private void deleteSelectProduct() {
        /**
         * 更新缓存选中的商品
         */
        int size = deleteProductList.size();
        Iterator<NewProductInfo> iterator = mAdapter.dataList.iterator();
        while (iterator.hasNext()) {
            String skuId = iterator.next().getId();
            for (int i = 0; i < size; i++) {
                if (deleteProductList.get(i).getId().equals(skuId)) {
                    iterator.remove();
                }
            }
        }
        if (mydateList != null ) {
//			if( mydateList.size() == 0){
//				 baseActivity.btProductNum.setVisibility(View.GONE);
//			}else{
//				 baseActivity.btProductNum.setText(getTotalNum() + "");
//
//			}
        } else {
        }
//		if (mAdapter.dataList != null && mAdapter.dataList.size() == 0) {
//			 baseActivity.btProductNum.setVisibility(View.GONE);
//		} else {
//			 baseActivity.btProductNum.setText(getTotalNum() + "");
//		}
    }

    //获取总件数
    int getTotalNum(){
        num = 0;
        for(NewProductInfo info:ShoppingCartActivity.productListData){
            if(!NewShoppingCartFragmentAdapter.isShowActivityIcon(info.getStatus())){
                num =  num +Integer.parseInt(info.getNum());
            }
        }
        return num;
    }
    /**
     * 获取购物车商品列表
     */
    @Override
    protected void getNetData() {
        if(IsLogin.isLogin(baseActivity)){
            ApiHttpCilent.getInstance(getApplicationContext()).getShoppingCartInfor(
                    baseActivity, new ShoppingCartInforRequestCallBack());
        }else{
            Utils.StartLogin(baseActivity, true);
        }
    }

    //设置购物车数据
    void setShoppingcar(){
        if (shoppingCartData != null && shoppingCartData.getResult() instanceof NewShoppingCartInforBean.NewShoppingCartInfor) {
            shoppingCartInfor = shoppingCartData.getResult();
            productListData = shoppingCartInfor.getList();
            if (productListData != null&& productListData.size() > 0) {
                bindViewData();
                mydateList = mAdapter.dataList;
                lvListView.setVisibility(View.VISIBLE);
                linear_no_login.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("编辑");
            } else {
                /**
                 * 处理空数据
                 */
                showShopCartEmpty();
                tvRight.setVisibility(View.INVISIBLE);
                linear_no_login.setVisibility(View.GONE);
            }

        } else {
            /**
             * 处理空数据
             */
            // ToastUtil.showToast(getReference().baseActivity,
            // "请求数据为空");
            // 处理空白页
            showShopCartEmpty();
            linear_no_login.setVisibility(View.GONE);
        }
    }
    /**
     *
     * Describe:获取购物车信息网络请求回调
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    public  class ShoppingCartInforRequestCallBack extends
            BaseJsonHttpResponseHandler<NewShoppingCartInforBean> {

        @SuppressWarnings("deprecation")
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, NewShoppingCartInforBean arg4) {
            DimissLoding();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            shoppingCartMessageThisHandler.sendMessage(message);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              NewShoppingCartInforBean arg3) {
            DimissLoding();
        }

        @Override
        protected NewShoppingCartInforBean parseResponse(String response,
                                                         boolean arg1) throws Throwable {
            DimissLoding();
            Gson gson = new Gson();
            shoppingCartData = gson.fromJson(response,
                    NewShoppingCartInforBean.class);
            Message message = Message.obtain( );
            if ("1".equals(shoppingCartData.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = shoppingCartData.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = shoppingCartData.getError().getInfo();
            }
            shoppingCartMessageThisHandler.sendMessage(message);
            return shoppingCartData;
        }
    }
    private void DimissLoding(){
        if(baseActivity!=null){
            baseActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ApiHttpCilent.loading.dismiss();
                }
            });}
    }
    //跳转到生成预览订单界面
    private void ToOrderPre(NewOrderBaseBean.OrderList bean) {
        Intent intent = new Intent();
        intent.setClass(baseActivity, NewOrderDetailActivity.class);
        intent.putExtra("bean", bean);
        intent.putExtra("selectProductNotInEditToNext", (Serializable)selectProductNotInEditToNext);
        StartActivityUtil.startActivity(baseActivity,intent);
    }

    /**
     *
     * Describe:处理获取购物车返回数据
     *
     * Date:2015-10-16
     *
     * Author:liuzhouliang
     */
    public static class shoppingCartMessageThisHandler extends
            WeakHandler<ShoppingCartActivity> {

        public shoppingCartMessageThisHandler(ShoppingCartActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.ALI_BACK_CODE:
                    //生成预览订单成功
                    getReference().bean = (NewOrderBaseBean.OrderList) msg.obj;
                    getReference().ToOrderPre(getReference().bean);
                    break;
                case 1000:
                    /**
                     * 商品数量价格更新
                     */
                    Bundle dataBundle = msg.getData();
                    long[] num = dataBundle.getLongArray("totalNum");
                    float productPrice = dataBundle.getFloat("totalPrice");
                    if(num[0] == 0){
                        productPrice = 0;
                    }

                    if(num[1] > 0){
                        //有商品
                        ViewUtil.setActivityPrice(
                                getReference().tvTotalPriceTextView, getReference().DoPrice(productPrice)+ "");
                        getReference().tvTotalNumPriceTextView.setText("(共"+num[1]+"件)");
                        getReference().tvTotalPriceTextView
                                .setText(getReference().tvTotalPriceTextView
                                        .getText() + "元");
                        //底部购物车总个数
//                        getReference(). baseActivity.btProductNum.setText(num[0] + "");

//                        getReference(). baseActivity.btProductNum.setVisibility(View.VISIBLE);
                        getReference().btSettlement.setEnabled(true);
                        getReference().btSettlement.setBackgroundResource(R.drawable.login_button);
                    }else{
                        getReference().btSettlement.setEnabled(false);
                        getReference().btSettlement.setBackgroundColor(getReference().baseActivity.getResources().getColor(R.color.color_888888));
                        ViewUtil.setActivityPrice(
                                getReference().tvTotalPriceTextView, 0 + "");
                        getReference().tvTotalNumPriceTextView.setText("(共0件)");
                        getReference().tvTotalPriceTextView.setText("¥ 0.00");
//                        getReference(). baseActivity.btProductNum.setVisibility(View.INVISIBLE);
                    }
//                    if(num[0]>0){
//                        getReference(). baseActivity.btProductNum.setText(num[0] + "");
//                        getReference(). baseActivity.btProductNum.setVisibility(View.VISIBLE);
//                    }
                    getReference().getPayListLaster();
                    break;
                case ConstantsUtil.HTTP_SUCCESS:
                    /**
                     * 处理成功获取购物车的数据
                     */
                    getReference().refreshableView.finishRefreshing();
                    if (msg.obj != null && msg.obj instanceof NewShoppingCartInfor) {
                        //当前购车数据赋值给首页获取的数据 保持一致
                        getReference().shoppingCartInfor = (NewShoppingCartInfor) msg.obj;
                        ShoppingCartActivity.productListData = getReference().shoppingCartInfor.getList();
                        if (ShoppingCartActivity.productListData != null&& ShoppingCartActivity.productListData.size() > 0) {
                            getReference().bindViewData();
                            mydateList = getReference().mAdapter.dataList;
                            getReference().lvListView.setVisibility(View.VISIBLE);
                            getReference().linear_no_login.setVisibility(View.GONE);
                            getReference().tvRight.setVisibility(View.VISIBLE);
                            getReference().tvRight.setText("编辑");
                        } else {
                            /**
                             * 处理空数据
                             */
                            getReference().showShopCartEmpty();
                            getReference().tvRight.setVisibility(View.INVISIBLE);
                            getReference().linear_no_login.setVisibility(View.GONE);
                            getReference().lvListView.setVisibility(View.GONE);
                            getReference().refreshableView.setVisibility(View.GONE);
                            getReference().rlBottom.setVisibility(View.GONE);
                        }

                    } else {
                        /**
                         * 处理空数据
                         */
                        //清空主页购物车数据
                        getReference().showShopCartEmpty();
                        getReference().linear_no_login.setVisibility(View.GONE);
                    }

                    break;
                case ConstantsUtil.HTTP_FAILE:
                    /**
                     * 处理失败的数据
                     */
                    getReference().refreshableView.finishRefreshing();
                    String messageString = (String) msg.obj;
                    if (messageString != null) {
//					{"result":{},"error":{"info":"服务器异常。","code":"101"},"status":0}
                        if (shoppingCartData.getError().getCode().equals("100")) {
                            StartActivityUtil.startActivity(
                                    getReference().baseActivity,
                                    LoginActivity.class);
                            getReference(). baseActivity.finish();
                        } else {
                            ToastUtil.showToast(getReference().baseActivity,
                                    messageString);
                            getReference().lvListView.setVisibility(View.VISIBLE);
                            getReference().linear_no_login.setVisibility(View.GONE);
                        }
                        /**
                         * 处理失败数据
                         */
                    } else {
                        ToastUtil.showToast(getReference().baseActivity, "请求失败,请查看网络连接");
                        getReference().lvListView.setVisibility(View.VISIBLE);
                        getReference().linear_no_login.setVisibility(View.GONE);
                    }
                    break;
                case ConstantsUtil.HTTP_FAILE_LOGIN:
                    //获取失败的情况下返回不满足条件的商品数量HTTP_FAILE_LOGIN
                    NewOrderBaseBean.ErrorBeanorder ErrorBeanorder =  (ErrorBeanorder) msg.obj;
                    getReference().sortListByBack(ErrorBeanorder);
                    break;
                case ConstantsUtil.HTTP_SUCCESS_HEHEPAY:
                    getReference().getNetData();
                    break;
                default:
                    break;
            }
        }
    }

    private void showShopCartEmpty() {
        linear_need_buy.setVisibility(View.VISIBLE);
    }

    //获取 不符合要求的商品列表
    private void sortListByBack(NewOrderBaseBean.ErrorBeanorder ErrorBeanorder) {
        if(ErrorBeanorder != null){
            final List<shoppingbean> listshoppingbean = ErrorBeanorder.getList();
            if(listshoppingbean !=null ){
                int errorSize = listshoppingbean.size();
                if(errorSize>0){
                    //第一次提交的商品list
                    selectProductNotInEditTemp = new ArrayList<ShoppingCartSelectBean>();
                    selectProductNotInEditTemp.addAll(selectProductNotInEdit);
                    //继续提交 返回的异常商品列表
                    for(NewOrderBaseBean.ErrorBeanorder.shoppingbean a : listshoppingbean){
                        for(ShoppingCartSelectBean bean:selectProductNotInEditTemp){
									/*
									 * 如果当前返回的错误list 里面有第一次提交商品firstlist里面，就在移除firstlist中移除
									 * */
                            if(bean.getAid().equals(a.getAid()) || bean.getSid().equals(a.getSid())){
                                selectProductNotInEditTemp.remove(bean);
                                break;
                            }
                        }
                    }


                    if(selectProductNotInEditTemp!=null && selectProductNotInEditTemp.size()>0){
                        mDialogCoustom.AlertShoppingError(1,baseActivity, StringUtil.isEmpty(ErrorBeanorder.getTips())?"":ErrorBeanorder.getTips(),new AlertDialogCustom.UpdateOrNot() {
                            @Override
                            public void setResult(int modle) {
                                if(2 == modle){
                                    Preprview(selectProductNotInEditTemp);
                                }else{
                                    SortCartListNum(listshoppingbean);
                                }
                                mDialogCoustom.Demiss();
                            }
                        });
                    }else{
                        ToastUtil.showToast(baseActivity, "商品数量存在异常,请您重新确认后再结算");
                    }
                }else{
                    ToastUtil.showToast(baseActivity, StringUtil.isEmpty(ErrorBeanorder.getInfo())?"获取失败":ErrorBeanorder.getInfo());
                }
            }else{
//					ToastUtil.showToast(baseActivity, StringUtil.isEmpty(ErrorBeanorder.getInfo())?"获取失败":ErrorBeanorder.getInfo());
                mDialogCoustom.AlertShoppingError(3,baseActivity, StringUtil.isEmpty(ErrorBeanorder.getTips())?"":ErrorBeanorder.getTips(),new AlertDialogCustom.UpdateOrNot() {
                    @Override
                    public void setResult(int modle) {
                        mDialogCoustom.Demiss();
                    }
                });
            }
        }
    }

    //遍历集合获取当前界面没有库存的数据
    public void SortCartListNum(List<shoppingbean> listshoppingbean) {
        // 购物车数据
        if (shoppingCartData != null && shoppingCartData.getResult() !=null && shoppingCartData.getResult().getList() != null) {
            List<NewProductInfo> listshoping = shoppingCartData.getResult().getList();
            if(listshoping.size()>0){
                for (shoppingbean wholebean:listshoppingbean) {
                    for (NewProductInfo shopcart : listshoping) {
                        if (wholebean.getAid().equals(wholebean.getAid())) {
                            // 购车商品在当前列表有数据
//								shopcart.setNumresult(wholebean.getMsg()+wholebean.getKcnum());
                            break;
                        }
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    /**
     *
     * Describe:清除数据
     *
     * Date:2015-11-3
     *
     * Author:liuzhouliang
     */
    public void clearData() {
        rlBottom.setVisibility(View.GONE);
        // cbSelectAllBox.setChecked(false);
        // cbSelectAllBox.setOnCheckedChangeListener(null);
        // btSettlement.setText("结算");
        // btSettlement.setBackgroundResource(R.drawable.bg_gray_corner);
        // tvTotalPriceTextView.setText("");
        // btSettlement.setOnClickListener(null);
    }

    /**
     *
     * 王奎 处理生产预付订单数据
     *
     * */

    private void Dimess() {
        baseActivity.runOnUiThread(new Runnable() {
            public void run() {
                if(ApiHttpCilent.loading!=null && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

/*	class MyShoppingCallBack extends BaseJsonHttpResponseHandler<NewOrderBaseBean> {
		@Override
		public void onFailure(int arg0, Header[] arg1, Throwable arg2,
				String arg3, NewOrderBaseBean arg4) {
			Dimess();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2,
				NewOrderBaseBean arg3) {
			Dimess();
		}

		@Override
		protected NewOrderBaseBean parseResponse(String response, boolean arg1)
				throws Throwable {
			// TODO Auto-generated method stub
			Dimess();
			Gson gson = new Gson();
			java.lang.reflect.Type type = new TypeToken<NewOrderBaseBean>() {
			}.getType();
			NewOrderBaseBean bean = gson.fromJson(response, type);
			Message message = Message.obtain();
			if ("1".equals(bean.getStatus())) {// 正常
				message.what = ConstantsUtil.HTTP_SUCCESS;
				message.obj = bean.getResult();
			} else {
				if (ConstantsUtil.ERROE_LOGIN_CODE.equals(bean.getError()
						.getCode())) {
					message.what = ConstantsUtil.HTTP_NEED_LOGIN;
				} else {
					message.what = ConstantsUtil.HTTP_FAILE;// 错误
					message.obj = bean.getError().getInfo();
				}
			}
			orderMessageHandler.sendMessage(message);
			return bean;
		}
	}

	public class MyOrderHandler extends WeakHandler<ShoppingCartActivity> {

		@SuppressLint("HandlerLeak")
		public MyOrderHandler(ShoppingCartActivity reference) {
			super(reference);
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				paymentbean = (OrderList) msg.obj;
				if (paymentbean != null) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("commitorder", "");
					MobclickAgent.onEvent(baseActivity, "0047", map);
					Intent intent = new Intent(getActivity(),
							NewOrderDetailActivity.class);
					intent.putExtra(ConstantsUtil.ORDER_CREATEPREPARE_KEY,
							paymentbean);
					intent.putExtra("unique_id", paymentbean.getUnique_id());//服务器返回随机数
					// 结算需要的id num
					ShoppingCartListBean obj = new ShoppingCartListBean();
					obj.setListDataBeans(selectProductNotInEdit);
					intent.putExtra(ConstantsUtil.SHOPCAR_PRODUCT_LIST_KEY, obj);
					intent.putExtra("from", "1");//购物车传1一键购买传0
					StartActivityUtil.startActivity(baseActivity, intent);
				}
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(baseActivity, back);
				// to_pay.setEnabled(false);
				// to_pay.setBackground(getResources().getDrawable(R.drawable.verifystatus_button));
				break;
			default:
				break;
			}
		}
	}
*/

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     * @param listView
     */
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    /**
     *
     * Describe:绑定视图数据
     *
     * Date:2015-9-27
     *
     * Author:liuzhouliang
     */
    private void bindViewData() {
        if (shoppingCartInfor != null && shoppingCartInfor.getList() != null
                && productListData.size() > 0) {
            if(linear_need_buy.getVisibility()==View.VISIBLE){
                linear_need_buy.setVisibility(View.GONE);
            }
            if(linear_no_login.getVisibility()==View.VISIBLE){
                linear_no_login.setVisibility(View.GONE);
            }
            llTotalLayout.setVisibility(View.VISIBLE);
            btSettlement.setBackgroundResource(R.drawable.login_button);
            rlBottom.setVisibility(View.VISIBLE);
            rlBottom.getBackground().setAlpha(204);
//            lvListView.addFooterView(footerView);
//            mAdapter = new NewShoppingCartFragmentAdapter(productListData,
//                    baseActivity, this, shoppingCartMessageThisHandler, lvListView,ivShoppingCart);
            mAdapter = new NewShoppingCartFragmentAdapter(productListData,baseActivity,this,shoppingCartMessageThisHandler,lvListView,ivShoppingCart);
            lvListView.setAdapter(mAdapter);
            btSettlement.setText("去结算");
            tvTotalNumPriceTextView.setText("(共"+NewShoppingCartFragmentAdapter.totalNum+"件)");
            mAdapter.updateDataMessage();

            ViewUtil.setActivityPrice(tvTotalPriceTextView, shoppingCartInfor.getAmount());
            btSettlement.setTag(true);
            /**
             * 初始化默认全选商品
             */
            cbSelectAllBox.setChecked(true);
            selectAllProduct();
            setChildViewListener();
        } else {
            /**
             * 处理列表数据为空
             */

        }

    }
    /**
     *
     * Describe:设置控件监听
     *
     * Date:2015-10-16
     *
     * Author:liuzhouliang
     */
    @Override
    protected void setChildViewListener() {

//        cbSelectAllBox.setOnCheckedChangeListener(this);
    }



//    protected void setChildViewListener() {
//        ivBack.setOnClickListener(this);
//        tvRight.setOnClickListener(this);
//        cbSelectAllBox.setOnCheckedChangeListener(this);
//        no_shopcart_buy.setOnClickListener(this);
//    }

//    @Override
//    protected void setViewListener() {
//        btSettlement.setOnClickListener(this);
//    }
//
//    @Override
//    protected boolean hasTitle() {
//        return true;
//    }
//
//    @Override
//    protected void reloadCallback() {
//        getNetData();
//    }
//
//    @Override
//    protected String setTitleName() {
//        return "购物车";
//    }
//
//    @Override
//    protected String setRightText() {
//        return "编辑";
//    }
//
//    @Override
//    protected int setLeftImageResource() {
//        return 0;
//    }
//
//    @Override
//    protected int setMiddleImageResource() {
//        return 0;
//    }
//
//    @Override
//    protected int setRightImageResource() {
//        return 0;
//    }
//
//    @Override
//    protected boolean isShowLeftIcon() {
//        return true;
//    }
//
//    @Override
//    protected boolean hasTitleIcon() {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    protected boolean hasDownIcon() {
//        // TODO Auto-generated method stub
//        return false;
//    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        if (isChecked) {
//            /**
//             * 全选择
//             */
//            map.put("allchioce", "");
//            MobclickAgent.onEvent(baseActivity, "0043", map);
//            if (mAdapter.dataList != null && mAdapter.dataList.size() > 0) {
//                selectAllProduct();
//            } else {
////				ToastUtil.showToast(baseActivity, "商品为空");
//            }
//        } else {
//            /**
//             * 取消全选择
//             */
//            map.put("allchiocecancle", "");
//            MobclickAgent.onEvent(baseActivity, "0044", map);
//            if (isCancelAll) {
//                cancelSelectkAll();
//            }
//
//        }
//    }

    /**
     *
     * Describe:在非编辑状态下，是否可以取消全部选中
     *
     * Date:2015-11-3
     *
     * Author:liuzhouliang
     */
    private boolean notEditIsShow() {
        for (NewProductInfo productInfor : mAdapter.dataList) {
            if (productInfor.getCheckBoxState()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Describe:取消全部选中
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    private void cancelSelectkAll() {
        if (!isEditType) {
            /**
             * 非编辑状态
             */
            List<NewProductInfo> checkArray = mAdapter.dataList;
            int size = checkArray.size();
            for (int i = 0; i < size; i++) {
                checkArray.get(i).setCheckBoxState(false);
            }
            mAdapter.notifyDataSetChanged();
            cancelAllProductNotInEdit();
        } else {
            /**
             * 编辑状态
             */
            List<NewProductInfo> checkArray = mAdapter.dataList;
            int size = checkArray.size();
            for (int i = 0; i < size; i++) {
                checkArray.get(i).setCheckBoxState(false);
            }
            mAdapter.notifyDataSetChanged();
            cancelAllProductInEdit();
        }
    }
    // 处理成2位小数点
    private String DoPrice(float ft) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String fts=fnum.format(ft);
        return fts;
    }
    /**
     *
     * Describe:全部选中事件
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    private void selectAllProduct() {
        if (!isEditType) {
            /**
             * 非编辑状态:过滤已经活动过期的商品
             */
            if (mAdapter != null && mAdapter.dataList != null) {

                List<NewProductInfo> checkArray = mAdapter.dataList;
                int size = checkArray.size();
                for (int i = 0; i < size; i++) {
                    checkArray.get(i).setCheckBoxState(true);
                }
                mAdapter.notifyDataSetChanged();

                mAdapter.getTotalPrice();
                btSettlement.setText("去结算");
                tvTotalNumPriceTextView.setText("(共"+NewShoppingCartFragmentAdapter.totalNum+"件)");
//				btSettlement.setText("结算" + "(" + mAdapter.totalNum + ")");
//				if (NewShoppingCartFragmentAdapter.totalNum == 0) {
//					 baseActivity.btProductNum.setVisibility(View.GONE);
//				} else {
//					 baseActivity.btProductNum.setVisibility(View.VISIBLE);
////					 baseActivity.btProductNum.setText(getTotalNum() + "");
//					 baseActivity.btProductNum.setText(NewShoppingCartFragmentAdapter.totalNum + "");
//
//				}
                ViewUtil.setActivityPrice(tvTotalPriceTextView,
                        DoPrice(mAdapter.totalPrice) + "");
                tvTotalPriceTextView.setText(tvTotalPriceTextView.getText()
                        + "元");
                // cbSelectAllBox.setChecked(true);
                selectAllProductNotInEdit();
                // LogUtil.d("TAG", selectProductNotInEdit.toString());
            }
        } else {
            /**
             * 编辑状态
             */

            List<NewProductInfo> checkArray = mAdapter.dataList;
            int size = checkArray.size();
            for (int i = 0; i < size; i++) {
                checkArray.get(i).setCheckBoxState(true);
            }
            mAdapter.notifyDataSetChanged();
            btSettlement.setText("删除");
            /**
             * 缓存所有选中的商品信息
             */
            selectAllProductInEdit();
        }

    }

    /**
     *
     * 计算需要提交的商品数量
     *
     * */
    private void getPayListLaster(){
        selectProductNotInEdit.clear();
        int size = mAdapter.dataList.size();
        for (int i = 0; i < size; i++) {
            String stateString = mAdapter.dataList.get(i).getStatus();
            boolean isOutTime = isActivityOutTime(stateString);
            if (!isOutTime &&  mAdapter.dataList.get(i).getCheckBoxState()) {
                /**
                 * 商品活动未过期
                 */
                ShoppingCartSelectBean dataBean = new ShoppingCartSelectBean();
                dataBean.setNum(mAdapter.dataList.get(i).getItemNewNum());//商品数量
                if(mAdapter.dataList.get(i).getIssuit() != 2){
                    dataBean.setAid(mAdapter.dataList.get(i).getAid());//活动ID
                    dataBean.setSid("");
                }else{
                    dataBean.setSid(mAdapter.dataList.get(i).getSid());//活动ID
                    dataBean.setAid("");
                }
                selectProductNotInEdit.add(dataBean);
            }
        }
    }
    /**
     * 非编辑状态下，全部选择商品的缓存
     */
    private void selectAllProductNotInEdit() {
        selectProductNotInEdit.clear();
        int size = mAdapter.dataList.size();

        for (int i = 0; i < size; i++) {
            String stateString = mAdapter.dataList.get(i).getStatus();
            boolean isOutTime = isActivityOutTime(stateString);
            if (!isOutTime) {
                /**
                 * 商品活动未过期
                 */
                ShoppingCartSelectBean dataBean = new ShoppingCartSelectBean();
                dataBean.setNum(mAdapter.dataList.get(i).getItemNewNum());//商品数量
                if(mAdapter.dataList.get(i).getIssuit() != 2){
                    dataBean.setAid(mAdapter.dataList.get(i).getAid());//活动ID
                    dataBean.setSid("");
                }else{
                    dataBean.setSid(mAdapter.dataList.get(i).getSid());//活动ID
                    dataBean.setAid("");
                }
                selectProductNotInEdit.add(dataBean);
            }
        }

        if (selectProductNotInEdit != null
                && selectProductNotInEdit.size() == 0) {
            /**
             * 没有可以结算的商品吗，置灰结算控件
             */
            btSettlement.setBackgroundColor(baseActivity.getResources().getColor(R.color.color_888888));
            btSettlement.setEnabled(false);
            cbSelectAllBox.setChecked(false);
            cbSelectAllBox.setVisibility(View.INVISIBLE);
            tv_allcheck.setVisibility(View.INVISIBLE);

        } else {
            btSettlement.setBackgroundResource(R.drawable.login_button);
            btSettlement.setEnabled(true);
            cbSelectAllBox.setChecked(true);
            cbSelectAllBox.setVisibility(View.VISIBLE);
            tv_allcheck.setVisibility(View.VISIBLE);
        }

    }
    private boolean isActivityOutTime(String state) {
        if ("0".equals(state)) {
            /**
             * 未开始
             */
            return false;
        } else if ("1".equals(state)) {
            /**
             * 已经开始
             */
            return false;
        } else if ("2".equals(state)) {
            /**
             * 已经抢光
             */
            return true;
        } else if ("3".equals(state)) {
            /**
             * 未成单
             */
            return true;
        }else if ("4".equals(state)) {
            /**
             * 已经成单 不能生成订单
             */
            return true;
        } else if ("5".equals(state)) {
            /**
             * 已经售罄抢光
             */
            return true;
        }else if ("6".equals(state)) {
            /**
             * 已经下架
             */
            return true;
        }else if ("7".equals(state)) {
            /**
             * 已经结束
             */
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Describe:编辑状态下，全部缓存商品
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     *
     */
    private void selectAllProductInEdit() {
        deleteProductList.clear();
        int size = mAdapter.dataList.size();
        for (int i = 0; i < size; i++) {
            String skuid = mAdapter.dataList.get(i).getAid();
            boolean isInCache = isInCacheDeleteProduct(skuid);
            if (!isInCache) {
                ShoppingCartProductDeleteBean dataBean = new ShoppingCartProductDeleteBean();
                dataBean.setId(mAdapter.dataList.get(i).getId());
//				dataBean.setIid(mAdapter.dataList.get(i).getId());
                deleteProductList.add(dataBean);
            }

        }
        // ToastUtil.showToast(baseActivity, deleteProductList.size() + "");
    }

    /**
     * 判断在编辑状态下，缓存中是否存在已经选中的商品
     *
     * @param skuid
     * @return
     */
    private boolean isInCacheDeleteProduct(String skuid) {
        boolean isAdd = false;
        /**
         * 判断当前选中的商品，在缓存中是否存在
         */
        for (int i = 0; i < deleteProductList.size(); i++) {
            if (skuid.equals(deleteProductList.get(i).getId())) {

                isAdd = true;
            }
        }
        return isAdd;
    }

    /**
     *
     * Describe:编辑状态下取消选中全部商品
     *
     * Date:2015-10-9
     *
     * Author:liuzhouliang
     */
    private void cancelAllProductInEdit() {
        if (deleteProductList != null) {
            deleteProductList.clear();
        }
        // ToastUtil.showToast(baseActivity, deleteProductList.size() + "");
    }

    /**
     * 非编辑状态下，取消全部商品信息缓存
     */
    public void cancelAllProductNotInEdit() {
        if (selectProductNotInEdit != null) {
            selectProductNotInEdit.clear();
            btSettlement.setText("去结算");
            btSettlement.setEnabled(false);
            tvTotalNumPriceTextView.setText("(共0件)");
            tvTotalPriceTextView.setText("¥ 0.00");
            btSettlement.setBackgroundColor( baseActivity.getResources().getColor(R.color.color_888888));
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		if (MyApplication.isRefreshShopcart) {
//			getNetData();
//			MyApplication.isRefreshShopcart = true;
//			hideShopCartEmpty();
//		}
        LogUtil.d(TAG, "onResume");
        MobclickAgent.onPageStart("PG_SCART");
    }

    @Override
    public void onDestroy() {
        if(ApiHttpCilent.loading !=null && ApiHttpCilent.loading.isShowing())
            ApiHttpCilent.loading .dismiss();
        ApiHttpCilent.loading = null;
        super.onDestroy();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtil.d(TAG, "onStop");
        MobclickAgent.onPageEnd("PG_SCART");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }
    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.fragment_shopping_cart);
        initData();
        initView();
    }

    @Override
    protected boolean hasTitle() {
        return true;
    }

    @Override
    protected void loadChildView() {


    }


    @Override
    protected void reloadCallback() {

    }


    @Override
    protected String setTitleName() {
        return "购物车";
    }

    @Override
    protected String setRightText() {
        return null;
    }

    @Override
    protected int setLeftImageResource() {
        return 0;
    }

    @Override
    protected int setMiddleImageResource() {
        return 0;
    }

    @Override
    protected int setRightImageResource() {
        return 0;
    }

}
