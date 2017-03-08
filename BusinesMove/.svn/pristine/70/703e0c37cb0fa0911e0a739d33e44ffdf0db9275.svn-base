package com.heheys.ec.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseActivity;
import com.heheys.ec.controller.listener.OnBackListener;

/**
 * 
 * 
 * @describe:Fragment管理
 * 
 * @author:LiuZhouLiang
 * 
 * @time:2014-11-4上午10:22:00
 * 
 */

public class FragmentUtil {
	/**
	 * 显示Fragment容器
	 */
//	public static int MAIN_ACTIVITY = R.id.main_content_parent;

	/**
	 * fragment跳转
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragment(FragmentActivity context,
			Fragment fragment, int id, boolean isAddOrReplace,Bundle bundle) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.addToBackStack(fragment.getClass().getName());

		if (isAddOrReplace) {
			fragment.setArguments(bundle);
			fragmentTransaction
					.add(id, fragment, fragment.getClass().getName())
					.commitAllowingStateLoss();
		} else {
			fragment.setArguments(bundle);
			fragmentTransaction.replace(id, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

	}
	/**
	 * 普通Fragment跳转
	 * 
	 * */
	public static void Replace(FragmentActivity context,int id){
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * Fragment跳转
	 * 
	 * @param context
	 * @param fragment
	 */
	public static void stratFragment(FragmentActivity context,
			Fragment fragment, boolean isAddOrReplace,int container) {
		stratFragment(context, fragment, container, isAddOrReplace,null);
	}

	/**
	 * 从右向左划入切换fragment
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragmentFromRight(FragmentActivity context,
			Fragment fragment, int id, BaseActivity baseActivity,
			OnBackListener onBackListener, boolean isAddOrReplace) {
		if (null != baseActivity.onBackListenerList) {
			baseActivity.onBackListenerList.add(onBackListener);
		}

		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction
				.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out,
						R.anim.push_left_in, R.anim.push_left_out);

		fragmentTransaction.addToBackStack(fragment.getClass().getName());
		if (isAddOrReplace) {
			fragmentTransaction
					.add(id, fragment, fragment.getClass().getName())
					.commitAllowingStateLoss();
		} else {
			fragmentTransaction.replace(id, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

	}

	/**
	 * 从右向左划入切换fragment
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragmentFromRight(FragmentActivity context,
			Fragment fragment, BaseActivity baseActivity,
			OnBackListener onBackListener, boolean isAddOrReplace,int container) {

		if (null != baseActivity.onBackListenerList) {
			baseActivity.onBackListenerList.add(onBackListener);
		}

		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction
				.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out,
						R.anim.push_left_in, R.anim.push_left_out);

		fragmentTransaction.addToBackStack(fragment.getClass().getName());

		if (isAddOrReplace) {
			fragmentTransaction.add(container, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		} else {
			fragmentTransaction.replace(container, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

	}

	/**
	 * 从右向左划入切换fragment
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragmentFromRightNotBack(FragmentActivity context,
			Fragment fragment, BaseActivity baseActivity,
			OnBackListener onBackListener, boolean isAddOrReplace,int container) {
		if (null != baseActivity.onBackListenerList) {
			baseActivity.onBackListenerList.add(onBackListener);
		}

		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction
				.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out,
						R.anim.push_left_in, R.anim.push_left_out);

		if (isAddOrReplace) {
			fragmentTransaction.add(container, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		} else {
			fragmentTransaction.replace(container, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

		fragmentManager.popBackStackImmediate(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);

	}

	/**
	 * 从下向上划入切换fragment
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragmentFromBottom(FragmentActivity context,
			Fragment fragment, int id, boolean isAddOrReplace) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		fragmentTransaction.setCustomAnimations(R.anim.push_bottom_in,
				R.anim.push_bottom_out, R.anim.push_bottom_in,
				R.anim.push_bottom_out);

		fragmentTransaction.addToBackStack(fragment.getClass().getName());
		if (isAddOrReplace) {
			fragmentTransaction
					.add(id, fragment, fragment.getClass().getName())
					.commitAllowingStateLoss();
		} else {
			fragmentTransaction.replace(id, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

	}

	/**
	 * Fragment跳转后不可返回
	 * 
	 * @param context
	 * @param fragment
	 * @param id
	 */
	public static void stratFragmentNotBack(FragmentActivity context,
			Fragment fragment, int id, boolean isAddOrReplace) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if (isAddOrReplace) {
			fragmentTransaction
					.add(id, fragment, fragment.getClass().getName())
					.commitAllowingStateLoss();
		} else {
			fragmentTransaction.replace(id, fragment,
					fragment.getClass().getName()).commitAllowingStateLoss();
		}

		fragmentManager.popBackStackImmediate(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	/**
	 * Fragment出栈
	 * 
	 * @param context
	 */
	public static void popBackStack(BaseActivity context) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		fragmentManager.popBackStackImmediate();
	}

	@SuppressWarnings("unused")
	private static void logBackStack(final String Tag, final String method,
			final FragmentManager fragmentManager) {
		fragmentManager
				.addOnBackStackChangedListener(new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {
						for (int i = 0; i < fragmentManager
								.getBackStackEntryCount(); i++) {
							BackStackEntry entry = fragmentManager
									.getBackStackEntryAt(i);
							if (null != entry) {
							}
						}
					}
				});

	}
}
