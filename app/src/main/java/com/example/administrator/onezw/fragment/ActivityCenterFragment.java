package com.example.administrator.onezw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.onezw.BaseFragment;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.activityCenter.Rules;
import com.example.administrator.onezw.adapters.CenterAdapter;
import com.example.administrator.onezw.models.CenterModel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class ActivityCenterFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG = ActivityCenterFragment.class.getSimpleName();
    private ListView center_listview;
    private PullToRefreshScrollView center_scrollview;
    private String url1 = "http://appapi2.1zw.com/index/scoremall.html?device_token=3df433d8524f3997de20e9d6b212e837&page=";
    private int page = 1;
    private String url2 = "&platform=Android&version=33";
    private CenterAdapter adapter;
    private TextView center_rule;
    private ScrollView mscrollview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activitycenter_fragment, container, false);
        center_listview = (ListView) rootView.findViewById(R.id.center_listview);
        center_rule = (TextView) rootView.findViewById(R.id.center_rule);
        center_scrollview = (PullToRefreshScrollView) rootView.findViewById(R.id.center_scrollview);
        mscrollview=center_scrollview.getRefreshableView();

        center_rule.setOnClickListener(this);
        //center_listview.setSelected(true);
        center_listview.setOnItemClickListener(ActivityCenterFragment.this);


        center_scrollview.setMode(PullToRefreshBase.Mode.BOTH);
        center_scrollview.setOnRefreshListener(this);

        String http = url1 + page + url2;
        RequestParams params = new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CenterModel model = gson.fromJson(result, CenterModel.class);
                List<CenterModel.DetailEntity> data = model.getDetail();

                adapter = new CenterAdapter(data, getActivity());
                center_listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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


        return rootView;
    }

    //下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        String http = url1 + page + url2;
        RequestParams params = new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CenterModel model = gson.fromJson(result, CenterModel.class);
                List<CenterModel.DetailEntity> data = model.getDetail();

                adapter = new CenterAdapter(data, getActivity());
                center_listview.setAdapter(adapter);
                center_scrollview.onRefreshComplete();
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
    }

    //上拉加载
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        String http = url1 + page + url2;
        RequestParams params = new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CenterModel model = gson.fromJson(result, CenterModel.class);
                List<CenterModel.DetailEntity> data = model.getDetail();

                // adapter=new CenterAdapter(data,getActivity());
                adapter.addData(data);

                adapter.notifyDataSetChanged();
                center_scrollview.onRefreshComplete();

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
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Rules.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        List<CenterModel.DetailEntity> allData = adapter.getAllData();
//        Log.e("TAG", "haha");
//        String url = allData.get(position).getDetail_url();
//        String Url = url.replace("\\/", "\\");
//        Log.e("TAG", Url);
//        Intent intent2 = new Intent(getActivity(), GoodsDetail.class);
//        intent2.putExtra("url", Url);
//        startActivity(intent2);
        Toast.makeText(getActivity(), "---->", Toast.LENGTH_SHORT).show();

    }
}
