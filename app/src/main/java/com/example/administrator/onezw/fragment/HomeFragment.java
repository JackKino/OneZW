package com.example.administrator.onezw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.administrator.onezw.BaseFragment;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.HomeListViewAdapter;
import com.example.administrator.onezw.home.ExclusiveActivity;
import com.example.administrator.onezw.home.HomeImgActivity;
import com.example.administrator.onezw.home.HomeListViewItemActivity;
import com.example.administrator.onezw.home.NextActivity;
import com.example.administrator.onezw.home.TOPActivity;
import com.example.administrator.onezw.home.TenActivity;
import com.example.administrator.onezw.home.YoupinhuiActivity;
import com.example.administrator.onezw.models.HomeListViewModel;
import com.example.administrator.onezw.view.NoScrollListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class HomeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, View.OnClickListener, AdapterView.OnItemClickListener {
   public static final String TAG=HomeFragment.class.getSimpleName();
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ScrollView mScrollView;
    private ImageView mImg;
    private NoScrollListView mListView;
    private HomeListViewAdapter mAdapter;
    private ImageView exclusiveImg,tenImg,nextImg;
    private List<HomeListViewModel.DetailEntity> entity;
    private List<HomeListViewModel.DetailEntity> cacheEntity;
    //ͷ��ͼƬ��תurl
    private String imgTourl;
    //listview��page
    private int mPage = 1;
    private Button topButton,youpinghuiButton,taoyoupinButton,guanghaohuoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.home_fragment,container,false);
        mPullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.home_scrollview);
        //scrollview第一个view获取焦点
        mPullToRefreshScrollView.setFocusable(true);
        mPullToRefreshScrollView.setFocusableInTouchMode(true);
        mPullToRefreshScrollView.requestFocus();

        mImg = (ImageView) rootView.findViewById(R.id.home_img);
        mListView = (NoScrollListView ) rootView.findViewById(R.id.home_listview);
        topButton = (Button) rootView.findViewById(R.id.home_button_top10);
        youpinghuiButton = (Button) rootView.findViewById(R.id.home_button_youpinhui);
        taoyoupinButton = (Button) rootView.findViewById(R.id.home_button_taoyoupin);
        guanghaohuoButton = (Button) rootView.findViewById(R.id.home_button_guanghaohuo);
        exclusiveImg = (ImageView) rootView.findViewById(R.id.home_exclusive);
        tenImg = (ImageView) rootView.findViewById(R.id.home_ten);
        nextImg = (ImageView) rootView.findViewById(R.id.home_next);
        initView();
        return rootView;
    }

    private void initView() {
        exclusiveImg.setOnClickListener(this);
        nextImg.setOnClickListener(this);
        tenImg.setOnClickListener(this);
        topButton.setOnClickListener(this);
        youpinghuiButton.setOnClickListener(this);
        taoyoupinButton.setOnClickListener(this);
        guanghaohuoButton.setOnClickListener(this);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mScrollView = mPullToRefreshScrollView.getRefreshableView();
        mPullToRefreshScrollView.setOnRefreshListener(this);
        cacheEntity = new ArrayList<HomeListViewModel.DetailEntity>();
        // TODO: ������ҳͷ��ͼƬ
        RequestParams imgParams = new RequestParams("http://appapi2.1zw.com/public/youbanner.html");
        x.http().get(imgParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray banner = object.getJSONArray("banner");
                    String imgUrl = (String) banner.getJSONObject(0).get("img");
                    imgTourl = (String) banner.getJSONObject(0).get("url");
                    ImageOptions options = new ImageOptions.Builder()
                            .setFailureDrawableId(R.mipmap.ic_launcher)
                            .setLoadingDrawableId(R.mipmap.ic_launcher)
                            .setCrop(true)
                            .setFadeIn(true)
                            .build();
                    x.image().bind(mImg, imgUrl, options);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG,ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
        mImg.setOnClickListener(this);
        mAdapter = new HomeListViewAdapter(getActivity(),null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        lodingListview(0);
//        mScrollView.smoothScrollTo(0, 0);
    }
        // TODO: ÿ����ѡ��listview
    public void lodingListview(final int code){
        String url = "http://appapi2.1zw.com/index/index.html?page="+mPage;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                entity = new Gson().fromJson(result, HomeListViewModel.class).getDetail();
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

    // TODO: ����ˢ�»ص�
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPage = 1;
        lodingListview(1);
        refreshView.onRefreshComplete();
    }

    // TODO: �������ػص�
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        mPage++;
        lodingListview(0);
        refreshView.onRefreshComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_img:
                Intent intent = new Intent(getContext(), HomeImgActivity.class);
                intent.putExtra("url",imgTourl);
                startActivity(intent);
                break;
            case R.id.home_button_top10:
                Intent topIntent = new Intent(getContext(), TOPActivity.class);
                startActivity(topIntent);
                break;
            case R.id.home_button_youpinhui:
                Intent intent2 = new Intent(getContext(), YoupinhuiActivity.class);
                startActivity(intent2);
                break;
            case R.id.home_button_taoyoupin:

                break;
            case R.id.home_button_guanghaohuo:

                break;
            case R.id.home_exclusive:
                Intent exclusiveIntent = new Intent(getContext(), ExclusiveActivity.class);
                startActivity(exclusiveIntent);
                break;
            case R.id.home_ten:
                Intent tenIntent = new Intent(getContext(), TenActivity .class);
                startActivity(tenIntent);
                break;
            case R.id.home_next:
                Intent nextIntent = new Intent(getContext(), NextActivity.class);
                startActivity(nextIntent);
                break;
        }
    }

    // TODO: listview item����ص�
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent listviewIntent = new Intent(getContext(), HomeListViewItemActivity.class);
        listviewIntent.putExtra("url",cacheEntity.get(position).getUrl());
        startActivity(listviewIntent);
    }
}
