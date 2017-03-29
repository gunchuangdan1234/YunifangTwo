package com.bawei.yunifang.bean;

/**
 * Created by Pooh on 2016/11/30.
 */
public class mineItemBean {
    public int img;
    public String name;
    public String message;

    public mineItemBean(int img, String name, String message) {
        this.img = img;
        this.name = name;
        this.message = message;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
