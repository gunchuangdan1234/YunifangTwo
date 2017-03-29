package com.bawei.yunifang.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.bawei.yunifang.bean.AddressBean;
import com.bawei.yunifang.bean.DetailBean;
import com.bawei.yunifang.bean.SqlBean;

/**
 * Created by Pooh on 2016/12/9.
 */
public class sqlDao {

    private final MySqlite sql;

    public sqlDao(Context context) {
        sql = new MySqlite(context);
    }
    //添加数据的方法
    public void addSql(DetailBean bean,int num){
        SQLiteDatabase db = sql.getWritableDatabase();
        db.execSQL("insert into shopping (name,url,price,number,tag) values(?,?,?,?,?)",new Object[]{
                bean.getData().getGoods().getGoods_name(),
                bean.getData().getGoods().getGoods_img(),
                bean.getData().getGoods().getShop_price(),num,
                0
        });
        //关闭数据库
        db.close();
    }
//    查询的方法
    ArrayList<SqlBean> list=new ArrayList<>();
    public ArrayList<SqlBean> selectSql(){
        //清空集合
        if(list!=null){
            list.clear();
        }
        //得到数据库
        SQLiteDatabase db=sql.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from shopping",null);
        while (cursor.moveToNext()) {
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String url=cursor.getString(cursor.getColumnIndex("url"));
            String price=cursor.getString(cursor.getColumnIndex("price"));
            String number=cursor.getString(cursor.getColumnIndex("number"));
            String tag=cursor.getString(cursor.getColumnIndex("tag"));
            SqlBean bean=new SqlBean(name,url,price,number,Integer.parseInt(tag));
            list.add(bean);
        }
        //关闭数据库
        db.close();
        return list;
    }
    //删除的方法
    public void deleteSql(String name){
        SQLiteDatabase db=sql.getWritableDatabase();
        db.execSQL("delete from shopping where name=?",new Object[]{name});
        //关闭数据库
        db.close();
    }
    //修改的方法
    public void updateSql(String name,int num){
        SQLiteDatabase db=sql.getWritableDatabase();
        db.execSQL("update shopping set number=? where name=?",new Object[]{num,name});
        db.close();
    }

    //修改的方法
    public void updateSqlTag(String name,int tag){
        SQLiteDatabase db=sql.getWritableDatabase();
        db.execSQL("update shopping set tag=? where name=?",new Object[]{tag,name});
        db.close();
    }

    //地址的添加方法
    public void addAddress(AddressBean bean){
        SQLiteDatabase db = sql.getWritableDatabase();
        db.execSQL("insert into address (name,phone,address,detailaddress,tag) values(?,?,?,?,?)",new Object[]{
                bean.getName(),
                bean.getPhone(),
                bean.getAddress(),
                bean.getDetailAddress(),
                bean.getTag()
        });
        //关闭数据库
        db.close();
    }
    //地址查询的方法
    ArrayList<AddressBean> list_address=new ArrayList<>();
    public ArrayList<AddressBean> selectSqlAddress(){
        //清空集合
        if(list_address!=null){
            list_address.clear();
        }
        //得到数据库
        SQLiteDatabase db=sql.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from address",null);
        while (cursor.moveToNext()) {
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String phone=cursor.getString(cursor.getColumnIndex("phone"));
            String address=cursor.getString(cursor.getColumnIndex("address"));
            String detailaddress=cursor.getString(cursor.getColumnIndex("detailaddress"));
            String tag=cursor.getString(cursor.getColumnIndex("tag"));
            AddressBean bean=new AddressBean(name,phone,address,detailaddress,false,tag);
            list_address.add(bean);
        }
        //关闭数据库
        db.close();
        return list_address;
    }

    //删除地址的方法
    public void deleteSqlAddress(String name){
        SQLiteDatabase db=sql.getWritableDatabase();
        db.execSQL("delete from address where name=?",new Object[]{name});
        //关闭数据库
        db.close();
    }
    //修改数据库的方法
    public void updateSqlUpdate(String name,AddressBean bean){
        SQLiteDatabase db=sql.getWritableDatabase();
        db.execSQL("update address set tag=?,name=?,phone=?,address=?,detailaddress=? where name=?",new Object[]{bean.getTag()
                ,bean.getName(),bean.getPhone(),bean.getAddress(),bean.getDetailAddress(),name});
        db.close();
    }
}
