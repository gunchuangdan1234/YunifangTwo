package com.bawei.yunifang.bean;

/**
 * Created by Pooh on 2016/12/9.
 */
public class SqlBean {
    private String name;
    private String imgUrl;
    private String price;
    private String num;
    private boolean flag=false;
    private int tag=0;


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public SqlBean(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public SqlBean(String name, String imgUrl, String price, String num,int tag) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.num = num;
        this.tag=tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
