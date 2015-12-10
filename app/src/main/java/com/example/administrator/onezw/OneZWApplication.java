package com.example.administrator.onezw;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by Administrator on 2015/12/3.
 */
public class OneZWApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
    public static Context getContext(){
        return context;
    }
}
