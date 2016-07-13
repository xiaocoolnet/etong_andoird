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
    /*
    *修改密码
    */
    String FORGETPASSWORD = NET_BASE_PREFIX + "a=forgetpwd";
    /*
    * 登录
    */
    String LOGIN = NET_BASE_PREFIX+ "a=applogin";
    /*
       * 获取个人资料
       */
    String GETUSERINFO = NET_BASE_PREFIX +"a=getuserinfo";

    /*
    *上传头像
    */
    String UPLOADAVATAR = NET_BASE_PREFIX +"a=uploadavatar";
    /*
    *修改头像资料
    */
    String UPLOADUSERAVATAR = NET_BASE_PREFIX + "a=UpdateUserAvatar";
    /*
    * 修改昵称
    */
    String UPDATAUSERNAME =NET_BASE_PREFIX + "a=UpdateUserName";
   /*
   * 修改性别
   */
    String UPDATAUSERSEX = NET_BASE_PREFIX + "a=UpdateUserSex";
    /*
    * 修改手机
    */
    String UPDATAUSERPHONE = NET_BASE_PREFIX+"a=UpdateUserPhone";

    /*
    * 创建店铺
    */
    String CREATESHOP = NET_BASE_PREFIX +"a=CreateShop";
    /*
    * 下载头像
    */
    String GETAVATAR = "http://mwn.xiaocool.net/uploads/microblog/";

    /*
    * 获取我的店铺
    */

    String GETMYSHOP = NET_BASE_PREFIX + "a=GetMyShop";
}
