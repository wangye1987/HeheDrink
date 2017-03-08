package com.heheys.ec.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.adapter.LocationAdapter;
import com.heheys.ec.model.dataBean.LoactionBean;
import com.heheys.ec.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：wangkui on 2016/12/21 17:29
 * 邮箱：wangkui20090909@sina.com
 * 说明:定位地址
 */

public class LocationSearchActivity extends BaseActivity  implements OnGetGeoCoderResultListener {
    private EditText search_location;
    private TextView tv_nowlcation;
    private TextView tv_location;
    private ListView location_lv;
    private LocationAdapter locationAdater;
    private List<LoactionBean> listLocation;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private String tempcoor = "bd09ll";
    private String locationCity;
    private String currentCheckCity;
    private PoiSearch mPoiSearch;
    private LinearLayout top_content;
    private boolean isShow = true;
    GeoCoder mSearch = null;
    //当前点击的地址
    String add = "";
    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.location);
        initView();
        initData();
    }

    private void initData() {
        //定位
        InitData();
        mPoiSearch = PoiSearch.newInstance();
//        第二步，创建POI检索监听者；
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
            public void onGetPoiResult(PoiResult result){
                //获取POI检索结果
                if(result != null){
                    List<PoiInfo>  poiAddrInfos = result.getAllPoi();
                    if(poiAddrInfos != null && poiAddrInfos.size()>0) {
                        top_content.setVisibility(View.GONE);
//                        top_content.setAnimationStyle(R.style.popwin_anim_style);
//                        top_content.setAnimation(AnimationUtils.loadAnimation());
                        for (PoiInfo info : poiAddrInfos) {
                            LoactionBean bean = new LoactionBean();
                            bean.setNameContent(info.address);
                            bean.setNameTitle(info.name);
                            bean.setCityName(info.city);
                            bean.setLatitude(info.location.latitude);
                            bean.setLongitude(info.location.longitude);
                            listLocation.add(bean);
                        }
                        locationAdater.notifyDataSetChanged();
                    }else{
                        ToastUtil.showToast(getApplicationContext(),"未查询到结果");
                    }
                }
            }
            public void onGetPoiDetailResult(PoiDetailResult result){
                //获取Place详情页检索结果

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
//        第三步，设置POI检索监听者；
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
//        第四步，发起检索请求；

    }
        //获取反编译地址
      private void SearchAddress(String city,String key){
          mPoiSearch.searchInCity((new PoiCitySearchOption())
                  .city(city)
                  .keyword(key)
                  .pageNum(10));
      }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_location:
                //获取地址
                if(mLocationClient != null){
                    mLocationClient.start();
//                    if (mLocationClient != null
//                            && mLocationClient.isStarted())
//                        mLocationClient.requestLocation();
                }
                break;
            case R.id.tv_nowlcation:
                //获取
                if(!StringUtil.isEmpty(tv_nowlcation.getText().toString())) {
                    if( tv_nowlcation.getText().toString().contains("定位失败")){
                        Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("add", add);
                        intent.putExtra("cityName", cityName);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;

        }
    }
    BDLocation location;
    public BDLocationListener myListener = new MyLocationListener();
    private String cityName;

    public void setLocation(BDLocation location) {

        this.location = location;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        //坐标反编译名称
        Intent intent=new Intent();
        intent.putExtra("add",  reverseGeoCodeResult.getAddressDetail().district+reverseGeoCodeResult.getAddressDetail().street+reverseGeoCodeResult.getAddressDetail().streetNumber);
        intent.putExtra("cityName", reverseGeoCodeResult.getAddressDetail().city);
        setResult(RESULT_OK, intent);
        finish();
    }

    //获取定位数据
    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null) {
                ToastUtil.showToast(getApplicationContext(),"定位失败",Toast.LENGTH_SHORT);
                return;
            }
            if (location != null) {
                if (StringUtil.isEmpty(location.getCity())) {
                    mLocationClient.stop();
                    locationCity = "定位失败,请重新请求";
                    if(isShow)
                        ToastUtil.showToast(getApplicationContext(),locationCity,Toast.LENGTH_SHORT);
                    tv_nowlcation.setText("定位失败,点击查看定位服务");
                    isShow = false;
                } else {
                    currentCheckCity = location.getCity();
                    locationCity = location.getCity();
                    setLocation(location);
                    tv_nowlcation.setText(location.getAddrStr());
                    add = location.getDistrict()+location.getStreet()+location.getStreetNumber();
                    cityName = location.getCity();
                    mLocationClient.stop();
                    ToastUtil.showToast(getApplicationContext(),"定位成功",Toast.LENGTH_SHORT);
                }

            }
        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }
    void InitData() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(baseActivity);
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();


        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
    }

    /**
     * 跳转定位服务界面
     *
     * @param context 全局信息接口
     */
    public static void gotoLocServiceSettings(Context context) {
        final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
//    private void initLocation(){
////        mLocationClient = new LocationClient(this.getApplicationContext());
//        mLocationClient = MyApplication.getInstance().mLocationClient;
//        mMyLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener((BDLocationListener) mMyLocationListener);
//        initLocations();
//        mLocationClient.start();
//        // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//        mLocationClient.requestLocation();
//    }

//    private void initLocations() {
//        LocationClientOption option = new LocationClientOption();
////		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType(tempcoor);// 可选，默认gcj02，设置返回的定位结果坐标系，
//        int span = 3000;
//        try {
//            span = Integer.valueOf(1000);
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        option.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);
//        option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        mLocationClient.setLocOption(option);
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch= null;
        mLocationClient.stop();
        mPoiSearch.destroy();
        mSearch.destroy();
    }


    class MyItemLister implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            LatLng ptCenter = new LatLng(listLocation.get(position).getLatitude(), listLocation.get(position).getLongitude());
            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(ptCenter));

            add = listLocation.get(position).getNameContent();

        }
    }
    private void initView() {
        top_content = (LinearLayout) findViewById(R.id.top_content);
        search_location = (EditText) findViewById(R.id.search_location);
        tv_nowlcation = (TextView) findViewById(R.id.tv_nowlcation);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_location.setOnClickListener(this);
        tv_nowlcation.setOnClickListener(this);
        location_lv = (ListView) findViewById(R.id.location_lv);
        listLocation = new ArrayList<>();
        locationAdater = new LocationAdapter(listLocation,this);
        location_lv.setAdapter(locationAdater);
        //点击返回
        location_lv.setOnItemClickListener(new MyItemLister());
        search_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					/**
					 * 搜索事件
					 */
					HashMap<String, String> mapss = new HashMap<String, String>();
					mapss.put("seach", "");
					MobclickAgent.onEvent(baseActivity, "0019", mapss);
					if (StringUtil.isEmpty(search_location.getText().toString())) {
						ToastUtil.showToast(baseActivity, "请输入搜索内容");
					} else {
                        if (location !=null && !StringUtil.isEmpty(location.getCity())) {
                            SearchAddress(location.getCity(), search_location.getText().toString());
                        }else{
                            ToastUtil.showToast(baseActivity, "定位失败，搜索服务不可用");
                        }
                    }
				}
				return false;
			}
		});
    }

    @Override
    protected boolean hasTitle() {
        return true;
    }

    @Override
    protected void loadChildView() {

    }

    @Override
    protected void getNetData() {

    }

    @Override
    protected void reloadCallback() {

    }

    @Override
    protected void setChildViewListener() {

    }

    @Override
    protected String setTitleName() {
        return "选择当前位置";
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

    private void submit() {
        // validate
        String location = search_location.getText().toString().trim();
        if (TextUtils.isEmpty(location)) {
            Toast.makeText(this, "location不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}
