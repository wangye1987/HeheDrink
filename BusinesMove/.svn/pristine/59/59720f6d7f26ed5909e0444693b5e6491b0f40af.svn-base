package com.heheys.ec.utils;

import java.util.Comparator;

import com.heheys.ec.model.dataBean.AddressModel;

/**
 * 
 * Describe:对源数据进行排序
 * 
 * Date:2015-9-22
 * 
 * Author:liuzhouliang
 */
public class PinyinComparator implements Comparator<AddressModel> {

	public int compare(AddressModel obj1, AddressModel obj2) {
		if (obj1.getSortLetters().equals("@")
				|| obj2.getSortLetters().equals("#")) {
			return -1;
		} else if (obj1.getSortLetters().equals("#")
				|| obj2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return obj1.getSortLetters().compareTo(obj2.getSortLetters());
		}
	}

}
