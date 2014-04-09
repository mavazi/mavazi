package com.mavazi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;



import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class LoadActivity extends TabActivity implements OnTabChangeListener, OnClickListener{

	//this activity sets up the tabs that will appear in each activity
	
	TabHost tabHost;
	ImageButton imb_camera;
	final static int captureResult = 1001;
	Bitmap bmp;

	String dest_photo_dir;
	String src_photo_file;
	String photoFName;
	File photoFile;

	String BAG_DIR;
	String SHOE_DIR;
	String TROUSERS_DIR;
	String TOPS_DIR;
	String SKIRTS_DIR;
	
	File fileBags, fileShoes, fileTrousers, fileTops, fileSkirts;

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		imb_camera = (ImageButton) findViewById(R.id.frag_cam_imb_take_photo);
		imb_camera.setOnClickListener(this);
		
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
					.show();
		} else {
			BAG_DIR = Environment.getExternalStorageDirectory()
					+ File.separator + "manguoz/bags";

			SHOE_DIR = Environment.getExternalStorageDirectory()
					+ File.separator + "manguoz/shoes";

			TROUSERS_DIR = Environment.getExternalStorageDirectory()
					+ File.separator + "manguoz/trousers";

			TOPS_DIR = Environment.getExternalStorageDirectory()
					+ File.separator + "manguoz/tops";

			SKIRTS_DIR = Environment.getExternalStorageDirectory()
					+ File.separator + "manguoz/skirts";

			fileBags = new File(BAG_DIR);
			fileBags.mkdirs();

			fileShoes = new File(SHOE_DIR);
			fileShoes.mkdirs();

			fileTrousers = new File(TROUSERS_DIR);
			fileTrousers.mkdirs();

			fileSkirts = new File(SKIRTS_DIR);
			fileSkirts.mkdirs();

			fileTops = new File(TOPS_DIR);
			fileTops.mkdirs();
		}
		//reference the tabhost created in xml
		/* TabHost will have Tabs */
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.setup(this.getLocalActivityManager());
        tabHost.setOnTabChangedListener(this);
        
        
        /* TabSpec used to create a new tab. 
         * By using TabSpec only we can  setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        //should also allow tabspec to create a new tab when the user wants to
        
        // Initialize a TabSpec for each tab 
        //"tis21" is a tag used
        TabSpec tab1 = tabHost.newTabSpec("tid21");
        TabSpec tab2 = tabHost.newTabSpec("tid21");
     //   TabSpec tab3 = tabHost.newTabSpec("tid23");
        
        /* TabSpec setIndicator() is used to set name for the tab. */
        //get drawables for each tab  as well
        tab1.setIndicator("My Closet"); //, getResources().getDrawable(android.R.drawable.ic_input_add));
        tab2.setIndicator("Looks"); //, getResources().getDrawable(android.R.drawable.ic_menu_share));
       // tab3.setIndicator("Want list"); //,getResources().getDrawable(android.R.drawable.bottom_bar));
        /* TabSpec setContent() is used to set content for a particular tab. */
        tab1.setContent(new Intent(this,ItemsActivity.class));
        tab2.setContent(new Intent(this,MainActivity.class));
      //  tab3.setContent(new Intent(this,DeleteData.class));
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
      //  tabHost.addTab(tab3);
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#D4A017"));
        }
        
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#3b3b3b"));
      //  LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
       // setup(mLocalActivityManager);
        
	}
	
	

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		for(int i=0; i<tabHost.getTabWidget().getChildCount(); i++)
        {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#D4A017"));
        } 
				
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#3b3b3b"));
		
	}



	@Override
	public void onClick(View arg0) {
		Toast.makeText(getApplicationContext(), "Tupige picha", 10000).show();

		// use the phone camera to take an image.
		// return a value *which is the image..take a pic and get that data back
		// Intent i = new
		// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// startActivityForResult(i, captureresult);
		// int targetID = v.getId();
		// selectImage(targetID);
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

		Long tsLong = System.currentTimeMillis() / 1000;
		String ts = tsLong.toString() + ".jpg";

		photoFName = ts;
		photoFile = new File(Environment.getExternalStorageDirectory(), ts);
		Uri imageUri = Uri.fromFile(photoFile);
		i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(i, captureResult);
	}

	public void copy(File src, File dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}
		
	private void selectImage() {
		final CharSequence[] items = { "Save Tops","Save Bag", "Save Shoes",
				"Save Trousers", "Save Skirt", "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoadActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				 if (items[item].equals("Save Tops")) {
						dest_photo_dir = TOPS_DIR;
						
				 }else if (items[item].equals("Save Bag")) {
					
					dest_photo_dir = BAG_DIR;
					// take photo from camera

				} else if (items[item].equals("Save Shoes")) {
					dest_photo_dir = SHOE_DIR;
					// take photo from gallery
					// Intent i = new
					// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					// startActivityForResult(i, captureResult);
				} else if (items[item].equals("Save Trousers")) {
					dest_photo_dir = TROUSERS_DIR;

				} else if (items[item].equals("Save Skirt")) {
					dest_photo_dir = SKIRTS_DIR;

				} else if (items[item].equals("cancel")) {
					// close the dialog
					dialog.dismiss();
				}
				File destFile = new File(dest_photo_dir, photoFName);
				try {
					copy(photoFile, destFile);
				} catch (IOException ioe) {
					Log.d("PhotoFile", ioe.toString());
				}
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), "So now..",
					Toast.LENGTH_LONG).show();

			/*
			 * Uri targetUri = data.getData(); imageBags.setImageBitmap(bmp);
			 */

			if (requestCode == captureResult) {

				selectImage();
			} else {

			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_about:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					LoadActivity.this);

			// set title
			alertDialogBuilder.setTitle("About Us");

			// set dialog message
			alertDialogBuilder
					.setMessage(
							"dressApp is your closet managing and outfit planning application. "
									+ "Get to put together great outfits and plan what to wear,wherever, whenever.")
					.setCancelable(false)

					.setNegativeButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			return true;

		case R.id.menu_share:

			AlertDialog.Builder alertDialogBuilderShare = new AlertDialog.Builder(
					LoadActivity.this);

			// set title
			alertDialogBuilderShare.setTitle("Share");

			// set dialog message
			alertDialogBuilderShare
					.setMessage(
							"Soon you will be able to share your favourite outfits with your friends and get their feedback."
									+ "Tell all your friends about the best looks."
									+ "Stay trendy")
					.setCancelable(false)

					.setNegativeButton("Great!",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialogS = alertDialogBuilderShare.create();

			// show it
			alertDialogS.show();

			return true;

		case R.id.menu_buy:
			AlertDialog.Builder alertDialogBuilderbuy = new AlertDialog.Builder(
					LoadActivity.this);

			// set title
			alertDialogBuilderbuy.setTitle("Shop");

			// set dialog message
			alertDialogBuilderbuy
					.setMessage(
							"Keep it fresh and upgrade your fashion consciousness by shopping from your favourite brands."
									+ "Discover great looks, best styles and buy here."
									+ "Coming Soon")
					.setCancelable(false)

					.setNegativeButton("Cant wait!",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialogbuy = alertDialogBuilderbuy.create();

			// show it
			alertDialogbuy.show();

			return true;
		case R.id.menu_help:
			AlertDialog.Builder alertDialogBuilderHelp = new AlertDialog.Builder(
					LoadActivity.this);

			// set title
			alertDialogBuilderHelp.setTitle("Help");

			// set dialog message
			alertDialogBuilderHelp
					.setMessage(
							"1. Take photos of items in your closet and save them to the appropriate category e.g Bag.                                      " +
							"2. Put together outfits by viewing how items look together. Click on the 'Outfits' Tab and select from images in gallery                       " +
							"3. Tell all your friends about it :-)")
					.setCancelable(false)

					.setNegativeButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialogHelp = alertDialogBuilderHelp.create();

			// show it
			alertDialogHelp.show();

			return true;

		case R.id.menu_exit:
			AlertDialog.Builder alertDialogBuilderExit = new AlertDialog.Builder(
					LoadActivity.this);

			// set title
			alertDialogBuilderExit.setTitle("Exit");

			// set dialog message
			alertDialogBuilderExit
					.setMessage("Click Yes to exit app!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							LoadActivity.this.finish();
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
			AlertDialog alertDialogExit = alertDialogBuilderExit.create();

			// show it
			alertDialogExit.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
