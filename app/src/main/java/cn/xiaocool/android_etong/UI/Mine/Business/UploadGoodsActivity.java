package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.app.text.City;
import cn.xiaocool.android_etong.app.text.District;
import cn.xiaocool.android_etong.app.text.Provence;
import cn.xiaocool.android_etong.dao.CommunalInterfaces;
import cn.xiaocool.android_etong.net.constant.request.MainRequest;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/6.
 */
public class UploadGoodsActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private RelativeLayout rl_back,rl_carousel_pic;
    private EditText et_biaoti,et_pinpai,et_guige,et_huohao,et_yunfei,et_fahuodi,et_xiangqing;
    private TextView tx_goods_upload;
    private ProgressDialog progressDialog;
    private String show;
    private String shopid;
    private List<Provence> provences;
    private Provence provence;
    private City city;
    private static int visable = 0;
    ArrayAdapter<Provence> adapter01;
    ArrayAdapter<City>adapter02;
    ArrayAdapter<District> adapter03;
    private Spinner spinner01, spinner02, spinner03;
    private String result_data;
    private String picname1,picname2,picname3;
    private String pic_path1,pic_path2,pic_path3;
    private String biaoti,pinpai,huohao,guige,yunfei,fahuodi,xiangqing;
    private int state=0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 1:
                    Log.e("222","222");
                    try{
                        JSONObject jsonObject = (JSONObject)msg.obj;
                        Log.e("111", "111");
                        JSONArray array_provence = jsonObject.getJSONArray("data");
                        for(int i = 0 ;i<array_provence.length();i++){
                            Provence p = new Provence();
                            JSONObject object_provence = array_provence.getJSONObject(i);
                            p.setName(object_provence.getString("name"));
//                            p.setId(object_provence.getString("id"));
                            Log.e("省",p.getName());
                            JSONArray array_city = object_provence.getJSONArray("childlist");
                            List<City> l_c = new ArrayList<City>();
                            for (int j = 0 ; j<array_city.length();j++){
                                City c = new City();
                                JSONObject object_city = array_city.getJSONObject(j);
                                c.setName(object_city.getString("name"));
//                                c.setId(object_city.getString("id"));
                                Log.e("市",c.getName());
                                JSONArray array_district = object_city.getJSONArray("childlist");
                                List<District> l_d = new ArrayList<District>();
                                for (int l = 0 ; l<array_district.length(); l++){
                                    District d = new District();
                                    JSONObject object_district = array_district.getJSONObject(l);
                                    d.setName(object_district.getString("name"));
//                                    d.setId(object_district.getString("id"));
                                    Log.e("区", d.getName());
                                    l_d.add(d);
                                }
                                c.setDistricts(l_d);
                                l_c.add(c);
                            }
                            p.setCitys(l_c);
                            provences.add(p);
                            adapter01 = new ArrayAdapter<Provence>(mContext, R.layout.spinner_list_item, provences);
//                            adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner01.setAdapter(adapter01);
                            spinner01.setSelection(0, true);

                            adapter02 = new ArrayAdapter<City>(mContext,
                                    R.layout.spinner_list_item, provences.get(0)
                                    .getCitys());
                            spinner02.setAdapter(adapter02);
                            spinner02.setSelection(0, true);

                            adapter03 = new ArrayAdapter<District>(mContext,
                                   R.layout.spinner_list_item, provences.get(0)
                                    .getCitys().get(0).getDistricts());
                            spinner03.setAdapter(adapter03);
                            spinner03.setSelection(0, true);
                            Log.e("list", String.valueOf(provences.size()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommunalInterfaces.PUBLISHGOODS:
                    Log.e("success","publish");
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        String data = jsonObject.getString("data");
                        if (status.equals("success")){
                            progressDialog.dismiss();
                            Toast.makeText(mContext,"上传成功",Toast.LENGTH_SHORT).show();
                            Log.e("success","publish");
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, data,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_uploadgoods);
        mContext=this;
        Intent intent = getIntent();
        shopid=intent.getStringExtra("shopid");
        Log.e("shopid=", shopid);
        progressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
        initview();
        initDatas();
        Log.e("解析完毕", "JSON");
        setonclick();

    }

    private void initview() {
        provences = new ArrayList<>();
        spinner01 = (Spinner) findViewById(R.id.spinner01);
        spinner02 = (Spinner) findViewById(R.id.spinner02);
        spinner03 = (Spinner) findViewById(R.id.spinner03);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_carousel_pic = (RelativeLayout) findViewById(R.id.rl_carousel_pic);
        rl_carousel_pic.setOnClickListener(this);
        //输入信息
        et_biaoti = (EditText) findViewById(R.id.et_biaoti);
        et_pinpai = (EditText) findViewById(R.id.et_pinpai);
        et_huohao = (EditText) findViewById(R.id.et_huohao);
        et_guige = (EditText) findViewById(R.id.et_guige);
        et_yunfei = (EditText) findViewById(R.id.et_yunfei);
        et_fahuodi = (EditText) findViewById(R.id.et_fahuodi);
        et_xiangqing = (EditText) findViewById(R.id.et_xiangqing);
        tx_goods_upload = (TextView) findViewById(R.id.tx_goods_upload);
        tx_goods_upload.setOnClickListener(this);
    }

    private void initDatas(){
        if(NetUtil.isConnnected(this)){
            new Thread() {
                Message msg = Message.obtain();
                @Override
                public void run() {
                    try {
//                        result_data = NetUtil.getResponse("http://www.xiaocool.net/index.php?g=apps&m=index&a=getcitylist", "");
                        result_data = NetUtil.getResponse("http://mwn.xiaocool.net/index.php?g=apps&m=index&a=getShopTypeList&type=0", "");
                        Log.e("announcement", result_data);
                        JSONObject jsonObject = new JSONObject(result_data);
                        msg.obj = jsonObject;
                        msg.what = 1;
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        handler.sendMessage(msg);
                    }
                }
            }.start();

        }else{
            Log.e("net", "is not open");
        }
    }

    private void setonclick() {
        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                provence = provences.get(position);
                adapter02 = new ArrayAdapter<City>(UploadGoodsActivity.this,
                        R.layout.spinner_list_item, provences.get(
                        position).getCitys());
                spinner02.setAdapter(adapter02);
                spinner02.setSelection(0, true);
//				visable = 1;
//				//设置自动触发
//				spinner02.performClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinner02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                city = provence.getCitys().get(position);
                adapter03 = new ArrayAdapter<District>(UploadGoodsActivity.this,
                        R.layout.spinner_list_item, provence.getCitys().get(position)
                        .getDistricts());
                spinner03.setAdapter(adapter03);
//                spinner03.setSelection(0, true);
//				if(visable==1){
//
//				}else {
//					spinner03.performClick();
//				}
//				visable=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spinner03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = city.getDistricts().get(position).toString();
                Log.e("show=",show);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_carousel_pic:
                Intent intent = new Intent();
                intent.setClass(mContext, CarouselPicActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.tx_goods_upload:
                uploads();
                break;
        }
    }

    private void uploads() {
        biaoti = et_biaoti.getText().toString();
        pinpai = et_pinpai.getText().toString();
        huohao = et_huohao.getText().toString();
        guige = et_guige.getText().toString();
        yunfei = et_yunfei.getText().toString();
        fahuodi = et_fahuodi.getText().toString();
        xiangqing = et_xiangqing.getText().toString();
        if (state==1){
            if (!TextUtils.isEmpty(biaoti)){
                if (!TextUtils.isEmpty(pinpai)){
                    if (!TextUtils.isEmpty(huohao)){
                        if (!TextUtils.isEmpty(guige)){
                            if (!TextUtils.isEmpty(yunfei)){
                                if (!TextUtils.isEmpty(fahuodi)){
                                    if (!TextUtils.isEmpty(xiangqing)){
                                        if (!TextUtils.isEmpty(show)){
                                            if(NetUtil.isConnnected(mContext)){
                                                progressDialog.setMessage("正在上传");
                                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                progressDialog.show();
                                                new MainRequest(mContext,handler).publishgoods(shopid,picname1,picname2,picname3,
                                                        biaoti,show,pinpai,yunfei,xiangqing,fahuodi,guige,huohao);
                                            }else {
                                                Toast.makeText(mContext,"请检查网络",Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(mContext,"请选择分类",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(mContext,"请输入宝贝详情",Toast.LENGTH_SHORT).show();
                                        et_xiangqing.requestFocus();
                                    }
                                }else {
                                    Toast.makeText(mContext,"请输入发货地",Toast.LENGTH_SHORT).show();
                                    et_fahuodi.requestFocus();
                                }
                            }else {
                                Toast.makeText(mContext,"请输入运费",Toast.LENGTH_SHORT).show();
                                et_yunfei.requestFocus();
                            }
                        }else {
                            Toast.makeText(mContext,"请输入规格",Toast.LENGTH_SHORT).show();
                            et_guige.requestFocus();
                        }
                    }else {
                        Toast.makeText(mContext,"请输入货号",Toast.LENGTH_SHORT).show();
                        et_huohao.requestFocus();
                    }
                }else {
                    Toast.makeText(mContext,"请输入品牌",Toast.LENGTH_SHORT).show();
                    et_pinpai.requestFocus();
                }
            }else {
                Toast.makeText(mContext,"请输入标题",Toast.LENGTH_SHORT).show();
                et_biaoti.requestFocus();
            }
        }else {
            Toast.makeText(mContext,"请传入至少一张照片",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            Log.e("success",data.getStringExtra("1111"));
            state = data.getIntExtra("state",1);
            pic_path1 = data.getStringExtra("pic_path1");
            Log.e("pic_path1=",pic_path1);
            pic_path2 = data.getStringExtra("pic_path2");
            Log.e("pic_path2=",pic_path2);
            pic_path3 = data.getStringExtra("pic_path3");
            Log.e("pic_path3=",pic_path3);
            picname1 = data.getStringExtra("picname1");
            Log.e("picname1",picname1);
            picname2 = data.getStringExtra("picname2");
            Log.e("picname2",picname2);
            picname3 = data.getStringExtra("picname3");
            Log.e("picname3",picname3);
            if(pic_path1==null||pic_path1.equals("")){
                pic_path1="";
            }if(pic_path2==null||pic_path2.equals("")){
                pic_path2="";
            }if(pic_path3==null||pic_path3.equals("")){
                pic_path3="";
            }if(picname1==null||picname1.equals("")){
                picname1="";
            }else {
                picname1 = picname1+".jpg";
            }if(picname2==null||picname2.equals("")){
                picname2="";
            }else {
                picname2= picname2+".jpg";
            }if(picname3==null||picname3.equals("")){
                picname3="";
            }else {
                picname3 = picname3+".jpg";
            }
        }
    }
}



//此处为XML文件加载数据函数  调用函数可直接加载

    //	public List<Provence> getProvinces() throws XmlPullParserException,
//			IOException {
//		List<Provence> provinces = null;
//		Provence province = null;
//		List<City> citys = null;
//		City city = null;
//		List<District> districts = null;
//		District district = null;
//		Resources resources = getResources();
//
//		InputStream in = resources.openRawResource(R.raw.citys_weather);
//
//		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlPullParser parser = factory.newPullParser();
//
//		parser.setInput(in, "utf-8");
//		int event = parser.getEventType();
//		while (event != XmlPullParser.END_DOCUMENT) {
//			switch (event) {
//			case XmlPullParser.START_DOCUMENT:
//				provinces = new ArrayList<Provence>();
//				break;
//			case XmlPullParser.START_TAG:
//				String tagName = parser.getName();
//				if ("p".equals(tagName)) {
//					province = new Provence();
//					citys = new ArrayList<City>();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("p_id".equals(attrName))
//							province.setId(attrValue);
//					}
//				}
//				if ("pn".equals(tagName)) {
//					province.setName(parser.nextText());
//				}
//				if ("c".equals(tagName)) {
//					city = new City();
//					districts = new ArrayList<District>();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("c_id".equals(attrName))
//							city.setId(attrValue);
//					}
//				}
//				if ("cn".equals(tagName)) {
//					city.setName(parser.nextText());
//				}
//				if ("d".equals(tagName)) {
//					district = new District();
//					int count = parser.getAttributeCount();
//					for (int i = 0; i < count; i++) {
//						String attrName = parser.getAttributeName(i);
//						String attrValue = parser.getAttributeValue(i);
//						if ("d_id".equals(attrName))
//							district.setId(attrValue);
//					}
//					district.setName(parser.nextText());
//					districts.add(district);
//				}
//				break;
//			case XmlPullParser.END_TAG:
//				if ("c".equals(parser.getName())) {
//					city.setDistricts(districts);
//					citys.add(city);
//				}
//				if ("p".equals(parser.getName())) {
//					province.setCitys(citys);
//					provinces.add(province);
//				}
//
//				break;
//
//			}
//			event = parser.next();
//
//		}
//		return provinces;
//	}