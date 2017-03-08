package com.heheys.ec.view; 

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.application.MyApplication;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.lib.activityManager.StartActivityUtil;
import com.heheys.ec.lib.base.BaseListAdapter;
import com.heheys.ec.lib.base.ViewHolderUtil;
import com.heheys.ec.lib.view.ViewUtil;
import com.heheys.ec.model.dataBean.PaymentBean.PayList;
import com.heheys.ec.utils.ActivityManagerUtil;
import com.heheys.ec.utils.ConstantsUtil;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-11-4 上午10:49:39 
 * 类说明 
 * @param 订单无效商品弹出框
 */
public class OrderDialog implements OnClickListener{

	private Context mcontext;
	private MyDialog builders;
	private View layout;
	private RefreshAdapter refreshCall;
	private List<PayList> listSave;
	public MyDialog CreateDialog(Activity mcontext,List<PayList> listSave,List<PayList> list,RefreshAdapter refreshCall){
		this.mcontext = mcontext;
		this.refreshCall = refreshCall;
		this.listSave = listSave;
		LayoutInflater inflater = (LayoutInflater)
				mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         layout = inflater.inflate(R.layout.order_dialog,null);
         builders = new MyDialog(mcontext,0,0,layout,R.style.dialog);
		 initView(list);
		 Show();
		return builders;
	}
	
	public class MyDialog extends Dialog {
	     
	    private static final int default_width = 160; //默认宽度
	    private static final int default_height = 120;//默认高度
	    public MyDialog(Context context, int width, int height, View layout, int style) {
	        super(context, style);
	        setContentView(layout);
	        Window window = getWindow();
	        WindowManager.LayoutParams params = window.getAttributes();
	        params.gravity = Gravity.CENTER;
	        float widthdp = window.getWindowManager().getDefaultDisplay().getWidth();
	        float heightdp = window.getWindowManager().getDefaultDisplay().getHeight();
	        params.width  = (int) (widthdp-ViewUtil.dip2px(mcontext, 34));
	        window.setAttributes(params);
	    }
	}

	//动态生成界面view
	private void initView(List<PayList> data) {
		ListView lv_order_dialog = (ListView) layout.findViewById(R.id.lv_order_dialog);
		Button bu_cancle = (Button) layout.findViewById(R.id.bu_cancle);
		Button bu_remove = (Button) layout.findViewById(R.id.bu_remove);
		DiaglogAdapter adpter = new DiaglogAdapter(data, mcontext); 
		lv_order_dialog.setAdapter(adpter);
		bu_cancle.setOnClickListener(this);
		bu_remove.setOnClickListener(this);
		if(listSave.size()==0){
			bu_remove.setText("返回购物车");
		}
	}
	
	
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.bu_cancle:
		Demiss();
		break;
	case R.id.bu_remove:
		if(listSave.size()>0){
			refreshCall.setData(listSave);
		}else{
			int num = ActivityManagerUtil.getActivityManager().getActivityNum(
					MainActivity.class);
			Intent intent = new Intent(mcontext, MainActivity.class);
			intent.putExtra(ConstantsUtil.MAIN_TAB_TYPE_KEY,
					ConstantsUtil.MAIN_TAB_SHOP_CAR);
			intent.putExtra(ConstantsUtil.SHOPPING_CART_IS_SHOW_BACK_KEY,
					ConstantsUtil.SHOPPING_CART_SHOW_BACK);
			if (num > 1) {
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			StartActivityUtil.startActivity((Activity) mcontext, intent);
		}
		Demiss();
		break;
	default:
		break;
	}
	}
	
	/*
	 * 显示当前对话框
	 * */
	public void Show(){
		if(builders!=null){
			 builders.show();
		}
	}
	/*
	 * 显示当前对话框
	 * */
	public void Demiss(){
		if(builders!=null){
			builders.dismiss();
		}
	}
	
	//移除后刷新adapter
	 public interface RefreshAdapter{
		void setData(List<PayList> data);
	}
	public class DiaglogAdapter extends BaseListAdapter<PayList>{

		private ImageView iv_goods;
		private TextView tv_name;
		private TextView tv_num;

		private List<PayList> listSave;
		public DiaglogAdapter(List<PayList> data, Context context) {
			super(data, context);
			// TODO Auto-generated constructor stub
		
		}

		@Override
		public View bindView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			PayList bean = dataList.get(position);
			if (convertView == null) {
				convertView = baseInflater.inflate(
						R.layout.alert_item, parent, false);
			}
			iv_goods = (ImageView) ViewHolderUtil.getItemView(convertView, R.id.iv_goods);
			tv_name = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_name);
			tv_num = (TextView) ViewHolderUtil.getItemView(convertView, R.id.tv_num);
			MyApplication.imageLoader.displayImage(bean.getPic(), iv_goods,
					MyApplication.options_banner);
			tv_name.setText(bean.getName());
			tv_num.setText(bean.getNum());
			return convertView;
		}
	}

}
