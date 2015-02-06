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
							Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//����android�Դ�������� 
							photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
							startActivityForResult(intent, 1); 
							
							
							
							
							
						}
					});
					tuku.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(Intent.ACTION_PICK, 
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//����android��ͼ�� 
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
		//��������ص��ô˷��� 
		super.onActivityResult(requestCode, resultCode, data); 
		//    case 1: 
		switch (resultCode) { 
		case Activity.RESULT_OK://������ɵ��ȷ�� 
			String sdStatus = Environment.getExternalStorageState(); 
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ���� 
				Log.v("TestFile", "SD card is not avaiable/writeable right now."); 
				return; } 
			Bundle bundle = data.getExtras(); 
			Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ 
			FileOutputStream b = null; 
			File file = new File("/sdcard/myImage/"); 
			file.mkdirs();
			String str = null; 
			Date date = null; 
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// ��ȡ��ǰʱ�䣬��һ��ת��Ϊ�ַ��� 
			date = new Date(resultCode); 
			str = format.format(date); 
			fileName = "/sdcard/myImage/" + str + ".jpg"; 
			
			try { 
				b = new FileOutputStream(fileName); 
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ� 
				
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
			
		case Activity.RESULT_CANCELED:// ȡ�� 
			break; 
		} 
		
		//  case 2: 
		switch (resultCode) { 
		case Activity.RESULT_OK: { 
			Uri uri = data.getData(); 
			Cursor cursor = MainActivity.this.getContentResolver().query(uri, null, 
					null, null, null); 
			cursor.moveToFirst(); 
			String imgNo = cursor.getString(0); // ͼƬ��� 
			String imgPath = cursor.getString(1); // ͼƬ�ļ�·�� 
			String imgSize = cursor.getString(2); // ͼƬ��С 
			String imgName = cursor.getString(3); // ͼƬ�ļ��� 
			cursor.close(); 
			// Options options = new BitmapFactory.Options(); 
			// options.inJustDecodeBounds = false; 
			// options.inSampleSize = 10; 
			// Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options); 
		} 
		break; 
		case Activity.RESULT_CANCELED:// ȡ�� 
			break; 
		} 
		
	} 
}
