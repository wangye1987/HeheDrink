package com.heheys.ec.exception;

import android.content.Context;

import com.heheys.ec.lib.utils.SharedPreferencesUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常工具类
 *
 * @author Administrator
 */

public class ExceptionUtil {

    public static final int EXCEPTION_STATE = 0;
    public static final int EXCEPTION_REQUEST_CODE = 0;
    public static final int RESTART_PENDING_TIME = 1000;

    /**
     * Describe:获取异常信息
     * <p/>
     * Date:2015-6-24
     * <p/>
     * Author:liuzhouliang
     */
    public static String getErrorInfo(Throwable t) {
        if (null == t) {
            return "";
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        if (null != pw) {
            t.printStackTrace(pw);
            pw.close();
        }

        String error = "";
        if (null != writer) {
            error = writer.toString();
        }
        return error;
    }

    /**
     * Describe:获取上次保存的异常信息
     * <p/>
     * Date:2015-6-24
     * <p/>
     * Author:liuzhouliang
     */
    public static String getSavedErrorInfo(Context context) {
        String lastErrorInfo = SharedPreferencesUtil
                .getSharedPreferencesString(context,
                        SharedPreferencesUtil.APP_EXCEPTION,
                        SharedPreferencesUtil.APP_EXCEPTION_KEY, "");
        return lastErrorInfo;
    }

    /**
     * Describe:保存异常信息
     * <p/>
     * Date:2015-6-24
     * <p/>
     * Author:liuzhouliang
     */
    public static void saveErrorInfo(Context context, String content) {
        if (null == context) {
            return;
        }

        SharedPreferencesUtil.writeSharedPreferencesString(context,
                SharedPreferencesUtil.APP_EXCEPTION,
                SharedPreferencesUtil.APP_EXCEPTION_KEY, content);
    }

    /**
     * Describe:清空异常信息
     * <p/>
     * Date:2015-6-24
     * <p/>
     * Author:liuzhouliang
     */
    public static void clearSavedErrorInfo(Context context) {
        if (null == context) {
            return;
        }
        SharedPreferencesUtil.ClearSharedPreferences(context,
                SharedPreferencesUtil.APP_EXCEPTION);
    }

}
