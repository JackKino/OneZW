package com.example.administrator.onezw.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.onezw.NotificationActivity;
import com.example.administrator.onezw.R;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/11/20.
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = MessageReceiver.class.getSimpleName();

    private final  int NOTIFICATION_ID=1;
    private final  int REQUEST_CODE=2;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
       // Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context);
            // 必须设置的三个属性
            // 小图标
            // 标题
            // 内容
            mBuilder.setSmallIcon(R.drawable.ic_launcher);
            mBuilder.setContentTitle("通知：");
            mBuilder.setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
           // 点击通知跳转界面
           Intent intent2=new Intent(context, NotificationActivity.class);
            //必须
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent2.putExtra("data",bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 即将发生的意图，对普通意图进行封装
            PendingIntent pendingIntent=PendingIntent.getActivity(context,REQUEST_CODE,intent2,PendingIntent.FLAG_ONE_SHOT);
                    // 给Builder设置意图
            mBuilder.setContentIntent(pendingIntent);

            // 获得通知对象
            Notification notification = mBuilder.build();
            // 通知管理者
            NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 管理者发布一条通知
            manager.notify(NOTIFICATION_ID, notification);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知");


          /*  //打开自定义的Activity
            Intent i = new Intent(context, TestActivity.class);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);*/

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }




}
