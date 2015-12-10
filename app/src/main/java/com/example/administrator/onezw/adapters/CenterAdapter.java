package com.example.administrator.onezw.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.onezw.R;
import com.example.administrator.onezw.models.CenterModel;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class CenterAdapter extends BaseAdapter {
    private List<CenterModel.DetailEntity> data;
    private Context context;
    private LayoutInflater inflater;

    public CenterAdapter(List<CenterModel.DetailEntity> data, Context context) {
        if (data != null) {
            this.data = data;
        } else {
            data = new ArrayList<>();
        }
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //添加数据
    public void addData(List<CenterModel.DetailEntity> data) {
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    //更新数据
    public void updateData(List<CenterModel.DetailEntity> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CenterModel.DetailEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.centeradapter_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.center_productimage);
            holder.name = (TextView) convertView.findViewById(R.id.center_productname);
            holder.price = (TextView) convertView.findViewById(R.id.center_productprice);
            holder.priceRMB = (TextView) convertView.findViewById(R.id.center_priceRMB);
            holder.limitNum = (TextView) convertView.findViewById(R.id.center_limitnum);
            holder.time= (Button) convertView.findViewById(R.id.center_time);
            holder.introduction= (TextView) convertView.findViewById(R.id.center_introduction);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();

        }
        holder.name.setText(data.get(position).getGoods_name());
        holder.price.setText(data.get(position).getIntegral()+"优币");
        holder.limitNum.setText("限量"+data.get(position).getQuantum()+"件");
        holder.priceRMB.setText("价格 "+data.get(position).getWorth());
        String surplus=data.get(position).getSurplus();
        if(surplus.equals("1")){
            String showtime=data.get(position).getShow_time();
            Date date=new Date(Long.parseLong(showtime));
            SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
            holder.time.setText(sdf.format(date)+"开抢");
            holder.time.setBackgroundResource(R.drawable.btn_shape1);
        }else if(surplus.equals("0")){
            holder.time.setText("已兑换完");
            holder.time.setBackgroundResource(R.drawable.btn_shape2);
        }

        holder.introduction.setText(data.get(position).getIntroduction());
        String url=data.get(position).getProduct_img();
        String Url=url.replace("\\/","\\");
        x.image().bind(holder.image,Url);
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        TextView priceRMB;
        TextView limitNum;
        Button time;
        TextView introduction;
    }
    public List<CenterModel.DetailEntity> getAllData(){
        return  this.data;
    }
}
