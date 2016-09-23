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
                String result_data = NetUtil.getResponse(WebAddress.SEND_CODE, data);
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
    public void register(final String phone, final String password, final String code) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone=" + phone + "&password=" + password + "&code=" + code + "&devicestate=1";
                Log.e("data is ", data);
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
    public void forgetpassword(final String phone, final String code, final String password) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&phone=" + phone + "&code=" + code + "&password=" + password;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.FORGETPASSWORD, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.FORGETPASSWORD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //登录
    public void login(final String phone, final String password) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&phone=" + phone + "&password=" + password;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.LOGIN, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LOGIN;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //上传头像
    public void uploadavatar(final String img, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
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
    public void userinfo() {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.GETUSERINFO, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETUSERINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改头像资料
    public void updatauseravatar(final String avatar) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&avatar=" + avatar;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.UPLOADUSERAVATAR, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPUSERAVATAR;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改昵称资料
    public void updatausername(final String nicename) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&nicename=" + nicename;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATAUSERNAME, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERNAME;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改性别资料
    public void updatausersex(final String sex) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&sex=" + sex;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATAUSERSEX, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERSEX;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改手机号
    public void updatauserphone(final String phone) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&phone=" + phone;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATAUSERPHONE, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATAUSERPHONE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //创建店铺
    public void CreateShop(final String city,final String type,final String legalperson, final String phone, final String idcard, final String address,
                           final String positive_pic, final String opposite_pic, final String license_pic, final int KEY) {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&city="+city + "&legalperson=" + legalperson +
                        "&phone=" + phone + "&type=" + type+"&businesslicense=123" + "&address=" + address + "&idcard=" + idcard
                        + "&positive_pic=" + positive_pic + "&opposite_pic=" + opposite_pic + "&license_pic=" + license_pic;
                Log.e("data is ", data);
                String result_data = NetUtil.getResponse(WebAddress.CREATESHOP, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取店铺状态
    public void getmyshop() {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GETMYSHOP, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETMYSHOP;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //获取店铺状态以设置我要开店文字
    public void getMyShopText() {
        new Thread() {
            Message msg = new Message();

            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GETMYSHOP, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_MY_SHOP_TEXT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //发布商品
    public void publishgoods(final String shopid, final String pic1, final String pic2, final String pic3,final String pic4,final String pic5, final String goodsname, final String type,
                             final String brand, final String artNo, final String standard, final String price, final String oprice, final String freight,
                             final String inventory, final String description, final String address) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&shopid=" + shopid + "&piclist=" + pic1 + "," + pic2 + "," + pic3 +","+pic4+","+pic5+
                        "&goodsname=" + goodsname + "&type=" + type + "&brand=" + brand + "&artno=" + artNo + "&unit=" + standard +
                        "&price=" + price + "&oprice=" + oprice + "&freight=" + freight + "&inventory=" + inventory + "&description="
                        + description + "&address=" + address;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.PUBLISHGOODS, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.PUBLISHGOODS;
                    msg.obj = obj;
                    Log.e("return is", result_data);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取上架产品列表
    public void getshopgoodlist(final String shopid) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&shopid=" + shopid + "&xiajia=0";
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPGOODLIST, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETSHOPGOODLIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取下架产品列表
    public void getshopgoodlist_xiajia(final String shopid) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&shopid=" + shopid + "&xiajia=1";
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPGOODLIST_XIAJIA, data);
                Log.e("successful", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GETSHOPGOODLIST_XIAJIA;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //下架产品
    public void goodsxiajia(final String id) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GOODSXIAJIA, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GOODSXIAJIA;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //上架产品
    public void goodsshangjia(final String id) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GOODSSHANGJIA, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GOODSSHANGJIA;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //删除产品
    public void deletegoods(final String id) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.DELETEGOODS, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.DELETEGOODS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改店铺头像(实为更改地址 先拿来储存头像信息)
    public void updatashopaddress(final String id, final String address) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id + "&address=" + address;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATASHOPADDRESS, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATASHOPADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改店铺头像(实为更改地址 先拿来储存头像信息)
    public void UpdateShopPhoto(final String id, final String photo) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id + "&photo=" + photo;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATESHOPPHOTO, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATASHOPADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //修改店铺名称
    public void updateshopname(final String id, final String shopname) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id + "&shopname=" + shopname;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATASHOPNAME, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATASHOPNAME;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //修改店铺地址
    public void UpdateShopAddress(final String id, final String address) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id + "&address=" + address;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.UPDATA_SHOP_ADDRESS, data);
                Log.e("result_data = ", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.UPDATA_SHOP_ADDRESS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //获取单个商品详情
    public void getgoodsinfo(final String id) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&id=" + id;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GET_GOODS_INFO, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_GOODS_INFO;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //获取店铺详情
    public void getshopinfo(final String shopid) {
        new Thread() {
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&shopid=" + shopid;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GET_SHOP_INFO, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOP_INFO;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //商品产品购买
    public void bookingshopping(final String goodsid, final String peoplename, final String address, final String goodnum, final String mobile, final String remark, final String money , final String proid) {
        new Thread() {
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodsid + "&address=" + address +
                        "&goodnum=" + goodnum + "&mobile=" + mobile + "&remark=" + remark +
                        "&money=" + money + "&proid=" + proid;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.BOOKING_SHOPPING, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BOOKING_SHOPPING;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取我的商城订单列表
    public void getshoppingorderlist(final String state) {

        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + state;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GETSHOPPINGORDERLIST, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOPPING_ORDER_LIST;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    //获取店铺列表
    public void GetShopList(final String city,final String type) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&city=" + city + "&type=" + type;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GET_SHOP_LIST, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOP_LIST;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //    添加购物车
    public void addShoppingCart(final String goodsid, final String goodsnum, final String shopid , final String proid) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodsid + "&goodsnum=" + goodsnum + "&shopid=" + shopid + "&proid=" + proid;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.ADD_SHOPPING_CART, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ADD_SHOPPING_CART;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //    获取我的购物车
    public void GetShoppingCart() {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.GET_SHOPPPING_CART, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SHOPPING_CART;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //修改购物车
    public void EditShoppingCart(final String goodsid, final String goodsnum) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodsid + "&goodsnum=" + goodsnum;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.Edit_Shopping_Cart, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.EDIT_SHOPPING_CART;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //删除购物车
    public void DeleteShoppingCart(final String goodsid) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&userid=" + user.getUserId() + "&goodsid=" + goodsid;
                Log.e("data=", data);
                String result_data = NetUtil.getResponse(WebAddress.Delete_Shopping_Cart, data);
                Log.e("result_data=", result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.DELETE_SHOPPING_CART;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取卖家中心的订单列表
    public void getSellerOrderList(final String shopId ,final String state) {

        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&shopid=" + shopId + state;
                String result_data = NetUtil.getResponse(WebAddress.GET_SELLER_ORDER_LIST, data);
                Log.e("result_data=", result_data);
                Log.e(".shopis is", shopId);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_SELLER_ORDER_LIST;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }
    //搜索店铺
    public void SearchShops(final String shop) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&shop="+shop;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.SearchShops,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEARCH_SHOPS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //搜索店铺_本地
    public void SearchShops(final String shop,final String address) {
        new Thread() {
            Message msg = new Message();
            @Override
            public void run() {
                String data = "&shop="+shop+"&address="+address;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.SearchShops,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEARCH_SHOPS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //搜索商品
    public void SearchGoods(final String goods) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&goods="+goods;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.SearchGoods,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEARCH_GOODS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //搜索商品_本地
    public void SearchGoods(final String goods,final String address) {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "&goods="+goods+"&address="+address;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.SearchGoods,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEARCH_GOODS;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //e抢购
    public void IsE() {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.IsE,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.IsE;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //e抢购
    public void IsPrice() {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.IsPrice,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.IsPrice;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //新客专享
    public void IsNew() {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.IsNew,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.IsNew;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //猜你喜欢
    public void IsLike() {
        new Thread() {
            Message msg = new Message();

            @Override
            public void run() {
                String data = "";
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.IsLike,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.IsLike;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品简介
    public void GetGoodsComments(final String goodsid){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&goodsid="+goodsid;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GetGoodsComments,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GetGoodsComments;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品简介
    public void GetGoodPropertyList(final String type){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&goodstype="+type;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GetGoodPropertyList,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GetGoodPropertyList;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //添加产品附加属性
    public void AddGoodsProperty(final String goodsid,final String type , final String propertylist){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&goodsid="+goodsid+"&type="+type+"&propertylist="+propertylist;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.AddGoodsProperty,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.AddGoodsProperty;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取商品简介
    public void GetGoodsPropertyList(final String goodsid){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&goodsid="+goodsid;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.GetGoodsPropertyList,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GetGoodsPropertyList;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //发送聊天记录
    public void SendChatData(final String receive_uid,final String content){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&send_uid="+user.getUserId()+"&receive_uid="+receive_uid+"&content="+content;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.SendChatData,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SendChatData;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    // 获取聊天信息（两个人之间的）
    public void xcGetChatData(final String receive_uid){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&send_uid="+user.getUserId()+"&receive_uid="+receive_uid;
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.xcGetChatData,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.xcGetChatData;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    // 获取聊天列表
    public void xcGetChatListData(){
        new Thread(){
            Message msg = Message.obtain();
            @Override
            public void run() {
                String data = "&uid="+user.getUserId();
                Log.e("data=",data);
                String result_data = NetUtil.getResponse(WebAddress.xcGetChatListData,data);
                Log.e("result_data=",result_data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.xcGetChatListData;
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
