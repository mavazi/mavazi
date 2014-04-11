package com.mavazi;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class ItemsActivity extends Activity {

	LinearLayout layoutBags, layoutShoes, layoutTrousers, layoutTops,
			layoutSkirts;
	int imageWidth;
	ScrollView scv;
	LayoutParams params;
	private String state;
	boolean canW = false, canR = false;
	File fileBags, fileShoes, fileTrousers, fileTops, fileSkirts;
	private String[] FilePathStrings;
	private String[] FilePathStringsTops;
	private String[] FilePathStringsTrousers;
	private String[] FilePathStringsSkirts;
	private String[] FilePathStringsS;

	private String[] FileNameStrings;
	private String[] FileNameStringsTops;
	private String[] FileNameStringsTrousers;
	private String[] FileNameStringsS;
	private String[] FileNameStringsSkirts;

	private File[] listFile;
	private File[] listFileTops;
	private File[] listFileTrousers;
	private File[] listFileskirts;
	private File[] listFileS;

	Gallery gallery, galleryShoes, galleryTrousers, galleryTops, gallerySkirts;
	ImageAdapter adapter;
	ImageAdapterShoe adaptershoes;
	ImageAdapterTrouser adapterTrousers;
	ImageAdapterTops adapterTops;
	ImageAdpaterSkirts adapterSkirts;
	final static int captureResult = 1001;
	ImageButton imb_camera;
	Bitmap bmp, bmpS, bmpTr, bmpSk, bmpTop;

	String dest_photo_dir;
	String src_photo_file;
	String photoFName;
	File photoFile;

	String BAG_DIR;
	String SHOE_DIR;
	String TROUSERS_DIR;
	String TOPS_DIR;
	String SKIRTS_DIR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_items);
		layoutBags = (LinearLayout) findViewById(R.id.layoutBags);
		layoutShoes = (LinearLayout) findViewById(R.id.layoutShoes);
		layoutTrousers = (LinearLayout) findViewById(R.id.layoutTrousers);
		layoutTops = (LinearLayout) findViewById(R.id.layoutTops);
		layoutSkirts = (LinearLayout) findViewById(R.id.layoutSkirts);
		galleryShoes = (Gallery) findViewById(R.id.galleryShoes);
		gallerySkirts = (Gallery) findViewById(R.id.gallerySkirts);
		galleryTops = (Gallery) findViewById(R.id.galleryTops);
		scv = (ScrollView) findViewById(R.id.scrollview);

		// imb_camera = (ImageButton)
		// findViewById(R.id.frag_cam_imb_take_photo);
		// imb_camera.setOnClickListener(this);

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

			showBags();
			showShoes();
			showTrousers();
			showTops();
			showSkirts();

			adapter = new ImageAdapter(this, FilePathStrings, FileNameStrings);
			gallery.setSpacing(3);
			gallery.setAdapter(adapter);

			gallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					final Dialog dialog = new Dialog(ItemsActivity.this);
					try {
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.getWindow().setBackgroundDrawableResource(
								android.R.color.transparent);
						dialog.setContentView(R.layout.zoom_zoom);
						ImageView imageview = (ImageView) dialog
								.findViewById(R.id.imageView1);

						Bitmap bmp = BitmapFactory
								.decodeFile(FilePathStrings[arg2]);
						// Bitmap bmpShoe =
						// BitmapFactory.decodeFile(FilePathStringsS[arg2]);
						imageview.setImageBitmap(bmp);
						imageview
								.setOnLongClickListener(new OnLongClickListener() {

									@Override
									public boolean onLongClick(View v) {
										AlertDialog.Builder alertDialogBuilderDelete = new AlertDialog.Builder(
												ItemsActivity.this);

										// set title
										alertDialogBuilderDelete
												.setTitle("Delete Image");

										// set dialog message
										alertDialogBuilderDelete
												.setMessage(
														"Delete this image?")
												.setCancelable(false)
												.setPositiveButton(
														"Yes",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																// if this
																// button is
																// clicked,
																// close
																// current
																// activity
																File toBeDeleted = new File(
																		FilePathStrings[arg2]);

																deleteImage(toBeDeleted);
																;
															}
														})

												.setNegativeButton(
														"No!",
														new DialogInterface.OnClickListener() {
															public void onClick(
																	DialogInterface dialog,
																	int id) {
																// if this
																// button is
																// clicked, just
																// close
																// the dialog
																// box and do
																// nothing
																dialog.cancel();
															}
														});

										// create alert dialog
										AlertDialog alertDialogDelete = alertDialogBuilderDelete
												.create();

										// show it
										alertDialogDelete.show();

										return true;

									}
								});

						dialog.show();
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						if (bmp != null && bmp.isRecycled()) {
							bmp.recycle();
							bmp = null;
						}
					}
				}
			});

			// //////////////
			adaptershoes = new ImageAdapterShoe(this, FilePathStringsS,
					FileNameStringsS);
			galleryShoes.setSpacing(3);
			galleryShoes.setAdapter(adaptershoes);
			galleryShoes.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					final Dialog dialogS = new Dialog(ItemsActivity.this);
					try {
						dialogS.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogS.getWindow().setBackgroundDrawableResource(
								android.R.color.transparent);
						dialogS.setContentView(R.layout.zoom_zoom);
						ImageView imageS = (ImageView) dialogS
								.findViewById(R.id.imageView1);

						Bitmap bmpS = BitmapFactory
								.decodeFile(FilePathStringsS[arg2]);
						imageS.setImageBitmap(bmpS);
						imageS.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								AlertDialog.Builder alertDialogBuilderDelete = new AlertDialog.Builder(
										ItemsActivity.this);

								// set title
								alertDialogBuilderDelete
										.setTitle("Delete Image");

								// set dialog message
								alertDialogBuilderDelete
										.setMessage("Delete this image?")
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, close
														// current activity
														File toBeDeleted = new File(
																FilePathStringsS[arg2]);

														deleteImage(toBeDeleted);
														;
													}
												})

										.setNegativeButton(
												"No!",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, just close
														// the dialog box and do
														// nothing
														dialog.cancel();
													}
												});

								// create alert dialog
								AlertDialog alertDialogDelete = alertDialogBuilderDelete
										.create();

								// show it
								alertDialogDelete.show();

								return true;

							}
						});

						dialogS.show();
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						if (bmpS != null && bmpS.isRecycled()) {
							bmpS.recycle();
							bmpS = null;
						}
					}
				}
			});

			// /////
			adapterTrousers = new ImageAdapterTrouser(this,
					FilePathStringsTrousers, FileNameStringsTrousers);

			galleryTrousers.setSpacing(3);
			galleryTrousers.setAdapter(adapterTrousers);
			galleryTrousers.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					final Dialog dialogTr = new Dialog(ItemsActivity.this);
					try {
						dialogTr.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogTr.getWindow().setBackgroundDrawableResource(
								android.R.color.transparent);
						dialogTr.setContentView(R.layout.zoom_zoom);
						ImageView imageTr = (ImageView) dialogTr
								.findViewById(R.id.imageView1);

						bmpTr = BitmapFactory
								.decodeFile(FilePathStringsTrousers[arg2]);
						// Bitmap bmpShoe =
						// BitmapFactory.decodeFile(FilePathStringsS[arg2]);
						imageTr.setImageBitmap(bmpTr);
						imageTr.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								AlertDialog.Builder alertDialogBuilderD = new AlertDialog.Builder(
										ItemsActivity.this);

								// set title
								alertDialogBuilderD.setTitle("Delete Image");

								// set dialog message
								alertDialogBuilderD
										.setMessage("Delete this image?")
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, close
														// current activity
														File toBeDeleted = new File(
																FilePathStringsTrousers[arg2]);

														deleteImage(toBeDeleted);
														;
													}
												})

										.setNegativeButton(
												"No!",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, just close
														// the dialog box and do
														// nothing
														dialog.cancel();
													}
												});

								// create alert dialog
								AlertDialog alertDialogD = alertDialogBuilderD
										.create();

								// show it
								alertDialogD.show();

								return true;

							}
						});

						dialogTr.show();
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						if (bmpTr != null && bmpTr.isRecycled()) {
							bmpTr.recycle();
							bmpTr = null;
						}
					}
				}
			});

			// ///////
			adapterTops = new ImageAdapterTops(this, FilePathStringsTops,
					FileNameStringsTops);
			galleryTops.setSpacing(3);
			galleryTops.setAdapter(adapterTops);
			galleryTops.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					final Dialog dialogTop = new Dialog(ItemsActivity.this);
					try {
						dialogTop.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogTop.getWindow().setBackgroundDrawableResource(
								android.R.color.transparent);
						dialogTop.setContentView(R.layout.zoom_zoom);
						ImageView imageTop = (ImageView) dialogTop
								.findViewById(R.id.imageView1);

						bmpTop = BitmapFactory
								.decodeFile(FilePathStringsTops[arg2]);
						// Bitmap bmpShoe =
						// BitmapFactory.decodeFile(FilePathStringsS[arg2]);
						imageTop.setImageBitmap(bmpTop);
						imageTop.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								AlertDialog.Builder alertDialogBuilderDel = new AlertDialog.Builder(
										ItemsActivity.this);

								// set title
								alertDialogBuilderDel.setTitle("Delete Image");

								// set dialog message
								alertDialogBuilderDel
										.setMessage("Delete this image?")
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, close
														// current activity
														File toBeDeleted = new File(
																FilePathStringsTops[arg2]);

														deleteImage(toBeDeleted);
														;
													}
												})

										.setNegativeButton(
												"No!",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, just close
														// the dialog box and do
														// nothing
														dialog.cancel();
													}
												});

								// create alert dialog
								AlertDialog alertDialogDel = alertDialogBuilderDel
										.create();

								// show it
								alertDialogDel.show();

								return true;

							}
						});

						dialogTop.show();
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						if (bmpTop != null && bmpTop.isRecycled()) {
							bmpTop.recycle();
							bmpTop = null;
						}
					}
				}
			});

			// //
			adapterSkirts = new ImageAdpaterSkirts(this, FilePathStringsSkirts,
					FileNameStringsSkirts);
			gallerySkirts.setSpacing(3);
			gallerySkirts.setAdapter(adapterSkirts);
			gallerySkirts.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					final Dialog dialogSk = new Dialog(ItemsActivity.this);
					try {
						dialogSk.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialogSk.getWindow().setBackgroundDrawableResource(
								android.R.color.transparent);
						dialogSk.setContentView(R.layout.zoom_zoom);
						ImageView imageSk = (ImageView) dialogSk
								.findViewById(R.id.imageView1);

						Bitmap bmpSk = BitmapFactory
								.decodeFile(FilePathStringsSkirts[arg2]);
						// Bitmap bmpShoe =
						// BitmapFactory.decodeFile(FilePathStringsS[arg2]);
						imageSk.setImageBitmap(bmpSk);
						imageSk.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								AlertDialog.Builder alertDialogBuilderDele = new AlertDialog.Builder(
										ItemsActivity.this);

								// set title
								alertDialogBuilderDele.setTitle("Delete Image");

								// set dialog message
								alertDialogBuilderDele
										.setMessage("Delete this image?")
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, close
														// current activity
														File toBeDeleted = new File(
																FilePathStringsSkirts[arg2]);

														deleteImage(toBeDeleted);
														;
													}
												})

										.setNegativeButton(
												"No!",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														// if this button is
														// clicked, just close
														// the dialog box and do
														// nothing
														dialog.cancel();
													}
												});

								// create alert dialog
								AlertDialog alertDialogDele = alertDialogBuilderDele
										.create();

								// show it
								alertDialogDele.show();

								return true;

							}
						});

						dialogSk.show();
					} catch (OutOfMemoryError e) {

						e.printStackTrace();
						if (bmpSk != null && bmpSk.isRecycled()) {
							bmpSk.recycle();
							bmpSk = null;
						}
					}
				}

			});
		}

	}

	public void deleteImage(File file) {
		if (file.delete()) {
			Log.i("Manguoz", "Image deleted successfully");
		} else {
			Log.e("Manguoz", "Error deleteing image...");
		}
	}

	private void showSkirts() {
		if (fileSkirts.isDirectory()) {
			listFileskirts = fileSkirts.listFiles();
			FilePathStringsSkirts = new String[listFileskirts.length];
			FileNameStringsSkirts = new String[listFileskirts.length];

			// Loop through each image, get path and name
			for (int i = 0; i < listFileskirts.length; i++) {
				FilePathStringsSkirts[i] = listFileskirts[i].getPath();
				FileNameStringsSkirts[i] = listFileskirts[i].getName();
				// Toast.makeText(getApplicationContext(),
				// listFileskirts[i].getPath(), Toast.LENGTH_SHORT).show();
			}
			layoutSkirts.addView(insertSkirts(fileSkirts.getPath()));

		}

	}

	private void showTops() {

		if (fileTops.isDirectory()) {
			listFileTops = fileTops.listFiles();
			FilePathStringsTops = new String[listFileTops.length];
			FileNameStringsTops = new String[listFileTops.length];

			// Loop through each image, get path and name
			for (int i = 0; i < listFileTops.length; i++) {
				FilePathStringsTops[i] = listFileTops[i].getAbsolutePath();
				FileNameStringsTops[i] = listFileTops[i].getName();
			}
			layoutTops.addView(insertTops(fileTops.getAbsolutePath()));

		}

	}

	private void showTrousers() {
		if (fileTrousers.isDirectory()) {
			listFileTrousers = fileTrousers.listFiles();
			FilePathStringsTrousers = new String[listFileTrousers.length];
			FileNameStringsTrousers = new String[listFileTrousers.length];

			// Loop through each image, get path and name
			for (int tt = 0; tt < listFileTrousers.length; tt++) {
				FilePathStringsTrousers[tt] = listFileTrousers[tt]
						.getAbsolutePath();
				FileNameStringsTrousers[tt] = listFileTrousers[tt].getName();
			}

			galleryTrousers = (Gallery) findViewById(R.id.galleryTrousers);
			layoutTrousers.addView(insertTrousers(fileTrousers
					.getAbsolutePath()));

		}

	}

	private void showBags() {
		if (fileBags.isDirectory()) {
			listFile = fileBags.listFiles();
			FilePathStrings = new String[listFile.length];
			FileNameStrings = new String[listFile.length];

			// Loop through each image, get path and name
			for (int i = 0; i < listFile.length; i++) {
				FilePathStrings[i] = listFile[i].getAbsolutePath();
				FileNameStrings[i] = listFile[i].getName();
			}

			gallery = (Gallery) findViewById(R.id.galleryBags);
			layoutBags.addView(insertPhoto(fileBags.getAbsolutePath()));

		}

	}

	private void showShoes() {
		if (fileShoes.isDirectory()) {
			listFileS = fileShoes.listFiles();
			FilePathStringsS = new String[listFileS.length];
			FileNameStringsS = new String[listFileS.length];

			// Loop through each image, get path and name
			for (int s = 0; s < listFileS.length; s++) {
				FilePathStringsS[s] = listFileS[s].getAbsolutePath();
				FileNameStringsS[s] = listFileS[s].getName();

				layoutShoes.addView(insertPhotoS(fileShoes.getAbsolutePath()));
			}

		}

	}

	private void checkState() {
		state = Environment.getExternalStorageState(); // checks what the state
		Log.d("Activity", "State: " + state);
		if (state.equals(Environment.MEDIA_MOUNTED)) { // can read and write
			canR = canW = true;

		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {

			Toast.makeText(getApplicationContext(), "Read only", 9000).show();

			canW = false;
			canR = false;
		} else {
			canR = canW = false;
		}

	}

	// to insert the photo into the layout within given position
	View insertPhoto(String path) {
		Bitmap bm = decodeSampledBitmapFromUri(path, 250, 250);

		LinearLayout layout = new LinearLayout(getApplicationContext());
		// layout.setLayoutParams(new LayoutParams(100, 250));
		layout.setGravity(Gravity.LEFT);

		ImageView imageView = new ImageView(getApplicationContext());
		// imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);

		layout.addView(imageView);
		return layout;
	}

	View insertPhotoS(String paths) {
		Bitmap bmS = decodeSampledBitmapFromUriS(paths, 250, 250);

		LinearLayout layoutS = new LinearLayout(getApplicationContext());
		// layoutS.setLayoutParams(new LayoutParams(200, 250));
		layoutS.setGravity(Gravity.LEFT);

		ImageView imageS = new ImageView(getApplicationContext());
		// imageView.setLayoutParams(new LayoutParams(220, 220));
		imageS.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageS.setImageBitmap(bmS);

		layoutS.addView(imageS);
		return layoutS;
	}

	View insertTrousers(String pathTrousers) {
		Bitmap bmTr = decodeSampledBitmapFromUriTr(pathTrousers, 250, 250);

		LinearLayout layoutTrousers = new LinearLayout(getApplicationContext());
		// layout.setLayoutParams(new LayoutParams(100, 250));
		layoutTrousers.setGravity(Gravity.LEFT);

		ImageView imageTrousers = new ImageView(getApplicationContext());
		// imageView.setLayoutParams(new LayoutParams(220, 220));
		imageTrousers.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageTrousers.setImageBitmap(bmTr);

		layoutTrousers.addView(imageTrousers);
		return layoutTrousers;
	}

	View insertSkirts(String pathSkirts) {
		Bitmap bm = decodeSampledBitmapFromUriSk(pathSkirts, 250, 250);

		LinearLayout layoutSkirts = new LinearLayout(getApplicationContext());
		// layout.setLayoutParams(new LayoutParams(100, 250));
		layoutSkirts.setGravity(Gravity.LEFT);

		ImageView imageSkirts = new ImageView(getApplicationContext());
		// imageView.setLayoutParams(new LayoutParams(220, 220));
		imageSkirts.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageSkirts.setImageBitmap(bm);

		layoutSkirts.addView(imageSkirts);
		return layoutSkirts;
	}

	View insertTops(String pathTops) {
		Bitmap bm = decodeSampledBitmapFromUri(pathTops, 250, 250);

		LinearLayout layoutTops = new LinearLayout(getApplicationContext());
		// layout.setLayoutParams(new LayoutParams(100, 250));
		layoutTops.setGravity(Gravity.LEFT);

		ImageView imageTops = new ImageView(getApplicationContext());
		// imageView.setLayoutParams(new LayoutParams(220, 220));
		imageTops.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageTops.setImageBitmap(bm);

		layoutTops.addView(imageTops);
		return layoutTops;
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;
		Bitmap bmS = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		try {
			options.inDither = false; // Disable Dithering mode
			options.inPurgeable = true; // Tell to gc that whether it needs free
										// memory, the Bitmap can be cleared
			options.inInputShareable = true; // Which kind of reference will be
												// used to recover the Bitmap
												// data after being clear, when
												// it will be used in the future
			options.inTempStorage = new byte[32 * 1024];

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options);
			bmS = BitmapFactory.decodeFile(path, options);
			//
		} catch (OutOfMemoryError e) {

			e.printStackTrace();
			if (bm != null && bm.isRecycled()) {
				bm.recycle();
				bm = null;
			}
		}

		return bm;

	}

	public Bitmap decodeSampledBitmapFromUriSk(String pathSkirts, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;
		Bitmap bmS = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			options.inJustDecodeBounds = true;
			options.inDither = false; // Disable Dithering mode
			options.inPurgeable = true; // Tell to gc that whether it needs free
										// // memory, the Bitmap can be cleared
			options.inInputShareable = true; // Which kind of reference will be
												// used to recover the Bitmap //
												// data after being clear, when
												// // it will be used in the
												// future
			options.inTempStorage = new byte[32 * 1024];

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(pathSkirts, options);
			bmS = BitmapFactory.decodeFile(pathSkirts, options);
			//
		} catch (OutOfMemoryError e) {

			e.printStackTrace();
			if (bm != null && bm.isRecycled()) {
				bm.recycle();
				bm = null;
			}
		}

		return bm;

	}

	public Bitmap decodeSampledBitmapFromUriS(String paths, int reqWidth,
			int reqHeight) {

		Bitmap bmS = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paths, options);
		try {
			options.inJustDecodeBounds = true;
			options.inDither = false; // Disable Dithering mode
			options.inPurgeable = true; // Tell to gc that whether it needs free
										// // memory, the Bitmap can be cleared
			options.inInputShareable = true; // Which kind of reference will be
												// used to recover the Bitmap //
												// data after being clear, when
												// // it will be used in the
												// future
			options.inTempStorage = new byte[32 * 1024];

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;

			bmS = BitmapFactory.decodeFile(paths, options);
			//
		} catch (OutOfMemoryError e) {

			e.printStackTrace();
			if (bmS != null && bmS.isRecycled()) {
				bmS.recycle();
				bmS = null;
			}
		}

		return bmS;

	}

	public Bitmap decodeSampledBitmapFromUriTr(String pathTrousers,
			int reqWidth, int reqHeight) {

		Bitmap bmTr = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathTrousers, options);
		try {
			options.inJustDecodeBounds = true;
			options.inDither = false; // Disable Dithering mode
			options.inPurgeable = true; // Tell to gc that whether it needs free
										// // memory, the Bitmap can be cleared
			options.inInputShareable = true; // Which kind of reference will be
												// used to recover the Bitmap //
												// data after being clear, when
												// // it will be used in the
												// future
			options.inTempStorage = new byte[32 * 1024];

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bmTr = BitmapFactory.decodeFile(pathTrousers, options);

			//
		} catch (OutOfMemoryError e) {

			e.printStackTrace();
			if (bmTr != null && bmTr.isRecycled()) {
				bmTr.recycle();
				bmTr = null;
			}
		}

		return bmTr;

	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}
}
// public boolean forDelete(){
/*
 * AlertDialog.Builder alertDialogBuilderDelete = new AlertDialog.Builder(
 * ItemsActivity.this);
 * 
 * // set title alertDialogBuilderDelete.setTitle("Delete Image");
 * 
 * // set dialog message alertDialogBuilderDelete
 * .setMessage("Delete this image?") .setCancelable(false)
 * .setPositiveButton("Yes",new DialogInterface.OnClickListener() { public void
 * onClick(DialogInterface dialog,int id) { // if this button is clicked, close
 * // current activity File toBeDeleted = new File(FilePathStrings[arg2]);
 * 
 * deleteImage(toBeDeleted);; } })
 * 
 * .setNegativeButton("No!", new DialogInterface.OnClickListener() { public void
 * onClick(DialogInterface dialog, int id) { // if this button is clicked, just
 * close // the dialog box and do nothing dialog.cancel(); } });
 * 
 * // create alert dialog AlertDialog alertDialogDelete =
 * alertDialogBuilderDelete.create();
 * 
 * // show it alertDialogDelete.show();
 * 
 * return true;
 */

