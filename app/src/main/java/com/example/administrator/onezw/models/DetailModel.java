package com.example.administrator.onezw.models;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/12/4.
 */
@Table(name="goods")
public class DetailModel {

    /**
     * goods_name : 冬装连帽卫衣韩版外套
     * goods_id : 59529
     * product_id : 524070272683
     * is_new : 0
     * item_type : 0
     * sales : 24
     * promo_price : 58.00
     * p_price : 156.00
     * app_price : 0.00
     * is_stock : 0
     * is_gai : 1
     * origin_price : 156.00
     * img : https://img.alicdn.com/imgextra/i4/2439850672/TB2HgfDhVXXXXbIXXXXXXXXXXXX_!!2439850672.jpg
     * url : http://appapi2.1zw.com/index/show/goods_id/59529.html
     * start_time : 1449190800
     * end_time : 1449622800
     */
   @Column(name="goods_name")
    private String goods_name;
    @Column(name="goods_id",isId = true)
    private String goods_id;
    @Column(name="item_type")
    private String item_type;
    @Column(name="sales")
    private String sales;
    @Column(name="promo_price")
    private String promo_price;
    @Column(name="img")
    private String img;
    @Column(name="url")
    private String url;

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public void setPromo_price(String promo_price) {
        this.promo_price = promo_price;
    }



    public void setImg(String img) {
        this.img = img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public String getSales() {
        return sales;
    }

    public String getPromo_price() {
        return promo_price;
    }



    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }
}
