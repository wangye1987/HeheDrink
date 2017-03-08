package com.heheys.ec.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.Bean;
import com.heheys.ec.model.dataBean.ProvinceListBaseBean.CityBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by wangkui on 2016/12/14.
 */

public class AddressChioceActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;
    private Bean proviceDate;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

    /**
     * key - 省 value - 市ID
     */
    protected Map<String, String> mCitisDatasMapID = new HashMap<String, String>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区ID
     */
    protected Map<String, String> mDistrictDatasMapID = new HashMap<String, String>();

    /**
     * key - 区 values - ID
     */
    protected Map<String, String> mIdcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";
    /**
     * 省数据
     * */
    private String mProvinceDatas[] ;
    /**
     *
     * 当前选中城市ID
     * */
    private int cityId;
    //省份ID
    private int proviceId;
    //地区ID
    private String countyId;
    private int screenHeight;
    private Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          switch (msg.what){
              case ConstantsUtil.HTTP_SUCCESS:
                  // 存储省份 城市 地区 元数据
                  Bean bean = (Bean) msg.obj;
                  proviceDate = bean;
                  setUpData();
                  break;
          }
      }
  };



    @Override
    protected boolean hasTitle() {
        return false;
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
        return null;
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
    @Override
    protected void onCreate() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏显示
        setContentView(R.layout.address_chioce);
        setUpViews();
        setUpListener();
        init();
    }

    private void init() {
        // 获取屏幕高度
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                 .getHeight();
        WindowManager.LayoutParams lp = getWindow().getAttributes();// //lp包含了布局的很多信息，通过lp来设置对话框的布局
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
            lp.height = screenHeight * 1 / 2;// lp高度设置为屏幕的1/2
//        getWindow().setAttributes(lp);// 将设置好属性的lp应用到对话框


    }
    private void Dimess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (ApiHttpCilent.loading != null
                        && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }
    class MyCallBack extends BaseJsonHttpResponseHandler<ProvinceListBaseBean> {
        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, ProvinceListBaseBean arg4) {
            Dimess();
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              ProvinceListBaseBean arg3) {
            Dimess();
        }

        @Override
        protected ProvinceListBaseBean parseResponse(String response,
                                                     boolean arg1) throws Throwable {
            // TODO Auto-generated method stub
            Dimess();
            Gson gson = new Gson();
            ProvinceListBaseBean bean = gson.fromJson(response,
                    ProvinceListBaseBean.class);
            Message message = Message.obtain();
            if ("1".equals(bean.getStatus())) {// 正常
                message.what = ConstantsUtil.HTTP_SUCCESS;
                message.obj = bean.getResult();
            } else {
                message.what = ConstantsUtil.HTTP_FAILE;// 错误
                message.obj = bean.getError().getInfo();
            }
            handler.sendMessage(message);

            return bean;
        }
    }
    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);

        ApiHttpCilent.getInstance(getApplicationContext()).InitProvinceList(this,
                new MyCallBack());
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
    }
    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AddressChioceActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        proviceId =Integer.parseInt(mCitisDatasMapID.get(mCurrentProviceName));
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        cityId = Integer.parseInt(mDistrictDatasMapID.get(mCurrentCityName));
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        mCurrentDistrictName = areas[0];
        countyId = mIdcodeDatasMap.get(mCurrentCityName+mCurrentDistrictName);
        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }


    /**
     * 获取省市区数据
     * */
    private void initProvinceDatas() {

        List<ProvinceListBaseBean.ProvinceList> provice = proviceDate.getList();

        //*/ 初始化默认选中的省、市、区
        if (provice!= null && !provice.isEmpty()) {
            mCurrentProviceName = provice.get(0).getProvince().getName();
            proviceId = provice.get(0).getProvince().getId();
            List<ProvinceListBaseBean.CityBean> cityList = provice.get(0).getProvince().getCity();
            if (cityList!= null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                cityId = cityList.get(0).getId();
                List<ProvinceListBaseBean.County> districtList = cityList.get(0).getCounty();
                mCurrentDistrictName = districtList.get(0).getName();
                countyId = districtList.get(0).getId()+"";
            }
        }
        mProvinceDatas = new String[provice.size()];
        for (int i=0; i< provice.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provice.get(i).getProvince().getName();
            List<CityBean> cityList = provice.get(i).getProvince().getCity();
            mCitisDatasMapID.put(provice.get(i).getProvince().getName(),provice.get(i).getProvince().getId()+"");
            String[] cityNames = new String[cityList.size()];
            for (int j=0; j< cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                cityNames[j] = cityList.get(j).getName();
                //城市
                List<ProvinceListBaseBean.County> districtList = cityList.get(j).getCounty();
                String[] distrinctNameArray = new String[districtList.size()];
                mDistrictDatasMapID.put(cityList.get(j).getName(),cityList.get(j).getId()+"");
                ProvinceListBaseBean.County[] distrinctArray = new ProvinceListBaseBean.County[districtList.size()];
                for (int k=0; k<districtList.size(); k++) {
                    // 遍历市下面所有区/县的数据
                    ProvinceListBaseBean.County districtModel = new  ProvinceListBaseBean.County(districtList.get(k).getName(), districtList.get(k).getId());
                    // 区/县对于的id，保存到mZipcodeDatasMap
                    //key 存储的值是城市名字和地区名字 防止地区名字一直 获取到的value值不一致
                    mIdcodeDatasMap.put(cityList.get(j).getName()+districtList.get(k).getName(), districtList.get(k).getId()+"");
                    distrinctArray[k] = districtModel;
                    distrinctNameArray[k] = districtList.get(k).getName();

                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provice.get(i).getProvince().getName(), cityNames);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
//        Toast.makeText(AddressChioceActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
//                +mCurrentDistrictName+","+distanceid, Toast.LENGTH_SHORT).show();
        if(!StringUtil.isEmpty(countyId)) {
            Intent intent = new Intent();
            intent.putExtra("name", mCurrentProviceName + mCurrentCityName + mCurrentDistrictName);
            intent.putExtra("cityId", cityId);
            intent.putExtra("proviceId",proviceId);
            intent.putExtra("countryid",Integer.parseInt(countyId));
            intent.putExtra("cityName", mCurrentCityName);
            intent.putExtra("proviceName", mCurrentProviceName);
            intent.putExtra("countryName", mCurrentDistrictName);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            ToastUtil.showToast(this,"请选择地区");
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
           countyId = mIdcodeDatasMap.get(mCurrentCityName+mCurrentDistrictName);
        }
    }
}
