package com.example.administrator.onezw.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.HomeNextAdapter;
import com.example.administrator.onezw.models.HomeListViewModel;
import com.example.administrator.onezw.view.NoScrollGridView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2015/12/5.
 */
@ContentView(R.layout.activity_home_top)
public class TOPActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    private String cid;
    private HomeNextAdapter mAdapter;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private NoScrollGridView mGridView;
    private List<HomeListViewModel.DetailEntity> cacheEntity;
    private String url;
    private CheckBox mCheckbox;
    private LinearLayout mLayout;
    private Button backBtn,quanbuBtn,nvzhuangBtn,nanzhuangBtn,muyingBtn,xiebaoBtn,jiajuBtn,meishiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        cacheEntity = new ArrayList<HomeListViewModel.DetailEntity>();
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.home_top_scrollview);
        mGridView = (NoScrollGridView) findViewById(R.id.home_top_gridview);
        mCheckbox = (CheckBox) findViewById(R.id.home_top_checkbox);
        mLayout = (LinearLayout) findViewById(R.id.home_top_layout);
        quanbuBtn = (Button) findViewById(R.id.home_top_quanbu);
        mCheckbox.setOnCheckedChangeListener(this);
        mAdapter = new HomeNextAdapter(this,null);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(2);
        mGridView.setOnItemClickListener(this);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        url = "http://appapi2.1zw.com/index/topten.html?page=1&cid=";
        lodingGridview(0);
    }

    @Event(value = {R.id.top_back,R.id.home_top_quanbu,R.id.home_top_nvzhuang,R.id.home_top_nanzhuang,
            R.id.home_top_muying,R.id.home_top_xiebao,R.id.home_top_jiaju,R.id.home_top_meishi})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.top_back:
                this.finish();
                break;
            case R.id.home_top_quanbu:
                cid = null;
                break;
            case R.id.home_top_nvzhuang:
                cid = "1";
                break;
            case R.id.home_top_nanzhuang:
                cid = "2";
                break;
            case R.id.home_top_muying:
                cid = "3";
                break;
            case R.id.home_top_xiebao:
                cid = "4";
                break;
            case R.id.home_top_jiaju:
                cid = "5";
                break;
            case R.id.home_top_meishi:
                cid = "6";
                break;
        }
                lodingGridview(1);
    }

    // TODO: 每日优选的listview
    public void lodingGridview(final int code){
        RequestParams params = new RequestParams(url+cid);
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

    // TODO: 下拉刷新回调
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        lodingGridview(1);
        refreshView.onRefreshComplete();
    }
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
    }
    // TODO: listview item点击回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listviewIntent = new Intent(this, HomeListViewItemActivity.class);
        listviewIntent.putExtra("url", cacheEntity.get(position).getUrl());
        startActivity(listviewIntent);
    }

    // TODO: 更多/收起
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
}
