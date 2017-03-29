package com.bawei.yunifang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import com.bawei.yunifang.DetailActivity;
import com.bawei.yunifang.HomeWebViewActivity;
import com.bawei.yunifang.LoginActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.ZhuanTiActivity;
import com.bawei.yunifang.application.MyApplication;
import com.bawei.yunifang.base.CommonAdapter;
import com.bawei.yunifang.base.MyViewHolder;
import com.bawei.yunifang.bean.HomeBean;
import com.bawei.yunifang.fragment.Home_Fragment;
import com.bawei.yunifang.interface1.OnItemClickListener;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.ImageLoaderUtils;
import com.bawei.yunifang.view.MyRoolViewPager;

/**
 * Created by Pooh on 2016/12/1.
 */
public class homeAdapter<T> extends CommonAdapter<T> implements AdapterView.OnItemClickListener, View.OnClickListener {
    private boolean flag = true;
    private MyViewHolder viewHolder;
    private ImageView home_item_position0_img;
    private LinearLayout home_item_position0_lin;
    private MyRoolViewPager home_fragment_vp;
    private GridView home_fregment_gv;
    private LinearLayout home_fragment_benzhourexiao_lin;
    private LinearLayout home_fragment_youhui_lin;
    private HomeBean homeBean;
    private ArrayList<String> listImg;
    public int[] arrDot = new int[]{R.mipmap.login_btn_bg_033_03, R.mipmap.page_indicator_unfocused};
    private ArrayList<ImageView> listDot;
    private LinearLayout home_fragment_vp_lin;
    private ImageView home_item_position1_img;
    private LinearLayout home_item_position1_lin;
    private ImageView fragment_position3_img;
    private TextView fragment_position3_name;
    private TextView fragment_position3_jieshao;
    private TextView fragment_position3_price_tv1;
    private TextView fragment_position3_price_tv2;
    private ImageView fragment_two_position3_img;
    private TextView fragment_two_position3_name;
    private TextView fragment_two_position3_jieshao;
    private TextView fragment_two_position3_price_tv1;
    private TextView fragment_two_position3_price_tv2;
    private ArrayList<String> titleList;
    private PopupWindow popupWindow;
    private LinearLayout activity_main;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View view;
    private final ArrayList<View> listView;
    private int n=0;
    private OnItemClickListener onItemClickListener;
    public static HomeBean.DataBean.SubjectsBean subjectsBean;

