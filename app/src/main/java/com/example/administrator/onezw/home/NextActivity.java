package com.example.administrator.onezw.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.HomeNextAdapter;
import com.example.administrator.onezw.adapters.HomeYoupinhuiAdapter;
import com.example.administrator.onezw.models.HomeListViewModel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2015/12/5.
 */
public class NextActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    //listview的page
    private int mPage = 1;
    private HomeNextAdapter mAdapter;
    private PullToRefreshGridView mPullToRefreshGridView;
    private GridView mGridView;
    private List<HomeListViewModel.DetailEntity> cacheEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_next);
        initView();
    }

    private void initView() {
        cacheEntity = new ArrayList<HomeListViewModel.DetailEntity>();
        mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.home_next_gridview);
        mGridView = mPullToRefreshGridView.getRefreshableView();
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new HomeNextAdapter(this,null);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(2);
        mGridView.setOnItemClickListener(this);
        mPullToRefreshGridView.setOnRefreshListener(this);
        lodingGridview(0);
    }

    // TODO: 每日优选的listview
    public void lodingGridview(final int code){
        String url = "http://appapi2.1zw.com/index/tmall.html?page="+mPage;
        RequestParams params = new RequestParams(url);
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
        mPage = 1;
        lodingGridview(1);
        refreshView.onRefreshComplete();
    }

    // TODO: 上拉加载回调
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage++;
        lodingGridview(0);
        refreshView.onRefreshComplete();
    }
    // TODO: listview item点击回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listviewIntent = new Intent(this, HomeListViewItemActivity.class);
        listviewIntent.putExtra("url", cacheEntity.get(position).getUrl());
        startActivity(listviewIntent);
    }
}
