package com.example.administrator.onezw.MY;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;

public class MyInformation extends BaseActivity {
    private TextView barName;
    private Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        barName= (TextView) this.findViewById(R.id.cate_name);
        barName.setText("我的资料");

        home= (Button) this.findViewById(R.id.go_home);
        home.setVisibility(View.GONE);
    }


}
