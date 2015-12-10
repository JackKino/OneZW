package com.example.administrator.onezw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.administrator.onezw.BaseFragment;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.HomeNextAdapter;
import com.example.administrator.onezw.adapters.PostManAdapter;
import com.example.administrator.onezw.home.HomeListViewItemActivity;
import com.example.administrator.onezw.models.HomeListViewModel;
import com.example.administrator.onezw.view.NoScrollGridView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
@ContentView(R.layout.postman_fragment)
public class PostManFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener,PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    public static final String TAG=PostManFragment.class.getSimpleName();
    private String cid;
    private int mPage = 1;
    private PostManAdapter mAdapter;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private NoScrollGridView mGridView;
    private List<HomeListViewModel.DetailEntity> cacheEntity;
    private String url;
    private CheckBox mCheckbox;
    private LinearLayout mLayout;
    private Button quanbuBtn,nvzhuangBtn,nanzhuangBtn,muyingBtn,xiebaoBtn,jiajuBtn,meishiBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = x.view().inject(this,inflater,container);
        mPullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.postman_scrollview);
        mGridView = (NoScrollGridView) rootView.findViewById(R.id.postman_gridview);
        mCheckbox = (CheckBox) rootView.findViewById(R.id.postman_checkbox);
        mLayout = (LinearLayout) rootView.findViewById(R.id.postman_layout);
        quanbuBtn = (Button) rootView.findViewById(R.id.postman_quanbu);
        initView();
        return rootView;
    }

    private void initView() {
        cacheEntity = new ArrayList<HomeListViewModel.DetailEntity>();

        mCheckbox.setOnCheckedChangeListener(this);
        mAdapter = new PostManAdapter(getContext(),null);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(2);
        mGridView.setOnItemClickListener(this);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.setFocusable(true);
        mPullToRefreshScrollView.setFocusableInTouchMode(true);
        mPullToRefreshScrollView.requestFocus();
        url = "http://appapi2.1zw.com/index/jkj.html?page=";
        lodingGridview(0);
    }
    @Event(value = {R.id.postman_quanbu,R.id.postman_nvzhuang,R.id.postman_nanzhuang,
            R.id.postman_muying,R.id.postman_xiebao,R.id.postman_jiaju,R.id.postman_meishi})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.postman_quanbu:
                cid = null;
                break;
            case R.id.postman_nvzhuang:
                cid = "1";
                break;
            case R.id.postman_nanzhuang:
                cid = "2";
                break;
            case R.id.postman_muying:
                cid = "3";
                break;
            case R.id.postman_xiebao:
                cid = "4";
                break;
            case R.id.postman_jiaju:
                cid = "5";
                break;
            case R.id.postman_meishi:
                cid = "6";
                break;
        }
        lodingGridview(1);
    }

    public void lodingGridview(final int code){
        RequestParams params = new RequestParams(url+mPage+"&cid="+cid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<HomeListViewModel.DetailEntity> entity = new Gson().fromJson(result, HomeListViewModel.class).getDetail();
                switch (code) {
                    case 0:
                        mAdapter.addData(entity);
                        if (entity != null) {
                            cacheEntity.addAll(entity);
                        }
                        break;
                    case 1:
                        mAdapter.upData(entity);
                        if (entity != null) {
                            cacheEntity.clear();
                            cacheEntity = entity;
                        }
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            mLayout.setVisibility(View.VISIBLE);
            mCheckbox.setText("收起");
        }else{

            mLayout.setVisibility(View.GONE);
            mCheckbox.setText("更多");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listviewIntent = new Intent(getContext(), HomeListViewItemActivity.class);
        listviewIntent.putExtra("url", cacheEntity.get(position).getUrl());
        startActivity(listviewIntent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        lodingGridview(1);
        refreshView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage++;
        lodingGridview(0);
        refreshView.onRefreshComplete();
    }
}
