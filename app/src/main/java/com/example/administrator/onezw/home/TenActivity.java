package com.example.administrator.onezw.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.HomeListViewAdapter;
import com.example.administrator.onezw.adapters.HomeYoupinhuiAdapter;
import com.example.administrator.onezw.models.HomeListViewModel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by abc on 2015/12/5.
 */
public class TenActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    //listview的page
    private int mPage = 1;
    private HomeListViewAdapter mAdapter;
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;
    private List<HomeListViewModel.DetailEntity> entity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ten);
        initView();
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.home_ten_listview);
        mListView = mPullToRefreshListView.getRefreshableView();
        mAdapter = new HomeListViewAdapter(this,null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mPullToRefreshListView.setOnRefreshListener(this);
        lodingListview();
    }

    public void lodingListview(){
        String url = "http://appapi2.1zw.com/index/ten.html?page="+mPage;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                entity = new Gson().fromJson(result, HomeListViewModel.class).getDetail();
                mAdapter.upData(entity);

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
        lodingListview();
        refreshView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
    }
    // TODO: listview item点击回调
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listviewIntent = new Intent(this, HomeListViewItemActivity.class);
        listviewIntent.putExtra("url",entity.get(position-1).getUrl());
        startActivity(listviewIntent);
    }
}
