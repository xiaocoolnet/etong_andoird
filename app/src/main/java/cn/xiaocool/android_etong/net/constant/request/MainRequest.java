package cn.xiaocool.android_etong.net.constant.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.bean.UserInfo;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.WebAddress;
import cn.xiaocool.android_etong.util.NetBaseUtils;
import cn.xiaocool.android_etong.util.NetUtil;


/**
 * Created by 潘 on 2016/6/21.
 */
public class MainRequest {
    private UserInfo user;
    private Context mContext;
    private Handler handler;

    public MainRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo(mContext);
        user.readData(mContext);
    }

    //发送验证码
    public void sendCode(final String phoNum) {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                String data = "&phone=" + phoNum;
                String result_data = NetUtil.getResponse(WebAddress.SEND_CODE,data);
                Log.e("result data is", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEND_CODE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //注册
    public void register(final String phone , final String password ,final String code) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone="+phone+"&password="+password+"&code="+code +"&devicestate=1";
                Log.e("data is ",data);
                String result_data = NetUtil.getResponse(WebAddress.REGISTER, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.REGISTER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //忘记密码
    public void forgetpassword(final String phone ,final String code , final String password){
        new Thread(){
            Message msg = Message.obtain();
            public void run(){
                String data = "&phone=" +phone+"&code="+code+"&password="+password;
                Log.e("data is ",data);
                String result_data = NetUtil.getResponse(WebAddress.FORGETPASSWORD,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.FORGETPASSWORD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //登录
    public void login(final String phone , final String password){
        new Thread(){
            Message msg =new Message();
            public  void run(){
                String data = "&phone=" +phone+"&password="+password;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.LOGIN,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LOGIN;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //上传头像
    public void uploadavatar(final String img ,final int KEY){
        new Thread(){
            Message msg = Message.obtain();
            public void run(){
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("upfile", img));
                    String result = NetBaseUtils.getResponseForImg(WebAddress.UPLOADAVATAR, params, mContext);
                    Log.e("头像路径", img);
//                    Log.e("result= ",result);
                    msg.what = KEY;
                    msg.obj = result;
                    handler.sendMessage(msg);
            }
        }.start();
    }
    //获取个人资料
    public void userinfo(){
        new Thread(){
            Message msg = new Message();
            public void run(){
                String data = "&userid=" +user.getUserId();
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.GETUSERINFO,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETUSERINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改头像资料
    public void updatauseravatar(final String avatar){
        new Thread(){
            Message msg =new Message();
            public  void run(){
                String data = "&userid=" +user.getUserId()+"&avatar="+avatar;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.UPLOADUSERAVATAR,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPUSERAVATAR;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改昵称资料
    public void updatausername(final String nicename){
        new Thread(){
            Message msg =new Message();
            public  void run(){
                String data = "&userid=" +user.getUserId()+"&nicename="+nicename;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.UPDATAUSERNAME,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERNAME;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改性别资料
    public void updatausersex(final String sex){
        new Thread(){
            Message msg =new Message();
            public  void run(){
                String data = "&userid=" +user.getUserId()+"&sex="+sex;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.UPDATAUSERSEX,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERSEX;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改手机号
    public  void updatauserphone(final String phone){
        new Thread(){
            Message msg = new Message();
            public void run(){
                String data = "&userid=" +user.getUserId()+"&phone="+phone;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.UPDATAUSERPHONE,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERPHONE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

//创建店铺
    public void CreateShop(final String legalperson,final String phone,final String idcard,final String address,
                           final String positive_pic,final String opposite_pic,final String license_pic ,final int KEY){
        new Thread(){
            Message msg = new Message();
            public void run(){
                String data = "&userid="+user.getUserId()+"&city=yantai"+"&legalperson="+legalperson+
                        "&phone="+phone+"&type=shangpin"+"&businesslicense=123"+"&address="+address+"&idcard="+idcard
                        +"&positive_pic="+positive_pic+"&opposite_pic="+opposite_pic+"&license_pic="+license_pic;
                Log.e("data is ",data);
                String result_data =NetUtil.getResponse(WebAddress.CREATESHOP,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取店铺状态
    public void getmyshop(){
        new Thread(){
            Message msg = new Message();
            public void run(){
                String data="&userid="+user.getUserId();
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GETMYSHOP,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what =CommunalInterfaces.GETMYSHOP;
                    msg.obj=obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //发布商品
public void publishgoods(final String shopid,final String pic1,final String pic2,final String pic3,final String goodsname,final String type,
                         final String oprice,final String price,final String address,final String description ,
                         final String unit , final String longitude){
    new Thread(){
        Message msg = new Message();
        @Override
        public void run() {
            String data="&userid="+user.getUserId()+"&shopid="+shopid+"&piclist="+pic1+","+pic2+","+pic3+
                    "&goodsname="+goodsname+"&type="+type+"&oprice="+oprice+"&price="+price+"&description="
                    +description+"&address="+address+"&unit="+unit+"&longitude="+longitude+"&latitude=1";
            Log.e("data=",data);
            String result_data = NetUtil.getResponse(WebAddress.PUBLISHGOODS,data);
            Log.e("successful", result_data);
            try {
                JSONObject obj = new JSONObject(result_data);
                msg.what =CommunalInterfaces.PUBLISHGOODS;
                msg.obj=obj;
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                handler.sendMessage(msg);
            }
        }
    }.start();
}

    //获取上架产品列表
    public void getshopgoodlist(final String shopid){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data="&userid="+user.getUserId()+"&shopid="+shopid+"&xiajia=0";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPGOODLIST,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what =CommunalInterfaces.GETSHOPGOODLIST;
                    msg.obj=obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取下架产品列表
    public void getshopgoodlist_xiajia(final String shopid){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data="&userid="+user.getUserId()+"&shopid="+shopid+"&xiajia=1";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPGOODLIST_XIAJIA,data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what =CommunalInterfaces.GETSHOPGOODLIST_XIAJIA;
                    msg.obj=obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //下架产品
    public void goodsxiajia(final String id){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GOODSXIAJIA,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject =new JSONObject(result_data) ;
                    msg.what = CommunalInterfaces.GOODSXIAJIA;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //上架产品
    public void goodsshangjia(final String id){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GOODSSHANGJIA,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject =new JSONObject(result_data) ;
                    msg.what = CommunalInterfaces.GOODSSHANGJIA;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //删除产品
    public void deletegoods(final String id){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.DELETEGOODS,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject =new JSONObject(result_data) ;
                    msg.what = CommunalInterfaces.DELETEGOODS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改店铺头像(实为更改地址 先拿来储存头像信息)
    public  void  updatashopaddress(final String id,final String address){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id+"&address="+address;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATASHOPADDRESS,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject =new JSONObject(result_data) ;
                    msg.what = CommunalInterfaces.UPDATASHOPADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改店铺头像(实为更改地址 先拿来储存头像信息)
    public  void  UpdateShopPhoto(final String id,final String photo){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id+"&photo="+photo;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATESHOPPHOTO,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject =new JSONObject(result_data) ;
                    msg.what = CommunalInterfaces.UPDATASHOPADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //修改店铺名称
    public void updateshopname(final String id ,final String shopname){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id+"&shopname="+shopname;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATASHOPNAME,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATASHOPNAME;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //修改店铺地址
    public void UpdateShopAddress(final String id,final String address){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id+"&address="+address;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATA_SHOP_ADDRESS,data);
                Log.e("result_data = ",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATA_SHOP_ADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


//获取单个商品详情
    public void getgoodsinfo(final String id){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&id="+id;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GET_GOODS_INFO,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GOODS_INFO;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //获取店铺详情
    public void getshopinfo(final String shopid){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&shopid="+shopid;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GET_SHOP_INFO,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOP_INFO;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //商品产品购买
    public void bookingshopping(final String goodsid,final String peoplename,final String address,final String goodnum,final String mobile,final String remark,final String money){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&userid="+user.getUserId()+"&goodsid="+goodsid+"&address="+address+"&goodnum="+goodnum+"&mobile="+mobile+"&remark="+remark+"&money="+money;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.BOOKING_SHOPPING,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BOOKING_SHOPPING;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的商城订单列表
    public void getshoppingorderlist(){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&userid="+user.getUserId();
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPPINGORDERLIST,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOPPING_ORDER_LIST;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //获取店铺列表
    public void GetShopList(final String city){
        new Thread(){
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&city="+city;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GET_SHOP_LIST,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOP_LIST;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
