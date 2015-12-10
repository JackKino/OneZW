package com.example.administrator.onezw.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.onezw.R;

public class GoodsDetail extends AppCompatActivity {
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        webview = (WebView) this.findViewById(R.id.goodsDetail_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        // webview.loadUrl(url);
        //设置是否可以调用网页中javascript代码
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

       /* webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());//.希望点击链接继续在当前browser中响应，必须覆盖 WebViewClient对象。
        webview.getSettings().setJavaScriptEnabled(true);*/

      /* // String content = getUnicodeContent() ;

        webview.getSettings().setDefaultTextEncodingName("UTF -8") ;

       // wv.loadData(content, "text/html", "UTF-8") ;
        webview.loadDataWithBaseURL(url,url,"text/html","UTF-8",null);*/
        webview.loadUrl(url);
    }


}
