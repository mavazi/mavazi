package com.mavazi;

import java.io.File;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OutfitsActivity extends Activity implements OnClickListener {

	Button bags, shoes, trousers, tops;
	File fileS;
	private String[] FilePathStrings;
	private String[] FileNameStrings;
	private File[] listFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outfits);
		bridge();
		
	}

	private void bridge() {
		// TODO Auto-generated method stub
		bags = (Button) findViewById(R.id.btbags);
		shoes = (Button) findViewById(R.id.btshoes);
		trousers = (Button) findViewById(R.id.bttrousers);
		tops = (Button) findViewById(R.id.bttops);
		bags.setOnClickListener(this);
		shoes.setOnClickListener(this);
		trousers.setOnClickListener(this);
		tops.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.outfits, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btbags:
			Toast.makeText(getApplicationContext(), "Bags", 10000).show();
			break;
		case R.id.btshoes:
			Toast.makeText(getApplicationContext(), "Shoes", 10000).show();
			fileS = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "shoes");
			fileS.mkdirs();
			
			if (fileS.isDirectory()) {
				listFile = fileS.listFiles();
				FilePathStrings = new String[listFile.length];
				FileNameStrings = new String[listFile.length];

				// Loop through each image, get path and name
				for (int i = 0; i < listFile.length; i++) {
					FilePathStrings[i] = listFile[i].getAbsolutePath();
					FileNameStrings[i] = listFile[i].getName();
				}
			}
			
			
			
			break;
		case R.id.bttrousers:
			Toast.makeText(getApplicationContext(), "Pants", 10000).show();
			break;
		case R.id.bttops:
			Toast.makeText(getApplicationContext(), "Tops", 10000).show();
			break;
		}

	}
	View insertPhoto(String path){
	     Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);
	     
	    LinearLayout layout = new LinearLayout(getApplicationContext());
	     layout.setLayoutParams(new LayoutParams(250, 250));
	     layout.setGravity(Gravity.CENTER);
	     
	     ImageView imageView = new ImageView(getApplicationContext());
	     //imageView.setLayoutParams(new LayoutParams(220, 220));
	     imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	     imageView.setImageBitmap(bm);
	     
	     layout.addView(imageView);
	     return layout;
	    }
	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
	     Bitmap bm = null;
	     
	     // First decode with inJustDecodeBounds=true to check dimensions
	     final BitmapFactory.Options options = new BitmapFactory.Options();
	     options.inJustDecodeBounds = true;
	     BitmapFactory.decodeFile(path, options);
	     
	     // Calculate inSampleSize
	     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	     
	     // Decode bitmap with inSampleSize set
	     options.inJustDecodeBounds = false;
	     bm = BitmapFactory.decodeFile(path, options); 
	     
	     return bm;  
	    }
	    
	    
	    public int calculateInSampleSize(
	      
	     BitmapFactory.Options options, int reqWidth, int reqHeight) {
	     // Raw height and width of image
	     final int height = options.outHeight;
	     final int width = options.outWidth;
	     int inSampleSize = 1;
	        
	     if (height > reqHeight || width > reqWidth) {
	      if (width > height) {
	       inSampleSize = Math.round((float)height / (float)reqHeight);   
	      } else {
	       inSampleSize = Math.round((float)width / (float)reqWidth);   
	      }   
	     }
	     
	     return inSampleSize;
	    }
}