/*
 * @Override public boolean onCreateOptionsMenu(Menu menu) { MenuInflater
 * menuInflater = getMenuInflater(); menuInflater.inflate(R.layout.menu, menu);
 * return true; }
 *//**
 * Event Handling for Individual menu item selected Identify single menu item
 * by it's id
 * */
/*
 * @Override public boolean onOptionsItemSelected(MenuItem item) {
 * 
 * switch (item.getItemId()) { case R.id.menu_about: // Single menu item is
 * selected do something // Ex: launching new activity/screen or show alert
 * message
 * 
 * AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
 * ItemsActivity.this);
 * 
 * // set title alertDialogBuilder.setTitle("About Us");
 * 
 * // set dialog message alertDialogBuilder .setMessage(
 * "dressApp is your closet managing and outfit planning application. " +
 * "Get to put together great outfits where ever, when ever.")
 * .setCancelable(false)
 * 
 * .setNegativeButton("Ok", new DialogInterface.OnClickListener() { public void
 * onClick(DialogInterface dialog, int id) { // if this button is clicked, just
 * close // the dialog box and do nothing dialog.cancel(); } });
 * 
 * // create alert dialog AlertDialog alertDialog = alertDialogBuilder.create();
 * 
 * // show it alertDialog.show();
 * 
 * return true;
 * 
 * case R.id.menu_share:
 * 
 * AlertDialog.Builder alertDialogBuilderShare = new AlertDialog.Builder(
 * ItemsActivity.this);
 * 
 * // set title alertDialogBuilderShare.setTitle("Share");
 * 
 * // set dialog message alertDialogBuilderShare .setMessage(
 * "Soon you will be able to share your favourite outfits with your friends and get their feedback."
 * + "Tell all your friends about the best looks." + "Stay trendy")
 * .setCancelable(false)
 * 
 * .setNegativeButton("Great!", new DialogInterface.OnClickListener() { public
 * void onClick(DialogInterface dialog, int id) { // if this button is clicked,
 * just close // the dialog box and do nothing dialog.cancel(); } });
 * 
 * // create alert dialog AlertDialog alertDialogS =
 * alertDialogBuilderShare.create();
 * 
 * // show it alertDialogS.show();
 * 
 * return true;
 * 
 * case R.id.menu_buy: AlertDialog.Builder alertDialogBuilderbuy = new
 * AlertDialog.Builder( ItemsActivity.this);
 * 
 * // set title alertDialogBuilderbuy.setTitle("Shop");
 * 
 * // set dialog message alertDialogBuilderbuy .setMessage(
 * "Keep it fresh and upgrade your fashion consciousness by shopping from your favourite brands."
 * + "Discover great looks, best styles and buy here." + "Coming Soon")
 * .setCancelable(false)
 * 
 * .setNegativeButton("Cant wait!", new DialogInterface.OnClickListener() {
 * public void onClick(DialogInterface dialog, int id) { // if this button is
 * clicked, just close // the dialog box and do nothing dialog.cancel(); } });
 * 
 * // create alert dialog AlertDialog alertDialogbuy =
 * alertDialogBuilderbuy.create();
 * 
 * // show it alertDialogbuy.show();
 * 
 * return true;
 * 
 * case R.id.menu_exit: Toast.makeText(ItemsActivity.this, "Exit is Selected",
 * Toast.LENGTH_SHORT).show(); return true;
 * 
 * default: return super.onOptionsItemSelected(item); } }
 */

