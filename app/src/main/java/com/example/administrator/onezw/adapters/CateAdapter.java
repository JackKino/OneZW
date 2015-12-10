package com.example.administrator.onezw.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.onezw.R;
import com.example.administrator.onezw.models.DetailModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 */
public class CateAdapter extends BaseAdapter {
    private List<DetailModel>  data;
    private Context context;
    private LayoutInflater inflater;

    public CateAdapter(List<DetailModel> data, Context context) {
       // if(data!=null) {
            this.data = data;
      //  }else{
           // data=new ArrayList<>();
       // }
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    //添加数据
    public void addData(List<DetailModel> data){
        //if(data!=null) {
            this.data.addAll(data);
       // }
        notifyDataSetChanged();
    }
     //更新数据
    public void updateData(List<DetailModel> data){
        if(data!=null) {
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
    public DetailModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.goods_item,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.goods_image);
            holder.goodsName= (TextView) convertView.findViewById(R.id.goods_name);
            holder.type= (ImageView) convertView.findViewById(R.id.goods_type);
            holder.goodsPrice= (TextView) convertView.findViewById(R.id.goods_price);
            holder.sales= (TextView) convertView.findViewById(R.id.goods_sales);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.goodsName.setText(data.get(position).getGoods_name());
        String type=data.get(position).getItem_type();
        if(type.equals("0")){
            holder.type.setImageResource(R.mipmap.taobao);
        }else if(type.equals("1")){
            holder.type.setImageResource(R.mipmap.tmall);
        }

        holder.goodsPrice.setText("¥"+data.get(position).getPromo_price());
        holder.sales.setText(data.get(position).getSales()+"人已购买");
        String img=data.get(position).getImg();
        String url=img.replace("\\/", "\\");
        ImageOptions options=new  ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.ic_launcher)
                .setFailureDrawableId(R.drawable.ic_launcher)
                .setFadeIn(true)
                .build();
        x.image().bind(holder.image,url,options);

        return convertView;
    }

    public class ViewHolder{
        ImageView image;
        ImageView type;
        TextView goodsName;
        TextView goodsPrice;
        TextView sales;
    }

    public List<DetailModel> getAllData(){
        return  this.data;
    }
}
