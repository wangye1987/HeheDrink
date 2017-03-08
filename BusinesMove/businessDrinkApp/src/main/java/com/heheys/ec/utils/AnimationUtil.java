package com.heheys.ec.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.heheys.ec.R;

/**
 * @author wangkui
 *
 * 
 * 晃动动画
 */
public class AnimationUtil {
	Context mContext;
	static AnimationUtil animationUtil;
	 AnimationUtil(Context mContext){
		this.mContext = mContext;
	}
	
	public static AnimationUtil getInstance(Context mContext){
		if(animationUtil == null){
			animationUtil = new AnimationUtil(mContext);
		}
		return animationUtil;
	}
	public  void ShakeView(View view){
	    Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);//加载动画资源文件
	    view.startAnimation(shake); //给组件播放动画效果
		}
	
	//下面view 向上移动动画 上面view 也向上动画 
	public  void TitleEnterView(View one,View two,View three){
		Animation exit = AnimationUtils.loadAnimation(mContext, R.anim.push_up_out);//加载动画资源文件
		one.startAnimation(exit); //给组件播放动画效果
		two.startAnimation(exit); //给组件播放动画效果
		three.startAnimation(exit); //给组件播放动画效果
		
//	    Animation exter = AnimationUtils.loadAnimation(mContext, R.anim.push_up_in);//加载动画资源文件
//	    four.startAnimation(exter); //给组件播放动画效果
		}
	//下面view 向上移动动画 上面view 也向上动画 
	public  void buttomEnterView(View EnterView,View ExitView){
		Animation exit = AnimationUtils.loadAnimation(mContext, R.anim.push_up_out);//加载动画资源文件
		ExitView.startAnimation(exit); //给组件播放动画效果
		Animation exter = AnimationUtils.loadAnimation(mContext, R.anim.push_up_in);//加载动画资源文件
		EnterView.startAnimation(exter); //给组件播放动画效果
	}
	//下面view 向下移动动画 上面view 也向下动画 
	public  void TopEnterView(View EnterView,View ExitView){
		Animation exit = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_out);//加载动画资源文件
		ExitView.startAnimation(exit); //给组件播放动画效果
		Animation enter = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in);//加载动画资源文件
		EnterView.startAnimation(enter); //给组件播放动画效果
	}
}
