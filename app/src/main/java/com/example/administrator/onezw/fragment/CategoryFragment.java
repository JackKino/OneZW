package com.example.administrator.onezw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.onezw.BaseFragment;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.category.CateActivity;

/**
 * Created by Administrator on 2015/12/3.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG=CategoryFragment.class.getSimpleName();

   private  Button cate_dress,cate_foods,cate_manswear,cate_mothernal,cate_shoesbag,cate_home;
    private  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.category_fragment,container,false);
        init();
        return rootView;
    }

    private void init() {
        cate_dress= (Button) rootView.findViewById(R.id.cate_dress);
        cate_foods= (Button) rootView.findViewById(R.id.cate_foods);
        cate_home= (Button) rootView.findViewById(R.id.cate_home);
        cate_manswear= (Button) rootView.findViewById(R.id.cate_manswear);
        cate_mothernal= (Button) rootView.findViewById(R.id.cate_mothernal);
        cate_shoesbag= (Button) rootView.findViewById(R.id.cate_shoesbag);

        cate_dress.setOnClickListener(this);
        cate_foods.setOnClickListener(this);
        cate_home.setOnClickListener(this);
        cate_manswear.setOnClickListener(this);
        cate_mothernal.setOnClickListener(this);
        cate_shoesbag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cate_dress:

                jumpPage(CateActivity.class,1,"时尚女装");
                break;
            case R.id.cate_manswear:

                jumpPage(CateActivity.class,2,"潮流男装");
                break;
            case R.id.cate_mothernal:

                jumpPage(CateActivity.class,3,"母婴儿童");
                break;
            case R.id.cate_shoesbag:

                jumpPage(CateActivity.class,4,"鞋包配饰");
                break;
            case R.id.cate_home:

                jumpPage(CateActivity.class,5,"家居生活");
                break;
            case R.id.cate_foods:

                jumpPage(CateActivity.class,6,"美食特产");
                break;

        }
    }

    public void jumpPage(Class cls,int cid,String cateName){
        Intent intent=new Intent(getActivity(),cls);
        Bundle bundle=new Bundle();
        bundle.putString("cateName",cateName);
        bundle.putInt("cid", cid);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
