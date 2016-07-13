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
}
