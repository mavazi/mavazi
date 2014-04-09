package com.mavazi;

import java.io.File;



import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

public class ItemsTwo extends Activity implements OnItemClickListener{

	File file;
	private String[] FilePathStrings;
	private String[] FileNameStrings;
	private File[] listFile;
	Gallery gallery;
	ImageAdapter adapter;
	HorizontalScrollView hsv;
	ImageView iv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_items);

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
					.show();
		} else {

			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "bags");
			
			
			file.mkdirs();
		}

		if (file.isDirectory()) {
			listFile = file.listFiles();
			FilePathStrings = new String[listFile.length];
			FileNameStrings = new String[listFile.length];

			// Loop through each image, get path and name
			for (int i = 0; i < listFile.length; i++) {
				FilePathStrings[i] = listFile[i].getAbsolutePath();
				FileNameStrings[i] = listFile[i].getName();
			}
		}

		//gallery = (Gallery) findViewById(R.id.gridview);
		//hsv = (HorizontalScrollView) findViewById(R.id.hsv);
	
		
        
		adapter = new ImageAdapter(this, FilePathStrings, FileNameStrings);
		gallery.setSpacing(2);
		gallery.setAdapter(adapter); // unable to set the adapter to gallery for
										// population
		gallery.setOnItemClickListener(this);
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		zoomImage(arg2);
	}

	private void zoomImage(int arg2) {
		final Dialog dialog = new Dialog(ItemsTwo.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(R.layout.zoom_zoom);
		ImageView imageview = (ImageView) dialog.findViewById(R.id.imageView1);
		Bitmap bmp = BitmapFactory.decodeFile(FilePathStrings[arg2]);
		imageview.setImageBitmap(bmp);
		dialog.show();
		
	}
}
