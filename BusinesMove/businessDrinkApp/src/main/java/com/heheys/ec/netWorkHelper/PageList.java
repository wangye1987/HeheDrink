package com.heheys.ec.netWorkHelper;

import java.util.List;

public interface PageList<T> {

	public int getPageSize();

	public int getCount();

	public List<T> getList();
}
