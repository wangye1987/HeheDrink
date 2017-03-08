package com.heheys.ec.sqliteHelper;

import java.util.ArrayList;
import java.util.List;

import com.heheys.ec.model.dataBean.BusinessCardBaseBean.BusinessCardBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * @author 作者 E-mail: wangkui
 * @version 创建时间：2015-11-23 下午6:11:12 类说明
 * @param 名片管理页面
 */
public class IdCardManager {

	static BaseSQLHelper sqlhelper;
	static IdCardManager idManager = new IdCardManager();

	public IdCardManager() {
		// TODO Auto-generated constructor stub
		super();
	}

	public static IdCardManager getIntence(Context context) {
		if (idManager == null) {
			idManager = new IdCardManager();
		}
		sqlhelper = new BaseSQLHelper(context, DataBaseConstant.DATABASE_NAME);
		return idManager;
	}

	public void insertCardInfo(List<BusinessCardBean> list) {
		SQLiteDatabase sqliteDatabase = sqlhelper.getWritableDatabase();
//		clearIdCardTableInfor(sqliteDatabase);
		if (list != null) {
			try {
				sqliteDatabase.beginTransaction();
				for (BusinessCardBean data : list) {
					ContentValues value = new ContentValues();
					value.put("NAME", data.getName());
					value.put("POSITION", data.getPosition());
					value.put("COMPANY", data.getCompany());
					value.put("MOBILE", data.getMobile());
					value.put("LANDLINE", data.getLandline());
					value.put("WEIXING", data.getWeixin());
					value.put("ADDRESS", data.getAddress());
					value.put("REMARK", data.getRemark());
					value.put("TIME", data.getTime());
					value.put("ID", data.getId());
					value.put("VERIFY_STATUS", data.getStatus());
					addData(value);
				}
				sqliteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (sqliteDatabase != null) {
					sqliteDatabase.endTransaction();
					sqliteDatabase.close();
				}
			}
		}
	}


	private void addData(ContentValues values) {
		if (values == null) {
			throw new IllegalArgumentException(
					"initialValues cannot be null : " + values);
		}

		String path = DataBaseConstant.BUSCARD_MANAGER_TABLE;
		boolean isHave = sqlhelper.tableIsExist(path);
		if (isHave && !TextUtils.isEmpty(path)) {
			SQLiteDatabase writable = sqlhelper.getWritableDatabase();
			Cursor cursor = writable.rawQuery("select * from "+DataBaseConstant.BUSCARD_MANAGER_TABLE+" where ID = ?", new String[]{(String) values.get("ID")});
			if(cursor.getCount()!=0){
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex("ID"));
				if(id.equals(values.get("ID")))
				   writable.update(DataBaseConstant.BUSCARD_MANAGER_TABLE, values, "ID=?", new String[]{id} );
				}
			}else{
				writable.insert(path, BaseColumns._ID, values);
			}
//			long rowId = writable.replaceOrThrow(path, BaseColumns._ID, values);
		}
	}

	
	/**
	 * 获取名片信息
	 *
	 */
	public List<BusinessCardBean> getOrderInfor() {
		SQLiteDatabase sqliteDatabase = sqlhelper.getReadableDatabase();
		Cursor cursor = sqliteDatabase.query(
				DataBaseConstant.BUSCARD_MANAGER_TABLE, null, null, null, null,
				null, null);
		List<BusinessCardBean> list = new ArrayList<BusinessCardBean>();
		BusinessCardBean obj = null;
		while (cursor.moveToNext()) {
			obj = new BusinessCardBean();
			String name = cursor.getString(cursor.getColumnIndex("NAME"));
			String position = cursor.getString(cursor
					.getColumnIndex("POSITION"));
			String company = cursor.getString(cursor.getColumnIndex("COMPANY"));
			String mobile = cursor.getString(cursor.getColumnIndex("MOBILE"));
			String landline = cursor.getString(cursor
					.getColumnIndex("LANDLINE"));
			String weixin = cursor.getString(cursor.getColumnIndex("WEIXING"));
			String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
			String remark = cursor.getString(cursor.getColumnIndex("REMARK"));
			String time = cursor.getString(cursor.getColumnIndex("TIME"));
			String id = cursor.getString(cursor.getColumnIndex("ID"));
			String status = cursor.getString(cursor
					.getColumnIndex("VERIFY_STATUS"));
			obj.setName(name);
			obj.setPosition(position);
			obj.setAddress(address);
			obj.setCompany(company);
			obj.setLandline(landline);
			obj.setWeixin(weixin);
			obj.setRemark(remark);
			obj.setId(id);
			obj.setMobile(mobile);
			obj.setStatus(status);
			obj.setTime(time);
			list.add(obj);
		}
		cursor.close();
		if (sqliteDatabase != null) {

			sqliteDatabase.close();

		}
		return list;
	}

	/**
	 * 
	 * Describe:清除名片表信息
	 *
	 * Date:2015年11月24日上午11:08:08
	 *
	 * Author:LZL
	 *
	 */
	protected void clearIdCardTableInfor(SQLiteDatabase sqlDatabase) {
		sqlDatabase.execSQL("delete from buscard_manager");

	}

	/**
	 * 
	 * Describe:删除表中某个名片
	 *
	 * Date:2015年11月24日上午11:32:14
	 *
	 * Author:LZL
	 *
	 */
	public boolean deleteCardInfor(String id) {
		SQLiteDatabase sqliteDatabase = sqlhelper.getWritableDatabase();
		String whereClause = "ID=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		try {
			sqliteDatabase.delete(DataBaseConstant.BUSCARD_MANAGER_TABLE,
					whereClause, whereArgs);
		} catch (SQLException e) {
			return false;
		}
		if (sqliteDatabase != null) {

			sqliteDatabase.close();

		}
		return true;
	}

	/**
	 * 
	 * Describe:更新单个名片信息
	 *
	 * Date:2015年11月24日上午11:37:43
	 *
	 * Author:LZL
	 *
	 */
	public void updateCardInfor(BusinessCardBean data) {
		SQLiteDatabase sqliteDatabase = sqlhelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("NAME", data.getName());
		values.put("POSITION", data.getPosition());
		values.put("COMPANY", data.getCompany());
		values.put("MOBILE", data.getMobile());
		values.put("LANDLINE", data.getLandline());
		values.put("WEIXING", data.getWeixin());
		values.put("ADDRESS", data.getAddress());
		values.put("REMARK", data.getRemark());
		values.put("TIME", data.getTime());
		values.put("ID", data.getId());
		values.put("VERIFY_STATUS", data.getStatus());
		String whereClause = "ID=?";
		String[] whereArgs = new String[] { String.valueOf(data.getId()) };
		sqliteDatabase.update(DataBaseConstant.BUSCARD_MANAGER_TABLE, values,
				whereClause, whereArgs);
		if (sqliteDatabase != null) {

			sqliteDatabase.close();

		}
	}

}
