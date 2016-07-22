package cn.xiaocool.android_etong.dao;

/**
 * Created by 潘 on 2016/6/21.
 */
public interface CommunalInterfaces {
    //main number 0x0000开始
    int SEND_CODE = 0x0000;//发送验证码
    int BTN_UNTOUCH = 0x0001;//发送验证码按钮不可点击
    int BTN_TOUCH = 0x0002;//发送验证码按钮可点击
    int REGISTER = 0x0003;//注册
    int FORGETPASSWORD = 0x0004;//忘记密码
    int LOGIN = 0x0005;//登录
    //首页商城(0x0100开始)
    int LIKE_GOOD = 0x0100;//收藏商城商品
    int CANCLE_LIKE_GOOD = 0x0101;//取消收藏商城商品

    //修改资料
    int UPLOADAVATAR = 0x0006;//上传头像
    int UPUSERAVATAR = 0x0007;//修改用户头像资料
    int GETUSERINFO = 0x0008;//获取用户基本资料
    int UPDATAUSERNAME = 0x0009;//修改姓名
    int UPDATAUSERSEX = 0x0010;//修改性别
    int UPDATAUSERPHONE =0x0011;//修改手机号

    //商家
    int AUTHENTICATION = 0x0012; //商家身份认证
    int POSITIVE_PIC = 0x0013; //身份证前面照片
    int OPPOSITE_PIC = 0x0014 ; //身份证后面照片
    int LICENSE_PIC = 0x0015; //营业执照照片
    int CREATESHOP = 0x0016;//创建店铺
    int GETMYSHOP = 0x0017;//获取我的店铺
    int PUBLISHGOODS = 0x0018;//发布商城
    int GETSHOPGOODLIST = 0x0019;//获取商城商品——上架
    int GETSHOPGOODLIST_XIAJIA = 0x0020;//获取商城商品——下架
    int GOODSXIAJIA = 0x0021;//下架
    int GOODSSHANGJIA = 0x0022;//上架
    int DELETEGOODS = 0x0023;//删除产品
    int UPDATASHOPADDRESS = 0x0024;//修改商铺地址(头像)
    int UPDATASHOPNAME = 0x0025;//修改店铺名称
    int CHANGE_GOOD_INTRO = 0x0026;//修改宝贝详情
    int CHANGE_GOOD_INTRO_ITEM = 0x0027;//修改宝贝详情单条信息
    int GET_GOODS_INFO = 0x0028;//获取单个商品详情
    int GET_SHOP_INFO = 0x0029;//获取单个店铺详情
    int BOOKING_SHOPPING = 0x0030;//商城产品购买
    int GET_SHOPPING_ORDER_LIST = 0x0031;// 获取我的商城订单列表
    int MY_LIKE_GOOD = 0x0028;//我喜欢的宝贝
    int MY_LIKE_SHOP = 0x0029;//我喜欢的商店
}
