package com.example.administrator.onezw.utils;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.onezw.adapters.CateAdapter;
import com.example.administrator.onezw.models.DetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 */
public class DataBaseUtils {
    private Context context;
    private CateAdapter adapter;
     private DbManager.DaoConfig daoConfig=new DbManager.DaoConfig()
             .setDbName("goods")
             .setDbDir(new File("/sdcard"))
             .setDbVersion(1)
             .setAllowTransaction(true)
             .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                 @Override
                 public void onUpgrade(DbManager dbManager, int i, int i1) {

                 }
             });

    public DataBaseUtils(Context context, CateAdapter adapter) {
        this.context = context;
        this.adapter=adapter;
    }

    public void setData(String url,final GridView gridView,final PullToRefreshGridView cate_gridlview ){
           DbManager select= x.getDb(daoConfig);
        try {
            List<DetailModel> data=select.selector(DetailModel.class).limit(10).findAll();
            if(data!=null){
                  adapter=new CateAdapter(data,context);
                 gridView.setAdapter(adapter);
                 new PullDownRefreshUtils(context,adapter).pullRefresh(url, cate_gridlview, gridView);
            }else{
                RequestParams params=new RequestParams(url);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        try {
                            JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                            Type type = new TypeToken<List<DetailModel>>() {
                            }.getType();
                            List<DetailModel> data = gson.fromJson(jsonArray.toString(), type);
                            Log.e("TAG", "DATA2");
                           adapter = new CateAdapter(data,context);
                            Log.e("TAG", "DATA3");
                            gridView.setAdapter(adapter);


                            cate_gridlview.onRefreshComplete();

                            //数据存入数据库
                            DbManager insert=x.getDb(daoConfig);
                            int size=data.size();
                            for(DetailModel model:data){
                                try {
                                    insert.save(model);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(context, "数据存储成功", Toast.LENGTH_SHORT).show();
                            }

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
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


}
