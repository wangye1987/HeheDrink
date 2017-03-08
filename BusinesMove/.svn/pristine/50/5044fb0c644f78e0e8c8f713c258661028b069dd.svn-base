package com.heheys.ec.callback; 

import org.apache.http.Header;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.model.dataBean.BaseBean;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.ToastUtil;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2015-9-29 下午4:00:47 
 * 类说明 
 * @param
 */
public class  MyCallBack {
	private Context context;
	MyCallBack(Context context){
		this.context = context;
	}
	//接口123
    class CallBacK  extends  BaseJsonHttpResponseHandler<BaseBean>{
	@Override
	public void onFailure(int arg0, Header[] arg1, Throwable arg2,
			String arg3, BaseBean arg4) {
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, String arg2,
			BaseBean arg3) {
	}

	@Override
	protected BaseBean parseResponse(String response, boolean arg1)
			throws Throwable {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		BaseBean loginBean = gson.fromJson(response,BaseBean.class);
		Message message = Message.obtain();
		if("1".equals(loginBean.getStatus())){//正常
			message.what = ConstantsUtil.HTTP_SUCCESS;
		}else{
			message.what = ConstantsUtil.HTTP_FAILE;//错误
			message.obj = loginBean.getError().getInfo();
		}
		handlerLogin.sendMessage(message);
		return loginBean;
	}
	private  Handler handlerLogin = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantsUtil.HTTP_SUCCESS:
				ToastUtil.showToast(context,context.getResources().getString(R.string.login_success));
				break;
			case ConstantsUtil.HTTP_FAILE:
				String back = (String) msg.obj;
				ToastUtil.showToast(context,back);
				break;
			default:
				break;
			}
		}
	};
}
}