package com.example.administrator.onezw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.onezw.BaseFragment;
import com.example.administrator.onezw.MY.LoginActivityy;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.utils.SharedPreferenceUtil;

/**
 * Created by Administrator on 2015/12/3.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = MyFragment.class.getSimpleName();
    private TextView myName,myApplication;
    private Button my_ziliao;
    private View rootView;
    private String uid=null;
    public static int REQUESTCODE=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_fragment, container, false);
        myName = (TextView) rootView.findViewById(R.id.my_name);
        myApplication= (TextView) rootView.findViewById(R.id.my_applicition);
        myName.setOnClickListener(this);
        init();
        return rootView;
    }

    private void init() {
        uid= SharedPreferenceUtil.getString("uid");
        my_ziliao= (Button) rootView.findViewById(R.id.my_ziliao );
        if (uid!=null){
            myApplication.setVisibility(View.GONE);
        }
        myName.setText(SharedPreferenceUtil.getString("usernames"));
        Log.e(TAG,uid+"ffff");
        my_ziliao.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
     if(uid.length()==0) {
      switch (v.getId()) {
        case R.id.my_name:
            jumpPage(LoginActivityy.class);
            break;
        case R.id.my_ziliao:
                Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                jumpPage(LoginActivityy.class);
                break;
            }



}
    }

    public void jumpPage(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
       startActivityForResult(intent,REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LoginActivityy.RESULTCODE&&requestCode==MyFragment.REQUESTCODE) {
            String username=data.getStringExtra("usernames");
            myName.setText(username);
            myApplication.setVisibility(View.GONE);
            myName.setClickable(false);

        }
    }
}
