package com.example.administrator.onezw.adapters;

import android.content.Context;
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
public class HomeTOPAdapter extends BaseAdapter {
    private List<HomeListViewModel.DetailEntity> data;
    private LayoutInflater inflater;
    public HomeTOPAdapter(Context context, List<HomeListViewModel.DetailEntity> data){
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
            convertView = inflater.inflate(R.layout.item_home_gridview_top,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.home_top_name);
            holder.promoPrice = (TextView) convertView.findViewById(R.id.home_top_promo_price);
            holder.sales = (TextView) convertView.findViewById(R.id.home_top_sales);
            holder.img = (ImageView) convertView.findViewById(R.id.home_top_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getGoods_name());
        holder.promoPrice.setText("$" + data.get(position).getPromo_price());
        holder.sales.setText(data.get(position).getSales()+"人已买");
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
        TextView name,promoPrice,sales;
    }
}
