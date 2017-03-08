package com.heheys.ec.sqliteHelper;

import com.heheys.ec.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * Describe:数据库操作基础类
 *
 * Date:2015年11月16日下午4:07:06
 *
 * Author:LZL
 *
 */
public class BaseSQLHelper extends SQLiteOpenHelper {

	protected static final String TAG = BaseSQLHelper.class.getSimpleName();
	protected Resources mRes;
	protected Context context;
	public BaseSQLHelper(Context context, String name) {
		super(context, name, null, DataBaseConstant.DATABASE_VERSION);
		mRes = context.getResources();
		this.context = context;
	}

	/**
	 * 当数据库被首次创建时执行该方法，一般将创建表等初始化操作在该方法中执行
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		dropTable(db);
		createTable(db);
	}

	/**
	 * 当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}

	/**
	 * 
	 * Describe:创建表
	 *
	 * Date:2015年11月16日下午4:47:41
	 *
	 * Author:LZL
	 *
	 */
	private void createTable(SQLiteDatabase db) {
		String[] createTableSQL = mRes.getStringArray(R.array.create_table);
		for (String sql : createTableSQL) {
			db.execSQL(sql);
		}
	}

	/**
	 * 
	 * Describe:删除表
	 *
	 * Date:2015年11月16日下午4:47:56
	 *
	 * Author:LZL
	 *
	 */
	private void dropTable(SQLiteDatabase db) {
		String[] dropTableSQL = mRes.getStringArray(R.array.drop_table);
		for (String sql : dropTableSQL) {
			db.execSQL(sql);
		}
	}

	/**
	 * 
	 * Describe:判断表是否存在
	 *
	 * Date:2015年11月17日上午11:19:43
	 *
	 * Author:LZL
	 *
	 */
	public boolean tableIsExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();
			String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public void ClearDB(){
		context.deleteDatabase(DataBaseConstant.DATABASE_NAME);
	}
}
