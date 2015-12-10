package com.example.administrator.onezw.utils;

import com.example.administrator.onezw.BaseActivity;

import java.util.Stack;

public class ScreenManager extends BaseActivity {

		  private static Stack<BaseActivity> activityStack;
		  private static ScreenManager instance;
		  public  ScreenManager(){
		  }
		  public static ScreenManager getScreenManager(){
		   if(instance==null){
		    instance=new ScreenManager();
		   }
		   return instance;
		  }
		 //退出栈顶Activity
		  public void popActivity(BaseActivity activity){
		   if(activity!=null){
		    activity.finish();
		    activityStack.remove(activity);
		    activity=null;
		   }
		  }

		 //获得当前栈顶Activity
		  public BaseActivity currentActivity(){
		  BaseActivity activity=activityStack.lastElement();
		   return activity;
		  }

		 //将当前Activity推入栈中
		  public void pushActivity(BaseActivity activity){
		   if(activityStack==null){
		    activityStack=new Stack<BaseActivity>();
		   }
		   activityStack.add(activity);
		  }
		  //退出栈中所有Activity
		  public void popAllActivityExceptOne(Class cls){
		   while(true){
			   BaseActivity activity=currentActivity();
		    if(activity==null){
		     break;
		    }
		    if(activity.getClass().equals(cls) ){
		     break;
		    }
		    popActivity((BaseActivity)activity);
		   }
		  }
		
		// from:http://www.111cn.net/sj/android/84273.htm
}
