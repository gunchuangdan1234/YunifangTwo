package com.bawei.yunifang.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

import com.bawei.yunifang.LoginActivity;
import com.bawei.yunifang.MyOrderActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.SettingActivity;
import com.bawei.yunifang.adapter.mineLvAdapter;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.base.BaseFragment;
import com.bawei.yunifang.bean.mineItemBean;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.mineList;
import com.bawei.yunifang.view.ShowingPage;

/**
 * Created by Pooh on 2016/11/28.
 */
public class Mine_Fragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView mine_img_title;
    private ImageView mine_deng_img;
    private ImageView mine_img_setting;
    private GridView mine_gv;
    private ListView mine_lv;
    private ArrayList<mineItemBean> list_mineItemBean;
    private ScrollView ssss;
    private ImageView mine_img_setting1;
    public static ImageView mine_img_xiaomogu;
    private RadioGroup mine_rg;


    @Override
    protected void onload() {
        //请求网络
        //设置布局
        settingFram();
    }
    @Override
    protected View createSuccessView() {
        //初始化布局
        View vv=initView();
        return vv;
    }




    private View initView() {
        View vv=CommonUtils.inflate(R.layout.mine1_fragment);
        mine_img_setting1 = (ImageView) vv.findViewById(R.id.mine_img_setting);
        //给设置按钮添加监听
        mine_img_setting1.setOnClickListener(this);
        mine_img_title = (ImageView) vv.findViewById(R.id.mine_img_title);
        mine_img_xiaomogu = (ImageView) vv.findViewById(R.id.mine_img_xiaomogu);
        mine_deng_img = (ImageView) vv.findViewById(R.id.mine_deng_img);
        mine_deng_img.setOnClickListener(this);
        mine_img_setting1 = (ImageView) vv.findViewById(R.id.mine_img_setting);
        mine_lv = (ListView) vv.findViewById(R.id.mine_lv);
        mine_rg = (RadioGroup) vv.findViewById(R.id.mine_rg);
        //设置监听
        mine_rg.setOnCheckedChangeListener(this);
        mine_lv.setOnItemClickListener(this);
        //初始化集合
        initArrayList();
        //添加适配器
        addAdapter();
        mine_lv.setFocusable(false);
        return vv;
    }

    private void addAdapter() {
        mine_lv.setAdapter(new mineLvAdapter<mineItemBean>(getActivity(),list_mineItemBean));
    }

    private void initArrayList() {
        list_mineItemBean = (ArrayList<mineItemBean>) mineList.list_mineItemBean;
        list_mineItemBean.add(new mineItemBean(R.mipmap.my_order_icon,"我的订单",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.my_invite_gift_icon,"邀请送礼",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.guilian3,"刷脸测尺寸",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.my_coupon_icon,"我的现金券",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.my_lottery_icon,"我的抽奖单",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.my_collection_icon,"我收藏的商品",""));
        list_mineItemBean.add(new mineItemBean(R.mipmap.personal_center_contact_service_icon,"联系客服",""));
    }

    private void settingFram() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                    Mine_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //条目的监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_img_setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.mine_deng_img:
                startActivity(new Intent(getActivity(), LoginActivity.class).putExtra("show", MyApplication.showLoginImg));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rb= (RadioButton) group.getChildAt(i);
            if(rb.getId()==checkedId){
                //跳转到我的订单界面
                Intent inn=new Intent(getActivity(), MyOrderActivity.class);
                inn.putExtra("id",i);
                startActivity(inn);
            }
        }
    }
}
