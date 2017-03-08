package com.heheys.ec.controller.receiver;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.heheys.ec.R;
import com.heheys.ec.controller.activity.CouponActivity;
import com.heheys.ec.controller.activity.GroupWholeSaleActivity;
import com.heheys.ec.controller.activity.MainActivity;
import com.heheys.ec.controller.activity.MyBusCardActivity;
import com.heheys.ec.controller.activity.NewProductDetailActivity;
import com.heheys.ec.controller.activity.SalonDetailActivity;
import com.heheys.ec.controller.activity.UpdateActivity;
import com.heheys.ec.lib.utils.DeviceUtil;
import com.heheys.ec.model.dataBean.PushBaseBean;
import com.heheys.ec.utils.ConstantsUtil;
import com.heheys.ec.utils.SharedPreferencesUtil;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
//import com.heheys.ec.lib.R;
/**
 * @author wangkui
 * 
 * @param个推监听服务接受
 * 
 * **/
public class PushReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();
	public static boolean isAlert = false;
	public  AlertDialog.Builder dialogBuilder;
	public static AlertDialog alertDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                if (payload != null) {
                    String data = new String(payload);
                    Gson gson = new Gson();
                    PushBaseBean pushBaseBean = gson.fromJson(data, PushBaseBean.class);
                    if(pushBaseBean!=null){
                    	SharedPreferencesUtil.writeSharedPreferencesBoolean(context, "message", "new", true);
                    	String type = pushBaseBean.getResult().getBiztype();
                    	String Level = pushBaseBean.getResult().getLevel();//0 系统  1业务
                    if("1".equals(Level)){
                    	if(type.equals("6")){//拼单详情
                    		String id = pushBaseBean.getResult().getBiz_id();
                    		if(id !=null){
                    			Intent intent1 = new Intent(context, NewProductDetailActivity.class);
                    			intent1.putExtra(ConstantsUtil.PRODUCT_ID_KEY,id);
                    			intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    			intent1.putExtra("isPush", true);
                    			PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intent1);
                    		}
                    	}else if(type.equals("4")){//沙龙详情 1 订单列表 2 订单详情 3 沙龙列表 4 沙龙详情 5 拼单列表 6 拼单详情
                    		String salonid = pushBaseBean.getResult().getBiz_id();
                    		String uid= pushBaseBean.getResult().getUid();
                    		Intent intent2 = new Intent(context,SalonDetailActivity.class);
                    		if(salonid !=null && !salonid.equals("")){
                    			intent2.putExtra("id", Integer.parseInt(salonid));
                    		}
                    		//uid不为空是交换名片事件
                    		if(uid !=null && !uid.equals("")){
                    			intent2.putExtra("userid", uid);
                    		}else{ 
                    			intent2.putExtra("userid", "");
                    		}
                    		intent2.putExtra("isPush", true);
                    		intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    		PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intent2);
                    	}else if(type.equals("7")){//
                    		Intent intent3 = new Intent(context,MyBusCardActivity.class);
                    		intent3.putExtra("isPush", true);
                    		intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    		PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intent3);
                    	}else if(type.equals("5")){
                			Intent intentOrder = new Intent(context,GroupWholeSaleActivity.class);
                			intentOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                			intentOrder.putExtra("isPush", true);
                			PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intentOrder);
                    	}else if(type.equals("8")){//我的优惠券列表
                			Intent intentOrder = new Intent(context,CouponActivity.class);
                			intentOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                			intentOrder.putExtra("isPush", true);
                			intentOrder.putExtra("key", "scan");//查看
                			PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intentOrder);
                    	 }
                    	}else{
                    		if(isRunningForeground(context)){
                    			//如果当前app在前端 发送广播
                    			Intent intentaction = new Intent(context, UpdateActivity.class);
                    			intentaction.putExtra("pushBaseBean", pushBaseBean);
                    			intentaction.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    			context.startActivity(intentaction);
                			}else{
                				Intent intentOrder = new Intent(context,MainActivity.class);
                				intentOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                				intentOrder.putExtra("isPush", true);
                				intentOrder.putExtra("pushBaseBean", pushBaseBean);
                				intentOrder.setAction(Intent.ACTION_MAIN);
                				PandIntent(context,pushBaseBean.getResult().getTitle(),pushBaseBean.getResult().getContent(), intentOrder);
                			}
                    	}
                    }
//                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                if(!cid.equals("") && cid!=null){
                	Tag tag = new Tag();
                	tag.setName("AndroidVersion"+DeviceUtil.getVersionInfo(context));
                	Tag[] stTag = new Tag[]{tag};
                	int tagnum = PushManager.getInstance().setTag(context, stTag);
                }
                SharedPreferencesUtil.writeSharedPreferencesString(context, "loginCid", "cid", cid);//存储push cid
//                Log.d("cid=", "onReceive() action=" + cid);
//                if (GetuiSdkDemoActivity.tView != null) {
//                    GetuiSdkDemoActivity.tView.setText(cid);
//                }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
    private boolean isRunningForeground (Context context)  
    {  
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
        String currentPackageName = cn.getPackageName();  
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))  
        {  
            return true ;  
        }  
       
        return false ;  
    }  
    @SuppressLint("NewApi") private void PandIntent(Context context,String title,String content,Intent intent){
		///// 第一步：获取NotificationManager
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		///// 第二步：定义Notification
		//PendingIntent是待执行的Intent
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
		Notification notification = new Notification.Builder(context)
				.setContentTitle(title)
				.setContentText(content)
				.setSmallIcon(R.drawable.push).setContentIntent(pi)
				.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_ALL;
		/////第三步：启动通知栏，第一个参数是一个通知的唯一标识
		nm.notify(0, notification);
		
		//关闭通知
		//nm.cancel(0);
    }
}
