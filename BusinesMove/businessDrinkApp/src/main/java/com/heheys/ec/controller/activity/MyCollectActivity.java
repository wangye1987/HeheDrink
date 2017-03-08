package com.heheys.ec.controller.activity;

import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.NetWorkState;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.lib.view.RefreshListView;
import com.heheys.ec.model.adapter.BussinessItemAdapter;
import com.heheys.ec.model.adapter.GoodCollectAdapter;
import com.heheys.ec.model.dataBean.AddCollectResultBean;
import com.heheys.ec.model.dataBean.CollectBussinessResultBean;
import com.heheys.ec.model.dataBean.CollectGoodsResultBean;
import com.heheys.ec.model.dataBean.CollectGoodsResultBean.CollectGoodResultListBean.CollectGoods;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.CommonDialog;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import static com.heheys.ec.R.id.linear_empty;


/**
 * 作者：wangkui on 2016/12/26 10:01
 * 邮箱：wangkui20090909@sina.com
 * 说明:我是收藏
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener , RefreshListView.OnRefreshListener, RefreshListView.OnMoreListener{
    private TextView collect_goods;
    private TextView collect_one;
    private LinearLayout my_collect_goods;
    private TextView collect_business;
    private TextView collect_two;
    private LinearLayout my_collect_business;
    private TextView textView11;
    private ViewPager vp_collect;
    private CheckBox collect_all;
    private Button cancle_collect;
    // 列表数据开始和结束位置
    private int startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
            endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
    private int startIndex_bus = ConstantsUtil.DEFAULT_PAGE_START_INDEX,
            endIndex_bus = ConstantsUtil.DEFAULT_PAGE_SIZE;
    // 标记是否是下拉刷新状态
    private boolean isRefresh;
    // 标记是否是分页状态
    private boolean isLoadMore;
    // 标记是否是下拉刷新状态
    private boolean isRefresh_bus;
    // 标记是否是分页状态
    private boolean isLoadMore_bus;
    private View goods_collect;
    private View bussiness_collect;
    private RefreshListView listView_business;
    private RefreshListView listView_goods;
    private MyHandler mHandler = new MyHandler(this);
    private ArrayList<View> viewContainter;
    //商品收藏适配器
    private GoodCollectAdapter goodsAdapter;
    //商家收藏适配器
    private BussinessItemAdapter bussinessAdapter;
    private List<CollectGoods> initdata;
    //选择数据集合
    private List<CollectGoods> deleteinitdata;
    private List<CollectBussinessResultBean.CollectBussiness> initdata_bus;
    private LinearLayout linear_empty_goods;
    private LinearLayout linear_empty_bus;
    //收藏是编辑状态
    private boolean isCanEdit = false;
    private LinearLayout linear_buttom;
    //待删除id集合
    private String deleteAPi;
    private AddCollectResultBean.AddResultBean collectResultListBean;
    private AddCollectResultBean deleteResultBean;
    private List<String> deletefids = new ArrayList<String>();

    @Override
    public void onRefresh() {
        if (!NetWorkState.isNetWorkConnection(this)) {
            ToastUtil.showToast(this, "网络连接失败");
            return;
        }
        if(vp_collect.getCurrentItem() == 0) {
            isRefresh = true;
            initReuquestParams();
            getNetData();
        }else{
            //商家
            isRefresh_bus = true;
            initBusReuquestParams();
            getBusinessData();
        }
    }
    // 更多界面
    private void hideLoadMoreView() {
        if(vp_collect.getCurrentItem() == 0) {
        if (isLoadMore) {
            listView_goods.onLoadComplete();
            goodsAdapter.setNewData(initdata);
            goodsAdapter.notifyDataSetChanged();
            isLoadMore = false;
         }
        }else{
            if (isLoadMore_bus) {
                listView_business.onLoadComplete();
                bussinessAdapter.setNewData(initdata_bus);
                bussinessAdapter.notifyDataSetChanged();
                isLoadMore_bus = false;
            }
        }
    }

    @Override
    public void onLoadMore() {
        if (!NetWorkState.isNetWorkConnection(this)) {
            ToastUtil.showToast(this, "网络连接失败");
            return;
        }
        if(vp_collect.getCurrentItem() == 0) {
            isLoadMore = true;
            startIndex = startIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
            endIndex = endIndex + ConstantsUtil.DEFAULT_PAGE_SIZE;
            getNetData();
        }else{
            isLoadMore_bus = true;
            startIndex_bus = startIndex_bus + ConstantsUtil.DEFAULT_PAGE_SIZE;
            endIndex_bus = endIndex_bus + ConstantsUtil.DEFAULT_PAGE_SIZE;
            getBusinessData();
        }
    }

    private void hideRefreshView() {
        if(vp_collect.getCurrentItem() == 0) {
        if (isRefresh) {
            listView_goods.onRefreshComplete();
            isRefresh = false;
         }
        }else{
            if (isRefresh_bus) {
                listView_business.onRefreshComplete();
                isRefresh_bus = false;
            }
        }
    }
    private void initReuquestParams() {
        startIndex = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
        endIndex = ConstantsUtil.DEFAULT_PAGE_SIZE;
    }
    private void initBusReuquestParams() {
        startIndex_bus = ConstantsUtil.DEFAULT_PAGE_START_INDEX;
        endIndex_bus = ConstantsUtil.DEFAULT_PAGE_SIZE;
    }
    public class MyHandler extends WeakHandler<MyCollectActivity> {

        private MyApplication myApplication;


        public MyHandler(MyCollectActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        public void handleMessage(final Message msg) {
               switch (msg.what){
                   case ConstantsUtil.HTTP_SUCCESS:
                       if(vp_collect.getCurrentItem() == 0)
                       getReference().bindGoodsData();
                       break;
                   case ConstantsUtil.HTTP_SUCCESS_LATER:
                       if(vp_collect.getCurrentItem() == 1)
                       getReference().bindBussinseData();
                       break;
                   case ConstantsUtil.HTTP_SUCCESS_LOGIN_LAST:
                       //接受adapter传递过来的选中待删除收藏ID 集合
//                       deletefids = (List<String>) msg.obj;

                       deleteinitdata = (List<CollectGoods>) msg.obj;
                       deletefids.clear();
                       if(deleteinitdata == null || deleteinitdata.size()==0)
                           return;
                       for(CollectGoods delete:deleteinitdata){
                           if(delete.isCheck()) {
                               deletefids.add(delete.getFid());
                           }
                       }
                       if(deletefids != null && deletefids.size() > 0){
                           if(vp_collect.getCurrentItem() == 0){
                               if(deletefids.size() == initdata.size()){
                                   collect_all.setChecked(true);
                               }else {
                                   collect_all.setChecked(false);
                               }
                           }else{
                               if(deletefids.size() == initdata_bus.size()){
                                   collect_all.setChecked(true);
                               }else {
                                   collect_all.setChecked(false);
                               }
                           }
                           deleteAPi = listToString(deletefids);
                       }else{
                           deleteAPi = "";
                           collect_all.setChecked(false);
                       }
                       break;
                   case 501:
                       //获取添加结果
                       if(getReference().collectResultListBean != null){
                            ToastUtil.showToast(getReference(),"取消成功");
                             getReference().deletefids.clear();
                             deleteAPi = "";
                       }else{
                           ToastUtil.showToast(getReference(),"取消失败");
                       }
                      if(vp_collect.getCurrentItem() == 0)
                          getReference().getNetData();
                      else
                          getReference().getBusinessData();
                       break;
                   case ConstantsUtil.HTTP_FAILE:
                      String msgInfo = (String) msg.obj;
                       if(!StringUtil.isEmpty(msgInfo)){
                           ToastUtil.showToast(getReference(),msgInfo);
                       }else{
                           ToastUtil.showToast(getReference(),"数据异常,请稍后重试");
                       }
                       break;
               }
        }
    }
    //list转String 带 “,”
    public  String listToString(List<String> stringList){
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
    /**
     * 绑定收藏商家数据
     * */
    private void bindBussinseData() {
        //第一页
        if (startIndex_bus == 1) {
            if(collectBussinessResultListBean != null && collectBussinessResultListBean.getList() != null && collectBussinessResultListBean.getList().size() > 0){
                initdata_bus = collectBussinessResultListBean.getList();
                bussinessAdapter.setNewData(collectBussinessResultListBean.getList());
                bussinessAdapter.notifyDataSetChanged();
                hideRefreshView();
                tvRight.setVisibility(View.VISIBLE);
            }else{
                //没数据
                listView_business.setVisibility(View.GONE);
                linear_empty_bus.setVisibility(View.VISIBLE);
                linear_buttom.setVisibility(View.GONE);
                tvRight.setVisibility(View.GONE);
            }
        }else{
            //分页数据
            if(collectBussinessResultListBean != null && collectBussinessResultListBean.getList() != null && collectGoodResultListBean.getList().size() > 0){
                initdata_bus.addAll(collectBussinessResultListBean.getList());
                hideLoadMoreView();
            }else{
                // 回滚页码
                startIndex_bus = startIndex_bus - ConstantsUtil.DEFAULT_PAGE_SIZE;
                endIndex_bus = endIndex_bus - ConstantsUtil.DEFAULT_PAGE_SIZE;
                hideLoadMoreView();
            }
        }
    }

    /**
     * 绑定收藏商品数据
     * */
    private void bindGoodsData() {
        //第一页
        if (startIndex == 1) {
          if(collectGoodResultListBean != null && collectGoodResultListBean.getList() != null && collectGoodResultListBean.getList().size() > 0){
              initdata = collectGoodResultListBean.getList();
              goodsAdapter.setNewData(collectGoodResultListBean.getList());
              goodsAdapter.notifyDataSetChanged();
              hideRefreshView();
              tvRight.setVisibility(View.VISIBLE);
          }else{
            //没数据
              listView_goods.setVisibility(View.GONE);
              linear_empty_goods.setVisibility(View.VISIBLE);
              linear_buttom.setVisibility(View.GONE);
              tvRight.setVisibility(View.GONE);
          }
        }else{
            //分页数据
            if(collectGoodResultListBean != null && collectGoodResultListBean.getList() != null && collectGoodResultListBean.getList().size() > 0){
                initdata.addAll(collectGoodResultListBean.getList());
                hideLoadMoreView();
            }else{
                // 回滚页码
                startIndex = startIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
                endIndex = endIndex - ConstantsUtil.DEFAULT_PAGE_SIZE;
                hideLoadMoreView();
            }
        }
    }

    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.my_collect);
        initView();
    }

    private void initView() {
        rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
        collect_goods = (TextView) findViewById(R.id.collect_goods);
        collect_goods.setOnClickListener(this);
        collect_one = (TextView) findViewById(R.id.collect_one);
        my_collect_goods = (LinearLayout) findViewById(R.id.my_collect_goods);
        my_collect_goods.setOnClickListener(this);
        collect_business = (TextView) findViewById(R.id.collect_business);
        collect_business.setOnClickListener(this);
        collect_two = (TextView) findViewById(R.id.collect_two);
        my_collect_business = (LinearLayout) findViewById(R.id.my_collect_business);
        my_collect_business.setOnClickListener(this);
        textView11 = (TextView) findViewById(R.id.textView11);
        vp_collect = (ViewPager) findViewById(R.id.vp_collect);
        collect_all = (CheckBox) findViewById(R.id.collect_all);
        collect_all.setOnCheckedChangeListener(new AllCheckChange());
        linear_buttom = (LinearLayout) findViewById(R.id.linear_buttom);
        cancle_collect = (Button) findViewById(R.id.cancle_collect);
        cancle_collect.setOnClickListener(this);
        tvRight.setOnClickListener(this);
                /**
                 * 收藏商品视图
                 * */
        goods_collect = LinearLayout.inflate(this, R.layout.collect_listview, null);
        listView_goods = (RefreshListView) goods_collect.findViewById(R.id.list_lv);
        linear_empty_goods = (LinearLayout) goods_collect.findViewById(linear_empty);
        /**
         * 收藏商家视图
         * */
        bussiness_collect = LinearLayout.inflate(this, R.layout.collect_listview, null);
        listView_business = (RefreshListView) bussiness_collect.findViewById(R.id.list_lv);
        linear_empty_bus = (LinearLayout) bussiness_collect.findViewById(linear_empty);
        listView_goods.setonLoadListener(this);
        listView_goods.setonRefreshListener(this);
        listView_business.setonLoadListener(this);
        listView_business.setonRefreshListener(this);
        viewContainter = new ArrayList<View>();
        viewContainter.add(goods_collect);
//        viewContainter.add(bussiness_collect);
        vp_collect.setAdapter(new CollectViewPager());
        vp_collect.addOnPageChangeListener(new MyOnPageChangeListener());
         //初始化空数据
        initdata = new ArrayList<>();
        initdata_bus = new ArrayList<>();
        goodsAdapter = new GoodCollectAdapter(initdata,this,mHandler);
        bussinessAdapter = new BussinessItemAdapter(initdata_bus,this,mHandler);
        listView_goods.setAdapter(goodsAdapter);
        listView_business.setAdapter(bussinessAdapter);
//        getBusinessData();
    }


    class AllCheckChange implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //当按钮被按下时会触发此listener
            if(!buttonView.isPressed())
                return;
            if(vp_collect.getCurrentItem() == 0){
                 //商品页选中全部
                 goodsAdapter.setAllCheck(isChecked);
             }else{
                 //商家页选中全部
                 bussinessAdapter.setAllCheck(isChecked);
             }
        }
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            vp_collect.getParent().requestDisallowInterceptTouchEvent(true);
        }

        @Override
        public void onPageSelected(int position) {
            LineVisible(position);
            if(position == 0)
                bindGoodsData();
                else
                bindBussinseData();

        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    }
    private void LineVisible(int index) {
        AllINVisible();
        switch (index) {
            case 0:
                collect_one.setVisibility(View.VISIBLE);
                collect_two.setVisibility(View.INVISIBLE);
                collect_goods.setTextColor(getResources().getColor(R.color.color_FFd838));
                collect_business.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                collect_one.setVisibility(View.INVISIBLE);
                collect_two.setVisibility(View.VISIBLE);
                collect_business.setTextColor(getResources().getColor(R.color.color_FFd838));
                collect_goods.setTextColor(getResources().getColor(R.color.black));
                break;
                 }
        tvRight.setText("编辑");
        isCanEdit = false;
        if(vp_collect.getCurrentItem() == 0){
            goodsAdapter.setEdit(false);
        }else{
            bussinessAdapter.setEdit(false);
        }
        linear_buttom.setVisibility(View.GONE);
    }

    void AllINVisible() {
        collect_one.setVisibility(View.INVISIBLE);
        collect_two.setVisibility(View.INVISIBLE);
    }
    class CollectViewPager extends PagerAdapter{

        // 适配器
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewContainter.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewContainter.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                // TODO Auto-generated method stub
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewContainter.get(position));
                return viewContainter.get(position);
            }
    }
    @Override
    protected boolean hasTitle() {
        return true;
    }

    @Override
    protected void loadChildView() {

    }
    private CollectBussinessResultBean.CollectBussinseeResultListBean collectBussinessResultListBean;
    private CollectBussinessResultBean bussinesssResultBean;
    @Override
    protected void getNetData() {
        //获取商品收藏数据
        ApiHttpCilent.getInstance(getApplicationContext()).GoodsCollectApi(this, startIndex, endIndex, new CollectGoodCallBack());
    }

    /**
     * 获取商家信息
     * */
    void getBusinessData(){
        ApiHttpCilent.getInstance(getApplicationContext()).BussinessCollectApi(this,"","", startIndex, endIndex, new CollectBussinessCallBack());
    }

    private class CollectBussinessCallBack extends
            BaseJsonHttpResponseHandler<CollectBussinessResultBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, CollectBussinessResultBean arg4) {
            Dimessis();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            mHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              CollectBussinessResultBean arg3) {
        }

        @Override
        protected CollectBussinessResultBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimessis();
            Gson gson = new Gson();
            bussinesssResultBean = gson.fromJson(response,
                    CollectBussinessResultBean.class);
            Message message = Message.obtain();
            if ("1".equals(bussinesssResultBean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS_LATER;
                collectBussinessResultListBean = bussinesssResultBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = bussinesssResultBean.getError().getInfo();
            }
            mHandler.sendMessage(message);
            return bussinesssResultBean;
        }
    }
    private CollectGoodsResultBean.CollectGoodResultListBean collectGoodResultListBean;
    private CollectGoodsResultBean goodsResultBean;

    private class CollectGoodCallBack extends
            BaseJsonHttpResponseHandler<CollectGoodsResultBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, CollectGoodsResultBean arg4) {
            Dimessis();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            message.obj = "";
            mHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              CollectGoodsResultBean arg3) {
        }

        @Override
        protected CollectGoodsResultBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimessis();
            Gson gson = new Gson();
            goodsResultBean = gson.fromJson(response,
                    CollectGoodsResultBean.class);
            Message message = Message.obtain();
            if ("1".equals(goodsResultBean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                collectGoodResultListBean = goodsResultBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = goodsResultBean.getError().getInfo();
            }
            mHandler.sendMessage(message);
            return goodsResultBean;
        }
    }

    /**
     * 隐藏等待框
     */
    private void Dimessis() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MyCollectActivity.this != null
                        && !MyCollectActivity.this.isFinishing()) {
                    if (ApiHttpCilent.loading != null
                            && ApiHttpCilent.loading.isShowing())
                        ApiHttpCilent.loading.dismiss();
                }
            }
        });
    }

    @Override
    protected void reloadCallback() {

    }

    @Override
    protected void setChildViewListener() {

    }

    @Override
    protected String setTitleName() {
        return "我的收藏";
    }

    @Override
    protected String setRightText() {
        return "编辑";
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
    /**
     * 取消收藏
     * */
    private void CancleCollect(){
        ApiHttpCilent.getInstance(getApplicationContext()).CancleCollectApi(this,deleteAPi,new AddCollectCallBack());
    }

    public class AddCollectCallBack extends
            BaseJsonHttpResponseHandler<AddCollectResultBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, AddCollectResultBean arg4) {
            Dimessis();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            mHandler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              AddCollectResultBean arg3) {
        }

        @Override
        protected AddCollectResultBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimessis();
            Gson gson = new Gson();
            deleteResultBean = gson.fromJson(response,
                    AddCollectResultBean.class);
            Message message = Message.obtain();
            if ("1".equals(deleteResultBean.getStatus())) {// 正常
                message.what = 501;
                collectResultListBean = deleteResultBean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = deleteResultBean.getError().getInfo();
            }
            mHandler.sendMessage(message);
            return deleteResultBean;
        }
    }

    public void ShowDialog(String notice, String title) {
        CommonDialog.makeText(baseActivity, title, notice, new CommonDialog.OnDialogListener() {
            @Override
            public void onResult(int result, CommonDialog commonDialog,
                                 String tel) {
                // TODO Auto-generated method stub
                if (CommonDialog.OnDialogListener.LEFT == result ) {
                    CancleCollect();
                    CommonDialog.Dissmess();
                } else {
                    CommonDialog.Dissmess();
                }
            }
        }).showDialog();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.my_collect_goods:
                vp_collect.setCurrentItem(0);
                break;
            case R.id.collect_goods:
                vp_collect.setCurrentItem(0);
                break;
            case R.id.my_collect_business:
                vp_collect.setCurrentItem(1);
                break;
            case R.id.collect_business:
                vp_collect.setCurrentItem(1);
                break;
            case R.id.cancle_collect:
                  if(!StringUtil.isEmpty(deleteAPi)){
                      ShowDialog("是否取消收藏?","提示");
                  }else{
                      ToastUtil.showToast(this,"请选择待删除的数据");
                  }
                break;
            case R.id.base_activity_title_right_righttv:
                if(tvRight.getText().equals("完成")) {
                    //不能编辑
                    tvRight.setText("编辑");
                    isCanEdit = false;
                    linear_buttom.setVisibility(View.GONE);
                    if(vp_collect.getCurrentItem() == 0){
                        goodsAdapter.setEdit(false);
                    }else{
                        bussinessAdapter.setEdit(false);
                    }
                    linear_buttom.setVisibility(View.GONE);
                }else{
                    //可以编辑
                    isCanEdit = true;
                    linear_buttom.setVisibility(View.VISIBLE);
                    tvRight.setText("完成");
                    if(vp_collect.getCurrentItem() == 0){
                        goodsAdapter.setEdit(true);
                    }else{
                        bussinessAdapter.setEdit(true);
                    }
                    linear_buttom.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}