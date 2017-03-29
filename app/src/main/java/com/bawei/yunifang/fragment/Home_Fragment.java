package com.bawei.yunifang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.bawei.yunifang.DetailActivity;
import com.bawei.yunifang.MoreGoodsActivity;
import com.bawei.yunifang.R;
import com.bawei.yunifang.adapter.homeAdapter;
import com.bawei.yunifang.base.BaseDate;
import com.bawei.yunifang.base.BaseFragment;
import com.bawei.yunifang.bean.HomeBean;
import com.bawei.yunifang.utils.CommonUtils;
import com.bawei.yunifang.utils.URLUtils;
import com.bawei.yunifang.view.ShowingPage;
import com.bawei.yunifang.view.XListView;

/**
 * Created by Pooh on 2016/11/28.
 */
public class Home_Fragment extends BaseFragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
String data=null;
    private View vv;
    private XListView home_xlv;
    public static HomeBean homeBean;
    private ArrayList<String> listData;
    public static Context cc;
    public static Window window;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HomeBean homeBean= (HomeBean) msg.obj;
            //初始化控件
            initView(homeBean);
        }
    };

    @Override
    protected void onload() {

        //设置布局
        Home_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }
    @Override
    protected View createSuccessView() {
        new BaseDate() {
            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }

            @Override
            public void setResultData(String data) {

                Home_Fragment.this.data=data;
                //解析数据
                Gson gson=new Gson();
                homeBean = gson.fromJson(data, HomeBean.class);
                //Toast.makeText(getActivity(),"...."+data,Toast.LENGTH_SHORT).show();
                Message msg=new Message();
                msg.obj=homeBean;
                handler.sendMessage(msg);
            }
        }.getDate(URLUtils.homeUrl,URLUtils.homeArgs,0,BaseDate.NOMALTIME);
        vv = CommonUtils.inflate(R.layout.home1_fragment);
          window = getActivity().getWindow();
//        //初始化控件
//        initView();
        return vv;
    }

    private void initView(HomeBean homeBean) {
        //初始化集合
        initList(homeBean);

        home_xlv = (XListView) vv.findViewById(R.id.home1_xlv);
        home_xlv.setAdapter(new homeAdapter<String>(getActivity(),listData,homeBean));

        //设置xlv可以上拉加载
        home_xlv.setPullLoadEnable(true);
        //设置监听
        home_xlv.setXListViewListener(this);

        home_xlv.setOnItemClickListener(this);
    }

    private void initList(HomeBean homeBean) {
        //集合循环的次数
        int listCount=homeBean.getData().getSubjects().size();
        int gridCount=homeBean.getData().getDefaultGoodsList().size();
        int count=listCount+((int)gridCount/2)+1;

        int n=listCount+1;
        //创建集合
        listData = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            listData.add("000");
        }
    }

    @Override
    public void onRefresh() {
        onload();
    }

    @Override
    public void onLoadMore() {
        onload();
        getActivity().startActivity(new Intent(getActivity(), MoreGoodsActivity.class));
    }

    //停止刷新，加载
    public void onLoad(){
        home_xlv.stopRefresh();
        home_xlv.stopLoadMore();
        home_xlv.setRefreshTime("刚刚");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到详情界面
        Intent inn=new Intent(getActivity(), DetailActivity.class);
        inn.putExtra("id",homeBean.getData().getDefaultGoodsList().get(position - 10).getId());
        startActivity(inn);
    }
}
