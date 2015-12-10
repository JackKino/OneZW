package com.example.administrator.onezw.utils;

import android.content.Context;
import android.widget.GridView;

import com.example.administrator.onezw.adapters.CateAdapter;
import com.example.administrator.onezw.models.DetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 */
public class PullDownRefreshUtils {
    private Context context;
     private CateAdapter adapter;

    public PullDownRefreshUtils(Context context, CateAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public void pullRefresh(String url, final PullToRefreshGridView cate_gridlview, final GridView gridview) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                try {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                    Type type = new TypeToken<List<DetailModel>>() {
                    }.getType();
                    List<DetailModel> data = gson.fromJson(jsonArray.toString(), type);

                    adapter = new CateAdapter(data, context);


                    gridview.setAdapter(adapter);
                    // cate_gridlview.setRefreshing();

                    adapter.notifyDataSetChanged();
                    cate_gridlview.onRefreshComplete();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

}
