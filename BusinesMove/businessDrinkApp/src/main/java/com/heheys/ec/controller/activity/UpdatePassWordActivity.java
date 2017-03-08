package com.heheys.ec.controller.activity;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.utils.StringUtil;
import com.heheys.ec.lib.utils.WeakHandler;
import com.heheys.ec.model.dataBean.BaseMessageBean;
import com.heheys.ec.model.dataBean.MyBalanceBaseBean;
import com.heheys.ec.netWorkHelper.ApiHttpCilent;
import com.heheys.ec.utils.ActivityManagerUtils;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.heheys.ec.view.DeleteEditText;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

/**
 * 作者：wangkui on 2016/12/20 09:39
 * 邮箱：wangkui20090909@sina.com
 * 说明:修改登录密码
 */

public class UpdatePassWordActivity extends BaseActivity {

    private String mobile;
    private TextView name;
    private DeleteEditText et_pw;
    private DeleteEditText pw_one;
    private LinearLayout setting_new_pwa;
    private DeleteEditText pw_two;
    private MyUpdateHandler handler = new MyUpdateHandler(this);
    private Button bt_subimt;

    @Override
    protected void onCreate() {
        setBaseContentView(R.layout.update_password);
        initView();
        initData();
    }
    public static class MyUpdateHandler extends WeakHandler<UpdatePassWordActivity> {

        private MyBalanceBaseBean.ResultBanlanceBean resultBanlanceBean;

        public MyUpdateHandler(UpdatePassWordActivity reference) {
            super(reference);
            // TODO Auto-generated constructor stub
        }

        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.HTTP_SUCCESS:
                    ToastUtil.showToast(getReference(),"修改成功");
                    getReference().GoLogin();
                    break;
                case ConstantsUtil.HTTP_FAILE:
                    BaseMessageBean.BaseErrorBean error  = (BaseMessageBean.BaseErrorBean) msg.obj;
                    if(error != null) {
                        ToastUtil.showToast(getReference(), StringUtil.isEmpty(error.getInfo())?"修改失败":error.getInfo());
                    }else{
                        ToastUtil.showToast(getReference(), "数据错误,请稍后重试");
                    }
                    break;
             }
        }
    }
    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mobile = intent.getStringExtra("mobile");
            name.setText(StringUtil.isEmpty(mobile) ? "" : mobile);
        }
    }

    private void GoLogin(){
        Intent intent = new Intent(UpdatePassWordActivity.this,LoginActivity.class);
        StartActivityUtil.startActivity(UpdatePassWordActivity.this,intent);
        //关闭指定界面
        ActivityManagerUtils.getInstance().finishActivityclass(UpdatePassWordActivity.class);
        ActivityManagerUtils.getInstance().finishActivityclass(CountSettingActivity.class);
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
        return "修改密码";
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

    private void initView() {
        ActivityManagerUtils.getInstance().addActivity(this);
        rlTitlePrent.setBackgroundColor(getResources().getColor(R.color.white));
        bt_subimt = (Button) findViewById(R.id.bt_subimt);
        name = (TextView) findViewById(R.id.name);
        et_pw = (DeleteEditText) findViewById(R.id.et_pw);
        pw_one = (DeleteEditText) findViewById(R.id.pw_one);
        setting_new_pwa = (LinearLayout) findViewById(R.id.setting_new_pwa);
        pw_two = (DeleteEditText) findViewById(R.id.pw_two);
        bt_subimt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bt_subimt:
            submit();
            break;
        }
    }

    private void submit() {
        // validate
        String pw = et_pw.getText().toString().trim();
        if (TextUtils.isEmpty(pw)) {
            Toast.makeText(this, "登录密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String one = pw_one.getText().toString().trim();
        if (TextUtils.isEmpty(one)) {
            Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String two = pw_two.getText().toString().trim();
        if (TextUtils.isEmpty(two)) {
            Toast.makeText(this, "确定新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pw.length() < 6) {
            Toast.makeText(this, "登录密码长度不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pw_one.length() < 6) {
            Toast.makeText(this, "新密码长度不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pw_two.length() < 6) {
            Toast.makeText(this, "确定密码长度不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!two.equals(one)){
            Toast.makeText(this, "新旧密码不一致,请重新填写", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        ApiHttpCilent.getInstance(getApplicationContext()).UpdatePassWord(baseActivity,pw,two,
                new UpdatePawCallBack());

    }

    private void Dimess() {
        baseActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }
    public class UpdatePawCallBack extends BaseJsonHttpResponseHandler<BaseMessageBean> {

        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2,
                              String arg3, BaseMessageBean arg4) {
            Dimess();
            Message message = Message.obtain();
            message.what = ConstantsUtil.HTTP_FAILE;// 错误
            handler.sendMessage(message);
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2,
                              BaseMessageBean arg3) {
        }

        @Override
        protected BaseMessageBean parseResponse(String response, boolean arg1)
                throws Throwable {
            Dimess();
            Gson gson = new Gson();
            BaseMessageBean baseMessageBean = gson.fromJson(response, BaseMessageBean.class);
            if (baseMessageBean != null) {
                Message msg = new Message();
                if (("1").equals(baseMessageBean.getStatus())) {
                    msg.what = ConstantsUtil.HTTP_SUCCESS;
                    msg.obj = baseMessageBean.getResult();
                } else if (("0").equals(baseMessageBean.getStatus())) {
                    msg.what = ConstantsUtil.HTTP_FAILE;
                    msg.obj = baseMessageBean.getError();
                }
                handler.sendMessage(msg);
            }
            return baseMessageBean;
        }
    }
}
