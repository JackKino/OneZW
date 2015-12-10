package com.example.administrator.onezw.MY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.utils.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivityy extends BaseActivity implements View.OnClickListener {
    private TextView barName;
    private Button home;
    private EditText username, password;
    private Button login;
    private String url = "http://appapi2.1zw.com/public/login.html?sign=wcomI01Iz5VhMUfBw2WmbSJPfQjpX9tSiGMWBdXp4CpCvAmF2I8zCURSrn%2F3CWBmMb1iznPuxXw6AXK1TotQYVgKhefP0jJsDSR%2B5LehhG0%3D&device_token&platform=Android&version=33%20%3E%3E%20appapiappapi2.1zw.com/public/login.html?sign=wcomI01Iz5VhMUfBw2WmbSJPfQjpX9tSiGMWBdXp4CpCvAmF2I8zCURSrn/3CWBmMb1iznPuxXw6AXK1TotQYVgKhefP0jJsDSR+5LehhG0=&device_token&platform=Android&version=33";
    private String user;
    private String pwd;
    private String usernames;
    public static  int RESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activityy);
        barName = (TextView) this.findViewById(R.id.cate_name);
        barName.setText("登  录");

        home = (Button) this.findViewById(R.id.go_home);
        home.setVisibility(View.GONE);

        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        login = (Button) this.findViewById(R.id.login);


        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        user = username.getText().toString();
        pwd = password.getText().toString();
        Log.e("TAG", "user:" + user + "pwd:" + pwd);
        if (user.equals("13760308942") && pwd.equals("PAYEASY0824")) {
           /* Intent intent=new Intent(this, MyFragment.class);
            startActivity(intent);*/
            //this.finish();
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jo= (JSONObject) new JSONObject(result).get("return");
                        String uid = jo.getString("uid");
                        usernames = jo.getString("username");
                        String kind = jo.getString("kind");
                        String score = jo.getString("score");

                        SharedPreferenceUtil.putString("uid", uid);
                        SharedPreferenceUtil.putString("usernames", usernames);
                        SharedPreferenceUtil.putString("kind", kind);
                        SharedPreferenceUtil.putString("score", score);
                        Log.e("Tag", uid + username + kind + score);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }







                   // LoginActivityy.();
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            Toast.makeText(LoginActivityy.this, "用户名/密码错误", Toast.LENGTH_SHORT).show();

        }
        Intent intent = new Intent();
        intent.putExtra("username", usernames);
        setResult(RESULTCODE, intent);
        this.finish();

    }
}
