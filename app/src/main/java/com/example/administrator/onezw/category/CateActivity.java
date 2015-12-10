package com.example.administrator.onezw.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.onezw.BaseActivity;
import com.example.administrator.onezw.MainActivity;
import com.example.administrator.onezw.R;
import com.example.administrator.onezw.adapters.CateAdapter;
import com.example.administrator.onezw.models.DetailModel;
import com.example.administrator.onezw.utils.ScreenManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
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

public class CateActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    private TextView cateName;
    private Button goHome;
    private PullToRefreshGridView cate_gridlview;
    private GridView gridView;
    private String url01 = "http://appapi2.1zw.com/index/cat.html?cid=";
    private int cid;
    private String url02 = "&page=";
    private int page = 1;
    private String url03 = "&platform=Android&version=33";

    //private List<DetailModel> data;
    private CateAdapter adapter;

    private Gson gson = new Gson();
    //数据库缓存
    private DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("goods")
            .setDbDir(new File("/sdcard"))
            .setDbVersion(1)
            .setAllowTransaction(true)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager dbManager, int i, int i1) {

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate);
        init();
        getData();
    }


    //获取网络数据
    private void getData() {
        String http = url01 + cid + url02 + page + url03;
        //data=new ArrayList<>();
        //new DataBaseUtils(this,adapter).setData(http, gridView, cate_gridlview);
        DbManager select = x.getDb(daoConfig);
        try {
            List<DetailModel> data = select.selector(DetailModel.class).limit(10).findAll();
            if (data != null) {
                adapter = new CateAdapter(data, this);
                gridView.setAdapter(adapter);
                //进入页面是自动刷新，延迟1秒
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        cate_gridlview.setRefreshing();
                    }
                }, 1000);
               //autoFresh();
               // new PullDownRefreshUtils(this,adapter).pullRefresh(http, cate_gridlview, gridView);
            } else {
                RequestParams params = new RequestParams(http);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        try {
                            JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                            Type type = new TypeToken<List<DetailModel>>() {
                            }.getType();
                            List<DetailModel>  data = gson.fromJson(jsonArray.toString(), type);

                            adapter = new CateAdapter(data, CateActivity.this);

                            gridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            cate_gridlview.onRefreshComplete();

                            //数据存入数据库
                            DbManager insert = x.getDb(daoConfig);
                            int size = data.size();
                            for (DetailModel model : data) {
                                try {
                                    insert.save(model);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(CateActivity.this, "数据存储成功", Toast.LENGTH_SHORT).show();
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



    //初始化栈的管理类
    ScreenManager instance = ScreenManager.getScreenManager();

    private void init() {

        cateName = (TextView) this.findViewById(R.id.cate_name);
        goHome = (Button) this.findViewById(R.id.go_home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cid = bundle.getInt("cid");
        String Name = bundle.getString("cateName");

        //设置topbar的名称
        cateName.setText(Name);
        goHome.setOnClickListener(this);

        cate_gridlview = (PullToRefreshGridView) this.findViewById(R.id.cate_gridlview);
        cate_gridlview.setMode(PullToRefreshBase.Mode.BOTH);
        gridView = cate_gridlview.getRefreshableView();
        gridView.setOnItemClickListener(this);
        cate_gridlview.setOnRefreshListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                BaseActivity ac = instance.currentActivity();
                instance.popActivity(ac);

                break;
        }
    }

    //下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        String http = url01 + cid + url02 + page + url03;
       //new PullDownRefreshUtils(this,adapter).pullRefresh(http, cate_gridlview, gridView);


        RequestParams params=new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                    Type type = new TypeToken<List<DetailModel>>() {
                    }.getType();
                    List<DetailModel> data = gson.fromJson(jsonArray.toString(), type);
                    adapter=new CateAdapter(data,CateActivity.this);
                    gridView.setAdapter(adapter);
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

    //上拉加载
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        String http = url01 + cid + url02 + page + url03;
       // new PullUpRefreshUtils(this).pullRefresh(http, adapter, cate_gridlview);
     RequestParams params=new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.e("TAG", "data1");
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                    Log.e("TAG", "data2");
                    Type type = new TypeToken<List<DetailModel>>() {
                    }.getType();
                    Log.e("TAG", "data3");
                    List<DetailModel> data = gson.fromJson(jsonArray.toString(), type);
                    Log.e("TAG", "data4");
                    adapter.addData(data);
                    adapter.notifyDataSetChanged();
                    Log.e("TAG", "data5");
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

    //gridview的item点击监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        List<DetailModel> allData = adapter.getAllData();
        String urls = allData.get(position).getUrl();
        String url=urls.replace("\\/", "\\");
        //Log.e("TAG",url);
        Intent intent = new Intent(this, GoodsDetail.class);
        intent.putExtra("url", url);
        startActivity(intent);



    }

    //进入页面是自动刷新
    public void autoFresh(){
        String http = url01 + cid + url02 + page + url03;
        //new PullDownRefreshUtils(this,adapter).pullRefresh(http, cate_gridlview, gridView);


        RequestParams params=new RequestParams(http);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("detail");
                    Type type = new TypeToken<List<DetailModel>>() {
                    }.getType();
                    List<DetailModel> data = gson.fromJson(jsonArray.toString(), type);
                    adapter=new CateAdapter(data,CateActivity.this);
                    gridView.setAdapter(adapter);
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
