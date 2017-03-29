package com.bawei.yunifang;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.bawei.yunifang.base.BaseDate;
import com.bawei.yunifang.bean.DetailBean;
import com.bawei.yunifang.bean.GoodsXiang;
import com.bawei.yunifang.bean.SqlBean;
import com.bawei.yunifang.fragment.Cart_Fragment;
import com.bawei.yunifang.sql.sqlDao;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.ImageLoaderUtils;
import com.bawei.yunifang.view.MyListView;
import com.bawei.yunifang.view.MyRoolViewPagerNoScoll;
import com.bawei.yunifang.view.MyScollView;
import com.bawei.yunifang.view.ShowingPage;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, MyScollView.OnScrollListener {
    private int num=1;
    private ImageView goods_back_img;
    private ImageView goods_sopping_img;
    private ImageView goods_share_img;
    private MyRoolViewPagerNoScoll goods_vp;
    private TextView goods_name;
    private TextView goods_shop_price;
    private TextView goods_first_price;
    private ImageView goods_di_img;
    private TextView goods_shoucang_tv;
    private TextView goods_tv1;
    private TextView goods_tv2;
    private TextView goods_tv3;
    private TextView goods_goods_tv;
    private TextView goods_parameter_tv;
    private TextView goods_comment_tv;
    private MyListView goods_lv;
    private TextView goods_kefu_tv;
    private TextView goods_jia_shopping_tv;
    private TextView goods_buy_tv;
    private String id;
    //判断要用那个适配器
    public final int goodsGoods=0;
    public final int goodsComment=1;
    public final int goods_parameter=2;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data=(String) msg.obj;
            //解析
            Gson gson=new Gson();
            detailBean = gson.fromJson(data, DetailBean.class);
            //设置vp适配器
            setVpAdapter(detailBean);
            //控件赋值
            setDate(detailBean);
        }
    };
    private ColorStateList colorStateList;
    private int changingConfigurations;
    private DetailBean detailBean;
    private List<DetailBean.DataBean.GoodsBean.AttributesBean> attributes;
    private List<DetailBean.DataBean.CommentsBean> listComments;
    private LinearLayout search01;
    private LinearLayout search02;
    private View goods_title;
    private MyScollView home_myscollview;
    private RelativeLayout goods_xiangqing_dingbu_rela;
    private int searchLayoutTop;
    private RelativeLayout goods_xangqing_rela;
    private LinearLayout goods_xiangqing_dingbu_lin;
    private PopupWindow popupWindow;
    private ImageView buy_goods_img;
    private TextView buy_pop_name_tv;
    private TextView buy_pop_sql_tv;
    private TextView buy_pop_count_tv;
    private TextView buy_pop_jian_tv;
    private TextView buy_pop_goods_number_tv;
    private TextView buy_pop_jia_tv;
    private TextView buy_tv;
    private ImageView buy_pop_close;


    private void setDate(DetailBean detailBean) {
        //名称及价格赋值
        goods_name.setText(detailBean.getData().getGoods().getGoods_name());
        goods_shop_price.setText("￥"+detailBean.getData().getGoods().getShop_price()+"");
        //底部加横线
        goods_first_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goods_first_price.setText("￥"+detailBean.getData().getGoods().getMarket_price());
        //下面三行赋值
        goods_tv1.setText(detailBean.getData().getActivity().get(0).getTitle());
        goods_tv2.setText(detailBean.getData().getActivity().get(1).getTitle());
        goods_tv3.setText(detailBean.getData().getActivity().get(0).getTitle());
        //商品详情的lv
        Goods_lv_Adapter(detailBean,R.layout.goodsxiangimg,goodsGoods);
        //商品详情的tv
        goods_comment_tv.setText("评论"+"("+detailBean.getData().getCommentNumber()+")");
    }

    private void Goods_lv_Adapter(final DetailBean detailBean, int view, int tag) {
        if(tag==goodsGoods){
            //解析数据
            Gson gson=new Gson();
            GoodsXiang[] goodsXiang=gson.fromJson(detailBean.getData().getGoods().getGoods_desc(), GoodsXiang[].class);
            ArrayList<GoodsXiang> goods_list=new ArrayList<>();
            for (int i = 0; i < goodsXiang.length; i++) {
                goods_list.add(goodsXiang[i]);
            }
            //lv赋适配器
            goods_lv.setAdapter(new CommonAdapter<GoodsXiang>(DetailActivity.this, R.layout.goodsxiangimg,goods_list) {

                private ImageView img;

                @Override
                protected void convert(ViewHolder viewHolder, GoodsXiang item, int position) {
                    img = viewHolder.getView(R.id.goods_xiangqing_img);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    ImageLoader.getInstance().displayImage(item.getUrl(),img);
                }
            });
            //评论
        }else if(tag==goods_parameter){
            listComments = detailBean.getData().getComments();
            if(listComments==null){
                Toast.makeText(DetailActivity.this,"暂无评论....",Toast.LENGTH_SHORT).show();
            }else {
                goods_lv.setAdapter(new CommonAdapter<DetailBean.DataBean.CommentsBean>(DetailActivity.this, view, listComments) {
                    @Override
                    protected void convert(ViewHolder viewHolder, DetailBean.DataBean.CommentsBean item, int position) {
                        //找到控件
                        ImageView goods_parameter_user_img = viewHolder.getView(R.id.goods_parameter_user_img);
                        TextView goods_parameter_user_name = viewHolder.getView(R.id.goods_parameter_user_name);
                        TextView goods_parameter_user_time = viewHolder.getView(R.id.goods_parameter_user_time);
                        TextView goods_parameter_user_parameter = viewHolder.getView(R.id.goods_parameter_user_parameter);
                        ImageView goods_parameter_user_img1 = viewHolder.getView(R.id.goods_parameter_user_img1);
                        ImageView goods_parameter_user_img2 = viewHolder.getView(R.id.goods_parameter_user_img2);
                        ImageView goods_parameter_user_img3 = viewHolder.getView(R.id.goods_parameter_user_img3);
                        //赋值
                        ImageLoader.getInstance().displayImage(item.getUser().getIcon(), goods_parameter_user_img, ImageLoaderUtils.initOptions2());
                        goods_parameter_user_name.setText(item.getUser().getNick_name());
                        //时间的转换
                        String[] arrTime = item.getCreatetime().split(" ");
                        goods_parameter_user_time.setText(arrTime[0].substring(0, 4) + "年" + arrTime[0].substring(5, 7) + "月" + arrTime[0].substring(8, 10) + "日");
                        goods_parameter_user_parameter.setText(item.getContent());
                        //判断评论是否有图片
                        if (item.getUserList() != null) {
                            if (item.getUserList().size() == 1) {
                                ImageLoader.getInstance().displayImage(item.getUserList().get(0).getNormal_url(), goods_parameter_user_img1, ImageLoaderUtils.initOptions());
                            } else if (item.getUserList().size() == 2) {
                                ImageLoader.getInstance().displayImage(item.getUserList().get(1).getNormal_url(), goods_parameter_user_img2, ImageLoaderUtils.initOptions());
                            } else {
                                ImageLoader.getInstance().displayImage(item.getUserList().get(2).getNormal_url(), goods_parameter_user_img3, ImageLoaderUtils.initOptions());
                            }
                            //如果没有图片就将3个img隐藏
                        } else {
                            goods_parameter_user_img1.setVisibility(View.GONE);
                            goods_parameter_user_img2.setVisibility(View.GONE);
                            goods_parameter_user_img3.setVisibility(View.GONE);
                        }
                    }
                });
            }
            //产品参数
        }else{
            if(detailBean!=null){
            attributes = detailBean.getData().getGoods().getAttributes();
            goods_lv.setAdapter(new CommonAdapter<DetailBean.DataBean.GoodsBean.AttributesBean>(DetailActivity.this,view,attributes) {
                @Override
                protected void convert(ViewHolder viewHolder, DetailBean.DataBean.GoodsBean.AttributesBean item, int position) {
                    //找控件
                    TextView goods_lv_comment_tv1=viewHolder.getView(R.id.goods_lv_comment_tv1);
                    TextView goods_lv_comment_tv2=viewHolder.getView(R.id.goods_lv_comment_tv2);
                    goods_lv_comment_tv1.setText(detailBean.getData().getGoods().getAttributes().get(position).getAttr_name());
                    goods_lv_comment_tv2.setText(detailBean.getData().getGoods().getAttributes().get(position).getAttr_value()+"");
                }
            });
            }
        }
    }

    private ArrayList<String> list_detail_img;
    private ArrayList<ImageView> list_dot;
    private LinearLayout goods_dot;
    int[] arrDot=new int[]{R.mipmap.face_guidance_tip_selected3,R.mipmap.face_guidance_tip_unselected3};
    //设置vp适配器
    private void setVpAdapter(DetailBean detailBean) {
        //初始化小圆点
        list_dot = new ArrayList<>();
        //初始化图片集合
        list_detail_img = new ArrayList<>();
        for (int i = 0; i < detailBean.getData().getGoods().getGallery().size(); i++) {
            //图片
            list_detail_img.add(detailBean.getData().getGoods().getGallery().get(i).getNormal_url());
            //小圆点
            ImageView dot_img=new ImageView(DetailActivity.this);
            if(i==0){
                dot_img.setImageResource(R.mipmap.face_guidance_tip_selected3);
                list_dot.add(dot_img);
            }else{
                dot_img.setImageResource(R.mipmap.face_guidance_tip_unselected3);
                list_dot.add(dot_img);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(CommonUtils.dip2px(5), CommonUtils.dip2px(10), CommonUtils.dip2px(5), CommonUtils.dip2px(10));
            goods_dot.addView(dot_img, params);
        }

        //设置vp数据适配器
        goods_vp.initData(list_detail_img, arrDot, list_dot);
        goods_vp.startViewPager();
        goods_vp.setOnPageClickListener(new MyRoolViewPagerNoScoll.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_goods_xiangqing);
        //接收传来的id值
        id = getIntent().getStringExtra("id");
        //Toast.makeText(this,"...."+id,Toast.LENGTH_SHORT).show();
        //初始化控件
        initView();
        //请求网络数据
        getWww();
        //设置监听的方法
        setListener();

        Runnable run=new Runnable() {
            @Override
            public void run() {
                home_myscollview.scrollTo(10,10);
            }
        };
        handler.post(run);
    }

    private void setListener() {
        goods_goods_tv.setOnClickListener(this);
        goods_parameter_tv.setOnClickListener(this);
        goods_comment_tv.setOnClickListener(this);
    }

    private void initView() {
        //返回按钮
        goods_back_img = (ImageView) findViewById(R.id.goods_back_img);
        //购物车按钮
        goods_sopping_img = (ImageView) findViewById(R.id.goods_shopping_img);
        //分享按钮
        goods_share_img = (ImageView) findViewById(R.id.goods_share_img);
        //viewpager
        goods_vp = (MyRoolViewPagerNoScoll) findViewById(R.id.goods_vp);
        //商品名称
        goods_name = (TextView) findViewById(R.id.goods_name);
        //商品现售价格
        goods_shop_price = (TextView) findViewById(R.id.goods_shop_price);
        //商品原价
        goods_first_price = (TextView) findViewById(R.id.goods_first_price);
        //"抵"图片
        goods_di_img = (ImageView) findViewById(R.id.goods_di_img);
        //收藏的tv
        goods_shoucang_tv = (TextView) findViewById(R.id.goods_shoucang_tv);
        //商品活动三个tv
        goods_tv1 = (TextView) findViewById(R.id.goods_tv1);
        goods_tv2 = (TextView) findViewById(R.id.goods_tv2);
        goods_tv3 = (TextView) findViewById(R.id.goods_tv3);
        //产品详情tv
        goods_goods_tv = (TextView) findViewById(R.id.goods_goods_tv);
        //产品参数tv
        goods_parameter_tv = (TextView) findViewById(R.id.goods_parameter_tv);
        //评论tv
        goods_comment_tv = (TextView) findViewById(R.id.goods_comment_tv);
        //产品详情lv
        goods_lv = (MyListView) findViewById(R.id.goods_lv);
        //客服tv
        goods_kefu_tv = (TextView) findViewById(R.id.goods_kefu_tv);
        //加入购物车
        goods_jia_shopping_tv = (TextView) findViewById(R.id.goods_jia_shopping_tv);
        goods_jia_shopping_tv.setOnClickListener(this);
        //立即购买
        goods_buy_tv = (TextView) findViewById(R.id.goods_buy_tv);
        goods_buy_tv.setOnClickListener(this);
        //小圆点的lin
        goods_dot = (LinearLayout) findViewById(R.id.goods_dot);
        //空中悬浮的lin
        search01 = (LinearLayout) findViewById(R.id.search01);
        search02 = (LinearLayout) findViewById(R.id.search02);
        //空中悬浮的lin要添加的View
        goods_xangqing_rela = (RelativeLayout)findViewById(R.id.goods_xangqing_rela);
        //scollview
        home_myscollview = (MyScollView) findViewById(R.id.home_myscollview);
        //设置滑动监听
        home_myscollview.setOnScrollListener(this);
        //获取顶部
        goods_xiangqing_dingbu_rela = (RelativeLayout) findViewById(R.id.goods_xiangqing_dingbu_rela);

        goods_xiangqing_dingbu_lin = (LinearLayout) findViewById(R.id.goods_xiangqing_dingbu_lin);

        goods_lv.setFocusable(false);
    }
    //请求商品详情的网络数据
    public void getWww(){
        new BaseDate() {
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String data) {
                //发送Handler
                Message msg=new Message();
                msg.obj=data;
                handler.sendMessage(msg);
            }
        }.getDate("http://m.yunifang.com/yunifang/mobile/goods/detail","random=6716&encode=b02382bd9e457e06e09b68a6a4f26eb4&id="+id,Integer.parseInt(id),BaseDate.NOMALTIME);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_goods_tv:
                //改变字体颜色
                changeTextColor(goods_goods_tv,goods_parameter_tv,goods_comment_tv);
                //设置数据适配器
                Goods_lv_Adapter(detailBean,R.layout.goodsxiangimg,goodsGoods);
                break;
            case R.id.goods_parameter_tv:
                //改变字体颜色
                changeTextColor(goods_parameter_tv,goods_goods_tv,goods_comment_tv);
                //设置数据适配器
                Goods_lv_Adapter(detailBean,R.layout.goods_paramete_item_view,goodsComment);
                break;
            case R.id.goods_comment_tv:
                //改变字体颜色
                changeTextColor(goods_comment_tv,goods_goods_tv,goods_parameter_tv);
                //设置数据适配器
                Goods_lv_Adapter(detailBean,R.layout.goods_pinglun_item_view,goods_parameter);
                break;
            //加入购物车的按钮
            case R.id.goods_jia_shopping_tv:
                bottomwindow(v);
                getBack();
                break;
            //立即购买的按钮
            case R.id.goods_buy_tv:

                break;
            case R.id.buy_tv:
                boolean ff=true;
                sqlDao dao=new sqlDao(DetailActivity.this);
                //先查询
                ArrayList<SqlBean> list=dao.selectSql();
                int b=0;
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getName().equals(detailBean.getData().getGoods().getGoods_name())){
                        Log.i("kkkkkk", "onClick: ......"+list.get(i).getName());
                        ff=false;
                        b=Integer.parseInt(list.get(i).getNum());
                    }
                }
                if(ff){
                        Log.i("kkkkkk", "onClick: ......");
                     dao.addSql(detailBean, Integer.parseInt(buy_pop_goods_number_tv.getText().toString()));
                }else{
                    dao.updateSql(detailBean.getData().getGoods().getGoods_name(),b+1);
                }
                //刷新购物车的方法
                Cart_Fragment.Refrush();
             break;
            case R.id.buy_pop_jian_tv:
                if(num>1){
                    num--;
                    buy_pop_jian_tv.setBackgroundResource(R.mipmap.heijian);
                }else{
                    buy_pop_jian_tv.setBackgroundResource(R.mipmap.huijian);
                }

                if(num<5){
                    buy_pop_jia_tv.setBackgroundResource(R.mipmap.heijia);
                }else{
                    buy_pop_jia_tv.setBackgroundResource(R.mipmap.huijia);
                }
                buy_pop_goods_number_tv.setText(num+"");
                break;
            case R.id.buy_pop_jia_tv:
                if(num<5){
                    num++;
                    buy_pop_jia_tv.setBackgroundResource(R.mipmap.heijia);
                }else{
                    buy_pop_jia_tv.setBackgroundResource(R.mipmap.huijia);
                }

                if(num>1){
                    buy_pop_jian_tv.setBackgroundResource(R.mipmap.heijian);
                }else{
                    buy_pop_jian_tv.setBackgroundResource(R.mipmap.huijian);
                }
                buy_pop_goods_number_tv.setText(num+"");
                break;
            case R.id.buy_pop_close:
                if(popupWindow!=null){
                    popupWindow.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private void changeTextColor(TextView tv1Red,TextView tv2Gray,TextView tv3Gray) {
        //改变字体颜色
        Resources resources=getBaseContext().getResources();
        ColorStateList colorStateList=resources.getColorStateList(R.color.text_color);
        tv1Red.setTextColor(colorStateList);
        tv2Gray.setTextColor(Color.GRAY);
        tv3Gray.setTextColor(Color.GRAY);
    }
    //scollview滑动的监听
    boolean flag=true;
    @Override
    public void onScroll(int scrollY) {
        if(scrollY >= searchLayoutTop){
            if (goods_xangqing_rela.getParent()!=search01) {
                search02.removeView(goods_xangqing_rela);
                search01.addView(goods_xangqing_rela);
            }
        }else{
            if (goods_xangqing_rela.getParent()!=search02) {
                search01.removeView(goods_xangqing_rela);
                search02.addView(goods_xangqing_rela);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            //获取searchLayout的顶部位置
            searchLayoutTop = goods_xiangqing_dingbu_lin.getBottom();
        }
    }

    //弹出pop
    public void bottomwindow(View v){
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //LinearLayout layout2 = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.window_popup, null);
        LinearLayout layout= (LinearLayout) CommonUtils.inflate(R.layout.buy_pop);
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
        popupWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackBackground();
            }
        });
    }
    //加入购物车的popwindow的布局控件
    private void getPopControl(LinearLayout layout) {
        buy_pop_close = (ImageView) layout.findViewById(R.id.buy_pop_close);
        buy_goods_img = (ImageView) layout.findViewById(R.id.buy_goods_img);
        buy_pop_name_tv = (TextView) layout.findViewById(R.id.buy_pop_name_tv);
        buy_pop_sql_tv = (TextView) layout.findViewById(R.id.buy_pop_sql_tv);
        buy_pop_count_tv = (TextView) layout.findViewById(R.id.buy_pop_count_tv);
        buy_pop_jian_tv = (TextView) layout.findViewById(R.id.buy_pop_jian_tv);
        buy_pop_goods_number_tv = (TextView) layout.findViewById(R.id.buy_pop_goods_number_tv);
        buy_pop_jia_tv = (TextView) layout.findViewById(R.id.buy_pop_jia_tv);
        buy_tv = (TextView) layout.findViewById(R.id.buy_tv);
        buy_tv.setOnClickListener(this);
        buy_pop_jian_tv.setOnClickListener(this);
        buy_pop_jia_tv.setOnClickListener(this);
        buy_pop_close.setOnClickListener(this);
        //控件赋值
        if(detailBean!=null){
            ImageLoader.getInstance().displayImage(detailBean.getData().getGoods().getGoods_img(),buy_goods_img);
            buy_pop_name_tv.setText("￥"+detailBean.getData().getGoods().getShop_price());
            buy_pop_sql_tv.setText("库存"+detailBean.getData().getCommentNumber()+"件");
        }
    }

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            backgroundAlpha((float) msg.obj);
        }
    };

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0n
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    //popwindow消失，背景颜色逐渐变亮
    private float alpha=1.0f;
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
                    Message msg =handler2.obtainMessage();
                    msg.what = 1;
                    alpha+=0.01f;
                    msg.obj =alpha ;
                    handler2.sendMessage(msg);
                }
            }

        }).start();
    }

    //点击时背景逐渐变暗
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
                    Message msg =handler2.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha-=0.01f;
                    msg.obj =alpha ;
                    handler2.sendMessage(msg);
                }
            }

        }).start();
    }
}
