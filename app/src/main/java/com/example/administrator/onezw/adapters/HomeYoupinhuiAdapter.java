package com.example.administrator.onezw.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.onezw.R;
import com.example.administrator.onezw.models.HomeListViewModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2015/12/4.
 */
public class HomeYoupinhuiAdapter extends BaseAdapter {
    private List<HomeListViewModel.DetailEntity> data;
    private LayoutInflater inflater;
    public HomeYoupinhuiAdapter(Context context, List<HomeListViewModel.DetailEntity> data){
        inflater = LayoutInflater.from(context);
        if (data!=null){
            this.data = data;
        }else{
            this.data = new ArrayList<HomeListViewModel.DetailEntity>();
        }
    }
    public void addData(List<HomeListViewModel.DetailEntity> data){
        if (data!=null){
        this.data.addAll(data);
        notifyDataSetChanged();
        }
    }
    public void upData(List<HomeListViewModel.DetailEntity> data){
        if(data!=null){
            this.data.clear();
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_home_listview_youpinhui,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.home_youpinhui_name);
            holder.promoPrice = (TextView) convertView.findViewById(R.id.home_youpinhui_promo_price);
            holder.originPrice = (TextView) convertView.findViewById(R.id.home_youpinhui_origin_price);
            holder.discount = (TextView) convertView.findViewById(R.id.home_youpinhui_discount);
            holder.sales = (TextView) convertView.findViewById(R.id.home_youpinhui_sales);
            holder.img = (ImageView) convertView.findViewById(R.id.home_youpinhui_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getGoods_name());
        holder.promoPrice.setText("$" + data.get(position).getPromo_price());
        holder.originPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.originPrice.setText("$"+data.get(position).getOrigin_price());
        holder.discount.setText("|"+(float)((Double.parseDouble(data.get(position).getPromo_price())/Double.parseDouble(data.get(position).getOrigin_price()))*10)+"折");
        holder.sales.setText("已售"+data.get(position).getSales()+"件");
        ImageOptions options = new ImageOptions.Builder()
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFadeIn(true)
                .setCrop(true)
                .build();
        x.image().bind(holder.img,data.get(position).getImg(),options);
        return convertView;
    }
    private static class ViewHolder{
        ImageView img;
        TextView name,promoPrice,originPrice,discount,sales;
    }

}