    public homeAdapter(Context context, List<T> mDatas,HomeBean bean) {
        super(context, mDatas);
        this.homeBean=bean;
        listView = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //拿到解析出来的baen
        homeBean = Home_Fragment.homeBean;
        if (position == 0) {
            viewHolder = new MyViewHolder(mContext, parent, R.layout.home_xlvitem_position0, position);
            home_fragment_vp = viewHolder.getView(R.id.home_fragment_vp);
            home_fragment_vp_lin = viewHolder.getView(R.id.home_fragment_vp_lin);
            home_fregment_gv = viewHolder.getView(R.id.home_fregment_gv);
            home_fragment_benzhourexiao_lin=viewHolder.getView(R.id.home_fragment_benzhourexiao_lin);
            //设置监听
            home_fregment_gv.setOnItemClickListener(this);
            //home_fragment_benzhourexiao_lin = viewHolder.getView(R.id.home_fragment_benzhourexiao_lin);
            mViewPager = (ViewPager) viewHolder.getView(R.id.id_viewpager);
            //赋值
            getMyData();

            /////////////...........错误所在地  NullPointerException
        } else if (position < homeBean.getData().getSubjects().size() + 1) {
            viewHolder = new MyViewHolder(mContext, parent, R.layout.home_xlvitem_position1, position);
            home_item_position1_img = viewHolder.getView(R.id.home_item_position1_img);
            home_item_position1_lin = viewHolder.getView(R.id.home_item_position1_lin);
            //设置监听
            home_item_position1_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subjectsBean = homeBean.getData().getSubjects().get(position - 1);
                    //传递对象
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("bean",subjectsBean);
                    Intent inn=new Intent(mContext, ZhuanTiActivity.class);
                    inn.putExtras(bundle);
                    mContext.startActivity(inn);
                }
            });
            //图片赋值
            ImageLoader.getInstance().displayImage(homeBean.getData().getSubjects().get(position - 1).getImage(), home_item_position1_img, ImageLoaderUtils.initOptions());
            //对这个item布局中的lin添加view
            getMyLinData(position - 1);
        } else {
            viewHolder = new MyViewHolder(mContext, parent, R.layout.home_xlvitem_position2, position);
            fragment_position3_img = viewHolder.getView(R.id.fragment_position3_img);
            fragment_position3_name = viewHolder.getView(R.id.fragment_position3_name);
            fragment_position3_jieshao = viewHolder.getView(R.id.fragment_position3_jieshao);
            fragment_position3_price_tv1 = viewHolder.getView(R.id.fragment_position3_price_tv1);
            fragment_position3_price_tv2 = viewHolder.getView(R.id.fragment_position3_price_tv2);

            //赋值
            ImageLoader.getInstance().displayImage(homeBean.getData().getDefaultGoodsList().get(position - 7).getGoods_img(), fragment_position3_img, ImageLoaderUtils.initOptions());
            fragment_position3_name.setText(homeBean.getData().getDefaultGoodsList().get(position - 7).getEfficacy());
            fragment_position3_jieshao.setText(homeBean.getData().getDefaultGoodsList().get(position - 7).getGoods_name());
            fragment_position3_price_tv1.setText("￥" + homeBean.getData().getDefaultGoodsList().get(position - 7).getShop_price() + "");
            fragment_position3_price_tv2.setText("￥" + homeBean.getData().getDefaultGoodsList().get(position - 7).getMarket_price() + "");


            fragment_two_position3_img = viewHolder.getView(R.id.fragment_two_position3_img);
            fragment_two_position3_name = viewHolder.getView(R.id.fragment_two_position3_name);
            fragment_two_position3_jieshao = viewHolder.getView(R.id.fragment_two_position3_jieshao);
            fragment_two_position3_price_tv1 = viewHolder.getView(R.id.fragment_two_position3_price_tv1);
            fragment_two_position3_price_tv2 = viewHolder.getView(R.id.fragment_two_position3_price_tv2);

            //赋值
            ImageLoader.getInstance().displayImage(homeBean.getData().getDefaultGoodsList().get(position - 7).getGoods_img(), fragment_two_position3_img, ImageLoaderUtils.initOptions());
            fragment_two_position3_name.setText(homeBean.getData().getDefaultGoodsList().get(position - 7).getEfficacy());
            fragment_two_position3_jieshao.setText(homeBean.getData().getDefaultGoodsList().get(position - 7).getGoods_name());
            fragment_two_position3_price_tv1.setText("￥" + homeBean.getData().getDefaultGoodsList().get(position - 7).getShop_price() + "");
            fragment_two_position3_price_tv2.setText("￥" + homeBean.getData().getDefaultGoodsList().get(position - 7).getMarket_price() + "");

        }

        return viewHolder.getConvertView();
    }

    private void getMyLinData(final int position) {
        n = 0;
        //循环遍历集合中的数据
        if (homeBean.getData().getSubjects().size() > 6) {
            n = 6;
        } else {
            n = homeBean.getData().getSubjects().size();
        }
        for (int i = 0; i < n + 1; i++) {
            //查找布局及控件
            View hot_item = CommonUtils.inflate(R.layout.hot_item);
            ImageView hot_item_img = (ImageView) hot_item.findViewById(R.id.hot_item_img);
            TextView hot_item_name_tv = (TextView) hot_item.findViewById(R.id.hot_item_name_tv);
            TextView hot_item_tv2 = (TextView) hot_item.findViewById(R.id.hot_item_tv2);
            TextView hot_item_tv3 = (TextView) hot_item.findViewById(R.id.hot_item_tv3);
            TextView hot_item_tv_line = (TextView) hot_item.findViewById(R.id.hot_item_tv_line);
            //控件赋值
            ImageLoader.getInstance().displayImage(homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getGoods_img(), hot_item_img, ImageLoaderUtils.initOptions());
            if (homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getGoods_name().length() > 13) {
                hot_item_name_tv.setText(homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getGoods_name().substring(0, 12) + "...");
            } else {
                hot_item_name_tv.setText(homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getGoods_name());
            }
            hot_item_tv2.setText("￥" + homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getShop_price() + "");
            hot_item_tv3.setText("￥" + homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getMarket_price() + "");
            hot_item_tv_line.setText(homeBean.getData().getSubjects().get(position).getGoodsList().get(i).getMarket_price() + "");
            //添加尾部的view
            if (i == n) {
                hot_item_img.setVisibility(View.INVISIBLE);
                hot_item_name_tv.setVisibility(View.INVISIBLE);
                hot_item_tv2.setVisibility(View.INVISIBLE);
                hot_item_tv3.setVisibility(View.INVISIBLE);
                hot_item_tv_line.setVisibility(View.INVISIBLE);
                hot_item.setBackgroundResource(R.mipmap.weibu);
            }
            //跳转到详情界面
            hot_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inn=new Intent(mContext, DetailActivity.class);
                    //将商品的id值传过去
                    inn.putExtra("id",homeBean.getData().getSubjects().get(position).getGoodsList().get(v.getId()).getId());
                    mContext.startActivity(inn);
                   // Toast.makeText(mContext, v.getId() +"......"+position,Toast.LENGTH_SHORT).show();
                }
            });
            hot_item.setTag(homeBean.getData().getSubjects().get(position).getId());
            hot_item.setId(i);
            home_item_position1_lin.addView(hot_item);
        }
    }

    private void getMyData() {
        //拿到解析出来的baen
        homeBean = Home_Fragment.homeBean;
        //设置vp数据
        initImg();
        //设置gv数据
        intiGv();
        //本周热销的数据
        initHot();
        //优惠活动的数据
        initVip();
    }
    //viewpager的适配器方法
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    public void JieKou(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnPageClickListener{
        public void JieKou(int position);
    }
//    //设置接口
//    public void setOnPageClickListener(OnPageClickListener onPageClickListener){
//        this.onPageClickListener = onPageClickListener;
//    }
    private void initVip() {
        //设置Page间间距
        mViewPager.setPageMargin(50);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter()
        {
            @Override
            public Object instantiateItem(ViewGroup container, final int position)
            {
                LinearLayout home_vip_vp_item_view= (LinearLayout) CommonUtils.inflate(R.layout.home_vip_vp_item_view);
                ImageView home_vip_item_img= (ImageView) home_vip_vp_item_view.findViewById(R.id.home_vip_item_img);
                home_vip_vp_item_view.setTag(homeBean.getData().getActivityInfo().getActivityInfoList().get(position%homeBean.getData().getActivityInfo().getActivityInfoList().size()).getActivityImg());
 ImageView view = new ImageView(mContext);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoader.getInstance().displayImage(homeBean.getData().getActivityInfo().getActivityInfoList().get(position%homeBean.getData().getActivityInfo().getActivityInfoList().size()).getActivityImg(),home_vip_item_img);
                home_vip_vp_item_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
                    }
                });
                home_vip_vp_item_view.setOnClickListener(homeAdapter.this);
                container.addView(home_vip_vp_item_view);
                return home_vip_vp_item_view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }

            @Override
            public int getCount()
            {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o)
            {
                return view == o;
            }
        });
        mViewPager.setPageTransformer(true, new
                ScaleInTransformer());

        //设置vp的当前条目
        mViewPager.setCurrentItem(homeBean.getData().getActivityInfo().getActivityInfoList().size()*1000);

    }


    private void initHot() {

        int n = 0;
        //循环遍历集合中的数据
        if (homeBean.getData().getBestSellers().get(0).getGoodsList().size() > 6) {
            n = 6;
        } else {
            n = homeBean.getData().getBestSellers().get(0).getGoodsList().size();
        }

        for (int i = 0; i < n + 1; i++) {

            View hot_item = CommonUtils.inflate(R.layout.hot_item);
            ImageView hot_item_img = (ImageView) hot_item.findViewById(R.id.hot_item_img);
            TextView hot_item_name_tv = (TextView) hot_item.findViewById(R.id.hot_item_name_tv);
            TextView hot_item_tv2 = (TextView) hot_item.findViewById(R.id.hot_item_tv2);
            TextView hot_item_tv3 = (TextView) hot_item.findViewById(R.id.hot_item_tv3);
            TextView hot_item_tv_line = (TextView) hot_item.findViewById(R.id.hot_item_tv_line);
            //赋值
            ImageLoader.getInstance().displayImage(homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_img(), hot_item_img, ImageLoaderUtils.initOptions());
            if (homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_name().length() > 13) {
                hot_item_name_tv.setText(homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_name().substring(0, 12) + "...");
            } else {
                hot_item_name_tv.setText(homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_name());
            }
            hot_item_tv2.setText("￥" + homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getShop_price() + "");
            hot_item_tv3.setText("￥" + homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getMarket_price() + "");
            hot_item_tv_line.setText(homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getMarket_price() + "");
            //添加尾部的view
            if (i == n) {
                hot_item_img.setVisibility(View.INVISIBLE);
                hot_item_name_tv.setVisibility(View.INVISIBLE);
                hot_item_tv2.setVisibility(View.INVISIBLE);
                hot_item_tv3.setVisibility(View.INVISIBLE);
                hot_item_tv_line.setVisibility(View.INVISIBLE);
                hot_item.setBackgroundResource(R.mipmap.weibu);
            }
            //设置唯一的表示
            hot_item.setOnClickListener(this);
            hot_item.setId(i+100);
            hot_item.setTag(homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getId());
            home_fragment_benzhourexiao_lin.addView(hot_item);
        }
    }

    private void intiGv() {
        homeGvAdapter hg = new homeGvAdapter(mContext, homeBean.getData().getAd5());
        home_fregment_gv.setAdapter(hg);
    }

    private void initImg() {
        //Log.i("kkkkkkk", "initImg: ......"+homeBean.getData().getAd1().size());
        //图片集合
        listImg = new ArrayList<>();
        //listImg.add("http://image.hmeili.com/yunifang/images/goods/ad0/161202215838912667249788661.jpg");
        for (int i = 0; i < homeBean.getData().getAd1().size(); i++) {
            listImg.add(homeBean.getData().getAd1().get(i).getImage());
        }
        //小圆点集合
        listDot = new ArrayList<>();
        // + 1
        for (int j = 0; j < homeBean.getData().getAd1().size(); j++) {
            ImageView img = new ImageView(mContext);
            if (j == 0) {
                img.setImageResource(arrDot[0]);
                listDot.add(img);
            } else {
                img.setImageResource(arrDot[1]);
                listDot.add(img);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(CommonUtils.dip2px(5), CommonUtils.dip2px(10), CommonUtils.dip2px(5), CommonUtils.dip2px(10));
            home_fragment_vp_lin.addView(img, params);
        }
        //设置vp数据适配器
        home_fragment_vp.initData(listImg, arrDot, listDot);
        home_fragment_vp.startViewPager();
        //viewpager 的页面点击的监听事件
        home_fragment_vp_onclick();
        //初始化头部数据
        initTitleList();
    }

    private void initTitleList() {
        titleList = new ArrayList<>();
        titleList.add("");
        titleList.add("");
        titleList.add("海量赠品任性选");
        titleList.add("12月【晒单 赢好礼】活动");
        titleList.add("积分抵现");
        titleList.add("会员权益");
        titleList.add("御泥坊美肤讲堂");
        titleList.add("御泥坊 我的御用面膜");
    }


    private void home_fragment_vp_onclick() {
        home_fragment_vp.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                //Toast.makeText(mContext, "....." + position % 7, Toast.LENGTH_SHORT).show();
                ///if (position % homeBean.getData().getAd1().size() != 0 && position % homeBean.getData().getAd1().size() != 1) {
                    //跳转到详情
                    Intent inn = new Intent(mContext, HomeWebViewActivity.class);
                    //传值
                    inn.putExtra("name", homeBean.getData().getAd1().get(position % homeBean.getData().getAd1().size()).getAd_type_dynamic_data());
                    inn.putExtra("title", titleList.get(position % homeBean.getData().getAd1().size()));
                    mContext.startActivity(inn);
                //}
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                //弹出popwindow
                bottomwindow(view);
                //渐变背景色
                getBack();
                break;
            case 1:
                Intent inn = new Intent(mContext, HomeWebViewActivity.class);
                inn.putExtra("name", homeBean.getData().getAd5().get(1).getAd_type_dynamic_data());
                inn.putExtra("title", "登陆");
                mContext.startActivity(inn);
                break;
            case 2:
                //弹出popwindow
                bottomwindow(view);
                //渐变背景色
                getBack();
                break;
            case 3:
                Intent inn2 = new Intent(mContext, HomeWebViewActivity.class);
                inn2.putExtra("name", homeBean.getData().getAd5().get(3).getAd_type_dynamic_data());
                inn2.putExtra("title", "御泥坊产品-真伪查询");
                mContext.startActivity(inn2);
                break;
            case 4:
                //弹出popwindow
                bottomwindow(view);
                //渐变背景色
                getBack();
                break;
            case 5:
                Intent inn3 = new Intent(mContext, HomeWebViewActivity.class);
                inn3.putExtra("name", homeBean.getData().getAd5().get(5).getAd_type_dynamic_data());
                inn3.putExtra("title", "三人团列表");
                mContext.startActivity(inn3);
                break;
            case 6:
                Intent inn4 = new Intent(mContext, HomeWebViewActivity.class);
                inn4.putExtra("name", "http://m.yunifang.com/yunifang/web/h/rebate.html");
                inn4.putExtra("title", "双12超值满减惊喜满赠");
                mContext.startActivity(inn4);
                break;
            case 7:
                Intent inn5 = new Intent(mContext, HomeWebViewActivity.class);
                inn5.putExtra("name", "http://h.yunifang.com/scan/face_intro.html");
                inn5.putExtra("title", "测脸部尺寸");
                mContext.startActivity(inn5);
                break;
        }
    }
    //点击按钮时背景颜色逐渐变暗
    private float alpha=1.0f;
    private void getBack() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(alpha>0.5f){
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha-=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            backgroundAlpha((float) msg.obj);
        }
    };
    //弹出pop
    public void bottomwindow(View v){
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //LinearLayout layout2 = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.window_popup, null);
        LinearLayout layout= (LinearLayout) CommonUtils.inflate(R.layout.home_popwindow);
        //获取popwindow的中的控件
        getPopControl(layout);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackBackground();
            }
        });
    }
    //获取控件的方法
    private void getPopControl(LinearLayout layout) {
        TextView home_pop_cancel= (TextView) layout.findViewById(R.id.home_pop_cancel);
        TextView home_pop_confirm= (TextView) layout.findViewById(R.id.home_pop_confirm);
        //添加监听
        home_pop_cancel.setOnClickListener(this);
        home_pop_confirm.setOnClickListener(this);
    }

    //popwindow消失，背景颜色逐渐变亮
    private void BackBackground() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                while(alpha<0.999f){
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait","alpha:"+alpha);
                    Message msg =handler.obtainMessage();
                    msg.what = 1;
                    alpha+=0.01f;
                    msg.obj =alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = Home_Fragment.window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0n
        Home_Fragment.window.setAttributes(lp);
        Home_Fragment.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_pop_cancel:
                //popwindow消失
                popupWindow.dismiss();
                break;
            case R.id.home_pop_confirm:
                //跳转到登陆界面
                Intent inn=new Intent(mContext, LoginActivity.class);
                //传值过去，判断是否要显示首单立减图片
                inn.putExtra("show", MyApplication.unShowLoginImg);
                mContext.startActivity(inn);
                popupWindow.dismiss();
                break;
            default:
                break;

        }

        for (int i = 0; i <home_fragment_benzhourexiao_lin.getChildCount() ; i++) {
            View vv=home_fragment_benzhourexiao_lin.getChildAt(i);
            if(v.getId()==vv.getId()){
                //跳转到详情界面
                Intent inn=new Intent(mContext,DetailActivity.class);
                inn.putExtra("id",homeBean.getData().getBestSellers().get(0).getGoodsList().get(i).getId());
                mContext.startActivity(inn);
            }
        }
           // Toast.makeText(mContext,"....."+v.getTag(),Toast.LENGTH_SHORT).show();
    }
}