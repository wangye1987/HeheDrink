package com.heheys.ec.sqliteHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.heheys.ec.model.dataBean.CityListBean.CityDataList.CityData;
import com.heheys.ec.utils.LogUtil;

/**
 * Describe:城市列表数据库管理
 *
 * Date:2015年11月16日下午4:55:29
 *
 * Author:LZL
 *
 */
public class HomeCitySQL {
	private static final String TAG = HomeCitySQL.class.getName();
	private static BaseSQLHelper sqlHelper;
	private static HomeCitySQL homeCitySql = new HomeCitySQL();;

	public HomeCitySQL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static HomeCitySQL getInstance(Context mcontext) {

		if (homeCitySql == null) {
			homeCitySql = new HomeCitySQL();
		}
		sqlHelper = new BaseSQLHelper(mcontext, DataBaseConstant.DATABASE_NAME);
		return homeCitySql;
	}

	/**
	 * 
	 * Describe:保存城市信息
	 *
	 * Date:2015年11月17日上午11:21:41
	 *
	 * Author:LZL
	 *
	 */
	public void saveCityInfor(List<CityData> cityList) {
		/**
		 * 在调getReadableDatabase或getWritableDatabase时，会判断指定的数据库是否存在，
		 * 不存在则调SQLiteDatabase.create创建， onCreate只在数据库第一次创建时才执行
		 */
		SQLiteDatabase db = sqlHelper.getWritableDatabase();
		clearHotCityTableInfor(db);
		try {
			LogUtil.d(TAG, "事务开始成功");
			db.beginTransaction();
			for (CityData cityInfor : cityList) {
				ContentValues cv = new ContentValues();
				cv.put("CITY_ID", Integer.parseInt(cityInfor.getId()));
				cv.put("NAME", cityInfor.getName());
				cv.put("HOT", cityInfor.getHot());
				insert(cv);

			}
			db.setTransactionSuccessful();
			LogUtil.d(TAG, "事务执行成功");
		} finally {
			db.endTransaction();
			LogUtil.d(TAG, "事务执行结束");
		}
		if (db != null) {

			db.close();

		}

	}

	/**
	 * 
	 * Describe:清除表中的数据
	 *
	 * Date:2015年11月24日下午2:23:08
	 *
	 * Author:LZL
	 *
	 */
	protected void clearHotCityTableInfor(SQLiteDatabase sqlDatabase) {
		sqlDatabase.execSQL("delete from home_city");

	}

	/**
	 * 
	 * Describe:数据库插入城市信息
	 *
	 * Date:2015年11月17日上午11:23:46
	 *
	 * Author:LZL
	 *
	 */
	private void insert(ContentValues initialValues) {
		if (null == initialValues) {
			throw new IllegalArgumentException(
					"initialValues cannot be null : " + initialValues);
		}

		String path = DataBaseConstant.HOME_CITY_TABLE;

		if (!TextUtils.isEmpty(path) && sqlHelper.tableIsExist(path)) {
			SQLiteDatabase db = sqlHelper.getWritableDatabase();
			ContentValues values = new ContentValues(initialValues);
			long rowId = db.insert(path, BaseColumns._ID, values);

			if (rowId < 0) {
			} else {
			}

		}
	}

	/**
	 * 
	 * Describe:获取城市列表
	 *
	 * Date:2015年11月16日下午7:33:09
	 *
	 * Author:LZL
	 *
	 */
	public List<CityData> getCityList() {
		List<CityData> cityList = new ArrayList<CityData>();
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		Cursor cursor = db.query(DataBaseConstant.HOME_CITY_TABLE, null, null,
				null, null, null, null);
		while (cursor.moveToNext()) {
			CityData obj = new CityData();
			String cityId = cursor.getString(cursor.getColumnIndex("CITY_ID"));
			String cityName = cursor.getString(cursor.getColumnIndex("NAME"));
			int hot = cursor.getInt(cursor.getColumnIndex("HOT"));
			obj.setName(cityName);
			obj.setId(cityId);
			obj.setHot(hot);
			cityList.add(obj);
		}

		cursor.close();
		if (db != null) {

			db.close();

		}

		return cityList;

	}

}
