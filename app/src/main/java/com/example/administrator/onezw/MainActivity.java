package com.example.administrator.onezw;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.onezw.fragment.ActivityCenterFragment;
import com.example.administrator.onezw.fragment.CategoryFragment;
import com.example.administrator.onezw.fragment.HomeFragment;
import com.example.administrator.onezw.fragment.MyFragment;
import com.example.administrator.onezw.fragment.PostManFragment;
import com.example.administrator.onezw.utils.ScreenManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment catcheFragment;
    private FragmentManager fm;
    private boolean isExit;
    private RadioGroup controller_radioGroup;
    private RadioButton controller_radiobutton_home, controller_radiobutton_category, controller_radiobutton_postman,
            controller_radiobutton_activitycenter, controller_radiobutton_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setView();

    }

    //初始化栈的管理类
    ScreenManager instance=ScreenManager.getScreenManager();

    private void init() {
        //将MainActivity加入栈
        instance.pushActivity(this);
        controller_radioGroup= (RadioGroup) this.findViewById(R.id.controller_radiogroup);
        controller_radioGroup.setOnCheckedChangeListener(this);
    }
//更新view
    private void setView() {
         fm=getSupportFragmentManager();
        //事务
        FragmentTransaction transaction=fm.beginTransaction();
        catcheFragment=new HomeFragment();
        transaction.add(R.id.container,catcheFragment,HomeFragment.TAG);
        transaction.commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction=fm.beginTransaction();
        switch (checkedId){
            case R.id.controller_radiobutton_home:
                //判断缓存的Fragment是否为null,不为null则隐藏
                if(catcheFragment!=null){
                    transaction.hide(catcheFragment);
                }
                //加载新页面,首先去栈中查找
                catcheFragment=fm.findFragmentByTag(HomeFragment.TAG);
                //查出来的fragment不为空则显示,为空则创建一个,并加到栈里
                if(catcheFragment!=null){
                    transaction.show(catcheFragment);
                }else{
                    catcheFragment=new HomeFragment();
                    transaction.add(R.id.container,catcheFragment,HomeFragment.TAG);
                }
                break;
            case R.id.controller_radiobutton_category:
                //判断缓存的Fragment是否为null,不为null则隐藏
                if(catcheFragment!=null){
                    transaction.hide(catcheFragment);
                }
                //加载新页面,首先去栈中查找
                catcheFragment=fm.findFragmentByTag(CategoryFragment.TAG);
                //查出来的fragment不为空则显示,为空则创建一个,并加到栈里
                if(catcheFragment!=null){
                    transaction.show(catcheFragment);
                }else{
                    catcheFragment=new CategoryFragment();
                    transaction.add(R.id.container,catcheFragment,CategoryFragment.TAG);
                }
                break;

            case R.id.controller_radiobutton_postman:
                //判断缓存的Fragment是否为null,不为null则隐藏
                if(catcheFragment!=null){
                    transaction.hide(catcheFragment);
                }
                //加载新页面,首先去栈中查找
                catcheFragment=fm.findFragmentByTag(PostManFragment.TAG);
                //查出来的fragment不为空则显示,为空则创建一个,并加到栈里
                if(catcheFragment!=null){
                    transaction.show(catcheFragment);
                }else{
                    catcheFragment=new PostManFragment();
                    transaction.add(R.id.container,catcheFragment,PostManFragment.TAG);
                }
                break;

            case R.id.controller_radiobutton_activitycenter:
                //判断缓存的Fragment是否为null,不为null则隐藏
                if(catcheFragment!=null){
                    transaction.hide(catcheFragment);
                }
                //加载新页面,首先去栈中查找
                catcheFragment=fm.findFragmentByTag(ActivityCenterFragment.TAG);
                //查出来的fragment不为空则显示,为空则创建一个,并加到栈里
                if(catcheFragment!=null){
                    transaction.show(catcheFragment);
                }else{
                    catcheFragment=new ActivityCenterFragment();
                    transaction.add(R.id.container,catcheFragment,ActivityCenterFragment.TAG);
                }
                break;

            case R.id.controller_radiobutton_me:
                //判断缓存的Fragment是否为null,不为null则隐藏
                if(catcheFragment!=null){
                    transaction.hide(catcheFragment);
                }
                //加载新页面,首先去栈中查找
                catcheFragment=fm.findFragmentByTag(MyFragment.TAG);
                //查出来的fragment不为空则显示,为空则创建一个,并加到栈里
                if(catcheFragment!=null){
                    transaction.show(catcheFragment);
                }else{
                    catcheFragment=new MyFragment();
                    transaction.add(R.id.container,catcheFragment,MyFragment.TAG);
                }
                break;

        }
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (!isExit) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            isExit = true;
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}
