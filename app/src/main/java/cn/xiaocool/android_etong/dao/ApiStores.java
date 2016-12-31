package cn.xiaocool.android_etong.dao;

import cn.xiaocool.android_etong.bean.ActivityRegisterBean;
import cn.xiaocool.android_etong.bean.CityBBSBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static cn.xiaocool.android_etong.net.constant.NetBaseConstant.MIDDLE;

/**
 * Created by hzh on 2016/12/31.
 */

public interface ApiStores {

    //        @GET("adat/sk/{cityId}.html")
//        Call<CityBBSBean> getWeather(@Path("type") String type);
    //获取e专区bbs列表
    @GET(MIDDLE + "getbbspostlist")
    Call<CityBBSBean> getBBSList(@Query("type") String type);

    @GET(MIDDLE + "getActivityList")
    Call<ActivityRegisterBean> getActivityRegister(@Query("userid") String userId);
}
