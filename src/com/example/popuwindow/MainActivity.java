package com.example.popuwindow;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
	View view;
	Button tuku;
	 Button phone ;
	 ImageView img;
	 String fileName;
	 int flag_img;
	private Uri photoUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ImageView) findViewById(R.id.imageView1);
	
		      img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 view = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop, null);
				     phone = (Button) view.findViewById(R.id.button1);
				     tuku = (Button) view.findViewById(R.id.button2);
					
					
					phone.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用android自带的照相机 
							photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
							startActivityForResult(intent, 1); 
							
							
							
							
							
						}
					});
					tuku.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(Intent.ACTION_PICK, 
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库 
							startActivityForResult(i, 2); 
						}
					});
					
					
					PopupWindow pw = new PopupWindow(view, 400, 1200);
					pw.setOutsideTouchable(true);
					pw.setFocusable(true);
				//	pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
					pw.showAsDropDown(img);
				}
			});
	}
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		//完成照相后回调用此方法 
		super.onActivityResult(requestCode, resultCode, data); 
		//    case 1: 
		switch (resultCode) { 
		case Activity.RESULT_OK://照相完成点击确定 
			String sdStatus = Environment.getExternalStorageState(); 
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用 
				Log.v("TestFile", "SD card is not avaiable/writeable right now."); 
				return; } 
			Bundle bundle = data.getExtras(); 
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式 
			FileOutputStream b = null; 
			File file = new File("/sdcard/myImage/"); 
			file.mkdirs();
			String str = null; 
			Date date = null; 
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串 
			date = new Date(resultCode); 
			str = format.format(date); 
			fileName = "/sdcard/myImage/" + str + ".jpg"; 
			
			try { 
				b = new FileOutputStream(fileName); 
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件 
				
			} catch (FileNotFoundException e) { 
				e.printStackTrace(); 
			} finally { 
				try { 
					b.flush(); 
					b.close(); 
				} catch (IOException e) { 
					e.printStackTrace(); 
				} 
			} 
			break;
			
		case Activity.RESULT_CANCELED:// 取消 
			break; 
		} 
		
		//  case 2: 
		switch (resultCode) { 
		case Activity.RESULT_OK: { 
			Uri uri = data.getData(); 
			Cursor cursor = MainActivity.this.getContentResolver().query(uri, null, 
					null, null, null); 
			cursor.moveToFirst(); 
			String imgNo = cursor.getString(0); // 图片编号 
			String imgPath = cursor.getString(1); // 图片文件路径 
			String imgSize = cursor.getString(2); // 图片大小 
			String imgName = cursor.getString(3); // 图片文件名 
			cursor.close(); 
			// Options options = new BitmapFactory.Options(); 
			// options.inJustDecodeBounds = false; 
			// options.inSampleSize = 10; 
			// Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options); 
		} 
		break; 
		case Activity.RESULT_CANCELED:// 取消 
			break; 
		} 
		
	} 
}
