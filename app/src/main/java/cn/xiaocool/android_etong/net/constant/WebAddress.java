package cn.xiaocool.android_etong.net.constant;

/**
 * Created by 潘 on 2016/6/21.
 */
public interface WebAddress extends NetBaseConstant {
    /**
     * 发送验证码后缀
     */
    String SEND_CODE = NET_BASE_PREFIX + "a=SendMobileCode";
    /**
     * 注册后缀
    */
    String REGISTER = NET_BASE_PREFIX + "a=AppRegister";
    /**
     * 修改密码
    */
    String FORGETPASSWORD = NET_BASE_PREFIX + "a=forgetpwd";
    /**
    * 登录
    */
    String LOGIN = NET_BASE_PREFIX+ "a=applogin";
    /**
       * 获取个人资料
       */
    String GETUSERINFO = NET_BASE_PREFIX +"a=getuserinfo";

    /**
    *上传头像
    */
    String UPLOADAVATAR = NET_BASE_PREFIX +"a=uploadavatar";
    /**
    *修改头像资料
    */
    String UPLOADUSERAVATAR = NET_BASE_PREFIX + "a=UpdateUserAvatar";
    /**
    * 修改昵称
    */
    String UPDATAUSERNAME =NET_BASE_PREFIX + "a=UpdateUserName";
   /**
   * 修改性别
   */
    String UPDATAUSERSEX = NET_BASE_PREFIX + "a=UpdateUserSex";
    /**
    * 修改手机
    */
    String UPDATAUSERPHONE = NET_BASE_PREFIX+"a=UpdateUserPhone";

    /**
    * 创建店铺
    */
    String CREATESHOP = NET_BASE_PREFIX +"a=CreateShop";
    /**
    * 下载头像
    */
    String GETAVATAR = "http://mwn.xiaocool.net/uploads/microblog/";

    /**
    * 获取我的店铺
    */

    String GETMYSHOP = NET_BASE_PREFIX + "a=GetMyShop";
    /**
    *发布商品
    */

    String PUBLISHGOODS = NET_BASE_PREFIX + "a=PublishGoods";

    /**
    *获取上架产品列表
    */

    String GETSHOPGOODLIST = NET_BASE_PREFIX + "a=GetShopGoodList";

    /**
    *获取下架产品列表
    */

    String GETSHOPGOODLIST_XIAJIA = NET_BASE_PREFIX + "a=GetShopGoodList";

    /**
    * 下架产品
    */

    String GOODSXIAJIA = NET_BASE_PREFIX + "a=GoodsXiajia";

    /**
    * 上架产品
    */
    String GOODSSHANGJIA = NET_BASE_PREFIX + "a=GoodsShangjia";

    /**
    * 删除产品
    */

    String DELETEGOODS = NET_BASE_PREFIX +"a=DeleteGoods";

    /**
    *修改店铺地址
     *
    */

    String UPDATASHOPADDRESS = NET_BASE_PREFIX + "a=UpdateShopAddress";

    /*
    * 修改店铺头像
    */

    String UPDATESHOPPHOTO = NET_BASE_PREFIX + "a=UpdateShopPhoto";

    /**
    * 修改店铺名称
    */

    String UPDATASHOPNAME = NET_BASE_PREFIX +"a=UpdateShopName";

    /*
    *修改店铺地址
    */

    String UPDATA_SHOP_ADDRESS = NET_BASE_PREFIX + "a=UpdateShopAddress";

    /**
    * 修改商品详情信息
    */

    String CHANGE_GOOD_INTRO = NET_BASE_PREFIX +"a=GetGoodsInfo";
    /**
     * 修改商品详情单条信息
     */

    String CHANGE_GOOD_INTRO_ITEM = NET_BASE_PREFIX;
    /**
     * 收藏商品
     */

    String LIKE_GOOD = NET_BASE_PREFIX + "a=addfavorite";
    /**
     * 取消收藏商品
     */

    String CANCLE_LIKE_GOOD = NET_BASE_PREFIX + "a=cancelfavorite";
    /**
     * 我收藏的宝贝
     */
    String MY_LIKE_GOOD = NET_BASE_PREFIX + "a=getfavoritelist";
    /**
     * 我收藏的店铺
     *
     */
    String MY_LIKE_SHOP = NET_BASE_PREFIX + "a=getfavoritelist";

    /**
    *获取单个商品详情
    */

    String GET_GOODS_INFO = NET_BASE_PREFIX + "a=GetGoodsInfo";

    /**
    *获取单个店铺详情
    */

    String GET_SHOP_INFO = NET_BASE_PREFIX + "a=GetShopInfo";

    /**
    *商城产品购买
    */

    String BOOKING_SHOPPING = NET_BASE_PREFIX + "a=bookingshopping";

    /**
    * 获取我的商城订单列表
    */

    String GETSHOPPINGORDERLIST = NET_BASE_PREFIX + "a=getshoppingorderlist";
    /**
     * 获取首页新品上市
     */
    String GET_NEW_ARRIVAL = NET_BASE_PREFIX + "a=GetHotGoodList";
    /**
     * 添加商品附加属性
     */
    String UPLOAD_GOOD_STANDARD = NET_BASE_PREFIX + "a=AddGoodsProperty";
    /**
     * 获取商品对应的附加属性列表
     */
    String GOOD_ATTACHED_PROPERTY = NET_BASE_PREFIX + "a=GetGoodPropertyList";

    /*
    * 获取店铺列表
    */

    String GET_SHOP_LIST = NET_BASE_PREFIX + "a=GetShopList";

    /*
    * 添加购物车
    */

    String  ADD_SHOPPING_CART = NET_BASE_PREFIX + "a=AddShoppingCart";

    /*
    *获取我的购物车
    */

    String GET_SHOPPPING_CART = NET_BASE_PREFIX + "a=GetShoppingCart";
}


