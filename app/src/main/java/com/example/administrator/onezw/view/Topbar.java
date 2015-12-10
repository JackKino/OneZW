package com.example.administrator.onezw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;


/**
 * Created by Administrator on 2015/12/4.
 */
public class Topbar extends RelativeLayout{
    private Context context;
    private ImageView  left;
    private Button right;
    private TextView catename;
    public Topbar(Context context) {
        this(context, null);
    }

    public Topbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        //获取布局导入器
        LayoutInflater inflater=LayoutInflater.from(context);
        //将布局导入
        inflater.inflate(R.layout.catetopbar_item, this);

          left= (ImageView)this.findViewById(R.id.cate_back);
          right= (Button)this. findViewById(R.id.go_home);
          catename= (TextView)this. findViewById(R.id.cate_name);

       setLeftClick(true);
    }

    //左侧返回按钮
    public void setLeftClick(boolean isok){
        if(isok){
            left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) context).finish();
                }
            });
        }

    }

}
