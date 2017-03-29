package com.bawei.yunifang.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import com.bawei.yunifang.CategoryActivity;
import com.bawei.yunifang.DetailActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.base.BaseDate;
import com.bawei.yunifang.base.BaseFragment;
import com.bawei.yunifang.bean.CateGoryBean;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.ImageLoaderUtils;
import com.bawei.yunifang.utils.URLUtils;
import com.bawei.yunifang.view.ShowingPage;

/**
 * Created by Pooh on 2016/11/28.
 */
public class Category_Fragment extends BaseFragment implements SpringView.OnFreshListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private View category_fragment;
    private ImageView category_seach_img;
    private ImageView category_two_img1;
    private ImageView category_two_img2;
    private ImageView category_two_img3;
    private ImageView category_two_img4;
    private ImageView category_two_img5;
    private ImageView category_two_img6;
    private TextView category_fragment_effect_name1;
    private TextView category_fragment_effect_name2;
    private TextView category_fragment_effect_name3;
    private TextView category_fragment_effect_name4;
    private TextView category_fragment_effect_name5;
    private TextView category_fragment_skin_tv1;
    private TextView category_fragment_skin_tv2;
    private TextView category_fragment_skin_tv3;
    private TextView category_fragment_skin_tv4;
    private TextView category_fragment_skin_tv5;
    private TextView category_fragment_skin_tv6;
    private GridView category_lv;
    //请求出的数据
    public static String data;
    private CateGoryBean cateBean;
    private ArrayList<CateGoryBean.DataBean.GoodsBriefBean> listQiang;
    private SpringView cate_fragment_springview;

    @Override
    protected void onload() {
        //请求数据
        new BaseDate() {
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String data) {
                Category_Fragment.this.data=data;
                Message msg=new Message();
                msg.obj=data;
                handler.sendMessage(msg);
                //Toast.makeText(getActivity(),"....."+data,Toast.LENGTH_SHORT).show();
            }
        }.getDate(URLUtils.categoryUrl,"",0,BaseDate.NOMALTIME);
        //设置布局
        Category_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //解析数据
            Gson gson=new Gson();
            cateBean = gson.fromJson(data, CateGoryBean.class);
            getValue(cateBean);
        }
    };

    @Override
    protected View createSuccessView() {
        category_fragment = CommonUtils.inflate(R.layout.category_fragment);
        //初始化控件
        initCateGoryView();
//        //解析数据的方法
//        getData();
//        //控件赋值的方法
//        getValue();
        return category_fragment;
    }
    //控件赋值的方法
    private void getValue(CateGoryBean bean) {
        //设置springview
        initSpr();
        //分类的六张图片
        getCateGoryImg(bean);
        //功效的五个textview
        getCateGoryEffect(bean);
        //肤质的六个textview
        getCateGorySkin(bean);
        //明星的listviewx
        getCateGoryStar(bean);
    }

    private void initSpr() {
        cate_fragment_springview.setFooter(new DefaultFooter(getActivity()));
        cate_fragment_springview.setHeader(new DefaultFooter(getActivity()));
        //监听
        cate_fragment_springview.setListener(this);
    }

    private void getCateGoryStar(CateGoryBean cateBean) {
        //设置数据适配器
        if(cateBean.getData().getGoodsBrief().size()%2==0){
            listQiang = (ArrayList<CateGoryBean.DataBean.GoodsBriefBean>) cateBean.getData().getGoodsBrief();
        }else{
            listQiang = (ArrayList<CateGoryBean.DataBean.GoodsBriefBean>) cateBean.getData().getGoodsBrief();
            listQiang.remove(listQiang.size()-1);
        }
        category_lv.setAdapter(new CommonAdapter<CateGoryBean.DataBean.GoodsBriefBean>(getActivity(),R.layout.item_category_view,listQiang) {
            @Override
            protected void convert(ViewHolder viewHolder, CateGoryBean.DataBean.GoodsBriefBean item, int position) {
                ImageView fragment_category_img=viewHolder.getView(R.id.fragment_category_img);
                TextView fragment_category_name=viewHolder.getView(R.id.fragment_category_name);
                TextView fragment_category_jieshao=viewHolder.getView(R.id.fragment_category_jieshao);
                TextView fragment_category_price_tv1=viewHolder.getView(R.id.fragment_category_price_tv1);
                TextView fragment_category_price_tv2=viewHolder.getView(R.id.fragment_category_price_tv2);
                //赋值
                ImageLoader.getInstance().displayImage(item.getGoods_img(),fragment_category_img, ImageLoaderUtils.initOptions());
                fragment_category_name.setText(item.getGoods_name());
                fragment_category_jieshao.setText(item.getEfficacy());
                fragment_category_price_tv1.setText("￥"+item.getShop_price());
                fragment_category_price_tv2.setText("￥"+item.getMarket_price());
            }
        });
    }

    //肤质的六个textview
    private void getCateGorySkin(CateGoryBean cateBean) {
        category_fragment_skin_tv1.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(0).getCat_name()+"#");
        category_fragment_skin_tv2.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(1).getCat_name()+"#");
        category_fragment_skin_tv3.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(2).getCat_name()+"#");
        category_fragment_skin_tv4.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(3).getCat_name()+"#");
        category_fragment_skin_tv5.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(4).getCat_name()+"#");
        category_fragment_skin_tv6.setText("#"+cateBean.getData().getCategory().get(2).getChildren().get(5).getCat_name()+"#");
    }

    //功效的五个textview
    private void getCateGoryEffect(CateGoryBean bean) {

    }

    //分类的六张图片
    private void getCateGoryImg(CateGoryBean cateBean) {
        category_fragment_effect_name1.setText(" "+cateBean.getData().getCategory().get(0).getChildren().get(0).getCat_name().substring(0,2)+"\r\n"+(cateBean.getData().getCategory().get(0).getChildren().get(0).getCat_name()).substring(2,4));
        category_fragment_effect_name2.setText(" "+cateBean.getData().getCategory().get(0).getChildren().get(1).getCat_name().substring(0,2)+"\r\n"+(cateBean.getData().getCategory().get(0).getChildren().get(1).getCat_name()).substring(2,4));
        category_fragment_effect_name3.setText(" "+cateBean.getData().getCategory().get(0).getChildren().get(2).getCat_name().substring(0,2)+"\r\n"+(cateBean.getData().getCategory().get(0).getChildren().get(2).getCat_name()).substring(2,4));
        category_fragment_effect_name4.setText(" "+cateBean.getData().getCategory().get(0).getChildren().get(3).getCat_name().substring(0,2)+"\r\n"+(cateBean.getData().getCategory().get(0).getChildren().get(3).getCat_name()).substring(2,4));
        category_fragment_effect_name5.setText(" "+cateBean.getData().getCategory().get(0).getChildren().get(4).getCat_name().substring(0,2)+"\r\n"+(cateBean.getData().getCategory().get(0).getChildren().get(4).getCat_name()).substring(2,4));
    }
    //解析数据的方法
    private void getData() {
//        //解析数据
//        Gson gson=new Gson();
//        cateBean = gson.fromJson(data, CateGoryBean.class);
    }
    //初始化控件
    private void initCateGoryView() {
        //搜索的按钮
        category_seach_img = (ImageView) category_fragment.findViewById(R.id.category_seach_img);
        //分类的六张图片
        category_two_img1 = (ImageView) category_fragment.findViewById(R.id.category_two_img1);
        category_two_img2 = (ImageView) category_fragment.findViewById(R.id.category_two_img2);
        category_two_img3 = (ImageView) category_fragment.findViewById(R.id.category_two_img3);
        category_two_img4 = (ImageView) category_fragment.findViewById(R.id.category_two_img4);
        category_two_img5 = (ImageView) category_fragment.findViewById(R.id.category_two_img5);
        category_two_img6 = (ImageView) category_fragment.findViewById(R.id.category_two_img6);
        //添加监听
        category_two_img1.setOnClickListener(this);
        category_two_img2.setOnClickListener(this);
        category_two_img3.setOnClickListener(this);
        category_two_img4.setOnClickListener(this);
        category_two_img5.setOnClickListener(this);
        category_two_img6.setOnClickListener(this);
        //功效的布局控件
        category_fragment_effect_name1 = (TextView) category_fragment.findViewById(R.id.category_fragment_effect_name1);
        category_fragment_effect_name2 = (TextView) category_fragment.findViewById(R.id.category_fragment_effect_name2);
        category_fragment_effect_name3 = (TextView) category_fragment.findViewById(R.id.category_fragment_effect_name3);
        category_fragment_effect_name4 = (TextView) category_fragment.findViewById(R.id.category_fragment_effect_name4);
        category_fragment_effect_name5 = (TextView) category_fragment.findViewById(R.id.category_fragment_effect_name5);
        category_fragment_effect_name1.setOnClickListener(this);
        category_fragment_effect_name2.setOnClickListener(this);
        category_fragment_effect_name3.setOnClickListener(this);
        category_fragment_effect_name4.setOnClickListener(this);
        category_fragment_effect_name5.setOnClickListener(this);


                //肤质的布局控件
        category_fragment_skin_tv1 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv1);
        category_fragment_skin_tv2 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv2);
        category_fragment_skin_tv3 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv3);
        category_fragment_skin_tv4 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv4);
        category_fragment_skin_tv5 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv5);
        category_fragment_skin_tv6 = (TextView) category_fragment.findViewById(R.id.category_fragment_skin_tv6);
        category_fragment_skin_tv1.setOnClickListener(this);
        category_fragment_skin_tv2.setOnClickListener(this);
        category_fragment_skin_tv3.setOnClickListener(this);
        category_fragment_skin_tv4.setOnClickListener(this);
        category_fragment_skin_tv5.setOnClickListener(this);
        category_fragment_skin_tv6.setOnClickListener(this);


        //明星的listview
        category_lv = (GridView) category_fragment.findViewById(R.id.cate_fragment_gv);
        category_lv.setOnItemClickListener(this);
        //springview
        cate_fragment_springview = (SpringView) category_fragment.findViewById(R.id.cate_fragment_springview);
        cate_fragment_springview.setType(SpringView.Type.FOLLOW);
    }

    @Override
    public void onRefresh() {
        onLoad();
    }

    @Override
    public void onLoadmore() {
        onLoad();
    }
    //停止刷新和加载
    public void onLoad(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cate_fragment_springview.onFinishFreshAndLoad();
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        Intent inn=new Intent(getActivity(), CategoryActivity.class);
        String id="";
        String name="";
        switch (v.getId()) {
            case R.id.category_two_img1:
                id=cateBean.getData().getCategory().get(1).getChildren().get(0).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(0).getCat_name();
                break;
            case R.id.category_two_img2:
                id=cateBean.getData().getCategory().get(1).getChildren().get(1).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(1).getCat_name();
                break;
            case R.id.category_two_img3:
                id=cateBean.getData().getCategory().get(1).getChildren().get(2).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(2).getCat_name();
                break;
            case R.id.category_two_img4:
                id=cateBean.getData().getCategory().get(1).getChildren().get(3).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(3).getCat_name();
                break;
            case R.id.category_two_img5:
                id=cateBean.getData().getCategory().get(1).getChildren().get(4).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(4).getCat_name();
                break;
            case R.id.category_two_img6:
                id=cateBean.getData().getCategory().get(1).getChildren().get(5).getId();
                name=cateBean.getData().getCategory().get(1).getChildren().get(5).getCat_name();
                break;
            case R.id.category_fragment_effect_name1:
            case R.id.category_fragment_effect_name2:
            case R.id.category_fragment_effect_name3:
            case R.id.category_fragment_effect_name4:
            case R.id.category_fragment_effect_name5:
                id=cateBean.getData().getCategory().get(1).getChildren().get(5).getId();
                name="功效";
                break;
            case R.id.category_fragment_skin_tv1:
                id=cateBean.getData().getCategory().get(2).getChildren().get(0).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(0).getCat_name();
                break;
            case R.id.category_fragment_skin_tv2:
                id=cateBean.getData().getCategory().get(2).getChildren().get(1).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(1).getCat_name();
                break;
            case R.id.category_fragment_skin_tv3:
                id=cateBean.getData().getCategory().get(2).getChildren().get(2).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(2).getCat_name();
                break;
            case R.id.category_fragment_skin_tv4:
                id=cateBean.getData().getCategory().get(2).getChildren().get(3).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(3).getCat_name();
                break;
            case R.id.category_fragment_skin_tv5:
                id=cateBean.getData().getCategory().get(2).getChildren().get(4).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(4).getCat_name();
                break;
            case R.id.category_fragment_skin_tv6:
                id=cateBean.getData().getCategory().get(2).getChildren().get(5).getId();
                name=cateBean.getData().getCategory().get(2).getChildren().get(5).getCat_name();
                break;
        }
        inn.putExtra("name",name);
        inn.putExtra("id",id);
        startActivity(inn);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到详情界面
        Intent inn=new Intent(getActivity(), DetailActivity.class);
        inn.putExtra("id",cateBean.getData().getGoodsBrief().get(position).getId());
        getActivity().startActivity(inn);
    }
}
