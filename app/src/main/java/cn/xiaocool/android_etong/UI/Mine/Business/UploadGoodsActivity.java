package cn.xiaocool.android_etong.UI.Mine.Business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.android_etong.R;
import cn.xiaocool.android_etong.app.text.City;
import cn.xiaocool.android_etong.app.text.District;
import cn.xiaocool.android_etong.app.text.Provence;
import cn.xiaocool.android_etong.util.NetUtil;

/**
 * Created by 潘 on 2016/7/6.
 */
public class UploadGoodsActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private List<Provence> provences;
    private RelativeLayout rl_back;
    private Provence provence;
    private static int visable = 0;
    ArrayAdapter<Provence> adapter01;
    ArrayAdapter<City>adapter02;
    ArrayAdapter<District> adapter03;
    private Spinner spinner01, spinner02, spinner03;
    private String result_data;
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
                            p.setId(object_provence.getString("id"));
                            Log.e("省",p.getName());
                            JSONArray array_city = object_provence.getJSONArray("c");
                            List<City> l_c = new ArrayList<City>();
                            for (int j = 0 ; j<array_city.length();j++){
                                City c = new City();
                                JSONObject object_city = array_city.getJSONObject(j);
                                c.setName(object_city.getString("name"));
                                c.setId(object_city.getString("id"));
                                Log.e("市",c.getName());
                                JSONArray array_district = object_city.getJSONArray("a");
                                List<District> l_d = new ArrayList<District>();
                                for (int l = 0 ; l<array_district.length(); l++){
                                    District d = new District();
                                    JSONObject object_district = array_district.getJSONObject(l);
                                    d.setName(object_district.getString("name"));
                                    d.setId(object_district.getString("id"));
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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_uploadgoods);
        mContext=this;
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
    }

    private void initDatas(){
        if(NetUtil.isConnnected(this)){
            new Thread() {
                Message msg = Message.obtain();
                @Override
                public void run() {
                    try {
                        result_data = NetUtil.getResponse("http://www.xiaocool.net/index.php?g=apps&m=index&a=getcitylist", "");
                        Log.e("announcement", result_data);
                        JSONObject jsonObject = new JSONObject(result_data);
                        msg.obj = jsonObject;
                        msg.what = 1;
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        handler.sendMessage(msg);
                    }
                    ;
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
                adapter03 = new ArrayAdapter<District>(UploadGoodsActivity.this,
                        R.layout.spinner_list_item, provence.getCitys().get(position)
                        .getDistricts());
                spinner03.setAdapter(adapter03);
                spinner03.setSelection(0, true);
//				if(visable==1){
//
//				}else {
//					spinner03.performClick();
//				}
//				visable=0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
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
