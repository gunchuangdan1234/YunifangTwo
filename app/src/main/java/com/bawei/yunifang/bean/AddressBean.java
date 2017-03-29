package com.bawei.yunifang.bean;

/**
 * Created by Pooh on 2016/12/16.
 */
public class AddressBean {
    private String name;
    private String phone;
    private String Address;
    private String DetailAddress;
    private boolean flag;
    private String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public AddressBean(String name, String phone, String address, String detailAddress, boolean flag,String tag) {
        this.name = name;
        this.phone = phone;
        Address = address;
        DetailAddress = detailAddress;
        this.flag = flag;
        this.tag=tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDetailAddress() {
        return DetailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        DetailAddress = detailAddress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
