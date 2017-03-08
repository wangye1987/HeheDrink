package com.heheys.ec.controller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.heheys.ec.service.HeartService;
import com.heheys.ec.utils.Utils;

/**
 * Created by wangkui on 2016/12/9.
 */

public class AlarmReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Utils.isProessRunning(context,Utils.process_name)) {
            Intent i = new Intent(context, HeartService.class);
            context.startService(i);
        }
        Log.i("Tag", "重新开启服务");
    }
}