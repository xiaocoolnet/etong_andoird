package cn.xiaocool.android_etong.adapter.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.xiaocool.android_etong.R;


public class ViewFactory {


	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
