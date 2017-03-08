package com.heheys.ec.sqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.heheys.ec.model.dataBean.ResultBean;

/**
 * 
 * Describe:登陆信息数据库管理
 *
 * Date:2015年11月17日下午1:48:25
 *
 * Author:LZL
 *
 */
public class LoginInforSQL {
	private static BaseSQLHelper sqlHelper;
	private static LoginInforSQL loginInforSql = new LoginInforSQL();;

	public LoginInforSQL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static LoginInforSQL getInstance(Context mcontext) {

		if (loginInforSql == null) {
			loginInforSql = new LoginInforSQL();
		}
		sqlHelper = new BaseSQLHelper(mcontext, DataBaseConstant.DATABASE_NAME);
		return loginInforSql;
	}

	/**
	 * 
	 * Describe:保存用户登陆信息
	 *
	 * Date:2015年11月17日下午1:49:39
	 *
	 * Author:LZL
	 *
	 */
	public void saveLoginInfor(ResultBean loginInfor) {
		/**
		 * 在调getReadableDatabase或getWritableDatabase时，会判断指定的数据库是否存在，
		 * 不存在则调SQLiteDatabase.create创建， onCreate只在数据库第一次创建时才执行
		 */
		ContentValues cv = new ContentValues();
		cv.put("USERID", loginInfor.getId());
		cv.put("MOBILE", loginInfor.getMobile());
		cv.put("SHOPNAME", loginInfor.getShopname());
		cv.put("VERIFY_STATUS", loginInfor.getVerifystatus());
		insert(cv);

	}

	/**
	 * 
	 * Describe:插入数据库
	 *
	 * Date:2015年11月17日下午2:09:17
	 *
	 * Author:LZL
	 *
	 */
	private void insert(ContentValues initialValues) {
		if (null == initialValues) {
			throw new IllegalArgumentException(
					"initialValues cannot be null : " + initialValues);
		}

		String path = DataBaseConstant.LOGIN_INFOR_TABLE;
		boolean isHave = sqlHelper.tableIsExist(path);
		if (!TextUtils.isEmpty(path) && isHave) {
			SQLiteDatabase db = sqlHelper.getWritableDatabase();
			ContentValues values = new ContentValues(initialValues);
			long rowId = db.insert(path, BaseColumns._ID, values);

			if (rowId < 0) {
			} else {
			}

			if (db != null) {
				db.close();
			}

		}
	}

	/**
	 * 
	 * Describe:登陆信息
	 *
	 * Date:2015年11月16日下午7:33:09
	 *
	 * Author:LZL
	 *
	 */
	public ResultBean getLogInfor() {
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		Cursor cursor = db.query(DataBaseConstant.LOGIN_INFOR_TABLE, null,
				null, null, null, null, null);
		ResultBean obj = null;
		while (cursor.moveToNext()) {
			obj = new ResultBean();
			String userId = cursor.getString(cursor.getColumnIndex("USERID"));
			String mobile = cursor.getString(cursor.getColumnIndex("MOBILE"));
			int verifyStatus = cursor.getInt(cursor
					.getColumnIndex("VERIFY_STATUS"));
			String shopName = cursor.getString(cursor
					.getColumnIndex("SHOPNAME"));
			obj.setId(userId);
			obj.setMobile(mobile + "");
			obj.setShopname(shopName);
			obj.setVerifystatus(verifyStatus + "");
		}

		cursor.close();
		if (db != null) {
			db.close();
		}
		return obj;
	}
}
