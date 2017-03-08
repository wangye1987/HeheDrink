package com.heheys.ec.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import com.heheys.ec.lib.utils.VerificationMD5;

/**
 * @describe:获取APP验证信息
 * @author:LiuZhouLiang
 * @time:2015-1-4下午2:39:03
 */
public class VerificationApp {
    private static String TAG = "APK信息";
    private static Context mcontext;
    private static String packageName, md5Code;

    public static String getPackageName() {
        PackageInfo info;
        String packageNames = null;
        try {
            info = mcontext.getPackageManager().getPackageInfo(
                    mcontext.getPackageName(), 0);
            // 当前应用的版本名称
            // String versionName = info.versionName;
            // 当前版本的版本号
            // int versionCode = info.versionCode;
            // 当前版本的包名
            packageNames = info.packageName;
            packageName = packageNames;
            LogUtil.d(TAG, "包名==" + packageNames);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageNames;
    }

    public static void getSign(Context context) {
        mcontext = context;
        Signature[] arrayOfSignature = getRawSignature(context,
                getPackageName());
        if ((arrayOfSignature == null) || (arrayOfSignature.length == 0)) {
            errout("signs is null");
            return;
        }
        int i = arrayOfSignature.length;
        for (int j = 0; j < i; j++)
            stdout(VerificationMD5.getMessageDigest(arrayOfSignature[j]
                    .toByteArray()));
    }

    private static Signature[] getRawSignature(Context paramContext,
                                               String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            errout("getSignature, packageName is null");
            return null;
        }
        PackageManager localPackageManager = paramContext.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(paramString,
                    PackageManager.GET_ACTIVITIES);
            if (localPackageInfo == null) {
                errout("info is null, packageName = " + paramString);
                return null;
            }
        } catch (NameNotFoundException localNameNotFoundException) {
            errout("NameNotFoundException");
            return null;
        }
        return localPackageInfo.signatures;
    }

    private static void errout(String paramString) {
        // tv_error.append(paramString + "\n");
    }

    private static void stdout(String paramString) {
        // tv_result.append(paramString + "\n");
        LogUtil.d(TAG, "签名信息" + paramString);
        md5Code = paramString;
    }


}