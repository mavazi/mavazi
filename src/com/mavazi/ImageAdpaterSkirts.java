package com.mavazi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdpaterSkirts extends BaseAdapter {

	// Declare variables
	//private Activity activity;
	private Context context;
	private String[] filepath;
	private String[] filename;

	private static LayoutInflater inflater = null;

	public ImageAdpaterSkirts (Context con, String[] fpath, String[] fname) {
		context = con;
		filepath = fpath;
		filename = fname;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
///get count of items in file path.....reading file empty
	public int getCount() {
		return filepath.length;

	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		ImageView i = new ImageView(context);
		Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);
		
		try{
		
		// i.setImageResource(mImageIds[position]);
		i.setLayoutParams(new Gallery.LayoutParams(100,100));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setImageBitmap(bmp);
		}catch (OutOfMemoryError e) {
			System.gc();
			e.printStackTrace();
			if (bmp != null && bmp.isRecycled()) {
				bmp.recycle();
				bmp = null;
			}
                    }
		return i;
	}
}