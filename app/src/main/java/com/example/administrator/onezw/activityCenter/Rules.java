package com.example.administrator.onezw.activityCenter;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;

public class Rules extends BaseActivity {
    private WebView center_rule_webview;
    private TextView barName;
    private Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        center_rule_webview= (WebView) this.findViewById(R.id.center_rule_webview);
        center_rule_webview.loadUrl("http://www.1zw.com/jifen/rules/app.html");


        barName= (TextView) this.findViewById(R.id.cate_name);
        barName.setText("规则/协议");

        home= (Button) this.findViewById(R.id.go_home);
        home.setVisibility(View.GONE);

    }


}
