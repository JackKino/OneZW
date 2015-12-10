package com.example.administrator.onezw.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;

import java.util.Timer;
import java.util.TimerTask;

/** 首页的头图片 点击跳转页
 * Created by abc on 2015/12/4.
 */
public class HomeImgActivity extends BaseActivity{
    private WebView mWebView;
    private boolean isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_img);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.home_img_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            // 重写Url加载,每次重新加载
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        mWebView.loadUrl(url);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //当按下的是返回键，并且WebView可回退
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
//            // 调用WebView回退
//            mWebView.goBack();
//            return true;
//        }
//
//        if (!isExit) {
//            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            isExit = true;
//            new Timer().schedule(new TimerTask() {
//
//                @Override
//                public void run() {
//                    isExit = false;
//                }
//            }, 2000);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
