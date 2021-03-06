/*
package com.heheys.ec.controller.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.heheys.ec.R;
import com.heheys.ec.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

*/
/**
 * @author wangkui
 *
 *//*

public class FindFragment extends BaseFragment implements OnClickListener {

	//跟界面
	private View view;
	private RadioGroup radioGroup;
	private FrameLayout frameLayout;
	private RadioButton drinkinfo;
	private RadioButton salon;
	private Context mActivity;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private TextView line_two;
	private TextView line_one;
	//酒讯列表fragment
	private DrinkInfoFragment drinkInfoFragment;
	//沙龙列表fragment
	private SalonListFragment salonListFragment;
	
	public FindFragment(){
		
	}
	@Override
	protected boolean isShowLeftIcon() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected View setContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.findfragment, container, true);
		initView();
//		initDrinkFragment();
		initSalonFragment();
		return view;
	}

	
	*/
/*
	 * 初始化界面组件
	 * *//*

	private void initView() {
		mActivity = getActivity();
		fragmentManager = getActivity().getSupportFragmentManager();
		frameLayout = (FrameLayout) view.findViewById(R.id.find_fragment_content);
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		salon = (RadioButton) view.findViewById(R.id.radio_drink_salon);
		drinkinfo = (RadioButton) view.findViewById(R.id.radio_drink_info);
		line_one = (TextView) view.findViewById(R.id.line_one);
		line_two = (TextView) view.findViewById(R.id.line_two);
		radioGroup.setOnCheckedChangeListener(changeClick);
		hideBack();//隐藏返回键
	}


	 void visible(){
		if(line_two.getVisibility()==View.VISIBLE)
			line_two.setVisibility(View.INVISIBLE);
		else
			line_two.setVisibility(View.VISIBLE);
		if(line_one.getVisibility()==View.INVISIBLE)
			line_two.setVisibility(View.VISIBLE);
		else
			line_two.setVisibility(View.INVISIBLE);
	}
	
	
	//显示喝喝云商酒讯界面
//	@SuppressLint("CommitTransaction")
//	public void initDrinkFragment(){
//		fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.out_alpha, 0);
//		HindFragment(fragmentTransaction);
//		if(drinkInfoFragment == null){
//			drinkInfoFragment = new DrinkInfoFragment(radioGroup);
//		}
//
//		if(drinkInfoFragment.isAdded()){
//			fragmentTransaction.show(drinkInfoFragment).commitAllowingStateLoss();
//		}else{
//			fragmentTransaction.add(R.id.find_fragment_content, drinkInfoFragment).commitAllowingStateLoss();
//		}
//	}
	
	//显示沙龙界面
//	@SuppressLint("CommitTransaction")
//	public void initSalonFragment(){
//		fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.out_alpha, 0);
//		HindFragment(fragmentTransaction);
//		if(salonListFragment == null){
//			salonListFragment = new SalonListFragment(radioGroup);
//		}
//
//		if(salonListFragment.isAdded()){
//			fragmentTransaction.show(salonListFragment).commitAllowingStateLoss();
//		}else{
//			fragmentTransaction.add(R.id.find_fragment_content, salonListFragment).commitAllowingStateLoss();
//		}
//	}
	
	//隐藏已经存在的fragment
	public void HindFragment(FragmentTransaction fragmentTransaction){
		if(drinkInfoFragment != null)
			fragmentTransaction.hide(drinkInfoFragment);
		
		if(salonListFragment != null)
			fragmentTransaction.hide(salonListFragment);
		
	}
	@Override
	protected void getNetData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewListener() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean hasTitle() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean hasTitleIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean hasDownIcon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void reloadCallback() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String setTitleName() {
		// TODO Auto-generated method stub
		return "发现";
	}

	@Override
	protected String setRightText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int setLeftImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setMiddleImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setRightImageResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	public  OnCheckedChangeListener changeClick = new  OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radio_drink_info:
				line_one.setVisibility(View.VISIBLE);
				line_two.setVisibility(View.INVISIBLE);
				initDrinkFragment();
				break;
			case R.id.radio_drink_salon:
//				line_one.setVisibility(View.INVISIBLE);
//				line_two.setVisibility(View.VISIBLE);
//				initSalonFragment();
				break;
			default:
				break;
			}
		}
	};
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("PG_DIS_SLN"); //统计页面，"MainScreen"为页面名称，可自定义
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("PG_DIS_SLN"); 
	}
}
*/
